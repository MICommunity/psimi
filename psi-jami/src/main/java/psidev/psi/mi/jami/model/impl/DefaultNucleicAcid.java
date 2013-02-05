package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactNucleicAcidComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Default implementation for NucleicAcid.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultNucleicAcid extends DefaultInteractor implements NucleicAcid{

    private ExternalIdentifier ddbjEmblGenbank;
    private ExternalIdentifier refseq;
    private String sequence;

    public DefaultNucleicAcid(String name, CvTerm type) {
        super(name, type);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultNucleicAcid(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultNucleicAcid(String name, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultNucleicAcid(String name, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public String getDdbjEmblGenbank() {
        return this.ddbjEmblGenbank != null ? this.ddbjEmblGenbank.getId() : null;
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new NucleicAcidIdentifierList();
    }

    public void setDdbjEmblGenbank(String id) {
        // add new ddbj/embl/genbank if not null
        if (id != null){
            CvTerm ddbjEmblGenbankDatabase = CvTermFactory.createDdbjEmblGenbankDatabase();
            // first remove old ddbj/embl/genbank if not null
            if (this.ddbjEmblGenbank != null){
                identifiers.remove(this.ddbjEmblGenbank);
            }
            this.ddbjEmblGenbank = new DefaultExternalIdentifier(ddbjEmblGenbankDatabase, id);
            this.identifiers.add(this.ddbjEmblGenbank);
        }
        // remove all ddbj/embl/genbank if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((NucleicAcidIdentifierList) identifiers).removeAllDdbjEmblGenbank();
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String id) {
        // add new refseq if not null
        if (id != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            // first remove refseq if not null
            if (this.refseq!= null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new DefaultExternalIdentifier(refseqDatabase, id);
            this.identifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((NucleicAcidIdentifierList) identifiers).removeAllRefseq();
        }
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof NucleicAcid)){
            return false;
        }

        // use UnambiguousExactNucleicAcid comparator for equals
        return UnambiguousExactNucleicAcidComparator.areEquals(this, (NucleicAcid) o);
    }

    @Override
    public String toString() {
        return ddbjEmblGenbank != null ? ddbjEmblGenbank.getId() : (refseq != null ? refseq.getId() : super.toString());
    }

    /**
     * Comparator which sorts external identifiers so DDBJ/EMBL/GenBank identifiers are always first, then refseq.
     */
    private class NucleicAcidIdentifierComparator implements Comparator<ExternalIdentifier> {

        public int compare(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            if (externalIdentifier1 == null && externalIdentifier2 == null){
                return EQUAL;
            }
            else if (externalIdentifier1 == null){
                return AFTER;
            }
            else if (externalIdentifier2 == null){
                return BEFORE;
            }
            else {
                // compares databases first : ddbj/EMBL/Genbank is before, then refseq
                CvTerm database1 = externalIdentifier1.getDatabase();
                CvTerm database2 = externalIdentifier2.getDatabase();
                ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();
                ExternalIdentifier databaseId2 = database2.getOntologyIdentifier();

                // if external id of database is set, look at database id only otherwise look at shortname
                int comp;
                if (databaseId1 != null && databaseId2 != null){
                    // both are ensembl, sort by id
                    if (Xref.DDBJ_EMBL_GENBANK_ID.equals(databaseId1.getId()) && Xref.DDBJ_EMBL_GENBANK_ID.equals(databaseId2.getId())){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), ddbjEmblGenbank != null ? ddbjEmblGenbank.getId() : null);
                    }
                    // ddbj/embl/genbank is first
                    else if (Xref.DDBJ_EMBL_GENBANK_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.DDBJ_EMBL_GENBANK_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ_ID.equals(databaseId1.getId()) && Xref.REFSEQ_ID.equals(databaseId2.getId())){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), refseq != null ? refseq.getId() : null);
                    }
                    // refseq is first
                    else if (Xref.REFSEQ_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.REFSEQ_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both databases are not standard gene databases
                    else {
                        comp = databaseId1.getId().compareTo(databaseId2.getId());
                    }
                }
                else {
                    String databaseName1 = database1.getShortName().toLowerCase().trim();
                    String databaseName2 = database2.getShortName().toLowerCase().trim();
                    // both are ddbj/embl/genbank, sort by id
                    if (Xref.DDBJ_EMBL_GENBANK.equals(databaseName1) && Xref.DDBJ_EMBL_GENBANK.equals(databaseName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), ddbjEmblGenbank != null ? ddbjEmblGenbank.getId() : null);
                    }
                    // ddbj/embl/genbank is first
                    else if (Xref.DDBJ_EMBL_GENBANK.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.DDBJ_EMBL_GENBANK.equals(databaseName2)){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ.equals(databaseName1) && Xref.REFSEQ.equals(databaseName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), refseq != null ? refseq.getId() : null);
                    }
                    // refseq is first
                    else if (Xref.REFSEQ.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.REFSEQ.equals(databaseName2)){
                        return AFTER;
                    }
                    // both databases are not standard gene databases
                    else {
                        comp = databaseName1.compareTo(databaseName2);
                    }
                }

                if (comp != 0){
                    return comp;
                }
                // check identifiers which cannot be null
                String id1 = externalIdentifier1.getId();
                String id2 = externalIdentifier2.getId();

                return id1.compareTo(id2);
            }
        }
    }

    private class NucleicAcidIdentifierList extends TreeSet<ExternalIdentifier> {
        public NucleicAcidIdentifierList(){
            super(new NucleicAcidIdentifierComparator());
        }

        @Override
        public boolean add(ExternalIdentifier externalIdentifier) {
            boolean added = super.add(externalIdentifier);

            // set DDBJ/EMBL/GenBank, refseq if not done yet
            if (added && (ddbjEmblGenbank == null || refseq == null)){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier firstIdentifier = identifierIterator.next();

                // starts with ddbj embl genbank
                if (ddbjEmblGenbank == null){
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK)){
                        ddbjEmblGenbank = firstIdentifier;
                        if (identifierIterator.hasNext()){
                            firstIdentifier = identifierIterator.next();
                        }
                        else {
                            firstIdentifier = null;
                        }
                    }
                }

                // process refseq
                if (refseq == null){
                    // go through all ddbj embl genbank before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.REFSEQ_ID, Xref.REFSEQ)){
                        refseq = firstIdentifier;
                    }
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // we have nothing left in identifiers, reset standard values
                if (isEmpty()){
                    ddbjEmblGenbank = null;
                    refseq = null;
                }
                else {

                    Iterator<ExternalIdentifier> identifierIterator = iterator();
                    ExternalIdentifier firstIdentifier = identifierIterator.next();

                    // first identifier is ddbj/embl/genbank
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK)){
                        ddbjEmblGenbank = firstIdentifier;
                    }

                    // process refseq
                    // go through all ddbj/embl/genbank before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.REFSEQ_ID, Xref.REFSEQ)){
                        refseq = firstIdentifier;
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            super.clear();
            ddbjEmblGenbank = null;
            refseq = null;
        }

        public void removeAllDdbjEmblGenbank(){

            ExternalIdentifier first = first();
            while (XrefUtils.isXrefFromDatabase(first, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK)){
                remove(first);
                first = first();
            }
        }

        public void removeAllRefseq(){

            if (!isEmpty()){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier first = identifierIterator.next();
                // skip the standard ensembl, ensemblGenomes and entrez gene ids
                while (identifierIterator.hasNext() &&
                        ( XrefUtils.isXrefFromDatabase(first, Xref.DDBJ_EMBL_GENBANK_ID, Xref.DDBJ_EMBL_GENBANK))){
                    first = identifierIterator.next();
                }

                while (identifierIterator.hasNext() && XrefUtils.isXrefFromDatabase(first, Xref.REFSEQ_ID, Xref.REFSEQ)){
                    identifierIterator.remove();
                    first = identifierIterator.next();
                }
            }
        }
    }
}
