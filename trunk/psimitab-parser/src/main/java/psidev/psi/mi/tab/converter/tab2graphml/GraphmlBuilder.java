package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Converts MITAB stream into graphml.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.1
 *        <p/>
 *        see http://graphml.graphdrawing.org/primer/graphml-primer.html
 *        <p/>
 *        http://graphml.graphdrawing.org/primer/graphml-primer.html#Graph
 *        "In GraphML there is no order defined for the appearance of node and edge elements."
 */
public class GraphmlBuilder {

    private static final Log log = LogFactory.getLog(GraphmlBuilder.class);

    public static final String NEW_LINE = "\n";

    public static final String GRAPHML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NEW_LINE +
            "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"  " + NEW_LINE +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + NEW_LINE +
            "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns" + NEW_LINE +
            "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">" + NEW_LINE +

            /* Node's properties */

            "  <key id=\"label\" for=\"node\" attr.name=\"label\" attr.type=\"string\"/>" + NEW_LINE +
            "  <key id=\"identifier\" for=\"node\" attr.name=\"identifier\" attr.type=\"string\"/>" + NEW_LINE +
            "  <key id=\"specie\" for=\"node\" attr.name=\"specie\" attr.type=\"string\"/>" + NEW_LINE +
            "  <key id=\"type\" for=\"node\" attr.name=\"type\" attr.type=\"string\"/>" + NEW_LINE +
            "  <key id=\"shape\" for=\"node\" attr.name=\"shape\" attr.type=\"string\">" + NEW_LINE +

            /* Edge's properties */

//            "  <key id=\"ac\" for=\"edge\" attr.name=\"ac\" attr.type=\"string\">"  + NEW_LINE +
//            "  <key id=\"type\" for=\"edge\" attr.name=\"type\" attr.type=\"string\">"  + NEW_LINE +
//            "  <key id=\"method\" for=\"edge\" attr.name=\"method\" attr.type=\"string\">"  + NEW_LINE +
//            "  <key id=\"pubId\" for=\"edge\" attr.name=\"pubId\" attr.type=\"string\">"  + NEW_LINE +
//            "  <key id=\"author\" for=\"edge\" attr.name=\"author\" attr.type=\"string\">"  + NEW_LINE +
//            "  <key id=\"confidence\" for=\"edge\" attr.name=\"confidence\" attr.type=\"string\">"  + NEW_LINE +
//
            "    <default>ELLIPSE</default>" + NEW_LINE +
            "  </key>" + NEW_LINE +
            "  <graph id=\"G\" edgedefault=\"undirected\">" + NEW_LINE;

    public static final String GRAPHML_FOOTER = "  </graph>" + NEW_LINE +
            "</graphml>";
    private static final String COMPLEX = "complex";
    private static final String COMPOUND = "compound";

    private int nextNodeId = 1;

    /**
     * Maps the identifier of a molecule to the node.id in GraphML.
     * TODO Should we want to convert large volumes of data, we could use ehcache instead of a HashMap.
     */
    private Map<String, Integer> molecule2node = Maps.newHashMap();

    public GraphmlBuilder() {
    }

//    protected Iterator<BinaryInteraction> getMitabIterator(InputStream is) throws ConverterException, IOException {
//
//        return reader.iterate(is);
//    }

