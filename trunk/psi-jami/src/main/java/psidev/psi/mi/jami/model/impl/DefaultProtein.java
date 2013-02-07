package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractAliasList;
import psidev.psi.mi.jami.utils.collection.AbstractChecksumList;
import psidev.psi.mi.jami.utils.collection.AbstractXrefList;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactProteinComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Default implementation for proteins and peptides
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultProtein extends DefaultInteractor implements Protein {

    private Xref uniprotkb;
    private Xref refseq;
    private Alias geneName;
    private Checksum rogid;
    private String sequence;

    public DefaultProtein(String name, CvTerm type) {
        super(name, type);
    }

    public DefaultProtein(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultProtein(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultProtein(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultProtein(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new ProteinIdentifierList();
    }

    @Override
    protected void initializeChecksums() {
        this.checksums = new ProteinChecksumList();
    }

    @Override
    protected void initializeAliases() {
        this.aliases = new ProteinAliasList();
    }

    public String getUniprotkb() {
        return this.uniprotkb != null ? this.uniprotkb.getId() : null;
    }

    public void setUniprotkb(String ac) {
        // add new uniprotkb if not null
        if (ac != null){
            CvTerm uniprotkbDatabase = CvTermFactory.createUniprotkbDatabase();
            // first remove old uniprotkb if not null
            if (this.uniprotkb != null){
                identifiers.remove(this.uniprotkb);
            }
            this.uniprotkb = new DefaultExternalIdentifier(uniprotkbDatabase, ac);
            this.identifiers.add(this.uniprotkb);
        }
        // remove all uniprotkb if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.UNIPROTKB_ID, Xref.UNIPROTKB);
            this.uniprotkb = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            // first remove old refseq if not null
            if (this.refseq != null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new DefaultExternalIdentifier(refseqDatabase, ac);
            this.identifiers.add(this.refseq);
        }
        // remove all refseq if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.REFSEQ_ID, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    public String getGeneName() {
        return this.geneName != null ? this.geneName.getName() : null;
    }

    public void setGeneName(String name) {
        // add new gene name if not null
        if (name != null){
            CvTerm geneNameType = CvTermFactory.createGeneNameAliasType();
            // first remove old gene name if not null
            if (this.geneName != null){
                aliases.remove(this.geneName);
            }
            this.geneName = new DefaultAlias(geneNameType, name);
            this.aliases.add(this.geneName);
        }
        // remove all gene names if the collection is not empty
        else if (!this.aliases.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(aliases, Alias.GENE_NAME_ID, Alias.GENE_NAME);
            this.geneName = null;
        }
    }

    public String getRogid() {
        return this.rogid != null ? this.rogid.getValue() : null;
    }

    public void setRogid(String rogid) {
        if (rogid != null){
            CvTerm rogidMethod = CvTermFactory.createRogid();
            // first remove old rogid
            if (this.rogid != null){
                this.checksums.remove(this.rogid);
            }
            this.rogid = new DefaultChecksum(rogidMethod, rogid);
            this.checksums.add(this.rogid);
        }
        // remove all smiles if the collection is not empty
        else if (!this.checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksums, Checksum.ROGID_ID, Checksum.ROGID);
            this.rogid = null;
        }
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    private class ProteinIdentifierList extends AbstractXrefList {
        public ProteinIdentifierList(){
            super();
        }

        @Override
        protected void processAddedXrefEvent(Xref added) {
            // the added identifier is uniprotkb and it is not the current uniprotkb identifier
            if (uniprotkb != added && XrefUtils.isXrefFromDatabase(added, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
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
            else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_ID, Xref.REFSEQ)){
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

        @Override
        protected void processRemovedXrefEvent(Xref removed) {
            if (uniprotkb == removed){
                uniprotkb = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.UNIPROTKB_ID, Xref.UNIPROTKB);
            }
            else if (refseq == removed){
                refseq = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.REFSEQ_ID, Xref.REFSEQ);
            }
        }

        @Override
        protected void clearProperties() {
            uniprotkb = null;
            refseq = null;
        }
    }

    private class ProteinChecksumList extends AbstractChecksumList{
        public ProteinChecksumList(){
            super();
        }

        @Override
        protected void processAddedChecksumEvent(Checksum added) {
            if (rogid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.ROGID_ID, Checksum.ROGID)){
                // the rogid is not set, we can set the rogid
                rogid = added;
            }
        }

        @Override
        protected void processRemovedChecksumEvent(Checksum removed) {
            if (rogid == removed){
                rogid = ChecksumUtils.collectFirstChecksumWithMethod(this, Checksum.ROGID_ID, Checksum.ROGID);
            }
        }

        @Override
        protected void clearProperties() {
            rogid = null;
        }
    }

    private class ProteinAliasList extends AbstractAliasList{
        public ProteinAliasList(){
            super();
        }

        @Override
        protected void processAddedAliasEvent(Alias added) {
            // the added alias is gene name and it is not the current gene name
            if (geneName == null && AliasUtils.doesAliasHaveType(added, Alias.GENE_NAME_ID, Alias.GENE_NAME)){
                geneName = added;
            }
        }

        @Override
        protected void processRemovedAliasEvent(Alias removed) {
            if (geneName == removed){
                geneName = AliasUtils.collectFirstAliasWithType(this, Alias.GENE_NAME_ID, Alias.GENE_NAME);
            }
        }

        @Override
        protected void clearProperties() {
            geneName = null;
        }
    }
}
