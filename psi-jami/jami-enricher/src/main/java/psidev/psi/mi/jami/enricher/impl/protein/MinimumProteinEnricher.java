package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.organism.MaximumOrganismUpdater;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher {



    @Override
    protected void processProtein(Protein proteinToEnrich) {
        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)){
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)){
                if(listener != null) listener.onAddedInteractorType(proteinToEnrich);
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
            }
        }

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinFetched.getFullName() != null){
            if(listener != null) listener.onFullNameUpdate(proteinFetched, null);
            proteinToEnrich.setFullName( proteinFetched.getFullName() );
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            if(listener != null) listener.onSequenceUpdate(proteinFetched, null);
            proteinToEnrich.setSequence(proteinFetched.getSequence());
        }

        //TODO
        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            if(listener != null) listener.onUniprotKbUpdate(proteinFetched, null);
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
        }


        //TODO - is this correct? Is there a scenario where 2 primary ACs are created?
        //Identifiers
        Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){
            if(listener != null) listener.onAddedIdentifier(proteinFetched, xref);
            proteinToEnrich.getIdentifiers().add(xref);
        }

        //TODO some introduced aliases may enter a form of conflict - need to do a further comparison.
        //Aliases
        Collection<Alias> subtractedAliases = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){
            if(listener != null) listener.onAddedAlias(proteinFetched, alias);
            proteinToEnrich.getAliases().add(alias);
        }

    }



    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MinimumOrganismEnricher();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }






    /*
    public MinimumProteinEnricher(){
        super();
    }


    public void enrichProtein(Protein proteinToEnrich)
            throws BridgeFailedException,
            MissingServiceException,
            BadToEnrichFormException,
            BadSearchTermException,
            BadResultException,
            SeguidException {

        Collection<Protein> proteinsEnriched = getFullyEnrichedForms(proteinToEnrich);
        Protein proteinEnriched = chooseProteinEnriched(proteinToEnrich, proteinsEnriched);

        if(proteinEnriched != null){
            super.setOrganismEnricher(new MinimumOrganismEnricherOLD());
            runAdditionOnCore(proteinToEnrich, proteinEnriched);
            runAdditionOnChecksum(proteinToEnrich, proteinEnriched);
            this.listener.onProteinEnriched(proteinToEnrich, "Success");
        }
    }

 */
}