    public String build(InputStream is) throws IOException, PsimiTabException {

        final long start = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder(8192);

        // get MITAB data from the current service
        final Iterator<BinaryInteraction> iterator;
        int interactionCount = 0;

//        boolean hasHeader = false;
        BufferedReader testReader = new BufferedReader(new InputStreamReader(is));
//        if (is.markSupported()) {
//            is.mark(0);
//            hasHeader = testReader.readLine().startsWith("ID interactor A");
//            is.reset();
//        }
        try {
			psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
			iterator = reader.iterate(testReader);
//            iterator = getMitabIterator(is, hasHeader);

            // create header of GraphML
            sb.append(GRAPHML_HEADER);

            // convert each lines into nodes and edges
            while (iterator.hasNext()) {

                BinaryInteraction interaction = iterator.next();

				Interactor A = interaction.getInteractorA();
				Interactor B = interaction.getInteractorB();

				if(A == null && B != null){
					A = B;
				}
				else if(B == null && A != null){
					B = A;
				}

				final Node nodeA = buildNode(A);
                if (nodeA.hasXml()) {
                    sb.append(nodeA.getXml());
                }

                final Node nodeB = buildNode(B);
                if (nodeB.hasXml()) {
                    sb.append(nodeB.getXml());
                }

                sb.append(buildEdge(interaction, nodeA.getId(), nodeB.getId()));

                interactionCount++;
            }

            log.info("Processed " + interactionCount + " binary interactions.");

            // create footer of GraphML
            sb.append(GRAPHML_FOOTER);

        } catch (PsimiTabException e) {

            sb.append("Failed to parse MITAB data");
            sb.append(NEW_LINE);
            sb.append(ExceptionUtils.getFullStackTrace(e));

        } finally {
            testReader.close();
            molecule2node.clear();
        }

        final String graphmlOutput = sb.toString();

        log.trace(graphmlOutput);

        final long stop = System.currentTimeMillis();
        log.info("GraphML conversion took: " + (stop - start) + "ms");

        return graphmlOutput;
    }

    private String buildEdge(BinaryInteraction interaction, int ida, int idb) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("     <edge source=\"" + ida + "\" target=\"" + idb + "\">").append(NEW_LINE);

        // TODO add more details on the edge, tho, how do we deal with multivalued fields ???

//        final String ac = getInteractionAc(interaction);
//        if( ac != null) {
//            sb.append("     <data key=\"ac\">"+ ac +"</data>").append(NEW_LINE);
//        }
//        sb.append("     <data key=\"type\"></data>").append(NEW_LINE);
//        sb.append("     <data key=\"method\"></data>").append(NEW_LINE);
//        sb.append("     <data key=\"pmid\"></data>").append(NEW_LINE);
//        sb.append("     <data key=\"author\"></data>").append(NEW_LINE);
//        sb.append("     <data key=\"confidence\"></data>").append(NEW_LINE);

        sb.append("     </edge>").append(NEW_LINE);
//        System.out.println("edge buffer size: " + sb.length() + "("+(sb.length() > 256 ? "EXPANDED" : "OK")+")");
        return sb.toString();
    }

