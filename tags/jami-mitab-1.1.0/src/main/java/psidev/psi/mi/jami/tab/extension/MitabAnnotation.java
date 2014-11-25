package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

/**
 * Extension of Annotation for Mitab with a sourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabAnnotation extends DefaultAnnotation implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabAnnotation(CvTerm topic) {
        super(topic);
    }

    public MitabAnnotation(CvTerm topic, String value) {
        super(topic, value);
    }

    public MitabAnnotation(String topic, String value) {
        super(new MitabCvTerm(topic != null ? topic : MitabUtils.UNKNOWN_DATABASE), value);
    }

    public MitabAnnotation(CvTerm topic, FileSourceLocator sourceLocator) {
        super(topic);
        this.sourceLocator = sourceLocator;
    }

    public MitabAnnotation(CvTerm topic, String value, FileSourceLocator sourceLocator) {
        super(topic, value);
        this.sourceLocator = sourceLocator;
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Mitab Annotation: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
