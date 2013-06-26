package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface ParticipantEvidenceEnricher {

    public void enrichParticipantEvidence(ParticipantEvidence participantEvidence) throws EnricherException;


    public void setParticipantEvidenceEnricherListener(ParticipantEvidenceEnricherListener listener);
    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener();

    /**
     * Sets the protein enricher which will be used to enrich any proteins in this participant.
     * @param proteinEnricher
     */
    public void setProteinEnricher(ProteinEnricher proteinEnricher);
    public ProteinEnricher getProteinEnricher();
}
