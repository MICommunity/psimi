package psidev.psi.mi.jami.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;

/**
 * CrossLink CSV extension for FeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public class CsvFeatureEvidence extends DefaultFeatureEvidence implements FileSourceContext{
    private FileSourceLocator sourceLocator;

    public CsvFeatureEvidence(ParticipantEvidence participant) {
        super(participant);
    }

    public CsvFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public CsvFeatureEvidence(ParticipantEvidence participant, CvTerm type) {
        super(participant, type);
    }

    public CsvFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public CsvFeatureEvidence() {
        super();
    }

    public CsvFeatureEvidence(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public CsvFeatureEvidence(CvTerm type) {
        super(type);
    }

    public CsvFeatureEvidence(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
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
