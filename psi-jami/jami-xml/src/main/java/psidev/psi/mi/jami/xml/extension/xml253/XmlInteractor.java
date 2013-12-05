package psidev.psi.mi.jami.xml.extension.xml253;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.annotation.*;

/**
 * The Xml implementation of Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "interactor", namespace = "net:sf:psidev:mi")
public class XmlInteractor extends AbstractXmlInteractor{

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlInteractor() {
    }

    public XmlInteractor(String name, CvTerm type) {
        super(name, type);
    }

    public XmlInteractor(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlInteractor(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlInteractor(String name) {
        super(name);
    }

    public XmlInteractor(String name, String fullName) {
        super(name, fullName);
    }

    public XmlInteractor(String name, Organism organism) {
        super(name, organism);
    }

    public XmlInteractor(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlInteractor(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlInteractor(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlInteractor(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlInteractor(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.NamesContainer }
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
     *     {@link psidev.psi.mi.jami.xml.extension.InteractorXrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public void setJAXBXref(InteractorXrefContainer value) {
        super.setJAXBXref(value);
    }

    @XmlElement(name = "interactorType", required = true, type = XmlCvTerm.class)
    public void setJAXBInteractorType(CvTerm interactorType) {
        super.setJAXBInteractorType(interactorType);
    }

    @XmlElement(name = "organism", type = XmlOrganism.class)
    public void setJAXBOrganism(Organism organism) {
        super.setJAXBOrganism(organism);
    }

    /**
     * Sets the value of the sequence property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlElement(name = "sequence")
    public void setSequence(String value) {
        super.setSequence(value);
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setJAXBId(value);
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link psidev.psi.mi.jami.xml.extension.XmlAnnotation }
     *
     */
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        super.setJAXBAttributeWrapper(wrapper);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getId()));
        }
    }
}
