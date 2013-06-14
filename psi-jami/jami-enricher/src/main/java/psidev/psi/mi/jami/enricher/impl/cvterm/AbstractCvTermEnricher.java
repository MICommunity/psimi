package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 14:19
 */
public abstract class AbstractCvTermEnricher
        implements CvTermEnricher {

    protected CvTermFetcher fetcher = null;
    protected CvTermEnricherListener listener = null;

    protected CvTerm cvTermFetched = null;

    public AbstractCvTermEnricher() {
    }

    public void setCvTermFetcher(CvTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public CvTermFetcher getCvTermFetcher() {
        return fetcher;
    }

    public void setCvTermEnricherListener(CvTermEnricherListener cvTermEnricherListener) {
        listener = cvTermEnricherListener;
    }

    public CvTermEnricherListener getCvTermEnricherListener() {
        return listener;
    }


    public void enrichCvTerm(CvTerm cvTermToEnrich) throws BridgeFailedException, MissingServiceException, BadToEnrichFormException, BadSearchTermException {
        cvTermFetched = fetchCvTerm(cvTermToEnrich);
        if(cvTermFetched == null){
            if(listener != null) listener.onCvTermEnriched(cvTermToEnrich, "Failed. No CvTerm could be found.");
            return;
        }

        processCvTerm(cvTermToEnrich);

        if(listener != null) listener.onCvTermEnriched(cvTermToEnrich, "Success. CvTerm minimum enriched.");
    }


    protected abstract void processCvTerm(CvTerm cvTermToEnrich);



    protected CvTerm fetchCvTerm(CvTerm cvTermToEnrich)
            throws BadToEnrichFormException, MissingServiceException,
            BridgeFailedException, BadSearchTermException {

        if(getCvTermFetcher() == null) throw new MissingServiceException("The CvTermFetcher was null.");
        if(cvTermToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null CvTerm.");

        CvTerm cvTermFetched = null;

        Collection<Xref> identifiersList = new ArrayList<Xref>();
        identifiersList.addAll(cvTermToEnrich.getIdentifiers());
        if(identifiersList.size() > 0){
            //Try with MI
            for(Xref identifierXref : identifiersList){
                if( cvTermFetched != null ) break;
                else if( identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MI_MI)){
                    cvTermFetched = getCvTermFetcher().getCvTermByID(
                            identifierXref.getId(), identifierXref.getDatabase());
                }
            }
            //Try with MOD
            if( cvTermFetched == null){
                for(Xref identifierXref : identifiersList){
                    if( cvTermFetched != null ) break;
                    else if( identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MOD_MI)){
                        cvTermFetched = getCvTermFetcher().getCvTermByID(
                                identifierXref.getId(), identifierXref.getDatabase());
                    }
                }
            }
            //Try with all other identifiers (ignoring if the database is one already checked.
            if( cvTermFetched == null){
                for(Xref identifierXref : identifiersList){
                    if( cvTermFetched != null ) break;
                    else if(! identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MOD_MI)
                            && ! identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MI_MI)){
                        cvTermFetched = getCvTermFetcher().getCvTermByID(
                                identifierXref.getId(), identifierXref.getDatabase());
                    }
                }
            }
        }

        //Todo - Allow for searching by fullname / shortname

        return cvTermFetched;
    }


}
