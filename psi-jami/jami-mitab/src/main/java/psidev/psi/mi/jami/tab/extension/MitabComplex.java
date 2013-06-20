package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultComplex;

/**
 * Mitab extension for Complex.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabComplex extends DefaultComplex implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabComplex(String name, CvTerm interactorType) {
        super(name, interactorType);
    }

    public MitabComplex(String name, String fullName, CvTerm interactorType) {
        super(name, fullName, interactorType);
    }

    public MitabComplex(String name, CvTerm interactorType, Organism organism) {
        super(name, interactorType, organism);
    }

    public MitabComplex(String name, String fullName, CvTerm interactorType, Organism organism) {
        super(name, fullName, interactorType, organism);
    }

    public MitabComplex(String name, CvTerm interactorType, Xref uniqueId) {
        super(name, interactorType, uniqueId);
    }

    public MitabComplex(String name, String fullName, CvTerm interactorType, Xref uniqueId) {
        super(name, fullName, interactorType, uniqueId);
    }

    public MitabComplex(String name, CvTerm interactorType, Organism organism, Xref uniqueId) {
        super(name, interactorType, organism, uniqueId);
    }

    public MitabComplex(String name, String fullName, CvTerm interactorType, Organism organism, Xref uniqueId) {
        super(name, fullName, interactorType, organism, uniqueId);
    }

    public MitabComplex(String name) {
        super(name);
    }

    public MitabComplex(String name, String fullName) {
        super(name, fullName);
    }

    public MitabComplex(String name, Organism organism) {
        super(name, organism);
    }

    public MitabComplex(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabComplex(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabComplex(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabComplex(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabComplex(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public FileSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
