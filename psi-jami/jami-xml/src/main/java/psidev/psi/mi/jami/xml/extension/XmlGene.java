package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultGene;
import psidev.psi.mi.jami.xml.Xml25EntryContext;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of a Gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlGene extends DefaultGene implements ExtendedPsi25Interactor, FileSourceContext{
    private int id;
    private PsiXmLocator sourceLocator;

    public XmlGene(String name) {
        super(name);
    }

    public XmlGene(String name, String fullName) {
        super(name, fullName);
    }

    public XmlGene(String name, Organism organism) {
        super(name, organism);
    }

    public XmlGene(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlGene(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlGene(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlGene(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlGene(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public XmlGene(String name, String fullName, String ensembl) {
        super(name, fullName);

        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    public XmlGene(String name, Organism organism, String ensembl) {
        super(name, organism);
        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    public XmlGene(String name, String fullName, Organism organism, String ensembl) {
        super(name, fullName, organism);
        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    /**
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        this.id = value;
        Xml25EntryContext.getInstance().registerObject(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
        }
    }

    public void setSourceLocator(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Gene: "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
