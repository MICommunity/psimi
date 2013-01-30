package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactBioactiveEntityComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.*;

/**
 * Default implementation for bioactive entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultBioactiveEntity extends DefaultInteractor implements BioactiveEntity {

    private String chebi;
    private String smile;
    private String standardInchi;
    private String standardInchiKey;

    public DefaultBioactiveEntity(String name, CvTerm type) {
        super(name, type);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    @Override
    protected void initializeAlternativeIdentifiers() {
        this.identifiers = new BioctiveEntityIdentifierList();
    }

    @Override
    protected void initializeChecksums() {
        this.checksums = new BioctiveEntityChecksumList();
    }

    public String getChebi() {
        return chebi;
    }

    public void setChebi(String id) {
        CvTerm chebiDatabase = CvTermFactory.createChebiDatabase();
        ExternalIdentifier oldChebi = new DefaultExternalIdentifier(chebiDatabase, chebi);
        // first remove old chebi
        identifiers.remove(oldChebi);
        this.chebi = id;
        this.identifiers.add(new DefaultExternalIdentifier(chebiDatabase, id));
    }

    public String getSmile() {
        return smile;
    }

    public void setSmile(String smile) {
        CvTerm smileMethod = CvTermFactory.createSmile();
        Checksum oldSmile = new DefaultChecksum(smileMethod, this.smile);
        // first remove old smile
        this.checksums.remove(oldSmile);
        this.smile = smile;
        this.checksums.add(new DefaultChecksum(smileMethod, smile));
    }

    public String getStandardInchiKey() {
        return standardInchiKey;
    }

    public void setStandardInchiKey(String key) {
        CvTerm inchiKeyMethod = CvTermFactory.createStandardInchiKey();
        Checksum oldInchiKey = new DefaultChecksum(inchiKeyMethod, this.standardInchiKey);
        // first remove old inchi key
        this.checksums.remove(oldInchiKey);
        this.standardInchiKey = key;
        this.checksums.add(new DefaultChecksum(inchiKeyMethod, key));
    }

    public String getStandardInchi() {
        return standardInchi;
    }

    public void setStandardInchi(String inchi) {
        CvTerm inchiMethod = CvTermFactory.createStandardInchi();
        Checksum oldInchi = new DefaultChecksum(inchiMethod, this.standardInchi);
        // first remove old inchi
        this.checksums.remove(oldInchi);
        this.standardInchi = inchi;
        this.checksums.add(new DefaultChecksum(inchiMethod, inchi));
    }

    @Override
    public String toString() {
        return chebi != null ? chebi : (standardInchi != null ? standardInchi : (standardInchiKey != null ? standardInchiKey : super.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof BioactiveEntity)){
            return false;
        }

        // use UnambiguousExactBioactiveEntity comparator for equals
        return UnambiguousExactBioactiveEntityComparator.areEquals(this, (BioactiveEntity) o);
    }

    /**
     * Comparator which sorts external identifiers so chebi identifiers are always first
     */
    private class BioactiveEntityIdentifierComparator implements Comparator<ExternalIdentifier>{

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
                // compares databases first : chebi is before
                CvTerm database1 = externalIdentifier1.getDatabase();
                CvTerm database2 = externalIdentifier2.getDatabase();
                ExternalIdentifier databaseId1 = database1.getOntologyIdentifier();
                ExternalIdentifier databaseId2 = database2.getOntologyIdentifier();

                // if external id of database is set, look at database id only otherwise look at shortname
                int comp;
                if (databaseId1 != null && databaseId2 != null){
                    // both are chebi, sort by id
                    if (Xref.CHEBI_ID.equals(databaseId1.getId()) && Xref.CHEBI_ID.equals(databaseId2.getId())){
                        return compareChebiIdentifiers(externalIdentifier1, externalIdentifier2);
                    }
                    // CHEBI is first
                    else if (Xref.CHEBI_ID.equals(databaseId1.getId())){
                        return BEFORE;
                    }
                    else if (Xref.CHEBI_ID.equals(databaseId2.getId())){
                        return AFTER;
                    }
                    // both databases are not chebi
                    else {
                        comp = databaseId1.getId().compareTo(databaseId2.getId());
                    }
                }
                else {
                    String databaseName1 = database1.getShortName().toLowerCase().trim();
                    String databaseName2 = database2.getShortName().toLowerCase().trim();
                    // both are chebi, sort by id
                    if (Xref.CHEBI.equals(databaseName1) && Xref.CHEBI.equals(databaseName2)){
                        return compareChebiIdentifiers(externalIdentifier1, externalIdentifier2);
                    }
                    // CHEBI is first
                    else if (Xref.CHEBI.equals(databaseName1)){
                        return BEFORE;
                    }
                    else if (Xref.CHEBI.equals(databaseName2)){
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

        private int compareChebiIdentifiers(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
            int comp;
            String id1 = externalIdentifier1.getId();
            String id2 = externalIdentifier2.getId();
            comp = id1.compareTo(id2);
            if (comp == 0){
                return 0;
            }

            // the unique chebi is first
            if (chebi != null && chebi.equals(id1)){
                return -1;
            }
            else if (chebi != null && chebi.equals(id2)){
                return 1;
            }
            else {
                return comp;
            }
        }
    }

    private class BioctiveEntityIdentifierList extends TreeSet<ExternalIdentifier>{
        public BioctiveEntityIdentifierList(){
            super(new BioactiveEntityIdentifierComparator());
        }

        @Override
        public boolean add(ExternalIdentifier externalIdentifier) {
            boolean added = super.add(externalIdentifier);

            // set chebi if not done
            if (chebi == null && added){
                ExternalIdentifier firstChebi = first();

                if (XrefUtils.isXrefFromDatabase(firstChebi, Xref.CHEBI_ID, Xref.CHEBI)){
                    chebi = firstChebi.getId();
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // check chebi
                ExternalIdentifier firstChebi = first();

                // first identifier is from chebi
                if (XrefUtils.isXrefFromDatabase(firstChebi, Xref.CHEBI_ID, Xref.CHEBI)){
                    chebi = firstChebi.getId();
                }
                // the first element is not chebi, the old chebi needs to be reset
                else if (chebi != null){
                    chebi = null;
                }
                return true;
            }

            return false;
        }

        @Override
        public void clear() {
            super.clear();
            chebi = null;
        }
    }

    /**
     * Comparator which sorts checksums so standard inchi keys are always first, then smile then standard inchi
     */
    private class BioactiveEntityChecksumComparator implements Comparator<Checksum>{

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
                ExternalIdentifier methodId1 = method1.getOntologyIdentifier();
                ExternalIdentifier methodId2 = method2.getOntologyIdentifier();

                // if external id of method is set, look at method id only otherwise look at shortname
                int comp;
                if (methodId1 != null && methodId2 != null){
                    // both are standard inchi keys, sort by id
                    if (Checksum.INCHI_KEY_ID.equals(methodId1.getId()) && Checksum.INCHI_KEY_ID.equals(methodId2.getId())){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, standardInchiKey);
                    }
                    // standard inchi key is first
                    else if (Checksum.INCHI_KEY_ID.equals(methodId1.getId())){
                        return BEFORE;
                    }
                    else if (Checksum.INCHI_KEY_ID.equals(methodId2.getId())){
                        return AFTER;
                    }
                    else if (Checksum.SMILE_ID.equals(methodId1.getId()) && Checksum.SMILE_ID.equals(methodId2.getId())){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, smile);
                    }
                    // smile is second
                    else if (Checksum.SMILE_ID.equals(methodId1.getId())){
                        return BEFORE;
                    }
                    else if (Checksum.SMILE_ID.equals(methodId2.getId())){
                        return AFTER;
                    }
                    else if (Checksum.INCHI_ID.equals(methodId1.getId()) && Checksum.INCHI_ID.equals(methodId2.getId())){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, standardInchi);
                    }
                    // standard inchi is third
                    else if (Checksum.INCHI_ID.equals(methodId1.getId())){
                        return BEFORE;
                    }
                    else if (Checksum.INCHI_ID.equals(methodId2.getId())){
                        return AFTER;
                    }
                    // both databases are not standard checksums
                    else {
                        comp = methodId1.getId().compareTo(methodId2.getId());
                    }
                }
                else {
                    String methodName1 = method1.getShortName().toLowerCase().trim();
                    String methodName2 = method2.getShortName().toLowerCase().trim();
                    // both are chebi, sort by id
                    if (Checksum.INCHI_KEY.equals(methodName1) && Checksum.INCHI_KEY.equals(methodName2)){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, standardInchiKey);
                    }
                    // standard inchi key is first
                    else if (Checksum.INCHI_KEY.equals(methodName1)){
                        return BEFORE;
                    }
                    else if (Checksum.INCHI_KEY.equals(methodName2)){
                        return AFTER;
                    }
                    else if (Checksum.SMILE.equals(methodName1) && Checksum.SMILE.equals(methodName2)){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, smile);
                    }
                    // smile is second
                    else if (Checksum.SMILE.equals(methodName1)){
                        return BEFORE;
                    }
                    else if (Checksum.SMILE.equals(methodName2)){
                        return AFTER;
                    }
                    else if (Checksum.INCHI.equals(methodName1) && Checksum.INCHI.equals(methodName2)){
                        return compareChecksumValuesWithBioactiveEntityProperty(checksum1, checksum2, standardInchi);
                    }
                    // smile is second
                    else if (Checksum.INCHI.equals(methodName1)){
                        return BEFORE;
                    }
                    else if (Checksum.INCHI.equals(methodName2)){
                        return AFTER;
                    }
                    // both databases are not standard checksum
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

        private int compareChecksumValuesWithBioactiveEntityProperty(Checksum checksum1, Checksum checksum2, String property) {
            int comp;
            String check1 = checksum1.getValue();
            String check2 = checksum2.getValue();
            comp = check1.compareTo(check2);
            if (comp == 0){
                return 0;
            }

            // the unique chebi is first
            if (property != null && property.equals(check1)){
                return -1;
            }
            else if (property != null && property.equals(check2)){
                return 1;
            }
            else {
                return comp;
            }
        }
    }

    private class BioctiveEntityChecksumList extends TreeSet<Checksum>{
        public BioctiveEntityChecksumList(){
            super(new BioactiveEntityChecksumComparator());
        }

        @Override
        public boolean add(Checksum checksum) {
            boolean added = super.add(checksum);

            // set standard inchi key, smile or standard inchi if not done
            if (added && (standardInchiKey == null || smile == null || standardInchi == null)){
                Iterator<Checksum> checksumIterator = iterator();
                Checksum firstChecksum = checksumIterator.next();

                // starts with standard inchi key
                if (standardInchiKey == null){
                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_KEY_ID, Checksum.INCHI_KEY)){
                        standardInchiKey = firstChecksum.getValue();
                        if (checksumIterator.hasNext()){
                            firstChecksum = checksumIterator.next();
                        }
                        else {
                            firstChecksum = null;
                        }
                    }
                }

                // process smile
                if (smile == null){
                    // go through all inchi keys before finding smiles
                    while (checksumIterator.hasNext() &&
                            ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_KEY_ID, Checksum.INCHI_KEY)){
                        firstChecksum = checksumIterator.next();
                    }

                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.SMILE_ID, Checksum.SMILE)){
                        smile = firstChecksum.getValue();
                        if (checksumIterator.hasNext()){
                            firstChecksum = checksumIterator.next();
                        }
                        else {
                            firstChecksum = null;
                        }
                    }
                }

                // process standard inchi
                if (standardInchi == null){
                    // go through all smiles before finding standard inchi
                    while (checksumIterator.hasNext() &&
                            ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.SMILE_ID, Checksum.SMILE)){
                        firstChecksum = checksumIterator.next();
                    }

                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_ID, Checksum.INCHI)){
                        standardInchi = firstChecksum.getValue();
                    }
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // we have nothing left in checksums, reset standard values
                if (isEmpty()){
                    standardInchi = null;
                    standardInchiKey = null;
                    smile = null;
                }
                else {

                    Iterator<Checksum> checksumIterator = iterator();
                    Checksum firstChecksum = checksumIterator.next();

                    // first checksum is standard inchi key
                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_KEY_ID, Checksum.INCHI_KEY)){
                        standardInchiKey = firstChecksum.getValue();
                    }
                    // process smile
                    // go through all inchi keys before finding smiles
                    while (checksumIterator.hasNext() &&
                            ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_KEY_ID, Checksum.INCHI_KEY)){
                        firstChecksum = checksumIterator.next();
                    }

                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.SMILE_ID, Checksum.SMILE)){
                        smile = firstChecksum.getValue();
                    }

                    // process standard inchi
                    // go through all inchi keys before finding smiles
                    while (checksumIterator.hasNext() &&
                            ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.SMILE_ID, Checksum.SMILE)){
                        firstChecksum = checksumIterator.next();
                    }

                    if (ChecksumUtils.isChecksumHavingMethod(firstChecksum, Checksum.INCHI_ID, Checksum.INCHI)){
                        standardInchi = firstChecksum.getValue();
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            super.clear();
            standardInchiKey = null;
            standardInchi = null;
            smile = null;
        }
    }
}
