package psidev.psi.mi.jami.model;

/**
 * Checksum is a value for checking consistency of the data and can also be used for identifying objects.
 * (ex: crc64, rogid, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Checksum {

    /**
     * The method is a controlled vocabulary term and cannot be null
     * @return the method used to compute this checksum.
     */
    public CvTerm getMethod();

    /**
     * The checksum cannot be null.
     * @return the checksum
     */
    public String getValue();
}
