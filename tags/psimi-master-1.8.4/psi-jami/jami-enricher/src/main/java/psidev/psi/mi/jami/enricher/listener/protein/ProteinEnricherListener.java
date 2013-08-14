package psidev.psi.mi.jami.enricher.listener.protein;


import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  10/06/13
 */
public interface ProteinEnricherListener
        extends ProteinChangeListener , EnricherListener<Protein> {

    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status , String message);

    public void onProteinRemapped(Protein protein, String oldUniprot);

}
