package psidev.psi.mi.jami.bridges.chebi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import uk.ac.ebi.webservices.chebi.*;



/**
 * Accesses Chebi entries using the WSDL SOAP service.
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
     * Searches Chebi for an entry matching the identifier.
     * If it's found, the record is used to create a bioactiveEntity with:
     * ChebiAsciiName = Full name, Short name
     * with Inchi, InchiKey, Smile, ChebiId matched to the corresponding fields.
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

            // Short name / Full name
            bioactiveEntity = new DefaultBioactiveEntity(entity.getChebiAsciiName());
            // bioactiveEntity.setFullName( entity.getChebiAsciiName() );

            // Chebi ID
            bioactiveEntity.setChebi( entity.getChebiId() );
            // Smile
            bioactiveEntity.setSmile( entity.getSmiles() );
            // Inchi code
            bioactiveEntity.setStandardInchi( entity.getInchi() );
            // Inchi Key
            bioactiveEntity.setStandardInchiKey( entity.getInchiKey() );

            //UNUSED FIELDS
            for(DataItem syn : entity.getDatabaseLinks()){
                log.info("LIN: ["+syn.getData()+"] ty ["+syn.getType()+"]");
            }
           /* for(DataItem syn : entity.getSynonyms()){
                log.info("SYN: "+syn.getData());
            }
            for(String sec : entity.getSecondaryChEBIIds()){
                log.info("SEC: "+sec);
            }*/

        } catch ( ChebiWebServiceFault_Exception e ) {
            throw new BridgeFailedException(e);
        }
        return bioactiveEntity;
    }
}
