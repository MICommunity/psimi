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
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 30/07/13
 */
public class EuroPubmedCentralFetcher
        implements PublicationFetcher {


    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralTranslationUtil.class.getName());

    // private ResponseWrapper searchResult;
    private static final String IDENTIFIER_TYPE = "med";
    private static final String DATA_SET = "metadata";
    private static final String RESULT_TYPE = "core"; // "lite";
    private static final String EMAIL = "intact-dev@ebi.ac.uk";

    // int offSet = 0;
    // boolean synonym = false;


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

    private ResultList searchForMetaDataByPubmedID(String id) throws BridgeFailedException {
        final String query = "EXT_ID:" + id + " SRC:"+IDENTIFIER_TYPE;
        try {
            // SRC:med is needed as the external ids are not unique.
            // ex : extId1 coresponds to 2 publication in citexplore one from medline, one from CiteSeer.
            // Putting : core allow to get a lighter object just with the title, authors name...
            // we can choose between metadata for citations or full text for full text searches
            ResponseWrapper wrapper = getPort().searchPublications(query, DATA_SET , RESULT_TYPE, 0, false, EMAIL);
            //ResponseWrapper wrapper = getPort().searchPublications(query, "metadata", "lite" , 0, false, "intact-dev@ebi.ac.uk");

            return  wrapper.getResultList();//"core"
        } catch (QueryException_Exception e) {
            throw new BridgeFailedException("Problem fetching query: "+query, e);
        }
    }

    private ResultList searchForCrossReferencesByPubmedID(String id) throws BridgeFailedException {
        try {
            ResponseWrapper wrapper = getPort().getDatabaseLinks(id , IDENTIFIER_TYPE , null , 0 , EMAIL);

            return  wrapper.getResultList();
        } catch (QueryException_Exception e) {
            throw new BridgeFailedException("Problem fetching query", e);
        }
    }

    public Publication getPublicationByPubmedID(String id) throws BridgeFailedException{
        if(id == null || id.length() < 1)
            throw new IllegalArgumentException("Can not fetch on an empty identifier");

        List<Result> results = searchForMetaDataByPubmedID(id).getResult();

        Publication publication = new DefaultPublication();

        if (!results.isEmpty()) {
            EuroPubmedCentralTranslationUtil.
                    convertDataResultToPublication(publication, results.iterator().next());

            if(results.iterator().next().getHasDbCrossReferences().equals("Y")){
                try {
                    ResponseWrapper xrefResults = getPort().getDatabaseLinks(id, IDENTIFIER_TYPE, null, 0, EMAIL) ;
                    EuroPubmedCentralTranslationUtil.
                            convertXrefResultToPublication(publication, xrefResults);
                } catch (QueryException_Exception e) {
                    throw new BridgeFailedException(e);
                }
            }
        }


        /*ResultList resultsList = searchForCrossReferencesByPubmedID(pubmedID);
        //log.info("checking test "+resultsList.getResult().toString());
        if (resultsList != null && !resultsList.getResult().isEmpty()) {
            log.info("checking test "+resultsList.getResult().toString());
            EuroPubmedCentralTranslationUtil.convertXrefResultToPublication(publication, results.iterator().next());
        }*/

        return publication;
    }

    /*

    protected void runAllMethodsSingle(String id) throws QueryException_Exception {

        //String query = "HAS_UNIPROT:y HAS_FREE_FULLTEXT:y HAS_REFLIST:y HAS_XREFS:y sort_cited:y";
        final String query = "EXT_ID:" + id + " SRC:med";

        ResponseWrapper resultsBean = getPort().searchPublications(query, DATA_SET , RESULT_TYPE, offSet, synonym, EMAIL);

        List<Result> beanCollection = resultsBean.getResultList().getResult();

        for (Result bean : beanCollection) {
            printResultBean(bean);
                                     //DATABASE
            searchResult = getPort().getDatabaseLinks(bean.getPmid(), bean.getSource(), null , 0, EMAIL);
            printFirstDbCrossReference(searchResult);
        }
    }


    private void printResultBean(Result citation) {
        log.info("Title: " + citation.getTitle());
        DataHandler dataHandler = citation.getFullText();
        try {
            if (dataHandler != null) {
                log.info("");
                dataHandler.getInputStream().close();
                log.info("");
            }
        } catch (Exception e) {
        }
    }

    private void printFirstDbCrossReference(ResponseWrapper searchResult) {
        if(searchResult == null || searchResult.getDbCrossReferenceList() == null) return;

        for(DbCrossReference dbXref : searchResult.getDbCrossReferenceList().getDbCrossReference()){
            for(DbCrossReferenceInfo dbXrefInfo : dbXref.getDbCrossReferenceInfo()){
                // log.info(dbXref.getDbName()+": "+dbXrefInfo.getInfo1()+", "+dbXrefInfo.getInfo2()+", "+ dbXrefInfo.getInfo3()+", "+dbXrefInfo.getInfo4());
                Xref xref = null;
                if( dbXref.getDbName().equals("UNIPROT") ){
                    xref = XrefUtils.createUniprotIdentity(dbXrefInfo.getInfo1());
                } else if( dbXref.getDbName().equals("EMBL")){
                    xref = XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK , Xref.DDBJ_EMBL_GENBANK_MI , dbXrefInfo.getInfo1());
                } else if( dbXref.getDbName().equals("PDB") ){

                } else if( dbXref.getDbName().equals("INTERPRO") ){
                    xref = XrefUtils.createIdentityXref(Xref.INTERPRO , Xref.INTERPRO_MI , dbXrefInfo.getInfo1());

                } else if( dbXref.getDbName().equals("OMIN") ){

                } else if( dbXref.getDbName().equals("CHEBI") ){
                    xref = XrefUtils.createChebiIdentity(dbXrefInfo.getInfo1());
                } else if( dbXref.getDbName().equals("CHEMBL") ){

                } else if( dbXref.getDbName().equals("INTACT")){

                } else if( dbXref.getDbName().equals("ARXPR")){

                }
            }
        }
    } */
}
