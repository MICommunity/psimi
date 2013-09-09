package psidev.psi.mi.jami.bridges.chebi;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.ListUtils;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.chebi.webapps.chebiWS.model.OntologyDataItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Accesses Chebi entries using the WSDL SOAP service.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class ChebiFetcher
        implements BioactiveEntityFetcher {

    private ChebiWebServiceClient client;
    private static final int MAX_SIZE_CHEBI_IDS = 50;
    public final static String CHEBI_POLYSACCHARYDE_PARENT="CHEBI:18154";

    public ChebiFetcher(){
        client = new ChebiWebServiceClient();
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

        BioactiveEntity bioactiveEntity = null;
        try {
            Entity entity = client.getCompleteEntity(identifier);
            if(entity == null) return null;
            bioactiveEntity = createNewBioactiveEntity(entity);

        } catch ( ChebiWebServiceFault_Exception e ) {
            throw new BridgeFailedException("Cannot fetch the bioactive entity from CHEBI",e);
        }
        return bioactiveEntity;
    }

    private BioactiveEntity createNewBioactiveEntity(Entity entity) {

        CvTerm entityType = retrieveMoleculeTypeFrom(entity);

        BioactiveEntity bioactiveEntity = new DefaultBioactiveEntity(entity.getChebiAsciiName(), entity.getChebiAsciiName(), entityType);

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
                    Alias.SYNONYM, Alias.SYNONYM_MI, syn.getData()));
        }

        // == IUPAC names
        for(DataItem syn : entity.getIupacNames()){
            bioactiveEntity.getAliases().add(AliasUtils.createAlias(
                    Alias.IUPAC ,  Alias.IUPAC_MI , syn.getData()));
        }
        return bioactiveEntity;
    }

    private CvTerm retrieveMoleculeTypeFrom(Entity entity) {
        CvTerm entityType = null;
        if (entity.getChebiId().equals(CHEBI_POLYSACCHARYDE_PARENT)){
            entityType = CvTermUtils.createMICvTerm(BioactiveEntity.POLYSACCHARIDE, BioactiveEntity.POLYSACCHARIDE_MI);
        }
        else{
            List<OntologyDataItem> parents = entity.getOntologyParents();
            if (parents != null && !parents.isEmpty()){
                for (OntologyDataItem item : parents){
                    if (item.getChebiId().equals(CHEBI_POLYSACCHARYDE_PARENT)){
                        entityType = CvTermUtils.createMICvTerm(BioactiveEntity.POLYSACCHARIDE, BioactiveEntity.POLYSACCHARIDE_MI);
                        break;
                    }
                }

                if (entityType == null){
                    entityType = CvTermUtils.createMICvTerm(BioactiveEntity.SMALL_MOLECULE, BioactiveEntity.SMALL_MOLECULE_MI);
                }
            }
            else{
                entityType = CvTermUtils.createMICvTerm(BioactiveEntity.SMALL_MOLECULE, BioactiveEntity.SMALL_MOLECULE_MI);
            }
        }
        return entityType;
    }

    public Collection<BioactiveEntity> fetchBioactiveEntitiesByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {

        if (identifiers == null) {
            throw new IllegalArgumentException("The collection of identifiers cannot be null");
        }
        Collection<BioactiveEntity> results = new ArrayList<BioactiveEntity>();
        List<Entity> entities = null;

        List<List<String>> parts = ListUtils.splitter(new ArrayList<String>(identifiers), MAX_SIZE_CHEBI_IDS);

        for (List<String> part : parts) {
            try {
                //If we have the same Id in the list, we will have only one Entity
                entities = client.getCompleteEntityByList(part);

                if (entities != null && !entities.isEmpty()) {
                    for (Entity entity : entities) {
                        results.add(createNewBioactiveEntity(entity));
                    }
                }

            } catch (ChebiWebServiceFault_Exception e) {
                throw new BridgeFailedException("Cannot fetch the bioactive entity from CHEBI",e);
            }
            entities.clear();
        }

        return results;
    }
}
