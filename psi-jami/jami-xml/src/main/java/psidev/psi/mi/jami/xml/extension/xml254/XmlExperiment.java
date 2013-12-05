package psidev.psi.mi.jami.xml.extension.xml254;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.annotation.*;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlRootElement(name = "experimentDescription", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlExperiment extends AbstractXmlExperiment{

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlExperiment() {
    }

    public XmlExperiment(Publication publication) {
        super(publication);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod) {
        super(publication, interactionDetectionMethod);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism) {
        super(publication, interactionDetectionMethod, organism);
    }

    /**
     * Sets the value of the namesContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.NamesContainer }
     *
     */
    @XmlElement(name = "names")
    public void setNames(NamesContainer value) {
        super.setNames(value);
    }

    @XmlElement(name = "bibref", required = true, type = BibRef.class)
    public void setJAXBPublication(Publication publication) {
        super.setJAXBPublication(publication);
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.XrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public void setJAXBXref(ExperimentXrefContainer value) {
        super.setJAXBXref(value);
    }

    @XmlElement(name="hostOrganismList")
    public void setJAXBHostOrganismWrapper(JAXBHostOrganismWrapper wrapper) {
        super.setJAXBHostOrganismWrapper(wrapper);
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.XmlCvTerm }
     *
     */
    @XmlElement(name = "interactionDetectionMethod", required = true, type = XmlCvTerm.class)
    public void setJAXBInteractionDetectionMethod(CvTerm value) {
        super.setJAXBInteractionDetectionMethod(value);
    }

    /**
     * Sets the value of the participantIdentificationMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.XmlCvTerm }
     *
     */
    @XmlElement(name = "participantIdentificationMethod", type = XmlCvTerm.class)
    public void setParticipantIdentificationMethod(CvTerm value) {
        super.setParticipantIdentificationMethod(value);
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.extension.XmlCvTerm }
     *
     */
    @XmlElement(name = "featureDetectionMethod", type = XmlCvTerm.class)
    public void setFeatureDetectionMethod(CvTerm value) {
        super.setFeatureDetectionMethod(value);
    }

    @XmlElement(name="confidenceList")
    public void setJAXBConfidenceWrapper(JAXBConfidenceWrapper wrapper) {
        super.setJAXBConfidenceWrapper(wrapper);
    }

    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        super.setJAXBAttributeWrapper(wrapper);
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setJAXBId(value);
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
