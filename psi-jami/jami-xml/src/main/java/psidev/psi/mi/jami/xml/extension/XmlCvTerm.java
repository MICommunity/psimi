package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
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
 * Does not write annotations
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "cvType", propOrder = {
        "JAXBNames",
        "JAXBXref"
})
@XmlSeeAlso({
        ExperimentalCvTerm.class
})
public class XmlCvTerm extends AbstractXmlCvTerm{

    @XmlLocation
    @XmlTransient
    protected Locator locator;

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

    public String getShortName() {
        return getNamesContainer().getJAXBShortLabel();
    }

    public void setShortName(String name) {
        getNamesContainer().setJAXBShortLabel(name != null ? name : PsiXmlUtils.UNSPECIFIED);
    }

    public String getFullName() {
        return getNamesContainer().getJAXBFullName();
    }

    public void setFullName(String name) {
        getNamesContainer().setJAXBFullName(name);
    }

    public Collection<Xref> getIdentifiers() {
        return getXrefContainer().getAllIdentifiers();
    }

    public String getMIIdentifier() {
        return getXrefContainer().getMIIdentifier();
    }

    public String getMODIdentifier() {
        return getXrefContainer().getMODIdentifier();
    }

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

    public Collection<Xref> getXrefs() {
        return getXrefContainer().getAllXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    public Collection<Alias> getSynonyms() {
        return getNamesContainer().getJAXBAliases();
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
    public NamesContainer getJAXBNames() {
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
    public void setJAXBNames(NamesContainer value) {
        super.setNamesContainer(value);
    }

    /**
     * Gets the value of the xrefContainer property.
     *
     * @return
     *     possible object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref", required = true)
    public CvTermXrefContainer getJAXBXref() {
        if (super.getXrefContainer().isEmpty()){
            return null;
        }
        return super.getXrefContainer();
    }

    /**
     * Sets the value of the xrefContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link XrefContainer }
     *
     */
    public void setJAXBXref(CvTermXrefContainer value) {
        super.setXrefContainer(value);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null));
        }
    }
}
