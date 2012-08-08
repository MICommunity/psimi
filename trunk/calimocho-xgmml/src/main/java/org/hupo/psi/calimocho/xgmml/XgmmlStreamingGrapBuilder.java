package org.hupo.psi.calimocho.xgmml;

import calimocho.internal.rdf.*;
import calimocho.internal.xgmml.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.DefaultRowReader;
import org.hupo.psi.calimocho.tab.io.IllegalColumnException;
import org.hupo.psi.calimocho.tab.io.RowReader;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The graph streaming builder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/08/12</pre>
 */

public class XgmmlStreamingGrapBuilder {

    private String title;
    private String description;
    private String source;

    private Map<String, XgmmlNode> nodeMap;

    private calimocho.internal.xgmml.ObjectFactory xgmmlObjectFactory;
    private calimocho.internal.rdf.ObjectFactory rdfObjectFactory;

    private XMLStreamWriter xmlOut;
    private CalimochoXgmmlStreamingMarshall<calimocho.internal.xgmml.Att> attributeStreamBuilder;
    private CalimochoXgmmlStreamingMarshall<calimocho.internal.xgmml.Node> nodeStreamBuilder;
    private CalimochoXgmmlStreamingMarshall<calimocho.internal.xgmml.Edge> edgeStreamBuilder;

    private RowReader calimochoReader;

    private NamespacePrefixMapper mapper;

    public XgmmlStreamingGrapBuilder() throws JAXBException{
        this("Calimocho", "Data generated using Calimocho", "PSI");
    }

