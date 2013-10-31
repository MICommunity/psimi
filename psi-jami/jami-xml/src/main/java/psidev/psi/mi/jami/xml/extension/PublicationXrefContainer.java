package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;

/**
 * Xref container for a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "publicationXrefContainer")
public class PublicationXrefContainer extends XrefContainer {

    private Xref pubmedId;
    private Xref doi;
    private Xref imexId;
    private List<Xref> identifiers;

    @Override
    protected void processAddedPrimaryRef(Xref added) {
        // identity or primary ref
        if (XrefUtils.isXrefAnIdentifier(added) || XrefUtils.doesXrefHaveQualifier(added, Xref.PRIMARY_MI, Xref.PRIMARY)){
            getIdentifiers().add(added);
        }
        else {
            getXrefs().add(added);
        }
    }

    public Collection<Xref> getIdentifiers() {
        if (identifiers == null){
            initialiseIdentifiers();
        }
        return identifiers;
    }

    public String getPubmedId() {
        return this.pubmedId != null ? this.pubmedId.getId() : null;
    }

    public void setPubmedId(String pubmedId) {
        FullIdentifierList identifiers = (FullIdentifierList) getIdentifiers();

        // add new pubmed if not null
        if (pubmedId != null){
            CvTerm pubmedDatabase = CvTermUtils.createPubmedDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old pubmed if not null
            if (this.pubmedId != null){
                identifiers.removeOnly(this.pubmedId);
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

    public String getDoi() {
        return this.doi != null ? this.doi.getId() : null;
    }

    public void setDoi(String doi) {
        FullIdentifierList identifiers = (FullIdentifierList) getIdentifiers();
        // add new doi if not null
        if (doi != null){
            CvTerm doiDatabase = CvTermUtils.createDoiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old doi if not null
            if (this.doi != null){
                identifiers.removeOnly(this.doi);
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

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        FullXrefList xrefs = (FullXrefList) getXrefs();
        // add new imex if not null
        if (identifier != null){
            CvTerm imexDatabase = CvTermUtils.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermUtils.createImexPrimaryQualifier();
            // first remove old imex if not null
            if (this.imexId != null){
                xrefs.removeOnly(this.imexId);
            }
            this.imexId = new XmlXref(imexDatabase, identifier, imexPrimaryQualifier);
            xrefs.addOnly(this.imexId);
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
            pubmedId = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.PUBMED_MI, Xref.PUBMED);
        }
        // the removed identifier is doi
        else if (doi != null && doi.equals(removed)){
            doi = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.DOI_MI, Xref.DOI);
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

    protected void initialiseIdentifiers(){
        this.identifiers = new FullIdentifierList();
    }

    @Override
    protected void initialiseXrefs() {
        super.initialiseXrefsWith(new FullXrefList());
    }

    @Override
    protected void initialiseSecondaryRefs() {
        super.initialiseSecondaryResWith(new JAXBSecondaryXrefList());
    }

    ///////////////////////////// classes
    //////////////////////////////// private class
    private class JAXBSecondaryXrefList extends XrefContainer.JAXBSecondaryXrefList{

        private JAXBSecondaryXrefList() {
            super();
            if (identifiers == null){
                initialiseIdentifiers();
            }
        }

        protected boolean addXref(Integer index, Xref xref) {
            if (XrefUtils.isXrefAnIdentifier(xref)){
                return addIdentifier(index, xref);
            }
            else{
                return processXref(index, xref);
            }

        }
        protected boolean addIdentifier(Integer index, Xref xref) {
            if (index == null){
                return identifiers.add(xref);
            }
            identifiers.add(index, xref);
            return true;
        }

        private boolean processXref(Integer index, Xref xref) {
            if (index == null){
                return getXrefs().add(xref);
            }
            getXrefs().add(index, xref);
            return true;
        }
    }
    private class FullIdentifierList extends AbstractListHavingProperties<Xref> {
        public FullIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();
        }
    }

    private class FullXrefList extends AbstractListHavingProperties<Xref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            processAddedPotentialImex(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedPotentialImex(removed);
        }

        @Override
        protected void clearProperties() {
            clearImexId();
        }
    }
}
