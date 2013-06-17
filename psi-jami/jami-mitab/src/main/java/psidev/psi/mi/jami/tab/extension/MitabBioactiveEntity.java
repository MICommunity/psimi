package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;

/**
 * Mitab extension for BioactiveEntity.
 * It contains a file sourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabBioactiveEntity extends DefaultBioactiveEntity implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabBioactiveEntity(String name, CvTerm type) {
        super(name, type);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public MitabBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public MitabBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public MitabBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type, uniqueChebi);
    }

    public MitabBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type, organism, uniqueChebi);
    }

    public MitabBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type, organism, uniqueChebi);
    }

    public MitabBioactiveEntity(String name) {
        super(name);
    }

    public MitabBioactiveEntity(String name, String fullName) {
        super(name, fullName);
    }

    public MitabBioactiveEntity(String name, Organism organism) {
        super(name, organism);
    }

    public MitabBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabBioactiveEntity(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public MitabBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, uniqueChebi);
    }

    public MitabBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, organism, uniqueChebi);
    }

    public MitabBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, organism, uniqueChebi);
    }

    public MitabSourceLocator getSourceLocator() {
        return this.sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
