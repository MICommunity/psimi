package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Java class for inferredInteraction complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="inferredInteraction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="participant" type="{http://psi.hupo.org/mi/mif}inferredInteractionParticipant" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;element name="experimentRefList" type="{http://psi.hupo.org/mi/mif}experimentRefList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "inferredInteraction", propOrder = {
        "JAXBParticipants",
        "JAXBExperimentRefList"
})
public class InferredInteraction
        implements FileSourceContext
{

    private List<InferredInteractionParticipant> participants;
    private Collection<Experiment> experiments;

    private PsiXmLocator sourceLocator;

    public InferredInteraction() {
        XmlEntryContext.getInstance().getInferredInteractions().add(this);
    }

    /**
     * Gets the value of the participants property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participants property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipants().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InferredInteractionParticipant }
     *
     *
     */
    @XmlElement(name="participant", required = true)
    public List<InferredInteractionParticipant> getJAXBParticipants() {
        if (participants == null) {
            participants = new ArrayList<InferredInteractionParticipant>();
        }
        return this.participants;
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef", required = true)
    public ArrayList<Integer> getJAXBExperimentRefList() {
        if (experiments == null || experiments.isEmpty()){
            return null;
        }
        ArrayList<Integer> references = new ArrayList<Integer>(experiments.size());
        for (Experiment exp : experiments){
            if (exp instanceof XmlExperiment){
                references.add(((XmlExperiment) exp).getId());
            }
        }
        if (references.isEmpty()){
            return null;
        }
        return references;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        if (value != null){
            for (Integer val : value){
                getExperiments().add(new AbstractExperimentRef(val) {
                    public boolean resolve(Map<Integer, Object> parsedObjects) {
                        if (parsedObjects.containsKey(this.ref)){
                            Object obj = parsedObjects.get(this.ref);
                            if (obj instanceof Experiment){
                                experiments.remove(this);
                                experiments.add((Experiment)obj);
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return "Experiment reference: "+ref+" in inferred interaction "+(getInferredInteractionLocator() != null? getInferredInteractionLocator().toString():"") ;
                    }

                    public FileSourceLocator getSourceLocator() {
                        return getInferredInteractionLocator();
                    }

                    public void setSourceLocator(FileSourceLocator locator) {
                        throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
                    }
                });
            }
        }
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }

    private FileSourceLocator getInferredInteractionLocator(){
        return this.sourceLocator;
    }
}
