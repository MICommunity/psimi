package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultGene;

/**
 * Mitab extension for Gene.
 * It contains FileSourceContext
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabGene extends DefaultGene implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public MitabGene(String name) {
        super(name);
    }

    public MitabGene(String name, String fullName) {
        super(name, fullName);
    }

    public MitabGene(String name, Organism organism) {
        super(name, organism);
    }

    public MitabGene(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public MitabGene(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public MitabGene(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public MitabGene(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public MitabGene(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public MitabGene(String name, String fullName, String ensembl) {
        super(name, fullName, ensembl);
    }

    public MitabGene(String name, Organism organism, String ensembl) {
        super(name, organism, ensembl);
    }

    public MitabGene(String name, String fullName, Organism organism, String ensembl) {
        super(name, fullName, organism, ensembl);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
