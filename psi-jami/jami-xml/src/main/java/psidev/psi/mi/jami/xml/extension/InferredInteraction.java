package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
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
        "participants",
        "JAXBExperimentRefList"
})
public class InferredInteraction
        implements FileSourceContext, Locatable
{

    private List<InferredInteractionParticipant> participants;
    private Collection<Experiment> experiments;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBExperimentRefList jaxbExperimentRefList;

    public InferredInteraction() {
        XmlEntryContext.getInstance().getInferredInteractions().add(this);
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
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
    public List<InferredInteractionParticipant> getParticipants() {
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
    public JAXBExperimentRefList getJAXBExperimentRefList() {
        return this.jaxbExperimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(JAXBExperimentRefList value) {
        this.jaxbExperimentRefList = value;
        if (value != null){
            experiments = new ArrayList<Experiment>();
           this.jaxbExperimentRefList.parent = this;
        }
    }

    private FileSourceLocator getInferredInteractionLocator(){
        return this.sourceLocator;
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    public static class JAXBExperimentRefList extends ArrayList<Integer>{

        private InferredInteraction parent;

        public JAXBExperimentRefList(){
        }

        public JAXBExperimentRefList(int initialCapacity) {
        }

        public JAXBExperimentRefList(Collection<? extends Integer> c) {
            addAll(c);
        }

        @Override
        public boolean add(Integer val) {
            if (val == null){
                return false;
            }
            return parent.experiments.add(new ExperimentRef(val));
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Integer a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, Integer element) {
            addToSpecificIndex(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (Integer a : c){
                if (addToSpecificIndex(newIndex, a)){
                    newIndex++;
                    add = true;
                }
            }
            return add;
        }

        private boolean addToSpecificIndex(int index, Integer val) {
            if (val == null){
                return false;
            }
            ((ArrayList<Experiment>)parent.experiments).add(index, new ExperimentRef(val));
            return true;
        }

        ///////////////////////////////////////////
        /**
         * Experiment ref for experimental interactor
         */
        private class ExperimentRef extends AbstractExperimentRef{
            public ExperimentRef(int ref) {
                super(ref);
            }

            public boolean resolve(Map<Integer, Object> parsedObjects) {
                if (parsedObjects.containsKey(this.ref)){
                    Object obj = parsedObjects.get(this.ref);
                    if (obj instanceof Experiment){
                        parent.experiments.remove(this);
                        parent.experiments.add((Experiment)obj);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Experiment reference: "+ref+" in inferred interaction "+(parent.getInferredInteractionLocator() != null? parent.getInferredInteractionLocator().toString():"") ;
            }

            public FileSourceLocator getSourceLocator() {
                return parent.getInferredInteractionLocator();
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
            }
        }
    }
}
