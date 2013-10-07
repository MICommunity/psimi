package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Xref container for a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "")
public class PublicationXrefContainer extends XrefContainer {

    private Xref pubmedId;
    private Xref doi;
    private Xref imexId;
    private List<Xref> allIdentifiers;

    @Override
    protected void processRemovedPrimaryRef(XmlXref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            if (((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef)){
                processRemovedPotentialImex(this.primaryRef);
            }
        }
        else {
            processRemovedIdentifierEvent(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        // identity or primary ref
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef) || XrefUtils.doesXrefHaveQualifier(this.primaryRef, Xref.PRIMARY_MI, Xref.PRIMARY)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processAddedIdentifierEvent(this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
            processAddedPotentialImex(this.primaryRef);
        }
    }

    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            allIdentifiers = new FullIdentifierList();
        }
        return allIdentifiers;
    }

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
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.pubmedId);
        }
        // remove all pubmed if the collection is not empty
        else if (!identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.PUBMED_MI, Xref.PUBMED);
            this.pubmedId = null;
        }
    }

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
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.doi);
        }
        // remove all doi if the collection is not empty
        else if (!identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.DOI_MI, Xref.DOI);
            this.doi = null;
        }
    }

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
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.imexId);
        }
        else if (this.imexId != null){
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
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

    protected void processAddedPotentialImex(Xref added) {

        // the added identifier is imex and the current imex is not set
        if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
            // the added xrefContainer is imex-primary
            if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                imexId = added;
            }
        }
    }

    protected void processRemovedPotentialImex(Xref removed) {
        // the removed identifier is pubmed
        if (imexId != null && imexId.equals(removed)){
            imexId = null;
        }
    }

    protected void clearImexId() {
        imexId = null;
    }

    private void processAddedPrimaryAndSecondaryRefs(XmlXref added) {
        if (primaryRef == null){
            primaryRef = added;
        }
        else{
            ((SecondaryXrefList)getJAXBSecondaryRefs()).addOnly(added);
        }
    }

    private void processRemovedPrimaryAndSecondaryRefs(XmlXref removed) {
        if (primaryRef != null && removed.equals(primaryRef)){
            if (!getJAXBSecondaryRefs().isEmpty()){
                primaryRef = secondaryRefs.iterator().next();
                ((SecondaryXrefList)secondaryRefs).removeOnly(primaryRef);
            }
            else{
                primaryRef = null;
            }
        }
    }



    @Override
    protected void processAddedSecondaryXref(XmlXref added) {
        // it is an identifier
        if (XrefUtils.isXrefAnIdentifier(added) || XrefUtils.doesXrefHaveQualifier(this.primaryRef, Xref.PRIMARY_MI, Xref.PRIMARY)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(added);
            processAddedIdentifierEvent(added);
        }
        // it is not an identifier
        else {
            ((FullXrefList)getAllXrefs()).addOnly(added);
            processAddedPotentialImex(added);
        }
    }

    @Override
    protected void processRemovedSecondaryXref(XmlXref removed) {
        // if it is an identifier
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(removed)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(removed);
            processRemovedPotentialImex(removed);
        }
        else {
            processRemovedIdentifierEvent(removed);
        }
    }

    @Override
    protected void clearSecondaryXrefProperties() {
        if (primaryRef != null){
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            identifiersToBeDeleted.remove(primaryRef);
            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                processRemovedIdentifierEvent(ref);
            }

            identifiersToBeDeleted.clear();
            identifiersToBeDeleted.addAll(getAllXrefs());
            identifiersToBeDeleted.remove(primaryRef);
            for (Xref ref : identifiersToBeDeleted){
                ((FullXrefList)getAllXrefs()).removeOnly(ref);
                processRemovedPotentialImex(ref);
            }
        }
        else{
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                processRemovedIdentifierEvent(ref);
            }

            identifiersToBeDeleted.clear();
            identifiersToBeDeleted.addAll(getAllXrefs());
            for (Xref ref : identifiersToBeDeleted){
                ((FullXrefList)getAllXrefs()).removeOnly(ref);
                processRemovedPotentialImex(ref);
            }
        }
    }

    @Override
    protected void processAddedXref(Xref added) {
        if (added instanceof XmlXref){
            processAddedPrimaryAndSecondaryRefs((XmlXref) added);
        }
        processAddedPotentialImex(added);
    }

    @Override
    protected void processRemovedXref(Xref removed) {
        if (removed instanceof XmlXref){
            processRemovedPrimaryAndSecondaryRefs((XmlXref) removed);
        }
        processRemovedPotentialImex(removed);
    }

    @Override
    protected void clearFullXrefProperties() {
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

        ((SecondaryXrefList)getJAXBSecondaryRefs()).retainAllOnly(getAllIdentifiers());
        clearImexId();
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
            ((SecondaryXrefList)getJAXBSecondaryRefs()).retainAllOnly(getAllXrefs());
            clearPropertiesLinkedToIdentifiers();
        }
    }
}
