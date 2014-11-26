package psidev.psi.mi.jami.crosslink.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

/**
 * Crosslink CSV extension of Protein.
 * It contains a FileSourceContext
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CsvProtein extends DefaultProtein implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public CsvProtein(String name, CvTerm type) {
        super(name, type);
    }

    public CsvProtein(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public CsvProtein(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public CsvProtein(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public CsvProtein(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public CsvProtein(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public CsvProtein(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public CsvProtein(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public CsvProtein(String name) {
        super(name);
    }

    public CsvProtein(String name, String fullName) {
        super(name, fullName);
    }

    public CsvProtein(String name, Organism organism) {
        super(name, organism);
    }

    public CsvProtein(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public CsvProtein(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public CsvProtein(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public CsvProtein(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public CsvProtein(String name, String fullName, Organism organism, Xref uniqueId) {
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
        return "CSV Protein: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
