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
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.listeners.MitabParserListener;
import psidev.psi.mi.tab.listeners.MitabParsingLogger;
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

    public static BinaryInteraction<Interactor> buildBinaryInteraction(String[] line, int lineIndex, List<MitabParserListener> listenerList) {

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

        BinaryInteractionImpl interaction = new BinaryInteractionImpl(interactorA, interactorB);
        interaction.setLineNumber(lineIndex);

        //MITAB 2.5
        interactorA.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.ID_INTERACTOR_A.ordinal()));
        interactorA.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()));
        interactorA.setInteractorAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()));
        interactorA.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.TAXID_A.ordinal()));

        //MITAB 2.5
        interactorB.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.ID_INTERACTOR_B.ordinal()));
        interactorB.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()));
        interactorB.setInteractorAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()));
        interactorB.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.TAXID_B.ordinal()));

        //MITAB 2.5
        interaction.setDetectionMethods(splitControlledVocabulary(line[PsimiTabColumns.INT_DET_METHOD.ordinal()], listenerList, lineIndex, PsimiTabColumns.INT_DET_METHOD.ordinal()));
        interaction.setAuthors(splitAuthor(line[PsimiTabColumns.PUB_AUTH.ordinal()], listenerList, lineIndex, PsimiTabColumns.PUB_AUTH.ordinal()));
        interaction.setPublications(splitCrossReferences(line[PsimiTabColumns.PUB_ID.ordinal()], listenerList, lineIndex, PsimiTabColumns.PUB_AUTH.ordinal()));
        interaction.setInteractionTypes(splitControlledVocabulary(line[PsimiTabColumns.INTERACTION_TYPE.ordinal()], listenerList, lineIndex, PsimiTabColumns.INTERACTION_TYPE.ordinal()));
        interaction.setSourceDatabases(splitControlledVocabulary(line[PsimiTabColumns.SOURCE.ordinal()], listenerList, lineIndex, PsimiTabColumns.SOURCE.ordinal()));
        interaction.setInteractionAcs(splitCrossReferences(line[PsimiTabColumns.INTERACTION_ID.ordinal()], listenerList, lineIndex, PsimiTabColumns.INTERACTION_ID.ordinal()));
        interaction.setConfidenceValues(splitConfidences(line[PsimiTabColumns.CONFIDENCE.ordinal()], listenerList, lineIndex, PsimiTabColumns.CONFIDENCE.ordinal()));


        //MITAB 2.6
        interactorA.setBiologicalRoles(splitControlledVocabulary(line[PsimiTabColumns.BIOROLE_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.BIOROLE_A.ordinal()));
        interactorA.setExperimentalRoles(splitControlledVocabulary(line[PsimiTabColumns.EXPROLE_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.EXPROLE_A.ordinal()));
        interactorA.setInteractorTypes(splitControlledVocabulary(line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()));
        interactorA.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.XREFS_A.ordinal()));
        interactorA.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.ANNOTATIONS_A.ordinal()));
        interactorA.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.CHECKSUM_A.ordinal()));

        //MITAB 2.6
        interactorB.setBiologicalRoles(splitControlledVocabulary(line[PsimiTabColumns.BIOROLE_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.BIOROLE_B.ordinal()));
        interactorB.setExperimentalRoles(splitControlledVocabulary(line[PsimiTabColumns.EXPROLE_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.EXPROLE_B.ordinal()));
        interactorB.setInteractorTypes(splitControlledVocabulary(line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()));
        interactorB.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.XREFS_B.ordinal()));
        interactorB.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.ANNOTATIONS_B.ordinal()));
        interactorB.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.CHECKSUM_B.ordinal()));

        //MITAB 2.6
        interaction.setComplexExpansion(splitControlledVocabulary(line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()], listenerList, lineIndex, PsimiTabColumns.COMPLEX_EXPANSION.ordinal()));
        interaction.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_I.ordinal()], listenerList, lineIndex, PsimiTabColumns.XREFS_I.ordinal()));
        interaction.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_I.ordinal()], listenerList, lineIndex, PsimiTabColumns.ANNOTATIONS_I.ordinal()));
        interaction.setHostOrganism(splitOrganism(line[PsimiTabColumns.HOST_ORGANISM.ordinal()], listenerList, lineIndex, PsimiTabColumns.HOST_ORGANISM.ordinal()));
        interaction.setParameters(splitParameters(line[PsimiTabColumns.PARAMETERS_I.ordinal()], listenerList, lineIndex, PsimiTabColumns.PARAMETERS_I.ordinal()));
        interaction.setCreationDate(splitDates(line[PsimiTabColumns.CREATION_DATE.ordinal()], listenerList, lineIndex, PsimiTabColumns.CREATION_DATE.ordinal()));
        interaction.setUpdateDate(splitDates(line[PsimiTabColumns.UPDATE_DATE.ordinal()], listenerList, lineIndex, PsimiTabColumns.UPDATE_DATE.ordinal()));
        interaction.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_I.ordinal()], listenerList, lineIndex, PsimiTabColumns.CHECKSUM_I.ordinal()));
        interaction.setNegativeInteraction(splitNegative(line[PsimiTabColumns.NEGATIVE.ordinal()], listenerList, lineIndex, PsimiTabColumns.NEGATIVE.ordinal()));

        //MITAB 2.7
        interactorA.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.FEATURES_A.ordinal()));
        interactorA.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.STOICHIOMETRY_A.ordinal()));
        interactorA.setParticipantIdentificationMethods(splitCrossReferences(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()], listenerList, lineIndex, PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()));


        //MITAB 2.7
        interactorB.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.FEATURES_B.ordinal()));
        interactorB.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.STOICHIOMETRY_B.ordinal()));
        interactorB.setParticipantIdentificationMethods(splitCrossReferences(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()], listenerList, lineIndex, PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()));

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

    public static BinaryInteraction<Interactor> buildBinaryInteraction(String[] line) {

        return buildBinaryInteraction(line, 0, new ArrayList<MitabParserListener>(Arrays.asList(new MitabParsingLogger())));
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

    public static Organism splitOrganism(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) throws IllegalFormatException {

        Organism organism = null;

        if (column != null && !column.isEmpty()) {
            organism = new OrganismImpl();

            String[] result = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);

            Set<String> taxids = new HashSet<String>(result.length);

            for (String r : result) {

                if (r != null) {
                    String[] fields = MitabParserUtils.quoteAwareSplit(r, new char[]{':', '(', ')'}, true);
                    if (fields != null) {
                        int length = fields.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid organism (check the syntax taxid:value(name)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid organism (check the syntax taxid:value(name)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            taxids.add(fields[1]);
                            organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1]));
                        } else if (length == 3) {
                            taxids.add(fields[1]);
                            organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1], fields[2]));
                        }
                    }
                }
            }

            if (taxids.size() > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(taxids, "We have several organisms for a same interactor");
                evt.setColumnNumber(columnNumber);
                evt.setLineNumber(lineNumber);

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return organism;
    }

    public static List<CrossReference> splitCrossReferences(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {

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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        else if (length == 1) {
                            //Backward compatibility
                            if (field.equalsIgnoreCase("spoke")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1060", "spoke expansion");
                            } else if (field.equalsIgnoreCase("matrix")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1061", "matrix expansion");
                            } else if (field.equalsIgnoreCase("bipartite")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1062", "bipartite expansion");
							} else if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
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

    public static List<CrossReference> splitControlledVocabulary(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {

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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        else if (length == 1) {
                            //Backward compatibility
                            if (field.equalsIgnoreCase("spoke")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1060", "spoke expansion");
                            } else if (field.equalsIgnoreCase("matrix")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1061", "matrix expansion");
                            } else if (field.equalsIgnoreCase("bipartite")) {
                                object = new CrossReferenceImpl("psi-mi", "MI:1062", "bipartite expansion");
                            } else if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
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

            if (fields.length > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), "We have several controlled vocabulary terms where we expect only one term");
                evt.setColumnNumber(columnNumber);
                evt.setLineNumber(lineNumber);

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return objects;
    }

    public static List<CrossReference> splitPublications(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {

        List<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            List<String> dbNames = new ArrayList<String>(fields.length);

            for (String field : fields) {
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        else if (length == 1) {
                            //Backward compatibility
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid cross reference (check the syntax db:value(text)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        } else if (length == 2) {
                            dbNames.add(result[0]);
                            object = new CrossReferenceImpl(result[0], result[1]);
                        } else if (length == 3) {
                            dbNames.add(result[0]);
                            object = new CrossReferenceImpl(result[0], result[1], result[2]);
                        }

                        if (object != null) {
                            objects.add(object);
                        }
                    }
                }
            }

            if (fields.length > 1){
                for (String field : fields){

                }
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), "We have several controlled vocabulary terms where we expect only one term");
                evt.setColumnNumber(columnNumber);
                evt.setLineNumber(lineNumber);

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return objects;
    }

    public static List<Alias> splitAliases(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid alias (check the syntax db:name(alias type)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid alias (check the syntax db:name(alias type)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
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

    public static List<Confidence> splitConfidences(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid confidence (check the syntax type:value(unit)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
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

    public static List<Annotation> splitAnnotations(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid annotation (check the syntax topic:value): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {

                                //We allow annotations only with free text.
                                object = new AnnotationImpl(result[0]);
                            }

                        } else if (length == 2) {
                            object = new AnnotationImpl(result[0], result[1]);
                        } else  {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid annotation (check the syntax topic:value): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
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

    public static List<Parameter> splitParameters(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber){
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid parameter (check the syntax type:value(unit)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                         }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid parameter (check the syntax type:value(unit)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                             }
                        } else if (length == 2) {
                            try {
                                object = new ParameterImpl(result[0], result[1]);
                            } catch (IllegalParameterException e) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid parameter (check the syntax type:value(unit)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                             }
                        } else if (length == 3) {
                            try {
                                object = new ParameterImpl(result[0], result[1], result[2]);
                            } catch (IllegalParameterException e) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid parameter (check the syntax type:value(unit)): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                             }
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

    public static List<Checksum> splitChecksums(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {

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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid checksum (check the syntax method:value): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                         }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid checksum (check the syntax method:value): " + Arrays.asList(result).toString());
                                evt.setColumnNumber(columnNumber);
                                evt.setLineNumber(lineNumber);
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                            }
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

    public static List<Integer> splitStoichiometries(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid stoichiometry (check the syntax. It has to be a Integer): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                        }

                        else if (length != 1) {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid stoichiometry (check the syntax. It has to be a Integer): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                        }
                        else if (!result[0].equalsIgnoreCase("-"))
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

    public static List<Feature> splitFeatures(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid feature (check the syntax. featureType:range1;range2(free text)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
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

    public static List<Author> splitAuthor(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {

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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid author (check the syntax first author et al. (date)): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (!field.equalsIgnoreCase("-")) {
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

    public static List<Date> splitDates(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
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
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid date (check the syntax. yyyy/MM/dd): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        if (length != 1) {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid date (check the syntax. yyyy/MM/dd): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        try {
                            if (!result[0].equalsIgnoreCase("-"))
                                object = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(result[0]);
                        } catch (ParseException e) {
                            InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid date (check the syntax. yyyy/MM/dd): " + Arrays.asList(result).toString());
                            evt.setColumnNumber(columnNumber);
                            evt.setLineNumber(lineNumber);
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                             }

                        if (object != null) {
                            objects.add(object);
                        }
                    }
                }
            }
        }
        return objects;
    }

    public static Boolean splitNegative(String column, List<MitabParserListener> listenerList, int lineNumber, int columnNumber) {
        boolean object = false;

        if (column != null && !column.isEmpty()) {

            String[] result = MitabParserUtils.quoteAwareSplit(column, new char[]{':', '(', ')'}, true);
            if (result != null) {

                int length = result.length;

                // some exception handling
                if (length == 0 || length > 3) {
                    InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid negative boolean value (check the syntax. true, false or -): " + Arrays.asList(result).toString());
                    evt.setColumnNumber(columnNumber);
                    evt.setLineNumber(lineNumber);
                    for (MitabParserListener l : listenerList){
                        l.fireOnInvalidFormat(evt);
                    }                  }

                else if (length != 1) {
                    InvalidFormatEvent evt = new InvalidFormatEvent("It is not a valid negative boolean value (check the syntax. true, false or -): " + Arrays.asList(result).toString());
                    evt.setColumnNumber(columnNumber);
                    evt.setLineNumber(lineNumber);
                    for (MitabParserListener l : listenerList){
                        l.fireOnInvalidFormat(evt);
                    }                 }

                else if (!result[0].equalsIgnoreCase("-"))
                    object = Boolean.valueOf(result[0]);
            }

        }

        return object;
    }
}
