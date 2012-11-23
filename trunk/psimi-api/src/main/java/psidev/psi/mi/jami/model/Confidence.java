package psidev.psi.mi.jami.model;

/**
 * A Confidence gives information about how reliable an object is. It is defined by a type, a value and maybe a unit as well.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Confidence {

    public CvTerm getType();
    public void setType(CvTerm type);

    public String getValue();
    public void setValue(String value);

    public CvTerm getUnit();
    public void setUnit(CvTerm unit);
}
