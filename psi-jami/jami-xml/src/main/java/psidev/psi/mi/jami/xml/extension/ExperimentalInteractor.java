package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.AbstractInteractorReference;

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
public class ExperimentalInteractor implements FileSourceContext
{
    private Interactor interactor;
    private Collection<Experiment> experiments;

    private XmlInteractorFactory interactorFactory;
    private PsiXmLocator sourceLocator;

    public ExperimentalInteractor() {
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
    @XmlElement(name = "interactor", type = XmlInteractor.class)
    public Interactor getJAXBInteractor() {
        return interactor;
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
            this.interactor = this.interactorFactory.createInteractorFromInteractorType(value.getJAXBInteractorType(), value.getShortName());
            Xref primary = value.getPreferredIdentifier();
            if (this.interactor == null && primary != null){
                this.interactor = this.interactorFactory.createInteractorFromDatabase(primary.getDatabase(), value.getShortName());
            }
            else{
                this.interactor = value;
            }
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
        if (interactor instanceof XmlInteractor){
           return ((XmlInteractor)interactor).getJAXBId();
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
            this.interactor = new AbstractInteractorReference(value) {
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
            };
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
     *     {@link ArrayList<Integer> }
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
                });
            }
        }
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
}
