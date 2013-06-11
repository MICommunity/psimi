package psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener;


import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 10:56
 */
public interface ProteinEnricherListener extends ProteinChangeListener{

    public void onProteinEnriched(Protein protein, String status);

}
