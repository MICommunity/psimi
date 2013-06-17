package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultNucleicAcid;

/**
 * Mitab extension for NucleicAcid
 * It will contain a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabNucleicAcid extends DefaultNucleicAcid implements FileSourceContext{

    private MitabSourceLocator sourceLocator;

    public MitabNucleicAcid(String name, CvTerm type) {
        super(name, type);
    }

    public MitabNucleicAcid(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public MitabNucleicAcid(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public MitabNucleicAcid(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public MitabNucleicAcid(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public MitabNucleicAcid(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public MitabNucleicAcid(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public MitabNucleicAcid(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public MitabNucleicAcid(String name) {
        super(name);
    }

    public MitabNucleicAcid(String name, String fullName) {
        super(name, fullName);
    }

    public MitabNucleicAcid(String name, Organism organism) {
        super(name, organism);
    }

    public MitabNucleicAcid(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabNucleicAcid(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabNucleicAcid(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabNucleicAcid(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public MitabNucleicAcid(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(MitabSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
