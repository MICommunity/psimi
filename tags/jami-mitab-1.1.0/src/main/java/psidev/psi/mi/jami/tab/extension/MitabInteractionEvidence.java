package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;

/**
 * Mitab extension for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabInteractionEvidence extends DefaultInteractionEvidence implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public MitabInteractionEvidence(Experiment experiment) {
        super(experiment);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName) {
        super(experiment, shortName);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName, Source source) {
        super(experiment, shortName, source);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName, CvTerm type) {
        super(experiment, shortName, type);
    }

    public MitabInteractionEvidence(Experiment experiment, Xref imexId) {
        super(experiment, imexId);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName, Xref imexId) {
        super(experiment, shortName, imexId);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(experiment, shortName, source, imexId);
    }

    public MitabInteractionEvidence(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(experiment, shortName, type, imexId);
    }

    public MitabInteractionEvidence(Xref imexId) {
        super(imexId);
    }

    public MitabInteractionEvidence(String shortName, Xref imexId) {
        super(shortName, imexId);
    }

    public MitabInteractionEvidence(String shortName, Source source, Xref imexId) {
        super(shortName, source, imexId);
    }

    public MitabInteractionEvidence(String shortName, CvTerm type, Xref imexId) {
        super(shortName, type, imexId);
    }

    public MitabInteractionEvidence() {
        super();
    }

    public MitabInteractionEvidence(String shortName) {
        super(shortName);
    }

    public MitabInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Interaction: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
