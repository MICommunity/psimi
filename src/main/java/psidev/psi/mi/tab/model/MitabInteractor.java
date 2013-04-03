package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Default MITAB interactor implementation which is a patch for backward compatibility.
 * It only contains molecule information (not any participant information such as experimental role, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class MitabInteractor extends DefaultInteractor implements Serializable, FileSourceContext{

    ///////////////////////
    // Instance variables

    private FileSourceLocator locator;

    /**
     * Primary uniqueIdentifiers of the interactor.
     */
    protected List<CrossReference> uniqueIdentifiers;

    /**
     * Alternative uniqueIdentifiers of the interactor.
     */
    protected List<CrossReference> alternativeIdentifiers;

    /**
     * Aliases of the interactor (ie. alternative names).
     */
    protected List<Alias> mitabAliases;

    /**
     * Organism the interactor belongs to.
     */
    protected Organism organism;

    /**
     * Cross references of the interactor.
     */
    protected List<CrossReference> mitabXrefs;

    /**
     * Annotations of the interactor.
     */
    protected List<Annotation> mitabAnnotations;

    /**
     * Checksums of the interactor.
     */
    protected List<Checksum> mitabChecksums;

    /**
     * Type of the interactor.
     */
    protected List<CrossReference> interactorTypes;

    public MitabInteractor() {
        super("unknown", CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
        ((InteractorTypeList)getInteractorTypes()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR));
    }

    public MitabInteractor(List<CrossReference> identifiers) {
        super("unknown", CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));

        if (identifiers == null) {
            throw new IllegalArgumentException("You must give a non null list of uniqueIdentifiers.");
        }
        getUniqueIdentifiers().addAll(identifiers);
        ((InteractorTypeList)getInteractorTypes()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR));
    }

    public MitabInteractor(CvTerm interactorType) {
        super("unknown", interactorType);

        processNewTypeInInteractorTypesList(interactorType);
    }

    public MitabInteractor(List<CrossReference> identifiers, CvTerm interactorType) {
        super("unknown", interactorType);

        if (identifiers == null) {
            throw new IllegalArgumentException("You must give a non null list of uniqueIdentifiers.");
        }
        getUniqueIdentifiers().addAll(identifiers);
        processNewTypeInInteractorTypesList(interactorType);
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new InteractorIdentifierList());
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new InteractorAliasList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new InteractorXrefList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new InteractorAnnotationList());
    }

    @Override
    protected void initialiseChecksums() {
        initialiseChecksumsWith(new InteractorChecksumList());
    }

    /**
     * Getter for property 'identifiers'.
     *
     * @return Value for property 'identifiers'.
     */
    public List<CrossReference> getUniqueIdentifiers() {
        if (uniqueIdentifiers == null){
            uniqueIdentifiers = new InteractorUniqueIdentifierList();
        }
        return uniqueIdentifiers;
    }

    /**
     * Setter for property 'identifiers'.
     *
     * @param identifiers Value to set for property 'identifiers'.
     */
    public void setUniqueIdentifiers(List<CrossReference> identifiers) {
        getUniqueIdentifiers().clear();
        if (identifiers != null) {
            this.uniqueIdentifiers.addAll(identifiers);
        }
    }

    public FileSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(FileSourceLocator locator) {
        this.locator = locator;
    }

    /**
     * Getter for property 'alternativeIdentifiers'.
     *
     * @return Value for property 'alternativeIdentifiers'.
     */
    public List<CrossReference> getAlternativeIdentifiers() {
        if (alternativeIdentifiers == null){
            alternativeIdentifiers = new InteractorAlternativeIdentifierList();
        }
        return alternativeIdentifiers;
    }

    /**
     * Setter for property 'alternativeIdentifiers'.
     *
     * @param alternativeIdentifiers Value to set for property 'alternativeIdentifiers'.
     */
    public void setAlternativeIdentifiers(List<CrossReference> alternativeIdentifiers) {
        getAlternativeIdentifiers().clear();
        if (alternativeIdentifiers != null) {
            this.alternativeIdentifiers.addAll(alternativeIdentifiers);
        }
    }

    /**
     * Getter for property 'aliases'.
     *
     * @return Value for property 'aliases'.
     */
    public List<Alias> getMitabAliases() {
        if (mitabAliases == null){
           mitabAliases = new InteractorMitabAliasList();
        }
        return mitabAliases;
    }

    /**
     * Setter for property 'aliases'.
     *
     * @param aliases Value to set for property 'aliases'.
     */
    public void setMitabAliases(List<Alias> aliases) {
        getMitabAliases().clear();
        if (aliases != null) {
            this.mitabAliases.addAll(aliases);
        }
    }

    /**
     * Getter fot property 'xrefs'.
     *
     * @return Value for property 'xrefs'.
     */
    public List<CrossReference> getMitabXrefs() {
        if (mitabXrefs == null){
            mitabXrefs = new InteractorMitabXrefList();
        }
        return mitabXrefs;
    }

    /**
     * Setter for property 'xrefs'.
     *
     * @param xrefs Value to set for property 'xrefs'.
     */
    public void setMitabXrefs(List<CrossReference> xrefs) {
        getMitabXrefs().clear();
        if (xrefs != null) {
            this.mitabXrefs.addAll(xrefs);
        }
    }

    /**
     * Getter fot property 'annotations'.
     *
     * @return Value for property 'annotations'.
     */
    public List<Annotation> getMitabAnnotations() {
        if (mitabAnnotations == null){
            mitabAnnotations = new InteractorMitabAnnotationList();
        }
        return mitabAnnotations;
    }

    /**
     * Setter for property 'annotations'.
     *
     * @param annotations Value to set for property 'annotations'.
     */
    public void setMitabAnnotations(List<Annotation> annotations) {
        getMitabAnnotations().clear();
        if (annotations != null) {
            this.mitabAnnotations.addAll(annotations);
        }
    }

    /**
     * Getter fot property 'checksum'.
     *
     * @return Value for property 'checksums'.
     */
    public List<Checksum> getMitabChecksums() {
        if (mitabChecksums == null){
            mitabChecksums = new InteractorMitabChecksumList();
        }
        return mitabChecksums;
    }

    /**
     * Setter for property 'checksums'.
     *
     * @param checksums Value to set for property 'checksums'.
     */
    public void setMitabChecksums(List<Checksum> checksums) {
        getMitabChecksums().clear();
        if (checksums != null) {
            this.mitabChecksums.addAll(checksums);
        }
    }

    /**
     * Getter for property 'organism'.
     *
     * @return Value for property 'organisms'.
     */
    public Organism getOrganism() {
        return organism;
    }

    /**
     * Setter for property 'organism'.
     *
     * @param organism Value to set for property 'organisms'.
     */
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    /**
     * Checks if the interactor has a organism associated.
     *
     * @return true if has a organism
     */
    public boolean hasOrganism() {
        return organism != null && organism.getIdentifiers() != null && !organism.getIdentifiers().isEmpty();
    }

    /**
     * Getter fot property 'interactorTypes'.
     *
     * @return Value for property 'interactorTypes'.
     */
    public List<CrossReference> getInteractorTypes() {
        if (interactorTypes == null){
            interactorTypes = new InteractorTypeList();
        }
        return interactorTypes;
    }

    /**
     * Setter for property 'interactorTypes'.
     *
     * @param interactorTypes Value to set for property 'interactorTypes'.
     */
    public void setInteractorTypes(List<CrossReference> interactorTypes) {
        if (interactorTypes != null && !interactorTypes.isEmpty()) {
            ((InteractorTypeList)getInteractorTypes()).clearOnly();
            this.interactorTypes.addAll(interactorTypes);
        }
        else {
            getInteractorTypes().clear();
        }
    }

    public boolean isEmpty() {
        //We don not want to have a empty interactor, we prefer a null interactor
        return
                (this.getIdentifiers() == null || this.getIdentifiers().isEmpty()) &&
                        (this.getAlternativeIdentifiers() == null || this.getAlternativeIdentifiers().isEmpty()) &&
                        (this.getAliases() == null || this.getAliases().isEmpty()) &&
                        (!this.hasOrganism() || (this.hasOrganism()
                                && this.getOrganism().getIdentifiers().isEmpty()
                                && this.getOrganism().getTaxid() == null || this.getOrganism().getTaxid().isEmpty()) ) &&
                        (this.getInteractorTypes() == null || this.getInteractorTypes().isEmpty()) &&
                        (this.getXrefs() == null || this.getXrefs().isEmpty()) &&
                        (this.getChecksums() == null || this.getChecksums().isEmpty());
    }

    protected void resetShortNameFromAliases(){
        if (mitabAliases.isEmpty()){
            super.setShortName(null);
            super.setFullName(null);
        }
        else {
            String newShortName=null;
            for (Alias alias : mitabAliases){
                if (newShortName == null){
                    newShortName = alias.getName();
                }
                else if (newShortName.length() > alias.getName().length()){
                    newShortName = alias.getName();
                }
            }
            super.setShortName(newShortName);
        }
    }

    protected void resetFullNameFromAliases(){
        String newScientificName=null;
        for (Alias alias : mitabAliases){
            if (newScientificName == null){
                newScientificName = alias.getName();
            }
            else if (newScientificName.length() < alias.getName().length()){
                newScientificName = alias.getName();
            }
        }
        super.setFullName(newScientificName);
    }

    protected void resetShortNameFromIdentifiers(){
        if (getIdentifiers().isEmpty()){
            super.setShortName("unknown");
            super.setFullName(null);
        }
        else {
            super.setShortName(getIdentifiers().iterator().next().getId());
        }
    }

    protected void resetInteractorTypeNameFromMiReferences(){
        if (!interactorTypes.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(interactorTypes), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getType().setShortName(name);
                getType().setFullName(name);
            }
        }
    }

    protected void resetInteractorTypeNameFromFirstReferences(){
        if (!interactorTypes.isEmpty()){
            Iterator<CrossReference> typesIterator = interactorTypes.iterator();
            String name = null;

            while (name == null && typesIterator.hasNext()){
                CrossReference ref = typesIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getType().setShortName(name != null ? name : "unknown");
            getType().setFullName(name != null ? name : "unknown");
        }
    }

    protected void processAddedIdentifierEvent(Xref added){
       // do nothing, to override only
    }

    protected void processRemovedIdentifierEvent(Xref removed){
        // do nothing, to override only
    }

    protected void clearPropertiesLinkedToIdentifiers(){
        // do nothing, only to override
    }

    protected void processAddedAliasEvent(psidev.psi.mi.jami.model.Alias added){
        // do nothing, to override only
    }

    protected void processRemovedAliasEvent(psidev.psi.mi.jami.model.Alias removed){
        // do nothing, to override only
    }

    protected void clearPropertiesLinkedToAliases(){
        // do nothing, only to override
    }

    protected void processAddedChecksumEvent(psidev.psi.mi.jami.model.Checksum added){
        // do nothing, to override only
    }

    protected void processRemovedChecksumEvent(psidev.psi.mi.jami.model.Checksum removed){
        // do nothing, to override only
    }

    protected void clearPropertiesLinkedToChecksum(){
        // do nothing, only to override
    }

    /////////////////////////////
    // Object's override

    //TODO Update the toString, equals and hash

    @Override
    public void setShortName(String name) {
        super.setShortName(name);

        Alias newAlias = new AliasImpl( "unknown", name, "display_short");
        if (!getMitabAliases().contains(newAlias)){
            ((InteractorMitabAliasList)mitabAliases).addOnly(newAlias);
        }
    }

    @Override
    public void setFullName(String name) {
        super.setFullName(name);

        Alias newAlias = new AliasImpl( "unknown", name, "display_long");
        if (!getMitabAliases().contains(newAlias)){
            ((InteractorMitabAliasList)mitabAliases).addOnly(newAlias);
        }
    }

    public void setShortNameOnly(String name) {
        super.setShortName(name);
    }

    public void setFullNameOnly(String name) {
        super.setFullName(name);
    }

    @Override
    public void setType(CvTerm type) {
        super.setType(type);

        processNewTypeInInteractorTypesList(type);
    }

    protected void setTypeOnly(CvTerm type) {
        super.setType(type);
    }

    private void processNewTypeInInteractorTypesList(CvTerm type) {
        ((InteractorTypeList)getInteractorTypes()).clearOnly();
        if (type.getMIIdentifier() != null){
            ((InteractorTypeList)interactorTypes).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, type.getMIIdentifier(), type.getFullName() != null ? type.getFullName(): type.getShortName()));
        }
        else{
            if (!type.getIdentifiers().isEmpty()){
                Xref ref = type.getIdentifiers().iterator().next();
                ((InteractorTypeList)interactorTypes).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), type.getFullName() != null ? type.getFullName(): type.getShortName()));
            }
            else {
                ((InteractorTypeList)interactorTypes).addOnly(new CrossReferenceImpl("unknown", "-", type.getFullName() != null ? type.getFullName(): type.getShortName()));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{identifiers=").append(getUniqueIdentifiers());
        sb.append(", alternativeIdentifiers=").append(getAlternativeIdentifiers());
        sb.append(", aliases=").append(getMitabAliases());
        sb.append(", organism=").append(organism != null ? organism.getTaxId() : "-");
        sb.append(", interactorTypes=").append(getInteractorTypes());
        sb.append(", xrefs=").append(getMitabXrefs());
        sb.append(", annotations=").append(getMitabAnnotations());
        sb.append(", checksums=").append(getMitabChecksums());
        sb.append('}');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        //TODO When two interactions are equals?

        MitabInteractor that = (MitabInteractor) o;

        if (uniqueIdentifiers != null ? !CollectionUtils.isEqualCollection(uniqueIdentifiers, that.getUniqueIdentifiers()) : (that.uniqueIdentifiers != null && !that.uniqueIdentifiers.isEmpty()))
            return false;
        if (organism != null ? !organism.equals(that.organism) : that.organism != null) return false;
        if (alternativeIdentifiers != null ? !CollectionUtils.isEqualCollection(alternativeIdentifiers, that.getAlternativeIdentifiers()) : (that.alternativeIdentifiers != null && !that.alternativeIdentifiers.isEmpty()))
            return false;
        if (mitabAliases != null ? !CollectionUtils.isEqualCollection(mitabAliases, that.getMitabAliases()) : (that.mitabAliases != null && !that.mitabAliases.isEmpty()))
            return false;
        if (interactorTypes != null ? !CollectionUtils.isEqualCollection(interactorTypes, that.getInteractorTypes()) : (that.interactorTypes != null && !that.interactorTypes.isEmpty()))
            return false;
        if (mitabXrefs != null ? !CollectionUtils.isEqualCollection(mitabXrefs, that.getMitabXrefs()) : (that.mitabXrefs != null && !that.mitabXrefs.isEmpty())) return false;
        if (mitabAnnotations != null ? !CollectionUtils.isEqualCollection(mitabAnnotations, that.getMitabAnnotations()) : (that.mitabAnnotations != null && !that.mitabAnnotations.isEmpty()))
            return false;
        if (mitabChecksums != null ? !CollectionUtils.isEqualCollection(mitabChecksums, that.getMitabChecksums()) : (that.mitabChecksums != null && !that.mitabChecksums.isEmpty()))
            return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = (uniqueIdentifiers != null ? uniqueIdentifiers.hashCode() : 0);
        result = 31 * result + (organism != null ? organism.hashCode() : 0);
        result = 31 * result + (alternativeIdentifiers != null ? alternativeIdentifiers.hashCode() : 0);
        result = 31 * result + (mitabAliases != null ? mitabAliases.hashCode() : 0);
        result = 31 * result + (interactorTypes != null ? interactorTypes.hashCode() : 0);
        result = 31 * result + (mitabXrefs != null ? mitabXrefs.hashCode() : 0);
        result = 31 * result + (mitabAnnotations != null ? mitabAnnotations.hashCode() : 0);
        result = 31 * result + (mitabChecksums != null ? mitabChecksums.hashCode() : 0);


        return result;
    }

    protected class InteractorIdentifierList extends AbstractListHavingPoperties<Xref> {
        public InteractorIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            CrossReference modified = null;

            if (getUniqueIdentifiers().isEmpty()){
                if (added instanceof CrossReference){
                    modified = (CrossReference) added;
                    ((InteractorUniqueIdentifierList)uniqueIdentifiers).addOnly(modified);
                }
                else {
                    modified = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName():null);
                    ((InteractorUniqueIdentifierList)uniqueIdentifiers).addOnly(modified);
                }
            }
            else {
                if (added instanceof CrossReference){
                    modified = (CrossReference) added;
                    ((InteractorAlternativeIdentifierList)alternativeIdentifiers).addOnly(modified);
                }
                else {
                    modified = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName():null);
                    ((InteractorAlternativeIdentifierList)alternativeIdentifiers).addOnly(modified);
                }
            }

            if (getShortName() == null){
                setShortNameOnly(added.getId());
            }
            else if (getFullName() == null){
                setFullNameOnly(added.getId());
            }

            processAddedIdentifierEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {

            CrossReference modified = null;
            if (removed instanceof CrossReference){
                modified = (CrossReference) removed;

                ((InteractorUniqueIdentifierList)getUniqueIdentifiers()).removeOnly(removed);
                ((InteractorAlternativeIdentifierList)getAlternativeIdentifiers()).removeOnly(removed);
            }
            else {
                modified = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier() != null ? removed.getQualifier().getShortName():null);
                ((InteractorUniqueIdentifierList)getUniqueIdentifiers()).removeOnly(modified);
                ((InteractorAlternativeIdentifierList)getAlternativeIdentifiers()).removeOnly(modified);
            }

            // reset shortName/fullName using identifiers
            if (getShortName() != null && getShortName().equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (getFullName() != null && getFullName().equals(removed.getId())){
                setFullNameOnly(getShortName());
            }

            processRemovedIdentifierEvent(modified);
        }

        @Override
        protected void clearProperties() {
            ((InteractorUniqueIdentifierList)getUniqueIdentifiers()).clearOnly();
            ((InteractorAlternativeIdentifierList)getAlternativeIdentifiers()).clearOnly();
            if (getMitabAliases().isEmpty()){
                setFullNameOnly( null);
                resetShortNameFromIdentifiers();
            }

            clearPropertiesLinkedToIdentifiers();
        }
    }

    protected class InteractorAlternativeIdentifierList extends AbstractListHavingPoperties<CrossReference> {
        public InteractorAlternativeIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {
            // we added a alternative identifier, needs to add identifier
            ((InteractorIdentifierList)getIdentifiers()).addOnly(added);

            if (getShortName() == null){
                setShortNameOnly(added.getId());
            }
            else if (getFullName() == null){
                setFullNameOnly(added.getId());
            }

            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // we removed a alternative identifier, needs to removed identifier
            ((InteractorIdentifierList)getIdentifiers()).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (getShortName() != null && getShortName().equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (getFullName() != null && getFullName().equals(removed.getId())){
                setFullNameOnly(getShortName());
            }

            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all identifiers that are not in unique identifiers
            retainAllOnly(getUniqueIdentifiers());

            if (getMitabAliases().isEmpty()){
                setFullNameOnly(null);
                resetShortNameFromIdentifiers();
            }

            clearPropertiesLinkedToIdentifiers();
        }
    }

    protected class InteractorUniqueIdentifierList extends AbstractListHavingPoperties<CrossReference> {
        public InteractorUniqueIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {
            // we added a unique identifier, needs to add identifier
            ((InteractorIdentifierList)getIdentifiers()).addOnly(added);

            if (getShortName() == null){
                setShortNameOnly(added.getId());
            }
            else if (getFullName() == null){
                setFullNameOnly(added.getId());
            }

            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // we removed a unique identifier, needs to removed identifier
            ((InteractorIdentifierList)getIdentifiers()).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (getShortName() != null && getShortName().equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (getFullName() != null && getFullName().equals(removed.getId())){
                setFullNameOnly(getShortName());
            }

            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all identifiers that are not in alternative identifiers
            retainAllOnly(getAlternativeIdentifiers());

            if (getMitabAliases().isEmpty()){
                setFullNameOnly(null);
                resetShortNameFromIdentifiers();
            }

            clearPropertiesLinkedToIdentifiers();
        }
    }

    protected class InteractorAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public InteractorAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {
            Alias modified = null;
            if (added instanceof Alias){
                modified = (Alias) added;
                ((InteractorMitabAliasList)getMitabAliases()).addOnly(modified);
            }
            else {
                modified = new AliasImpl("unknown", added.getName(), added.getType() != null ? added.getType().getShortName() : null);
                ((InteractorMitabAliasList)getMitabAliases()).addOnly(modified);
            }

            if (getShortName() == null){
                setShortNameOnly(added.getName());
            }
            else if (getFullName() == null){
                setFullNameOnly(added.getName());
            }
            // the first added alias is the shortname
            else if (size() == 1){
                setShortNameOnly(added.getName());
            }

            processAddedAliasEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {
            Alias modified = null;
            if (removed instanceof Alias){
                modified = (Alias) removed;
                ((InteractorMitabAliasList)getMitabAliases()).removeOnly(modified);
            }
            else {
                modified = new AliasImpl("unknown", removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null);
                ((InteractorMitabAliasList)getMitabAliases()).removeOnly(modified);
            }

            // reset shortName/fullName using identifiers
            if (isEmpty()){
                resetShortNameFromIdentifiers();
                setFullNameOnly(null);
            }
            else if (getShortName() != null && getShortName().equals(removed.getName())){
                resetShortNameFromAliases();
            }
            else if (getFullName() != null && getFullName().equals(removed.getName())){
                resetFullNameFromAliases();
            }

            processRemovedAliasEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all aliases
            ((InteractorMitabAliasList)getMitabAliases()).clearOnly();
            setFullNameOnly(null);
            resetShortNameFromIdentifiers();
            clearPropertiesLinkedToAliases();
        }
    }

    protected class InteractorMitabAliasList extends AbstractListHavingPoperties<Alias> {
        public InteractorMitabAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Alias added) {

            // we added a alias, needs to add it in aliases
            ((InteractorAliasList)getAliases()).addOnly(added);

            if (getShortName() == null){
                setShortNameOnly(added.getName());
            }
            else if (getFullName() == null){
                setFullNameOnly(added.getName());
            }
            // the first added alias is the shortname
            else if (size() == 1){
                setShortNameOnly(added.getName());
            }

            processAddedAliasEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {

            // we removed a alias, needs to remove it in aliases
            ((InteractorAliasList)getAliases()).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (isEmpty()){
                resetShortNameFromIdentifiers();
                setFullNameOnly(null);
            }
            else if (getShortName() != null && getShortName().equals(removed.getName())){
                resetShortNameFromAliases();
            }
            else if (getFullName() != null && getFullName().equals(removed.getName())){
                resetFullNameFromAliases();
            }

            processRemovedAliasEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all aliases
            ((InteractorAliasList)getAliases()).clearOnly();
            setFullNameOnly(null);
            resetShortNameFromIdentifiers();
            clearPropertiesLinkedToAliases();
        }
    }

    protected class InteractorXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public InteractorXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {
            if (added instanceof CrossReference){
                ((InteractorMitabXrefList)getMitabXrefs()).addOnly((CrossReference) added);
            }
            else {
                ((InteractorMitabXrefList)getMitabXrefs()).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (removed instanceof CrossReference){
                ((InteractorMitabXrefList)getMitabXrefs()).removeOnly(removed);
            }
            else {
                ((InteractorMitabXrefList)getMitabXrefs()).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier() != null ? removed.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all xrefs
            ((InteractorMitabXrefList)getMitabXrefs()).clearOnly();
        }
    }

    protected class InteractorMitabXrefList extends AbstractListHavingPoperties<CrossReference> {
        public InteractorMitabXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // we added a xref, needs to add it in xrefs
            ((InteractorXrefList)getXrefs()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            // we removed a xref, needs to remove it in xrefs
            ((InteractorXrefList)getXrefs()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all xrefs
            ((InteractorXrefList)getXrefs()).clearOnly();
        }
    }

    protected class InteractorAnnotationList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Annotation> {
        public InteractorAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Annotation){
                ((InteractorMitabAnnotationList)getMitabAnnotations()).addOnly((Annotation) added);
            }
            else {
                ((InteractorMitabAnnotationList)getMitabAnnotations()).addOnly(new AnnotationImpl(added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractorMitabAnnotationList)getMitabAnnotations()).removeOnly(removed);
            }
            else {
                ((InteractorMitabAnnotationList)getMitabAnnotations()).removeOnly(new AnnotationImpl(removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorMitabAnnotationList)getMitabAnnotations()).clearOnly();
        }
    }

    protected class InteractorMitabAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractorMitabAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {

            // we added a annotation, needs to add it in annotations
            ((InteractorAnnotationList)getAnnotations()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {

            // we removed a annotation, needs to remove it in annotations
            ((InteractorAnnotationList)getAnnotations()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorAnnotationList)getAnnotations()).clearOnly();
        }
    }

    protected class InteractorChecksumList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Checksum> {
        public InteractorChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {
            Checksum modified = null;
            if (added instanceof Checksum){
                modified = (Checksum) added;
                ((InteractorMitabChecksumList)getMitabChecksums()).addOnly(modified);
            }
            else {
                modified = new ChecksumImpl(added.getMethod().getShortName(), added.getValue());
                ((InteractorMitabChecksumList)getMitabChecksums()).addOnly(modified);
            }

            processAddedChecksumEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            Checksum modified = null;
            if (removed instanceof Checksum){
                modified = (Checksum) removed;
                ((InteractorMitabChecksumList)getMitabChecksums()).removeOnly(modified);
            }
            else {
                modified = new ChecksumImpl(removed.getMethod().getShortName(), removed.getValue());
                ((InteractorMitabChecksumList)getMitabChecksums()).removeOnly(modified);
            }

            processRemovedChecksumEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractorMitabChecksumList)getMitabChecksums()).clearOnly();
            clearPropertiesLinkedToChecksum();
        }
    }

    protected class InteractorMitabChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractorMitabChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {

            // we added a checksum, needs to add it in checksums
            ((InteractorChecksumList)getChecksums()).addOnly(added);
            processRemovedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {

            // we removed a checksum, needs to remove it in checksums
            ((InteractorChecksumList)getChecksums()).removeOnly(removed);
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractorChecksumList)getChecksums()).clearOnly();
            clearPropertiesLinkedToChecksum();
        }
    }

    protected class InteractorTypeList extends AbstractListHavingPoperties<CrossReference> {
        public InteractorTypeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // the type is not set yet
            if (getType() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setTypeOnly(new DefaultCvTerm(name, name, added));
            }
            // it was a UNSPECIFIED type, needs to clear it
            else if (size() > 1 && Interactor.UNKNOWN_INTERACTOR.equalsIgnoreCase(getType().getShortName().trim())){
                // remove unspecified method
                CrossReference old = new CrossReferenceImpl(CvTerm.PSI_MI, Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR);
                removeOnly(old);
                getType().getXrefs().remove(old);

                String name = added.getText() != null ? added.getText() : "unknown";

                setTypeOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getType().getXrefs().add(added);
                // reset shortname
                if (getType().getMIIdentifier() != null && getType().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getType().setShortName(name);
                    }
                    else {
                        resetInteractorTypeNameFromMiReferences();
                        if (getType().getShortName().equals("unknown")){
                            resetInteractorTypeNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getType() != null){
                getType().getXrefs().remove(removed);

                if (removed.getText() != null && getType().getShortName().equals(removed.getText())){
                    if (getType().getMIIdentifier() != null){
                        resetInteractorTypeNameFromMiReferences();
                        if (getType().getShortName().equals("unknown")){
                            resetInteractorTypeNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractorTypeNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setTypeOnly(CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
                processNewTypeInInteractorTypesList(getType());
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            setTypeOnly(CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
            processNewTypeInInteractorTypesList(getType());
        }
    }
}
