package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.enricher.util.ChecksumMerger;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MinimumProteinUpdater
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    protected boolean isRemapped = false;

    public MinimumProteinUpdater(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    @Override
    public void enrichProtein(Protein proteinToEnrich) throws EnricherException {
        isRemapped = false;
        super.enrichProtein(proteinToEnrich);
    }

    @Override
    protected boolean remapDeadProtein(Protein proteinToEnrich) throws EnricherException {

        proteinToEnrich.getXrefs().add(
                new DefaultXref(
                        CvTermUtils.createUniprotkbDatabase(),
                        proteinToEnrich.getUniprotkb()
                )); //TODO UNIPROT REMOVED QUALIFIER

        proteinToEnrich.getAnnotations().add(
                AnnotationUtils.createCaution("This sequence has been withdrawn from Uniprot."));

        proteinToEnrich.setUniprotkb(null);

        if(remapProtein(proteinToEnrich , "proteinToEnrich has a dead UniprotKB ID.")){
            isRemapped = true;
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void processProtein(Protein proteinToEnrich)  {
        //ShortName - is never null
        if (! proteinToEnrich.getShortName().equalsIgnoreCase(proteinFetched.getShortName() )) {
            String oldValue = proteinToEnrich.getShortName();
            proteinToEnrich.setShortName(proteinFetched.getShortName());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onShortNameUpdate(proteinToEnrich, oldValue);
        }

        //Full name
        if(proteinFetched.getFullName() != null
                && ! proteinFetched.getFullName().equalsIgnoreCase(proteinToEnrich.getFullName()) ) {
            String oldValue = proteinToEnrich.getFullName();
            proteinToEnrich.setFullName(proteinFetched.getFullName());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onFullNameUpdate(proteinToEnrich, oldValue);
        }

        //PRIMARY Uniprot AC
        if(proteinFetched.getUniprotkb() != null
                && ! proteinFetched.getUniprotkb().equalsIgnoreCase(proteinToEnrich.getUniprotkb()) ) {
            String oldValue = proteinToEnrich.getUniprotkb();
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onUniprotKbUpdate(proteinToEnrich, oldValue);
        }

        //Sequence
        if(proteinFetched.getSequence() != null
                && ! isRemapped //Todo - check that if the protein was remapped, the sequence remains unchanged
                && ! proteinFetched.getSequence().equalsIgnoreCase(proteinToEnrich.getSequence()) ) {
            String oldValue = proteinToEnrich.getSequence();
            proteinToEnrich.setSequence(proteinFetched.getSequence());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onSequenceUpdate(proteinToEnrich, oldValue);
        }

        ChecksumMerger checksumMerger = new ChecksumMerger();
        //TODO



        // For Identifiers and Aliases:
        // 1 - Find the CvTerms which represent the different types in the fetched set.
        // 2 - Cycle through the toEnrich set, recording any entries which meet the criteria to be removed
        //      and any entries which do not need to be added.
        // 3 - Add/remove those entries on the appropriate lists.

        // == Identifiers ==
        if(! proteinFetched.getIdentifiers().isEmpty()) {
            XrefMerger xrefMerger = new XrefMerger();
            xrefMerger.merge(proteinFetched.getIdentifiers() , proteinToEnrich.getIdentifiers() , false);

            for(Xref xref: xrefMerger.getToRemove()){
                proteinToEnrich.getIdentifiers().remove(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onRemovedIdentifier(proteinToEnrich , xref);
            }

            for(Xref xref: xrefMerger.getToAdd()){
                proteinToEnrich.getIdentifiers().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedIdentifier(proteinToEnrich, xref);
            }
        }



        // == Alias ==
        if(! proteinFetched.getAliases().isEmpty()) {
            AliasMerger aliasMerger = new AliasMerger();
            aliasMerger.merge(proteinFetched.getAliases() , proteinToEnrich.getAliases());

            for(Alias alias: aliasMerger.getToRemove()){
                proteinToEnrich.getAliases().remove(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onRemovedAlias(proteinToEnrich , alias);
            }

            for(Alias alias: aliasMerger.getToAdd()){
                proteinToEnrich.getAliases().add(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedAlias(proteinToEnrich, alias);
            }
        }
    }

    /*
    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MinimumOrganismUpdater();
        }

        return organismEnricher;
    }*/
}
