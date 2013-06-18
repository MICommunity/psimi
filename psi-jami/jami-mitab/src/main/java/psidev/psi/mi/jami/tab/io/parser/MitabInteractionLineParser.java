package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * An extension of MitabLineParser that returns simple interactions only.
 *
 * It ignore properties of InteractionEvidence and ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class MitabInteractionLineParser extends MitabLineParser {

    private MitabParserListener listener;

    public MitabInteractionLineParser(InputStream stream) {
        super(stream);
    }

    public MitabInteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public MitabInteractionLineParser(Reader stream) {
        super(stream);
    }

    public MitabInteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    MitabParserListener getParserListener() {
        return listener;
    }

    @Override
    void setParserListener(MitabParserListener listener) {
        this.listener = listener;
    }

    @Override
    void fireOnInvalidSyntax(int numberLine, int numberColumn, int mitabColumn, boolean isRange) {
        if (this.listener != null){
            int newMitabColumn = mitabColumn - 1;

            if (newMitabColumn == MitabColumnName.ID_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ID_INTERACTOR_B.ordinal() ||
                    newMitabColumn == MitabColumnName.ALTID_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ALTID_INTERACTOR_B.ordinal() ||
                    newMitabColumn == MitabColumnName.PUB_ID.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTION_ID.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_A.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_B.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_I.ordinal()){
                this.listener.onInvalidXref(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.ALIAS_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ALIAS_INTERACTOR_B.ordinal()){
                this.listener.onInvalidAlias(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.INT_DET_METHOD.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTION_TYPE.ordinal() ||
                    newMitabColumn == MitabColumnName.COMPLEX_EXPANSION.ordinal() ||
                    newMitabColumn == MitabColumnName.BIOROLE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.BIOROLE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.EXPROLE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.EXPROLE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTOR_TYPE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTOR_TYPE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.PARTICIPANT_IDENT_MED_A.ordinal() ||
                    newMitabColumn == MitabColumnName.PARTICIPANT_IDENT_MED_B.ordinal()){
                this.listener.onInvalidCvTerm(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.PUB_AUTH.ordinal()){
                this.listener.onInvalidFirstAuthor(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.TAXID_A.ordinal() ||
                    newMitabColumn == MitabColumnName.TAXID_B.ordinal() ||
                    newMitabColumn == MitabColumnName.HOST_ORGANISM.ordinal()){
                this.listener.onInvalidOrganismSyntax(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.SOURCE.ordinal()){
                this.listener.onInvalidSource(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CONFIDENCE.ordinal()){
                this.listener.onInvalidConfidence(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.ANNOTATIONS_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ANNOTATIONS_B.ordinal() ||
                    newMitabColumn == MitabColumnName.ANNOTATIONS_I.ordinal()){
                this.listener.onInvalidAnnotation(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.PARAMETERS_I.ordinal()){
                this.listener.onInvalidParameter(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CHECKSUM_A.ordinal() ||
                    newMitabColumn == MitabColumnName.CHECKSUM_B.ordinal() ||
                    newMitabColumn == MitabColumnName.CHECKSUM_I.ordinal()){
                this.listener.onInvalidChecksum(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CREATION_DATE.ordinal() ||
                    newMitabColumn == MitabColumnName.UPDATE_DATE.ordinal()){
                this.listener.onInvalidDate(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.NEGATIVE.ordinal()){
                this.listener.onInvalidNegative(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.FEATURES_A.ordinal() ||
                    newMitabColumn == MitabColumnName.FEATURES_B.ordinal()){
                if (!isRange){
                    this.listener.onInvalidFeature(numberLine, numberColumn, mitabColumn);
                }
                else{
                    this.listener.onInvalidRange(numberLine, numberColumn, mitabColumn);
                }
            }
            else if (newMitabColumn == MitabColumnName.STOICHIOMETRY_A.ordinal() ||
                    newMitabColumn == MitabColumnName.STOICHIOMETRY_B.ordinal()){
                this.listener.onInvalidStoichiometry(numberLine, numberColumn, mitabColumn);
            }
            else{
                this.listener.onInvalidLine(numberLine, numberColumn, mitabColumn);
            }
        }
    }

    @Override
    MitabParticipant finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<MitabFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod) {
        // first identify interactor

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    MitabInteraction finishInteraction(Participant A, Participant B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
