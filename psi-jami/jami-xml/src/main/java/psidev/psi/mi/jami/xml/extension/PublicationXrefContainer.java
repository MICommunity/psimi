package psidev.psi.mi.jami.xml.extension;

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
 * Xref container for a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "xrefContainer", propOrder = {
        "primaryRef",
        "secondaryRefs"
})
public class PublicationXrefContainer implements FileSourceContext,Serializable {

    private Xref pubmedId;
    private Xref doi;
    private Xref imexId;
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
                if (((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef)){
                    processRemovedXrefEvent(this.primaryRef);
                }
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
            processAddedXrefEvent(this.primaryRef);
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
    public String getPubmedId() {
        return this.pubmedId != null ? this.pubmedId.getId() : null;
    }

    public void setPubmedId(String pubmedId) {
        FullIdentifierList identifiers = (FullIdentifierList) getAllIdentifiers();

        // add new pubmed if not null
        if (pubmedId != null){
            CvTerm pubmedDatabase = CvTermUtils.createPubmedDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old pubmed if not null
            if (this.pubmedId != null){
                identifiers.removeOnly(this.pubmedId);
                if (this.pubmedId instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.pubmedId);
                }
            }
            this.pubmedId = new XmlXref(pubmedDatabase, pubmedId, identityQualifier);
            identifiers.addOnly(this.pubmedId);
        }
        // remove all pubmed if the collection is not empty
        else if (!identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.PUBMED_MI, Xref.PUBMED);
            this.pubmedId = null;
        }
    }

    @XmlTransient
    public String getDoi() {
        return this.doi != null ? this.doi.getId() : null;
    }

    public void setDoi(String doi) {
        FullIdentifierList identifiers = (FullIdentifierList) getAllIdentifiers();
        // add new doi if not null
        if (doi != null){
            CvTerm doiDatabase = CvTermUtils.createDoiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old doi if not null
            if (this.doi != null){
                identifiers.removeOnly(this.doi);
                if (this.doi instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.doi);
                }
            }
            this.doi = new XmlXref(doiDatabase, doi, identityQualifier);
            identifiers.addOnly(this.doi);
        }
        // remove all doi if the collection is not empty
        else if (!identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.DOI_MI, Xref.DOI);
            this.doi = null;
        }
    }

    @XmlTransient
    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        FullXrefList xrefs = (FullXrefList) getAllXrefs();
        // add new imex if not null
        if (identifier != null){
            CvTerm imexDatabase = CvTermUtils.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermUtils.createImexPrimaryQualifier();
            // first remove old imex if not null
            if (this.imexId != null){
                xrefs.removeOnly(this.imexId);
                if (this.imexId instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.imexId);
                }
            }
            this.imexId = new XmlXref(imexDatabase, identifier, imexPrimaryQualifier);
            xrefs.addOnly(this.imexId);
        }
        else if (this.imexId != null){
            throw new IllegalArgumentException("The imex id has to be non null.");
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

        // the added identifier is pubmed and it is not the current pubmed identifier
        if (pubmedId != added && XrefUtils.isXrefFromDatabase(added, Xref.PUBMED_MI, Xref.PUBMED)){
            // the current pubmed identifier is not identity, we may want to set pubmed Identifier
            if (!XrefUtils.doesXrefHaveQualifier(pubmedId, Xref.IDENTITY_MI, Xref.IDENTITY) && !XrefUtils.doesXrefHaveQualifier(pubmedId, Xref.PRIMARY_MI, Xref.PRIMARY)){
                // the pubmed identifier is not set, we can set the pubmed
                if (pubmedId == null){
                    pubmedId = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY) || XrefUtils.doesXrefHaveQualifier(added, Xref.PRIMARY_MI, Xref.PRIMARY)){
                    pubmedId = added;
                }
                // the added xrefContainer is secondary object and the current pubmed is not a secondary object, we reset pubmed identifier
                else if (!XrefUtils.doesXrefHaveQualifier(pubmedId, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    pubmedId = added;
                }
            }
        }
        // the added identifier is doi and it is not the current doi identifier
        else if (doi != added && XrefUtils.isXrefFromDatabase(added, Xref.DOI_MI, Xref.DOI)){
            // the current doi identifier is not identity, we may want to set doi
            if (!XrefUtils.doesXrefHaveQualifier(doi, Xref.IDENTITY_MI, Xref.IDENTITY) && !XrefUtils.doesXrefHaveQualifier(doi, Xref.PRIMARY_MI, Xref.PRIMARY)){
                // the doi is not set, we can set the doi
                if (doi == null){
                    doi = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY) || XrefUtils.doesXrefHaveQualifier(added, Xref.PRIMARY_MI, Xref.PRIMARY)){
                    doi = added;
                }
                // the added xrefContainer is secondary object and the current doi is not a secondary object, we reset doi
                else if (!XrefUtils.doesXrefHaveQualifier(doi, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    doi = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is pubmed
        if (pubmedId != null && pubmedId.equals(removed)){
            pubmedId = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.PUBMED_MI, Xref.PUBMED);
        }
        // the removed identifier is doi
        else if (doi != null && doi.equals(removed)){
            doi = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.DOI_MI, Xref.DOI);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        pubmedId = null;
        doi = null;
    }

    protected void processAddedXrefEvent(Xref added) {

        // the added identifier is imex and the current imex is not set
        if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
            // the added xrefContainer is imex-primary
            if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                imexId = added;
            }
        }
    }

    protected void processRemovedXrefEvent(Xref removed) {
        // the removed identifier is pubmed
        if (imexId != null && imexId.equals(removed)){
            imexId = null;
        }
    }

    protected void clearPropertiesLinkedToXrefs() {
        imexId = null;
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
                    processAddedPrimaryAndSecondaryRefs((XmlXref)added);
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
                    processAddedPrimaryAndSecondaryRefs((XmlXref) added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null && removed instanceof XmlXref){
                processRemovedPrimaryAndSecondaryRefs((XmlXref) removed);
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
