package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.Collection;

/**
 * Xml implementation of CvTerm.
 *
 * Does not write annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "cvType", propOrder = {
        "names",
        "xref"
})
@XmlSeeAlso({
        ExperimentalCvTerm.class
})
public class XmlCvTerm extends AbstractXmlCvTerm{

    public XmlCvTerm() {
    }

    public XmlCvTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
    }

    public XmlCvTerm(String shortName) {
        super(shortName);
    }

    public XmlCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
    }

    public XmlCvTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public XmlCvTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(name = "names", required = true)
    public NamesContainer getNames() {
        return super.getNamesContainer();
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    public void setNames(NamesContainer value) {
        super.setNamesContainer(value);
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref", required = true)
    public CvTermXrefAndIdentifierContainer getXref() {
        return super.getXrefContainer();
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link XrefContainer }
     *
     */
    public void setXref(CvTermXrefAndIdentifierContainer value) {
        super.setXrefContainer(value);
    }

    @XmlTransient
    public String getShortName() {
        return getNames().getShortLabel();
    }

    public void setShortName(String name) {
        getNames().setShortLabel(name != null ? name : PsiXmlUtils.UNSPECIFIED);
    }

    @XmlTransient
    public String getFullName() {
        return getNames().getFullName();
    }

    public void setFullName(String name) {
        getNames().setFullName(name);
    }

    @XmlTransient
    public Collection<Xref> getIdentifiers() {
        return getXrefContainer().getAllIdentifiers();
    }

    @XmlTransient
    public String getMIIdentifier() {
        return getXrefContainer().getMIIdentifier();
    }

    @XmlTransient
    public String getMODIdentifier() {
        return getXrefContainer().getMODIdentifier();
    }

    @XmlTransient
    public String getPARIdentifier() {
        return getXrefContainer().getPARIdentifier();
    }

    public void setMIIdentifier(String mi) {
        getXrefContainer().setMIIdentifier(mi);
    }

    public void setMODIdentifier(String mod) {
        getXrefContainer().setMODIdentifier(mod);
    }

    public void setPARIdentifier(String par) {
        getXrefContainer().setPARIdentifier(par);
    }

    @XmlTransient
    public Collection<Xref> getXrefs() {
        return getXrefContainer().getAllXrefs();
    }

    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    @XmlTransient
    public Collection<Alias> getSynonyms() {
        return getNames().getAliases();
    }
}
