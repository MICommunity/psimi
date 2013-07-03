package psidev.psi.mi.jami.tab.utils;

import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Utilisty class for MitabWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabUtils {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static final String COLUMN_SEPARATOR = "\t";
    public static final String FIELD_SEPARATOR = "|";
    public static final String EMPTY_COLUMN = "-";
    public static final String XREF_SEPARATOR = ":";
    public static final String RANGE_SEPARATOR = ",";
    public static final String COMMENT_PREFIX = "#";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final DateFormat PUBLICATION_YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final String UNKNOWN_DATABASE = "unknown";
    public static final String UNKNOWN_TYPE= "unknown";
    public static final String UNKNOWN_ID = "unknown";
    public static final String UNKNOWN_NAME = "unspecified name";
    public static final String AUTHOR_SUFFIX = " et al.";
    public static final String TAXID = "taxid";
    public static final String SHORTLABEL = "shortlabel";
    public static final String DISPLAY_SHORT = "display_short";
    public static final String DISPLAY_LONG = "display_long";

    public static final String [] SPECIAL_CHARACTERS = new String[]{FIELD_SEPARATOR,
            XREF_SEPARATOR, "(", ")"};

    public static final String MITAB_VERSION_OPTION = "mitab_version_key";
    public static final String MITAB_HEADER_OPTION = "mitab_header_key";
    public static final String MITAB_EXTENDED_OPTION = "mitab_extended_key";

    /**
     * Build the header and return an array of String which is an array of column names
     * @param version
     * @return
     * @throws IllegalArgumentException
     */
    public static String[] buildHeader(MitabVersion version) throws IllegalArgumentException {
        if (version == null) {
            throw new IllegalArgumentException("The header for this version of MITAB can not be created ");
        }

        MitabColumnName[] columns = MitabColumnName.values();
        int numberOfColumns = version.getNumberOfColumns();
        String[] header = new String[numberOfColumns];

        for (int i = 0; i < numberOfColumns; i++) {
            header[i] = columns[i].toString();
        }
        //We add the start of the header #
        header[0] = COMMENT_PREFIX + header[0];

        return header;
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. It is unknown otherwise.
     * @param alias
     * @return the default dbsource for this alias
     */
    public static String findDbSourceForAlias(Alias alias){

        // these aliases should come from uniprotkb
        if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
            return Xref.UNIPROTKB;
        }

        return UNKNOWN_DATABASE;
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. If the participant evidence has an interaction evidence with an experiment, publication and source,
     * it will return the source shortname. It is unknown otherwise.
     * @param participant
     * @param alias
     * @return the default dbsource for this alias and participant
     */
    public static String findDbSourceForAlias(ParticipantEvidence participant, Alias alias){

        if (participant != null){

            // these aliases should come from uniprotkb
            if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
                return Xref.UNIPROTKB;
            }
            // check source
            else if (participant.getInteractionEvidence() != null){
                InteractionEvidence interaction = participant.getInteractionEvidence();
                if (interaction.getExperiment() != null){
                    Experiment exp = interaction.getExperiment();
                    if (exp.getPublication() != null){
                        Publication pub = exp.getPublication();
                        if (pub.getSource() != null){
                            return pub.getSource().getShortName();
                        }
                    }
                }
            }
        }

        return UNKNOWN_DATABASE;
    }

    /**
     * The source of the Alias is uniprotkb if the alias type is gene name, gene name synonym, isoform synonym,
     * locus name or orf name. If the participant has an modelled interaction with a source,
     * it will return the source shortname. It is unknown otherwise.
     * @param participant
     * @param alias
     * @return
     */
    public static String findDbSourceForAlias(ModelledParticipant participant, Alias alias){

        if (participant != null){

            // these aliases should come from uniprotkb
            if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ISOFORM_SYNONYM_MI, Alias.ISOFORM_SYNONYM)
                    || AliasUtils.doesAliasHaveType(alias, Alias.LOCUS_NAME_MI, Alias.LOCUS_NAME)
                    || AliasUtils.doesAliasHaveType(alias, Alias.ORF_NAME_MI, Alias.ORF_NAME)){
                return Xref.UNIPROTKB;
            }
            // check source
            else if (participant.getModelledInteraction() != null){
                ModelledInteraction interaction = participant.getModelledInteraction();
                if (interaction.getSource() != null){
                    return interaction.getSource().getShortName();
                }
            }
        }

        return UNKNOWN_DATABASE;
    }

    public static String unescapeDoubleQuote(String stringToReplace){
        return stringToReplace.replaceAll("\\\\\"", "\"");
    }

    /**
     * To know if a publication annotation is an interactionTag
     * @param annot
     * @return
     */
    public static boolean isAnnotationAnInteractionTag(Annotation annot){
        return AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.FULL_COVERAGE_MI, Annotation.FULL_COVERAGE)
                || AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PARTIAL_COVERAGE_MI, Annotation.PARTIAL_COVERAGE) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.EXPERIMENTALLY_OBSERVED_MI, Annotation.EXPERIMENTALLY_OBSERVED) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMPORTED_MI, Annotation.IMPORTED) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.INTERNALLY_CURATED_MI, Annotation.INTERNALLY_CURATED) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PREDICTED_MI, Annotation.PREDICTED) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.TEXT_MINING_MI, Annotation.TEXT_MINING) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.NUCLEIC_ACID_PROTEIN_MI, Annotation.NUCLEIC_ACID_PROTEIN) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.SMALL_MOLECULE_PROTEIN_MI, Annotation.SMALL_MOLECULE_PROTEIN) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PROTEIN_PROTEIN_MI, Annotation.PROTEIN_PROTEIN) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.CLUSTERED_MI, Annotation.CLUSTERED) ||
                AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.EVIDENCE_MI, Annotation.EVIDENCE);
    }

    /**
     * Find the best alias to use as a shortname and fullName.
     * It will first collect the alias with display_short if it exists, otherwise display_long if it exists, otherwise gene name if it exists
     * , otherwise shortlabel if it exists
     * otherwise the alias with the shortest alias name.
     * @param aliases
     * @return the best alias to use as a shortname
     */
    public static MitabAlias[] findBestShortNameAndFullNameFromAliases(Collection<MitabAlias> aliases){

        MitabAlias shortLabel = null;
        MitabAlias displayShort = null;
        MitabAlias displayLong = null;
        MitabAlias geneName = null;
        MitabAlias shortName = null;

        for (MitabAlias alias : aliases){
            // display_short is a priority
            if (AliasUtils.doesAliasHaveType(alias, null, DISPLAY_SHORT)){
                if (displayShort == null){
                    displayShort = alias;
                }
            }
            // then display_long
            else if (AliasUtils.doesAliasHaveType(alias, null, DISPLAY_LONG)){
                if (displayLong == null){
                    displayLong = alias;
                }
            }
            // then gene name
            else if (AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
                if (geneName == null){
                    geneName = alias;
                }
            }
            // then shortlabel
            else if (AliasUtils.doesAliasHaveType(alias, null, SHORTLABEL)){
                if (shortLabel == null){
                    shortLabel = alias;
                }
            }
            // then shortname if not set
            else if (shortName == null){
                shortName = alias;
            }
            // then shortest
            else{
                // only replace if shorter
                if (shortName.getName().length() > alias.getName().length()){
                    shortName = alias;
                }
            }
        }

        if (displayShort != null){
            return displayLong != null ? new MitabAlias[]{displayShort, displayLong} : new MitabAlias[]{displayShort};
        }
        else if (displayLong != null){
            return new MitabAlias[]{displayLong, displayLong};
        }
        else if (geneName != null){
            return new MitabAlias[]{geneName};
        }
        else if (shortLabel != null){
            return new MitabAlias[]{shortLabel};
        }
        else {
            return new MitabAlias[]{shortName};
        }
    }

    /**
     * Find the best id to use as a shortname.
     * It will first collect the id with qualifier = display_short if it exists, otherwise qualifier =  display_long if it exists, otherwise qualifier = gene name if it exists
     * , otherwise qualifier = shortlabel if it exists
     * otherwise null.
     * @param altids
     * @return the best id to use as a shortname
     */
    public static MitabXref findBestShortNameFromAlternativeIdentifiers(Collection<MitabXref> altids){

        MitabXref shortLabel = null;
        MitabXref displayShort = null;
        MitabXref displayLong = null;
        MitabXref geneName = null;

        for (MitabXref xref : altids){
            // display_short is a priority
            if (XrefUtils.doesXrefHaveQualifier(xref, null, DISPLAY_SHORT)){
                displayShort = xref;
                break;
            }
            // then display_long
            else if (XrefUtils.doesXrefHaveQualifier(xref, null, DISPLAY_LONG)){
                if (displayLong == null){
                    displayLong = xref;
                }
            }
            // then gene name
            else if (XrefUtils.doesXrefHaveQualifier(xref, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
                if (geneName == null){
                    geneName = xref;
                }
            }
            // then shortlabel
            else if (XrefUtils.doesXrefHaveQualifier(xref, null, SHORTLABEL)){
                if (shortLabel == null){
                    shortLabel = xref;
                }
            }
        }

        if (displayShort != null){
            return displayShort;
        }
        else if (displayLong != null){
            return displayLong;
        }
        else if (geneName != null){
            return geneName;
        }
        else if (shortLabel != null){
            return shortLabel;
        }
        else {
            return null;
        }
    }
}
