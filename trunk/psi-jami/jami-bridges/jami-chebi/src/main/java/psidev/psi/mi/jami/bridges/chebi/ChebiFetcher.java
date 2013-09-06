package psidev.psi.mi.jami.bridges.chebi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.webservices.chebi.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Accesses Chebi entries using the WSDL SOAP service.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcher
        implements BioactiveEntityFetcher {

    public static final String IUPAC_MI = "MI:2007";
    public static final String IUPAC = "iupac name";

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
    public BioactiveEntity fetchBioactiveEntityByIdentifier (String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException("Can not fetch on null identifier");

        BioactiveEntity bioactiveEntity;
        try {
            Entity entity = client.getChebiWebServicePort().getCompleteEntity(identifier);
            if(entity == null) return null;

            // == Short name / Full name
            bioactiveEntity = new DefaultBioactiveEntity(entity.getChebiAsciiName());
            // bioactiveEntity.setFullName( entity.getChebiAsciiName() );

            // == Chebi ID
            bioactiveEntity.setChebi( entity.getChebiId() );

            // == Secondary CHEBI IDs
            for(String secondaryId : entity.getSecondaryChEBIIds()){
                bioactiveEntity.getIdentifiers().add(XrefUtils.createChebiSecondary(secondaryId));
            }

            // == Smile
            bioactiveEntity.setSmile( entity.getSmiles() );

            // == Inchi / Inchi Key
            bioactiveEntity.setStandardInchi( entity.getInchi() );
            bioactiveEntity.setStandardInchiKey( entity.getInchiKey() );

            // == SYNONYMS
            for(DataItem syn : entity.getSynonyms()){
                bioactiveEntity.getAliases().add(AliasUtils.createAlias(
                        Alias.SYNONYM , Alias.SYNONYM_MI , syn.getData()));
            }

            // == IUPAC names
            for(DataItem syn : entity.getIupacNames()){
                bioactiveEntity.getAliases().add(AliasUtils.createAlias(
                        IUPAC , IUPAC_MI , syn.getData()));
            }

            //UNUSED FIELDS
            /*for(DataItem syn : entity.getDatabaseLinks()){
                log.info("LIN: ["+syn.getData()+"] ty ["+syn.getType()+"]");
            } */


        } catch ( ChebiWebServiceFault_Exception e ) {
            throw new BridgeFailedException(e);
        }
        return bioactiveEntity;
    }

    @Override
    public Collection<BioactiveEntity> fetchBioactiveEntitiesByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        Collection<BioactiveEntity> results = new ArrayList<BioactiveEntity>();
        for(String id : identifiers){
            results.add(fetchBioactiveEntityByIdentifier(id));
        }
        return results;
    }
}
