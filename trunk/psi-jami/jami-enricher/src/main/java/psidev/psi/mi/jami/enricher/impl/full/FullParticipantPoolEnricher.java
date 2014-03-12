package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalParticipantPoolEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * A basic minimal updater for Participant pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullParticipantPoolEnricher<P extends ParticipantPool, F extends Feature> extends MinimalParticipantPoolEnricher<P,F> {

    private FullParticipantEnricher<P,F> fullEnricher;

    public FullParticipantPoolEnricher(){
        super();
        this.fullEnricher = new FullParticipantEnricher<P, F>();
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }

    @Override
    public void processOtherProperties(P poolToEnrich, P fetched) throws EnricherException {
        this.fullEnricher.processOtherProperties(poolToEnrich, fetched);
        super.processOtherProperties(poolToEnrich, fetched);
    }

    @Override
    public void processOtherProperties(P participantToEnrich) throws EnricherException {
        this.fullEnricher.processOtherProperties(participantToEnrich);
        super.processOtherProperties(participantToEnrich);
    }

    @Override
    public ParticipantEnricherListener getParticipantEnricherListener() {
        return this.fullEnricher.getParticipantEnricherListener();
    }

    @Override
    public void setParticipantListener(ParticipantEnricherListener listener) {
        this.fullEnricher.setParticipantListener(listener);
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher) {
        this.fullEnricher.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.fullEnricher.getCvTermEnricher();
    }

    @Override
    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher) {
        this.fullEnricher.setFeatureEnricher(featureEnricher);
    }

    @Override
    public FeatureEnricher<F> getFeatureEnricher() {
        return this.fullEnricher.getFeatureEnricher();
    }

    @Override
    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.fullEnricher.setInteractorEnricher(interactorEnricher);
    }

    @Override
    public CompositeInteractorEnricher getInteractorEnricher() {
        return this.fullEnricher.getInteractorEnricher();
    }
}
