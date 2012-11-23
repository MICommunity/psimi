package psidev.psi.mi.jami.model;

/**
 * A parameter which is defined by its type, base, exponent, factor, uncertainty and unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Parameter {

    public CvTerm getType();
    public void setType(CvTerm type);

    public short getBase();
    public void setBase(short base);

    public short getExponent();
    public void setExponent(short value);

    public double getFactor();
    public void setFactor(double value);

    public double getUncertainty();
    public void setUncertainty(double value);

    public CvTerm getUnit();
    public void setUnit(CvTerm unit);

}
