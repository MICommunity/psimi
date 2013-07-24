package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * Xml implementation of BioactiveEntity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */

public class XmlBioactiveEntity extends XmlInteractor implements BioactiveEntity{

    private Checksum smile;
    private Checksum standardInchi;
    private Checksum standardInchiKey;

    public XmlBioactiveEntity() {
    }

    public XmlBioactiveEntity(String name, CvTerm type) {
        super(name, type);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, String uniqueChebi) {
        super(name, fullName, type);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, CvTerm type, Organism organism, String uniqueChebi) {
        super(name, fullName, type, organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name) {
        super(name, CvTermUtils.createBioactiveEntityType());
    }

    public XmlBioactiveEntity(String name, String fullName) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
    }

    public XmlBioactiveEntity(String name, Organism organism) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
    }

    public XmlBioactiveEntity(String name, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), uniqueId);
    }

    public XmlBioactiveEntity(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism, uniqueId);

    }

    public XmlBioactiveEntity(String name, String fullName, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType());
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, Organism organism, String uniqueChebi) {
        super(name, CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    public XmlBioactiveEntity(String name, String fullName, Organism organism, String uniqueChebi) {
        super(name, fullName, CvTermUtils.createBioactiveEntityType(), organism);
        if (uniqueChebi != null){
            setChebi(uniqueChebi);
        }
    }

    @Override
    public void setXref(InteractorXrefContainer value) {
        if (value == null){
            this.xrefContainer = null;
        }
        else if (this.xrefContainer == null){
            this.xrefContainer = new BioactiveEntityXrefContainer();
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
        else {
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().clear();
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
    }

    @Override
    public void initialiseXrefContainer() {
        this.xrefContainer = new BioactiveEntityXrefContainer();
    }

    @XmlTransient
    public String getChebi() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((BioactiveEntityXrefContainer)xrefContainer).getChebi();
    }

    public void setChebi(String id) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((BioactiveEntityXrefContainer)xrefContainer).setChebi(id);
    }

    @XmlTransient
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

    @XmlTransient
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

    @XmlTransient
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

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(BioactiveEntity.BIOACTIVE_ENTITY, BioactiveEntity.BIOACTIVE_ENTITY_MI));
    }

    @Override
    protected void initialiseChecksums() {
        super.initialiseChecksumsWith(new ChecksumList());
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

    private class ChecksumList extends AbstractListHavingProperties<Checksum> {
        public ChecksumList(){
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
