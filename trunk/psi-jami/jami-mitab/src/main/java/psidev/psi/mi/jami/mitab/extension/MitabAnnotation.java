package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;

/**
 * Extension of Annotation for Mitab with a sourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabAnnotation extends DefaultAnnotation implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabAnnotation(CvTerm topic) {
        super(topic);
    }

    public MitabAnnotation(CvTerm topic, String value) {
        super(topic, value);
    }

    public MitabAnnotation(String topic, String value) {
        super(new MitabCvTerm(topic), value);
    }

    public MitabAnnotation(CvTerm topic, MitabSourceLocator sourceLocator) {
        super(topic);
        this.sourceLocator = sourceLocator;
    }

    public MitabAnnotation(CvTerm topic, String value, MitabSourceLocator sourceLocator) {
        super(topic, value);
        this.sourceLocator = sourceLocator;
    }

    public MitabSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }
}
