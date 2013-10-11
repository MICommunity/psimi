package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;

/**
 * Xml parameter wrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public class XmlParameterWrapper extends XmlModelledParameter{

    private Parameter parameter;

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
}
