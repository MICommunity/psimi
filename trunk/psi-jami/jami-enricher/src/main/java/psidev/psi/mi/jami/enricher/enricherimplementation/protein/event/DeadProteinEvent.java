package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;


import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 11:09
 */
public class DeadProteinEvent {
    private Protein protein;

    public DeadProteinEvent(Protein protein){
        this.protein = protein;
    }
}
