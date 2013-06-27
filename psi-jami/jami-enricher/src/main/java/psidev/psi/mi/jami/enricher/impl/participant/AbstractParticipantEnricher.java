package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 */
public class AbstractParticipantEnricher
        implements ParticipantEnricher {


    protected ParticipantEnricherListener listener;

    protected ProteinEnricher proteinEnricher;
    protected CvTermEnricher cvTermEnricher;
    protected FeatureEnricher featureEnricher;


    public void enrichParticipant(Participant participantToEnrich) throws EnricherException {

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
                    if(listener != null) listener.onParticipantEnriched(participantToEnrich , "Failed. " +
                            "Found interactor of type "+interactorType.getShortName()+
                            " ("+interactorType.getMIIdentifier()+") "+
                            "but was not an instance of 'Protein', " +
                            "was "+participantToEnrich.getInteractor().getClass()+" instead.");
                    return;
                }

            }
        }

        //Enrich ParticipantEvidence
        if(participantToEnrich instanceof ParticipantEvidence) {
            ParticipantEvidence participantEvidenceToEnrich = (ParticipantEvidence)participantToEnrich;
            if(getCvTermEnricher() != null){

                getCvTermEnricher().enrichCvTerm(
                        participantEvidenceToEnrich.getExperimentalRole());

                for(CvTerm cvTerm : participantEvidenceToEnrich.getIdentificationMethods()){
                    getCvTermEnricher().enrichCvTerm(cvTerm);
                }

                for(CvTerm cvTerm : participantEvidenceToEnrich.getExperimentalPreparations()){
                    getCvTermEnricher().enrichCvTerm(cvTerm);
                }
            }
        }


        for(Object obj : participantToEnrich.getFeatures()){
            if(obj instanceof Feature){
                Feature feature = (Feature)obj;
                if(getFeatureEnricher() != null) getFeatureEnricher().enrichFeature(feature);
            }
        }



        if(listener != null) listener.onParticipantEnriched(participantToEnrich , "Success.");

    }


    public void setParticipantListener(ParticipantEnricherListener participantEnricherListener) {
        this.listener = participantEnricherListener;
    }

    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
    }

    public ProteinEnricher getProteinEnricher() {
        return proteinEnricher;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    public void setFeatureEnricher(FeatureEnricher featureEnricher){
        this.featureEnricher = featureEnricher;
    }

    public FeatureEnricher getFeatureEnricher(){
        return featureEnricher;
    }

}
