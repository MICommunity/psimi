package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains several MI file listeners
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/12/13</pre>
 */

public class MIFileParserCompositeListener<T extends MIFileParserListener> implements MIFileParserListener{

    private List<T> delegates;

    public MIFileParserCompositeListener(){
        this.delegates = new ArrayList<T>();
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        for (T delegate : delegates){
            delegate.onInvalidSyntax(context, e);
        }
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        for (T delegate : delegates){
            delegate.onSyntaxWarning(context, message);
        }
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        for (T delegate : delegates){
            delegate.onMissingCvTermName(term, context, message);
        }
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingInteractorName(interactor, context);
        }
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onParticipantWithoutInteractor(participant, context);
        }
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onInteractionWithoutParticipants(interaction, context);
        }
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onInvalidOrganismTaxid(taxid, context);
        }
    }

    public void onMissingParameterValue(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingParameterValue(context);
        }
    }

    public void onMissingParameterType(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingParameterType(context);
        }
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingConfidenceValue(context);
        }
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingConfidenceType(context);
        }
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingChecksumValue(context);
        }
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onMissingChecksumMethod(context);
        }
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onInvalidPosition(message, context);
        }
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onInvalidRange(message, context);
        }
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onInvalidStoichiometry(message, context);
        }
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onXrefWithoutDatabase(context);
        }
    }

    public void onXrefWithoutId(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onXrefWithoutId(context);
        }
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onAnnotationWithoutTopic(context);
        }
    }

    public void onAliasWithoutName(FileSourceContext context) {
        for (T delegate : delegates){
            delegate.onAliasWithoutName(context);
        }
    }

    public List<T> getDelegates() {
        return delegates;
    }
}
