package psidev.psi.mi.jami.imex.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.imex.PublicationStatus;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.impl.log.PublicationEnricherLogger;
import psidev.psi.mi.jami.imex.listener.PublicationImexEnricherListener;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 */
public class PublicationImexEnricherLogger extends PublicationEnricherLogger implements PublicationImexEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(PublicationImexEnricherLogger.class.getName());

    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
        log.info(publication.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Publication object, String message, Exception e) {
        log.error(object.toString() + " enrichment error, message: " + message, e);    }


    public void onImexIdConflicts(Publication originalPublication, Collection<Xref> conflictingXrefs) {
        log.error("The publication "+originalPublication+" has "+conflictingXrefs.size()+" IMEx primary references and only one is allowed.");
    }

    public void onMissingImexId(Publication publication) {
        log.error("The publication "+publication+" does not have a IMEx primary reference and it was expected to process with the enrichment.");
    }

    public void onCurationDepthUpdated(Publication publication, CurationDepth oldDepth) {
        log.info("The curation deppth of the publication has been updated from "+oldDepth+" to "+publication.getCurationDepth());
    }

    public void onImexAdminGroupUpdated(Publication publication, Source oldSource) {
        log.info("The admin group of the publication "+publication+" has been updated in IMEx central");
    }

    public void onImexStatusUpdated(Publication publication, PublicationStatus oldStatus) {
        log.info("The status of the publication "+publication+" has been updated in IMEx central");
    }

    public void onImexPublicationIdentifierSynchronized(Publication publication) {
        log.info("The identifiers of the publication "+publication+" have been synchronized in IMEx central");
    }

    public void onPublicationAlreadyRegisteredInImexCentral(Publication publication, String imex) {
        log.error("The publication "+publication+" is already registered in IMEx central with id "+imex);
    }

    public void onPublicationRegisteredInImexCentral(Publication publication) {
        log.info("The publication "+publication+" has been registered in IMEx central.");
    }

    public void onPublicationWhichCannotBeRegistered(Publication publication) {
        log.error("The publication "+publication+" cannot be registered in IMEx central.");
    }

    public void onPublicationNotEligibleForImex(Publication publication) {
        log.error("The publication "+publication+" cannot be registered in IMEx central.");
    }

    public void onImexIdAssigned(Publication publication, String imex) {
        log.info("The IMEx identifier "+imex+" has been assigned to the publication "+publication);
    }

    public void onImexIdNotRecognized(Publication publication, String imex) {
        log.error("The publication "+publication+" does have an IMEx identifier which is not recognized in IMEx central "+imex);
    }
}
