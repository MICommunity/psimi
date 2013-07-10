package psidev.psi.mi.jami.enricher.impl.participantevidence;


import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.ProteinUpdaterMaximum;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
@Deprecated
public class MaximumParticipantEvidenceUpdater
        extends MinimumParticipantEvidenceUpdater
     {

    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new ProteinUpdaterMaximum();
        return proteinEnricher;
    }
}
