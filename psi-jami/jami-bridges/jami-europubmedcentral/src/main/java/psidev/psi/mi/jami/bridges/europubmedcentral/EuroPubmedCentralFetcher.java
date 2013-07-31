package psidev.psi.mi.jami.bridges.europubmedcentral;

import psidev.psi.mi.jami.bridges.europubmedcentral.util.EuroPubmedCentralTranslationUtil;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import uk.ac.ebi.cdb.webservice.*;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 30/07/13
 */
public class EuroPubmedCentralFetcher
        implements PublicationFetcher {

    private WSCitationImplService service;

    public EuroPubmedCentralFetcher(){
        this("http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl");
    }

    public EuroPubmedCentralFetcher(String wsdlUrl){
        try {
            service = new WSCitationImplService(new URL(wsdlUrl), new QName("http://webservice.cdb.ebi.ac.uk/", "WSCitationImplService"));
        } catch (MalformedURLException e) {
            throw new RuntimeException( e );
        }
    }

    private WSCitationImpl getPort() {
        return service.getWSCitationImplPort();
    }

    private ResultList searchCitationsByExternalId(String id) throws BridgeFailedException {
        final String query = "EXT_ID:" + id + " SRC:med";
        try {
            // SRC:med is needed as the external ids are not unique.
            // ex : extId1 coresponds to 2 publication in citexplore one from medline, one from CiteSeer.
            // Putting : core allow to get a lighter object just with the title, authors name...
            // we can choose between metadata for citations or full text for full text searches
            ResponseWrapper wrapper = getPort().searchPublications(query, "metadata","core", 0, false, "intact-dev@ebi.ac.uk");

            return  wrapper.getResultList();//"core"
        } catch (QueryException_Exception e) {
            throw new BridgeFailedException("Problem fetching query: "+query, e);
        }
    }

    /*public static void main(String[] args) throws Exception{
        CitexploreClient client = new CitexploreClient();
        Result c = client.getCitationById("1");

        System.out.println(c.getJournalInfo());
        System.out.println(c.getJournalInfo().getYearOfPublication());

        System.out.println(client.getCitationById("1234567").getTitle());
    }  */



    public Publication getPublicationByPubmedID(String pubmedID) throws BridgeFailedException{

        List<Result> results = searchCitationsByExternalId(pubmedID).getResult();

        if (!results.isEmpty()) {
            return EuroPubmedCentralTranslationUtil.convertResultToPublication(results.iterator().next());
        }

        return null;
    }


}
