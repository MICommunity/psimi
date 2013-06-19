package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.MaximumProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinUpdater;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MinimumParticipantEnricher
        extends AbstractParticipantEnricher
        implements ParticipantEnricher  {


    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MinimumProteinEnricher();
        return proteinEnricher;
    }
}
