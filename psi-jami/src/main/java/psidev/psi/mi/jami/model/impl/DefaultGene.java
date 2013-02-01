package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactGeneComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Default implementation for gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultGene extends DefaultInteractor implements Gene {

    private ExternalIdentifier ensembl;
    private ExternalIdentifier ensemblGenome;
    private ExternalIdentifier entrezGeneId;
    private ExternalIdentifier refseq;

    public DefaultGene(String name) {
        super(name, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, String fullName) {
        super(name, fullName, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, Organism organism) {
        super(name, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, ExternalIdentifier uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, String fullName, ExternalIdentifier uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, Organism organism, ExternalIdentifier uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
    }

    public DefaultGene(String name, String fullName, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new GeneIdentifierList();
    }

    public String getEnsembl() {
        return this.ensembl != null ? this.ensembl.getId() : null;
    }

    public void setEnsembl(String ac) {
        // add new ensembl if not null
        if (ac != null){
            CvTerm ensemblDatabase = CvTermFactory.createEnsemblDatabase();
            // first remove old ensembl if not null
            if (this.ensembl != null){
                identifiers.remove(this.ensembl);
            }
            this.ensembl = new DefaultExternalIdentifier(ensemblDatabase, ac);
            this.identifiers.add(this.ensembl);
        }
        // remove all ensembl if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((GeneIdentifierList) identifiers).removeAllEnsembl();
        }
    }

    public String getEnsembleGenome() {
        return this.ensemblGenome != null ? this.ensemblGenome.getId() : null;
    }

    public void setEnsemblGenome(String ac) {
        // add new ensembl genomes if not null
        if (ac != null){
            CvTerm ensemblGenomesDatabase = CvTermFactory.createEnsemblGenomesDatabase();
            // first remove old ensembl genome if not null
            if (this.ensemblGenome != null){
                identifiers.remove(this.ensemblGenome);
            }
            this.ensemblGenome = new DefaultExternalIdentifier(ensemblGenomesDatabase, ac);
            this.identifiers.add(this.ensemblGenome);
        }
        // remove all ensembl genomes if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((GeneIdentifierList) identifiers).removeAllEnsembleGenomes();
        }
    }

    public String getEntrezGeneId() {
        return this.entrezGeneId != null ? this.entrezGeneId.getId() : null;
    }

    public void setEntrezGeneId(String id) {
        // add new entrez gene id genomes if not null
        if (id != null){
            CvTerm entrezDatabase = CvTermFactory.createEntrezGeneIdDatabase();
            // first remove old entrez gene id if not null
            if (this.entrezGeneId!= null){
                identifiers.remove(this.entrezGeneId);
            }
            this.entrezGeneId = new DefaultExternalIdentifier(entrezDatabase, id);
            this.identifiers.add(this.entrezGeneId);
        }
        // remove all ensembl genomes if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((GeneIdentifierList) identifiers).removeAllEntrezGeneId();
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            // first remove refseq if not null
            if (this.refseq!= null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new DefaultExternalIdentifier(refseqDatabase, ac);
            this.identifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the list is not empty
        else if (!this.identifiers.isEmpty()) {
            ((GeneIdentifierList) identifiers).removeAllRefseq();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Gene)){
            return false;
        }

        // use UnambiguousExactGeneEntity comparator for equals
        return UnambiguousExactGeneComparator.areEquals(this, (Gene) o);
    }

    @Override
    public String toString() {
        return ensembl != null ? ensembl.getId() : (ensemblGenome != null ? ensemblGenome.getId() : (entrezGeneId != null ? entrezGeneId.getId() : (refseq != null ? refseq.getId() : super.toString())));
    }

    /**
     * Comparator which sorts external identifiers so ensembl identifiers are always first, then ensemblGenomes, then entrez/geneId and then refseq
     */
    private class GeneIdentifierComparator implements Comparator<ExternalIdentifier> {

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
                // compares databases first : ensembl is before, then sensemblgenomes, then entrez gene id and then refseq
                CvTerm database1 = externalIdentifier1.getDatabase();
                CvTerm database2 = externalIdentifier2.getDatabase();
                ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();
                ExternalIdentifier databaseId2 = database2.getOntologyIdentifier();

                // if external id of database is set, look at database id only otherwise look at shortname
                int comp;
                if (databaseId1 != null && databaseId2 != null){
                    // both are ensembl, sort by id
                    if (Xref.ENSEMBL_ID.equals(databaseId1.getId()) && Xref.ENSEMBL_ID.equals(databaseId2.getId())){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, ensembl);
                    }
                    // ensembl is first
                    else if (Xref.ENSEMBL_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.ENSEMBL_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both are ensembl genomes, sort by id
                    else if (Xref.ENSEMBL_GENOMES_ID.equals(databaseId1.getId()) && Xref.ENSEMBL_GENOMES_ID.equals(databaseId2.getId())){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, ensemblGenome);
                    }
                    // ensembl genomes is second
                    else if (Xref.ENSEMBL_GENOMES_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.ENSEMBL_GENOMES_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both are entrez gene id, sort by id
                    else if (Xref.ENTREZ_GENE_ID.equals(databaseId1.getId()) && Xref.ENTREZ_GENE_ID.equals(databaseId2.getId())){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, entrezGeneId);
                    }
                    // entrez gene id is second
                    else if (Xref.ENTREZ_GENE_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.ENTREZ_GENE_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ_ID.equals(databaseId1.getId()) && Xref.REFSEQ_ID.equals(databaseId2.getId())){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, refseq);
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
                    // both are ensembl, sort by id
                    if (Xref.ENSEMBL.equals(databaseName1) && Xref.ENSEMBL.equals(databaseName2)){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, ensembl);
                    }
                    // ensembl is first
                    else if (Xref.ENSEMBL.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.ENSEMBL.equals(databaseName2)){
                        return AFTER;
                    }
                    // both are ensembl genomes, sort by id
                    else if (Xref.ENSEMBL_GENOMES.equals(databaseName1) && Xref.ENSEMBL_GENOMES.equals(databaseName2)){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, ensemblGenome);
                    }
                    // ensembl genomes is second
                    else if (Xref.ENSEMBL_GENOMES.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.ENSEMBL_GENOMES.equals(databaseName2)){
                        return AFTER;
                    }
                    // both are entrez gene id, sort by id
                    else if (Xref.ENTREZ_GENE.equals(databaseName1) && Xref.ENTREZ_GENE.equals(databaseName2)){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, entrezGeneId);
                    }
                    // entrez gene id is second
                    else if (Xref.ENTREZ_GENE.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.ENTREZ_GENE.equals(databaseName2)){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ.equals(databaseName1) && Xref.REFSEQ.equals(databaseName2)){
                        return compareGeneIdentifiers(externalIdentifier1, externalIdentifier2, refseq);
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

        private int compareGeneIdentifiers(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2, ExternalIdentifier geneProperty) {
            int comp;
            String id1 = externalIdentifier1.getId();
            String id2 = externalIdentifier2.getId();
            comp = id1.compareTo(id2);
            if (comp == 0){
                return 0;
            }

            // the unique gene property is first
            if (geneProperty != null && geneProperty.getId().equals(id1)){
                return -1;
            }
            else if (geneProperty != null && geneProperty.getId().equals(id2)){
                return 1;
            }
            else {
                return comp;
            }
        }
    }

    private class GeneIdentifierList extends TreeSet<ExternalIdentifier> {
        public GeneIdentifierList(){
            super(new GeneIdentifierComparator());
        }

        @Override
        public boolean add(ExternalIdentifier externalIdentifier) {
            boolean added = super.add(externalIdentifier);

            // set ensembl, ensemblgenomes, entrez gene id or refseq if not done yet
            if (added && (ensembl == null || ensemblGenome == null || entrezGeneId == null || refseq == null)){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier firstIdentifier = identifierIterator.next();

                // starts with ensembl
                if (ensembl == null){
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                        ensembl = firstIdentifier;
                        if (identifierIterator.hasNext()){
                            firstIdentifier = identifierIterator.next();
                        }
                        else {
                            firstIdentifier = null;
                        }
                    }
                }

                // process ensemblgenome
                if (ensemblGenome == null){
                    // go through all ensembl before finding smiles
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES)){
                        ensemblGenome = firstIdentifier;
                        if (identifierIterator.hasNext()){
                            firstIdentifier = identifierIterator.next();
                        }
                        else {
                            firstIdentifier = null;
                        }
                    }
                }

                // process entrez gene id
                if (entrezGeneId == null){
                    // go through all ensemble genomes before finding entrez gene id
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE)){
                        entrezGeneId = firstIdentifier;
                    }
                }

                // process refseq
                if (refseq == null){
                    // go through all entrez gene id before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE)){
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
                    ensembl = null;
                    ensemblGenome = null;
                    entrezGeneId = null;
                    refseq = null;
                }
                else {

                    Iterator<ExternalIdentifier> identifierIterator = iterator();
                    ExternalIdentifier firstIdentifier = identifierIterator.next();

                    // first identifier is ensembl
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                        ensembl = firstIdentifier;
                    }
                    // process ensemblGenome
                    // go through all ensembl
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES)){
                        ensemblGenome = firstIdentifier;
                    }

                    // process entrez gene id
                    // go through all ensembl genomes before finding entrez gene id
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES)){
                        firstIdentifier = identifierIterator.next();
                    }

                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE)){
                        entrezGeneId = firstIdentifier;
                    }

                    // process refseq
                    // go through all entrez gene id before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE)){
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
            ensembl = null;
            ensemblGenome = null;
            entrezGeneId = null;
            refseq = null;
        }

        public void removeAllEnsembl(){

            ExternalIdentifier first = first();
            while (XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                remove(first);
            }
        }

        public void removeAllEnsembleGenomes(){

            if (!isEmpty()){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier first = identifierIterator.next();
                // skip the ensembl
                while (identifierIterator.hasNext() && XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_ID, Xref.ENSEMBL)){
                    first = identifierIterator.next();
                }

                while (identifierIterator.hasNext() && XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES)){
                    identifierIterator.remove();
                    first = identifierIterator.next();
                }
            }
        }

        public void removeAllEntrezGeneId(){

            if (!isEmpty()){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier first = identifierIterator.next();
                // skip the standard ensembl and ensemblGenomes
                while (identifierIterator.hasNext() &&
                        ( XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_ID, Xref.ENSEMBL) ||
                                XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES) )){
                    first = identifierIterator.next();
                }

                while (identifierIterator.hasNext() && XrefUtils.isXrefFromDatabase(first, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE)){
                    identifierIterator.remove();
                    first = identifierIterator.next();
                }
            }
        }

        public void removeAllRefseq(){

            if (!isEmpty()){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier first = identifierIterator.next();
                // skip the standard ensembl, ensemblGenomes and entrez gene ids
                while (identifierIterator.hasNext() &&
                        ( XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_ID, Xref.ENSEMBL) ||
                                XrefUtils.isXrefFromDatabase(first, Xref.ENSEMBL_GENOMES_ID, Xref.ENSEMBL_GENOMES) ||
                                XrefUtils.isXrefFromDatabase(first, Xref.ENTREZ_GENE_ID, Xref.ENTREZ_GENE))){
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
