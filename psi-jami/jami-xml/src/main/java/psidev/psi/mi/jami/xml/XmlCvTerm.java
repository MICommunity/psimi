package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
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
public class XmlCvTerm extends AbstractXmlCvTerm{

    private NamesContainer names;
    private CvTermXrefAndIdentifierContainer xref;
    private Collection<Annotation> annotations;

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(required = true)
    public NamesContainer getNames() {
        if (names == null){
           names = new NamesContainer();
           names.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        return names;
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
        if (value == null){
            names = new NamesContainer();
            names.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        else {
            this.names = value;
            if (this.names.getShortLabel() == null){
                names.setShortLabel(PsiXmlUtils.UNSPECIFIED);
            }
        }
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(required = true)
    public CvTermXrefAndIdentifierContainer getXref() {
        if (xref != null){
            if (xref.isEmpty()){
                return null;
            }
        }
        return xref;
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
        this.xref = value;
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
    public Collection<XmlXref> getIdentifiers() {
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
    public Collection<XmlXref> getXrefs() {
        return getXrefContainer().getAllXrefs();
    }

    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            this.annotations = new ArrayList<Annotation>();
        }
        return this.annotations;
    }

    @XmlTransient
    public Collection<XmlAlias> getSynonyms() {
        return getNames().getAliases();
    }
}
