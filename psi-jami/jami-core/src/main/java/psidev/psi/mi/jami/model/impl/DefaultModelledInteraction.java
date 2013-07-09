package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implemntation for ModelledInteraction
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the ModelledInteraction object is a complex object.
 * To compare ModelledInteraction objects, you can use some comparators provided by default:
 * - DefaultModelledInteractionComparator
 * - UnambiguousModelledInteractionComparator
 * - DefaultCuratedModelledInteractionComparator
 * - UnambiguousCuratedModelledInteractionComparator
 * - DefaultCuratedExactModelledInteractionComparator
 * - UnambiguousCuratedExactModelledInteractionComparator
 * - DefaultExactModelledInteractionComparator
 * - UnambiguousExactModelledInteractionComparator
 * - ModelledInteractionComparator
 * - CuratedModelledInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultModelledInteraction extends AbstractInteraction<ModelledParticipant> implements ModelledInteraction{

    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;

    public DefaultModelledInteraction() {
        super();
    }

    public DefaultModelledInteraction(String shortName) {
        super(shortName);
    }

    public DefaultModelledInteraction(String shortName, Source source) {
        super(shortName);
        this.source = source;
    }

    public DefaultModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultModelledInteraction(String shortName, Source source, CvTerm type) {
        this(shortName, type);
        this.source = source;
    }

    protected void initialiseInteractionEvidences(){
        this.interactionEvidences = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseCooperativeEffects(){
        this.cooperativeEffects = new ArrayList<CooperativeEffect>();
    }

    protected void initialiseCooperativeEffectsWith(Collection<CooperativeEffect> cooperativeEffects){
        if (cooperativeEffects == null){
            this.cooperativeEffects = Collections.EMPTY_LIST;
        }
        else{
            this.cooperativeEffects = cooperativeEffects;
        }
    }

    protected void initialiseModelledConfidences(){
        this.modelledConfidences = new ArrayList<ModelledConfidence>();
    }

    protected void initialiseModelledConfidencesWith(Collection<ModelledConfidence> confidences){
        if (confidences == null){
            this.modelledConfidences = Collections.EMPTY_LIST;
        }
        else {
            this.modelledConfidences = confidences;
        }
    }

    protected void initialiseInteractionEvidencesWith(Collection<InteractionEvidence> evidences){
        if (evidences == null){
            this.interactionEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.interactionEvidences = evidences;
        }
    }

    protected void initialiseModelledParameters(){
        this.modelledParameters = new ArrayList<ModelledParameter>();
    }

    protected void initialiseModelledParametersWith(Collection<ModelledParameter> parameters){
        if (parameters == null){
            this.modelledParameters = Collections.EMPTY_LIST;
        }
        else {
            this.modelledParameters = parameters;
        }
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactionEvidences == null){
            initialiseInteractionEvidences();
        }
        return this.interactionEvidences;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        if (modelledConfidences == null){
            initialiseModelledConfidences();
        }
        return this.modelledConfidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (modelledParameters == null){
            initialiseModelledParameters();
        }
        return this.modelledParameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (cooperativeEffects == null){
            initialiseCooperativeEffects();
        }
        return this.cooperativeEffects;
    }
}
