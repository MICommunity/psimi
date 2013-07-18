package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.annotation.UnambiguousAnnotationComparator;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Xml implementation of an Annotation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "attribute", propOrder = {
        "value"
})
public class XmlAnnotation implements Annotation, Serializable {

    private CvTerm topic;
    private String value;

    public XmlAnnotation() {
    }

    public XmlAnnotation(CvTerm topic, String value) {
        if (topic == null){
            throw new IllegalArgumentException("The annotation topic cannot be null.");
        }
        this.topic = topic;
        this.value = value;
    }

    @XmlTransient
    public CvTerm getTopic() {
        if (topic == null){
            this.topic = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.topic;
    }

    @XmlValue
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "name", required = true)
    public String getName() {
        return getTopic().getShortName();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        if (this.topic == null && value != null){
            this.topic = new DefaultCvTerm(value);
        }
        else if (this.topic != null){
            this.topic.setShortName(value != null ? value : PsiXmlUtils.UNSPECIFIED);
        }
    }

    /**
     * Gets the value of the nameAc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "nameAc")
    public String getNameAc() {
        return getTopic().getMIIdentifier();
    }

    /**
     * Sets the value of the nameAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameAc(String value) {
        if (this.topic == null && value != null){
            this.topic = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.topic != null){
            this.topic.setMIIdentifier(value);
        }
    }

    @Override
    public int hashCode() {
        return UnambiguousAnnotationComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Annotation)){
            return false;
        }

        return UnambiguousAnnotationComparator.areEquals(this, (Annotation) o);
    }

    @Override
    public String toString() {
        return topic.toString()+(value != null ? ": " + value : "");
    }
}
