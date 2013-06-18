package psidev.psi.mi.jami.tab.utils;

import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    public static final String AUTHOR_SUFFIX = " et al.";
    public static final String TAXID = "taxid";

    public static final String [] SPECIAL_CHARACTERS = new String[]{FIELD_SEPARATOR,
            XREF_SEPARATOR, "(", ")"};

    public static final String MITAB_VERSION_OPTION = "mitab_version_key";
    public static final String MITAB_HEADER_OPTION = "mitab_header_key";

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
        return stringToReplace.replaceAll("\\\"", "\"");
    }
}
