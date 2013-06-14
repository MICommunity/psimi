package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultFeature;

/**
 * A MitabFeature is a feature in MITAB with some free text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class MitabFeature extends DefaultFeature implements FileSourceContext{

    private String text;
    private MitabSourceLocator sourceLocator;

    public MitabFeature() {
        super();
    }

    public MitabFeature(CvTerm type) {
        super(type);
    }

    public MitabFeature(String type) {
        super(new MitabCvTerm(type));
    }

    public MitabFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
