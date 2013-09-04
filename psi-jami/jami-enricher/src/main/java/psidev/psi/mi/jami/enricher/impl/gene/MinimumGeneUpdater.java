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
public class MinimumGeneUpdater
        extends AbstractGeneEnricher{

    public MinimumGeneUpdater(GeneFetcher fetcher) {
        super(fetcher);
    }

    @Override
    void processGene(Gene geneToEnrich) {

        // == Short Name ================================================================
        if(geneFetched.getShortName() != null
                && ! geneFetched.getShortName().equalsIgnoreCase(geneToEnrich.getShortName())){

            String oldValue = geneToEnrich.getShortName();
            geneToEnrich.setShortName(geneFetched.getShortName());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onShortNameUpdate(geneToEnrich, oldValue);
        }

        // == Full Name ====================================================================
        if(geneFetched.getFullName() != null
                && ! geneFetched.getFullName().equalsIgnoreCase(geneToEnrich.getFullName())){

            String oldValue = geneToEnrich.getFullName();
            geneToEnrich.setFullName(geneFetched.getFullName());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onFullNameUpdate(geneToEnrich, oldValue);
        }

        // == Refseq AC ================================================================
        if(geneFetched.getRefseq() != null
                && ! geneFetched.getRefseq().equalsIgnoreCase(geneToEnrich.getRefseq())){

            String oldValue = geneToEnrich.getRefseq();
            geneToEnrich.setRefseq(geneFetched.getRefseq());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onRefseqUpdate(geneToEnrich, oldValue);
        }

        // == Ensembl AC ================================================================
        if(geneFetched.getEnsembl() != null
                && ! geneFetched.getEnsembl().equalsIgnoreCase(geneToEnrich.getEnsembl())){

            String oldValue = geneToEnrich.getEnsembl();
            geneToEnrich.setEnsembl(geneFetched.getEnsembl());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnsemblUpdate(geneToEnrich, oldValue);
        }

        // == EnsemblGenomes AC ================================================================
        if(geneFetched.getEnsembleGenome() != null
                && ! geneFetched.getEnsembleGenome().equalsIgnoreCase(geneToEnrich.getEnsembleGenome())){

            String oldValue = geneToEnrich.getEnsembleGenome();
            geneToEnrich.setEnsemblGenome(geneFetched.getEnsembleGenome());
            if (getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnsemblGenomeUpdate(geneToEnrich, oldValue);
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
            for(Alias alias : merger.getToRemove()){
                geneToEnrich.getAliases().remove(alias);
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onRemovedAlias(geneToEnrich, alias);
            }
        }
    }
}