//    private String getInteractionAc(BinaryInteraction interaction) {
//        String ac = null;
//
//        return ac;
//    }

    private class Node {
        /**
         * The selected id (based on MITAB data) that will allow to merge this node.
         */
        int id;

        /**
         * the XML snippet for that node.
         */
        String xml;

        private Node(int id, String xml) {
            this.id = id;
            this.xml = xml;
        }

        public int getId() {
            return id;
        }

        public String getXml() {
            return xml;
        }

        public boolean hasXml() {
            return xml != null;
        }
    }

    /**
     * This method converts a MITAB interactor into a node. Case must be taken not to repeat the same node if it has
     * already been processed.
     *
     * @param interactor
     * @return
     */
    private Node buildNode(Interactor interactor) {

        final CrossReference cr = pickIdentifier(interactor);
        final String db = cr.getDatabase();
        final String id = cr.getIdentifier();
        if (molecule2node.containsKey(id)) {
            return new Node(molecule2node.get(id), null); // the node was already exported, return its id instead
        }

        String moleculeType = "protein";
        if (db.equals("chebi") || db.equals("chembl") || db.equals("drugbank")) {
            moleculeType = COMPOUND;
        } else if (db.equals(COMPLEX)) {
            moleculeType = COMPLEX;
        }

        StringBuilder sb = new StringBuilder(256);
        String label = pickLabel(interactor);
        final int nodeId = getNextNodeId();
        sb.append("     <node id=\"").append(nodeId).append("\">").append(NEW_LINE);
        sb.append("        <data key=\"label\">").append(escape(label)).append("</data>").append(NEW_LINE);
        sb.append("        <data key=\"identifier\">").append(db + "#" + id).append("</data>").append(NEW_LINE);

        final String specieName = getSpecieName(interactor.getOrganism());
        if (specieName != null) {
            sb.append("        <data key=\"specie\">").append(escape(specieName)).append("</data>").append(NEW_LINE);
        }

        sb.append("        <data key=\"type\">").append(moleculeType).append("</data>").append(NEW_LINE);

        if (moleculeType.equals(COMPOUND)) {
            sb.append("        <data key=\"shape\">").append("TRIANGLE").append("</data>").append(NEW_LINE);
        } else if (moleculeType.equals(COMPLEX)) {
            sb.append("        <data key=\"shape\">").append("VEE").append("</data>").append(NEW_LINE);
        }

        sb.append("     </node>").append(NEW_LINE);
//        System.out.println("node buffer size: " + sb.length() + "("+(sb.length() > 256 ? "EXPANDED" : "OK")+")");

        molecule2node.put(id, nodeId);
        return new Node(nodeId, sb.toString());
    }

    /**
     * Makes sure the data stored in XML don't contain offending characters.
     *
     * @param s
     * @return
     */
    private String escape(String s) {
        return StringEscapeUtils.escapeXml(s);
    }

    private String getSpecieName(Organism organism) {

        String name = null;
        if (!organism.getIdentifiers().isEmpty()) {
            final CrossReference first = organism.getIdentifiers().iterator().next();
            name = first.getText();
            if (name == null || StringUtils.isEmpty(name)) {
                name = first.getIdentifier();
            }
        }

        if (name == null) {
            name = organism.getTaxid();
        }

        return name;
    }

    private int getNextNodeId() {
        return nextNodeId++;
    }

    /**
     * Pick the first relevant identifier available.
     *
     * @param interactor
     * @return
     */
    protected CrossReference pickIdentifier(Interactor interactor) {

        CrossReference identifier = null;

        if (!interactor.getIdentifiers().isEmpty()) {
            final CrossReferenceComparator comparator = new IdentifierByDatabaseComparator();
            identifier = pickFirstRelevantIdentifier(interactor.getIdentifiers(), comparator, true);
        } else if (!interactor.getAlternativeIdentifiers().isEmpty()) {
            final CrossReferenceComparator comparator = new IdentifierByDatabaseComparator();
            identifier = pickFirstRelevantIdentifier(interactor.getAlternativeIdentifiers(), comparator, true);
        }

        // get less strict and just pick the first thing available in identifier or alt. identifier.
        if (identifier == null) {
            log.debug("WARNING - Could not find a relevant identifier for interactor: " + interactor);
            if (!interactor.getIdentifiers().isEmpty()) {
                identifier = interactor.getIdentifiers().iterator().next();
            } else if (!interactor.getAlternativeIdentifiers().isEmpty()) {
                identifier = interactor.getAlternativeIdentifiers().iterator().next();
            }
        }

        if (identifier == null) {
            throw new IllegalStateException("Can't find an Identifier for interactor " + interactor);
        }

        return identifier;
    }

    /**
     * Assumes that the list of not empty.
     *
     * @param identifiers a non null, non empty list of <code>CrossRefenrence</code>.
     * @return the identifier of the first relevant cross reference based on the current IdentifierByDatabaseComparator.
     */
    private CrossReference pickFirstRelevantIdentifier(Collection<CrossReference> identifiers,
                                                       CrossReferenceComparator comparator,
                                                       boolean allowNullIfNoMatch) {

        if (identifiers == null || identifiers.isEmpty()) {
            throw new java.lang.IllegalArgumentException("You must give a non null/empty collection of identifiers");
        }

        boolean hasMatched = false;
        CrossReference picked = null;
        if (identifiers.size() > 1) {
            List<CrossReference> orderedRefs = new ArrayList<CrossReference>(identifiers);
            Collections.sort(orderedRefs, comparator);
            picked = orderedRefs.get(0);
            hasMatched = comparator.hasMatchedAny();
        } else {
            // only 1 element
            picked = identifiers.iterator().next();
            hasMatched = comparator.matches(picked);
        }

        if (allowNullIfNoMatch && !hasMatched) {
            return null;
        }

        return picked;
    }

    private static void printIdentifiers(List<CrossReference> identifiers) {
        log.info("--- ordered list of Refs ---");
        for (CrossReference cr : identifiers) {
            log.info(cr.getDatabase() + ":" + cr.getIdentifier());
        }
        log.info("----------------------------");
    }

    /**
     * Pick from the alias according to an ordered list of alias type, if none matched, then look into the alternative
     * identifiers according to an ordered list of text, if none matched, pick the shortest alias, if none found pick
     * the first alternative identifier, or if none found pick the first identifier according to the ordered list of
     * database, and if no match, pick the first one.
     *
     * @param interactor
     * @return
     */
    protected String pickLabel(Interactor interactor) {

        String label = null;

        // TODO Encourage the use of the alias type: 'display name' so that PSICQUIC providers can control the label used

        if (!interactor.getAliases().isEmpty()) {
            label = pickFirstRelevantAlias(interactor.getAliases(), new AliasByTypeComparator(), true);
        } else if (!interactor.getAlternativeIdentifiers().isEmpty()) {
            final IdentifierByTextComparator comparator = new IdentifierByTextComparator();
            CrossReference cr = pickFirstRelevantIdentifier(interactor.getAlternativeIdentifiers(), comparator, true);
            if (cr != null) label = cr.getIdentifier();
        }

        if (label == null) {
            log.debug("WARNING - Could not find a relevant label for interactor: " + interactor);
            if (!interactor.getAliases().isEmpty()) {
                label = pickFirstRelevantAlias(interactor.getAliases(), new AliasByIncreasingLengthComparator(), true);
            } else if (!interactor.getIdentifiers().isEmpty()) {
                final CrossReferenceComparator comparator = new IdentifierByDatabaseComparator();
                CrossReference cr = pickFirstRelevantIdentifier(interactor.getIdentifiers(), comparator, false);
                if (cr != null) label = cr.getIdentifier();
            } else if (!interactor.getAlternativeIdentifiers().isEmpty()) {
                final CrossReference cr = interactor.getAlternativeIdentifiers().iterator().next();
                label = cr.getIdentifier();
            }
        }

        if (label == null) {
            throw new IllegalStateException("Can't find a label for interactor " + interactor);
        }

        return label;
    }

    /**
     * Assumes that the list of not empty.
     *
     * @param aliases a non null, non empty list of <code>CrossRefenrence</code>.
     * @return the identifier of the first relevant cross reference based on the current IdentifierByDatabaseComparator.
     */
    private String pickFirstRelevantAlias(Collection<Alias> aliases,
                                          AliasComparator comparator,
                                          boolean allowNullIfNoMatch) {

        if (aliases == null || aliases.isEmpty()) {
            throw new IllegalArgumentException("You must give a non null/empty collection of aliases");
        }

        boolean hasMatched = false;
        Alias picked = null;
        if (aliases.size() > 1) {
            List<Alias> orderedRefs = new ArrayList<Alias>(aliases);
            Collections.sort(orderedRefs, comparator);
            picked = orderedRefs.get(0);
            hasMatched = comparator.hasMatchedAny();
        } else {
            picked = aliases.iterator().next();
            hasMatched = comparator.matches(picked);
        }

        if (allowNullIfNoMatch && !hasMatched) {
            return null;
        }

        return picked.getName();
    }

    private void printAliases(List<Alias> aliases) {
        log.info("--- Ordered list of Aliases ---");
        for (Alias a : aliases) {
            log.info(a.getDbSource() + ":" + a.getName() + "(" + a.getAliasType() + ")");
        }
        log.info("-------------------------------");
    }
}
