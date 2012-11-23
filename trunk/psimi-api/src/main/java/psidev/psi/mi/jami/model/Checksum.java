package psidev.psi.mi.jami.model;

/**
 * Checksum of an object. It is defined by a method and a value
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Checksum {

    public CvTerm getMethod();
    public void setMethod(CvTerm method);

    public String getValue();
    public void setValue(String value);
}
