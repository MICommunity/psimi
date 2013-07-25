package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Xref container in XML implementation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "xrefContainer", propOrder = {
        "primaryRef",
        "secondaryRefs"
})
@XmlSeeAlso({
        CvTermXrefContainer.class, PublicationXrefContainer.class, InteractorXrefContainer.class, FeatureXrefContainer.class
})
public class XrefContainer implements FileSourceContext, Serializable{

    XmlXref primaryRef;
    Collection<XmlXref> secondaryRefs;
    List<Xref> allXrefs;

    private PsiXmLocator sourceLocator;

    /**
     * Gets the value of the primaryRef property.
     *
     * @return
     *     possible object is
     *     {@link XmlXref }
     *
     */
    @XmlElement(required = true)
    public XmlXref getPrimaryRef() {
        return primaryRef;
    }

    /**
     * Sets the value of the primaryRef property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlXref }
     *
     */
    public void setPrimaryRef(XmlXref value) {
        if (this.primaryRef != null){
            processRemovedPrimaryRef(this.primaryRef);
        }

        this.primaryRef = value;
        if (value != null){
            processAddedPrimaryRef();
        }
    }

    /**
     * Gets the value of the secondaryReves property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secondaryReves property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryReves().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlXref }
     *
     *
     */
    @XmlElement(name = "secondaryRef")
    public Collection<XmlXref> getSecondaryRefs() {
        if (secondaryRefs == null) {
            secondaryRefs = new SecondaryXrefList();
        }
        return secondaryRefs;
    }

    @XmlTransient
    public Collection<Xref> getAllXrefs() {
        if (allXrefs == null){
            initialiseXrefs();
        }
        return allXrefs;
    }

    @XmlTransient
    public boolean isEmpty(){
        if (primaryRef == null && getSecondaryRefs().isEmpty()){
            return true;
        }
        return false;
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

    protected void initialiseXrefs(){
        this.allXrefs = new FullXrefList();
    }

    protected void processAddedPrimaryRef() {
        ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
    }

    protected void processRemovedPrimaryRef(XmlXref removed) {
        ((FullXrefList)getAllXrefs()).removeOnly(removed);
    }

    protected void processAddedSecondaryXref(XmlXref added) {
        ((FullXrefList)getAllXrefs()).addOnly(added);
    }

    protected void processRemovedSecondaryXref(XmlXref removed) {
        ((FullXrefList)getAllXrefs()).removeOnly(removed);
    }

    protected void clearSecondaryXrefProperties() {
        if (primaryRef != null){
            ((FullXrefList)getAllXrefs()).retainAllOnly(Collections.singleton(primaryRef));
        }
        else{
            ((FullXrefList)getAllXrefs()).clearOnly();
        }
    }

    protected void processAddedXref(Xref added) {
        // it is a XML instance so it can be converted with jaxb
        if (added instanceof XmlXref){
            if (primaryRef == null){
                primaryRef = (XmlXref)added;
            }
            else{
                ((SecondaryXrefList)getSecondaryRefs()).addOnly((XmlXref)added);
            }
        }
    }

    protected void processRemovedXref(Xref removed) {
        if (removed instanceof XmlXref){
            if (primaryRef != null && removed.equals(primaryRef)){
                if (!getSecondaryRefs().isEmpty()){
                    primaryRef = secondaryRefs.iterator().next();
                    ((SecondaryXrefList)secondaryRefs).removeOnly(primaryRef);
                }
                else{
                    primaryRef = null;
                }
            }
        }
    }

    protected void clearFullXrefProperties() {
        primaryRef = null;
        ((SecondaryXrefList)getSecondaryRefs()).clearOnly();
    }

    class FullXrefList extends AbstractListHavingProperties<Xref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                processAddedXref(added);

            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null){
                processRemovedXref(removed);
            }
        }

        @Override
        protected void clearProperties() {
            clearFullXrefProperties();
        }
    }

    class SecondaryXrefList extends AbstractListHavingProperties<XmlXref> {
        public SecondaryXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(XmlXref added) {
            if (added != null){
                processAddedSecondaryXref(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(XmlXref removed) {
            if (removed != null){
                processRemovedSecondaryXref(removed);
            }
        }

        @Override
        protected void clearProperties() {
            clearSecondaryXrefProperties();
        }
    }
}
