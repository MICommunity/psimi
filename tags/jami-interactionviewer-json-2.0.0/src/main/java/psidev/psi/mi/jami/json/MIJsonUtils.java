package psidev.psi.mi.jami.json;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;

import java.io.IOException;
import java.io.Writer;

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
        else if (interaction instanceof InteractionEvidence && ((InteractionEvidence)interaction).getImexId() != null){
           interactorId = ((InteractionEvidence)interaction).getImexId();
            db = "imex";
        }
        else{
            interactorId = Integer.toString(interaction.hashCode());
            db = "generated";
        }
        return new String[]{db, interactorId};
    }

    /**
     *
     * @param ref
     * @param interaction
     * @param number a suffix
     * @return  an array of String : first the database, then the interactionId, then a number to append
     */
    public static String[] extractBinaryInteractionId(Xref ref, BinaryInteraction interaction, Integer number){
        String interactorId = null;
        String db = null;
        if (ref != null){
            interactorId = JSONValue.escape(ref.getId())+(number != null ? "_"+number:"");
            db = JSONValue.escape(ref.getDatabase().getShortName());
        }
        else if (interaction instanceof InteractionEvidence && ((InteractionEvidence)interaction).getImexId() != null){
            interactorId = ((InteractionEvidence)interaction).getImexId()+(number != null ? "_"+number:"");
            db = "imex";
        }
        else{
            interactorId = Integer.toString(interaction.hashCode())+(number != null ? "_"+number:"");
            db = "generated";
        }
        return new String[]{db, interactorId};
    }
}
