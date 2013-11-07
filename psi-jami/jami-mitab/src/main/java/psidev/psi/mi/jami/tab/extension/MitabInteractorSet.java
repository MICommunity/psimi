package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractorSet;

/**
 * The mitab extension for InteractorSet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class MitabInteractorSet extends DefaultInteractorSet implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabInteractorSet(String name, CvTerm type) {
        super(name, type);
    }

    public MitabInteractorSet(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public MitabInteractorSet(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public MitabInteractorSet(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public MitabInteractorSet(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public MitabInteractorSet(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public MitabInteractorSet(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public MitabInteractorSet(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public MitabInteractorSet(String name) {
        super(name);
    }

    public MitabInteractorSet(String name, String fullName) {
        super(name, fullName);
    }

    public MitabInteractorSet(String name, Organism organism) {
        super(name, organism);
    }

    public MitabInteractorSet(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabInteractorSet(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabInteractorSet(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabInteractorSet(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabInteractorSet(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Interactor set: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
