package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * The factory to populate the map of options for the InteractionWriterFactory for json writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonOptionFactory {

    private static final MIJsonOptionFactory instance = new MIJsonOptionFactory();

    private MIJsonOptionFactory(){

    }

    public static MIJsonOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param outputFile : the file where to write the interactions
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getDefaultJsonOptions(File outputFile){
        Map<String, Object> options = getJsonOptions(outputFile, InteractionCategory.mixed, null, MIJsonType.n_ary_only, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param outputFile : the file where to write the interactions
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, File outputFile){
        Map<String, Object> options = getJsonOptions(outputFile, objectCategory, null, MIJsonType.n_ary_only, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param type : json type
     * @param output : the outputstream
     * @return the options for a JSON interaction writer.
     */
    public Map<String, Object> getJsonOptions(MIJsonType type, File output){
        Map<String, Object> options = getJsonOptions(output, InteractionCategory.mixed, ComplexType.n_ary, type, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, OntologyTermFetcher fetcher,  File writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, null, MIJsonType.n_ary_only, fetcher, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              OntologyTermFetcher fetcher,  File writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, fetcher, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              File writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, null, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param output : the output
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getDefaultJsonOptions(OutputStream output){
        Map<String, Object> options = getJsonOptions(output, InteractionCategory.mixed, null, MIJsonType.n_ary_only, null, null);
        return options;
    }



    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param output : the output
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, OutputStream output){
        Map<String, Object> options = getJsonOptions(output, objectCategory, null, MIJsonType.n_ary_only, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param type : json type
     * @param output : the outputstream
     * @return the options for a JSON interaction writer.
     */
    public Map<String, Object> getJsonOptions(MIJsonType type, OutputStream output){
        Map<String, Object> options = getJsonOptions(output, InteractionCategory.mixed, ComplexType.n_ary, type, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, OntologyTermFetcher fetcher,  OutputStream writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, null, MIJsonType.n_ary_only, fetcher, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              OntologyTermFetcher fetcher,  OutputStream writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, fetcher, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              OutputStream writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, null, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getDefaultJsonOptions(Writer writer){
        Map<String, Object> options = getJsonOptions(writer, InteractionCategory.mixed, null, MIJsonType.n_ary_only, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, Writer writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, null, MIJsonType.n_ary_only, null, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, OntologyTermFetcher fetcher,  Writer writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, null, MIJsonType.n_ary_only, fetcher, null);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param fetcher : the ontology term fetcher
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              OntologyTermFetcher fetcher,  Writer writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, fetcher, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer which will be by default of type MIJsonType.binary_only as a complex ecpasnion is provided.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion
     * @param writer : the writer
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(InteractionCategory objectCategory, ComplexExpansionMethod expansion,
                                              Writer writer){
        Map<String, Object> options = getJsonOptions(writer, objectCategory, ComplexType.n_ary, MIJsonType.binary_only, null, expansion);
        return options;
    }

    /**
     * Create the options for a JSON interaction writer.
     * @param type : type of json
     * @param writer : the writer
     * @return the options for a JSON interaction writer.
     */
    public Map<String, Object> getJsonOptions(MIJsonType type, Writer writer){
        Map<String, Object> options = getJsonOptions(writer, InteractionCategory.mixed, ComplexType.n_ary, type, null, null);
        return options;
    }

    /**
     * Create the options for the JSON InteractionWriter.
     * @param output
     * @param objectCategory : the interaction object type to write
     * @param complexType : binary, n-ary, etc
     * @param type: the MI jason type (n_ary_only or binary_only)
     * @param ontologyFetcher : the ontology fetcher
     * @return the options for the JSON InteractionWriter
     */
    public Map<String, Object> getJsonOptions(Object output, InteractionCategory objectCategory, ComplexType complexType,
                                              MIJsonType type, OntologyTermFetcher ontologyFetcher, ComplexExpansionMethod complexExpansion){
        Map<String, Object> options = new HashMap<String, Object>(10);
        options.put(MIJsonWriterOptions.OUTPUT_OPTION_KEY, output);
        options.put(MIJsonWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIJsonWriterOptions.MI_JSON_FORMAT);
        options.put(MIJsonWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory != null ? objectCategory : InteractionCategory.mixed);
        options.put(MIJsonWriterOptions.MI_JSON_TYPE, type != null ? type : MIJsonType.n_ary_only);
        if (complexExpansion != null){
            options.put(MIJsonWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, complexExpansion);
        }
        if (complexType != null){
            options.put(MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        }
        if (ontologyFetcher != null){
            options.put(MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY, ontologyFetcher);
        }
        return options;
    }
}
