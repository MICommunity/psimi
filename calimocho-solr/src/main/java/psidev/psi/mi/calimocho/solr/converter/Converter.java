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
import java.util.Set;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.DefaultRow;
import org.hupo.psi.calimocho.tab.io.formatter.AnnotationFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.BooleanFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.DateFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.XrefFieldFormatter;

/**
 *
 * @author kbreuer
 */
public class Converter {

    protected Map<SolrFieldName, SolrFieldUnit> keyMap;

    public Converter() {
        keyMap = new HashMap<SolrFieldName, SolrFieldUnit>();
        initializeKeyMap();
    }

    protected void initializeKeyMap() {
        XrefFieldConverter xrefConverter = new XrefFieldConverter();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();
        TextFieldConverter textConverter = new TextFieldConverter();
        XrefFieldFormatter textFormatter = new XrefFieldFormatter();
        DateFieldConverter dateConverter = new DateFieldConverter();
        DateFieldFormatter dateFormatter = new DateFieldFormatter("yyyy/MM/dd");
        AnnotationFieldConverter annotConverter = new AnnotationFieldConverter();
        AnnotationFieldFormatter annotFormatter = new AnnotationFieldFormatter(":");
        SingleBooleanFieldConverter boolConverter = new SingleBooleanFieldConverter();
        TextToBooleanFieldConverter textToBoolConverter = new TextToBooleanFieldConverter();
        BooleanFieldFormatter boolFormatter = new BooleanFieldFormatter();
        LiteralFieldFormatter literalFormatter = new LiteralFieldFormatter();
        boolean storeOnly = true; //if false, add fn + fn_s + fn_o (fields in MIQL2.7), else only fn_o

        keyMap.put(SolrFieldName.idA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.idB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_B), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.altidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALTID_A), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.altidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.aliasA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.aliasB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pubid, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBID), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pubauth, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PUBAUTH), textConverter, literalFormatter, !storeOnly));
        keyMap.put(SolrFieldName.taxidA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.taxidB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.taxidHost, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_HOST_ORGANISM), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.type, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_TYPE), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.detmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_DETMETHOD),textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.interaction_id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTION_ID), xrefConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pbioroleA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pbioroleB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pexproleA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPROLE_A), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.pexproleB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPROLE_B), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.ptypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.ptypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pxrefA, new SolrFieldUnit(Arrays.asList(/*InteractionKeys.KEY_ID_A, */InteractionKeys.KEY_XREFS_A), textConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pxrefB, new SolrFieldUnit(Arrays.asList(/*InteractionKeys.KEY_ID_B, */InteractionKeys.KEY_XREFS_B), textConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.xref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_XREFS_I/*, InteractionKeys.KEY_INTERACTION_ID*/), textConverter, xrefFormatter, !storeOnly));
        keyMap.put(SolrFieldName.annotA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_A), annotConverter, annotFormatter, storeOnly));
        keyMap.put(SolrFieldName.annotB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_B), annotConverter, annotFormatter, storeOnly));
        keyMap.put(SolrFieldName.annot, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ANNOTATIONS_I), annotConverter, annotFormatter, !storeOnly));
        keyMap.put(SolrFieldName.udate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_UPDATE_DATE), dateConverter, dateFormatter, !storeOnly));
        keyMap.put(SolrFieldName.cdate, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CREATION_DATE), dateConverter, dateFormatter, storeOnly));
        keyMap.put(SolrFieldName.negative, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_NEGATIVE), boolConverter, boolFormatter, !storeOnly));
        keyMap.put(SolrFieldName.complex, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_EXPANSION), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.ftypeA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.ftypeB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pmethodA, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.pmethodB, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_B), textConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.stcA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A), boolConverter, boolFormatter, storeOnly));
        keyMap.put(SolrFieldName.stcB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_B), boolConverter, boolFormatter, storeOnly));
        keyMap.put(SolrFieldName.param, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PARAMETERS_I), textToBoolConverter, textFormatter, !storeOnly));
        keyMap.put(SolrFieldName.confidence,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CONFIDENCE), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.source,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_SOURCE), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.checksumA,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_A), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.checksumB,  new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_B), textConverter, textFormatter, storeOnly));
        keyMap.put(SolrFieldName.checksumI, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_CHECKSUM_I), textConverter, textFormatter, storeOnly));

 // we originally created composite fields, but they are now handled in the solr schema
        //keyMap.put(SolrFieldName.id, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B), xrefConverter, xrefFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.alias, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.identifier, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_ALTID_A, InteractionKeys.KEY_ID_B, InteractionKeys.KEY_ALTID_B, InteractionKeys.KEY_ALIAS_A, InteractionKeys.KEY_ALIAS_B), xrefConverter, xrefFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.species, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_TAXID_A, InteractionKeys.KEY_TAXID_B), textConverter, textFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.pbiorole, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_BIOROLE_A,InteractionKeys.KEY_BIOROLE_B), textConverter, textFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.ptype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_INTERACTOR_TYPE_A, InteractionKeys.KEY_INTERACTOR_TYPE_B), textConverter, textFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.pxref, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_ID_A, InteractionKeys.KEY_XREFS_A,InteractionKeys.KEY_ID_B, InteractionKeys.KEY_XREFS_B), xrefConverter, xrefFormatter, !storeOnly));
