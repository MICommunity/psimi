package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultGene;
import psidev.psi.mi.jami.utils.CvTermUtils;
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
        super(name, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlGene(String name, String fullName) {
        super(name, fullName, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlGene(String name, Organism organism) {
        super(name, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                organism);
    }

    public XmlGene(String name, String fullName, Organism organism) {
        super(name, fullName, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                organism);
    }

    public XmlGene(String name, Xref uniqueId) {
        super(name, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                uniqueId);
    }

    public XmlGene(String name, String fullName, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                uniqueId);
    }

    public XmlGene(String name, Organism organism, Xref uniqueId) {
        super(name, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                organism, uniqueId);
    }

    public XmlGene(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())),
                organism, uniqueId);
    }

    public XmlGene(String name, String fullName, CvTerm type, Xref ensembl) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier()))
                , ensembl);
    }

    public XmlGene(String name, CvTerm type, Xref ensembl) {
        super(name, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), ensembl);
    }

    public XmlGene(String name, CvTerm type, Organism organism, Xref ensembl) {
        super(name, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), organism, ensembl);
    }

    public XmlGene(String name, String fullName, CvTerm type, Organism organism, Xref ensembl) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), organism, ensembl);
    }

    public XmlGene(String name, CvTerm type, Xref uniqueId, Xref ensembl) {
        super(name, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), uniqueId, ensembl);
    }

    public XmlGene(String name, String fullName, CvTerm type, Xref uniqueId, Xref ensembl) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), uniqueId, ensembl);
    }

    public XmlGene(String name, CvTerm type, Organism organism, Xref uniqueId, Xref ensembl) {
        super(name, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId, ensembl);
    }

    public XmlGene(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId, Xref ensembl) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId, ensembl);
    }

    @Override
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            super.setInteractorType(new XmlCvTerm(Gene.GENE, new XmlXref(CvTermUtils.createPsiMiDatabase(),Gene.GENE_MI, CvTermUtils.createIdentityQualifier())));
        }
        else {
            super.setInteractorType(interactorType);
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
        return "Gene: "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
