package psidev.psi.mi.jami.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;

/**
 * CSV extension for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CsvInteractionEvidence extends DefaultInteractionEvidence implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public CsvInteractionEvidence(Experiment experiment) {
        super(experiment);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName) {
        super(experiment, shortName);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName, Source source) {
        super(experiment, shortName, source);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName, CvTerm type) {
        super(experiment, shortName, type);
    }

    public CsvInteractionEvidence(Experiment experiment, Xref imexId) {
        super(experiment, imexId);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName, Xref imexId) {
        super(experiment, shortName, imexId);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(experiment, shortName, source, imexId);
    }

    public CsvInteractionEvidence(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(experiment, shortName, type, imexId);
    }

    public CsvInteractionEvidence(Xref imexId) {
        super(imexId);
    }

    public CsvInteractionEvidence(String shortName, Xref imexId) {
        super(shortName, imexId);
    }

    public CsvInteractionEvidence(String shortName, Source source, Xref imexId) {
        super(shortName, source, imexId);
    }

    public CsvInteractionEvidence(String shortName, CvTerm type, Xref imexId) {
        super(shortName, type, imexId);
    }

    public CsvInteractionEvidence() {
        super();
    }

    public CsvInteractionEvidence(String shortName) {
        super(shortName);
    }

    public CsvInteractionEvidence(String shortName, CvTerm type) {
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
