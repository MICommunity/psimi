package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of CvTerm.
 *
 * Does write annotations
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlOpenCvTerm extends AbstractXmlCvTerm{

    @XmlLocation
    @XmlTransient
    private Locator locator;

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
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(name = "names", required = true)
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    /**
     * Sets the value of the xrefContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref", required = true)
    public void setJAXBXref(CvTermXrefContainer value) {
        super.setJAXBXref(value);
    }

    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper){
        super.setAttributeWrapper(wrapper);
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
