package psidev.psi.mi.calimocho.solr.converter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

/**
 *
 * @author kbreuer
 */
public class Converter {

    private Map<SolrFieldName, Collection<String>> keyMap;

    public Converter() {
        keyMap = new HashMap<SolrFieldName, Collection<String>>();

        keyMap.put(SolrFieldName.idA, Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A));
        keyMap.put(SolrFieldName.idB, Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B));
        keyMap.put(SolrFieldName.id, Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B));
        keyMap.put(SolrFieldName.alias, Arrays.asList(InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B));
        keyMap.put(SolrFieldName.identifier, Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B, InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B));
        keyMap.put(SolrFieldName.pubid, Arrays.asList(InteractionKeys.KEY_PUBID));
        keyMap.put(SolrFieldName.pubauth, Arrays.asList(InteractionKeys.KEY_PUBAUTH));
        keyMap.put(SolrFieldName.taxidA, Arrays.asList(InteractionKeys.KEY_TAXID_A));
        keyMap.put(SolrFieldName.taxidB, Arrays.asList(InteractionKeys.KEY_TAXID_B));
        keyMap.put(SolrFieldName.species, Arrays.asList(InteractionKeys.KEY_TAXID_A, InteractionKeys.KEY_TAXID_B));
        keyMap.put(SolrFieldName.type, Arrays.asList(InteractionKeys.KEY_INTERACTION_TYPE));
        keyMap.put(SolrFieldName.detmethod, Arrays.asList(InteractionKeys.KEY_DETMETHOD));
        keyMap.put(SolrFieldName.interaction_id, Arrays.asList(InteractionKeys.KEY_INTERACTION_ID));
        keyMap.put(SolrFieldName.pbioroleA, Arrays.asList(InteractionKeys.KEY_BIOROLE_A));
        keyMap.put(SolrFieldName.pbioroleB, Arrays.asList(InteractionKeys.KEY_BIOROLE_B));
        keyMap.put(SolrFieldName.pbiorole, Arrays.asList(InteractionKeys.KEY_BIOROLE_A,InteractionKeys.KEY_BIOROLE_B));
        keyMap.put(SolrFieldName.ptypeA, Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A));
        keyMap.put(SolrFieldName.ptypeB, Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_B));
        keyMap.put(SolrFieldName.ptype, Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A, InteractionKeys.KEY_INTERACTOR_TYPE_B));
        keyMap.put(SolrFieldName.pxrefA, Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A));
        keyMap.put(SolrFieldName.pxrefB, Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B));
        keyMap.put(SolrFieldName.pxref, Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A,InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B));
        keyMap.put(SolrFieldName.xref, Arrays.asList(InteractionKeys.KEY_XREFS_I, InteractionKeys.KEY_INTERACTION_ID));
        keyMap.put(SolrFieldName.annot, Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_I));
        keyMap.put(SolrFieldName.udate, Arrays.asList(InteractionKeys.KEY_UPDATE_DATE));
        keyMap.put(SolrFieldName.negative, Arrays.asList(InteractionKeys.KEY_NEGATIVE));
        keyMap.put(SolrFieldName.complex, Arrays.asList(InteractionKeys.KEY_EXPANSION));
        keyMap.put(SolrFieldName.ftypeA, Arrays.asList(InteractionKeys.KEY_FEATURE_A));
        keyMap.put(SolrFieldName.ftypeB, Arrays.asList(InteractionKeys.KEY_FEATURE_B));
        keyMap.put(SolrFieldName.ftype, Arrays.asList(InteractionKeys.KEY_FEATURE_A, InteractionKeys.KEY_FEATURE_B));
        keyMap.put(SolrFieldName.pmethodA, Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A));
        keyMap.put(SolrFieldName.pmethodB, Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_B));
        keyMap.put(SolrFieldName.stc, Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A,InteractionKeys.KEY_STOICHIOMETRY_B));
        keyMap.put(SolrFieldName.param, Arrays.asList(InteractionKeys.KEY_PARAMETERS_I));

    }

    /*
     * loop over solr field names, get interaction keys, retrieve fields for each interaction key and add it to solr input document
     *
     */
    public SolrInputDocument toSolrDocument(Row row) throws SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        
        for (Map.Entry<SolrFieldName,Collection<String>> entry : keyMap.entrySet()) {

            for (String key : entry.getValue()) {

                Collection<Field> fields = row.getFields(key);

                for (Field field : fields) {

                    

                }
            }

        }

        return doc;
    }

}
