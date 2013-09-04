package psidev.psi.mi.jami.enricher.impl.gene;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MinimumGeneEnricher
        extends AbstractGeneEnricher{

    public MinimumGeneEnricher(GeneFetcher fetcher) {
        super(fetcher);
    }

    @Override
    void processGene(Gene geneToEnrich) {

        // == FullName ================================================================
        if(geneToEnrich.getFullName() == null
                && geneFetched.getFullName() != null){

            geneToEnrich.setFullName(geneFetched.getFullName());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onFullNameUpdate(geneToEnrich, null);
        }

        // == Refseq AC ================================================================
        if(geneToEnrich.getRefseq() == null
                && geneFetched.getRefseq() != null){

            geneToEnrich.setRefseq(geneFetched.getRefseq());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onRefseqUpdate(geneToEnrich, null);
        }

        // == Ensembl AC ================================================================
        if(geneToEnrich.getEnsembl() == null
                && geneFetched.getEnsembl() != null){

            geneToEnrich.setFullName(geneFetched.getEnsembl());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnsemblUpdate(geneToEnrich, null);
        }

        // == EnsemblGenomes AC ================================================================
        if(geneToEnrich.getEnsembleGenome() == null
                && geneFetched.getEnsembleGenome() != null){

            geneToEnrich.setFullName(geneFetched.getEnsembl());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnsemblGenomeUpdate(geneToEnrich, null);
        }


        // == Alias ====================================================================
        if( ! geneToEnrich.getAliases().isEmpty()
                && ! geneFetched.getAliases().isEmpty() ){
            AliasMerger merger = new AliasMerger();
            merger.merge(geneFetched.getAliases() , geneToEnrich.getAliases());
            for(Alias alias : merger.getToAdd()){
                geneToEnrich.getAliases().add(alias);
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onAddedAlias(geneToEnrich , alias);
            }
        }
    }
}
