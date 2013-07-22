//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.03 at 12:49:45 PM BST 
//


package psidev.psi.mi.jami.xml;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Desciption of the source of the entry, usually an organisation
 *             
 * 
 * <p>Java class for source complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="source">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{http://psi.hupo.org/mi/mif}names" minOccurs="0"/>
 *         &lt;element name="bibref" type="{http://psi.hupo.org/mi/mif}bibref" minOccurs="0"/>
 *         &lt;element name="xref" type="{http://psi.hupo.org/mi/mif}xref" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{http://psi.hupo.org/mi/mif}attributeList" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="release">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "source", propOrder = {
    "names",
    "publication",
    "xref",
    "attributes"
})
public class XmlSource extends XmlOpenCvTerm
    implements Source, FileSourceContext, Serializable
{

    private Annotation url;
    private String postalAddress;
    private Publication bibRef;
    private String release;
    private XMLGregorianCalendar releaseDate;

    private PsiXmLocator sourceLocator;

    public XmlSource(){
        super();
    }

    public XmlSource(String shortName) {
        super(shortName);
    }

    public XmlSource(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public XmlSource(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public XmlSource(String shortName, String url, String address, Publication bibRef) {
        super(shortName);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public XmlSource(String shortName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, ontologyId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public XmlSource(String shortName, String fullName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, fullName, ontologyId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public XmlSource(String shortName, String miId) {
        super(shortName, miId);
    }

    public XmlSource(String shortName, String fullName, String miId) {
        super(shortName, fullName, miId);
    }

    public XmlSource(String shortName, String miId, String url, String address, Publication bibRef) {
        super(shortName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public XmlSource(String shortName, String fullName, String miId, String url, String address, Publication bibRef) {
        super(shortName, fullName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new SourceAnnotationList());
    }

    @XmlTransient
    public String getUrl() {
        return this.url != null ? this.url.getValue() : null;
    }

    public void setUrl(String url) {
        Collection<Annotation> sourceAnnotationList = getAnnotations();

        // add new url if not null
        if (url != null){
            CvTerm complexPhysicalProperties = CvTermUtils.createMICvTerm(Annotation.URL, Annotation.URL_MI);
            // first remove old url if not null
            if (this.url != null){
                sourceAnnotationList.remove(this.url);
            }
            this.url = new DefaultAnnotation(complexPhysicalProperties, url);
            sourceAnnotationList.add(this.url);
        }
        // remove all url if the collection is not empty
        else if (!sourceAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(sourceAnnotationList, Annotation.URL_MI, Annotation.URL);
            this.url = null;
        }
    }

    @XmlTransient
    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String address) {
        this.postalAddress = address;
    }

    @XmlElement(name = "bibRef", type = BibRef.class)
    public Publication getPublication() {
        return this.bibRef;
    }

    public void setPublication(Publication ref) {
        this.bibRef = ref;
    }

    /**
     * Gets the value of the release property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @XmlAttribute(name = "release")
    public String getRelease() {
        return release;
    }

    /**
     * Sets the value of the release property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelease(String value) {
        this.release = value;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    @XmlAttribute(name = "releaseDate")
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setReleaseDate(XMLGregorianCalendar value) {
        this.releaseDate = value;
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute")
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    @Override
    public Collection<Annotation> getAttributes() {
        if (getAnnotations().isEmpty() && this.postalAddress == null){
            return  null;
        }
        else {
            Collection<Annotation> annots = new ArrayList<Annotation>(getAnnotations().size());
            annots.addAll(getAnnotations());

            if (this.postalAddress != null){
                annots.add(new XmlAnnotation(new DefaultCvTerm("postaladdress"), this.postalAddress));
            }

            return annots;
        }
    }

    @Override
    public void setAttributes(Collection<Annotation> annotations){

        // we have a bibref. Some annotations can be processed
        for (Annotation annot : annotations){
            if (AnnotationUtils.doesAnnotationHaveTopic(annot, null, "postaladdress")){
                this.postalAddress = annot.getValue();
            }
            else {
                getAnnotations().add(annot);
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

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (url == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.URL_MI, Annotation.URL)){
            url = added;
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (url != null && url.equals(removed)){
            url = null;
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        url = null;
    }

    private class SourceAnnotationList extends AbstractListHavingProperties<Annotation> {
        public SourceAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAnnotations();
        }
    }
}
