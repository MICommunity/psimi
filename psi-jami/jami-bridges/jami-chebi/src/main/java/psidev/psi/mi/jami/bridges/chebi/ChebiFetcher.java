package psidev.psi.mi.jami.bridges.chebi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import uk.ac.ebi.webservices.chebi.*;



/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcher
        implements BioactiveEntityFetcher {

    protected static final Logger log = LoggerFactory.getLogger(ChebiFetcher.class.getName());

    ChebiWebServiceService client;

    public ChebiFetcher(){
        client = new ChebiWebServiceService();
    }

    /**
     *
     * @param identifier    The identifier of the CHEBI entry to find.
     * @return              A completed bioactiveEntity for the given identifier. May be null.
     * @throws BridgeFailedException    Thrown if the fetcher encounters a problem.
     */
    public BioactiveEntity getBioactiveEntityByIdentifier (String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException("Can not fetch on null identifier");

        BioactiveEntity bioactiveEntity;
        try {
            Entity entity = client.getChebiWebServicePort().getCompleteEntity(identifier);

            if(entity == null) return null;

            //log.info("bioactiveEntity.setFullName() : "+entity.getChebiAsciiName() );
            bioactiveEntity = new DefaultBioactiveEntity( entity.getChebiAsciiName() );
            bioactiveEntity.setFullName( entity.getChebiAsciiName() );

            //log.info("bioactiveEntity.setChebi() : "+entity.getChebiId());
            bioactiveEntity.setChebi( entity.getChebiId() );

            //log.info("bioactiveEntity.setSmile() : "+entity.getSmiles() );
            bioactiveEntity.setSmile( entity.getSmiles() );

            //log.info( "bioactiveEntity.setStandardInchi() : "+entity.getInchi() );
            bioactiveEntity.setStandardInchi( entity.getInchi() );

            //log.info( "bioactiveEntity.setStandardInchiKey() : "+entity.getInchiKey() );
            bioactiveEntity.setStandardInchiKey( entity.getInchiKey() );

            //entity.getSecondaryChEBIIds()


            //log.info( "bioactiveEntity?");
            //log.info( entity.getSynonyms().toString() );

            /*System.out.println("GetName: " + entity.getChebiAsciiName());
            List<DataItem> synonyms = entity.getSynonyms();
            // List all synonyms
            for ( DataItem dataItem : synonyms ) {
                System.out.println("synonyms: " + dataItem.getData());
            }*/

        } catch ( ChebiWebServiceFault_Exception e ) {
            throw new BridgeFailedException(e);
        }

        return bioactiveEntity;
    }
}
