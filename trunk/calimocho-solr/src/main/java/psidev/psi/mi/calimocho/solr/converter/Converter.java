package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kbreuer
 */
public class Converter {

    private Map<SolrFieldName, SolrFieldUnit> keyMap;

    public Converter() {
        keyMap = new HashMap<SolrFieldName, SolrFieldUnit>();

        initializeKeyMap();
    }

    private void initializeKeyMap() {
        keyMap.put(SolrFieldName.idA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.idB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.alias, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.identifier, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B, InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), new XrefFieldConverter()));

        keyMap.put(SolrFieldName.pubid, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBID), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.pubauth, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBAUTH), new TextFieldConverter()));
        keyMap.put(SolrFieldName.taxidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A), new TextFieldConverter()));
        keyMap.put(SolrFieldName.taxidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.species, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A, InteractionKeys.KEY_TAXID_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.type, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_TYPE), new TextFieldConverter()));
        keyMap.put(SolrFieldName.detmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_DETMETHOD), new TextFieldConverter()));
        keyMap.put(SolrFieldName.interaction_id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_ID), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.pbioroleA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A), new TextFieldConverter()));
        keyMap.put(SolrFieldName.pbioroleB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.pbiorole, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A,InteractionKeys.KEY_BIOROLE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ptypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ptypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ptype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A, InteractionKeys.KEY_INTERACTOR_TYPE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.pxrefA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.pxrefB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.pxref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A,InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.xref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_XREFS_I, InteractionKeys.KEY_INTERACTION_ID), new XrefFieldConverter()));
        keyMap.put(SolrFieldName.annot, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_I), new AnnotationFieldConverter()));
        keyMap.put(SolrFieldName.udate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_UPDATE_DATE), new DateFieldConverter()));
        keyMap.put(SolrFieldName.negative, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_NEGATIVE), new BooleanFieldConverter()));
        keyMap.put(SolrFieldName.complex, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPANSION), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ftypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ftypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.ftype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A, InteractionKeys.KEY_FEATURE_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.pmethodA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A), new TextFieldConverter()));
        keyMap.put(SolrFieldName.pmethodB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_B), new TextFieldConverter()));
        keyMap.put(SolrFieldName.stc, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A,InteractionKeys.KEY_STOICHIOMETRY_B), new BooleanFieldConverter()));
        keyMap.put(SolrFieldName.param, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PARAMETERS_I), new BooleanFieldConverter()));
        
    }

    /*
     * loop over solr field names, get interaction keys, retrieve fields for each interaction key and add it to solr input document
     *
     */
    public SolrInputDocument toSolrDocument(Row row) throws SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        
        for (Map.Entry<SolrFieldName,SolrFieldUnit> entry : keyMap.entrySet()) {

            Collection<String> rowKeys = entry.getValue().getRowKeys();
            SolrFieldConverter converter = entry.getValue().getConverter();

            if (rowKeys != null && converter != null && !rowKeys.isEmpty()){
                for (String key : rowKeys) {

                    Collection<Field> fields = row.getFields(key);

                    if (fields != null && !fields.isEmpty()){
                        for (Field field : fields) {
                            converter.indexFieldValues(field, entry.getKey(), doc);
                        }
                    }
                }
            }

        }

        return doc;
    }

}
