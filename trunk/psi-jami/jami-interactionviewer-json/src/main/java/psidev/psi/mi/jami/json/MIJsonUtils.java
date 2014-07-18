package psidev.psi.mi.jami.json;

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

    public static void writeStartObject(String objectName, Writer writer) throws IOException {
        writePropertyKey(objectName, writer);
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
}
