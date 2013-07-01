package psidev.psi.mi.jami.enricher.impl.participantevidence;


import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinUpdater;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
@Deprecated
public class MinimumParticipantEvidenceUpdater
        extends MinimumParticipantEvidenceEnricher{


    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MinimumProteinUpdater();
        return proteinEnricher;
    }
}
