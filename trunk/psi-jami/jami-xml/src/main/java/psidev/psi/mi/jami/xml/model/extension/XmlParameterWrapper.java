package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;

/**
 * Xml parameter wrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlParameterWrapper implements ModelledParameter {

    private Parameter parameter;
    private Publication publication;

    public XmlParameterWrapper(Parameter param){
        if (param == null){
            throw new IllegalArgumentException("A parameter wrapper needs a non null Parameter");
        }
        this.parameter = param;
    }

    @Override
    public CvTerm getType() {
        return this.parameter.getType();
    }

    @Override
    public BigDecimal getUncertainty() {
        return this.parameter.getUncertainty();
    }

    @Override
    public CvTerm getUnit() {
        return this.parameter.getUnit();
    }

    @Override
    public ParameterValue getValue() {
        return this.parameter.getValue();
    }

    @Override
    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @Override
    public String toString() {
        return this.parameter.toString();
    }
}
