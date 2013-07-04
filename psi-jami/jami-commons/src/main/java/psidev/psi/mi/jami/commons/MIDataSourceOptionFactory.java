package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.tab.listener.MitabParserLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(file));
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, file);

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

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(streamToAnalyse));
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, source);

        return options;
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the reader content.
     * @param readerToAnalyze : reader to be used to analyze the MIFileType
     * @param sourceReader : reader to be used by the MIFileDataSource
     * @return the default options for the MI datasource corresponding to this source reader
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(Reader readerToAnalyze, Reader sourceReader) throws IOException {

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(readerToAnalyze));
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, sourceReader);

        return options;
    }

    /**
     * Create a map of default options depending on the provided sourceType.
     * It can recognize mitab, psi-xml and other
     * @param sourceType
     * @return the map of default options for this sourceType
     */
    public Map<String, Object> getDefaultFileOptions(MIFileType sourceType){

        switch (sourceType){
            case mitab:
                return getDefaultMitabOptions();
            case psi25_xml:
                return getDefaultXmlOptions();
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
    public Map<String, Object> getDefaultMitabOptions(){
        return getMitabOptions(InteractionObjectCategory.evidence, true, new MitabParserLogger());
    }

    /**
     * Create the options for the MITAB datasource using the provided objectCategory.
     * It will read elements from this objectCategory in a streaming way.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param objectCategory
     * @return the options for the MITAB datasource using the provided objectCategory
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory){
        return getMitabOptions(objectCategory, true, null);
    }

    /**
     * Create the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getMitabOptions(boolean streaming){
        return getMitabOptions(null, streaming, null);
    }

    /**
     * Create the options for the MITAB datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements in a streaming way.
     * @param listener
     * @return the options for the MITAB datasource with the provided listener
     */
    public Map<String, Object> getMitabOptions(MIFileParserListener listener){
        return getMitabOptions(null, true, listener);
    }

    /**
     * Create the options for the MITAB datasource.
     * @param objectCategory : interaction object type to load
     * @param streaming : true if we want to load interactions in a streaming way
     * @param listener : the listener to use for listening MITAB parsing events
     * @return the MITAB datasource options
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.evidence);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        if (listener != null){
            options.put(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY, listener);
        }

        return options;
    }

    /**
     * Create the default options for the PSI-XML datasource.
     * It will read a mix of InteractionEvidence and ModelledInteraction elements in a streaming way.
     * @return the default options for the PSI-XML datasource
     */
    public Map<String, Object> getDefaultXmlOptions(){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, InteractionObjectCategory.mixed);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, true);

        return options;
    }
}
