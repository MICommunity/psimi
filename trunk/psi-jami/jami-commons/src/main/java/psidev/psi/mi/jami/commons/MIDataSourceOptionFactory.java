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

    public Map<String, Object> getDefaultOptions(File file) throws IOException {

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(file));
        options.put(MIDataSourceFactory.INPUT_FILE_OPTION_KEY, file);

        return options;
    }

    public Map<String, Object> getDefaultOptions(InputStream streamToAnalyse, InputStream source) throws IOException {

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(streamToAnalyse));
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, source);

        return options;
    }

    public Map<String, Object> getDefaultOptions(Reader readerToAnalyze, Reader sourceReader) throws IOException {

        Map<String, Object> options = getDefaultFileOptions(fileAnalyzer.identifyMIFileTypeFor(readerToAnalyze));
        options.put(MIDataSourceFactory.READER_OPTION_KEY, sourceReader);

        return options;
    }

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

    public Map<String, Object> getDefaultMitabOptions(){
        return getMitabOptions(InteractionObjectCategory.evidence, true, new MitabParserLogger());
    }

    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory){
        return getMitabOptions(objectCategory, true, null);
    }

    public Map<String, Object> getMitabOptions(boolean streaming){
        return getMitabOptions(null, streaming, null);
    }

    public Map<String, Object> getMitabOptions(MIFileParserListener listener){
        return getMitabOptions(null, true, listener);
    }

    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener){
        Map<String, Object> options = new HashMap<String, Object>();

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.evidence);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        if (listener != null){
            options.put(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY, listener);
        }

        return options;
    }

    public Map<String, Object> getDefaultXmlOptions(){
        Map<String, Object> options = new HashMap<String, Object>();

        options.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, InteractionObjectCategory.mixed);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, true);

        return options;
    }
}
