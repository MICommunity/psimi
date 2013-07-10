package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.protein.ProteinEnricherMinimum;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class ParticipantEnricherMinimum<P extends Participant , F extends Feature>
        extends AbstractParticipantEnricher<P , F>  {


    public void enrichParticipants(Collection<P> participantsToEnrich) throws EnricherException {
        for(Participant participant : participantsToEnrich){
            enrichParticipant((P)participant);
        }
    }


    public void enrichParticipant(P participantToEnrich) throws EnricherException {

        if(participantToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null participant.");


        // Enrich BioRole
        if(getCvTermEnricher() != null){
            getCvTermEnricher().enrichCvTerm(participantToEnrich.getBiologicalRole());
        }


        // Enrich Interactor
        CvTerm interactorType = participantToEnrich.getInteractor().getInteractorType();
        if(interactorType.getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)
                || interactorType.getShortName().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR)
                || interactorType.getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)
                || interactorType.getShortName().equalsIgnoreCase(Protein.PROTEIN)){

            if(getProteinEnricher() == null){
                throw new IllegalStateException("ProteinEnricher has not been provided.");
            }
            else {
                //Todo is there a more elegant solution to this?
                if(participantToEnrich.getInteractor() instanceof Protein){
                    getProteinEnricher().enrichProtein( (Protein) participantToEnrich.getInteractor() );
                } else {
                    if(listener != null) listener.onParticipantEnriched(participantToEnrich ,
                            EnrichmentStatus.FAILED,
                            "Found interactor of type "+interactorType.getShortName()+
                            " ("+interactorType.getMIIdentifier()+") "+
                            "but was not an instance of 'Protein', " +
                            "was "+participantToEnrich.getInteractor().getClass()+" instead.");
                    return;
                }

            }
        }


         /*

        for(Feature feature : participantToEnrich.getFeatures()){
            if(obj instanceof Feature){
                Feature feature = (Feature)obj;
                if(getFeatureEnricher() != null) getFeatureEnricher().enrichFeature(feature);
            }
        }        */

        if(listener != null) listener.onParticipantEnriched(participantToEnrich , EnrichmentStatus.SUCCESS , null);

    }





    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new ProteinEnricherMinimum();
        return proteinEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new CvTermEnricherMinimum();
        return cvTermEnricher;
    }

    @Override
    public FeatureEnricher<F> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new FeatureEnricherMinimum<F>();
        return featureEnricher;
    }
}
