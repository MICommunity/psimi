package uk.ac.ebi.intact.jami.imex.actions.impl;

import edu.ucla.mbi.imex.central.ws.v20.IcentralFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.imex.actions.ImexCentralPublicationRegister;

/**
 * This class can register a publication in IMEx central and collect a publication record in IMEx central using imex central webservice
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/03/12</pre>
 */

public class ImexCentralPublicationRegisterImpl implements ImexCentralPublicationRegister{

    private static final Log log = LogFactory.getLog(ImexCentralPublicationRegisterImpl.class);
    private ImexCentralClient imexCentral;

    /**
     *
     * @param publicationId : valid pubmed id or doi number or IMEx id or unassigned identifier (internal identifier)
     * @return the registered publication in IMEx central matching the publication id of the publication. Null if the publication has not been registered
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException
     */
    public Publication getExistingPublicationInImexCentral(String publicationId, String source) throws BridgeFailedException {

        if (publicationId != null && source != null){
            return imexCentral.fetchByIdentifier(publicationId, source);
        }
        return null;
    }

    /**
     * @param publication
     * @return the record created in IMEx central, null if it could not be created in IMEx central
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException
     */
    public Publication registerPublicationInImexCentral(Publication publication) throws BridgeFailedException{
        // create a new publication record in IMEx central
        psidev.psi.mi.jami.model.Publication newPublication = null;
        String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
        String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publication.getIdentifiers().isEmpty()){
            Xref id = publication.getXrefs().iterator().next();
            source = id.getDatabase().getShortName();
            pubId = id.getId();
        }

        try {
            newPublication = imexCentral.createPublicationById(pubId, source);
            log.info("Registered publication : " + pubId + " in IMEx central.");

            return newPublication;
        } catch (BridgeFailedException e) {
            IcentralFault f = (IcentralFault) e.getCause().getCause();
            // IMEx central throw an Exception when the record cannot be created
            if( f.getFaultInfo().getFaultCode() == ImexCentralClient.NO_RECORD_CREATED ) {
                log.error("Cannot create a new record in IMEx central for publication " + pubId, e);
                return null;
            }
            else {
                throw e;
            }
        }
    }

    public ImexCentralClient getImexCentralClient() {
        return imexCentral;
    }

    public void setImexCentralClient(ImexCentralClient imexCentral) {
        this.imexCentral = imexCentral;
    }
}
