package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for CooperativeEffect
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultCooperativeEffect implements CooperativeEffect {

    private Collection<CooperativityEvidence> cooperativityEvidences;
    private Collection<ModelledInteraction> affectedInteractions;
    private Collection<Annotation> annotations;
    private CvTerm outcome;
    private CvTerm response;

    public DefaultCooperativeEffect(CvTerm outcome){
        if (outcome == null){
            throw new IllegalArgumentException("The outcome of a CooperativeEffect cannot be null");
        }
        this.outcome = outcome;
    }

    public DefaultCooperativeEffect(CvTerm outcome, CvTerm response){
        this(outcome);
        this.response = response;
    }

    protected void initialiseCooperativityEvidences(){
        this.cooperativityEvidences = new ArrayList<CooperativityEvidence>();
    }

    protected void initialiseCooperativityEvidencesWith(Collection<CooperativityEvidence> evidences){
        if (evidences == null){
            this.cooperativityEvidences = Collections.EMPTY_LIST;
        }
        else{
            this.cooperativityEvidences = evidences;
        }
    }

    protected void initialiseAffectedInteractions(){
        this.affectedInteractions = new ArrayList<ModelledInteraction>();
    }

    protected void initialiseAffectedInteractionsWith(Collection<ModelledInteraction> affected){
        if (affected == null){
            this.affectedInteractions = Collections.EMPTY_LIST;
        }
        else{
            this.affectedInteractions = affected;
        }
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else{
            this.annotations = annotations;
        }
    }

    public Collection<CooperativityEvidence> getCooperativityEvidences() {
        if (cooperativityEvidences == null){
            initialiseCooperativityEvidences();
        }
        return cooperativityEvidences;
    }

    public Collection<ModelledInteraction> getAffectedInteractions() {
        if (affectedInteractions == null){
            initialiseAffectedInteractions();
        }
        return affectedInteractions;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return annotations;
    }

    public CvTerm getOutCome() {
        return outcome;
    }

    public void setOutCome(CvTerm effect) {
        if (effect == null){
           throw new IllegalArgumentException("The outcome of a CooperativeEffect cannot be null");
        }
        this.outcome = effect;
    }

    public CvTerm getResponse() {
        return this.response;
    }

    public void setResponse(CvTerm response) {
        this.response = response;
    }
}
