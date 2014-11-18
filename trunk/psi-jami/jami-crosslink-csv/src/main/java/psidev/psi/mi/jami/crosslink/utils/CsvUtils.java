package psidev.psi.mi.jami.crosslink.utils;

import psidev.psi.mi.jami.crosslink.CsvType;
import psidev.psi.mi.jami.crosslink.extension.datasource.*;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * Crosslink CSV utils class
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/08/14</pre>
 */

public class CsvUtils {

    public static final String PROTEIN_SEPARATOR = ";";
    public static final String XREF_SEPARATOR = "|";

    public static final String SEQUENCE_TAG = "sequence tag";
    public static final String SEQUENCE_TAG_MI = "MI:0102";

    public static final String PHYSICAL_INTERACTION = "physical association";
    public static final String PHYSICAL_INTERACTION_MI = "MI:0915";

    public static final String CROSS_LINK = "crosslink";
    public static final String CROSS_LINK_MI = "MI:0030";

    public static final String CROSS_LINKER= "cross linker";
    public static final String CROSS_LINKER_MI = "MI:0911";

    public static void initialiseDataSourceFactoryWithCrosslinkCsvDataSources(){
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions1 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, true, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvEvidenceStreamSource.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.binary, true, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvBinaryEvidenceStreamSource.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvEvidenceSource.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.binary, false, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvBinaryEvidenceSource.class, supportedOptions4);
        Map<String, Object> supportedOptions5 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, CsvType.single_nary);
        datasourceFactory.registerDataSource(CsvNaryEvidenceSource.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, CsvType.mix);
        datasourceFactory.registerDataSource(CsvMixedEvidenceSource.class, supportedOptions6);
    }

    /**
     *
     * @param inputSource : the file, filename, inputStream, or reader
     * @param type : the CSV type
     */
    public static Map<String, Object> createDefaultCrossLinkCsvOptions(Object inputSource, CsvType type){
        Map<String, Object> options = null;
        switch (type){
            case mix:
                options = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, type);
                break;
            case single_nary:
                options = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, type);
                break;
            case binary_only:
                options = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, true, type);
                break;
            default:
                options = createCrossLinkCsvDataSourceOptions(InteractionCategory.evidence, ComplexType.n_ary, false, type);
                break;
        }
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, inputSource);

        return options;
    }

    private static Map<String, Object> createCrossLinkCsvDataSourceOptions(InteractionCategory interactionCategory, ComplexType complexType, boolean streaming, CsvType csvType) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(MIFileDataSourceOptions.INPUT_TYPE_OPTION_KEY, "crosslinkCsv");
        supportedOptions4.put(MIFileDataSourceOptions.STREAMING_OPTION_KEY, streaming);
        supportedOptions4.put(MIFileDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory);
        supportedOptions4.put(MIFileDataSourceOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY, null);
        supportedOptions4.put(MIFileDataSourceOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, null);
        supportedOptions4.put(MIFileDataSourceOptions.INTERACTOR_FACTORY_OPTION_KEY, null);
        supportedOptions4.put(CsvDatasourceOptions.CSV_TYPE_OPTION_KEY, csvType != null ? csvType : CsvType.mix);
        return supportedOptions4;
    }
}
