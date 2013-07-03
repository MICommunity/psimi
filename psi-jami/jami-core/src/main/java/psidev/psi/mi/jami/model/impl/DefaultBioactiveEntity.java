package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.Collection;

/**
 * Default implementation for bioactive entity
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the BioactiveEntity object is a complex object.
 * To compare BioactiveEntity objects, you can use some comparators provided by default:
 * - DefaultBioactiveEntityComparator
 * - UnambiguousBioactiveEntityComparator
 * - DefaultExactBioactiveEntityComparator
 * - UnambiguousExactBioactiveEntityComparator
 * - BioactiveEntityComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultBioactiveEntity extends DefaultMolecule implements BioactiveEntity {

    private Xref chebi;
    private Checksum smile;
    private Checksum standardInchi;
    private Checksum standardInchiKey;

    public DefaultBioactiveEntity(String name, CvTerm type) {
        super(name, type != null ? type : CvTermUtils.createBioactiveEntityType());
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType());
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism, uniqueId);

    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType());
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type != null ? type : CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name) {
        super(name, CvTermUtils.createBioactiveEntityType());
    }

    public DefaultBioactiveEntity(String name, String fullName) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
    }

    public DefaultBioactiveEntity(String name, Organism organism) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public DefaultBioactiveEntity(String name, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public DefaultBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);

    }

    public DefaultBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public DefaultBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
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
            CvTerm chebiDatabase = CvTermUtils.createChebiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
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
            CvTerm smileMethod = CvTermUtils.createSmile();
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

        if (key != null){
            CvTerm inchiKeyMethod = CvTermUtils.createStandardInchiKey();
            // first remove old standard inchi key
            if (this.standardInchiKey != null){
                bioactiveEntityChecksums.remove(this.standardInchiKey);
            }
            this.standardInchiKey = new DefaultChecksum(inchiKeyMethod, key);
            bioactiveEntityChecksums.add(this.standardInchiKey);
        }
        // remove all standard inchi keys if the collection is not empty
        else if (!bioactiveEntityChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(bioactiveEntityChecksums, Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY);
            this.standardInchiKey = null;
        }
    }

    public String getStandardInchi() {
        return standardInchi != null ? standardInchi.getValue() : null;
    }

    public void setStandardInchi(String inchi) {
        Collection<Checksum> bioactiveEntityChecksums = getChecksums();

        if (inchi != null){
            CvTerm inchiMethod = CvTermUtils.createStandardInchi();
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
        if (standardInchiKey == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY)){
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
            standardInchiKey = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY);
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
    /**
     * Sets the interactor type of this bioactive entity.
     * If the given interactorType is null, it sets the interactorType to 'bioactive entity'(MI:1100)
     */
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            super.setInteractorType(CvTermUtils.createBioactiveEntityType());
        }
        else {
            super.setInteractorType(interactorType);
        }
    }

    @Override
    public String toString() {
        return chebi != null ? chebi.getId() : (standardInchiKey != null ? standardInchiKey.getValue() : (smile != null ? smile.getValue() : (standardInchi != null ? standardInchi.getValue() : super.toString())));
    }

    private class BioctiveEntityIdentifierList extends AbstractListHavingProperties<Xref> {
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

    private class BioctiveEntityChecksumList extends AbstractListHavingProperties<Checksum> {
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
