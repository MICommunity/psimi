package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;

/**
 * Mitab extension for interactor.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabInteractor extends DefaultInteractor implements FileSourceContext{
    private FileSourceLocator sourceLocator;

    public MitabInteractor(String name, CvTerm type) {
        super(name, type);
    }

    public MitabInteractor(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public MitabInteractor(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public MitabInteractor(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public MitabInteractor(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public MitabInteractor(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public MitabInteractor(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public MitabInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public MitabInteractor(String name) {
        super(name);
    }

    public MitabInteractor(String name, String fullName) {
        super(name, fullName);
    }

    public MitabInteractor(String name, Organism organism) {
        super(name, organism);
    }

    public MitabInteractor(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabInteractor(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabInteractor(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabInteractor(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabInteractor(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Interactor: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
