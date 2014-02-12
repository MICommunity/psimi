package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
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
public interface ProteinEnricher extends PolymerEnricher<Protein>{

    /**
     * The protein mapper to be used when a protein doesn't have a uniprot id or the uniprotID is dead.
     * @param proteinMapper   The remapper to use.
     */
    public void setProteinMapper(ProteinMapper proteinMapper);

    /**
     * The protein remapper has no default and can be left null
     * @return  The current remapper.
     */
    public ProteinMapper getProteinMapper();

    public Protein find(Protein objectToEnrich) throws EnricherException;
}
