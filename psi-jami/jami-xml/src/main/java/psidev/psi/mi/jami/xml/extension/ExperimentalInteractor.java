package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.XmlEntryContext;

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
public class ExperimentalInteractor
{

    private XmlInteractor xmlInteractor;
    private Integer interactorRef;
    private ArrayList<Integer> experimentRefList;

    private Interactor interactor;
    private Collection<Experiment> experiments;

    private Map<Integer, Object> mapOfReferencedObjects;
    private XmlInteractorFactory interactorFactory;

    public ExperimentalInteractor() {
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
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
        return xmlInteractor;
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
        this.interactor = this.interactorFactory.createInteractorFromInteractorType(value.getJAXBInteractorType(), value.getShortName());
        Xref primary = value.getPreferredIdentifier();
        if (this.interactor == null && primary != null){
            this.interactor = this.interactorFactory.createInteractorFromDatabase(primary.getDatabase(), value.getShortName());
        }
        else{
            this.interactor = value;
        }
        this.xmlInteractor = (XmlInteractor) this.interactor;
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
        return interactorRef;
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
        this.interactorRef = value;
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
    public ArrayList<Integer> getJAXBExperimentRefList() {
        return experimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Integer> }
     *
     */
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        this.experimentRefList = value;
    }

    public Interactor getInteractor() {
        if (this.interactor == null){
            if (this.interactorRef != null && this.mapOfReferencedObjects.containsKey(this.interactorRef)){
                Object object = this.mapOfReferencedObjects.get(this.interactorRef);
                if (object instanceof Interactor){
                    this.interactor = (Interactor) object;
                }
            }
        }
        return this.interactor;
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        if (experiments.isEmpty() && this.experimentRefList != null && !this.experimentRefList.isEmpty()){
            resolveExperimentReferences();
        }
        return experiments;
    }

    private void resolveExperimentReferences() {
        for (Integer id : this.experimentRefList){
            if (this.mapOfReferencedObjects.containsKey(id)){
                Object o = this.mapOfReferencedObjects.get(id);
                if (o instanceof Experiment){
                    this.experiments.add((Experiment)o);
                }
            }
        }
    }
}
