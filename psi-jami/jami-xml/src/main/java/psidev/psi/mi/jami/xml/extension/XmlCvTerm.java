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

    @Override
    @XmlElement(name = "names", required = true)
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref", required = true)
    public void setJAXBXref(CvTermXrefContainer value) {
        super.setJAXBXref(value);
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
