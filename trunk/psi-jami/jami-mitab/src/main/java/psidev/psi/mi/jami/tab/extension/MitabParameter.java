package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultModelledParameter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.math.BigDecimal;

/**
 * Mitab extension of Parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabParameter extends DefaultModelledParameter implements FileSourceContext {


    private FileSourceLocator sourceLocator;

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
        super(new MitabCvTerm(type!= null ? type : MitabUtils.UNKNOWN_TYPE), value != null ? value : "", unit != null ? new MitabCvTerm(unit) : null);
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab Parameter: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
