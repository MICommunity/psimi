package psidev.psi.mi.jami.xml;

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
@XmlType(name = "xref", propOrder = {
        "primaryRef",
        "secondaryRefs"
})
public class XrefContainer implements Serializable{

    private XmlXref primaryRef;
    private Collection<XmlXref> secondaryRefs;
    private List<XmlXref> allXrefs;

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
    public Collection<XmlXref> getAllXrefs() {
        if (allXrefs == null){
            allXrefs = new FullXrefList();
        }
        return allXrefs;
    }

    private class FullXrefList extends AbstractListHavingProperties<XmlXref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(XmlXref added) {
            if (added != null){
                if (primaryRef == null){
                    primaryRef = added;
                }
                else{
                    ((SecondaryXrefList)getSecondaryRefs()).addOnly(added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(XmlXref removed) {
            if (removed != null){
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
