package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.options.MIDataSourceOptions;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.listener.MitabParserLogger;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserLogger;
import psidev.psi.mi.jami.xml.model.extension.factory.options.PsiXmlWriterOptions;

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
     * @return the default options for the MI datasource corresponding to this source inputstream
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(InputStream streamToAnalyse) throws IOException {
        OpenedInputStream openedStream = fileAnalyzer.extractMIFileTypeFrom(streamToAnalyse);
        return getDefaultFileOptions(openedStream.getSource(), openedStream.getReader());
    }

    /**
     * Create a map with the default options to retrieve the default MI datasource that will read the reader content.
     * @param readerToAnalyze : reader to be used to analyze the MIFileType
     * @return the default options for the MI datasource corresponding to this source reader
     * @throws IOException
     */
    public Map<String, Object> getDefaultOptions(Reader readerToAnalyze) throws IOException {
        OpenedInputStream openedStream = fileAnalyzer.extractMIFileTypeAndCopiedInputStream(readerToAnalyze);
        return getDefaultFileOptions(openedStream.getSource(), openedStream.getReader());
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
            case psimi_xml:
                return getDefaultXmlOptions(inputData);
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
        return getMitabOptions(InteractionCategory.evidence, ComplexType.n_ary, true, new MitabParserLogger(), inputData);
    }

    /**
     * Create the options for the MITAB datasource using the provided objectCategory.
     * It will read elements from this objectCategory in a streaming way.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param objectCategory
     * @param complexType: the kind of complex : n-ary or binary
     * @return the options for the MITAB datasource using the provided objectCategory
     */
    public Map<String, Object> getMitabOptions(InteractionCategory objectCategory, psidev.psi.mi.jami.model.ComplexType complexType, Object inputData){
        return getMitabOptions(objectCategory, complexType, true, new MitabParserLogger(), inputData);
    }

    /**
     * Create the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements.
     * It will use the MitabParserLogger to listen to the MITAB parsing events
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the MITAB datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getMitabOptions(boolean streaming, Object inputData){
        return getMitabOptions(null, null, streaming, new MitabParserLogger(), inputData);
    }

    /**
     * Create the options for the MITAB datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements in a streaming way.
     * @param listener
     * @param inputData is the mitab data to read
     * @return the options for the MITAB datasource with the provided listener
     */
    public Map<String, Object> getMitabOptions(MIFileParserListener listener, Object inputData){
        return getMitabOptions(null, null, true, listener, inputData);
    }

    /**
     * Create the options for the MITAB datasource.
     * @param objectCategory : interaction object type to load
     * @param complexType: the kind of complex : n-ary or binary
     * @param streaming : true if we want to load interactions in a streaming way
     * @param listener : the listener to use for listening MITAB parsing events
     * @return the MITAB datasource options
     */
    public Map<String, Object> getMitabOptions(InteractionCategory objectCategory, psidev.psi.mi.jami.model.ComplexType complexType,
                                               boolean streaming, MIFileParserListener listener, Object input){
        return getOptions(MIFileType.mitab, objectCategory, complexType, streaming, listener, input);
    }

    /**
     * Create the default options for the Psi-XML 2.5 datasource.
     * It will read InteractionEvidence elements in a streaming way.
     * It will use the PsiXmlParserLogger to listen to the Psi-XML parsing events
     * It will keep the parsed objects having an id in memory.
     * @return the default options for the PSI-xml 2.5 datasource
     */
    public Map<String, Object> getDefaultXmlOptions(Object inputData){
        return getXmlOptions(InteractionCategory.evidence, psidev.psi.mi.jami.model.ComplexType.n_ary, true, new PsiXmlParserLogger(),
                inputData, null, null);
    }

    /**
     * Create the options for the Psi-XML datasource using the provided objectCategory.
     * It will read elements from this objectCategory in a streaming way.
     * It will use the PsiXmlParserLogger to listen to the Psi=XML parsing events
     * It will keep the parsed objects having an id in memory.
     * @param objectCategory
     * @param complexType: the kind of complex : n-ary or binary
     * @return the options for the Psi Xml datasource using the provided objectCategory
     */
    public Map<String, Object> getXmlOptions(InteractionCategory objectCategory, psidev.psi.mi.jami.model.ComplexType complexType, Object inputData){
        return getXmlOptions(objectCategory, complexType, true, new PsiXmlParserLogger(), inputData, null, null);
    }

    /**
     * Create the options for the PSI-XML  datasource and specify if we want a Streaming MIFileDatasource.
     * It will read InteractionEvidence elements.
     * It will use the PsiXmlParserLogger to listen to the PSI-XML parsing events
     * It will keep the parsed objects having an id in memory.
     * @param streaming : tru if we want to read the interactions in a streaming way
     * @return the options for the PSI-XML datasource and specify if we want a Streaming MIFileDatasource
     */
    public Map<String, Object> getXmlOptions(boolean streaming, Object inputData){
        return getXmlOptions(null, null, streaming, new PsiXmlParserLogger(), inputData, null, null);
    }

    /**
     * Create the options for the Psi-XML datasource using the provided MIFileParserListener.
     * It will read InteractionEvidence elements in a streaming way.
     * It will keep the parsed objects having an id in memory.
     * @param listener
     * @param inputData is the mitab data to read
     * @return the options for the PSI-XML datasource with the provided listener
     */
    public Map<String, Object> getXmlOptions(MIFileParserListener listener, Object inputData){
        return getXmlOptions(null, null, true, listener, inputData, null, null);
    }

    /**
     * Create the options for the PSI-XML datasource.
     * @param objectCategory : interaction object type to load
     * @param complexType: the kind of complex : n-ary or binary
     * @param streaming : true if we want to load interactions in a streaming way
     * @param listener : the listener to use for listening XML parsing events
     * @param input : the MI source containing data
     * @param expansionMethod: the complex expansion method
     * @param objectCache: cache for parsed objects having an id
     * @return the Xml 2.5 datasource options
     */
    public Map<String, Object> getXmlOptions(InteractionCategory objectCategory, psidev.psi.mi.jami.model.ComplexType complexType,
                                               boolean streaming, MIFileParserListener listener, Object input, ComplexExpansionMethod expansionMethod,
                                               PsiXmlIdCache objectCache){
        Map<String, Object> options = getOptions(MIFileType.psimi_xml, objectCategory, complexType, streaming, listener, input);
        if (expansionMethod != null){
            options.put(MIDataSourceOptions.COMPLEX_EXPANSION_OPTION_KEY, expansionMethod);
        }
        if (objectCache != null){
            options.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, objectCache);
        }
        return options;
    }

    /**
     * Create a map of options
     * @param type : MI source type (mitab, xml25, etc.)
     * @param objectCategory : the kind of interactions to be returned by the datasource (interaction evidence, binary, modelled interaction, ...)
     * @param complexType: the kind of complex : n-ary or binary
     * @param streaming : boolean value to know if we want to stream the interactions or load the full interaction dataset
     * @param listener : parser listener
     * @param input : the MI source containing data
     * @return the map of options
     */
    public Map<String, Object> getOptions(MIFileType type, InteractionCategory objectCategory, psidev.psi.mi.jami.model.ComplexType complexType,
                                          boolean streaming, MIFileParserListener listener, Object input){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(MIFileDataSourceOptions.INPUT_TYPE_OPTION_KEY, type.toString());
        if (objectCategory != null){
            options.put(MIDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory);
        }
        options.put(MIDataSourceOptions.COMPLEX_TYPE_OPTION_KEY, complexType != null ? complexType : psidev.psi.mi.jami.model.ComplexType.n_ary);
        options.put(MIFileDataSourceOptions.STREAMING_OPTION_KEY, streaming);
        if (listener != null){
            options.put(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY, listener);
        }
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, input);
        return options;
    }
}
