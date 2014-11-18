package psidev.psi.mi.jami.json;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.binary.MIJsonEvidenceWriter;
import psidev.psi.mi.jami.json.binary.MIJsonModelledWriter;
import psidev.psi.mi.jami.json.nary.LightMIJsonWriter;
import psidev.psi.mi.jami.json.nary.MIJsonWriter;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for public properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonUtils {
    public final static String OPEN = "{";
    public final static String CLOSE = "}";
    public final static String OPEN_ARRAY = "[";
    public final static String CLOSE_ARRAY = "]";
    public final static String PROPERTY_DELIMITER = "\"";
    public final static String PROPERTY_VALUE_SEPARATOR = ":";
    public final static String ELEMENT_SEPARATOR = ",";
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String INDENT = "\t";

    public static void writePropertyKey(String key, Writer writer) throws IOException {
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
        writer.write(key);
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
        writer.write(MIJsonUtils.PROPERTY_VALUE_SEPARATOR);
    }
    public static void writePropertyValue(String value, Writer writer) throws IOException {
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
    }

    public static void writeProperty(String propertyName, String value, Writer writer) throws IOException {
        writePropertyKey(propertyName, writer);
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
        writer.write(value);
        writer.write(MIJsonUtils.PROPERTY_DELIMITER);
    }

    public static void writeStartObject(Writer writer) throws IOException {
        writer.write(MIJsonUtils.OPEN);
    }

    public static void writeEndObject(Writer writer) throws IOException {
        writer.write(MIJsonUtils.CLOSE);
    }

    public static void writeSeparator(Writer writer) throws IOException {
        writer.write(MIJsonUtils.ELEMENT_SEPARATOR);
    }

    public static void writeOpenArray(Writer writer) throws IOException {
        writer.write(MIJsonUtils.OPEN_ARRAY);
    }

    public static void writeEndArray(Writer writer) throws IOException {
        writer.write(MIJsonUtils.CLOSE_ARRAY);
    }

    /**
     *
     * @param ref
     * @param interactor
     * @return an array of String : first the database, then the interactorId
     */
    public static String[] extractInteractorId(Xref ref, Interactor interactor){
        String interactorId = null;
        String db = null;
        if (ref != null){
            interactorId = JSONValue.escape(ref.getId());
            db = JSONValue.escape(ref.getDatabase().getShortName());
        }
        else{
            interactorId = Integer.toString(UnambiguousExactInteractorBaseComparator.hashCode(interactor));
            db = "generated";
        }
        return new String[]{db, interactorId};
    }

    /**
     *
     * @param ref
     * @param interaction
     * @return  an array of String : first the database, then the interactionId
     */
    public static String[] extractInteractionId(Xref ref, Interaction interaction){
        String interactorId = null;
        String db = null;
        if (ref != null){
            interactorId = JSONValue.escape(ref.getId());
            db = JSONValue.escape(ref.getDatabase().getShortName());
        }
        else{
            interactorId = Integer.toString(interaction.hashCode());
            db = "generated";
        }
        return new String[]{db, interactorId};
    }

    public static void initialiseWriterFactoryWithMIJsonWriters(){
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions1 = createMIJsonWriterOptions(InteractionCategory.evidence, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonEvidenceWriter.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createMIJsonWriterOptions(InteractionCategory.modelled, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonModelledWriter.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createMIJsonWriterOptions(InteractionCategory.mixed, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonWriter.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createMIJsonWriterOptions(InteractionCategory.basic, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.LightMIJsonWriter.class, supportedOptions4);

        Map<String, Object> supportedOptions5 = createMIJsonWriterOptions(InteractionCategory.evidence, ComplexType.binary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonEvidenceWriter.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createMIJsonWriterOptions(InteractionCategory.modelled, ComplexType.binary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonModelledWriter.class, supportedOptions6);
        Map<String, Object> supportedOptions7 = createMIJsonWriterOptions(InteractionCategory.mixed, ComplexType.binary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonWriter.class, supportedOptions7);
        Map<String, Object> supportedOptions8 = createMIJsonWriterOptions(InteractionCategory.basic, ComplexType.binary);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.LightMIJsonWriter.class, supportedOptions8);
    }

    private static Map<String, Object> createMIJsonWriterOptions(InteractionCategory interactionCategory, ComplexType complexType) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, "interactionViewerJson");
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY, null);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        return supportedOptions4;
    }
}
