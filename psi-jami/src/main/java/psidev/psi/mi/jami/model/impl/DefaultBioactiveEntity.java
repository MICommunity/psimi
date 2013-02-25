package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactBioactiveEntityComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Collection;

/**
 * Default implementation for bioactive entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultBioactiveEntity extends DefaultInteractor implements BioactiveEntity {

    private Xref chebi;
    private Checksum smile;
    private Checksum standardInchi;
    private Checksum standardInchiKey;

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

    public DefaultBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);

    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name) {
        super(name, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI));
    }

    public DefaultBioactiveEntity(String name, String fullName) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI));
    }

    public DefaultBioactiveEntity(String name, Organism organism) {
        super(name, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism);
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism);
    }

    public DefaultBioactiveEntity(String name, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), uniqueId);
    }

    public DefaultBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism, uniqueId);

    }

    public DefaultBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI));
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, CvTermFactory.createMICvTerm(BIOACTIVE_ENTITY, BIOACTIVE_ENTITY_MI), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    @Override
    protected void initialiseIdentifiers() {
        super.initialiseIdentifiersWith(new BioctiveEntityIdentifierList());
    }

    @Override
    protected void initialiseChecksums() {
        super.initialiseChecksumsWith(new BioctiveEntityChecksumList());
    }

    public String getChebi() {
        return chebi != null ? chebi.getId() : null;
    }

    public void setChebi(String id) {
        Collection<Xref> bioactiveEntityIdentifiers = getIdentifiers();

        // add new chebi if not null
        if (id != null){
            CvTerm chebiDatabase = CvTermFactory.createChebiDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old chebi if not null
            if (this.chebi != null){
                bioactiveEntityIdentifiers.remove(this.chebi);
            }
            this.chebi = new DefaultXref(chebiDatabase, id, identityQualifier);
            bioactiveEntityIdentifiers.add(this.chebi);
        }
        // remove all chebi if the collection is not empty
        else if (!bioactiveEntityIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(bioactiveEntityIdentifiers, Xref.CHEBI_MI, Xref.CHEBI);
            this.chebi = null;
        }
    }

    public String getSmile() {
        return smile != null ? smile.getValue() : null;
    }

    public void setSmile(String smile) {
        Collection<Checksum> bioactiveEntityChecksums = getChecksums();

        if (smile != null){
            CvTerm smileMethod = CvTermFactory.createSmile();
            // first remove old smile
            if (this.smile != null){
                bioactiveEntityChecksums.remove(this.smile);
            }
            this.smile = new DefaultChecksum(smileMethod, smile);
            bioactiveEntityChecksums.add(this.smile);
        }
        // remove all smiles if the collection is not empty
        else if (!bioactiveEntityChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(bioactiveEntityChecksums, Checksum.SMILE_MI, Checksum.SMILE);
            this.smile = null;
        }
    }

    public String getStandardInchiKey() {
        return standardInchiKey != null ? standardInchiKey.getValue() : null;
    }

    public void setStandardInchiKey(String key) {
        Collection<Checksum> bioactiveEntityChecksums = getChecksums();

        if (standardInchiKey != null){
            CvTerm inchiKeyMethod = CvTermFactory.createStandardInchiKey();
            // first remove old standard inchi key
            if (this.standardInchiKey != null){
                bioactiveEntityChecksums.remove(this.standardInchiKey);
            }
            this.standardInchiKey = new DefaultChecksum(inchiKeyMethod, key);
            bioactiveEntityChecksums.add(this.standardInchiKey);
        }
        // remove all standard inchi keys if the collection is not empty
        else if (!bioactiveEntityChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(bioactiveEntityChecksums, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY);
            this.standardInchiKey = null;
        }
    }

    public String getStandardInchi() {
        return standardInchi != null ? standardInchi.getValue() : null;
    }

    public void setStandardInchi(String inchi) {
        Collection<Checksum> bioactiveEntityChecksums = getChecksums();

        if (standardInchi != null){
            CvTerm inchiMethod = CvTermFactory.createStandardInchi();
            // first remove standard inchi
            if (this.standardInchi != null){
                bioactiveEntityChecksums.remove(this.standardInchi);
            }
            this.standardInchi = new DefaultChecksum(inchiMethod, inchi);
            bioactiveEntityChecksums.add(this.standardInchi);
        }
        // remove all standard inchi if the collection is not empty
        else if (!bioactiveEntityChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(bioactiveEntityChecksums, Checksum.INCHI_MI, Checksum.INCHI);
            this.standardInchi = null;
        }
    }

    protected void processAddedChecksumEvent(Checksum added) {
        // the added checksum is standard inchi key and it is not the current standard inchi key
        if (standardInchiKey == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY)){
            // the standard inchi key is not set, we can set the standard inchi key
            standardInchiKey = added;
        }
        else if (smile == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.SMILE_MI, Checksum.SMILE)){
            // the smile is not set, we can set the smile
            smile = added;
        }
        else if (standardInchi == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.INCHI_MI, Checksum.INCHI)){
            // the standard inchi is not set, we can set the standard inchi
            standardInchi = added;
        }
    }

    protected void processRemovedChecksumEvent(Checksum removed) {
        // the removed identifier is standard inchi key
        if (standardInchiKey != null && standardInchiKey.equals(removed)){
            standardInchiKey = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY);
        }
        else if (smile != null && smile.equals(removed)){
            smile = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.SMILE_MI, Checksum.SMILE);
        }
        else if (standardInchi != null && standardInchi.equals(removed)){
            standardInchi = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.INCHI_MI, Checksum.INCHI);
        }
    }

    protected void clearPropertiesLinkedToChecksums() {
        standardInchiKey = null;
        standardInchi = null;
        smile = null;
    }

    protected void processAddedIdentifierEvent(Xref added) {
        // the added identifier is chebi and it is not the current chebi identifier
        if (chebi != added && XrefUtils.isXrefFromDatabase(added, Xref.CHEBI_MI, Xref.CHEBI)){
            // the current chebi identifier is not identity, we may want to set chebiIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(chebi, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the chebi identifier is not set, we can set the chebi identifier
                if (chebi == null){
                    chebi = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    chebi = added;
                }
                // the added xref is secondary object and the current chebi is not a secondary object, we reset chebi identifier
                else if (!XrefUtils.doesXrefHaveQualifier(chebi, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    chebi = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is chebi
        if (chebi != null && chebi.equals(removed)){
            chebi = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.CHEBI_MI, Xref.CHEBI);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        chebi = null;
    }

    @Override
    public String toString() {
        return chebi != null ? chebi.getId() : (standardInchiKey != null ? standardInchiKey.getValue() : (smile != null ? smile.getValue() : (standardInchi != null ? standardInchi.getValue() : super.toString())));
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

    private class BioctiveEntityIdentifierList extends AbstractListHavingPoperties<Xref> {
        public BioctiveEntityIdentifierList(){
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

    private class BioctiveEntityChecksumList extends AbstractListHavingPoperties<Checksum>{
        public BioctiveEntityChecksumList(){
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
}
