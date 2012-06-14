package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.solr.common.SolrInputField;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.DefaultRow;
import org.hupo.psi.calimocho.tab.io.formatter.AnnotationFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.BooleanFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.DateFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.PositiveFloatFieldFormatter;
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
        AnnotationFieldFormatter annotFormatter = new AnnotationFieldFormatter(":");
//        SingleBooleanFieldConverter singleBoolConverter = new SingleBooleanFieldConverter();
        BooleanFieldConverter boolConverter = new BooleanFieldConverter();
        BooleanFieldFormatter boolFormatter = new BooleanFieldFormatter();
        LiteralFieldFormatter literalFormatter = new LiteralFieldFormatter();
        PositiveFloatFieldFormatter floatFormatter = new PositiveFloatFieldFormatter();
        FloatFieldConverter floatConverter = new FloatFieldConverter();
        boolean stored = true; //fn + fn_s + fn_o (fields in MIQL2.7)

        keyMap.put(SolrFieldName.idA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.idB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.altidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALTID_A), xrefConverter, xrefFormatter, !stored));
        keyMap.put(SolrFieldName.altidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, !stored));
//        keyMap.put(SolrFieldName.id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.aliasA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A), xrefConverter, xrefFormatter, !stored));
        keyMap.put(SolrFieldName.aliasB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, !stored));
//        keyMap.put(SolrFieldName.alias, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, stored));
//        keyMap.put(SolrFieldName.identifier, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B, InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pubid, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBID), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pubauth, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBAUTH), textConverter, literalFormatter, stored));
        keyMap.put(SolrFieldName.taxidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.taxidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.taxidHost, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_HOST_ORGANISM), textConverter, textFormatter, !stored));
//        keyMap.put(SolrFieldName.species, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A, InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.type, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_TYPE), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.detmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_DETMETHOD),textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.interaction_id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_ID), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pbioroleA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pbioroleB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, stored));
//        keyMap.put(SolrFieldName.pbiorole, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A,InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pexproleA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPROLE_A), textConverter, textFormatter, !stored));
        keyMap.put(SolrFieldName.pexproleB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPROLE_B), textConverter, textFormatter, !stored));
        keyMap.put(SolrFieldName.ptypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ptypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, stored));
//        keyMap.put(SolrFieldName.ptype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A, InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pxrefA, new SolrFieldUnit(Arrays.asList(/*InteractionKeys.KEY_ID_A, */InteractionKeys.KEY_XREFS_A), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.pxrefB, new SolrFieldUnit(Arrays.asList(/*InteractionKeys.KEY_ID_B, */InteractionKeys.KEY_XREFS_B), xrefConverter, xrefFormatter, stored));
//        keyMap.put(SolrFieldName.pxref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A,InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.xref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_XREFS_I/*, InteractionKeys.KEY_INTERACTION_ID*/), xrefConverter, xrefFormatter, stored));
        keyMap.put(SolrFieldName.annotA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_A), annotConverter, annotFormatter, !stored));
        keyMap.put(SolrFieldName.annotB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_B), annotConverter, annotFormatter, !stored));
        keyMap.put(SolrFieldName.annot, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_I), annotConverter, annotFormatter, stored));
        keyMap.put(SolrFieldName.udate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_UPDATE_DATE), dateConverter, dateFormatter, stored));
        keyMap.put(SolrFieldName.cdate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CREATION_DATE), dateConverter, dateFormatter, !stored));
        keyMap.put(SolrFieldName.negative, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_NEGATIVE), boolConverter, boolFormatter, stored));
        keyMap.put(SolrFieldName.complex, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPANSION), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ftypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.ftypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, stored));
//        keyMap.put(SolrFieldName.ftype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A, InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pmethodA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.pmethodB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_B), textConverter, textFormatter, stored));
//        keyMap.put(SolrFieldName.pmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A, InteractionKeys.KEY_PART_IDENT_METHOD_B), textConverter, textFormatter, stored));
        keyMap.put(SolrFieldName.stcA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A), floatConverter, floatFormatter, !stored));
        keyMap.put(SolrFieldName.stcB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_B), floatConverter, floatFormatter, !stored));
//        keyMap.put(SolrFieldName.stc, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A,InteractionKeys.KEY_STOICHIOMETRY_B), singleBoolConverter, boolFormatter, stored));
        keyMap.put(SolrFieldName.param, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PARAMETERS_I), boolConverter, boolFormatter, stored));
        keyMap.put(SolrFieldName.paramText,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PARAMETERS_I), textConverter, literalFormatter, !stored));
        keyMap.put(SolrFieldName.confidence,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CONFIDENCE), textConverter, literalFormatter, !stored));
        keyMap.put(SolrFieldName.source,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_SOURCE), textConverter, literalFormatter, !stored));
        keyMap.put(SolrFieldName.checksumA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_A), textConverter, literalFormatter, !stored));
        keyMap.put(SolrFieldName.checksumB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_B), textConverter, literalFormatter, !stored));
        keyMap.put(SolrFieldName.checksumI, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_I), textConverter, literalFormatter, !stored));
        
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
            Set<String> uniques = new HashSet();

            if (rowKeys != null && !rowKeys.isEmpty()){
                uniques.clear();
                for (String key : rowKeys) {

                    Collection<Field> fields = row.getFields(key);

                    if (fields != null && !fields.isEmpty()){
                        StringBuilder origField = new StringBuilder();
                        for (Field field : fields) {
                            origField.append(solrField.getFormatter().format(field)).append("|");
                            if (converter != null) {
                                converter.indexFieldValues(field, solrFieldName, doc, solrField.isStored(), uniques);
                            }
                        }
                        if (origField.length() != 0 && !uniques.contains("_o"+origField)) {
                            //TODO - this needs to be changed so that really only the original content is stored in case SolrFieldUnit holds multiple fields; quick fix for now: we only have single columns in the SolrFieldUnit
                            doc.addField(solrFieldName+"_o", origField.toString().substring(0, origField.length()-1));
                            uniques.add("_o"+origField);
                        }
                    }
                }
            }
        }

        return doc;
    }

    // TODO
    public Row toCalimochoRow(SolrInputDocument doc) throws Exception {
        Row row = new DefaultRow();

//        for (Entry<String, SolrInputField> entry : doc.entrySet() ) {
//
//            String solrFieldName = entry.getKey();
//            SolrInputField solrField = entry.getValue();
//
//            row.addField(solrFieldName, null);
//
//
//        }


        return row;
    }

}
