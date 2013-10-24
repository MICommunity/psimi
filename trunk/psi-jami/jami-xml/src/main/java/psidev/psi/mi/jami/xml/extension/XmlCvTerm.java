package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.*;

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
    private Locator locator;

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
    public NamesContainer getJAXBNames() {
        return super.getNamesContainer();
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
