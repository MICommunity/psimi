package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
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

    private String chebi;
    private int numberOfAlternativeIdentifiers = 0;
    private int numberOfChecksum = 0;
    private boolean isUniqueIdentifierFromChebi = false;
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

    public String getChebi() {
        if (this.alternativeIdentifiers.size() != numberOfAlternativeIdentifiers){
            if (!isUniqueIdentifierFromChebi){
                initializeChebiFromAlternativeIdentifiers();
            }
            numberOfAlternativeIdentifiers = this.alternativeIdentifiers.size();
        }
        return chebi;
    }

    public void setChebi(String id) {
        this.chebi = id;
        if (isUniqueIdentifierFromChebi){
            this.uniqueIdentifier = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), id);
        }
        else {
            Xref ref = XrefUtils.collectUniqueXrefFromDatabaseIfExists(this.alternativeIdentifiers, CvTerm.CHEBI_ID, CvTerm.CHEBI);
            if (ref != null){
                this.alternativeIdentifiers.remove(ref);
            }
            this.alternativeIdentifiers.add(new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), id));
        }
    }

    public String getSmile() {
        if (this.checksums.size() != numberOfChecksum){
            initializeSmileAndInchiFromChecksum();
            numberOfChecksum = this.checksums.size();
        }
        return smile;
    }

    public void setSmile(String smile) {
        Checksum checksum = ChecksumUtils.collectUniqueChecksumMethodIfExists(this.checksums, Checksum.SMILE_ID, Checksum.SMILE);
        if (checksum != null){
            this.checksums.remove(checksum);
        }
        this.checksums.add(new DefaultChecksum(CvTermFactory.createSmile(), smile));
    }

    public String getStandardInchiKey() {
        if (this.checksums.size() != numberOfChecksum){
            initializeSmileAndInchiFromChecksum();
            numberOfChecksum = this.checksums.size();
        }
        return standardInchiKey;
    }

    public void setStandardInchiKey(String key) {
        Checksum checksum = ChecksumUtils.collectUniqueChecksumMethodIfExists(this.checksums, Checksum.INCHI_KEY_ID, Checksum.INCHI_KEY);
        if (checksum != null){
            this.checksums.remove(checksum);
        }
        this.checksums.add(new DefaultChecksum(CvTermFactory.createStandardInchiKey(), key));
    }

    public String getStandardInchi() {
        if (this.checksums.size() != numberOfChecksum){
            initializeSmileAndInchiFromChecksum();
            numberOfChecksum = this.checksums.size();
        }
        return standardInchi;
    }

    public void setStandardInchi(String inchi) {
        Checksum checksum = ChecksumUtils.collectUniqueChecksumMethodIfExists(this.checksums, Checksum.INCHI_ID, Checksum.INCHI);
        if (checksum != null){
            this.checksums.remove(checksum);
        }
        this.checksums.add(new DefaultChecksum(CvTermFactory.createStandardInchi(), inchi));
    }

    @Override
    public void setUniqueIdentifier(ExternalIdentifier identifier) {
        this.uniqueIdentifier = identifier;
        // we can compare database identifiers of the unique identifier first
        if (uniqueIdentifier != null && uniqueIdentifier.getDatabase().getOntologyIdentifier() != null){
            // we have the chebi database id
            if (uniqueIdentifier.getDatabase().getOntologyIdentifier().getId().equals(CvTerm.CHEBI_ID)){
                this.chebi = uniqueIdentifier.getId();
                isUniqueIdentifierFromChebi = true;
            }
            else {
                isUniqueIdentifierFromChebi = false;
            }
        }
        else if (uniqueIdentifier != null && uniqueIdentifier.getDatabase().getShortName().equals(CvTerm.CHEBI)){
            this.chebi = uniqueIdentifier.getId();
            isUniqueIdentifierFromChebi = true;
        }
        else {
            isUniqueIdentifierFromChebi = false;
        }
    }

    private void initializeChebiFromAlternativeIdentifiers(){
        Xref ref = XrefUtils.collectUniqueXrefFromDatabaseIfExists(alternativeIdentifiers, CvTerm.CHEBI_ID, CvTerm.CHEBI);
        if (ref != null){
            this.chebi = ref.getId();
        }
    }

    private void initializeSmileAndInchiFromChecksum(){
        Checksum uniqueSmile = null;
        Checksum uniqueInchi = null;
        Checksum uniqueInchiKey = null;

        boolean hasFoundMultipleSmiles = false;
        boolean hasFoundMultipleInchi = false;
        boolean hasFoundMultipleInchiKeys = false;

        for (Checksum checksum : this.checksums){
            CvTerm method = checksum.getMethod();
            // we can compare identifiers
            if (method.getOntologyIdentifier() != null){
                // we have the same database id
                if (method.getOntologyIdentifier().getId().equals(Checksum.SMILE_ID)){
                    // it is a unique checksum
                    if (uniqueSmile == null){
                        uniqueSmile = checksum;
                    }
                    // we could not find a unique smile
                    else {
                        hasFoundMultipleSmiles = true;
                    }
                }
                else if (method.getOntologyIdentifier().getId().equals(Checksum.INCHI_ID)){
                    // it is a unique checksum
                    if (uniqueInchi == null){
                        uniqueInchi = checksum;
                    }
                    // we could not find a unique standard inchi
                    else {
                        hasFoundMultipleInchi = true;
                    }
                }
                else if (method.getOntologyIdentifier().getId().equals(Checksum.INCHI_KEY_ID)){
                    // it is a unique checksum
                    if (uniqueInchiKey == null){
                        uniqueInchiKey = checksum;
                    }
                    // we could not find a unique standard inchi key
                    else {
                        hasFoundMultipleInchiKeys = true;
                    }
                }
            }
            // we need to compare smiles
            else if (Checksum.SMILE.equals(method.getShortName())) {
                // it is a unique checksum
                if (uniqueSmile == null){
                    uniqueSmile = checksum;
                }
                // we could not find a unique checksum with this method so we return null
                else {
                    hasFoundMultipleSmiles = true;
                }
            }
            // we need to compare standard inchi
            else if (Checksum.INCHI.equals(method.getShortName())) {
                // it is a unique checksum
                if (uniqueInchi == null){
                    uniqueInchi = checksum;
                }
                // we could not find a unique checksum with this method so we return null
                else {
                    hasFoundMultipleInchi = true;
                }
            }
            // we need to compare smiles
            else if (Checksum.INCHI_KEY.equals(method.getShortName())) {
                // it is a unique checksum
                if (uniqueInchiKey == null){
                    uniqueInchiKey = checksum;
                }
                // we could not find a unique checksum with this method so we return null
                else {
                    hasFoundMultipleInchiKeys = true;
                }
            }
        }

        if (uniqueSmile != null && !hasFoundMultipleSmiles){
            this.smile = uniqueSmile.getValue();
        }
        if (uniqueInchi != null && !hasFoundMultipleInchi){
            this.standardInchi = uniqueInchi.getValue();
        }
        if (uniqueInchiKey != null && !hasFoundMultipleInchiKeys){
            this.standardInchiKey = uniqueInchiKey.getValue();
        }
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
}
