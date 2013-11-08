package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.annotation.UnambiguousAnnotationComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
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
public class XmlAnnotation implements Annotation, FileSourceContext, Locatable {

    private CvTerm topic;
    private String value;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    protected Locator locator;

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

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "name", required = true)
    public void setJAXBName(String value) {
        if (this.topic == null && value != null){
            this.topic = new DefaultCvTerm(value);
        }
        else if (this.topic != null){
            this.topic.setShortName(value != null ? value : PsiXmlUtils.UNSPECIFIED);
        }
        if (value == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onAnnotationWithoutTopic(this);
            }
        }
    }

    /**
     * Sets the value of the nameAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "nameAc")
    public void setJAXBNameAc(String value) {
        if (this.topic == null && value != null){
            this.topic = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.topic != null){
            this.topic.setMIIdentifier(value);
        }
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
        return "Xml Annotation: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}