    public XgmmlStreamingGrapBuilder(String title, String description, String source) throws JAXBException{
        this.title = title;
        this.description = description;
        this.source = source;

        this.nodeMap = Maps.newHashMap();

        xgmmlObjectFactory = new calimocho.internal.xgmml.ObjectFactory();
        rdfObjectFactory = new calimocho.internal.rdf.ObjectFactory();

        mapper = new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean b) {
                if ("http://www.cs.rpi.edu/XGMML".equals(namespaceUri)) {
                    return null;
                } else if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(namespaceUri)) {
                    return "rdf";
                } else if ("http://purl.org/dc/elements/1.1/".equals(namespaceUri)) {
                    return "dc";
                } else if ("http://www.w3.org/1999/xlink".equals(namespaceUri)) {
                    return "xlink";
                }
                return null;
            }
        };

        this.attributeStreamBuilder = new CalimochoXgmmlStreamingMarshall(calimocho.internal.xgmml.Att.class, this.xmlOut, this.mapper);
        this.nodeStreamBuilder = new CalimochoXgmmlStreamingMarshall(calimocho.internal.xgmml.Node.class, this.xmlOut, this.mapper);
        this.edgeStreamBuilder = new CalimochoXgmmlStreamingMarshall(calimocho.internal.xgmml.Edge.class, this.xmlOut, this.mapper);
    }

    public OutputStream open(String filename) throws XMLStreamException, IOException
    {
        OutputStream fileStream = new FileOutputStream(filename);

        open(fileStream);

        return fileStream;
    }

    public void close(OutputStream stream) throws XMLStreamException, IOException {
        xmlOut.writeEndElement();
        xmlOut.writeEndDocument();
        xmlOut.flush();
        xmlOut.close();
        stream.close();
    }

    public void open(OutputStream outputStream) throws XMLStreamException, IOException
    {
        xmlOut = new IndentingXMLStreamWriter(XMLOutputFactory.newFactory().createXMLStreamWriter(outputStream));
        xmlOut.setDefaultNamespace("http://www.cs.rpi.edu/XGMML");
        xmlOut.setNamespaceContext(new NamespaceContext() {
            public Iterator getPrefixes(String namespaceURI) {
                return null;
            }

            public String getPrefix(String namespaceURI) {
                return "";
            }

            public String getNamespaceURI(String prefix) {
                return null;
            }
        });


        this.attributeStreamBuilder.setXmlOut(xmlOut);
        this.nodeStreamBuilder.setXmlOut(xmlOut);
        this.edgeStreamBuilder.setXmlOut(xmlOut);
        xmlOut.writeStartDocument();
        xmlOut.writeStartElement("http://www.cs.rpi.edu/XGMML", "graph");
        xmlOut.writeDefaultNamespace("http://www.cs.rpi.edu/XGMML");
        xmlOut.writeNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        xmlOut.writeNamespace("dc", "http://purl.org/dc/elements/1.1/");
        xmlOut.writeNamespace("xlink", "http://www.w3.org/1999/xlink");

        xmlOut.flush();
    }

    public void writeAttributes(String name, String value) throws XMLStreamException {
        xmlOut.writeAttribute(name, value);
        xmlOut.flush();
    }

    public void createAndWriteGraph(OutputStream outputStream, InputStream mitabStream, ColumnBasedDocumentDefinition docDefinition, int numberOfResults) throws XMLStreamException, IOException, JAXBException {
        this.calimochoReader = new DefaultRowReader(docDefinition);

        writeAttributes("id", "calimocho-" + System.currentTimeMillis());
        writeAttributes("title", title);

        attributeStreamBuilder.write(createAtt("documentVersion", "1.1"));
        attributeStreamBuilder.write(createAtt("backgroundColor", "#ffffff", calimocho.internal.xgmml.ObjectType.STRING));
        attributeStreamBuilder.write(createMetadata());
        attributeStreamBuilder.write(createAtt("GRAPH_VIEW_ZOOM", "0.86823", calimocho.internal.xgmml.ObjectType.REAL));

        // calculate node size estimation
        int nodeSize = numberOfResults*2;
        int distance = 80;
        int cols = Double.valueOf(Math.ceil(Math.sqrt(nodeSize))).intValue();
        double viewCenter = ((cols - 1) * distance) / 2;

        attributeStreamBuilder.write(createAtt("GRAPH_VIEW_CENTER_X", String.valueOf(viewCenter), calimocho.internal.xgmml.ObjectType.REAL));
        attributeStreamBuilder.write(createAtt("GRAPH_VIEW_CENTER_Y", String.valueOf(viewCenter), calimocho.internal.xgmml.ObjectType.REAL));

        // start exporting results
        int nodeIndex = 0;
        double x = 0.0;
        double y = 0.0;
        int rowIndex = 0;
        int colIndex = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(mitabStream));

        try{
            String line = reader.readLine();

            while (line != null) {
                try {
                    Row row = calimochoReader.readLine(line);
                    XgmmlNode nodeA = toNodeA(row, ++nodeIndex, rowIndex, colIndex, cols, distance);
                    XgmmlNode nodeB = null;
                    // node A is not null
                    if (nodeA != null){
                        // we need to update coordinate if a new node has been found
                        if (!this.nodeMap.containsKey(nodeA.getKey())){
                            colIndex = nodeA.getColIndex();
                            rowIndex = nodeA.getRowIndex();
                            this.nodeMap.put(nodeA.getKey(), nodeA);
                        }

                        // we create nodeB
                        nodeB = toNodeB(row, ++nodeIndex, rowIndex, colIndex, cols, distance);
                        // self interaction, only one interactor is provided
                        if (nodeB == null){
                            nodeB = nodeA;
                        }
                        // we need to update coordinate if a new node has been found
                        else if (!this.nodeMap.containsKey(nodeB.getKey())){
                            colIndex = nodeB.getColIndex();
                            rowIndex = nodeB.getRowIndex();
                            this.nodeMap.put(nodeB.getKey(), nodeB);
                        }
                    }
                    // self interaction, only one interactor is provided and it should be nodeB
                    else {
                        // we create nodeB
                        nodeB = toNodeB(row, ++nodeIndex, rowIndex, colIndex, cols, distance);

                        nodeA = nodeB;

                        if (nodeB != null && !this.nodeMap.containsKey(nodeB.getKey())){
                            colIndex = nodeB.getColIndex();
                            rowIndex = nodeB.getRowIndex();
                            this.nodeMap.put(nodeB.getKey(), nodeB);
                        }
                    }

                    if (nodeA != null && nodeB != null){
                        toEdge(row, nodeA, nodeB);
                    }
                }
                catch( IllegalRowException e){
                    System.out.println("Ignore badly formatted mitab line : " + line);
                }
                catch( IllegalColumnException e){
                    System.out.println("Ignore badly formatted mitab line : " + line);
                }
                catch( IllegalFieldException e){
                    System.out.println("Ignore badly formatted mitab line : " + line);
                }

                line = reader.readLine();
            }
        }
        finally {
            reader.close();
        }
    }

    private calimocho.internal.xgmml.Att createMetadata() {
        final calimocho.internal.xgmml.Att networkMetadata = createAtt("networkMetadata", null);

        final RdfRDF rdfRDF = rdfObjectFactory.createRdfRDF();
        final RdfDescription rdfDescription = rdfObjectFactory.createRdfDescription();
        rdfRDF.getDescriptions().add(rdfDescription);

        final Type type = rdfObjectFactory.createType();
        type.setContent("Molecular Interactions");
        rdfDescription.getDcmes().add(rdfObjectFactory.createType(type));

        final Description description = rdfObjectFactory.createDescription();
        description.setContent(this.description);
        rdfDescription.getDcmes().add(rdfObjectFactory.createDescription(description));

        final Identifier identifier = rdfObjectFactory.createIdentifier();
        identifier.setContent("N/A");
        rdfDescription.getDcmes().add(rdfObjectFactory.createIdentifier(identifier));

        final calimocho.internal.rdf.Date date = rdfObjectFactory.createDate();
        date.setContent(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        rdfDescription.getDcmes().add(rdfObjectFactory.createDate(date));

        final Title title = rdfObjectFactory.createTitle();
        title.setContent(this.title);
        rdfDescription.getDcmes().add(rdfObjectFactory.createTitle(title));

        final Source source = rdfObjectFactory.createSource();
        source.setContent(this.source);
        rdfDescription.getDcmes().add(rdfObjectFactory.createSource(source));

        final Format format = rdfObjectFactory.createFormat();
        format.setContent("Cytoscape-XGMML");
        rdfDescription.getDcmes().add(rdfObjectFactory.createFormat(format));

        networkMetadata.getContent().add(rdfRDF);

        return networkMetadata;
    }

    private XgmmlNode toNodeA(Row row, int nodeIndex, int rowIndex, int colIndex, int cols, int distance) throws JAXBException,XMLStreamException {
        return toNode(row.getFields(InteractionKeys.KEY_ID_A),
                row.getFields(InteractionKeys.KEY_ALTID_A),
                row.getFields(InteractionKeys.KEY_ALIAS_A),
                row.getFields(InteractionKeys.KEY_TAXID_A),
                row.getFields(InteractionKeys.KEY_BIOROLE_A),
                row.getFields(InteractionKeys.KEY_EXPROLE_A),
                row.getFields(InteractionKeys.KEY_INTERACTOR_TYPE_A),
                row.getFields(InteractionKeys.KEY_XREFS_A),
                row.getFields(InteractionKeys.KEY_ANNOTATIONS_A),
                nodeIndex, rowIndex, colIndex, cols, distance);
    }

    private XgmmlNode toNodeB(Row row, int nodeIndex, int rowIndex, int colIndex, int cols, int distance) throws JAXBException, XMLStreamException {
        return toNode(row.getFields(InteractionKeys.KEY_ID_B),
                row.getFields(InteractionKeys.KEY_ALTID_B),
                row.getFields(InteractionKeys.KEY_ALIAS_B),
                row.getFields(InteractionKeys.KEY_TAXID_B),
                row.getFields(InteractionKeys.KEY_BIOROLE_B),
                row.getFields(InteractionKeys.KEY_EXPROLE_B),
                row.getFields(InteractionKeys.KEY_INTERACTOR_TYPE_B),
                row.getFields(InteractionKeys.KEY_XREFS_B),
                row.getFields(InteractionKeys.KEY_ANNOTATIONS_B),
                nodeIndex, rowIndex, colIndex, cols, distance);
    }

    private String extractKeyValueForNode(Collection<Field> altidAndAliasFields, Collection<Field> idFields){
        String key = null;

        if (altidAndAliasFields != null){
            for (Field alField : altidAndAliasFields) {
                if ("gene name".equals(alField.get(CalimochoKeys.TEXT))) {
                    key = alField.get(CalimochoKeys.VALUE);
                    return key;
                }
            }
        }

        if (key == null && idFields != null) {
            for (Field idField : idFields) {
                if ("uniprotkb".equals(idField.get(CalimochoKeys.KEY)) || "chebi".equals(idField.get(CalimochoKeys.KEY))) {
                    key = idField.get(CalimochoKeys.VALUE);
                    return key;
                }
            }

            if (key == null) {
                key = idFields.iterator().next().get(CalimochoKeys.VALUE);
            }
        }

        return key;
    }

    private XgmmlNode toNode(Collection<Field> idFields, Collection<Field> altidFields, Collection<Field> aliasFields,
                             Collection<Field> taxidFields, Collection<Field> bioRoleFields, Collection<Field> expRoleFields,
                             Collection<Field> typeFields, Collection<Field> xrefFields, Collection<Field> annotFields, int nodeIndex, int rowIndex, int colIndex, int cols, int distance) throws JAXBException, XMLStreamException {

        int initialSize=0;
        if (altidFields != null){
            initialSize+=altidFields.size();
        }
        if (aliasFields != null){
            initialSize+=aliasFields.size();
        }
        Collection<Field> altidAndAliasFields = new ArrayList< Field>(initialSize);
        altidAndAliasFields.addAll(altidFields);
        altidAndAliasFields.addAll(aliasFields);

        String key = extractKeyValueForNode(altidAndAliasFields, idFields);

        if (key == null){
            return null;
        }
        else if (nodeMap.containsKey(key)) {
            return nodeMap.get(key);
        }

        String nodeId = String.valueOf(++nodeIndex);
        String displayName = null;

        calimocho.internal.xgmml.Node node = xgmmlObjectFactory.createNode();

        node.setId(nodeId);
        node.setLabel(key);

        Multimap<String, calimocho.internal.xgmml.Att> attMultimap = HashMultimap.create();

        for (Field alField : altidAndAliasFields) {
            if ("gene name".equals(alField.get(CalimochoKeys.TEXT))) {
                displayName = alField.get(CalimochoKeys.VALUE);
                node.getAtts().add(createAtt("gene name", displayName));
                break;
            }
        }

        if (displayName == null) {
            for (Field idField : idFields) {
                if ("uniprotkb".equals(idField.get(CalimochoKeys.KEY)) || "chebi".equals(idField.get(CalimochoKeys.KEY))) {
                    displayName = idField.get(CalimochoKeys.VALUE);
                    break;
                }
            }

            if (displayName == null) {
                displayName = idFields.iterator().next().get(CalimochoKeys.VALUE);
            }
        }

        if (displayName == null) {
            final Field idField = idFields.iterator().next();
            final String fieldKey = idField.get(CalimochoKeys.KEY);
            node.getAtts().add(createAtt("identifier type", fieldKey));

            String value = idField.get(CalimochoKeys.VALUE);
            node.getAtts().add(createAtt("identifier", value));
        }

        // Uniprot ID att for Bingo
        for (Field idField : idFields) {
            final String fieldKey = idField.get(CalimochoKeys.KEY);
            if ("uniprotkb".equals(fieldKey) || "chebi".equals(fieldKey)) {
                node.getAtts().add(createAtt("identifier type", fieldKey));
                node.getAtts().add(createAtt("identifier", idField.get(CalimochoKeys.VALUE)));
                break;
            }
        }

        addToMultimap(attMultimap, createAtt("canonicalName", displayName, ObjectType.STRING));

        addFieldsAsAtts(idFields, attMultimap, "id");
        addFieldsAsAtts(altidFields, attMultimap, "altid");
        addFieldsAsAtts(aliasFields, attMultimap, "alias");
        addFieldsAsAtts(taxidFields, attMultimap, null);
        addFieldsAsAtts(bioRoleFields, attMultimap, "biorole");
        addFieldsAsAtts(expRoleFields, attMultimap, "exprole");
        addFieldsAsAtts(typeFields, attMultimap, "type");
        addFieldsAsAtts(xrefFields, attMultimap, "xref");
        addFieldsAsAtts(annotFields, attMultimap, "annot");

        // process the multimap. When there is more than one value, create a list att to wrap the atts
        final List<Att> atts = node.getAtts();

        addAttsFromMap(attMultimap, atts);

        node.getAtts().addAll(atts);

        XgmmlNode xgmmlNode = new XgmmlNode(key, nodeId, rowIndex, colIndex, cols, distance);
        // process graphics now
        calimocho.internal.xgmml.Graphics graphics = createGraphicsForNode(node, xgmmlNode);
        node.setGraphics(graphics);

        // write node
        this.nodeStreamBuilder.write(node);

        return xgmmlNode;
    }

    private calimocho.internal.xgmml.Graphics createGraphicsForNode(calimocho.internal.xgmml.Node node, XgmmlNode xgmmlNode) {
        calimocho.internal.xgmml.Graphics graphics = xgmmlObjectFactory.createGraphics();

        graphics.setH(25.0);
        graphics.setW(45.0);
        graphics.setFill("#ffffff");
        graphics.setWidth(new BigInteger("3"));
        graphics.setOutline("#282828");

        graphics.setX(xgmmlNode.getX());
        graphics.setY(xgmmlNode.getY());

        // shape depends on type
        graphics.setType(calimocho.internal.xgmml.TypeGraphicsType.ELLIPSE);

        for (calimocho.internal.xgmml.Att att : node.getAtts()) {
            if (att.getName().startsWith("type.")) {

                if ("MI:0326".equals(att.getValue())) { // protein
                    graphics.setFill("#00ffcc");
                } else if ("MI:0328".equals(att.getValue())) { // small molecule
                    graphics.setType(TypeGraphicsType.TRIANGLE);
                    graphics.setFill("#33CCCC");
                }

            }
        }

        return graphics;
    }

    private void toEdge(Row row, XgmmlNode nodeA, XgmmlNode nodeB) throws JAXBException, XMLStreamException{
        Multimap<String, calimocho.internal.xgmml.Att> attMultimap = HashMultimap.create();

        calimocho.internal.xgmml.Edge edge = xgmmlObjectFactory.createEdge();
        edge.setSource(nodeA.getId());
        edge.setTarget(nodeB.getId());

        final Collection<Field> idFields = row.getFields(InteractionKeys.KEY_INTERACTION_ID);

        if (!idFields.isEmpty()) {
            edge.setLabel(nodeA.getKey()+" ("+idFields.iterator().next().get(CalimochoKeys.VALUE)+") "+nodeB.getKey());
        } else {
            edge.setLabel(nodeA.getKey() + "(interaction)" + nodeB.getKey());
        }

        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_DETMETHOD), attMultimap, "detmethod");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_PUBAUTH), attMultimap, "pubauth");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_PUBID), attMultimap, "pubid");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_INTERACTION_TYPE), attMultimap, "type");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_SOURCE), attMultimap, "source");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_INTERACTION_ID), attMultimap, "id");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_CONFIDENCE), attMultimap, "confidence");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_DATASET), attMultimap, "dataset");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_EXPANSION), attMultimap, null);
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_XREFS_I), attMultimap, "xref");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_HOST_ORGANISM), attMultimap, "host");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_ANNOTATIONS_I), attMultimap, "annot");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_PARAMETERS_I), attMultimap, "param");
        addFieldsAsAtts(row.getFields(InteractionKeys.KEY_NEGATIVE), attMultimap, "negative");

        // process the multimap. When there is more than one value, create a list att to wrap the atts
        final List<calimocho.internal.xgmml.Att> atts = edge.getAtts();

        addAttsFromMap(attMultimap, atts);

        edge.getAtts().addAll(atts);

        // write edge
        this.edgeStreamBuilder.write(edge);
    }

    private calimocho.internal.xgmml.Att createAtt(String key, String value) {
        return createAtt(key, value, null);
    }

    private calimocho.internal.xgmml.Att createAtt(String key, String value, ObjectType type) {
        calimocho.internal.xgmml.Att att = xgmmlObjectFactory.createAtt();
        att.setName(key);
        att.setValue(value);
        att.setType(type);
        return att;
    }

    private void addToMultimap(Multimap<String, calimocho.internal.xgmml.Att> multimap, calimocho.internal.xgmml.Att att) {
        multimap.put(att.getName(), att);
    }

    private void addFieldsAsAtts(Collection<Field> idFields, Multimap<String, calimocho.internal.xgmml.Att> multimap, String prefix) {
        for (Field field : idFields) {

            String prefixAndDot = "";

            if (prefix != null && !prefix.isEmpty()) {
                prefixAndDot = prefix + ".";
            }

            calimocho.internal.xgmml.Att att = createAtt(prefixAndDot + field.get(CalimochoKeys.KEY), field.get(CalimochoKeys.VALUE));
            att.setType(calimocho.internal.xgmml.ObjectType.STRING);

            addToMultimap(multimap, att);
        }
    }

    private void addAttsFromMap(Multimap<String, calimocho.internal.xgmml.Att> attMultimap, List<calimocho.internal.xgmml.Att> atts) {
        for (Map.Entry<String, Collection<calimocho.internal.xgmml.Att>> entry : attMultimap.asMap().entrySet()) {
            if (entry.getValue().size() > 1) {
                calimocho.internal.xgmml.Att list = createAtt(entry.getKey(), null);

                for (calimocho.internal.xgmml.Att att : entry.getValue()) {
                    list.getContent().add(att);
                }

                atts.add(list);
            } else {
                atts.add(entry.getValue().iterator().next());
            }
        }
    }
}