//        keyMap.put(SolrFieldName.ftype, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_FEATURE_A, InteractionKeys.KEY_FEATURE_B), textConverter, textFormatter, !storeOnly));
        //keyMap.put(SolrFieldName.pmethod, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_PART_IDENT_METHOD_A, InteractionKeys.KEY_PART_IDENT_METHOD_B), textConverter, textFormatter, !storeOnly));
//        keyMap.put(SolrFieldName.stc, new SolrFieldUnit(Arrays.asList(InteractionKeys.KEY_STOICHIOMETRY_A,InteractionKeys.KEY_STOICHIOMETRY_B), textToBoolConverter, textFormatter, !storeOnly));

    }

    /*
     * reads a MITAB line and converts it into Solr index fields
     */
    public SolrInputDocument toSolrDocument(Row row) throws SolrServerException, IllegalFieldException {
        SolrInputDocument doc = new SolrInputDocument();
        
        for (Map.Entry<SolrFieldName,SolrFieldUnit> entry : keyMap.entrySet()) {

            SolrFieldName solrFieldName = entry.getKey();
            SolrFieldUnit solrFieldUnit = entry.getValue();
            Collection<String> rowKeys = solrFieldUnit.getRowKeys();
            SolrFieldConverter converter = solrFieldUnit.getConverter();
            Set<String> uniques = new HashSet();

            if (rowKeys != null && !rowKeys.isEmpty()){
                uniques.clear();
                for (String key : rowKeys) {

                    Collection<Field> fields = row.getFields(key);

                    if (fields != null && !fields.isEmpty()){
                        StringBuilder origField = new StringBuilder();
                        for (Field field : fields) {
                            origField.append(solrFieldUnit.getFormatter().format(field)).append("|");
                            if (converter != null && !solrFieldUnit.isStoreOnly()) {
                                doc = converter.indexFieldValues(field, solrFieldName, doc, uniques);
                            }
                        }
                        if (origField.length() > 0 && !uniques.contains(origField+"_o")) {
                            doc.addField(solrFieldName+"_o", origField.toString().replaceAll("\\|$",""));
                            uniques.add(origField+"_o"); 
                        }
                    }
                    if (key.contains("stc") && doc.getField(SolrFieldName.stc.toString()) == null) { //special case for composite field stc
                        if (fields.isEmpty()) {
                            doc.addField(SolrFieldName.stc.toString(), false);
                            doc.addField(SolrFieldName.stc.toString() + "_s", "false");
                        } else {
                            doc.addField(SolrFieldName.stc.toString(), true);
                            doc.addField(SolrFieldName.stc.toString() + "_s", "true");
                        }
                    }
                }
            }
        }

        return doc;
    }

    // TODO .. or, is this method obsolete any ways?
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
