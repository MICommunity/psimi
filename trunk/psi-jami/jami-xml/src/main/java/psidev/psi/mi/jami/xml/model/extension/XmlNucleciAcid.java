package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultNucleicAcid;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of Nucleic acid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlNucleciAcid extends DefaultNucleicAcid implements ExtendedPsiXmlInteractor, FileSourceContext {
    private int id;
    private PsiXmlLocator sourceLocator;

    public XmlNucleciAcid(String name, CvTerm type) {
        super(name, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlNucleciAcid(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlNucleciAcid(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlNucleciAcid(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    public XmlNucleciAcid(String name) {
        super(name, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlNucleciAcid(String name, String fullName) {
        super(name, fullName, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlNucleciAcid(String name, Organism organism) {
        super(name, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlNucleciAcid(String name, String fullName, Organism organism) {
        super(name, fullName, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlNucleciAcid(String name, Xref uniqueId) {
        super(name, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlNucleciAcid(String name, Organism organism, Xref uniqueId) {
        super(name, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    @Override
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            super.setInteractorType(new XmlCvTerm(NucleicAcid.NULCEIC_ACID, new XmlXref(CvTermUtils.createPsiMiDatabase(),NucleicAcid.NULCEIC_ACID_MI, CvTermUtils.createIdentityQualifier())));
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
        XmlEntryContext.getInstance().registerInteractor(this.id, this);
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
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmlLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Nucleic Acid: "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
