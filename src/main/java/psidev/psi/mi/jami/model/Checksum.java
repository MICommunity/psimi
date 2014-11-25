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

    public static final String SMILE = "smiles string";
    public static final String SMILE_SHORT = "smile";
    public static final String SMILE_MI = "MI:2039";
    public static final String INCHI = "stamdard inchi";
    public static final String INCHI_SHORT = "inchi id";
    public static final String INCHI_MI = "MI:2010";
    public static final String STANDARD_INCHI_KEY = "standard inchi key";
    public static final String STANDARD_INCHI_KEY_MI = "MI:1101";
    public static final String ROGID = "rogid";
    public static final String ROGID_MI = "MI:1333";
    public static final String RIGID = "rigid";
    public static final String RIGID_MI = "MI:1334";
    public static final String IRIGID = "irigid";
    public static final String IROGID = "irogid";
    public static final String INCHI_KEY = "inchi key";
    public static final String INCHI_KEY_MI = "MI:0970";
    public static final String CHECKUM = "checksum";
    public static final String CHECKSUM_MI = "MI:1212";

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
