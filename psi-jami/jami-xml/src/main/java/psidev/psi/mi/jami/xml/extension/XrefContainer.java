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
public class XrefContainer implements FileSourceContext, Serializable{

    private XmlXref primaryRef;
    private Collection<XmlXref> secondaryRefs;
    private List<Xref> allXrefs;

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
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }

        this.primaryRef = value;
        if (value != null){
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
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
            allXrefs = new FullXrefList();
        }
        return allXrefs;
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

    private class FullXrefList extends AbstractListHavingProperties<Xref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                // it is a XML instance so it can be converted with jaxb
                if (added instanceof XmlXref){
                    processAddedXref((XmlXref) added);
                }
            }
        }

        private void processAddedXref(XmlXref added) {
            if (primaryRef == null){
                primaryRef = added;
            }
            else{
                ((SecondaryXrefList)getSecondaryRefs()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null && removed instanceof XmlXref){
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

        @Override
        protected void clearProperties() {
            primaryRef = null;
            ((SecondaryXrefList)getSecondaryRefs()).clearOnly();
        }
    }

    private class SecondaryXrefList extends AbstractListHavingProperties<XmlXref> {
        public SecondaryXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(XmlXref added) {
            if (added != null){
                ((FullXrefList)getAllXrefs()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(XmlXref removed) {
            if (removed != null){
                ((FullXrefList)getAllXrefs()).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            if (primaryRef != null){
                ((FullXrefList)getAllXrefs()).retainAllOnly(Collections.singleton(primaryRef));
            }
            else{
                ((FullXrefList)getAllXrefs()).clearOnly();
            }
        }
    }
}
