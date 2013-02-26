package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Collection;
import java.util.List;

/**
 * Default MITAB interactor implementation for bioactive entities which is a patch for backward compatibility.
 * It only contains molecule information (not any participant information such as experimental role, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class MitabBioactiveEntity extends MitabInteractor implements BioactiveEntity{

    private Xref chebi;
    private Checksum smile;
    private Checksum standardInchi;
    private Checksum standardInchiKey;

    public MitabBioactiveEntity() {
        super(CvTermFactory.createMICvTerm(BioactiveEntity.BIOACTIVE_ENTITY, BioactiveEntity.BIOACTIVE_ENTITY_MI));
    }

    public MitabBioactiveEntity(List<CrossReference> identifiers) {
        super(identifiers, CvTermFactory.createMICvTerm(BioactiveEntity.BIOACTIVE_ENTITY, BioactiveEntity.BIOACTIVE_ENTITY_MI));
    }

    public MitabBioactiveEntity(CvTerm type) {
        super(type);
    }

    public MitabBioactiveEntity(List<CrossReference> identifiers, CvTerm type) {
        super(identifiers, type);
    }

    public String getChebi() {
        return chebi != null ? chebi.getId() : null;
    }

    public void setChebi(String id) {
        Collection<Xref> identifiers = getIdentifiers();

        // add new chebi if not null
        if (id != null){
            // first remove old chebi if not null
            if (this.chebi != null){
                identifiers.remove(this.chebi);
            }
            this.chebi = new CrossReferenceImpl(Xref.CHEBI, id);
            identifiers.add(chebi);
        }
        // remove all chebi if the collection is not empty
        else if (!identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.CHEBI_MI, Xref.CHEBI);
            this.chebi = null;
        }
    }

    public String getSmile() {
        return smile != null ? smile.getValue() : null;
    }

    public void setSmile(String smile) {
        Collection<Checksum> checksum = getChecksums();
        if (smile != null){
            // first remove old smile
            if (this.smile != null){
                checksum.remove(this.smile);
            }
            this.smile = new ChecksumImpl(Checksum.SMILE, smile);
            checksum.add(this.smile);
        }
        // remove all smiles if the collection is not empty
        else if (!checksum.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksum, Checksum.SMILE_MI, Checksum.SMILE);
            this.smile = null;
        }
    }

    public String getStandardInchiKey() {
        return standardInchiKey != null ? this.standardInchiKey.getValue() : null;
    }

    public void setStandardInchiKey(String key) {
        Collection<Checksum> checksum = getChecksums();
        if (standardInchiKey != null){
            // first remove old standard inchi key
            if (this.standardInchiKey != null){
                checksum.remove(this.standardInchiKey);
            }
            this.standardInchiKey = new ChecksumImpl(Checksum.INCHI_KEY, key);
            checksum.add(this.standardInchiKey);
        }
        // remove all standard inchi keys if the collection is not empty
        else if (!checksum.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksum, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY);
            this.standardInchiKey = null;
        }
    }

    public String getStandardInchi() {
        return standardInchi != null ? standardInchi.getValue() : null;
    }

    public void setStandardInchi(String inchi) {
        Collection<Checksum> checksum = getChecksums();
        if (standardInchi != null){
            // first remove standard inchi
            if (this.standardInchi != null){
                checksum.remove(this.standardInchi);
            }
            this.standardInchi = new ChecksumImpl(Checksum.INCHI, inchi);
            checksum.add(this.standardInchi);
        }
        // remove all standard inchi if the collection is not empty
        else if (!checksum.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksum, Checksum.INCHI_MI, Checksum.INCHI);
            this.standardInchi = null;
        }
    }

    @Override
    protected void processAddedIdentifierEvent(Xref added) {
        if (chebi == null && XrefUtils.isXrefFromDatabase(added, Xref.CHEBI_MI, Xref.CHEBI)){
            chebi = added;
        }
    }
    @Override
    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is chebi
        if (chebi != null && XrefUtils.isXrefFromDatabase(removed, Xref.CHEBI_MI, Xref.CHEBI) && removed.getId().equals(chebi.getId())){
            chebi = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.CHEBI_MI, Xref.CHEBI);
        }
    }

    @Override
    protected void clearPropertiesLinkedToIdentifiers() {
        chebi = null;
    }

    @Override
    protected void processAddedChecksumEvent(psidev.psi.mi.jami.model.Checksum added) {
        // the added checksum is standard inchi key and it is not the current standard inchi key
        if (standardInchiKey == null && ChecksumUtils.doesChecksumHaveMethod(added, psidev.psi.mi.jami.model.Checksum.INCHI_KEY_MI, psidev.psi.mi.jami.model.Checksum.INCHI_KEY)){
            // the standard inchi key is not set, we can set the standard inchi key
            standardInchiKey = added;
        }
        else if (smile == null && ChecksumUtils.doesChecksumHaveMethod(added, psidev.psi.mi.jami.model.Checksum.SMILE_MI, psidev.psi.mi.jami.model.Checksum.SMILE)){
            // the smile is not set, we can set the smile
            smile = added;
        }
        else if (standardInchi == null && ChecksumUtils.doesChecksumHaveMethod(added, psidev.psi.mi.jami.model.Checksum.INCHI_MI, psidev.psi.mi.jami.model.Checksum.INCHI)){
            // the standard inchi is not set, we can set the standard inchi
            standardInchi = added;
        }
    }

    @Override
    protected void processRemovedChecksumEvent(psidev.psi.mi.jami.model.Checksum removed) {
        // the removed identifier is standard inchi key
        if (standardInchiKey != null && standardInchiKey.getValue().equals(removed.getValue())
                && ChecksumUtils.doesChecksumHaveMethod(removed, psidev.psi.mi.jami.model.Checksum.INCHI_KEY_MI, psidev.psi.mi.jami.model.Checksum.INCHI_KEY)){
            standardInchiKey = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), psidev.psi.mi.jami.model.Checksum.INCHI_KEY_MI, psidev.psi.mi.jami.model.Checksum.INCHI_KEY);
        }
        else if (smile != null && smile.getValue().equals(removed.getValue())
                && ChecksumUtils.doesChecksumHaveMethod(removed, Checksum.SMILE_MI, Checksum.SMILE)){
            smile = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.SMILE_MI, Checksum.SMILE);
        }
        else if (standardInchi != null && standardInchi.getValue().equals(removed.getValue())
                && ChecksumUtils.doesChecksumHaveMethod(removed, Checksum.INCHI_MI, Checksum.INCHI)){
            standardInchi = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.INCHI_MI, Checksum.INCHI);
        }
    }

    @Override
    protected void clearPropertiesLinkedToChecksum() {
        standardInchiKey = null;
        standardInchi = null;
        smile = null;
    }
}
