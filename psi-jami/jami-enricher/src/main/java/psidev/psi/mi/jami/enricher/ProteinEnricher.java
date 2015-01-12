package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.model.Protein;

/**
 * The Protein enricher is an enricher which can enrich either single protein or a collection.
 * A protein enricher must be initiated with a protein fetcher.
 * Sub enrichers: CvTerm, Organism.
 * Additionally, the protein enricher has a protein remapper for finding dead or demerged proteins.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ProteinEnricher extends InteractorEnricher<Protein>{

    /**
     * The protein remapper has no default and can be left null
     * @return  The current remapper.
     */
    public ProteinMapper getProteinMapper();

    public void setProteinMapper(ProteinMapper mapper);
}
