package psidev.psi.mi.tab.model.builder;

import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.utils.MitabEscapeUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 08/07/2012
 * Time: 23:30
 */
public class MitabWriterUtils {

    private static final String MI_PREFIX = "MI";
    private static final String FIELD_DELIMITER = "|";
    private static final String EMPTY_COLUMN = "-";
    private static final String COLUMN_DELIMITER = "\t";
    private static final String EOF_DELIMITER = "\n";


    public static String buildHeader(PsimiTabVersion version) {
        return createMitabLine(PsimiTabColumns.getHeader(version), version);
    }

    public static String buildLine(BinaryInteraction interaction, PsimiTabVersion version) throws IllegalArgumentException {

        if (version == null) {
            throw new IllegalArgumentException("The version of MITAB can not be written");
        }

        //Interaction to string
        String[] line = new String[version.getNumberOfColumns()];

        //version is 2.5
        Interactor interactorA = interaction.getInteractorA();
        Interactor interactorB = interaction.getInteractorB();


        switch (version) {
            case v2_7:

                // MITAB 2.7
                line[PsimiTabColumns.FEATURES_A.ordinal()] = joinFeatureCollection(interactorA.getFeatures());//37
                line[PsimiTabColumns.STOICHIOMETRY_A.ordinal()] = joinStoichiometryCollection(interactorA.getStoichiometry());//39
                line[PsimiTabColumns.PARTICIPANT_IDENT_MED_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getParticipantIdentificationMethods());//41

                // MITAB 2.7
                line[PsimiTabColumns.FEATURES_B.ordinal()] = joinFeatureCollection(interactorB.getFeatures());//38
                line[PsimiTabColumns.STOICHIOMETRY_B.ordinal()] = joinStoichiometryCollection(interactorB.getStoichiometry()); //40
                line[PsimiTabColumns.PARTICIPANT_IDENT_MED_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getParticipantIdentificationMethods());//42

            case v2_6:
                //MITAB 2.6
                line[PsimiTabColumns.BIOROLE_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getBiologicalRoles()); // 17
                line[PsimiTabColumns.EXPROLE_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getExperimentalRoles());// 19
                line[PsimiTabColumns.INTERACTOR_TYPE_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getInteractorTypes());// 21
                line[PsimiTabColumns.XREFS_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getXrefs());// 23
                line[PsimiTabColumns.ANNOTATIONS_A.ordinal()] = joinAnnotationsCollection(interactorA.getAnnotations());// 26
                line[PsimiTabColumns.CHECKSUM_A.ordinal()] = joinChecksumCollection(interactorA.getChecksums());// 33

                //MITAB 2.6
                line[PsimiTabColumns.BIOROLE_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getBiologicalRoles());// 18
                line[PsimiTabColumns.EXPROLE_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getExperimentalRoles());// 20
                line[PsimiTabColumns.INTERACTOR_TYPE_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getInteractorTypes());// 22
                line[PsimiTabColumns.XREFS_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getXrefs()); // 24
                line[PsimiTabColumns.ANNOTATIONS_B.ordinal()] = joinAnnotationsCollection(interactorB.getAnnotations()); // 27
                line[PsimiTabColumns.CHECKSUM_B.ordinal()] = joinChecksumCollection(interactorB.getChecksums());// 34

                //MITAB 2.6
                line[PsimiTabColumns.COMPLEX_EXPANSION.ordinal()] = joinCrossReferencStyleCollection(interaction.getComplexExpansion()); // 16
                line[PsimiTabColumns.XREFS_I.ordinal()] = joinCrossReferencStyleCollection(interaction.getXrefs());// 25
                line[PsimiTabColumns.ANNOTATIONS_I.ordinal()] = joinAnnotationsCollection(interaction.getAnnotations());// 28
                line[PsimiTabColumns.HOST_ORGANISM.ordinal()] = joinOrganism(interaction.getHostOrganism());// 29
                line[PsimiTabColumns.PARAMETERS_I.ordinal()] = joinParametersCollection(interaction.getParameters());// 30
                line[PsimiTabColumns.CREATION_DATE.ordinal()] = joinDateCollection(interaction.getCreationDate());// 31
                line[PsimiTabColumns.UPDATE_DATE.ordinal()] = joinDateCollection(interaction.getUpdateDate());// 32
                line[PsimiTabColumns.CHECKSUM_I.ordinal()] = joinChecksumCollection(interaction.getChecksums());// 35
                line[PsimiTabColumns.NEGATIVE.ordinal()] = createNegative(interaction.isNegativeInteraction());//36

            case v2_5:

                if (interaction.isNegativeInteraction()) {
                    //TODO check the version
                    throw new IllegalFormatException("The interaction between interactors: "
                            + interaction.getInteractorA().getIdentifiers().toString() + "->"
                            + interaction.getInteractorB().getIdentifiers().toString() + " could not be converted to MITAB25 as it is negative.");
                }
                //MITAB 2.5
                line[PsimiTabColumns.ID_INTERACTOR_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getIdentifiers());// 1
                line[PsimiTabColumns.ALTID_INTERACTOR_A.ordinal()] = joinCrossReferencStyleCollection(interactorA.getAlternativeIdentifiers());// 3
                line[PsimiTabColumns.ALIAS_INTERACTOR_A.ordinal()] = joinAliasCollection(interactorA.getAliases());// 5
                line[PsimiTabColumns.TAXID_A.ordinal()] = joinOrganism(interactorA.getOrganism());// 10

                //MITAB 2.5
                line[PsimiTabColumns.ID_INTERACTOR_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getIdentifiers()); // 2
                line[PsimiTabColumns.ALTID_INTERACTOR_B.ordinal()] = joinCrossReferencStyleCollection(interactorB.getAlternativeIdentifiers());// 4
                line[PsimiTabColumns.ALIAS_INTERACTOR_B.ordinal()] = joinAliasCollection(interactorB.getAliases());// 6
                line[PsimiTabColumns.TAXID_B.ordinal()] = joinOrganism(interactorB.getOrganism()); // 11

                // MITAB 2.5
                line[PsimiTabColumns.INT_DET_METHOD.ordinal()] = joinCrossReferencStyleCollection(interaction.getDetectionMethods());// 7
                line[PsimiTabColumns.PUB_AUTH.ordinal()] = joinAuthorCollection(interaction.getAuthors()); // 8
                line[PsimiTabColumns.PUB_ID.ordinal()] = joinCrossReferencStyleCollection(interaction.getPublications());// 9
                line[PsimiTabColumns.INTERACTION_TYPE.ordinal()] = joinCrossReferencStyleCollection(interaction.getInteractionTypes());// 12
                line[PsimiTabColumns.SOURCE.ordinal()] = joinCrossReferencStyleCollection(interaction.getSourceDatabases());// 13
                line[PsimiTabColumns.INTERACTION_ID.ordinal()] = joinCrossReferencStyleCollection(interaction.getInteractionAcs());// 14
                line[PsimiTabColumns.CONFIDENCE.ordinal()] = joinConfidenceCollection(interaction.getConfidenceValues());// 15
                break;
            default:
                throw new IllegalArgumentException("This version of MITAB is not supported");
        }


        return createMitabLine(line, version);  //To change body of created methods use File | Settings | File Templates.
    }

