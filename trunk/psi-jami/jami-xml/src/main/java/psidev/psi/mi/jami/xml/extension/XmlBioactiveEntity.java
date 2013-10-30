package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

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
        super(name, type);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name) {
        super(name, CvTermUtils.createBioactiveEntityType());
    }

    public XmlBioactiveEntity(String name, String fullName) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
    }

    public XmlBioactiveEntity(String name, Organism organism) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public XmlBioactiveEntity(String name, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public XmlBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
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
        XmlEntryContext.getInstance().getMapOfReferencedObjects().put(this.id, this);
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
}
