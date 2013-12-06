package psidev.psi.mi.jami.json;

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
}
