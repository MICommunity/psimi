package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

/**
 * The abstract Mitab line parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public abstract class AbstractMitabLineParser {

    private MitabParserListener listener;

    public MitabParserListener getParserListener(){
        return listener;
    }
    public void setParserListener(MitabParserListener listener){
        this.listener = listener;
    }

    public void fireOnInvalidSyntax(int numberLine, int numberColumn, int mitabColumn, boolean isRange){
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
}
