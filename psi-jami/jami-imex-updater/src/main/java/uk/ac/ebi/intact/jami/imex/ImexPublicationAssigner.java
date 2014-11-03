package uk.ac.ebi.intact.jami.imex;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullPublicationEnricher;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;
import uk.ac.ebi.intact.jami.imex.actions.ImexAssigner;
import uk.ac.ebi.intact.jami.imex.actions.ImexCentralPublicationRegister;
import uk.ac.ebi.intact.jami.imex.listener.PublicationImexEnricherListener;

import java.util.regex.Pattern;

/**
 * This enricher will update a publication having IMEx id and synchronize it with IMEx central
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/14</pre>
 */

public class ImexPublicationAssigner extends FullPublicationEnricher{

    private ImexAssigner imexAssigner;
    private ImexCentralPublicationRegister publicationRegister;
    private ImexPublicationUpdater publicationUpdater;

    public ImexPublicationAssigner(ImexCentralClient fetcher) {
        super(fetcher);
    }

    @Override
    protected void processCurationDepth(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processCurationDepth(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processReleasedDate(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processReleasedDate(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processXrefs(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processXrefs(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processAnnotations(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processAnnotations(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processJournal(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processJournal(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processPublicationTitle(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processPublicationDate(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processPublicationDate(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processAuthors(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processAuthors(publicationToEnrich, fetched);
        }
    }

    @Override
    protected void processIdentifiers(Publication publicationToEnrich, Publication fetched) {
        if (getPublicationUpdater() != null){
            getPublicationUpdater().processIdentifiers(publicationToEnrich, fetched);
        }
    }


    @Override
    public ImexPublication find(Publication publicationToEnrich) throws EnricherException {
        String pubId = publicationToEnrich.getPubmedId() != null ? publicationToEnrich.getPubmedId() : publicationToEnrich.getDoi();
        String source = publicationToEnrich.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publicationToEnrich.getIdentifiers().isEmpty()) {
            Xref id = publicationToEnrich.getXrefs().iterator().next();
            source = id.getDatabase().getShortName();
            pubId = id.getId();
        }

        if(source != null && pubId != null){
            ImexPublication imexPublication = fetchImexPublication(pubId, source);

            // the publication is already registered in IMEx central
            if (imexPublication != null){

                // we already have an IMEx id in IMEx central, it is not possible to update publication because we have a conflict
                if (imexPublication.getImexId() != null || imexPublication.getOwner() == null){
                    if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                        ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationAlreadyRegisteredInImexCentral(publicationToEnrich, imexPublication.getImexId());
                    }
                    return null;
                }
                // the publication has been registered in IMex central but does not have an IMEx id. We cannot assign IMEx id, the curator must have a look at it
                else if (!isEntitledToAssignImexId(publicationToEnrich, imexPublication)){
                    if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                        ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationAlreadyRegisteredInImexCentral(publicationToEnrich, imexPublication.getImexId());
                        ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationNotEligibleForImex(publicationToEnrich);
                    }
                    return null;
                }
            }

            // the publication has a valid pubmed identifier and can be registered and assign IMEx id in IMEx central
            if (Pattern.matches(ImexCentralClient.pubmed_regexp.toString(), pubId)) {
                if (imexPublication == null && getPublicationRegister() != null){
                    try {
                        imexPublication = (ImexPublication)getPublicationRegister().registerPublicationInImexCentral(publicationToEnrich);
                        if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                            ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationRegisteredInImexCentral(publicationToEnrich);
                        }
                    } catch (BridgeFailedException e) {
                        throw new EnricherException("Cannot register Publication "+publicationToEnrich);
                    }
                }

                if (imexPublication != null){
                    // assign IMEx id to publication and update publication annotations
                    try {
                        imexPublication = (ImexPublication)getImexAssigner().assignImexIdentifier(publicationToEnrich, imexPublication);
                        if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                            ((PublicationImexEnricherListener)getPublicationEnricherListener()).onImexIdAssigned(publicationToEnrich, publicationToEnrich.getImexId());
                        }
                    } catch (BridgeFailedException e) {
                        throw new EnricherException("Cannot assign IMEx id to Publication "+publicationToEnrich);
                    }
                }
                else {
                    if (getPublicationEnricherListener() instanceof PublicationImexEnricherListener){
                        ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationWhichCannotBeRegistered(publicationToEnrich);
                    }
                    else{
                        getPublicationEnricherListener().onEnrichmentError(publicationToEnrich, "The publication cannot be registered in IMEx central", null);
                    }
                }

                return imexPublication;
            }
            // unassigned publication, cannot use the webservice to automatically assign IMEx id for now, ask the curator to manually register and assign IMEx id to this publication
            else {
                ((PublicationImexEnricherListener)getPublicationEnricherListener()).onPublicationNotEligibleForImex(publicationToEnrich);
                return null;
            }
        }

        return null;
    }

    protected boolean isEntitledToAssignImexId(Publication publicationToEnrich, ImexPublication imexPublication) throws EnricherException {

        Source source = publicationToEnrich.getSource();
        return source != null && DefaultCvTermComparator.areEquals(source, imexPublication.getSource());
    }

    public ImexAssigner getImexAssigner() {
        return imexAssigner;
    }

    public void setImexAssigner(ImexAssigner imexAssigner) {
        this.imexAssigner = imexAssigner;
    }

    public ImexCentralPublicationRegister getPublicationRegister() {
        return publicationRegister;
    }

    public void setPublicationRegister(ImexCentralPublicationRegister publicationRegister) {
        this.publicationRegister = publicationRegister;
    }

    public ImexPublicationUpdater getPublicationUpdater() {
        return publicationUpdater;
    }

    public void setPublicationUpdater(ImexPublicationUpdater publicationUpdater) {
        this.publicationUpdater = publicationUpdater;
    }

    private ImexPublication fetchImexPublication(String id, String db) throws EnricherException {
        try {
            return (ImexPublication)getPublicationFetcher().fetchByIdentifier(id, db);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < getRetryCount()){
                try {
                    return (ImexPublication)getPublicationFetcher().fetchByIdentifier(id, db);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ getRetryCount() +" times to fetch the Publication but cannot connect to the fetcher.", e);
        }
    }
}


