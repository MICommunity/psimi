package psidev.psi.mi.jami.enricher.impl.participantevidence;


import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.ProteinEnricherMinimum;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
@Deprecated
public class MinimumParticipantEvidenceEnricher
        extends AbstractParticipantEvidenceEnricher
{


    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new ProteinEnricherMinimum();
        return proteinEnricher;
    }
}
