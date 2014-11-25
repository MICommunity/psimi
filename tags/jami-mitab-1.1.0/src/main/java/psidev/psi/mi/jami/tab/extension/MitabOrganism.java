package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Mitab extension of Organism.
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabOrganism extends DefaultOrganism implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabOrganism(int taxId) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3);
    }

    public MitabOrganism(int taxId, String commonName) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3, commonName);
    }

    public MitabOrganism(int taxId, String commonName, String scientificName) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3, commonName, scientificName);
    }

    public MitabOrganism(int taxId, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3, cellType, tissue, compartment);
    }

    public MitabOrganism(int taxId, String commonName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3, commonName, cellType, tissue, compartment);
    }

    public MitabOrganism(int taxId, String commonName, String scientificName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super((taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId == -5 || taxId > 0) ? taxId : -3, commonName, scientificName, cellType, tissue, compartment);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Organism: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
