package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

/**
 * Mitab extension of ModelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public class MitabModelledFeature extends DefaultModelledFeature implements MitabFeature<ModelledEntity,ModelledFeature>, FileSourceContext{
    private String text;
    private FileSourceLocator sourceLocator;

    public MitabModelledFeature(ModelledEntity participant) {
        super(participant);
    }

    public MitabModelledFeature(ModelledEntity participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public MitabModelledFeature(ModelledEntity participant, CvTerm type) {
        super(participant, type);
    }

    public MitabModelledFeature(ModelledEntity participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public MitabModelledFeature() {
        super();
    }

    public MitabModelledFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public MitabModelledFeature(CvTerm type) {
        super(type);
    }

    public MitabModelledFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
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

    @Override
    public String toString() {
        return "Feature: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