    /* Create a string from a collection of features */
    private static String joinFeatureCollection(List<Feature> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator<Feature> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Feature field = iterator.next();

                String ranges = StringUtils.join(field.getRanges(), ", ");
                sb.append(joinAttributes(field.getFeatureType(), ranges, field.getText()));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();
    }

    /* Create a string from a collection of annotations */
    private static String joinAnnotationsCollection(List<Annotation> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator<Annotation> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Annotation field = iterator.next();

                sb.append(joinAttributes(field.getTopic(), field.getText(), null));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();

    }

    /* Create a string from a collection of stoichiometries */
    private static String joinStoichiometryCollection(List<Integer> stoichiometry) {
        StringBuilder sb = new StringBuilder();
        if (stoichiometry != null && !stoichiometry.isEmpty()) {

            Iterator<Integer> iterator = stoichiometry.iterator();

            while (iterator.hasNext()) {
                Integer field = iterator.next();

                sb.append(joinAttributes(null, field.toString(), null));
                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();
    }

    /* Create a string from the negative value */
    private static String createNegative(Boolean negativeInteraction) {
        String negative;

        if (negativeInteraction == null) {
            negative = "-";
        } else {
            negative = negativeInteraction.toString();
        }

        return negative;
    }

    /* Create a string from a collection of checksums */
    private static String joinChecksumCollection(List<Checksum> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator<Checksum> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Checksum field = iterator.next();

                sb.append(joinAttributes(field.getMethodName(), field.getChecksum(), null));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }

        return sb.toString();
    }

    private static String joinParametersCollection(List<Parameter> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator<Parameter> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Parameter field = iterator.next();

                sb.append(joinAttributes(field.getType(), field.getValue(), field.getUnit()));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();
    }

    private static String joinOrganism(Organism organism) {
        String result;
        if (organism != null && !organism.getIdentifiers().isEmpty()) {
            result = joinCrossReferencStyleCollection(organism.getIdentifiers());
        } else {
            result = "-";
        }
        return result;
    }

    private static String joinAuthorCollection(List<Author> collection) {
        StringBuilder sb = new StringBuilder();

        if (collection != null && !collection.isEmpty()) {
            Iterator<Author> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Author field = iterator.next();

//                sb.append(joinAttributes(null, field.getName(), null));
                sb.append(field.getName());


                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }

        return sb.toString();
    }

    private static String joinConfidenceCollection(List<Confidence> collection) {
        StringBuilder sb = new StringBuilder();

        if (collection != null && !collection.isEmpty()) {

            Iterator<Confidence> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Confidence field = iterator.next();

                sb.append(joinAttributes(field.getType(), field.getValue(), field.getText()));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }

        return sb.toString();
    }

    /**
     * @param collection The values can not be null.
     * @return
     */
    private static String joinCrossReferencStyleCollection(List<? extends CrossReference> collection) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                Object field = iterator.next();
                if (field instanceof CrossReference) {
                    CrossReference crossReference = (CrossReference) field;
                    sb.append(joinAttributes(crossReference.getDatabase(), crossReference.getIdentifier(), crossReference.getText()));
                }

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();
    }

