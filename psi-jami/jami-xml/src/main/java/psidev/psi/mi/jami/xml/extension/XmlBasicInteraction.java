package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.AbstractInteractionAttributeList;
import psidev.psi.mi.jami.xml.AbstractInteractionParticipantList;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Xml implementation of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interaction", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBParticipants",
        "JAXBInferredInteractions",
        "JAXBInteractionTypes",
        "JAXBIntraMolecular",
        "JAXBAttributes"
})
public class XmlBasicInteraction extends AbstractXmlInteraction<Participant>{

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBAttributeList jaxbAttributeList;
    private JAXBParticipantList jaxbParticipantList;

    public XmlBasicInteraction() {
        super();
    }

    public XmlBasicInteraction(String shortName) {
        super(shortName);
    }

    public XmlBasicInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    @Override
    @XmlElement(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlElement(name = "xref")
    public InteractionXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElement(type=XmlAnnotation.class, name = "attribute", required = true)
    public JAXBAttributeList getJAXBAttributes() {
        return jaxbAttributeList;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList< psidev.psi.mi.jami.model.Annotation >  }
     *
     */
    public void setJAXBAttributes(JAXBAttributeList  value) {
        this.jaxbAttributeList = value;
        if (value != null){
            this.jaxbAttributeList.setParent(this);
        }
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    @XmlElementWrapper(name="participantList")
    @XmlElement(type=XmlParticipant.class, name = "participant", required = true)
    public JAXBParticipantList getJAXBParticipants() {
        return jaxbParticipantList;
    }

    public void setJAXBParticipants(JAXBParticipantList jaxbParticipantList) {
        this.jaxbParticipantList = jaxbParticipantList;
        if (jaxbParticipantList != null){
            this.jaxbParticipantList.setParent(this);
        }
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElements({@XmlElement(name="interactionType", type = XmlCvTerm.class)})
    public ArrayList<CvTerm> getJAXBInteractionTypes() {
        return super.getJAXBInteractionTypes();
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getJAXBId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getJAXBId()));
        }
    }

    @Override
    protected void initialiseAnnotations(){
        if (jaxbAttributeList != null){
            super.initialiseAnnotationsWith(new ArrayList<Annotation>(jaxbAttributeList));
            this.jaxbAttributeList = null;
        }else{
            super.initialiseAnnotations();
        }
    }

    @Override
    protected void initialiseParticipants(){
        if (jaxbParticipantList != null){
            super.initialiseParticipantsWith(new ArrayList<Participant>(jaxbParticipantList));
            this.jaxbParticipantList = null;
        }else{
            super.initialiseParticipants();
        }
    }

    /**
     * The attribute list used by JAXB to populate interaction annotations
     */
    public static class JAXBAttributeList extends AbstractInteractionAttributeList<XmlBasicInteraction>{

        public JAXBAttributeList(){
            super();
        }
    }

    /**
     * The participant list used by JAXB to populate interaction participants
     */
    public static class JAXBParticipantList<T extends Participant> extends AbstractInteractionParticipantList<Participant, XmlBasicInteraction>{

        public JAXBParticipantList(){
            super();
        }
    }
}
