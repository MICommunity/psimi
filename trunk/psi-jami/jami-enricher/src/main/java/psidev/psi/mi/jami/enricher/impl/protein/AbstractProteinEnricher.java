package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/06/13
 * Time: 15:34
 */
public abstract class AbstractProteinEnricher
implements ProteinEnricher {

    protected ProteinFetcher fetcher = null;
    protected ProteinEnricherListener listener = null;

    protected OrganismEnricher organismEnricher = null;

    protected Protein proteinFetched = null;


    public boolean enrichProtein(Protein proteinToEnrich)
            throws BridgeFailedException, MissingServiceException,
            BadToEnrichFormException, BadEnrichedFormException,
            BadSearchTermException, BadResultException,
            SeguidException {

        proteinFetched = fetchProtein(proteinToEnrich);
        if(proteinFetched == null){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Failed. No protein could be found.");
            return false;
        }

        if (! areNoConflicts(proteinToEnrich) ) return false;

        if (getOrganismEnricher().getFetcher() instanceof MockOrganismFetcher){
            MockOrganismFetcher organismFetcher = (MockOrganismFetcher)getOrganismEnricher().getFetcher();
            organismFetcher.clearOrganisms();
            organismFetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinFetched.getOrganism());
        }

        getOrganismEnricher().enrichOrganism(proteinToEnrich.getOrganism());

        processProtein(proteinToEnrich);

        if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Success. Protein enriched.");
        return true;
    }



    /**
     * Checks for fatal conflicts between the proteinToEnrich and the enriched form.
     * The interactor type is the first comparison.
     * It may either be the psi-mi term for 'protein' or 'unknown interactor'.
     * If anything other than these terms are found, the enrichment stops and returns false.
     * <p>
     * The organisms are then compared. If the Enriched organism is null, an exception is fired.
     * If the proteinToEnrich has a null organism, a new, 'unknown' organism is added.
     * Otherwise the organisms taxids' are compared, returning false if they do not match.
     * <p>
     * In both cases, if false is returned, an onProteinEnriched event is fired with a 'failed' status.
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @return  Whether there were any fatal conflicts which
     * @throws  BadEnrichedFormException
     */
    protected boolean areNoConflicts(Protein proteinToEnrich)
            throws BadEnrichedFormException {

        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                Protein.PROTEIN_MI)
                && !proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                Interactor.UNKNOWN_INTERACTOR_MI)){

            if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Failed. Conflict in interactorType. " +
                    "Found " + proteinToEnrich.getInteractorType().getShortName() + " " +
                    "with the psi-mi id " + proteinToEnrich.getInteractorType().getMIIdentifier() + ".");
            return false;
        }

        //Organism
        if(proteinFetched.getOrganism() == null) throw new BadEnrichedFormException(
                "The enriched protein has a null organism.");
        else if(proteinToEnrich.getOrganism() == null){
            proteinToEnrich.setOrganism(new DefaultOrganism(-3));
        }else if(proteinToEnrich.getOrganism().getTaxId() != -3
                && ! OrganismTaxIdComparator.areEquals(proteinToEnrich.getOrganism(), proteinToEnrich.getOrganism())){

            if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Failed. Conflict in organism. " +
                    "Found taxid " + proteinToEnrich.getOrganism().getTaxId() + " " +
                    "but was enriching taxid " + proteinFetched.getOrganism().getTaxId() + ".");
            return false;
        }

        return true;
    }






    protected Protein fetchProtein(Protein proteinToEnrich) {
        return null;
    }

    protected abstract void processProtein(Protein proteinToEnrich);

    public void setFetcher(ProteinFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public ProteinFetcher getFetcher() {
        return fetcher;
    }

    public void setProteinEnricherListener(ProteinEnricherListener proteinEnricherListener) {
        this.listener = proteinEnricherListener;
    }

    public ProteinEnricherListener getProteinEnricherListener() {
        return listener;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }

    public abstract OrganismEnricher getOrganismEnricher();
}
