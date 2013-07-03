package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * The factory to get options for the InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIWriterOptionFactory {

    private static final MIWriterOptionFactory instance = new MIWriterOptionFactory();

    private MIWriterOptionFactory(){
    }

    public static MIWriterOptionFactory getInstance() {
        return instance;
    }

    public Map<String, Object> getDefaultMitabOptions(){
        return getMitabOptions(InteractionObjectCategory.mixed, null, true, null, false);
    }

    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory){
        return getMitabOptions(objectCategory, null, true, null, false);
    }

    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version){
        return getMitabOptions(null, null, writeHeader, version, false);
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param outputFile : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(File outputFile){
        Map<String, Object> options = getMitabOptions(InteractionObjectCategory.mixed, null, true, null, false);
        options.put(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY, outputFile);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param outputFile : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, File outputFile){
        Map<String, Object> options = getMitabOptions(objectCategory, null, true, null, false);
        options.put(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY, outputFile);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param outputFile : the file where to write the interactions
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, File outputFile){
        Map<String, Object> options = getMitabOptions(null, null, writeHeader, version, false);
        options.put(InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY, outputFile);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param output : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(OutputStream output){
        Map<String, Object> options = getMitabOptions(InteractionObjectCategory.mixed, null, true, null, false);
        options.put(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY, output);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param output : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, OutputStream output){
        Map<String, Object> options = getMitabOptions(objectCategory, null, true, null, false);
        options.put(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY, output);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param output : the outputstream
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, OutputStream output){
        Map<String, Object> options = getMitabOptions(null, null, writeHeader, version, false);
        options.put(InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY, output);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writer : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(Writer writer){
        Map<String, Object> options = getMitabOptions(InteractionObjectCategory.mixed, null, true, null, false);
        options.put(InteractionWriterFactory.WRITER_OPTION_KEY, writer);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param writer : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, Writer writer){
        Map<String, Object> options = getMitabOptions(objectCategory, null, true, null, false);
        options.put(InteractionWriterFactory.WRITER_OPTION_KEY, writer);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param writer : the writer
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, Writer writer){
        Map<String, Object> options = getMitabOptions(null, null, writeHeader, version, false);
        options.put(InteractionWriterFactory.WRITER_OPTION_KEY, writer);
        return options;
    }

    /**
     * Create the options for the MITAB InteractionWriter.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion method to use if we have n-ary interactions
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param extended : true if all the aliases, features and confidences are pure mitab objects
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, ComplexExpansionMethod expansion, boolean writeHeader, MitabVersion version, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        if (expansion != null){
            options.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, expansion);
        }
        options.put(MitabUtils.MITAB_HEADER_OPTION, writeHeader);
        options.put(MitabUtils.MITAB_VERSION_OPTION, version != null ? version : MitabVersion.v2_7);
        options.put(MitabUtils.MITAB_EXTENDED_OPTION, extended);
        return options;
    }
}
