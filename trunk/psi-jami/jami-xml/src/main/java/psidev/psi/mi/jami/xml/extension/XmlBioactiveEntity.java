package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.Xml25EntryContext;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of BioactiveEntity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlBioactiveEntity extends DefaultBioactiveEntity implements ExtendedPsi25Interactor,FileSourceContext{

    private int id;
    private PsiXmLocator sourceLocator;

    public XmlBioactiveEntity(String name, CvTerm type) {
        super(name, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())));
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type != null ? type : new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name) {
        super(name, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
        );
    }

    public XmlBioactiveEntity(String name, String fullName) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
        );
    }

    public XmlBioactiveEntity(String name, Organism organism) {
        super(name, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
    }

    public XmlBioactiveEntity(String name, Xref uniqueId) {
        super(name, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , uniqueId);
    }

    public XmlBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
        );
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    @Override
    /**
     * Sets the interactor type of this bioactive entity.
     * If the given interactorType is null, it sets the interactorType to 'bioactive entity'(MI:1100)
     */
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            super.setInteractorType(new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, new XmlXref(CvTermUtils.createPsiMiDatabase(), BioactiveEntity.BIOACTIVE_ENTITY_MI, CvTermUtils.createIdentityQualifier())));
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
        return "Bioactive entity: "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
