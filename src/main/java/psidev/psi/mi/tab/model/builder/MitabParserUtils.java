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

import org.apache.commons.lang.ArrayUtils;
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
public final class MitabParserUtils {

    private static final Log log = LogFactory.getLog(MitabParserUtils.class);

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


    public static BinaryInteraction<Interactor> buildBinaryInteraction(String[] line) throws IllegalFormatException {

        if (line == null) {
            throw new NullPointerException("Null line to create to create a BinaryInteraction");
        }

        if (line.length == 0) {
            throw new IllegalArgumentException("Empty line passed to create a BinaryInteraction");
        }

        if (line.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
            line = MitabParserUtils.extendFormat(line, PsimiTabColumns.MITAB_LENGTH.ordinal());
        }

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
        interaction.setDetectionMethods(splitCrossReferences(line[PsimiTabColumns.INT_DET_METHOD.ordinal()]));
        interaction.setAuthors(splitAuthor(line[PsimiTabColumns.PUB_AUTH.ordinal()]));
        interaction.setPublications(splitCrossReferences(line[PsimiTabColumns.PUB_ID.ordinal()]));
        interaction.setInteractionTypes(splitCrossReferences(line[PsimiTabColumns.INTERACTION_TYPE.ordinal()]));
        interaction.setSourceDatabases(splitCrossReferences(line[PsimiTabColumns.SOURCE.ordinal()]));
        interaction.setInteractionAcs(splitCrossReferences(line[PsimiTabColumns.INTERACTION_ID.ordinal()]));
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
        interaction.setComplexExpansion(splitCrossReferences(line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()]));
        interaction.setMitabXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_I.ordinal()]));
        interaction.setMitabAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_I.ordinal()]));
        interaction.setHostOrganism(splitOrganism(line[PsimiTabColumns.HOST_ORGANISM.ordinal()]));
        interaction.setMitabParameters(splitParameters(line[PsimiTabColumns.PARAMETERS_I.ordinal()]));
        interaction.setCreationDate(splitDates(line[PsimiTabColumns.CREATION_DATE.ordinal()]));
        interaction.setUpdateDate(splitDates(line[PsimiTabColumns.UPDATE_DATE.ordinal()]));
        interaction.setMitabChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_I.ordinal()]));
        interaction.setNegativeInteraction(splitNegative(line[PsimiTabColumns.NEGATIVE.ordinal()]));

        //MITAB 2.7
        interactorA.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_A.ordinal()]));
        interactorA.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()]));
        interactorA.setParticipantIdentificationMethods(splitCrossReferences(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()]));


        //MITAB 2.7
        interactorB.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_B.ordinal()]));
        interactorB.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()]));
        interactorB.setParticipantIdentificationMethods(splitCrossReferences(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()]));

		//We check some consistency in the interactors

		if(!interactorA.isEmpty() && (interactorA.getIdentifiers() == null || interactorA.getIdentifiers().isEmpty())){
			//We have some information in the interactor A, but is hasn't a identifier so we throw an exception.
			throw new IllegalFormatException("The interactor A has not an identifier but contains information in other attributes." +
					"Please, add an identifier: " + interactorA.toString());
		}

		if(!interactorB.isEmpty() && (interactorB.getIdentifiers() == null || interactorB.getIdentifiers().isEmpty())){
			//We have some information in the interactor A, but is hasn't a identifier so we throw an exception.
			throw new IllegalFormatException("The interactor B has not an identifier but contains information in other attributes. " +
					"Please, add an identifier: " + interactorB.toString());
		}

		if(interactorA.isEmpty() && interactorB.isEmpty()){
			//We don't have interactor, so we throw an exception
			throw new IllegalFormatException("Both interactors are null or empety. We can have a interaction without interactors");
		}

		//We check if it is a intra-inter interaction. In that case one of the interactors can be null
		if(interactorA.isEmpty() && !interactorB.isEmpty()){
			interaction.setInteractorA(null);
		}

		if(interactorB.isEmpty() && !interactorA.isEmpty()){
			interaction.setInteractorB(null);
		}

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

    public static Organism splitOrganism(String column) throws IllegalFormatException {

        Organism organism = null;

        if (column != null && !column.isEmpty()) {
            organism = new OrganismImpl();

            String[] result = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);

            for (String r : result) {

                if (r != null) {
                    String[] fields = MitabParserUtils.quoteAwareSplit(r, new char[]{':', '(', ')'}, true);
                    if (fields != null) {
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
        }
        return organism;
    }


    public static List<CrossReference> splitCrossReferences(String column) throws IllegalFormatException {

        List<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            throw new IllegalFormatException("String cannot be parsed to create a cross reference (check the syntax): " + Arrays.asList(result).toString());
                        }

                        if (length == 1) {
                            //Backward compatibility
                            if (field.equalsIgnoreCase("spoke")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1060", "spoke expansion");
                            } else if (field.equalsIgnoreCase("matrix")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1061", "matrix expansion");
                            } else if (field.equalsIgnoreCase("bipartite")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1062", "bipartite expansion");
							} else if (!result[0].equalsIgnoreCase("-")) {
                                throw new IllegalFormatException("String cannot be parsed to create a cross reference (check the syntax): " + ArrayUtils.toString(result));
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
        }
        return objects;
    }

    public static List<Alias> splitAliases(String column) throws IllegalFormatException {
        List<Alias> objects = new ArrayList<Alias>();
        Alias object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static List<Confidence> splitConfidences(String column) throws IllegalFormatException {
        List<Confidence> objects = new ArrayList<Confidence>();
        Confidence object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static List<Annotation> splitAnnotations(String column) throws IllegalFormatException {
        List<Annotation> objects = new ArrayList<Annotation>();
        Annotation object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            throw new IllegalFormatException("String cannot be parsed to create an annotation (check the syntax): " + Arrays.asList(result).toString());
                        }

                        if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {

                                //We allow annotations only with free text.
                                object = new AnnotationImpl(result[0]);
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
        }

        return objects;
    }

    public static List<Parameter> splitParameters(String column) throws IllegalFormatException {
        List<Parameter> objects = new ArrayList<Parameter>();
        Parameter object = null;

        if (column != null && !column.isEmpty()) {
            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static List<Checksum> splitChecksums(String column) throws IllegalFormatException {

        List<Checksum> objects = new ArrayList<Checksum>();
        Checksum object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }


    public static List<Integer> splitStoichiometries(String column) throws IllegalFormatException {
        List<Integer> objects = new ArrayList<Integer>();
        Integer object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static List<Feature> splitFeatures(String column) throws IllegalFormatException {
        List<Feature> objects = new ArrayList<Feature>();
        Feature object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            throw new IllegalFormatException("String cannot be parsed to create a feature (check the syntax): " + Arrays.asList(result).toString());
                        }

                        if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {

                                //We have a feature without ranges {?-?}
                                String[] undeterminedRange = {"?-?"};
                                object = new FeatureImpl(result[0], Arrays.asList(undeterminedRange));
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
        }
        return objects;
    }

    public static List<Author> splitAuthor(String column) throws IllegalFormatException {

        //TODO in the future add the year as a new field in the author
        //Now all is a string and we don not need split the author

        List<Author> objects = new ArrayList<Author>();
        Author object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static List<Date> splitDates(String column) throws IllegalFormatException {
        List<Date> objects = new ArrayList<Date>();
        Date object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

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
        }
        return objects;
    }

    public static Boolean splitNegative(String column) throws IllegalFormatException {
        boolean object = false;

        if (column != null && !column.isEmpty()) {

            String[] result = MitabParserUtils.quoteAwareSplit(column, new char[]{':', '(', ')'}, true);
            if (result != null) {

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

        }

        return object;
    }
}
