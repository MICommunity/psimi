package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.html.utils.HtmlWriterOptions;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * The factory to populate the map of options for the InteractionWriterFactory for html writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIHtmlOptionFactory {

    private static final MIHtmlOptionFactory instance = new MIHtmlOptionFactory();

    private MIHtmlOptionFactory(){

    }

    public static MIHtmlOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param outputFile : the file where to write the interactions
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getHtmlOptions(File outputFile){
        Map<String, Object> options = getHtmlOptions(outputFile, InteractionCategory.mixed, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param outputFile : the file where to write the interactions
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getHtmlOptions(InteractionCategory objectCategory, File outputFile){
        Map<String, Object> options = getHtmlOptions(outputFile, objectCategory, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param outputFile : the file where to write the interactions
     * @return the options for a HTML interaction writer.
     */
    public Map<String, Object> getHtmlOptions(boolean writeHeader, File outputFile){
        Map<String, Object> options = getHtmlOptions(outputFile, InteractionCategory.mixed, writeHeader);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param output : the output
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(OutputStream output){
        Map<String, Object> options = getHtmlOptions(output, InteractionCategory.mixed, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param output : the output
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getHtmlOptions(InteractionCategory objectCategory, OutputStream output){
        Map<String, Object> options = getHtmlOptions(output, objectCategory, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param output : the outputstream
     * @return the options for a HTML interaction writer.
     */
    public Map<String, Object> getHtmlOptions(boolean writeHeader, OutputStream output){
        Map<String, Object> options = getHtmlOptions(output, InteractionCategory.mixed, writeHeader);
        return options;
    }

    /**
     * Create the options for a Html interaction writer.
     * @param writer : the writer
     * @return the options for the Html InteractionWriter
     */
    public Map<String, Object> getDefaultHtmlOptions(Writer writer){
        Map<String, Object> options = getHtmlOptions(writer, InteractionCategory.mixed, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param writer : the writer
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getHtmlOptions(InteractionCategory objectCategory, Writer writer){
        Map<String, Object> options = getHtmlOptions(writer, objectCategory, true);
        return options;
    }

    /**
     * Create the options for a HTML interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param writer : the writer
     * @return the options for a HTML interaction writer.
     */
    public Map<String, Object> getHtmlOptions(boolean writeHeader, Writer writer){
        Map<String, Object> options = getHtmlOptions(writer, InteractionCategory.mixed, true);
        return options;
    }

    /**
     * Create the options for the HTML InteractionWriter.
     * @param output
     * @param objectCategory : the interaction object type to write
     * @param writeHeader : true if we want to write the header/body
     * @return the options for the HTML InteractionWriter
     */
    public Map<String, Object> getHtmlOptions(Object output, InteractionCategory objectCategory, boolean writeHeader){
        Map<String, Object> options = new HashMap<String, Object>(10);
        options.put(HtmlWriterOptions.OUTPUT_OPTION_KEY, output);
        options.put(HtmlWriterOptions.OUTPUT_FORMAT_OPTION_KEY, HtmlWriterOptions.MI_HTML_FORMAT);
        options.put(HtmlWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory != null ? objectCategory : InteractionCategory.mixed);
        options.put(HtmlWriterOptions.WRITE_HTML_HEADER_BODY_OPTION, writeHeader);
        return options;
    }
}
