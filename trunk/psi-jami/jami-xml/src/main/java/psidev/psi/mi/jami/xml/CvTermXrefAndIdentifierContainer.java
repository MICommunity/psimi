package psidev.psi.mi.jami.xml;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Container for both xrefs and identifiers  in a CvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "xref", propOrder = {
        "primaryRef",
        "secondaryRefs"
})
public class CvTermXrefAndIdentifierContainer implements FileSourceContext,Serializable{

    private Xref miIdentifier;
    private Xref modIdentifier;
    private Xref parIdentifier;
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
            else {
                processRemovedIdentifierEvent(this.primaryRef);
            }
        }

        this.primaryRef = value;
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processAddedIdentifierEvent(this.primaryRef);
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
            allXrefs = new FullXrefList();
        }
        return allXrefs;
    }

    @XmlTransient
    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            allIdentifiers = new FullIdentifierList();
        }
        return allIdentifiers;
    }

    @XmlTransient
    public String getMIIdentifier() {
        return this.miIdentifier != null ? this.miIdentifier.getId() : null;
    }

    @XmlTransient
    public String getMODIdentifier() {
        return this.modIdentifier != null ? this.modIdentifier.getId() : null;
    }

    @XmlTransient
    public String getPARIdentifier() {
        return this.parIdentifier != null ? this.parIdentifier.getId() : null;
    }

    public void setMIIdentifier(String mi) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mi if not null
        if (mi != null){
            CvTerm psiMiDatabase = CvTermUtils.createPsiMiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                cvTermIdentifiers.removeOnly(this.miIdentifier);
                if (this.miIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.miIdentifier);
                }
            }
            this.miIdentifier = new XmlXref(psiMiDatabase, mi, identityQualifier);
            cvTermIdentifiers.addOnly(this.miIdentifier);
            processAddedIdentifierEvent(this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    public void setMODIdentifier(String mod) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mod if not null
        if (mod != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiModDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                cvTermIdentifiers.removeOnly(this.modIdentifier);
                if (this.modIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.modIdentifier);
                }
            }
            this.modIdentifier = new XmlXref(psiModDatabase, mod, identityQualifier);
            cvTermIdentifiers.addOnly(this.parIdentifier);
            processAddedIdentifierEvent(this.parIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    public void setPARIdentifier(String par) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mod if not null
        if (par != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiParDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.parIdentifier != null){
                cvTermIdentifiers.removeOnly(this.parIdentifier);
                if (this.parIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.parIdentifier);
                }
            }
            this.parIdentifier = new XmlXref(psiModDatabase, par, identityQualifier);
            cvTermIdentifiers.addOnly(this.parIdentifier);
            processAddedIdentifierEvent(this.parIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), null, CvTerm.PSI_PAR);
            this.parIdentifier = null;
        }
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

    protected void processAddedIdentifierEvent(Xref added) {

        // the added identifier is psi-mi and it is not the current mi identifier
        if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
            // the current psi-mi identifier is not identity, we may want to set miIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the miidentifier is not set, we can set the miidentifier
                if (miIdentifier == null){
                    miIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    miIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    miIdentifier = added;
                }
            }
        }
        // the added identifier is psi-mod and it is not the current mod identifier
        else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
            // the current psi-mod identifier is not identity, we may want to set modIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the modIdentifier is not set, we can set the modIdentifier
                if (modIdentifier == null){
                    modIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    modIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    modIdentifier = added;
                }
            }
        }
        // the added identifier is psi-par and it is not the current par identifier
        else if (parIdentifier != added && XrefUtils.isXrefFromDatabase(added, null, CvTerm.PSI_PAR)){
            // the current psi-par identifier is not identity, we may want to set parIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the parIdentifier is not set, we can set the parIdentifier
                if (parIdentifier == null){
                    parIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    parIdentifier = added;
                }
                // the added xref is secondary object and the current par is not a secondary object, we reset paridentifier
                else if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    parIdentifier = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is psi-mi
        if (miIdentifier != null && miIdentifier.equals(removed)){
            miIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
        }
        // the removed identifier is psi-mod
        else if (modIdentifier != null && modIdentifier.equals(removed)){
            modIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
        }
        // the removed identifier is psi-par
        else if (parIdentifier != null && parIdentifier.equals(removed)){
            parIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), null, CvTerm.PSI_PAR);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        miIdentifier = null;
        modIdentifier = null;
        parIdentifier = null;
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
                processAddedIdentifierEvent(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null){
                if (removed instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
                }
                processRemovedIdentifierEvent(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // the primary ref is in xrefs
            if (getAllXrefs().contains(primaryRef)){
                // do nothing
            }
            // the primary ref is in identifiers, we reset it to the first xref
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
            clearPropertiesLinkedToIdentifiers();
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
                    processAddedIdentifierEvent(added);
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
                else {
                    processRemovedIdentifierEvent(removed);
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
                    processRemovedIdentifierEvent(ref);
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
                    processRemovedIdentifierEvent(ref);
                }
            }

        }
    }
}
