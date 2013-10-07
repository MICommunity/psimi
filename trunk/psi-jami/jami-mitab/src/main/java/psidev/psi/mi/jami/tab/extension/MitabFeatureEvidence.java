package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;

/**
 * Mitab extension for FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public class MitabFeatureEvidence extends DefaultFeatureEvidence implements MitabFeature<ExperimentalEntity, FeatureEvidence>, FileSourceContext{
    private String text;
    private FileSourceLocator sourceLocator;

    public MitabFeatureEvidence(ParticipantEvidence participant) {
        super(participant);
    }

    public MitabFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public MitabFeatureEvidence(ParticipantEvidence participant, CvTerm type) {
        super(participant, type);
    }

    public MitabFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public MitabFeatureEvidence() {
        super();
    }

    public MitabFeatureEvidence(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public MitabFeatureEvidence(CvTerm type) {
        super(type);
    }

    public MitabFeatureEvidence(String shortName, String fullName, CvTerm type) {
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
}
