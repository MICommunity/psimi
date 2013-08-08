package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimumBioactiveEntityUpdater
        extends AbstractBioactiveEntityEnricher{

    public MinimumBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) {


        // bioactiveEntityToEnrich.getShortName();
        //bioactiveEntityToEnrich.getInteractorType();
        //bioactiveEntityToEnrich.getOrganism();
        //bioactiveEntityToEnrich.getAliases();
        //bioactiveEntityToEnrich.getXrefs();

        // FULL NAME
        if(bioactiveEntityFetched.getFullName() != null
                && ! bioactiveEntityFetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())){
            bioactiveEntityToEnrich.setFullName(bioactiveEntityFetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }

        // CHEBI IDENTIFIER
        if(bioactiveEntityFetched.getChebi() != null
                && ! bioactiveEntityFetched.getChebi().equalsIgnoreCase(bioactiveEntityToEnrich.getChebi())){
            bioactiveEntityToEnrich.setChebi(bioactiveEntityFetched.getChebi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onChebiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI Code
        if( bioactiveEntityFetched.getStandardInchi() != null
                && bioactiveEntityFetched.getStandardInchi().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchi())){

            bioactiveEntityToEnrich.setStandardInchi(bioactiveEntityFetched.getStandardInchi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI KEY
        if(bioactiveEntityFetched.getStandardInchiKey() != null
                && ! bioactiveEntityFetched.getStandardInchiKey().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchiKey())){
            bioactiveEntityToEnrich.setStandardInchiKey(bioactiveEntityFetched.getStandardInchiKey());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiKeyUpdate(bioactiveEntityToEnrich , null);
        }

        // SMILE
        if(bioactiveEntityFetched.getSmile() != null
                && bioactiveEntityFetched.getSmile().equalsIgnoreCase(bioactiveEntityToEnrich.getSmile())){
            bioactiveEntityToEnrich.setSmile(bioactiveEntityFetched.getSmile());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onSmileUpdate(bioactiveEntityToEnrich , null);
        }
    }
}
