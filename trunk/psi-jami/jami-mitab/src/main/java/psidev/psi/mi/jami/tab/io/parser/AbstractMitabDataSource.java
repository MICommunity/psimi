package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.FileSourceError;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The abstract Mitab data source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public abstract class AbstractMitabDataSource implements MIFileDataSource, StreamingInteractionSource {

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

    public void initialiseContext(Map<String, Object> options) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void open() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean validateFileSyntax() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends ModelledInteraction> getModelledInteractionsIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<? extends Interaction> getInteractionsIterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
