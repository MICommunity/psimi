package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public abstract class DefaultParticipantEnricher<P extends Participant>
        implements ParticipantEnricher<P>  {


    protected ParticipantEnricherListener listener;

    protected ProteinEnricher proteinEnricher;
    protected CvTermEnricher cvTermEnricher;
    protected FeatureEnricher featureEnricher;


    public void setParticipantListener(ParticipantEnricherListener participantEnricherListener) {
        this.listener = participantEnricherListener;
    }

    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
    }

    /*public ProteinEnricher getProteinEnricher() {
        return proteinEnricher;
    } */

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    /*public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }  */

    public void setFeatureEnricher(FeatureEnricher featureEnricher){
        this.featureEnricher = featureEnricher;
    }

   /* public FeatureEnricher getFeatureEnricher(){
        return featureEnricher;
    }  */

}
