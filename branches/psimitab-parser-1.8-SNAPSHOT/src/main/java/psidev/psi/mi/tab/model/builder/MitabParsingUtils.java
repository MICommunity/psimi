/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.tab.model.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 08/07/2012
 * Time: 23:30
 */
public final class MitabParsingUtils {

    private static final Log log = LogFactory.getLog(MitabParsingUtils.class);

    /**
     * <p>Processes an String and splits using a set of delimiters.
     * If the delimiter is in a group surrounded by quotes, don't split that group.
     * Quotes in the String must be escaped using a backlash if that quote does not delimit a group.</p>
     * <p/>
     * <pre>
     *       aaaaa|bbbb  (delimiter '|')          = ["aaaaaa", "bbbbbb"]
     *       "aaa|aaa"|bbbbb  (delimiter '|)      = ["aaa|aaa", "bbbbb"]
     *       "aa\"a|a\"aa"|bbbbb  (delimiter '|)  = ["aa"a|a"aa", "bbbbb"]
     * </pre>
     *
     * @param str                   The string to split
     * @param delimiters            The delimiters to use
     * @param removeUnescapedQuotes remove unsecaped quotes.
     * @return An array containing the groups after splitting
     */
    public static String[] quoteAwareSplit(String str, char[] delimiters, boolean removeUnescapedQuotes) {
        if (str == null) {
            throw new NullPointerException("Null String to create Field");
        }

        if (str.isEmpty()) {
            throw new IllegalArgumentException("Empty String passed to create Field");
        }

        if (delimiters == null) {
            throw new NullPointerException("Null delimiters to create Field");
        }

        if (delimiters.length == 0) {
            throw new IllegalArgumentException("At least one delimiter char is needed");
        }

        List<String> groups = new LinkedList<String>();

        StringBuilder currGroup = new StringBuilder(str.length());

        final char[] chars = str.toCharArray();

        boolean withinQuotes = false;
        boolean previousCharIsEscape = false;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            boolean markedAsEscape = false;

            if (c == '"') {
                if (withinQuotes) {
                    if (previousCharIsEscape) {
                        if (!removeUnescapedQuotes) {
                            currGroup.append("\\");
                        }
                        currGroup.append(c);
                    } else if (currGroup.length() > 0) {
                        withinQuotes = !withinQuotes;
                    }
                } else {
                    withinQuotes = true;
                }

                if (!removeUnescapedQuotes && !previousCharIsEscape) {
                    currGroup.append(c);
                }

            } else if (arrayContains(delimiters, c)) {
                if (currGroup.length() > 0) {
                    if (!withinQuotes) {
                        groups.add(currGroup.toString());
                        // Note: the length of the stringbuilder can only be smaller, let's reuse the existing one.
                        currGroup.setLength(0);
                    } else {
                        currGroup.append(c);
                    }
                } else if (withinQuotes) {
                    currGroup.append(c);
                }
            } else if (c == '\\') {
                if (withinQuotes) {
                    previousCharIsEscape = true;
                    markedAsEscape = true;
                } else {
                    currGroup.append(c);
                }
            } else {
                currGroup.append(c);
            }

            if (!markedAsEscape) {
                previousCharIsEscape = false;
            }
        }

        if (currGroup.length() > 0) {
            groups.add(currGroup.toString());
        }

