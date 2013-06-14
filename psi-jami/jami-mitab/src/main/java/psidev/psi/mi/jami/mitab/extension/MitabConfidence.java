package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledConfidence;

/**
 * A MitabConfidence is a confidence with some text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabConfidence extends DefaultModelledConfidence implements FileSourceContext{

    private String text;
    private MitabSourceLocator sourceLocator;

    public MitabConfidence(CvTerm type, String value) {
        super(type, value);
    }

    public MitabConfidence(CvTerm type, String value, String text) {
        super(type, value);
        this.text = text;
    }

    public MitabConfidence(String type, String value, String text) {
        super(new MitabCvTerm(type), value);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }
}
