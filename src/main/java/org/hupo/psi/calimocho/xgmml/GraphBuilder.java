/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hupo.psi.calimocho.xgmml;

import calimocho.internal.rdf.*;
import calimocho.internal.xgmml.*;
import calimocho.internal.xgmml.ObjectFactory;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class GraphBuilder {

    private String title;
    private String description;
    private String source;

    private Map<String, Node> nodeMap;

    private ObjectFactory xgmmlObjectFactory;
    private calimocho.internal.rdf.ObjectFactory rdfObjectFactory;

    public GraphBuilder() {
        this("Calimocho", "Data generated using Calimocho", "PSI");
    }

    public GraphBuilder(String title, String description, String source) {
        this.title = title;
        this.description = description;
        this.source = source;

        this.nodeMap = Maps.newHashMap();

        xgmmlObjectFactory = new ObjectFactory();
        rdfObjectFactory = new calimocho.internal.rdf.ObjectFactory();
    }

    public Graph createGraph(CalimochoDocument calimochoDocument) {
        Graph graph = xgmmlObjectFactory.createGraph();
        graph.setId("calimocho-" + System.currentTimeMillis());
        graph.setLabel(title);
//        graph.setDirected("1");
        graph.getAtts().add(createAtt("documentVersion", "1.1"));
        graph.getAtts().add(createAtt("backgroundColor", "#ffffff", ObjectType.STRING));

        graph.getAtts().add(createMetadata(graph));

        int nodeIndex = 0;

        for (Row row : calimochoDocument.getRows()) {
            Node nodeA = toNodeA(row, ++nodeIndex);
            Node nodeB = toNodeB(row, ++nodeIndex);

            Edge edge = toEdge(row, nodeA, nodeB);

            addNodeToGraph(graph, nodeA);
            addNodeToGraph(graph, nodeB);
            addEdgeToMap(graph, edge);
        }

        // calculate node positions
        int nodeSize = nodeMap.size();
        int cols = Double.valueOf(Math.ceil(Math.sqrt(nodeSize))).intValue();
        int distance = 80;

        double x = 0.0;
        double y = 0.0;
        int rowIndex = 0;
        int colIndex = 0;

        for (Node node : nodeMap.values()) {
            Graphics graphics = createGraphicsForNode(node, x, y);

            final Att graphicsNodeAtts = createAtt("cytoscapeNodeGraphicsAttributes", null);
            graphicsNodeAtts.getContent().add(createAtt("nodeLabelFont", "Default-0-12"));
            graphics.getAtts().add(graphicsNodeAtts);

            node.setGraphics(graphics);

            colIndex++;

            if (colIndex >= cols) {
                rowIndex++;
                colIndex = 0;
            }

            x = distance * colIndex;
            y = distance * rowIndex;
        }

        double viewCenter = ((cols - 1) * distance) / 2;

        graph.getAtts().add(createAtt("GRAPH_VIEW_ZOOM", "0.86823", ObjectType.REAL));
        graph.getAtts().add(createAtt("GRAPH_VIEW_CENTER_X", String.valueOf(viewCenter), ObjectType.REAL));
        graph.getAtts().add(createAtt("GRAPH_VIEW_CENTER_Y", String.valueOf(viewCenter), ObjectType.REAL));

        return graph;
    }

    private Att createMetadata(Graph graph) {
        final Att networkMetadata = createAtt("networkMetadata", null);

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

        final Date date = rdfObjectFactory.createDate();
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

    private void addEdgeToMap(Graph graph, Edge edge) {
        graph.getNodesAndEdges().add(edge);
    }

    private void addNodeToGraph(Graph graph, Node node) {
        final String key = node.getLabel();

        if (!nodeMap.containsKey(key)) {
            graph.getNodesAndEdges().add(node);
            nodeMap.put(key, node);
        }
    }


    private Node toNodeA(Row row, int nodeIndex) {
        return toNode(row.getFields(InteractionKeys.KEY_ID_A),
                row.getFields(InteractionKeys.KEY_ALTID_A),
                row.getFields(InteractionKeys.KEY_ALIAS_A),
                row.getFields(InteractionKeys.KEY_TAXID_A),
                row.getFields(InteractionKeys.KEY_BIOROLE_A),
                row.getFields(InteractionKeys.KEY_EXPROLE_A),
                row.getFields(InteractionKeys.KEY_INTERACTOR_TYPE_A),
                row.getFields(InteractionKeys.KEY_XREFS_A),
                row.getFields(InteractionKeys.KEY_ANNOTATIONS_A),
                row.getFields(InteractionKeys.KEY_PARAMETERS_A),
                nodeIndex);
    }

    private Node toNodeB(Row row, int nodeIndex) {
        return toNode(row.getFields(InteractionKeys.KEY_ID_B),
                row.getFields(InteractionKeys.KEY_ALTID_B),
                row.getFields(InteractionKeys.KEY_ALIAS_B),
                row.getFields(InteractionKeys.KEY_TAXID_B),
                row.getFields(InteractionKeys.KEY_BIOROLE_B),
                row.getFields(InteractionKeys.KEY_EXPROLE_B),
                row.getFields(InteractionKeys.KEY_INTERACTOR_TYPE_B),
                row.getFields(InteractionKeys.KEY_XREFS_B),
                row.getFields(InteractionKeys.KEY_ANNOTATIONS_B),
                row.getFields(InteractionKeys.KEY_PARAMETERS_B),
                nodeIndex);
    }

    private Node toNode(Collection<Field> idFields, Collection<Field> altidFields, Collection<Field> aliasFields,
                        Collection<Field> taxidFields, Collection<Field> bioRoleFields, Collection<Field> expRoleFields,
                        Collection<Field> typeFields, Collection<Field> xrefFields, Collection<Field> annotFields, Collection<Field> paramFields,
                        int nodeIndex) {
        String displayName = null;

        Node node = xgmmlObjectFactory.createNode();

        Multimap<String, Att> attMultimap = HashMultimap.create();

        Collection<Field> altidAndAliasFields = new ArrayList<Field>(altidFields.size() + aliasFields.size());
        altidAndAliasFields.addAll(altidFields);
        altidAndAliasFields.addAll(aliasFields);

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
        
        String key = displayName;
        
        String id = null;
        
        // Uniprot ID att for Bingo
        for (Field idField : idFields) {
            final String fieldKey = idField.get(CalimochoKeys.KEY);
            if ("uniprotkb".equals(fieldKey) || "chebi".equals(fieldKey)) {
                node.getAtts().add(createAtt("identifier type", fieldKey));
                node.getAtts().add(createAtt("identifier", idField.get(CalimochoKeys.VALUE)));
                break;
            }
        }
        
        if (key == null) {
            final Field idField = idFields.iterator().next();
            final String fieldKey = idField.get(CalimochoKeys.KEY);
            node.getAtts().add(createAtt("identifier type", fieldKey));
            node.getAtts().add(createAtt("identifier", idField.get(CalimochoKeys.VALUE)));
        }
        
        

        //species
//        String taxid = null;
//
//        if (!taxidFields.isEmpty()) {
//            taxid = taxidFields.iterator().next().get(CalimochoKeys.VALUE);
//        }
//
//        final String key = displayName + "_" + taxid;

        if (nodeMap.containsKey(key)) {
            return nodeMap.get(key);
        }

        node.setId(String.valueOf(++nodeIndex));
        node.setLabel(key);

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
        addFieldsAsAtts(paramFields, attMultimap, "param");

        // process the multimap. When there is more than one value, create a list att to wrap the atts
        final List<Att> atts = node.getAtts();

        addAttsFromMap(attMultimap, atts);

        node.getAtts().addAll(atts);

        return node;
    }


    private Edge toEdge(Row row, Node nodeA, Node nodeB) {
        Multimap<String, Att> attMultimap = HashMultimap.create();

        Edge edge = xgmmlObjectFactory.createEdge();
        edge.setSource(nodeA.getId());
        edge.setTarget(nodeB.getId());

        final Collection<Field> idFields = row.getFields(InteractionKeys.KEY_INTERACTION_ID);

        if (!idFields.isEmpty()) {
            edge.setLabel(nodeA.getLabel()+" ("+idFields.iterator().next().get(CalimochoKeys.VALUE)+") "+nodeB.getLabel());
        } else {
            edge.setLabel(nodeA.getLabel() + "(interaction)" + nodeB.getLabel());
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
        final List<Att> atts = edge.getAtts();

        addAttsFromMap(attMultimap, atts);

        edge.getAtts().addAll(atts);

        return edge;
    }

    private Att createAtt(String key, String value) {
        return createAtt(key, value, null);
    }

    private Att createAtt(String key, String value, ObjectType type) {
        Att att = xgmmlObjectFactory.createAtt();
        att.setName(key);
        att.setValue(value);
        att.setType(type);
        return att;
    }

    private void addToMultimap(Multimap<String, Att> multimap, Att att) {
        multimap.put(att.getName(), att);
    }

    private void addFieldsAsAtts(Collection<Field> idFields, Multimap<String, Att> multimap, String prefix) {
        for (Field field : idFields) {

            String prefixAndDot = "";

            if (prefix != null && !prefix.isEmpty()) {
                prefixAndDot = prefix + ".";
            }

            Att att = createAtt(prefixAndDot + field.get(CalimochoKeys.KEY), field.get(CalimochoKeys.VALUE));
            att.setType(ObjectType.STRING);

            addToMultimap(multimap, att);
        }
    }

    private void addAttsFromMap(Multimap<String, Att> attMultimap, List<Att> atts) {
        for (Map.Entry<String, Collection<Att>> entry : attMultimap.asMap().entrySet()) {
            if (entry.getValue().size() > 1) {
                Att list = createAtt(entry.getKey(), null);

                for (Att att : entry.getValue()) {
                    list.getContent().add(att);
                }

                atts.add(list);
            } else {
                atts.add(entry.getValue().iterator().next());
            }
        }
    }

    private Graphics createGraphicsForNode(Node node, double x, double y) {
        Graphics graphics = xgmmlObjectFactory.createGraphics();

        graphics.setH(25.0);
        graphics.setW(45.0);
        graphics.setFill("#ffffff");
        graphics.setWidth(new BigInteger("3"));
        graphics.setOutline("#282828");

        graphics.setX(x);
        graphics.setY(y);

        // shape depends on type
        graphics.setType(TypeGraphicsType.ELLIPSE);

        for (Att att : node.getAtts()) {
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
}