        return groups.toArray(new String[groups.size()]);
    }


    private static boolean arrayContains(char[] chars, char cToFind) {
        for (char c : chars) {
            if (c == cToFind) {
                return true;
            }
        }

        return false;
    }

    //    @SuppressWarnings("unchecked")
    public static BinaryInteraction<Interactor> buildBinaryInteraction(String[] line) throws IllegalFormatException {

        Interactor interactorA = new Interactor();
        Interactor interactorB = new Interactor();
        BinaryInteraction<Interactor> interaction = new BinaryInteractionImpl(interactorA, interactorB);

        //MITAB 2.5
        interactorA.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()]));
        interactorA.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()]));
        interactorA.setAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()]));
        interactorA.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_A.ordinal()]));

        //MITAB 2.5
        interactorB.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()]));
        interactorB.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()]));
        interactorB.setAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()]));
        interactorB.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_B.ordinal()]));

        //MITAB 2.5
        interaction.setDetectionMethods(splitInteractionDetMethods(line[PsimiTabColumns.INT_DET_METHOD.ordinal()]));
        interaction.setAuthors(splitAuthor(line[PsimiTabColumns.PUB_AUTH.ordinal()]));
        interaction.setPublications((List<CrossReference>) splitCrossReferences(line[PsimiTabColumns.PUB_ID.ordinal()]));
        interaction.setInteractionTypes(splitInteractionType(line[PsimiTabColumns.INTERACTION_TYPE.ordinal()]));
        interaction.setSourceDatabases((List<CrossReference>) splitCrossReferences(line[PsimiTabColumns.SOURCE.ordinal()]));
        interaction.setInteractionAcs((List<CrossReference>) splitCrossReferences(line[PsimiTabColumns.INTERACTION_ID.ordinal()]));
        interaction.setConfidenceValues(splitConfidences(line[PsimiTabColumns.CONFIDENCE.ordinal()]));


        //MITAB 2.6
        interactorA.setBiologicalRoles(splitCrossReferences(line[PsimiTabColumns.BIOROLE_A.ordinal()]));
        interactorA.setExperimentalRoles(splitCrossReferences(line[PsimiTabColumns.EXPROLE_A.ordinal()]));
        interactorA.setInteractorTypes(splitCrossReferences(line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()]));
        interactorA.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_A.ordinal()]));
        interactorA.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_A.ordinal()]));
        interactorA.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_A.ordinal()]));

        //MITAB 2.6
        interactorB.setBiologicalRoles(splitCrossReferences(line[PsimiTabColumns.BIOROLE_B.ordinal()]));
        interactorB.setExperimentalRoles(splitCrossReferences(line[PsimiTabColumns.EXPROLE_B.ordinal()]));
        interactorB.setInteractorTypes(splitCrossReferences(line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()]));
        interactorB.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_B.ordinal()]));
        interactorB.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_B.ordinal()]));
        interactorB.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_B.ordinal()]));

        //MITAB 2.6
        interaction.setComplexExpansion(splitComplexExpansions(line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()]));
        interaction.setXrefs((List<CrossReference>) splitCrossReferences(line[PsimiTabColumns.XREFS_I.ordinal()]));
        interaction.setAnnotations((List<Annotation>) splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_I.ordinal()]));
        interaction.setHostOrganism(splitOrganism(line[PsimiTabColumns.HOST_ORGANISM.ordinal()]));
        interaction.setParameters(splitParameters(line[PsimiTabColumns.PARAMETERS_I.ordinal()]));
        interaction.setCreationDate(splitDates(line[PsimiTabColumns.CREATION_DATE.ordinal()]));
        interaction.setUpdateDate(splitDates(line[PsimiTabColumns.UPDATE_DATE.ordinal()]));
        interaction.setChecksums((List<Checksum>) splitChecksums(line[PsimiTabColumns.CHECKSUM_I.ordinal()]));
        interaction.setNegativeInteraction(splitNegative(line[PsimiTabColumns.NEGATIVE.ordinal()]));

        //MITAB 2.7
        interactorA.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_A.ordinal()]));
        interactorA.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()]));
        interactorA.setParticipantIdentificationMethods(splitParticipantIdenMeth(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()]));


        //MITAB 2.7
        interactorB.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_B.ordinal()]));
        interactorB.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()]));
        interactorB.setParticipantIdentificationMethods(splitParticipantIdenMeth(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()]));


        return interaction;
    }

    /**
     * Reallocates an array with a new size, and copies the contents
     * of the old array to the new array.
     *
     * @param oldFormat the old array, to be reallocated.
     * @param newSize   the new array size.
     * @return A new array with the same contents.
     */
    public static String[] extendFormat(String[] oldFormat, int newSize) {

        int oldSize = oldFormat.length;
        if (oldSize >= newSize) {
            log.warn("The new size is smaller than the previous one, the array will not be modified.");
            return oldFormat;
        }

        String[] newFormat = new String[newSize];

        if (oldSize > 0) {
            System.arraycopy(oldFormat, 0, newFormat, 0, oldSize);
        }
        return newFormat;
    }

    // TODO delegate the creation of the objects

    private static Organism splitOrganism(String column) throws IllegalFormatException {

        Organism organism = null;

        if (column != null) {
            organism = new OrganismImpl();

            String[] result = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String r : result) {

                if (r != null) {
                    String[] fields = MitabParsingUtils.quoteAwareSplit(r, new char[]{':', '(', ')'}, true);

                    int length = fields.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a organism (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a organism (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1]));
                    } else if (length == 3) {
                        organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1], fields[2]));
                    }
                }
            }
        }
        return organism;
    }


    private static Collection<CrossReference> splitCrossReferences(String column) throws IllegalFormatException {

        Collection<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a cross reference (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a cross reference (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new CrossReferenceImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new CrossReferenceImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Collection<Alias> splitAliases(String column) throws IllegalFormatException {
        Collection<Alias> objects = new ArrayList<Alias>();
        Alias object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an alias (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create an alias (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new AliasImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new AliasImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<InteractionDetectionMethod> splitInteractionDetMethods(String column) throws IllegalFormatException {
        List<InteractionDetectionMethod> objects = new ArrayList<InteractionDetectionMethod>();
        InteractionDetectionMethod object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an interaction detection method (check the syntax): " + Arrays.asList(result).toString());
                    }
                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create an interaction detection method (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new InteractionDetectionMethodImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new InteractionDetectionMethodImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<InteractionType> splitInteractionType(String column) throws IllegalFormatException {
        List<InteractionType> objects = new ArrayList<InteractionType>();
        InteractionType object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an interactor type (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create an interactor type (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new InteractionTypeImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new InteractionTypeImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<Confidence> splitConfidences(String column) throws IllegalFormatException {
        List<Confidence>  objects = new ArrayList<Confidence>();
        Confidence object = null;

        if (column != null) {


            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create the confidence (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            object = new ConfidenceImpl("not-defined", result[0], "free-text");
                        }
                    } else if (length == 2) {
                        object = new ConfidenceImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new ConfidenceImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<ComplexExpansion> splitComplexExpansions(String column) throws IllegalFormatException {
        List<ComplexExpansion> objects = new ArrayList<ComplexExpansion>();
        ComplexExpansion object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an expansion complex (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        //Backward compatibility
                        if (field.equalsIgnoreCase("spoke")) {
                            object = new ComplexExpansionImpl("psi-mi", "MI:1060", "spoke expansion");
                        } else if (field.equalsIgnoreCase("matrix")) {
                            object = new ComplexExpansionImpl("psi-mi", "MI:1061", "matrix expansion");
                        } else if (field.equalsIgnoreCase("bipartite")) {
                            object = new ComplexExpansionImpl("psi-mi", "MI:1062", "bipartite expansion");
                        }
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create an expansion complex (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new ComplexExpansionImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new ComplexExpansionImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Collection<Annotation> splitAnnotations(String column) throws IllegalFormatException {
        Collection<Annotation> objects = new ArrayList<Annotation>();
        Annotation object = null;

        if (column != null) {


            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an annotation (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create an annotation (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new AnnotationImpl(result[0], result[1]);
                    } else
                        throw new IllegalFormatException("String cannot be parsed to create an annotation (check the syntax): " + Arrays.asList(result).toString());

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<Parameter> splitParameters(String column) throws IllegalFormatException {
        List<Parameter> objects = new ArrayList<Parameter>();
        Parameter object = null;

        if (column != null) {
            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a parameter (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a parameter (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new ParameterImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new ParameterImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Collection<Checksum> splitChecksums(String column) throws IllegalFormatException {

        Collection<Checksum> objects = new ArrayList<Checksum>();
        Checksum object = null;

        if (column != null) {



            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a checksum (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a checksum (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new ChecksumImpl(result[0], result[1]);
                    } else
                        throw new IllegalFormatException("String cannot be parsed to create a checksum (check the syntax): " + Arrays.asList(result).toString());

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }


    private static Collection<Integer> splitStoichiometries(String column) throws IllegalFormatException {
        Collection<Integer> objects = new ArrayList<Integer>();
        Integer object = null;

        if (column != null) {


            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a stoichiometry (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length != 1) {
                        throw new IllegalFormatException("String cannot be parsed to create a stoichiometry (check the syntax): " + Arrays.asList(result).toString());
                    }
                    if (!result[0].equalsIgnoreCase("-"))
                        object = new Integer(result[0]);

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Collection<Feature> splitFeatures(String column) throws IllegalFormatException {
        Collection<Feature> objects = new ArrayList<Feature>();
        Feature object = null;

        if (column != null) {

            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a feature (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a feature (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new FeatureImpl(result[0], Arrays.asList(result[1].split(",")));
                    } else if (length == 3) {
                        object = new FeatureImpl(result[0], Arrays.asList(result[1].split(",")), result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Collection<ParticipantIdentificationMethod> splitParticipantIdenMeth(String column) throws IllegalFormatException {
        Collection<ParticipantIdentificationMethod> objects = new ArrayList<ParticipantIdentificationMethod>();
        ParticipantIdentificationMethod object = null;

        if (column != null) {


            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a participant identification method (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length == 1) {
                        if (!result[0].equalsIgnoreCase("-")) {
                            throw new IllegalFormatException("String cannot be parsed to create a participant identification method (check the syntax): " + Arrays.asList(result).toString());
                        }
                    } else if (length == 2) {
                        object = new ParticipantIdentificationMethodImpl(result[0], result[1]);
                    } else if (length == 3) {
                        object = new ParticipantIdentificationMethodImpl(result[0], result[1], result[2]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<Author> splitAuthor(String column) throws IllegalFormatException {

        //TODO in the future add the year as a new field in the author
        //Now all is a string and we don not need split the author

        List<Author> objects = new ArrayList<Author>();
        Author object = null;

        if (column != null) {


            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create an author (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (!field.equalsIgnoreCase("-")) {
                        object = new AuthorImpl(field);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static List<Date> splitDates(String column) throws IllegalFormatException {
        List<Date> objects = new ArrayList<Date>();
        Date object = null;

        if (column != null) {



            String[] fields = MitabParsingUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParsingUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    int length = result.length;

                    // some exception handling
                    if (length == 0 || length > 3) {
                        throw new IllegalFormatException("String cannot be parsed to create a date (check the syntax): " + Arrays.asList(result).toString());
                    }

                    if (length != 1) {
                        throw new IllegalFormatException("String cannot be parsed to create a date (check the syntax): " + result[0]);
                    }
                    try {
                        if (!result[0].equalsIgnoreCase("-"))
                            object = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(result[0]);
                    } catch (ParseException e) {
                        throw new IllegalFormatException("The date cannot be parsed to create a date (check the syntax): " + result[0]);
                    }

                    if (object != null) {
                        objects.add(object);
                    }
                }
            }
        }
        return objects;
    }

    private static Boolean splitNegative(String column) throws IllegalFormatException {
        Boolean object = null;

        if (column != null) {

            String[] result = MitabParsingUtils.quoteAwareSplit(column, new char[]{':', '(', ')'}, true);
            int length = result.length;

            // some exception handling
            if (length == 0 || length > 3) {
                throw new IllegalFormatException("String cannot be parsed to create a negative field (check the syntax): " + Arrays.asList(result).toString());
            }

            if (length != 1) {
                throw new IllegalFormatException("String cannot be parsed to create a negative field (check the syntax): " + Arrays.asList(result).toString());
            }

            if (!result[0].equalsIgnoreCase("-"))
                object = Boolean.valueOf(result[0]);

        }

        return object;
    }
}
