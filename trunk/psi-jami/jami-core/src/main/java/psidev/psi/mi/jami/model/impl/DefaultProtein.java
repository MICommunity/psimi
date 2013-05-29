package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactProteinComparator;

import java.util.Collection;

/**
 * Default implementation for proteins and peptides
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultProtein extends DefaultPolymer implements Protein {

    private Xref uniprotkb;
    private Xref refseq;
    private Alias geneName;
    private Checksum rogid;

    public DefaultProtein(String name, CvTerm type) {
        super(name, type != null ? type : CvTermUtils.createProteinInteractorType());
    }

    public DefaultProtein(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : CvTermUtils.createProteinInteractorType());
    }

    public DefaultProtein(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : CvTermUtils.createProteinInteractorType(), organism);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : CvTermUtils.createProteinInteractorType(), organism);
    }

    public DefaultProtein(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createProteinInteractorType(), uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createProteinInteractorType(), uniqueId);
    }

    public DefaultProtein(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createProteinInteractorType(), organism, uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createProteinInteractorType(), organism, uniqueId);
    }

    public DefaultProtein(String name) {
        super(name, CvTermUtils.createProteinInteractorType());
    }

    public DefaultProtein(String name, String fullName) {
        super(name, fullName, CvTermUtils.createProteinInteractorType());
    }

    public DefaultProtein(String name, Organism organism) {
        super(name, CvTermUtils.createProteinInteractorType(), organism);
    }

    public DefaultProtein(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermUtils.createProteinInteractorType(), organism);
    }

    public DefaultProtein(String name, Xref uniqueId) {
        super(name, CvTermUtils.createProteinInteractorType(), uniqueId);
    }

    public DefaultProtein(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createProteinInteractorType(), uniqueId);
    }

    public DefaultProtein(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermUtils.createProteinInteractorType(), organism, uniqueId);
    }

    public DefaultProtein(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createProteinInteractorType(), organism, uniqueId);
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new ProteinIdentifierList());
    }

    @Override
    protected void initialiseChecksums() {
        initialiseChecksumsWith(new ProteinChecksumList());
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new ProteinAliasList());
    }

    public String getUniprotkb() {
        return this.uniprotkb != null ? this.uniprotkb.getId() : null;
    }

    public void setUniprotkb(String ac) {
        Collection<Xref> proteinIdentifiers = getIdentifiers();

        // add new uniprotkb if not null
        if (ac != null){
            CvTerm uniprotkbDatabase = CvTermUtils.createUniprotkbDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old uniprotkb if not null
            if (this.uniprotkb != null){
                proteinIdentifiers.remove(this.uniprotkb);
            }
            this.uniprotkb = new DefaultXref(uniprotkbDatabase, ac, identityQualifier);
            proteinIdentifiers.add(this.uniprotkb);
        }
        // remove all uniprotkb if the collection is not empty
        else if (!proteinIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(proteinIdentifiers, Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
            this.uniprotkb = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        Collection<Xref> proteinIdentifiers = getIdentifiers();

        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermUtils.createRefseqDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old refseq if not null
            if (this.refseq != null){
                proteinIdentifiers.remove(this.refseq);
            }
            this.refseq = new DefaultXref(refseqDatabase, ac, identityQualifier);
            proteinIdentifiers.add(this.refseq);
        }
        // remove all refseq if the collection is not empty
        else if (!proteinIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(proteinIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    public String getGeneName() {
        return this.geneName != null ? this.geneName.getName() : null;
    }

    public void setGeneName(String name) {
        Collection<Alias> proteinAliases = getAliases();

        // add new gene name if not null
        if (name != null){
            CvTerm geneNameType = CvTermUtils.createGeneNameAliasType();
            // first remove old gene name if not null
            if (this.geneName != null){
                proteinAliases.remove(this.geneName);
            }
            this.geneName = new DefaultAlias(geneNameType, name);
            proteinAliases.add(this.geneName);
        }
        // remove all gene names if the collection is not empty
        else if (!proteinAliases.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(proteinAliases, Alias.GENE_NAME_MI, Alias.GENE_NAME);
            this.geneName = null;
        }
    }

    public String getRogid() {
        return this.rogid != null ? this.rogid.getValue() : null;
    }

    public void setRogid(String rogid) {
        Collection<Checksum> proteinChecksums = getChecksums();

        if (rogid != null){
            CvTerm rogidMethod = CvTermUtils.createRogid();
            // first remove old rogid
            if (this.rogid != null){
                proteinChecksums.remove(this.rogid);
            }
            this.rogid = new DefaultChecksum(rogidMethod, rogid);
            proteinChecksums.add(this.rogid);
        }
        // remove all smiles if the collection is not empty
        else if (!proteinChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(proteinChecksums, Checksum.ROGID_MI, Checksum.ROGID);
            this.rogid = null;
        }
    }

    protected void processAddedAliasEvent(Alias added) {
        // the added alias is gene name and it is not the current gene name
        if (geneName == null && AliasUtils.doesAliasHaveType(added, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
            geneName = added;
        }
    }

    protected void processRemovedAliasEvent(Alias removed) {
        if (geneName != null && geneName.equals(removed)){
            geneName = AliasUtils.collectFirstAliasWithType(getAliases(), Alias.GENE_NAME_MI, Alias.GENE_NAME);
        }
    }

    protected void clearPropertiesLinkedToAliases() {
        geneName = null;
    }

    protected void processAddedChecksumEvent(Checksum added) {
        if (rogid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.ROGID_MI, Checksum.ROGID)){
            // the rogid is not set, we can set the rogid
            rogid = added;
        }
    }

    protected void processRemovedChecksumEvent(Checksum removed) {
        if (rogid != null && rogid.equals(removed)){
            rogid = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.ROGID_MI, Checksum.ROGID);
        }
    }

    protected void clearPropertiesLinkedToChecksums() {
        rogid = null;
    }

    protected void processAddedIdentifierEvent(Xref added) {
        // the added identifier is uniprotkb and it is not the current uniprotkb identifier
        if (uniprotkb != added && XrefUtils.isXrefFromDatabase(added, Xref.UNIPROTKB_MI, Xref.UNIPROTKB)){
            // the current uniprotkb identifier is not identity, we may want to set uniprotkb Identifier
            if (!XrefUtils.doesXrefHaveQualifier(uniprotkb, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the uniprotkb identifier is not set, we can set the uniprotkb identifier
                if (uniprotkb == null){
                    uniprotkb = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    uniprotkb = added;
                }
                // the added xref is secondary object and the current uniprotkb identifier is not a secondary object, we reset uniprotkb identifier
                else if (!XrefUtils.doesXrefHaveQualifier(uniprotkb, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    uniprotkb = added;
                }
            }
        }
        // the added identifier is refseq id and it is not the current refseq id
        else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            // the current refseq id is not identity, we may want to set refseq id
            if (!XrefUtils.doesXrefHaveQualifier(refseq, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the refseq id is not set, we can set the refseq id
                if (refseq == null){
                    refseq = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    refseq = added;
                }
                // the added xref is secondary object and the current refseq id is not a secondary object, we reset refseq id
                else if (!XrefUtils.doesXrefHaveQualifier(refseq, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    refseq = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        if (uniprotkb != null && uniprotkb.equals(removed)){
            uniprotkb = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        }
        else if (refseq != null && refseq.equals(removed)){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        uniprotkb = null;
        refseq = null;
    }

    @Override
    /**
     * Sets the interactor type of this protein.
     * If the given interactorType is null, it will set the interactor type to 'protein' (MI:0326)
     */
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            super.setInteractorType(CvTermUtils.createProteinInteractorType());
        }
        else {
            super.setInteractorType(interactorType);
        }
    }

    @Override
    public String toString() {
        return geneName != null ? geneName.getName() : (uniprotkb != null ? uniprotkb.getId() : (refseq != null ? refseq.getId() : super.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Protein)){
            return false;
        }

        // use UnambiguousExactProtein comparator for equals
        return UnambiguousExactProteinComparator.areEquals(this, (Protein) o);
    }

    private class ProteinIdentifierList extends AbstractListHavingPoperties<Xref> {
        public ProteinIdentifierList(){
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

    private class ProteinChecksumList extends AbstractListHavingPoperties<Checksum>{
        public ProteinChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {
            processAddedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToChecksums();
        }
    }

    private class ProteinAliasList extends AbstractListHavingPoperties<Alias>{
        public ProteinAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Alias added) {
            processAddedAliasEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {
            processRemovedAliasEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAliases();
        }
    }
}
