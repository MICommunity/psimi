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
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.factory.RangeFactory;
import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.events.MissingElementEvent;
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

    public static String[] quoteAwareStrictOrderSplit(String str, char[] delimiters, boolean removeUnescapedQuotes) {
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

        char [] orderedDelimiters = delimiters;
        int index = 1;

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

            } else {
                if (orderedDelimiters.length > 0 && c == orderedDelimiters[0]){

                    if (currGroup.length() > 0) {
                        if (!withinQuotes) {
                            groups.add(currGroup.toString());
                            // Note: the length of the stringbuilder can only be smaller, let's reuse the existing one.
                            currGroup.setLength(0);
                            orderedDelimiters = Arrays.copyOfRange(delimiters, index, delimiters.length);
                            index++;
                        } else {
                            currGroup.append(c);
                        }
                    } else if (withinQuotes) {
                        currGroup.append(c);
                    }

                }
                else if (c == '\\') {
                    if (withinQuotes) {
                        previousCharIsEscape = true;
                        markedAsEscape = true;
                    } else {
                        currGroup.append(c);
                    }
                } else {
                    currGroup.append(c);
                }
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
            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "It is not a valid MITAB line. A MITAB line cannot be null");
            evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));

            for (MitabParserListener l : listenerList){
                l.fireOnInvalidFormat(evt);
            }
        }
        else if (line.length == 0) {
            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "It is not a valid MITAB line. A MITAB line cannot be empty");
            evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));

            for (MitabParserListener l : listenerList){
                l.fireOnInvalidFormat(evt);
            }
        }
        else if (line.length != PsimiTabVersion.v2_5.getNumberOfColumns() && line.length != PsimiTabVersion.v2_6.getNumberOfColumns() && line.length != PsimiTabVersion.v2_7.getNumberOfColumns()){
            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "A MITAB file should have 15 (MITAB 2.5), 36 (MITAB 2.6), 42 (MITAB 2.7) but the parser could detect " + line.length + " columns.");
            evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
            for (MitabParserListener l : listenerList){
                l.fireOnInvalidFormat(evt);
            }
        }
        else {
            if (line.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                line = MitabParserUtils.extendFormat(line, PsimiTabColumns.MITAB_LENGTH.ordinal());
            }

            Interactor interactorA = new Interactor();
            Interactor interactorB = new Interactor();

            BinaryInteractionImpl interaction = new BinaryInteractionImpl(interactorA, interactorB);
            interaction.setLocator(new FileSourceLocator(lineIndex, -1));

            int charIndexIdA = 0;
            int charIndexIdB = charIndexIdA+4+(line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()] != null ? line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()].length() : 0);
            int charIndexAltIdA = charIndexIdB+4+(line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()] != null ? line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()].length() : 0);
            int charIndexAltIdB = charIndexAltIdA+4+(line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()] != null ? line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()].length() : 0);
            int charIndexAliasA = charIndexAltIdB+4+(line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()] != null ? line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()].length() : 0);
            int charIndexAliasB = charIndexAliasA+4+(line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()] != null ? line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()].length() : 0);
            int charIndexDetMethod = charIndexAliasB+4+(line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()] != null ? line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()].length() : 0);
            int charIndexFirstAuth = charIndexDetMethod+4+(line[PsimiTabColumns.INT_DET_METHOD.ordinal()] != null ? line[PsimiTabColumns.INT_DET_METHOD.ordinal()].length() : 0);
            int charIndexPublication = charIndexFirstAuth+4+(line[PsimiTabColumns.PUB_AUTH.ordinal()] != null ? line[PsimiTabColumns.PUB_AUTH.ordinal()].length() : 0);
            int charIndexTaxIdA = charIndexPublication+4+(line[PsimiTabColumns.PUB_ID.ordinal()] != null ? line[PsimiTabColumns.PUB_ID.ordinal()].length() : 0);
            int charIndexTaxIdB = charIndexTaxIdA+4+(line[PsimiTabColumns.TAXID_A.ordinal()] != null ? line[PsimiTabColumns.TAXID_A.ordinal()].length() : 0);
            int charIndexInteractionType = charIndexTaxIdB+4+(line[PsimiTabColumns.TAXID_B.ordinal()] != null ? line[PsimiTabColumns.TAXID_B.ordinal()].length() : 0);
            int charIndexSource = charIndexInteractionType+4+(line[PsimiTabColumns.INTERACTION_TYPE.ordinal()] != null ? line[PsimiTabColumns.INTERACTION_TYPE.ordinal()].length() : 0);
            int charIndexInteractionId = charIndexSource+4+(line[PsimiTabColumns.SOURCE.ordinal()] != null ? line[PsimiTabColumns.SOURCE.ordinal()].length() : 0);
            int charIndexConfidence = charIndexInteractionId+4+(line[PsimiTabColumns.INTERACTION_ID.ordinal()] != null ? line[PsimiTabColumns.INTERACTION_ID.ordinal()].length() : 0);
            int charIndexComplex = charIndexConfidence+4+(line[PsimiTabColumns.CONFIDENCE.ordinal()] != null ? line[PsimiTabColumns.CONFIDENCE.ordinal()].length() : 0);
            int charIndexBioRoleA = charIndexComplex+4+(line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()] != null ? line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()].length() : 0);
            int charIndexBioRoleB = charIndexBioRoleA+4+(line[PsimiTabColumns.BIOROLE_A.ordinal()] != null ? line[PsimiTabColumns.BIOROLE_A.ordinal()].length() : 0);
            int charIndexExpRoleA = charIndexBioRoleB+4+(line[PsimiTabColumns.BIOROLE_B.ordinal()] != null ? line[PsimiTabColumns.BIOROLE_B.ordinal()].length() : 0);
            int charIndexExpRoleB = charIndexExpRoleA+4+(line[PsimiTabColumns.EXPROLE_A.ordinal()] != null ? line[PsimiTabColumns.EXPROLE_A.ordinal()].length() : 0);
            int charIndexTypeA = charIndexExpRoleB+4+(line[PsimiTabColumns.EXPROLE_B.ordinal()] != null ? line[PsimiTabColumns.EXPROLE_B.ordinal()].length() : 0);
            int charIndexTypeB = charIndexTypeA+4+(line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()] != null ? line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()].length() : 0);
            int charIndexXrefA = charIndexTypeB+4+(line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()] != null ? line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()].length() : 0);
            int charIndexXrefB = charIndexXrefA+4+(line[PsimiTabColumns.XREFS_A.ordinal()] != null ? line[PsimiTabColumns.XREFS_A.ordinal()].length() : 0);
            int charIndexXref = charIndexXrefB+4+(line[PsimiTabColumns.XREFS_B.ordinal()] != null ? line[PsimiTabColumns.XREFS_B.ordinal()].length() : 0);
            int charIndexAnnotA = charIndexXref+4+(line[PsimiTabColumns.XREFS_I.ordinal()] != null ? line[PsimiTabColumns.XREFS_I.ordinal()].length() : 0);
            int charIndexAnnotB = charIndexAnnotA+4+(line[PsimiTabColumns.ANNOTATIONS_A.ordinal()] != null ? line[PsimiTabColumns.ANNOTATIONS_A.ordinal()].length() : 0);
            int charIndexAnnot = charIndexAnnotB+4+(line[PsimiTabColumns.ANNOTATIONS_B.ordinal()] != null ? line[PsimiTabColumns.ANNOTATIONS_B.ordinal()].length() : 0);
            int charIndexHost = charIndexAnnot+4+(line[PsimiTabColumns.ANNOTATIONS_I.ordinal()] != null ? line[PsimiTabColumns.ANNOTATIONS_I.ordinal()].length() : 0);
            int charIndexParameters = charIndexHost+4+(line[PsimiTabColumns.HOST_ORGANISM.ordinal()] != null ? line[PsimiTabColumns.HOST_ORGANISM.ordinal()].length() : 0);
            int charIndexCreationDate = charIndexParameters+4+(line[PsimiTabColumns.PARAMETERS_I.ordinal()] != null ? line[PsimiTabColumns.PARAMETERS_I.ordinal()].length() : 0);
            int charIndexUpdateDate = charIndexCreationDate+4+(line[PsimiTabColumns.CREATION_DATE.ordinal()] != null ? line[PsimiTabColumns.CREATION_DATE.ordinal()].length() : 0);
            int charIndexCheckA = charIndexUpdateDate+4+(line[PsimiTabColumns.UPDATE_DATE.ordinal()] != null ? line[PsimiTabColumns.UPDATE_DATE.ordinal()].length() : 0);
            int charIndexCheckB = charIndexCheckA+4+(line[PsimiTabColumns.CHECKSUM_A.ordinal()] != null ? line[PsimiTabColumns.CHECKSUM_A.ordinal()].length() : 0);
            int charIndexCheckI = charIndexCheckB+4+(line[PsimiTabColumns.CHECKSUM_B.ordinal()] != null ? line[PsimiTabColumns.CHECKSUM_B.ordinal()].length() : 0);
            int charIndexNegative = charIndexCheckI+4+(line[PsimiTabColumns.CHECKSUM_I.ordinal()] != null ? line[PsimiTabColumns.CHECKSUM_I.ordinal()].length() : 0);
            int charIndexFeatureA = charIndexNegative+4+(line[PsimiTabColumns.NEGATIVE.ordinal()] != null ? line[PsimiTabColumns.NEGATIVE.ordinal()].length() : 0);
            int charIndexFeatureB = charIndexFeatureA+4+(line[PsimiTabColumns.FEATURES_A.ordinal()] != null ? line[PsimiTabColumns.FEATURES_A.ordinal()].length() : 0);
            int charIndexStcA = charIndexFeatureB+4+(line[PsimiTabColumns.FEATURES_B.ordinal()] != null ? line[PsimiTabColumns.FEATURES_B.ordinal()].length() : 0);
            int charIndexStcB = charIndexStcA+4+(line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()] != null ? line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()].length() : 0);
            int charIndexPMethodA = charIndexStcB+4+(line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()] != null ? line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()].length() : 0);
            int charIndexPMethodB = charIndexPMethodA+4+(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()] != null ? line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()].length() : 0);

            //MITAB 2.5
            interactorA.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()], listenerList, lineIndex, charIndexIdA, PsimiTabColumns.ID_INTERACTOR_A.ordinal(), false));
            interactorA.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()], listenerList, lineIndex, charIndexAltIdA, PsimiTabColumns.ALTID_INTERACTOR_A.ordinal(), false));
            interactorA.setInteractorAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()], listenerList, lineIndex, charIndexAliasA, PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()));
            interactorA.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_A.ordinal()], listenerList, lineIndex, charIndexTaxIdA, PsimiTabColumns.TAXID_A.ordinal(), FileParsingErrorType.clustered_content));

            //MITAB 2.5
            interactorB.setIdentifiers(splitCrossReferences(line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()], listenerList, lineIndex, charIndexIdB, PsimiTabColumns.ID_INTERACTOR_B.ordinal(), false));
            interactorB.setAlternativeIdentifiers(splitCrossReferences(line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()], listenerList, lineIndex, charIndexAltIdB, PsimiTabColumns.ALTID_INTERACTOR_B.ordinal(), false));
            interactorB.setInteractorAliases(splitAliases(line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()], listenerList, lineIndex, charIndexAliasB, PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()));
            interactorB.setOrganism(splitOrganism(line[PsimiTabColumns.TAXID_B.ordinal()], listenerList, lineIndex, charIndexTaxIdB, PsimiTabColumns.TAXID_B.ordinal(), FileParsingErrorType.clustered_content));

            //MITAB 2.5
            List<CrossReference> detMethods = splitControlledVocabulary(line[PsimiTabColumns.INT_DET_METHOD.ordinal()], listenerList, lineIndex, charIndexDetMethod, PsimiTabColumns.INT_DET_METHOD.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_interaction_detection_method, "error");
            interaction.setDetectionMethods(detMethods);
            interaction.setAuthors(splitAuthor(line[PsimiTabColumns.PUB_AUTH.ordinal()], listenerList, lineIndex, charIndexFirstAuth, PsimiTabColumns.PUB_AUTH.ordinal(), FileParsingErrorType.clustered_content));
            interaction.setPublications(splitPublications(line[PsimiTabColumns.PUB_ID.ordinal()], listenerList, lineIndex, charIndexPublication, PsimiTabColumns.PUB_AUTH.ordinal(), FileParsingErrorType.clustered_content));
            interaction.setInteractionTypes(splitControlledVocabulary(line[PsimiTabColumns.INTERACTION_TYPE.ordinal()], listenerList, lineIndex, charIndexInteractionType,PsimiTabColumns.INTERACTION_TYPE.ordinal(), FileParsingErrorType.multiple_interaction_types, FileParsingErrorType.missing_cv, "info"));
            interaction.setSourceDatabases(splitControlledVocabulary(line[PsimiTabColumns.SOURCE.ordinal()], listenerList, lineIndex, charIndexSource, PsimiTabColumns.SOURCE.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_cv, "error"));
            interaction.setInteractionAcs(splitCrossReferences(line[PsimiTabColumns.INTERACTION_ID.ordinal()], listenerList, lineIndex, charIndexInteractionId, PsimiTabColumns.INTERACTION_ID.ordinal(), false));
            interaction.setConfidenceValues(splitConfidences(line[PsimiTabColumns.CONFIDENCE.ordinal()], listenerList, lineIndex, charIndexConfidence, PsimiTabColumns.CONFIDENCE.ordinal()));


            //MITAB 2.6
            interactorA.setBiologicalRoles(splitControlledVocabulary(line[PsimiTabColumns.BIOROLE_A.ordinal()], listenerList, lineIndex, charIndexBioRoleA, PsimiTabColumns.BIOROLE_A.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_biological_role, "info"));
            interactorA.setExperimentalRoles(splitControlledVocabulary(line[PsimiTabColumns.EXPROLE_A.ordinal()], listenerList, lineIndex, charIndexExpRoleA, PsimiTabColumns.EXPROLE_A.ordinal(), FileParsingErrorType.multiple_experimental_roles, FileParsingErrorType.missing_experimental_role, "info"));
            List<CrossReference> interactorTypesA = splitControlledVocabulary(line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()], listenerList, lineIndex, charIndexTypeA, PsimiTabColumns.INTERACTOR_TYPE_A.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_interactor_type, "error");
            interactorA.setInteractorTypes(interactorTypesA);
            interactorA.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_A.ordinal()], listenerList, lineIndex, charIndexXrefA, PsimiTabColumns.XREFS_A.ordinal(), true));
            interactorA.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_A.ordinal()], listenerList, lineIndex, charIndexAnnotA, PsimiTabColumns.ANNOTATIONS_A.ordinal()));
            interactorA.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_A.ordinal()], listenerList, lineIndex, charIndexCheckA, PsimiTabColumns.CHECKSUM_A.ordinal()));

            //MITAB 2.6
            interactorB.setBiologicalRoles(splitControlledVocabulary(line[PsimiTabColumns.BIOROLE_B.ordinal()], listenerList, lineIndex, charIndexBioRoleB, PsimiTabColumns.BIOROLE_B.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_biological_role, "info"));
            interactorB.setExperimentalRoles(splitControlledVocabulary(line[PsimiTabColumns.EXPROLE_B.ordinal()], listenerList, lineIndex, charIndexExpRoleB, PsimiTabColumns.EXPROLE_B.ordinal(), FileParsingErrorType.multiple_experimental_roles, FileParsingErrorType.missing_experimental_role, "info"));
            List<CrossReference> interactorTypesB = splitControlledVocabulary(line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()], listenerList, lineIndex, charIndexTypeB, PsimiTabColumns.INTERACTOR_TYPE_B.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_interactor_type, "error");
            interactorB.setInteractorTypes(interactorTypesB);
            interactorB.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_B.ordinal()], listenerList, lineIndex, charIndexXrefB, PsimiTabColumns.XREFS_B.ordinal(), true));
            interactorB.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_B.ordinal()], listenerList, lineIndex, charIndexAnnotB, PsimiTabColumns.ANNOTATIONS_B.ordinal()));
            interactorB.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_B.ordinal()], listenerList, lineIndex, charIndexCheckB, PsimiTabColumns.CHECKSUM_B.ordinal()));

            //MITAB 2.6
            interaction.setComplexExpansion(splitControlledVocabulary(line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()], listenerList, lineIndex, charIndexComplex, PsimiTabColumns.COMPLEX_EXPANSION.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_cv, ""));
            interaction.setXrefs(splitCrossReferences(line[PsimiTabColumns.XREFS_I.ordinal()], listenerList, lineIndex, charIndexXref, PsimiTabColumns.XREFS_I.ordinal(), true));
            interaction.setAnnotations(splitAnnotations(line[PsimiTabColumns.ANNOTATIONS_I.ordinal()], listenerList, lineIndex, charIndexAnnot, PsimiTabColumns.ANNOTATIONS_I.ordinal()));
            interaction.setHostOrganism(splitOrganism(line[PsimiTabColumns.HOST_ORGANISM.ordinal()], listenerList, lineIndex, charIndexHost, PsimiTabColumns.HOST_ORGANISM.ordinal(), FileParsingErrorType.multiple_host_organisms));
            interaction.setParameters(splitParameters(line[PsimiTabColumns.PARAMETERS_I.ordinal()], listenerList, lineIndex, charIndexParameters, PsimiTabColumns.PARAMETERS_I.ordinal()));
            interaction.setCreationDate(splitDates(line[PsimiTabColumns.CREATION_DATE.ordinal()], listenerList, lineIndex, charIndexCreationDate, PsimiTabColumns.CREATION_DATE.ordinal(), FileParsingErrorType.clustered_content));
            interaction.setUpdateDate(splitDates(line[PsimiTabColumns.UPDATE_DATE.ordinal()], listenerList, lineIndex, charIndexUpdateDate, PsimiTabColumns.UPDATE_DATE.ordinal(), FileParsingErrorType.clustered_content));
            interaction.setChecksums(splitChecksums(line[PsimiTabColumns.CHECKSUM_I.ordinal()], listenerList, lineIndex, charIndexCheckI, PsimiTabColumns.CHECKSUM_I.ordinal()));
            interaction.setNegativeInteraction(splitNegative(line[PsimiTabColumns.NEGATIVE.ordinal()], listenerList, lineIndex, charIndexNegative, PsimiTabColumns.NEGATIVE.ordinal()));

            //MITAB 2.7
            interactorA.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_A.ordinal()], listenerList, lineIndex, charIndexFeatureA, PsimiTabColumns.FEATURES_A.ordinal()));
            interactorA.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()], listenerList, lineIndex, charIndexStcA, PsimiTabColumns.STOICHIOMETRY_A.ordinal(), FileParsingErrorType.clustered_content));
            interactorA.setParticipantIdentificationMethods(splitControlledVocabulary(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()], listenerList, lineIndex, charIndexPMethodA, PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_cv, "info"));


            //MITAB 2.7
            interactorB.setFeatures(splitFeatures(line[PsimiTabColumns.FEATURES_B.ordinal()], listenerList, lineIndex, charIndexFeatureA, PsimiTabColumns.FEATURES_B.ordinal()));
            interactorB.setStoichiometry(splitStoichiometries(line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()], listenerList, lineIndex, charIndexFeatureB, PsimiTabColumns.STOICHIOMETRY_B.ordinal(), FileParsingErrorType.clustered_content));
            interactorB.setParticipantIdentificationMethods(splitControlledVocabulary(line[PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()], listenerList, lineIndex, charIndexPMethodB, PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal(), FileParsingErrorType.clustered_content, FileParsingErrorType.missing_cv, "info"));

            // we check consistency in interaction
            if (detMethods.isEmpty() && interaction.getAuthors().isEmpty() && interaction.getPublications().isEmpty() && interaction.getHostOrganism() == null &&
                    interaction.getCreationDate() == null && interaction.getSourceDatabases().isEmpty()){
                MissingElementEvent evt = new MissingElementEvent("warn", "The interaction does not have a valid experiment.", FileParsingErrorType.interaction_evidence_without_experiment);
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, 1));
                MissingElementEvent evt2 = new MissingElementEvent("warn", "The interaction does not have a valid publication.", FileParsingErrorType.missing_publication);
                evt2.setSourceLocator(new MitabSourceLocator(lineIndex, -1, 1));

                for (MitabParserListener l : listenerList){
                    l.fireOnMissingElementEvent(evt);
                    l.fireOnMissingElementEvent(evt2);
                }
            }
            else if (interaction.getAuthors().isEmpty() && interaction.getPublications().isEmpty() && interaction.getCreationDate() == null && interaction.getSourceDatabases().isEmpty()){
                MissingElementEvent evt = new MissingElementEvent("warn", "The interaction does not have a valid publication.", FileParsingErrorType.missing_publication);
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, 1));

                for (MitabParserListener l : listenerList){
                    l.fireOnMissingElementEvent(evt);
                }
            }

            //We check some consistency in the interactors
            // we check consistency in participant
            if (!interactorA.isEmpty() && interactorTypesA.isEmpty() && interactorA.getAliases().isEmpty() && interactorA.getIdentifiers().isEmpty() &&
                    interactorA.getAlternativeIdentifiers().isEmpty() && interactorA.getInteractorXrefs().isEmpty() &&
                    interactorA.getChecksums().isEmpty() && interactorA.getAnnotations().isEmpty() && interactorA.getOrganism() == null){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The participant A does not have a valid interactor.");
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, charIndexIdA, 1));

                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.participant_without_interactor, "The participant A does not have a valid interactor.");
                evt2.setSourceLocator(new MitabSourceLocator(lineIndex, charIndexIdA, 1));

                for (MitabParserListener l : listenerList){
                    l.fireOnInvalidFormat(evt);
                    l.fireOnInvalidFormat(evt2);
                }
            }

            if (!interactorB.isEmpty() && interactorTypesB.isEmpty() && interactorB.getAliases().isEmpty() && interactorB.getIdentifiers().isEmpty() &&
                    interactorB.getAlternativeIdentifiers().isEmpty() && interactorB.getInteractorXrefs().isEmpty() &&
                    interactorB.getChecksums().isEmpty() && interactorB.getAnnotations().isEmpty() && interactorB.getOrganism() == null){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The participant B does not have a valid interactor.");
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, charIndexIdB, 1));

                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.participant_without_interactor, "The participant B does not have a valid interactor.");
                evt2.setSourceLocator(new MitabSourceLocator(lineIndex, charIndexIdB, 1));

                for (MitabParserListener l : listenerList){
                    l.fireOnInvalidFormat(evt);
                    l.fireOnInvalidFormat(evt2);
                }
            }

            if(interactorA.isEmpty() && interactorB.isEmpty()){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Both interactors are null or empety. We can have a interaction without interactors");
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                for (MitabParserListener l : listenerList){
                    l.fireOnInvalidFormat(evt);
                }

                interaction.setInteractorA(null);
                interaction.setInteractorB(null);
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

        return null;
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

    public static Organism splitOrganism(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber, FileParsingErrorType errorType) throws IllegalFormatException {

        Organism organism = null;

        if (column != null && !column.isEmpty()) {
            organism = new OrganismImpl();
            organism.setLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

            String[] result = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);

            Set<String> taxids = new HashSet<String>(result.length);

            int newIndex = charIndex;

            for (String r : result) {

                if (r != null) {
                    String[] fields = MitabParserUtils.quoteAwareStrictOrderSplit(r, new char[]{':', '(', ')'}, true);
                    if (fields != null) {
                        int length = fields.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                    "The expected syntax is taxid:accession(organism name). "+
                                    "Check that you don't have one of these special characters ':', '(', ')' in database name, accession or organism name. " +
                                    "If yes, the special characters must be escaped with double quote.");                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The expected syntax is taxid:accession(organism name). ");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            String database = null;
                            String id = null;
                            if (fields[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database cannot be empty and should be set to 'taxid'. ");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database cannot be empty and should be set to 'taxid'. ");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                database = fields[0];
                            }
                            if (fields[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database accession cannot be empty and should be set to a valid ncbi taxid or -1(in vitro), -2(chemical synthesis), -3(unknown), -4(in vivo). ");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database accession cannot be empty and should be set to a valid ncbi taxid or -1(in vitro), -2(chemical synthesis), -3(unknown), -4(in vivo). ");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = fields[1];
                            }

                            if (database != null && id != null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1]));
                            }
                            else if (database == null && id != null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl("unspecified", fields[1]));
                            }
                            else if (database != null && id == null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl(fields[0], "-3"));
                            }
                            else {
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl("unspecified", "-3"));
                            }

                        } else if (length == 3) {
                            String database = null;
                            String id = null;
                            if (fields[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database cannot be empty and should be set to 'taxid'. ");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database cannot be empty and should be set to 'taxid'. ");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                database = fields[0];
                            }
                            if (fields[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database accession cannot be empty and should be set to a valid ncbi taxid or -1(in vitro), -2(chemical synthesis), -3(unknown), -4(in vivo). ");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The organism cross reference " + r + "is not a valid organism cross reference. " +
                                        "The database accession cannot be empty and should be set to a valid ncbi taxid or -1(in vitro), -2(chemical synthesis), -3(unknown), -4(in vivo). ");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = fields[1];
                            }

                            if (database != null && id != null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl(fields[0], fields[1], fields[2]));
                            }
                            else if (database == null && id != null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl("unspecified", fields[1], fields[2]));
                            }
                            else if (database != null && id == null){
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl(fields[0], "-3", fields[2]));
                            }
                            else {
                                taxids.add(fields[1]);
                                organism.addIdentifier(new CrossReferenceImpl("unspecified", "-3", fields[2]));
                            }
                        }
                    }
                    newIndex+=r.length();
                }
            }

            if (taxids.size() > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(taxids, errorType, "We have "+taxids.size()+" organisms for a same interactor");
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return organism;
    }

    public static List<CrossReference> splitCrossReferences(String column, List<MitabParserListener> listenerList, int lineNumber, int startCharNumber, int columnNumber, boolean allowsText) {

        List<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null && !column.isEmpty()) {

            int newIndex = startCharNumber;
            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                    "The expected syntax is database:accession"+(allowsText?"(qualifier)":" . " +
                                    "Check that you don't have one of these special characters ':'"+(allowsText?", '(', ')'":"")+" in database name, accession or text. " +
                                    "If yes, the special characters must be escaped with double quote."));
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        else if (length == 1) {
                            //Backward compatibility
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The expected syntax is database:accession"+(allowsText?"(qualifier)":" ."));
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);

                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database accession cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database accession cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                object = new CrossReferenceImpl(result[0], result[1]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl("unknown", result[1]);
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown");
                            }
                        } else if (length == 3) {
                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);

                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database accession cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The database accession cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                object = new CrossReferenceImpl(result[0], result[1], result[2]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1], result[2]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl("unknown", result[1], result[2]);
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown", result[2]);
                            }

                            if (!allowsText) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "It is not a valid cross reference (check the syntax db:value. A text element is not allowed in this column): " + Arrays.asList(result).toString());
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<CrossReference> splitControlledVocabulary(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber, FileParsingErrorType clusteredErrorType, FileParsingErrorType missingCvErrorType, String levelOfImportanceIfMissing) {

        List<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);

            int newIndex = charIndex;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                    "The expected syntax is database:term_id(term name) . " +
                                    "Check that you don't have one of these special characters ':', '(', ') in database name, term id or term name. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

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
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The expected syntax is database:term_id(term name) .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The controlled vocabulary term " + field + "is not valid. " +
                                    "The term name should be specified in parenthesis .");
                            evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt3);
                            }

                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The ontology/database name cannot be empty .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The ontology/database name cannot be empty .");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The term id cannot be empty .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The term id cannot be empty .");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                object = new CrossReferenceImpl(result[0], result[1]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl(result[0], "unknown");
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown");
                            }

                        } else if (length == 3) {
                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The ontology/database name cannot be empty .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The ontology/database name cannot be empty .");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The term id cannot be empty .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The cross reference " + field + "is not a valid cross reference. " +
                                        "The term id cannot be empty .");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                object = new CrossReferenceImpl(result[0], result[1], result[2]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1], result[2]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl(result[0], "unknown", result[2]);
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown", result[2]);
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }

            if (fields.length > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), clusteredErrorType, "We have several "+fields.length+" controlled vocabulary terms where we expect only one term");
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
            if (objects.isEmpty()){
                MissingElementEvent evt = new MissingElementEvent(levelOfImportanceIfMissing, "No cv term has been specified for this column.", missingCvErrorType);
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnMissingElementEvent(evt);
                }
            }
        }

        return objects;
    }

    public static List<CrossReference> splitPublications(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber, FileParsingErrorType errorType) {

        List<CrossReference> objects = new ArrayList<CrossReference>();
        CrossReference object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            List<String> dbNames = new ArrayList<String>(fields.length);

            int newIndex = charIndex;

            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                    "The expected syntax is database:publication_id . " +
                                    "Check that you don't have one of these special characters ':', '(', ') in database name or publication identifier. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                //Backward compatibility
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The expected syntax is database:publication_id .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be null or empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be null or empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The publication identifier cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The publication identifier cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                dbNames.add(result[0]);
                                object = new CrossReferenceImpl(result[0], result[1]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl(result[0], "unknown");
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown");
                            }
                        }
                        else if (result.length == 3) {
                            InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + " is unusual. A text/qualifier is not expected in this column");
                            evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt3);
                            }

                            String database = null;
                            String id = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be null or empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The database cannot be null or empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                            else {
                                database = result[0];
                            }
                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database_accession, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The publication identifier cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The publication cross reference " + field + "is not a valid cross reference. " +
                                        "The publication identifier cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                id = result[1];
                            }

                            if (database != null && id != null){
                                dbNames.add(result[0]);
                                object = new CrossReferenceImpl(result[0], result[1], result[2]);
                            }
                            else if (database == null && id != null){
                                object = new CrossReferenceImpl("unknown", result[1], result[2]);
                            }
                            else if (database != null && id == null){
                                object = new CrossReferenceImpl(result[0], "unknown", result[2]);
                            }
                            else {
                                object = new CrossReferenceImpl("unknown", "unknown", result[2]);
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }
                    newIndex+=field.length();
                }
            }

            if (fields.length > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), errorType, "We have "+fields.length+" controlled vocabulary terms where we expect only one term");
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return objects;
    }

    public static List<Alias> splitAliases(String column, List<MitabParserListener> listenerList, int lineNumber, int charNumber, int columnNumber) {
        List<Alias> objects = new ArrayList<Alias>();
        Alias object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charNumber;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The alias " + field + "is not a valid alias. " +
                                    "The expected syntax is database:alias_name(alias_type) . " +
                                    "Check that you don't have one of these special characters ':', '(', ') in database name, alias name or alias type. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The alias " + field + "is not a valid alias. " +
                                        "The expected syntax is database:alias_name(alias_type) .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 2) {
                            String db = null;
                            String name = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The alias " + field + "is not a valid alias. " +
                                        "The database cannot be null or empty. If the database is not known, set the database to 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax , "The alias " + field + "is not a valid alias. " +
                                        "The database cannot be null or empty. If the database is not known, set the database to 'unknown'.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                db = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_alias_name , "The alias " + field + "is not a valid alias. " +
                                        "The alias name cannot be null or empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax , "The alias " + field + "is not a valid alias. " +
                                        "The alias name cannot be null or empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                            else{
                                name = result[1];
                            }

                            if (db != null && name != null){
                                object = new AliasImpl(db, name);
                            }
                            else if (db == null && name != null){
                                object = new AliasImpl("unknown", result[1]);
                            }
                            else if (db != null && name == null){
                                object = new AliasImpl(result[0], "unspecified");
                            }
                            else {
                                object = new AliasImpl("unknown", "unspecified");
                            }
                        } else if (length == 3) {
                            String db = null;
                            String name = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_database, "The alias " + field + "is not a valid alias. " +
                                        "The database cannot be null or empty. If the database is not known, set the database to 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax , "The alias " + field + "is not a valid alias. " +
                                        "The database cannot be null or empty. If the database is not known, set the database to 'unknown'.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                db = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.missing_alias_name , "The alias " + field + "is not a valid alias. " +
                                        "The alias name cannot be null or empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax , "The alias " + field + "is not a valid alias. " +
                                        "The alias name cannot be null or empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                name = result[1];
                            }

                            if (db != null && name != null){
                                object = new AliasImpl(db, name);
                            }
                            else if (db == null && name != null){
                                object = new AliasImpl("unknown", result[1], result[2]);
                            }
                            else if (db != null && name == null){
                                object = new AliasImpl(result[0], "unspecified", result[2]);
                            }
                            else {
                                object = new AliasImpl("unknown", "unspecified", result[2]);
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<Confidence> splitConfidences(String column, List<MitabParserListener> listenerList, int lineNumber, int charNumber, int columnNumber) {
        List<Confidence> objects = new ArrayList<Confidence>();
        Confidence object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charNumber;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                    "The expected syntax is confidence_type:value(unit) . " +
                                    "Check that you don't have one of these special characters ':', '(', ') in confidence type, value or unit. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                        "The expected syntax is confidence_type:value(unit) .");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt4 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_type, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence type cannot be empty. If it is not known, it should be set to 'unknown'");
                                evt4.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt4);
                                }

                                if (result[0].length() == 0) {
                                    InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                            "The confidence value cannot be empty.");
                                    evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_value, "The confidence " + field + "is not a valid confidence. " +
                                            "The confidence value cannot be empty.");
                                    evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    for (MitabParserListener l : listenerList){
                                        l.fireOnInvalidFormat(evt2);
                                        l.fireOnInvalidFormat(evt3);
                                    }
                                    object = new ConfidenceImpl("unknown", "unknown");
                                }
                                else {
                                    object = new ConfidenceImpl("unknown", result[0]);
                                }
                            }
                        } else if (length == 2) {
                            String type = null;
                            String value = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence type cannot be empty. If the confidence type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt4 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_type, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence type cannot be empty. If it is not known, it should be set to 'unknown'");
                                evt4.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt4);
                                }
                            }
                            else {
                                type = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence value cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_value, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence value cannot be empty.");
                                evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt3);
                                }
                            }
                            else{
                                value = result[1];
                            }

                            if (type != null && value != null){
                                object = new ConfidenceImpl(type, value);
                            }
                            else if (type == null && value != null){
                                object = new ConfidenceImpl("unknown", value);
                            }
                            else if (type != null && value == null){
                                object = new ConfidenceImpl(type, "unspecified");
                            }
                            else {
                                object = new ConfidenceImpl("unknown", "unspecified");
                            }
                        } else if (length == 3) {
                            String type = null;
                            String value = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence type cannot be empty. If the confidence type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt4 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_type, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence type cannot be empty. If it is not known, it should be set to 'unknown'");
                                evt4.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt4);
                                }
                            }
                            else {
                                type = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence value cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.missing_confidence_value, "The confidence " + field + "is not a valid confidence. " +
                                        "The confidence value cannot be empty.");
                                evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt3);
                                }
                            }
                            else{
                                value = result[1];
                            }

                            if (type != null && value != null){
                                object = new ConfidenceImpl(type, value, result[2]);
                            }
                            else if (type == null && value != null){
                                object = new ConfidenceImpl("unknown", value, result[2]);
                            }
                            else if (type != null && value == null){
                                object = new ConfidenceImpl(type, "unspecified", result[2]);
                            }
                            else {
                                object = new ConfidenceImpl("unknown", "unspecified", result[2]);
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<Annotation> splitAnnotations(String column, List<MitabParserListener> listenerList, int lineNumber, int charNumber, int columnNumber) {
        List<Annotation> objects = new ArrayList<Annotation>();
        Annotation object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charNumber;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 2) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The annotation " + field + "is not a valid annotation. " +
                                    "The expected syntax is topic:description. " +
                                    "Check that you don't have one of these special characters ':' in topic or description. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {

                                if (result[0].length() == 0) {
                                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The annotation " + field + "is not a valid annotation. " +
                                            "The topic cannot be null or empty");
                                    evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_annotation_topic, "The annotation " + field + "is not a valid annotation. " +
                                            "The topic cannot be null or empty");
                                    evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    for (MitabParserListener l : listenerList){
                                        l.fireOnInvalidFormat(evt);
                                        l.fireOnInvalidFormat(evt2);
                                    }
                                }
                                else {
                                    //We allow annotations only with free text.
                                    object = new AnnotationImpl(result[0]);
                                }
                            }

                        } else if (length == 2) {
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The annotation " + field + "is not a valid annotation. " +
                                        "The topic cannot be null or empty");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_annotation_topic, "The annotation " + field + "is not a valid annotation. " +
                                        "The topic cannot be null or empty");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                                object = new AnnotationImpl("unspecified", result[1]);
                            }
                            else {
                                //We allow annotations only with free text.
                                object = new AnnotationImpl(result[0], result[1]);
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }

        return objects;
    }

    public static List<Parameter> splitParameters(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber){
        List<Parameter> objects = new ArrayList<Parameter>();
        Parameter object = null;

        if (column != null && !column.isEmpty()) {
            int newIndex = charIndex;

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                    "The expected syntax is parameter_type:factor x base exponent ~uncertainty (unit). " +
                                    "Check that you don't have one of these special characters ':' in parameter type, value or unit. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                         }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The expected syntax is parameter_type:factor x base exponent ~uncertainty (unit).");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                             }
                        } else if (length == 2) {
                            String type = null;
                            String value = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter type cannot be empty. If the parameter type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_parameter_type, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter type cannot be empty. If the parameter type is not known, use 'unknown'.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                type = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter value cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_parameter_factor, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter value cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                value = result[1];
                            }

                            try {
                                if (type != null && value != null){
                                    object = new ParameterImpl(type, value);
                                }
                                else if (type == null && value != null){
                                    object = new ParameterImpl("unknown", value);
                                }
                                else if (type != null && value == null){
                                    object = new ParameterImpl(type, "0");
                                }
                                else {
                                    object = new ParameterImpl("unknown", "0");
                                }
                            } catch (IllegalParameterException e) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        e.getMessage());
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        } else if (length == 3) {
                            String type = null;
                            String value = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter type cannot be empty. If the parameter type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_parameter_type, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter type cannot be empty. If the parameter type is not known, use 'unknown'.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else {
                                type = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter value cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.missing_parameter_factor, "The parameter " + field + "is not a valid interaction parameter. " +
                                        "The parameter value cannot be empty.");
                                evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                    l.fireOnInvalidFormat(evt2);
                                }
                            }
                            else{
                                value = result[1];
                            }

                            try {
                                if (type != null && value != null){
                                    object = new ParameterImpl(type, value, result[2]);
                                }
                                else if (type == null && value != null){
                                    object = new ParameterImpl("unknown", value, result[2]);
                                }
                                else if (type != null && value == null){
                                    object = new ParameterImpl(type, "0", result[2]);
                                }
                                else {
                                    object = new ParameterImpl("unknown", "0", result[2]);
                                }
                            } catch (IllegalParameterException e) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The parameter " + field + "is not a valid interaction parameter. " +
                                        e.getMessage());
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<Checksum> splitChecksums(String column, List<MitabParserListener> listenerList, int lineNumber, int charNumber, int columnNumber) {

        List<Checksum> objects = new ArrayList<Checksum>();
        Checksum object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charNumber;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 2) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The checksum " + field + "is not a valid checksum. " +
                                    "The expected syntax is method:checksum. " +
                                    "Check that you don't have one of these special characters ':' in method or checksum. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }                         }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The checksum " + field + "is not a valid checksum. " +
                                        "The expected syntax is method:checksum.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }                            }
                        } else if (length == 2) {
                            String type = null;
                            String value = null;
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The checksum " + field + "is not a valid checksum. " +
                                        "The method cannot be empty. If the method is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                            else {
                                type = result[0];
                            }

                            if (result[1].length() == 0){
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The checksum " + field + "is not a valid checksum. " +
                                        "The checksum cannot be empty.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                            }
                            else{
                                value = result[1];
                            }

                            if (type != null && value != null){
                                object = new ChecksumImpl(type, value);
                            }
                            else if (type == null && value != null){
                                object = new ChecksumImpl("unknown", value);
                            }
                            else if (type != null && value == null){
                                object = new ChecksumImpl(type, "unspecified");
                            }
                            else {
                                object = new ChecksumImpl("unknown", "unspecified");
                            }
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<Integer> splitStoichiometries(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber, FileParsingErrorType errorType) {
        List<Integer> objects = new ArrayList<Integer>();
        Integer object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charIndex;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    if (!field.equals("-")){
                        try{
                            object = Integer.parseInt(field);
                        } catch (NumberFormatException e) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The stoichiometry " + field + "is not a valid stoichiometry. " +
                                    "The expected syntax is number.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }
                    }

                    if (object != null) {
                        objects.add(object);
                    }

                    newIndex+=field.length();
                }

                if (fields.length > 1){
                    ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), errorType, "We have "+fields.length+" stoichiometry values where we expect only one stoichiometry");
                    evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                    for (MitabParserListener l : listenerList){
                        l.fireOnClusteredColumnEvent(evt);
                    }
                }
            }
        }
        return objects;
    }

    public static List<Feature> splitFeatures(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber) {
        List<Feature> objects = new ArrayList<Feature>();
        Feature object = null;

        if (column != null && !column.isEmpty()) {

            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charIndex;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    String[] result = MitabParserUtils.quoteAwareStrictOrderSplit(field, new char[]{':', '(', ')'}, true);
                    if (result != null) {

                        int length = result.length;

                        // some exception handling
                        if (length == 0 || length > 3) {
                            InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + "is not a valid feature. " +
                                    "The expected syntax is feature_type:range1,range2(free text). " +
                                    "Check that you don't have one of these special characters ':', '(', ')' in feature type, range or free text. " +
                                    "If yes, the special characters must be escaped with double quote.");
                            evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            for (MitabParserListener l : listenerList){
                                l.fireOnInvalidFormat(evt);
                            }
                        }

                        else if (length == 1) {
                            if (!result[0].equalsIgnoreCase("-")) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + "is not a valid feature. " +
                                        "The expected syntax is feature_type:range1,range2(free text). " +
                                        "At least one range should be specified. If undetermined, use '?-?'");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }

                                if (result[0].length() == 0) {
                                    InvalidFormatEvent evt3 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + "is not a valid feature. " +
                                            "The feature type cannot be empty. If the type is not known, use 'unknown'.");
                                    evt3.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    for (MitabParserListener l : listenerList){
                                        l.fireOnInvalidFormat(evt3);
                                    }
                                    object = new FeatureImpl();
                                }
                                else {
                                    object = new FeatureImpl(result[0]);
                                }
                            }
                        } else if (length == 2) {
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + "is not a valid feature. " +
                                        "The feature type cannot be empty. If the type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                                object = new FeatureImpl();
                            }
                            else {
                                object = new FeatureImpl(result[0]);
                            }

                            List<String> ranges = Arrays.asList(result[1].split(","));

                            for (String r : ranges){
                                try {
                                    Range range = RangeFactory.createRangeFromString(r);
                                    object.getRanges().add(range);
                                } catch (IllegalRangeException e) {
                                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_feature_range, "The feature " + field + " contains invalid ranges. Check the syntax for feature ranges. "+e.getMessage());
                                    evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                    InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + " contains invalid ranges. Check the syntax for feature ranges. "+e.getMessage());
                                    evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    for (MitabParserListener l : listenerList){
                                        l.fireOnInvalidFormat(evt);
                                        l.fireOnInvalidFormat(evt2);
                                    }
                                }
                            }
                        } else if (length == 3) {
                            if (result[0].length() == 0) {
                                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + "is not a valid feature. " +
                                        "The feature type cannot be empty. If the type is not known, use 'unknown'.");
                                evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                for (MitabParserListener l : listenerList){
                                    l.fireOnInvalidFormat(evt);
                                }
                                object = new FeatureImpl();
                            }
                            else {
                                object = new FeatureImpl(result[0]);
                            }

                            List<String> ranges = Arrays.asList(result[1].split(","));

                            for (String r : ranges){
                                try {
                                    Range range = RangeFactory.createRangeFromString(r);
                                    object.getRanges().add(range);
                                } catch (IllegalRangeException e) {
                                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_feature_range, "The feature " + field + " contains invalid ranges. Check the syntax for feature ranges. " + e.getMessage());
                                    evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));

                                    InvalidFormatEvent evt2 = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "The feature " + field + " contains invalid ranges. Check the syntax for feature ranges. "+e.getMessage());
                                    evt2.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                                    for (MitabParserListener l : listenerList){
                                        l.fireOnInvalidFormat(evt);
                                        l.fireOnInvalidFormat(evt2);
                                    }
                                }
                            }

                            object.setText(result[2]);
                        }

                        if (object != null) {
                            object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                            objects.add(object);
                        }
                    }

                    newIndex+=field.length();
                }
            }
        }
        return objects;
    }

    public static List<Author> splitAuthor(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber, FileParsingErrorType errorType) {

        //TODO in the future add the year as a new field in the author
        //Now all is a string and we don not need split the author

        List<Author> objects = new ArrayList<Author>();
        Author object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charIndex;
            for (String field : fields) {
                object = null;
                if (field != null) {

                    if (!field.equalsIgnoreCase("-")) {
                        object = new AuthorImpl(field);
                    }

                    if (object != null) {
                        object.setLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                        objects.add(object);
                    }

                    newIndex+=field.length();
                }
            }

            if (fields.length > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), errorType, "We have "+fields.length+" first authors where we expect only one first author");
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return objects;
    }

    public static List<Date> splitDates(String column, List<MitabParserListener> listenerList, int lineNumber, int charNumber, int columnNumber, FileParsingErrorType errorType) {
        List<Date> objects = new ArrayList<Date>();
        Date object = null;

        if (column != null && !column.isEmpty()) {


            String[] fields = MitabParserUtils.quoteAwareSplit(column, new char[]{'|'}, false);
            int newIndex = charNumber;

            for (String field : fields) {
                object = null;
                if (field != null) {

                    try {
                        if (!field.equalsIgnoreCase("-"))
                            object = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(field);
                    } catch (ParseException e) {
                        InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "It is not a valid date (check the syntax. yyyy/MM/dd): " + field.toString());
                        evt.setSourceLocator(new MitabSourceLocator(lineNumber, newIndex, columnNumber));
                        for (MitabParserListener l : listenerList){
                            l.fireOnInvalidFormat(evt);
                        }                             }

                    if (object != null) {
                        objects.add(object);
                    }

                    newIndex+=field.length();
                }
            }

            if (fields.length > 1){
                ClusteredColumnEvent evt = new ClusteredColumnEvent(new HashSet<String>(Arrays.asList(fields)), errorType, "We have "+fields.length+" dates where we expect only one date");
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charNumber, columnNumber));

                for (MitabParserListener l : listenerList){
                    l.fireOnClusteredColumnEvent(evt);
                }
            }
        }
        return objects;
    }

    public static Boolean splitNegative(String column, List<MitabParserListener> listenerList, int lineNumber, int charIndex, int columnNumber) {
        boolean object = false;

        if (column != null && !column.isEmpty()) {

            if (!column.equalsIgnoreCase("-") && (column.equalsIgnoreCase("false") || column.equalsIgnoreCase("true"))) {
                object = Boolean.valueOf(column);
            }
            else {
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "It is not a valid boolean value: " + column.toString());
                evt.setSourceLocator(new MitabSourceLocator(lineNumber, charIndex, columnNumber));
                for (MitabParserListener l : listenerList){
                    l.fireOnInvalidFormat(evt);
                }
            }

        }

        return object;
    }
}
