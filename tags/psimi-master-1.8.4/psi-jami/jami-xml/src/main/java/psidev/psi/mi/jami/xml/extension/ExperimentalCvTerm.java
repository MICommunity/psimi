//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.20 at 10:58:57 AM BST 
//


package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * This element is controlled by the PSI-MI controlled vocabulary
 *                 "experimentalPreparation", root term id MI:0346.
 *             
 * 
 * <p>Java class for experimentalPreparation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="experimentalPreparation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://psi.hupo.org/mi/mif}cvType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="experimentRefList" type="{http://psi.hupo.org/mi/mif}experimentRefList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "experimentalPreparation", propOrder = {
    "experimentRefList"
})
public class ExperimentalCvTerm
    extends XmlCvTerm
{

    private Map<Integer, Object> mapOfReferencedObjects;
    private Collection<Integer> experimentRefList;
    private Collection<Experiment> experiments;

    public ExperimentalCvTerm() {
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public ExperimentalCvTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public ExperimentalCvTerm(String shortName) {
        super(shortName);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public ExperimentalCvTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public ExperimentalCvTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public ExperimentalCvTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
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
    @XmlElement(name="experimentRef")
    public Collection<Integer> getExperimentRefList() {
        return experimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExperimentRefList(Collection<Integer> value) {
        this.experimentRefList = value;
    }

    @XmlTransient
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