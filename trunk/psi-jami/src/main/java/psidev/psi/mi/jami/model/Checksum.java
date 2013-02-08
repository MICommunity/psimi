package psidev.psi.mi.jami.model;

/**
 * Checksum is a value for checking consistency of the data and can also be used for identifying objects.
 * Ex: ROGID, CROGID, RIGID, CRC64, ...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Checksum {

    public static String SMILE = "smiles string";
    public static String SMILE_MI = "MI:2039";
    public static String INCHI = "stamdard inchi";
    public static String INCHI_MI = "MI:2010";
    public static String INCHI_KEY = "standard inchi key";
    public static String INCHI_KEY_MI = "MI:1101";
    public static String ROGID = "rogid";
    public static String ROGID_MI = "MI:xxxx";

    /**
     * The method is a controlled vocabulary term and cannot be null
     * Ex: ROGID, CROGID, RIGID, CRC64, ...
     * @return the method used to compute this checksum.
     */
    public CvTerm getMethod();

    /**
     * The checksum cannot be null.
     * Ex: ROGID = UcdngwpTSS6hG/pvQGgpp40u67I9606|crogid:UcdngwpTSS6hG/pvQGgpp40u67I9606
     * @return the checksum
     */
    public String getValue();
}