    private static String joinAliasCollection(List<Alias> collection) {

        StringBuilder sb = new StringBuilder();
        if (collection != null && !collection.isEmpty()) {

            Iterator<Alias> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Alias field = iterator.next();

                sb.append(joinAttributes(field.getDbSource(), field.getName(), field.getAliasType()));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();

    }

    private static String joinDateCollection(List<Date> collection) {

        StringBuilder sb = new StringBuilder();
        // Some examples
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        if (collection != null && !collection.isEmpty()) {

            Iterator<Date> iterator = collection.iterator();

            while (iterator.hasNext()) {
                Date field = iterator.next();

                sb.append(joinAttributes(null, formatter.format(field), null));

                if (iterator.hasNext()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
        } else {
            sb.append('-');
        }
        return sb.toString();

    }

    private static String joinAttributes(String type, String value, String description) {
        StringBuilder sb = new StringBuilder();

        boolean psiCvTerm = false;

        //Empty column
        if (type == null && value == null && description == null) {
            return EMPTY_COLUMN;
        }

        if (type != null) {
            if (MI_PREFIX.equals(type)) {
                type = "psi-mi";
                psiCvTerm = true;
            }
            sb.append(MitabEscapeUtils.escapeFieldElement(type));
            sb.append(':');

        }

        if (value != null) {
            if (type == null && description == null) {
                sb.append(MitabEscapeUtils.escapeFieldElement(value));
            } else {
                if (psiCvTerm) {
                    sb.append(MitabEscapeUtils.escapeFieldElement(MI_PREFIX + ':' + value));

                } else {
                    sb.append(MitabEscapeUtils.escapeFieldElement(value));
                }
            }
        }

        if (description != null) {
            sb.append('(');
            sb.append(MitabEscapeUtils.escapeFieldElement(description));
            sb.append(')');
        }

        return sb.toString();
    }

    protected static String createMitabLine(String[] columns, PsimiTabVersion version) {

        StringBuilder sb = new StringBuilder();
        int numberOfColumns = version.getNumberOfColumns();

        sb.append(columns[0]);

        for (int i = 1; i < numberOfColumns; i++) {
            sb.append(COLUMN_DELIMITER);
            sb.append(columns[i]);
        }

        sb.append(EOF_DELIMITER);

        return sb.toString();
    }
}
