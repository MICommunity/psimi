package psidev.psi.mi.jami.crosslink;

import psidev.psi.mi.jami.crosslink.listener.CsvParserLogger;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The factory to populate the map of options for the DataSourceFactory for crosslink csv datasources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class CrossLinkCsvOptionFactory {

    private static final CrossLinkCsvOptionFactory instance = new CrossLinkCsvOptionFactory();

    private CrossLinkCsvOptionFactory(){

    }

    public static CrossLinkCsvOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the file content.
     * @param file
     * @return the default options for the MI datasource corresponding to this file
     * @throws java.io.IOException
     */
    public Map<String, Object> getDefaultOptions(File file) throws IOException {

        return getDefaultFileOptions(CsvType.mix,file);
    }

    /**
     * Creates a map with the default options to retrieve MI datasource that will read the URL content
     * @param url
     * @return the default options for the MI datasource corresponding to this url
     * @throws java.io.IOException
     */
    public Map<String, Object> getDefaultOptions(URL url) throws IOException {
        return getDefaultFileOptions(CsvType.mix,url);
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the inputstream content.
     * @param streamToAnalyse : stream to be used to analyze the MIFileType
     * @return the default options for the MI datasource corresponding to this source inputstream
     * @throws java.io.IOException
     */
    public Map<String, Object> getDefaultOptions(InputStream streamToAnalyse) throws IOException {
        return getDefaultFileOptions(CsvType.mix,streamToAnalyse);
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the reader content.
     * @param readerToAnalyze : reader to be used to analyze the MIFileType
     * @return the default options for the MI datasource corresponding to this source reader
     * @throws java.io.IOException
     */
    public Map<String, Object> getDefaultCsvOptions(Reader readerToAnalyze) throws IOException {
        return getDefaultFileOptions(CsvType.mix,readerToAnalyze);
    }

    /**
     * Create a map of default options depending on the provided sourceType.
     * It can recognize mitab, psi-xml and other
     * @param csvType
     * @param inputData
     * @return the map of default options for this sourceType
     */
    public Map<String, Object> getDefaultFileOptions(CsvType csvType, Object inputData){
        if (csvType == null){
           csvType = CsvType.mix;
        }
        switch (csvType){
            case mix:
                return getCsvOptions(csvType, ComplexType.n_ary, null, new CsvParserLogger(), inputData);
            case binary_only:
                return getCsvOptions(csvType, ComplexType.n_ary, true, new CsvParserLogger(), inputData);
            case single_nary:
                return getCsvOptions(csvType, ComplexType.n_ary, null, new CsvParserLogger(), inputData);
            default:
                return getCsvOptions(csvType, ComplexType.n_ary, null, new CsvParserLogger(), inputData);
        }
    }

    /**
     * Create the default options for the CSV datasource.
     * It will read InteractionEvidence elements usingCsvType.mix.
     * It will use the CsvParserLogger to listen to the CSV parsing events
     * @return the default options for the CSV datasource
     */
    public Map<String, Object> getDefaultCsvOptions(Object inputData){
        return getCsvOptions(CsvType.mix, ComplexType.n_ary, null, new CsvParserLogger(), inputData);
    }

    /**
     * Create the options for the CSV datasource using the provided csvtype.
     * It will read elements from this objectCategory.
     * It will use the CsvParserLogger to listen to the CSV parsing events
     * @param csvType
     * @param complexType: the kind of complex : n-ary or binary
     * @return the options for the CSV datasource using the provided objectCategory
     */
    public Map<String, Object> getCsvOptions(CsvType csvType, ComplexType complexType, Object inputData){
        return getCsvOptions(csvType, complexType, true, new CsvParserLogger(), inputData);
    }

    /**
     * Create the options for the CSV datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements using CsvType.binary_only.
     * It will use the CsvParserLogger to listen to the Csv parsing events
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getCsvOptions(boolean streaming, Object inputData){
        return getCsvOptions(CsvType.binary_only, ComplexType.n_ary, streaming, new CsvParserLogger(), inputData);
    }

    /**
     * Create the options for the CSV datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements and use CsvType.mix by default.
     * @param listener
     * @param inputData is the mitab data to read
     * @return the options for the MITAB datasource with the provided listener
     */
    public Map<String, Object> getCsvOptions(MIFileParserListener listener, Object inputData){
        return getCsvOptions(CsvType.mix, ComplexType.n_ary, null, listener, inputData);
    }

    /**
     * Create a map of options
     * @param csvType : Crosslink Csv type (mix, binary_only, single_nary, etc.)
     * @param complexType: the kind of complex : n-ary or binary
     * @param streaming : boolean value to know if we want to stream the interactions or load the full interaction dataset
     * @param listener : parser listener
     * @param input : the MI source containing data
     * @return the map of options
     */
    public Map<String, Object> getCsvOptions(CsvType csvType, ComplexType complexType, Boolean streaming, MIFileParserListener listener, Object input){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(CsvDatasourceOptions.INPUT_TYPE_OPTION_KEY, CsvDatasourceOptions.CROSSLINK_CSV_FORMAT);
        options.put(CsvDatasourceOptions.CSV_TYPE_OPTION_KEY, csvType != null ? csvType : CsvType.mix);
        options.put(CsvDatasourceOptions.INTERACTION_CATEGORY_OPTION_KEY, InteractionCategory.evidence);
        options.put(CsvDatasourceOptions.COMPLEX_TYPE_OPTION_KEY, complexType != null ? complexType : ComplexType.n_ary);
        if (streaming != null){
            options.put(CsvDatasourceOptions.STREAMING_OPTION_KEY, streaming);
        }
        if (listener != null){
            options.put(CsvDatasourceOptions.PARSER_LISTENER_OPTION_KEY, listener);
        }
        options.put(CsvDatasourceOptions.INPUT_OPTION_KEY, input);
        return options;
    }
}
