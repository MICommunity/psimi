package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml implementation of Nucleic acid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class XmlNucleciAcid extends XmlPolymer implements NucleicAcid{

    public XmlNucleciAcid() {
    }

    public XmlNucleciAcid(String name, CvTerm type) {
        super(name, type);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlNucleciAcid(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlNucleciAcid(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlNucleciAcid(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlNucleciAcid(String name) {
        super(name);
    }

    public XmlNucleciAcid(String name, String fullName) {
        super(name, fullName);
    }

    public XmlNucleciAcid(String name, Organism organism) {
        super(name, organism);
    }

    public XmlNucleciAcid(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlNucleciAcid(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlNucleciAcid(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlNucleciAcid(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    @Override
    public void setXref(InteractorXrefContainer value) {
        if (value == null){
            this.xrefContainer = null;
        }
        else if (this.xrefContainer == null){
            this.xrefContainer = new NucleicAcidXrefContainer();
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
        else {
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().clear();
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
    }

    @Override
    public void initialiseXrefContainer() {
        this.xrefContainer = new NucleicAcidXrefContainer();
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(NucleicAcid.NULCEIC_ACID, NucleicAcid.NULCEIC_ACID_MI));
    }

    @XmlTransient
    public String getDdbjEmblGenbank() {
        if (xrefContainer == null){
           initialiseXrefContainer();
        }
        return ((NucleicAcidXrefContainer)xrefContainer).getDdbjEmblGenbank();
    }

    public void setDdbjEmblGenbank(String id) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((NucleicAcidXrefContainer)xrefContainer).setDdbjEmblGenbank(id);
    }

    @XmlTransient
    public String getRefseq() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((NucleicAcidXrefContainer)xrefContainer).getRefseq();
    }

    public void setRefseq(String id) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((NucleicAcidXrefContainer)xrefContainer).setRefseq(id);
    }
}
