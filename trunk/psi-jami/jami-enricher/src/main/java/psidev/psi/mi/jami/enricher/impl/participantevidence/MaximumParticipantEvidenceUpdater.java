package psidev.psi.mi.jami.enricher.impl.participantevidence;

import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEvidenceEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MaximumProteinUpdater;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MaximumParticipantEvidenceUpdater
        extends MinimumParticipantEvidenceUpdater
        implements ParticipantEvidenceEnricher {

    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MaximumProteinUpdater();
        return proteinEnricher;
    }
}
