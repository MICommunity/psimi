package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.xml.Xml25EntryContext;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of protein
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlProtein extends DefaultProtein implements ExtendedPsi25Interactor, FileSourceContext{

    private int id;
    private PsiXmLocator sourceLocator;

    public XmlProtein(String name, CvTerm type) {
        super(name, type);
    }

    public XmlProtein(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlProtein(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlProtein(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlProtein(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlProtein(String name) {
        super(name);
    }

    public XmlProtein(String name, String fullName) {
        super(name, fullName);
    }

    public XmlProtein(String name, Organism organism) {
        super(name, organism);
    }

    public XmlProtein(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlProtein(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlProtein(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlProtein(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlProtein(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
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
        Xml25EntryContext.getInstance().registerInteractor(this.id, this);
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
        return "Protein: "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
