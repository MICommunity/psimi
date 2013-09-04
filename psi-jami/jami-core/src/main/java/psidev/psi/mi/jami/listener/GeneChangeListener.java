package psidev.psi.mi.jami.listener;


import psidev.psi.mi.jami.model.Gene;

/**
 * A listener for changes to genes
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public interface GeneChangeListener
        extends InteractorChangeListener<Gene> {

    /**
     * Listens to the event where the Ensembl identifier is changed.
     * @param gene
     * @param oldValue
     */
    public void onEnsemblUpdate(Gene gene , String oldValue);

    /**
     * Listens to the event where the EnsemblGenomes identifier is changed
     * @param gene
     * @param oldValue
     */
    public void onEnsemblGenomeUpdate(Gene gene , String oldValue);

    /**
     * Listens to the event where the EntrezGene identifier is updated.
     * @param gene
     * @param oldValue
     */
    public void onEntrezGeneIdUpdate(Gene gene , String oldValue);

    /**
     * Listens to the event where the RefSeq identifier is updated.
     * @param gene
     * @param oldValue
     */
    public void onRefseqUpdate(Gene gene , String oldValue);

}
