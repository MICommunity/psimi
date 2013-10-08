package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.confidence.UnambiguousConfidenceComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Xml implementation of confidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "confidenceType", propOrder = {
        "JAXBType",
        "JAXBValue",
        "JAXBExperimentRefList"
})
public class XmlConfidence implements Confidence, FileSourceContext{

    private CvTerm type;
    private String value;
    private Map<Integer, Object> mapOfReferencedObjects;
    private ArrayList<Integer> experimentRefList;
    private Collection<Experiment> experiments;

    private PsiXmLocator sourceLocator;

    public XmlConfidence() {
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlConfidence(XmlOpenCvTerm type, String value) {
        if (type == null){
            throw new IllegalArgumentException("The confidence type is required and cannot be null");
        }
        this.type = type;
        if (value == null){
            throw new IllegalArgumentException("The confidence value is required and cannot be null");
        }
        this.value = value;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link XmlOpenCvTerm }
     *
     */
    public CvTerm getType() {
        if (this.type == null){
            this.type = new XmlOpenCvTerm();
        }
        return type;
    }

    public String getValue() {
        if (value == null){
            value = PsiXmlUtils.UNSPECIFIED;
        }
        return value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link XmlOpenCvTerm }
     *
     */
    @XmlElement(name = "unit", required = true)
    public CvTerm getJAXBType() {
        if (this.type == null){
            this.type = new XmlOpenCvTerm();
        }
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlOpenCvTerm }
     *
     */
    public void setJAXBType(XmlOpenCvTerm value) {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlElement(name = "value", required = true)
    public String getJAXBValue() {
        if (value == null){
            value = PsiXmlUtils.UNSPECIFIED;
        }
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBValue(String value) {
        this.value = value;
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
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
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

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Confidence)){
            return false;
        }

        return UnambiguousConfidenceComparator.areEquals(this, (Confidence) o);
    }

    @Override
    public String toString() {
        return getType().toString() + ": " + getValue();
    }

    @Override
    public int hashCode() {
        return UnambiguousConfidenceComparator.hashCode(this);
    }
}
