package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactBioactiveEntityComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

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
    }

    public DefaultBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type, organism);
    }

    public DefaultBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type, organism);
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new BioctiveEntityIdentifierList();
    }

    @Override
    protected void initializeChecksums() {
        this.checksums = new BioctiveEntityChecksumList();
    }

    public String getChebi() {
        return chebi != null ? chebi.getId() : null;
    }

    public void setChebi(String id) {
        // add new chebi if not null
        if (id != null){
            CvTerm chebiDatabase = CvTermFactory.createChebiDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old chebi if not null
            if (this.chebi != null){
                identifiers.remove(this.chebi);
            }
            this.chebi = new DefaultXref(chebiDatabase, id, identityQualifier);
            this.identifiers.add(this.chebi);
        }
        // remove all chebi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(xrefs, Xref.CHEBI_MI, Xref.CHEBI);
            this.chebi = null;
        }
    }

    public String getSmile() {
        return smile != null ? smile.getValue() : null;
    }

    public void setSmile(String smile) {
        if (smile != null){
            CvTerm smileMethod = CvTermFactory.createSmile();
            // first remove old smile
            if (this.smile != null){
                this.checksums.remove(this.smile);
            }
            this.smile = new DefaultChecksum(smileMethod, smile);
            this.checksums.add(this.smile);
        }
        // remove all smiles if the collection is not empty
        else if (!this.checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(this.checksums, Checksum.SMILE_MI, Checksum.SMILE);
            this.smile = null;
        }
    }

    public String getStandardInchiKey() {
        return standardInchiKey != null ? standardInchiKey.getValue() : null;
    }

    public void setStandardInchiKey(String key) {
        if (standardInchiKey != null){
            CvTerm inchiKeyMethod = CvTermFactory.createStandardInchiKey();
            // first remove old standard inchi key
            if (this.standardInchiKey != null){
                this.checksums.remove(this.standardInchiKey);
            }
            this.standardInchiKey = new DefaultChecksum(inchiKeyMethod, key);
            this.checksums.add(this.standardInchiKey);
        }
        // remove all standard inchi keys if the collection is not empty
        else if (!this.checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(this.checksums, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY);
            this.standardInchiKey = null;
        }
    }

    public String getStandardInchi() {
        return standardInchi != null ? standardInchi.getValue() : null;
    }

    public void setStandardInchi(String inchi) {
        if (standardInchi != null){
            CvTerm inchiMethod = CvTermFactory.createStandardInchi();
            // first remove standard inchi
            if (this.standardInchi != null){
                this.checksums.remove(this.standardInchi);
            }
            this.standardInchi = new DefaultChecksum(inchiMethod, inchi);
            this.checksums.add(this.standardInchi);
        }
        // remove all standard inchi if the collection is not empty
        else if (!this.checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(this.checksums, Checksum.INCHI_MI, Checksum.INCHI);
            this.standardInchi = null;
        }
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

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is chebi
            if (chebi == removed){
                chebi = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.CHEBI_MI, Xref.CHEBI);
            }
        }

        @Override
        protected void clearProperties() {
            chebi = null;
        }
    }

    private class BioctiveEntityChecksumList extends AbstractListHavingPoperties<Checksum>{
        public BioctiveEntityChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {
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

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {
            // the removed identifier is standard inchi key
            if (standardInchiKey == removed){
                standardInchiKey = ChecksumUtils.collectFirstChecksumWithMethod(this, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY);
            }
            else if (smile == removed){
                smile = ChecksumUtils.collectFirstChecksumWithMethod(this, Checksum.SMILE_MI, Checksum.SMILE);
            }
            else if (standardInchi == removed){
                standardInchi = ChecksumUtils.collectFirstChecksumWithMethod(this, Checksum.INCHI_MI, Checksum.INCHI);
            }
        }

        @Override
        protected void clearProperties() {
            standardInchiKey = null;
            standardInchi = null;
            smile = null;
        }
    }
}
