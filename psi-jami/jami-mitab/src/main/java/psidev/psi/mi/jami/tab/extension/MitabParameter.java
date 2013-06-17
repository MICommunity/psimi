package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultModelledParameter;

import java.math.BigDecimal;

/**
 * Mitab extension of Parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabParameter extends DefaultModelledParameter implements FileSourceContext {


    private MitabSourceLocator sourceLocator;

    public MitabParameter(CvTerm type, ParameterValue value) {
        super(type, value);
    }

    public MitabParameter(CvTerm type, ParameterValue value, CvTerm unit) {
        super(type, value, unit);
    }

    public MitabParameter(CvTerm type, ParameterValue value, CvTerm unit, BigDecimal uncertainty) {
        super(type, value, unit, uncertainty);
    }

    public MitabParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty) {
        super(type, value, uncertainty);
    }

    public MitabParameter(CvTerm type, String value) throws IllegalParameterException {
        super(type, value);
    }

    public MitabParameter(CvTerm type, String value, CvTerm unit) throws IllegalParameterException {
        super(type, value, unit);
    }

    public MitabParameter(String type, String value, String unit) throws IllegalParameterException {
        super(new MitabCvTerm(type), value, unit != null ? new MitabCvTerm(unit) : null);
    }

    public MitabSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
