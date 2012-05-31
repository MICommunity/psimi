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
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.tab.io.formatter.AnnotationFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.BooleanFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.DateFieldFormatter;
//import org.hupo.psi.calimocho.tab.io.formatter.LiteralFieldFormatter;
//import org.hupo.psi.calimocho.tab.io.formatter.PositiveFloatFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.XrefFieldFormatter;

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
        XrefFieldConverter xrefConverter = new XrefFieldConverter();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();
        TextFieldConverter textConverter = new TextFieldConverter();
        XrefFieldFormatter textFormatter = new XrefFieldFormatter();
        DateFieldConverter dateConverter = new DateFieldConverter();
        DateFieldFormatter dateFormatter = new DateFieldFormatter("yyyy/MM/dd");
        AnnotationFieldConverter annotConverter = new AnnotationFieldConverter();
        AnnotationFieldFormatter annotFormatter = new AnnotationFieldFormatter();
        BooleanFieldConverter boolConverter = new BooleanFieldConverter();
        BooleanFieldFormatter boolFormatter = new BooleanFieldFormatter();
//        LiteralFieldFormatter literalFormatter = new LiteralFieldFormatter();
//        PositiveFloatFieldFormatter floatFormatter = new PositiveFloatFieldFormatter();
        boolean stored = true; //store cell content as is (which means formatted by Calimocho)

        keyMap.put(SolrFieldName.idA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.idB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.alias, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.identifier, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B, InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pubid, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBID), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pubauth, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBAUTH), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.taxidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.taxidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.species, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A, InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.type, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_TYPE), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.detmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_DETMETHOD),textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.interaction_id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_ID), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pbioroleA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pbioroleB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pbiorole, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A,InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ptypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ptypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ptype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A, InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pxrefA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pxrefB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pxref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A,InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.xref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_XREFS_I, InteractionKeys.KEY_INTERACTION_ID), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.annot, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_I), annotConverter, annotFormatter, stored));
        keyMap.put(SolrFieldName.udate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_UPDATE_DATE), dateConverter, dateFormatter, stored));
        keyMap.put(SolrFieldName.negative, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_NEGATIVE), boolConverter, boolFormatter, stored));
        keyMap.put(SolrFieldName.complex, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPANSION), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ftypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ftypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ftype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A, InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pmethodA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pmethodB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.stc, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A,InteractionKeys.KEY_STOICHIOMETRY_B), boolConverter, boolFormatter, stored));
        keyMap.put(SolrFieldName.param, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PARAMETERS_I), boolConverter, boolFormatter, stored));
        
    }

    /*
     * reads a MITAB line and converts it into Solr index fields
     */
    public SolrInputDocument toSolrDocument(Row row) throws SolrServerException, IllegalFieldException {
        SolrInputDocument doc = new SolrInputDocument();
        
        for (Map.Entry<SolrFieldName,SolrFieldUnit> entry : keyMap.entrySet()) {

            SolrFieldName solrFieldName = entry.getKey();
            SolrFieldUnit solrField = entry.getValue();
            Collection<String> rowKeys = solrField.getRowKeys();
            SolrFieldConverter converter = solrField.getConverter();

            if (rowKeys != null && converter != null && !rowKeys.isEmpty()){
                for (String key : rowKeys) {

                    Collection<Field> fields = row.getFields(key);

                    if (fields != null && !fields.isEmpty()){
                        for (Field field : fields) {
                            String formattedField = solrField.getFormatter().format(field);
                            converter.indexFieldValues(field, formattedField, solrFieldName, doc, solrField.isStored());
                        }
                    }
                }
            }
        }

        return doc;
    }

}
