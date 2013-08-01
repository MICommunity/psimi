package psidev.psi.mi.jami.bridges.europubmedcentral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.europubmedcentral.util.EuroPubmedCentralTranslationUtil;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

import psidev.psi.mi.jami.model.impl.DefaultPublication;
import uk.ac.ebi.cdb.webservice.*;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Uses the EuroPubmedCentral WSDL service to fetch publication entries.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 30/07/13
 */
public class EuroPubmedCentralFetcher
        implements PublicationFetcher {

    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralFetcher.class.getName());

    private static final String IDENTIFIER_TYPE = "med";
    private static final String DATA_SET = "metadata";
    private static final String RESULT_TYPE = "core"; // "lite";
    private static final String EMAIL = "intact-dev@ebi.ac.uk";

    private WSCitationImplService service;


    public EuroPubmedCentralFetcher() throws BridgeFailedException {
        this("http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl");
    }

    private EuroPubmedCentralFetcher(String wsdlUrl) throws BridgeFailedException {
        try {
            service = new WSCitationImplService(new URL(wsdlUrl), new QName("http://webservice.cdb.ebi.ac.uk/", "WSCitationImplService"));
        } catch (MalformedURLException e) {
            throw new BridgeFailedException("Unable to initiate the publication fetcher.", e );
        }
    }

    private WSCitationImpl getPort() {
        return service.getWSCitationImplPort();
    }


    /**
     * Queries the EuroPubmedCentral WSDL service for the meta data.
     * A second query is made to gather Xrefs if the meta data shows they exist.
     * @param id    the pubmedID of the publication
     * @return      a completed publication record.
     * @throws BridgeFailedException
     */
    public Publication getPublicationByPubmedID(String id) throws BridgeFailedException{
        if(id == null || id.length() < 1)
            throw new IllegalArgumentException("Can not fetch on an empty identifier");


        Collection<Result> results = Collections.EMPTY_LIST;

        final String query = "EXT_ID:" + id + " SRC:"+IDENTIFIER_TYPE;
        try {
            ResponseWrapper wrapper = getPort().searchPublications(query, DATA_SET , RESULT_TYPE, 0, false, EMAIL);

            if(wrapper.getResultList() != null)
                results = wrapper.getResultList().getResult();
        } catch (QueryException_Exception e) {
            throw new BridgeFailedException("Problem fetching query: "+query, e);
        }

        Publication publication = new DefaultPublication();

        if (!results.isEmpty()) {
            Result entry = results.iterator().next();

            EuroPubmedCentralTranslationUtil.
                    convertDataResultToPublication(publication, entry);

            if( entry.getHasDbCrossReferences().equals("Y") ){
                try {
                    ResponseWrapper xrefResults = getPort().getDatabaseLinks(id, IDENTIFIER_TYPE, null, 0, EMAIL) ;
                    EuroPubmedCentralTranslationUtil.
                            convertXrefResultToPublication(publication, xrefResults);
                } catch (QueryException_Exception e) {
                    throw new BridgeFailedException(e);
                }
            }
        }

        return publication;
    }
}
