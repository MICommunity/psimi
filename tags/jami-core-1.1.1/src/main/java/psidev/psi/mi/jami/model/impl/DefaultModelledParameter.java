package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.Publication;

import java.math.BigDecimal;

/**
 * Default implementation for ModelledParameter
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousParameterComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public class DefaultModelledParameter extends DefaultParameter implements ModelledParameter{

    private Publication publication;

    public DefaultModelledParameter(CvTerm type, ParameterValue value) {
        super(type, value);
    }

    public DefaultModelledParameter(CvTerm type, ParameterValue value, CvTerm unit) {
        super(type, value, unit);
    }

    public DefaultModelledParameter(CvTerm type, ParameterValue value, CvTerm unit, BigDecimal uncertainty) {
        super(type, value, unit, uncertainty);
    }

    public DefaultModelledParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty) {
        super(type, value, uncertainty);
    }

    public DefaultModelledParameter(CvTerm type, String value) throws IllegalParameterException {
        super(type, value);
    }

    public DefaultModelledParameter(CvTerm type, String value, CvTerm unit) throws IllegalParameterException {
        super(type, value, unit);
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
