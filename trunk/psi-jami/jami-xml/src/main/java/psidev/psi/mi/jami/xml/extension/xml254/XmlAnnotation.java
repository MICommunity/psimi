package psidev.psi.mi.jami.xml.extension.xml254;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.extension.AbstractXmlAnnotation;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;

import javax.xml.bind.annotation.*;

/**
 * Xml implementation of an Annotation
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlRootElement(name = "attribute", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlAnnotation extends AbstractXmlAnnotation {

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public XmlAnnotation() {
    }

    public XmlAnnotation(CvTerm topic, String value) {
        super(topic, value);
    }

    public XmlAnnotation(CvTerm topic) {
        super(topic);
    }

    @XmlValue
    public void setValue(String value) {
        super.setValue(value);
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "name", required = true)
    public void setJAXBName(String value) {
        super.setJAXBName(value);
    }

    /**
     * Sets the value of the nameAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "nameAc")
    public void setJAXBNameAc(String value) {
        super.setJAXBNameAc(value);
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
