package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.annotation.UnambiguousAnnotationComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;

/**
 * Xml implementation of an Annotation
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlRootElement(name = "attribute", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "attribute", propOrder = {
        "JAXBValue"
})
public class XmlAnnotation implements Annotation, FileSourceContext {

    private CvTerm topic;
    private String value;
    private PsiXmLocator sourceLocator;

    public XmlAnnotation() {
    }

    public XmlAnnotation(CvTerm topic, String value) {
        if (topic == null){
            throw new IllegalArgumentException("The annotation topic cannot be null.");
        }
        this.topic = topic;
        this.value = value;
    }

    public XmlAnnotation(CvTerm topic) {
        if (topic == null){
            throw new IllegalArgumentException("The annotation topic cannot be null.");
        }
        this.topic = topic;
    }

    public CvTerm getTopic() {
        if (topic == null){
            this.topic = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.topic;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlValue
    public String getJAXBValue() {
        return this.value;
    }

    public void setJAXBValue(String value) {
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
    public String getJAXBName() {
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
    public void setJAXBName(String value) {
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
    public String getJAXBNameAc() {
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
    public void setJAXBNameAc(String value) {
        if (this.topic == null && value != null){
            this.topic = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.topic != null){
            this.topic.setMIIdentifier(value);
        }
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }    }

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
