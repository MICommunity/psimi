package psidev.psi.mi.jami.crosslink;

import psidev.psi.mi.jami.crosslink.extension.datasource.*;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a central access to basic methods to register crosslink csv datasources.
 *
 * Existing datasources :
 * - CsvEvidenceStreamSource : datasource which returns InteractionEvidence in a streaming way.
 * It will load all the lines as independent binary interactions. The interactor factory does not make sense as it only loads CsvProteins so
 * this datasource ignore the interactor factory option
 * - CsvBinaryEvidenceStreamSource : datasource which returns BinaryInteractionEvidence in a streaming way.
 * It will load all the lines as independent binary interactions. The interactor factory does not make sense as it only loads CsvProteins so
 * this datasource ignore the interactor factory option
 * - CsvEvidenceSource : datasource which returns InteractionEvidence and load all the file at once.
 * It will load all the lines as independent binary interactions. The interactor factory does not make sense as it only loads CsvProteins so
 * this datasource ignore the interactor factory option
 * - CsvBinaryEvidenceSource : datasource which returns BinaryInteractionEvidence and load all the file at once.
 * It will load all the lines as independent binary interactions. The interactor factory does not make sense as it only loads CsvProteins so
 * this datasource ignore the interactor factory option
 * - CsvNaryEvidenceSource : datasource which returns InteractionEvidence and load all the file at once.
 * It will build a single n-ary interaction from all the lines in the file.
 * The interactor factory does not make sense as it only loads CsvProteins so this datasource ignore the interactor factory option.
 * The streaming option does not make sense neither as the all file need to be loaded so it will be ignored.
 * - CsvMixedEvidenceSource : datasource which returns InteractionEvidence and load all the file at once.
 * It will load the lines and re-build n-ary interaction evidences if a column narygroup is provided. The interactor factory does not make sense as it only loads CsvProteins so
 * this datasource ignore the interactor factory option
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/14</pre>
 */

public class CrossLinkCsv {

    /**
     * Register all existing Crosslink csv datasource in the MI datasource factory
     */
    public static void initialiseAllCrossLinkCsvSources(){
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions1 = createCrossLinkCsvDataSourceOptions(ComplexType.n_ary, true, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvEvidenceStreamSource.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createCrossLinkCsvDataSourceOptions(ComplexType.binary, true, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvBinaryEvidenceStreamSource.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createCrossLinkCsvDataSourceOptions(ComplexType.n_ary, false, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvEvidenceSource.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createCrossLinkCsvDataSourceOptions(ComplexType.binary, false, CsvType.binary_only);
        datasourceFactory.registerDataSource(CsvBinaryEvidenceSource.class, supportedOptions4);
        Map<String, Object> supportedOptions5 = createCrossLinkCsvDataSourceOptions(ComplexType.n_ary, null, CsvType.single_nary);
        datasourceFactory.registerDataSource(CsvNaryEvidenceSource.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createCrossLinkCsvDataSourceOptions(ComplexType.n_ary, null, CsvType.mix);
        datasourceFactory.registerDataSource(CsvMixedEvidenceSource.class, supportedOptions6);
    }

    private static Map<String, Object> createCrossLinkCsvDataSourceOptions(ComplexType complexType,
                                                                           Boolean streaming,
                                                                           CsvType csvType) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(10);
        supportedOptions4.put(MIFileDataSourceOptions.INPUT_TYPE_OPTION_KEY, CsvDatasourceOptions.CROSSLINK_CSV_FORMAT);
        supportedOptions4.put(MIFileDataSourceOptions.STREAMING_OPTION_KEY, streaming);
        supportedOptions4.put(MIFileDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, InteractionCategory.evidence);
        supportedOptions4.put(MIFileDataSourceOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY, null);
        supportedOptions4.put(MIFileDataSourceOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, null);
        supportedOptions4.put(CsvDatasourceOptions.CSV_TYPE_OPTION_KEY, csvType != null ? csvType : CsvType.mix);
        return supportedOptions4;
    }
}
