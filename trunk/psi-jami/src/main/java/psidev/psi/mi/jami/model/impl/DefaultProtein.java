package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactProteinComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Default implementation for proteins and peptides
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultProtein extends DefaultInteractor implements Protein {

    private ExternalIdentifier uniprotkb;
    private ExternalIdentifier refseq;
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

    public DefaultProtein(String name, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultProtein(String name, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultProtein(String name, String fullName, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
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
            ((ProteinIdentifierList) identifiers).removeAllUniprotkb();
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
            ((ProteinIdentifierList) identifiers).removeAllRefseq();
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
            ((ProteinAliasList) aliases).removeAllGeneNames();
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
            ((ProteinChecksumList) checksums).removeAllRogids();
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

    /**
     * Comparator which sorts external identifiers so uniprotkb identifiers are always first, then refseq
     */
    private class ProteinIdentifierComparator implements Comparator<ExternalIdentifier> {

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
                // compares databases first : uniprotkb is before
                CvTerm database1 = externalIdentifier1.getDatabase();
                CvTerm database2 = externalIdentifier2.getDatabase();
                String databaseId1 = database1.getMIIdentifier();
                String databaseId2 = database2.getMIIdentifier();

                // if external id of database is set, look at database id only otherwise look at shortname
                int comp;
                if (databaseId1 != null && databaseId2 != null){
                    // both are uniprotkb, sort by id
                    if (Xref.UNIPROTKB_ID.equals(databaseId1) && Xref.UNIPROTKB_ID.equals(databaseId2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), uniprotkb != null ? uniprotkb.getId() : null);
                    }
                    // uniprotkb is first
                    else if (Xref.UNIPROTKB_ID.equals(databaseId1)){
                        return BEFORE;
                    }
                    else if (Xref.UNIPROTKB_ID.equals(databaseId2)){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ_ID.equals(databaseId1) && Xref.REFSEQ_ID.equals(databaseId2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), refseq != null ? refseq.getId() : null);
                    }
                    // refseq is second
                    else if (Xref.REFSEQ_ID.equals(databaseId1)){
                        return BEFORE;
                    }
                    else if (Xref.REFSEQ_ID.equals(databaseId2)){
                        return AFTER;
                    }
                    // both databases are not standard protein databases
                    else {
                        comp = databaseId1.compareTo(databaseId2);
                    }
                }
                else {
                    String databaseName1 = database1.getShortName().toLowerCase().trim();
                    String databaseName2 = database2.getShortName().toLowerCase().trim();
                    // both are uniprotkb, sort by id
                    if (Xref.UNIPROTKB.equals(databaseName1) && Xref.UNIPROTKB.equals(databaseName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), uniprotkb != null ? uniprotkb.getId() : null);
                    }
                    // uniprotkb is first
                    else if (Xref.UNIPROTKB.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.UNIPROTKB.equals(databaseName2)){
                        return AFTER;
                    }
                    // both are refseq, sort by id
                    else if (Xref.REFSEQ.equals(databaseName1) && Xref.REFSEQ.equals(databaseName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(externalIdentifier1.getId(), externalIdentifier2.getId(), refseq != null ? refseq.getId() : null);
                    }
                    // refseq is second
                    else if (Xref.REFSEQ.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.REFSEQ.equals(databaseName2)){
                        return AFTER;
                    }
                    // both databases are not chebi
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

    private class ProteinIdentifierList extends TreeSet<ExternalIdentifier> {
        public ProteinIdentifierList(){
            super(new ProteinIdentifierComparator());
        }

        @Override
        public boolean add(ExternalIdentifier externalIdentifier) {
            boolean added = super.add(externalIdentifier);

            // set uniprotkb or refseq if not done yet
            if (added && (uniprotkb == null || refseq == null)){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier firstIdentifier = identifierIterator.next();

                // starts with uniprotkb
                if (uniprotkb == null){
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
                        uniprotkb = firstIdentifier;
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
                    // go through all uniprotkb before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
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
                    uniprotkb = null;
                    refseq = null;
                }
                else {

                    Iterator<ExternalIdentifier> identifierIterator = iterator();
                    ExternalIdentifier firstIdentifier = identifierIterator.next();

                    // first identifier is uniprotkb
                    if (XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
                        uniprotkb = firstIdentifier;
                    }

                    // process refseq
                    // go through all uniprotkb before finding refseq
                    while (identifierIterator.hasNext() &&
                            XrefUtils.isXrefFromDatabase(firstIdentifier, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
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
            uniprotkb = null;
            refseq = null;
        }

        public void removeAllUniprotkb(){

            ExternalIdentifier first = first();
            while (XrefUtils.isXrefFromDatabase(first, Xref.UNIPROTKB_ID, Xref.UNIPROTKB)){
                remove(first);
                first = first();
            }
        }

        public void removeAllRefseq(){

            if (!isEmpty()){
                Iterator<ExternalIdentifier> identifierIterator = iterator();
                ExternalIdentifier first = identifierIterator.next();
                // skip the standard uniprotkb
                while (identifierIterator.hasNext() &&
                        ( XrefUtils.isXrefFromDatabase(first, Xref.UNIPROTKB_ID, Xref.UNIPROTKB))){
                    first = identifierIterator.next();
                }

                while (identifierIterator.hasNext() && XrefUtils.isXrefFromDatabase(first, Xref.REFSEQ_ID, Xref.REFSEQ)){
                    identifierIterator.remove();
                    first = identifierIterator.next();
                }
            }
        }
    }

    /**
     * Comparator which sorts checksums so rogids are always first
     */
    private class ProteinChecksumComparator implements Comparator<Checksum>{

        public int compare(Checksum checksum1, Checksum checksum2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            if (checksum1 == null && checksum2 == null){
                return EQUAL;
            }
            else if (checksum1 == null){
                return AFTER;
            }
            else if (checksum2 == null){
                return BEFORE;
            }
            else {
                // compares methods first
                CvTerm method1 = checksum1.getMethod();
                CvTerm method2 = checksum2.getMethod();
                String methodId1 = method1.getMIIdentifier();
                String methodId2 = method2.getMIIdentifier();

                // if external id of method is set, look at method id only otherwise look at shortname
                int comp;
                if (methodId1 != null && methodId2 != null){
                    // both are rogids, sort by id
                    if (Checksum.ROGID_ID.equals(methodId1) && Checksum.ROGID_ID.equals(methodId2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(checksum1.getValue(), checksum2.getValue(), rogid != null ? rogid.getValue() : null);
                    }
                    // rogid is first
                    else if (Checksum.ROGID_ID.equals(methodId1)){
                        return BEFORE;
                    }
                    else if (Checksum.ROGID_ID.equals(methodId2)){
                        return AFTER;
                    }
                    // both databases are not standard rogids
                    else {
                        comp = methodId1.compareTo(methodId2);
                    }
                }
                else {
                    String methodName1 = method1.getShortName().toLowerCase().trim();
                    String methodName2 = method2.getShortName().toLowerCase().trim();
                    // both are rogids, sort by id
                    if (Checksum.ROGID.equals(methodName1) && Checksum.ROGID.equals(methodName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(checksum1.getValue(), checksum2.getValue(), rogid != null ? rogid.getValue() : null);
                    }
                    // rogid is first
                    else if (Checksum.ROGID.equals(methodName1)){
                        return BEFORE;
                    }
                    else if (Checksum.ROGID.equals(methodName2)){
                        return AFTER;
                    }
                    // both databases are not rogid
                    else {
                        comp = methodName1.compareTo(methodName2);
                    }
                }

                if (comp != 0){
                    return comp;
                }
                // check values which cannot be null
                String id1 = checksum1.getValue();
                String id2 = checksum2.getValue();

                return id1.compareTo(id2);
            }
        }
    }

    private class ProteinChecksumList extends TreeSet<Checksum>{
        public ProteinChecksumList(){
            super(new ProteinChecksumComparator());
        }

        @Override
        public boolean add(Checksum checksum) {
            boolean added = super.add(checksum);

            // set rogid if not done
            if (added && rogid == null){
                Checksum firstChecksum = first();

                if (ChecksumUtils.doesChecksumHaveMethod(firstChecksum, Checksum.ROGID_ID, Checksum.ROGID)){
                    rogid = firstChecksum;
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // we have nothing left in checksums, reset standard values
                if (isEmpty()){
                    rogid = null;
                }
                else {

                    Checksum firstChecksum = first();

                    // first checksum is rogid
                    if (ChecksumUtils.doesChecksumHaveMethod(firstChecksum, Checksum.ROGID_ID, Checksum.ROGID)){
                        rogid = firstChecksum;
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            super.clear();
            rogid = null;
        }

        public void removeAllRogids(){

            Checksum first = first();
            while (ChecksumUtils.doesChecksumHaveMethod(first, Checksum.ROGID_ID, Checksum.ROGID)){
                remove(first);
                first = first();
            }
        }
    }

    /**
     * Comparator which sorts aliases so gene names are always first
     */
    private class ProteinAliasComparator implements Comparator<Alias>{

        public int compare(Alias alias1, Alias alias2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            if (alias1 == null && alias2 == null){
                return EQUAL;
            }
            else if (alias1 == null){
                return AFTER;
            }
            else if (alias2 == null){
                return BEFORE;
            }
            else {
                // compares alias types first
                CvTerm type1 = alias1.getType();
                CvTerm type2 = alias2.getType();
                String typeId1 = type1.getMIIdentifier();
                String typeId2 = type2.getMIIdentifier();

                // if external id of type is set, look at type id only otherwise look at shortname
                int comp;
                if (typeId1 != null && typeId2 != null){
                    // both are gene names, sort by id
                    if (Alias.GENE_NAME_ID.equals(typeId1) && Alias.GENE_NAME_ID.equals(typeId2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(alias1.getName(), alias2.getName(), geneName != null ? geneName.getName() : null);
                    }
                    // gene name is first
                    else if (Alias.GENE_NAME_ID.equals(typeId1)){
                        return BEFORE;
                    }
                    else if (Alias.GENE_NAME_ID.equals(typeId2)){
                        return AFTER;
                    }
                    // both databases are not standard rogids
                    else {
                        comp = typeId1.compareTo(typeId2);
                    }
                }
                else {
                    String typeName1 = type1.getShortName().toLowerCase().trim();
                    String typeName2 = type2.getShortName().toLowerCase().trim();
                    // both are rogids, sort by id
                    if (Alias.GENE_NAME.equals(typeName1) && Alias.GENE_NAME.equals(typeName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(alias1.getName(), alias2.getName(), geneName != null ? geneName.getName() : null);
                    }
                    // alias name is first
                    else if (Alias.GENE_NAME.equals(typeName1)){
                        return BEFORE;
                    }
                    else if (Alias.GENE_NAME.equals(typeName2)){
                        return AFTER;
                    }
                    // both types are not alias names
                    else {
                        comp = typeName1.compareTo(typeName2);
                    }
                }

                if (comp != 0){
                    return comp;
                }
                // check names which cannot be null
                String id1 = alias1.getName();
                String id2 = alias2.getName();

                return id1.compareTo(id2);
            }
        }
    }

    private class ProteinAliasList extends TreeSet<Alias>{
        public ProteinAliasList(){
            super(new ProteinAliasComparator());
        }

        @Override
        public boolean add(Alias alias) {
            boolean added = super.add(alias);

            // set gene name if not done
            if (added && geneName == null){
                Alias firstAlias = first();

                if (AliasUtils.doesAliasHaveType(firstAlias, Alias.GENE_NAME_ID, Alias.GENE_NAME)){
                    geneName = firstAlias;
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // we have nothing left in aliases, reset standard values
                if (isEmpty()){
                    geneName = null;
                }
                else {

                    Alias firstAlias = first();

                    // first alias is gene name
                    if (AliasUtils.doesAliasHaveType(firstAlias, Alias.GENE_NAME_ID, Alias.GENE_NAME)){
                        geneName = firstAlias;
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            super.clear();
            geneName = null;
        }

        public void removeAllGeneNames(){

            Alias first = first();
            while (AliasUtils.doesAliasHaveType(first, Alias.GENE_NAME_ID, Alias.GENE_NAME)){
                remove(first);
                first = first();
            }
        }
    }
}
