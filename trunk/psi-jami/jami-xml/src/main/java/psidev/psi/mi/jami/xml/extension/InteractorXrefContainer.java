package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Xref container for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "xrefContainer", propOrder = {
        "primaryRef",
        "secondaryRefs"
})
public class InteractorXrefContainer implements FileSourceContext,Serializable {

    private XmlXref primaryRef;
    private Collection<XmlXref> secondaryRefs;
    private List<Xref> allXrefs;
    private List<Xref> allIdentifiers;

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
            if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
                // if it is not an identifier
                ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
            }
        }

        this.primaryRef = value;
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
        }
        else {
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
            initialiseXrefs();
        }
        return allXrefs;
    }

    @XmlTransient
    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            initialiseIdentifiers();
        }
        return allIdentifiers;
    }

    @XmlTransient
    public Xref getPreferredIdentifier() {
        return !getAllIdentifiers().isEmpty() ? allIdentifiers.iterator().next() : null;
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
        this.allXrefs = new ArrayList<Xref>();
    }

    protected void initialiseIdentifiers(){
        this.allIdentifiers = new ArrayList<Xref>();
    }

    protected void initialiseXrefsWith(List<Xref> xrefs){
        if (xrefs == null){
            this.allXrefs = Collections.EMPTY_LIST;
        }
        else {
            this.allXrefs = xrefs;
        }
    }

    protected void initialiseIdentifiersWith(List<Xref> identifiers){
        if (identifiers == null){
            this.allIdentifiers = Collections.EMPTY_LIST;
        }
        else {
            this.allIdentifiers = identifiers;
        }
    }

    private void processAddedPrimaryAndSecondaryRefs(XmlXref added) {
        if (primaryRef == null){
            primaryRef = added;
        }
        else{
            ((SecondaryXrefList)getSecondaryRefs()).addOnly(added);
        }
    }

    private void processRemovedPrimaryAndSecondaryRefs(XmlXref removed) {
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

    private class FullIdentifierList extends AbstractListHavingProperties<Xref> {
        public FullIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                if (added instanceof XmlXref){
                    processAddedPrimaryAndSecondaryRefs((XmlXref) added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null){
                if (removed instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
                }
            }
        }

        @Override
        protected void clearProperties() {
            // the primary ref is in xrefs
            if (getAllXrefs().contains(primaryRef)){
                // do nothing
            }
            // the primary ref is in identifiers, we reset it to the first xrefContainer
            else if (!getAllXrefs().isEmpty()){
                Iterator<Xref> refsIterator = allXrefs.iterator();
                Xref ref = refsIterator.next();

                while(refsIterator.hasNext() && !(ref instanceof XmlXref)){
                    ref = refsIterator.next();
                }
                if (ref instanceof XmlXref){
                    primaryRef = (XmlXref) ref;
                    ((FullXrefList)allXrefs).removeOnly(ref);
                }
            }
            else{
                primaryRef = null;
            }
            ((SecondaryXrefList)getSecondaryRefs()).retainAllOnly(getAllXrefs());
        }
    }

    private class FullXrefList extends AbstractListHavingProperties<Xref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                if (added instanceof XmlXref){
                    processAddedPrimaryAndSecondaryRefs((XmlXref)added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null && removed instanceof XmlXref){
                processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
            }
        }

        @Override
        protected void clearProperties() {
            // the primary ref is in identifiers
            if (getAllIdentifiers().contains(primaryRef)){
                // do nothing
            }
            // the primary ref is in xrefs, we reset it to the first identifier
            else if (!getAllIdentifiers().isEmpty()){
                Iterator<Xref> refsIterator = allIdentifiers.iterator();
                Xref ref = refsIterator.next();

                while(refsIterator.hasNext() && !(ref instanceof XmlXref)){
                    ref = refsIterator.next();
                }
                if (ref instanceof XmlXref){
                    primaryRef = (XmlXref) ref;
                    ((FullIdentifierList)allIdentifiers).removeOnly(ref);
                }
            }
            else{
                primaryRef = null;
            }

            ((SecondaryXrefList)getSecondaryRefs()).retainAllOnly(getAllIdentifiers());
        }
    }

    private class SecondaryXrefList extends AbstractListHavingProperties<XmlXref> {
        public SecondaryXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(XmlXref added) {
            if (added != null){
                // it is an identifier
                if (XrefUtils.isXrefAnIdentifier(added)){
                    ((FullIdentifierList)getAllIdentifiers()).addOnly(added);
                }
                // it is not an identifier
                else {
                    ((FullXrefList)getAllXrefs()).addOnly(added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(XmlXref removed) {
            if (removed != null){
                // if it is an identifier
                if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(removed)){
                    // if it is not an identifier
                    ((FullXrefList)getAllXrefs()).removeOnly(removed);
                }
            }
        }

        @Override
        protected void clearProperties() {
            if (primaryRef != null){
                Collection<XmlXref> primary = Collections.singleton(primaryRef);
                List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
                identifiersToBeDeleted.remove(primaryRef);
                for (Xref ref : identifiersToBeDeleted){
                    ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                }
                if (!((FullIdentifierList)getAllIdentifiers()).retainAllOnly(primary)){
                    // if it is not an identifier
                    ((FullXrefList)getAllXrefs()).retainAllOnly(primary);
                }
                else {
                    ((FullXrefList)getAllXrefs()).clearOnly();
                }
            }
            else{
                ((FullXrefList)getAllXrefs()).clearOnly();
                List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
                for (Xref ref : identifiersToBeDeleted){
                    ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                }
            }

        }
    }
}
