package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.impl.DefaultFeature;

/**
 * A DefaultMitabFeature is a feature in MITAB with some free text.
 *
 * It can be ModelledFeature of FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class DefaultMitabFeature extends DefaultFeature implements MitabFeature<Entity, Feature>, FileSourceContext{

    private String text;
    private FileSourceLocator sourceLocator;

    public DefaultMitabFeature() {
        super();
    }

    public DefaultMitabFeature(CvTerm type) {
        super(type);
    }

    public DefaultMitabFeature(String type) {
        super(new MitabCvTerm(type));
    }

    public DefaultMitabFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
