package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultMolecule;

/**
 * Mitab extension for Molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabMolecule extends DefaultMolecule implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabMolecule(String name, CvTerm type) {
        super(name, type);
    }

    public MitabMolecule(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public MitabMolecule(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public MitabMolecule(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public MitabMolecule(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public MitabMolecule(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public MitabMolecule(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public MitabMolecule(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public MitabMolecule(String name) {
        super(name);
    }

    public MitabMolecule(String name, String fullName) {
        super(name, fullName);
    }

    public MitabMolecule(String name, Organism organism) {
        super(name, organism);
    }

    public MitabMolecule(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabMolecule(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabMolecule(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabMolecule(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public MitabMolecule(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
