package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.Publication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public class DefaultModelledParameter extends DefaultParameter implements ModelledParameter{

    private Collection<Publication> publications;

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

    protected void initialisePublications(){
        publications = new ArrayList<Publication>();
    }

    protected void initialiseExperimentsWith(Collection<Publication> publications){
        if (publications == null){
            this.publications = Collections.EMPTY_LIST;
        }
        else {
            this.publications = publications;
        }
    }

    public Collection<Publication> getPublications() {
        if (this.publications == null){
            initialisePublications();
        }
        return publications;
    }
}
