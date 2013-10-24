package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.AbstractInteractorReference;
import psidev.psi.mi.jami.xml.extension.factory.XmlInteractorFactory;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * <p>Java class for experimentalInteractor complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="experimentalInteractor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="interactorRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="interactor" type="{http://psi.hupo.org/mi/mif}interactor"/>
 *         &lt;/choice>
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
@XmlType(name = "experimentalInteractor", propOrder = {
        "JAXBInteractor",
        "JAXBInteractorRef",
        "JAXBExperimentRefList"
})
public class ExperimentalInteractor implements FileSourceContext, Locatable
{
    private Interactor interactor;
    private Collection<Experiment> experiments;
    private XmlInteractorFactory interactorFactory;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    private JAXBExperimentRefList jaxbExperimentRefList;

    public ExperimentalInteractor() {
        this.interactorFactory = new XmlInteractorFactory();
    }

    public Interactor getInteractor() {
        return this.interactor;
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
     * Gets the value of the interactor property.
     *
     * @return
     *     possible object is
     *     {@link Interactor }
     *
     */
    @XmlElement(name = "interactor")
    public XmlInteractor getJAXBInteractor() {
        if (interactor instanceof XmlInteractor){
           return (XmlInteractor)interactor;
        }
        return null;
    }

    /**
     * Sets the value of the interactor property.
     *
     * @param value
     *     allowed object is
     *     {@link Interactor }
     *
     */
    public void setJAXBInteractor(XmlInteractor value) {
        if (value == null){
            this.interactor = null;
        }
        else{
            this.interactor = this.interactorFactory.createInteractorFromXmlInteractorInstance(value);
        }
    }

    /**
     * Gets the value of the interactorRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "interactorRef")
    public Integer getJAXBInteractorRef() {
        if (interactor instanceof ExperimentalInteractor.InteractorRef){
           return ((ExperimentalInteractor.InteractorRef)interactor).getRef();
        }
        return null;
    }

    /**
     * Sets the value of the interactorRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBInteractorRef(Integer value) {
        if (value != null){
            this.interactor = new InteractorRef(value);
        }
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Integer> }
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
     *     {@link ArrayList<Integer> }
     *
     */
    public void setJAXBExperimentRefList(JAXBExperimentRefList value) {
        this.jaxbExperimentRefList = value;
    }

    private FileSourceLocator getExperimentalInteractorLocator(){
        return getSourceLocator();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    public class JAXBExperimentRefList extends ArrayList<Integer>{

        public JAXBExperimentRefList(){
            experiments = new ArrayList<Experiment>();
        }

        public JAXBExperimentRefList(int initialCapacity) {
            experiments = new ArrayList<Experiment>(initialCapacity);
        }

        public JAXBExperimentRefList(Collection<? extends Integer> c) {
            experiments = new ArrayList<Experiment>(c.size());
            addAll(c);
        }

        @Override
        public boolean add(Integer val) {
            if (val == null){
                return false;
            }
            return experiments.add(new ExperimentRef(val));
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
            ((ArrayList<Experiment>)experiments).add(index, new ExperimentRef(val));
            return true;
        }
    }

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
                    experiments.remove(this);
                    experiments.add((Experiment)obj);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Experiment reference: "+ref+" in experimental interactor "+(getExperimentalInteractorLocator() != null? getExperimentalInteractorLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return getExperimentalInteractorLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
        }
    }

    private class InteractorRef extends AbstractInteractorReference{
        public InteractorRef(int ref) {
            super(ref);
        }

        @Override
        public boolean resolve(Map<Integer, Object> parsedObjects) {
            if (parsedObjects.containsKey(this.ref)){
                Object obj = parsedObjects.get(this.ref);
                if (obj instanceof Interactor){
                    interactor = (Interactor) obj;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Interactor reference: "+ref+" in experimental interactor "+(getExperimentalInteractorLocator() != null? getExperimentalInteractorLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return getExperimentalInteractorLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an interactor ref");
        }
    }
}
