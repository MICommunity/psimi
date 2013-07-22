package psidev.psi.mi.jami.xml;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.Collection;

/**
 * Xml implementation of CvTerm.
 *
 * Does write annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "openCvType", propOrder = {
        "names",
        "xref",
        "annotations"
})
@XmlSeeAlso({
        XmlSource.class
})
public class XmlOpenCvTerm extends AbstractXmlCvTerm{

    public XmlOpenCvTerm() {
    }

    public XmlOpenCvTerm(String shortName) {
        super(shortName);
    }

    public XmlOpenCvTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
    }

    public XmlOpenCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
    }

    public XmlOpenCvTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public XmlOpenCvTerm(String shortName, String fullName, Xref ontologyId) {
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

    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute")
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    @XmlTransient
    public Collection<Alias> getSynonyms() {
        return getNames().getAliases();
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    public void setSaxLocator(Locator sourceLocator) {
        super.setSaxLocator(sourceLocator);
    }

    @Override
    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return super.getSourceLocator();
    }
}
