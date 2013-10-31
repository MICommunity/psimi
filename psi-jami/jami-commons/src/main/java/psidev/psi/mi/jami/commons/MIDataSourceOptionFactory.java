package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.tab.listener.MitabParserLogger;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The factory to populate the map of options for the DataSourceFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIDataSourceOptionFactory {

    private static final MIDataSourceOptionFactory instance = new MIDataSourceOptionFactory();
    private MIFileAnalyzer fileAnalyzer;

    private MIDataSourceOptionFactory(){
        this.fileAnalyzer = new MIFileAnalyzer();
    }

    public static MIDataSourceOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the file content.
     * @param file
     * @return the default options for the MI datasource corresponding to this file
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(File file) throws IOException {

        return getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(file), file);
    }

    /**
     * Creates a map with the default options to retrieve MI datasource that will read the URL content
     * @param url
     * @return the default options for the MI datasource corresponding to this url
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(URL url) throws IOException {
        InputStream stream = url.openStream();
        Map<String, Object> options = Collections.EMPTY_MAP;
        try {
            options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(stream), url);
        }
        finally {
            stream.close();
        }

        return options;
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the inputstream content.
     * @param streamToAnalyse : stream to be used to analyze the MIFileType
     * @param source : stream to be used by the MIFileDataSource
     * @return the default options for the MI datasource corresponding to this source inputstream
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(InputStream streamToAnalyse, InputStream source) throws IOException {

        return getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(streamToAnalyse), source);
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the reader content.
     * @param readerToAnalyze : reader to be used to analyze the MIFileType
     * @param sourceReader : reader to be used by the MIFileDataSource
     * @return the default options for the MI datasource corresponding to this source reader
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(Reader readerToAnalyze, Reader sourceReader) throws IOException {

        return getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(readerToAnalyze), sourceReader);
    }

    /**
     * Create a map of default options depending on the provided sourceType.
     * It can recognize mitab, psi-xml and other
     * @param sourceType
     * @return the map of default options for this sourceType
     */
    public Map<String, Object> getDefaultFileOptions(MIFileType sourceType, Object inputData){

        switch (sourceType){
            case mitab:
                return getDefaultMitabOptions(inputData);
            case psi25_xml:
                return getDefaultXml25Options(inputData);
            default:
                return null;
        }
    }

    /**
     * Create the default options for the MITAB datasource.
     * It will read InteractionEvidence elements in a streaming way.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @return the default options for the MITAB datasource
     */
    public Map<String, Object> getDefaultMitabOptions(Object inputData){
        return getMitabOptions(InteractionObjectCategory.evidence, true, new MitabParserLogger(), inputData);
    }

    /**
     * Create the options for the MITAB datasource using the provided objectCategory.
     * It will read elements from this objectCategory in a streaming way.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param objectCategory
     * @return the options for the MITAB datasource using the provided objectCategory
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, Object inputData){
        return getMitabOptions(objectCategory, true, null, inputData);
    }

    /**
     * Create the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getMitabOptions(boolean streaming, Object inputData){
        return getMitabOptions(null, streaming, null, inputData);
    }

    /**
     * Create the options for the MITAB datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements in a streaming way.
     * @param listener
     * @param inputData is the mitab data to read
     * @return the options for the MITAB datasource with the provided listener
     */
    public Map<String, Object> getMitabOptions(MIFileParserListener listener, Object inputData){
        return getMitabOptions(null, true, listener, inputData);
    }

    /**
     * Create the options for the MITAB datasource.
     * @param objectCategory : interaction object type to load
     * @param streaming : true if we want to load interactions in a streaming way
     * @param listener : the listener to use for listening MITAB parsing events
     * @return the MITAB datasource options
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener, Object input){
        return getOptions(MIFileType.mitab, objectCategory, streaming, listener, input);
    }

    /**
     * Create the default options for the Psi-XML 2.5 datasource.
     * It will read InteractionEvidence elements in a streaming way.
     * It will use the PsiXmlParserLogger to listen to the Psi-XML parsing events
     * @return the default options for the PSI-xml 2.5 datasource
     */
    public Map<String, Object> getDefaultXml25Options(Object inputData){
        return getXml25Options(InteractionObjectCategory.evidence, true, new PsiXmlParserLogger(), inputData);
    }

    /**
     * Create the options for the Psi-XML 2.4 datasource using the provided objectCategory.
     * It will read elements from this objectCategory in a streaming way.
     * It will use the PsiXmlParserLogger to listen to the Psi=XML parsing events
     * @param objectCategory
     * @return the options for the Psi Xml datasource using the provided objectCategory
     */
    public Map<String, Object> getXml25Options(InteractionObjectCategory objectCategory, Object inputData){
        return getXml25Options(objectCategory, true, null, inputData);
    }

    /**
     * Create the options for the PSI-XML 2.5 datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements.
     * It will use the PsiXmlParserLogger to listen to the PSI-XML parsing events
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the PSI-XML datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getXml25Options(boolean streaming, Object inputData){
        return getXml25Options(null, streaming, null, inputData);
    }

    /**
     * Create the options for the Psi-XML 2.5 datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements in a streaming way.
     * @param listener
     * @param inputData is the mitab data to read
     * @return the options for the PSI-XML datasource with the provided listener
     */
    public Map<String, Object> getXml25Options(MIFileParserListener listener, Object inputData){
        return getXml25Options(null, true, listener, inputData);
    }

    /**
     * Create the options for the PSI-XML 2.5 datasource.
     * @param objectCategory : interaction object type to load
     * @param streaming : true if we want to load interactions in a streaming way
     * @param listener : the listener to use for listening XML parsing events
     * @return the Xml 2.5 datasource options
     */
    public Map<String, Object> getXml25Options(InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener, Object input){
        return getOptions(MIFileType.psi25_xml, objectCategory, streaming, listener, input);
    }

    public Map<String, Object> getXmlOptions(InteractionObjectCategory objectCategory, boolean streaming, Object input){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, input);

        return options;
    }

    public Map<String, Object> getOptions(MIFileType type, InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener, Object input){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, type.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.evidence);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        if (listener != null){
            options.put(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY, listener);
        }
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, input);

        return options;
    }
}
