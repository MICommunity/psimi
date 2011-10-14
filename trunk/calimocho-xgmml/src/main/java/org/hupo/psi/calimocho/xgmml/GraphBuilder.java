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
import com.google.common.collect.Maps;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class GraphBuilder {
    
    private Map<String,Node> nodeMap;
    private Map<String,Edge> edgeMap;
    private ObjectFactory xgmmlObjectFactory;
    private calimocho.internal.rdf.ObjectFactory rdfObjectFactory;

    public GraphBuilder() {
        this.nodeMap = Maps.newHashMap();
        this.edgeMap = Maps.newHashMap();

        xgmmlObjectFactory = new ObjectFactory();
        rdfObjectFactory = new calimocho.internal.rdf.ObjectFactory();
    }

    public Graph createGraph(CalimochoDocument calimochoDocument) {
        Graph graph = xgmmlObjectFactory.createGraph();
        graph.setId("calimocho-"+System.currentTimeMillis());
        graph.setLabel("Calimocho");
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
        
        double viewCenter = ((cols-1) * distance) / 2;
                
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
        description.setContent("Data generated using Calimocho");
        rdfDescription.getDcmes().add(rdfObjectFactory.createDescription(description));
        
        final Identifier identifier = rdfObjectFactory.createIdentifier();
        identifier.setContent("N/A");
        rdfDescription.getDcmes().add(rdfObjectFactory.createIdentifier(identifier));
        
        final Date date = rdfObjectFactory.createDate();
        date.setContent(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        rdfDescription.getDcmes().add(rdfObjectFactory.createDate(date));
        
        final Title title = rdfObjectFactory.createTitle();
        title.setContent("Calimocho");
        rdfDescription.getDcmes().add(rdfObjectFactory.createTitle(title));

        final Source source = rdfObjectFactory.createSource();
        source.setContent("http://www.ebi.ac.uk/intact");
        rdfDescription.getDcmes().add(rdfObjectFactory.createSource(source));

        final Format format = rdfObjectFactory.createFormat();
        format.setContent("Cytoscape-XGMML");
        rdfDescription.getDcmes().add(rdfObjectFactory.createFormat(format));

        networkMetadata.getContent().add(rdfRDF);
        
        return networkMetadata;
    }

    private void addEdgeToMap(Graph graph, Edge edge) {
        if (!edgeMap.containsKey(edge.getLabel())) {
            graph.getNodesAndEdges().add(edge);
            edgeMap.put(edge.getLabel(), edge);
        }
    }

    private void addNodeToGraph(Graph graph, Node node) {
        if (!nodeMap.containsKey(node.getLabel())) {
            graph.getNodesAndEdges().add(node);
            nodeMap.put(node.getLabel(), node);
        }
    }


    private Node toNodeA(Row row, int nodeIndex) {
        return toNode(row.getFields(InteractionKeys.KEY_ID_A), nodeIndex);
    }

    private Node toNodeB(Row row, int nodeIndex) {
        return toNode(row.getFields(InteractionKeys.KEY_ID_B), nodeIndex);
    }

    private Node toNode(Collection<Field> idFields, int nodeIndex) {
        final String idValue = idFields.iterator().next().get(CalimochoKeys.VALUE);

        if (nodeMap.containsKey(idValue)) {
            return nodeMap.get(idValue);
        }
        
        Node node = xgmmlObjectFactory.createNode();
        node.setId(String.valueOf(++nodeIndex));
        node.setLabel(idValue);

        for (Field field : idFields) {
            Att att = createAtt(field.get(CalimochoKeys.KEY), field.get(CalimochoKeys.VALUE));
            att.setType(ObjectType.STRING);
            node.getAtts().add(att);
        }

        return node;
    }

    private Edge toEdge(Row row, Node nodeA, Node nodeB) {
        Edge edge = xgmmlObjectFactory.createEdge();
        edge.setSource(nodeA.getId());
        edge.setTarget(nodeB.getId());

        final Collection<Field> idFields = row.getFields(InteractionKeys.KEY_INTERACTION_ID);

        if (!idFields.isEmpty()) {
            edge.setLabel(idFields.iterator().next().get(CalimochoKeys.VALUE));
        } else {
            edge.setLabel("Interaction-" + nodeA.getId() + "-" + nodeB.getId());
        }
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

    private Graphics createGraphicsForNode(Node node, double x, double y) {
        Graphics graphics = xgmmlObjectFactory.createGraphics();
        graphics.setType(TypeGraphicsType.RECTANGLE);
        graphics.setH(25.0);
        graphics.setW(45.0);
        graphics.setFill("#008080");
        graphics.setWidth(new BigInteger("3"));
        graphics.setOutline("#282828");

        graphics.setX(x);
        graphics.setY(y);

        return graphics;
    }
}
