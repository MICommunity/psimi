package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
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

public class MitabInteractor extends DefaultInteractor implements Serializable {

    ///////////////////////
    // Instance variables

    /**
     * Primary uniqueIdentifiers of the interactor.
     */
    protected List<CrossReference> uniqueIdentifiers
            = new InteractorUniqueIdentifierList();

    /**
     * Alternative uniqueIdentifiers of the interactor.
     */
    protected List<CrossReference> alternativeIdentifiers
            = new InteractorAlternativeIdentifierList();

    /**
     * Aliases of the interactor (ie. alternative names).
     */
    protected List<Alias> mitabAliases
            = new InteractorMitabAliasList();

    /**
     * Organism the interactor belongs to.
     */
    protected Organism organism;

    /**
     * Cross references of the interactor.
     */
    protected List<CrossReference> mitabXrefs
            = new InteractorMitabXrefList();

    /**
     * Annotations of the interactor.
     */
    protected List<Annotation> mitabAnnotations
            = new InteractorMitabAnnotationList();

    /**
     * Checksums of the interactor.
     */
    protected List<Checksum> mitabChecksums
            = new InteractorMitabChecksumList();

    /**
     * Type of the interactor.
     */
    protected List<CrossReference> interactorTypes
            = new InteractorTypeList();

    public MitabInteractor() {
        super("unknown", CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
        ((InteractorTypeList)interactorTypes).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR));
    }

    public MitabInteractor(List<CrossReference> identifiers) {
        super("unknown", CvTermFactory.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));

        if (identifiers == null) {
            throw new IllegalArgumentException("You must give a non null list of uniqueIdentifiers.");
        }
        uniqueIdentifiers.addAll(identifiers);
        ((InteractorTypeList)interactorTypes).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR));
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
        uniqueIdentifiers.addAll(identifiers);
        processNewTypeInInteractorTypesList(interactorType);
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new InteractorIdentifierList();
    }

    @Override
    protected void initializeAliases() {
        this.aliases = new InteractorAliasList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new InteractorXrefList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new InteractorAnnotationList();
    }

    @Override
    protected void initializeChecksums() {
        this.checksums = new InteractorChecksumList();
    }

    /**
     * Getter for property 'identifiers'.
     *
     * @return Value for property 'identifiers'.
     */
    public List<CrossReference> getUniqueIdentifiers() {
        return uniqueIdentifiers;
    }

    /**
     * Setter for property 'identifiers'.
     *
     * @param identifiers Value to set for property 'identifiers'.
     */
    public void setUniqueIdentifiers(List<CrossReference> identifiers) {
        this.uniqueIdentifiers.clear();
        if (identifiers != null) {
            this.uniqueIdentifiers.addAll(identifiers);
        }
    }

    /**
     * Getter for property 'alternativeIdentifiers'.
     *
     * @return Value for property 'alternativeIdentifiers'.
     */
    public List<CrossReference> getAlternativeIdentifiers() {
        return alternativeIdentifiers;
    }

    /**
     * Setter for property 'alternativeIdentifiers'.
     *
     * @param alternativeIdentifiers Value to set for property 'alternativeIdentifiers'.
     */
    public void setAlternativeIdentifiers(List<CrossReference> alternativeIdentifiers) {
        this.alternativeIdentifiers.clear();
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
        return mitabAliases;
    }

    /**
     * Setter for property 'aliases'.
     *
     * @param aliases Value to set for property 'aliases'.
     */
    public void setMitabAliases(List<Alias> aliases) {
        this.mitabAliases.clear();
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
        return mitabXrefs;
    }

    /**
     * Setter for property 'xrefs'.
     *
     * @param xrefs Value to set for property 'xrefs'.
     */
    public void setMitabXrefs(List<CrossReference> xrefs) {
        this.mitabXrefs.clear();
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
        return mitabAnnotations;
    }

    /**
     * Setter for property 'annotations'.
     *
     * @param annotations Value to set for property 'annotations'.
     */
    public void setMitabAnnotations(List<Annotation> annotations) {
        this.mitabAnnotations.clear();
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
        return mitabChecksums;
    }

    /**
     * Setter for property 'checksums'.
     *
     * @param checksums Value to set for property 'checksums'.
     */
    public void setMitabChecksums(List<Checksum> checksums) {
        this.mitabChecksums.clear();
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
        return organism != null;
    }

    /**
     * Getter fot property 'interactorTypes'.
     *
     * @return Value for property 'interactorTypes'.
     */
    public List<CrossReference> getInteractorTypes() {
        return interactorTypes;
    }

    /**
     * Setter for property 'interactorTypes'.
     *
     * @param interactorTypes Value to set for property 'interactorTypes'.
     */
    public void setInteractorTypes(List<CrossReference> interactorTypes) {
        this.interactorTypes.clear();
        if (interactorTypes != null) {
            this.interactorTypes.addAll(interactorTypes);
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
            this.shortName = null;
            this.fullName = null;
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
            this.shortName = newShortName;
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
        this.fullName = newScientificName;
    }

    protected void resetShortNameFromIdentifiers(){
        if (identifiers.isEmpty()){
            this.shortName = null;
            this.fullName = null;
        }
        else {
            this.shortName = identifiers.iterator().next().getId();
        }
    }

    protected void resetInteractorTypeNameFromMiReferences(){
        if (!interactorTypes.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(interactorTypes), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                type.setShortName(name);
                type.setFullName(name);
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

            type.setShortName(name != null ? name : "unknown");
            type.setFullName(name != null ? name : "unknown");
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
        if (!mitabAliases.contains(newAlias)){
            ((InteractorMitabAliasList)mitabAliases).addOnly(newAlias);
        }
    }

    @Override
    public void setFullName(String name) {
        super.setFullName(name);

        Alias newAlias = new AliasImpl( "unknown", name, "display_long");
        if (!mitabAliases.contains(newAlias)){
            ((InteractorMitabAliasList)mitabAliases).addOnly(newAlias);
        }
    }

    @Override
    public void setType(CvTerm type) {
        super.setType(type);

        processNewTypeInInteractorTypesList(type);
    }

    private void processNewTypeInInteractorTypesList(CvTerm type) {
        ((InteractorTypeList)interactorTypes).clearOnly();
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
        sb.append("{identifiers=").append(identifiers);
        sb.append(", alternativeIdentifiers=").append(alternativeIdentifiers);
        sb.append(", aliases=").append(aliases);
        sb.append(", organism=").append(organism != null ? organism.getTaxId() : "-");
        sb.append(", interactorTypes=").append(interactorTypes);
        sb.append(", xrefs=").append(xrefs);
        sb.append(", annotations=").append(annotations);
        sb.append(", checksums=").append(checksums);
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

        if (identifiers != null ? !CollectionUtils.isEqualCollection(identifiers, that.identifiers) : that.identifiers != null)
            return false;
        if (organism != null ? !organism.equals(that.organism) : that.organism != null) return false;
        if (alternativeIdentifiers != null ? !CollectionUtils.isEqualCollection(alternativeIdentifiers, that.alternativeIdentifiers) : that.alternativeIdentifiers != null)
            return false;
        if (aliases != null ? !CollectionUtils.isEqualCollection(aliases, that.aliases) : that.aliases != null)
            return false;
        if (interactorTypes != null ? !CollectionUtils.isEqualCollection(interactorTypes, that.interactorTypes) : that.interactorTypes != null)
            return false;
        if (xrefs != null ? !CollectionUtils.isEqualCollection(xrefs, that.xrefs) : that.xrefs != null) return false;
        if (annotations != null ? !CollectionUtils.isEqualCollection(annotations, that.annotations) : that.annotations != null)
            return false;
        if (checksums != null ? !CollectionUtils.isEqualCollection(checksums, that.checksums) : that.checksums != null)
            return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = (identifiers != null ? identifiers.hashCode() : 0);
        result = 31 * result + (organism != null ? organism.hashCode() : 0);
        result = 31 * result + (alternativeIdentifiers != null ? alternativeIdentifiers.hashCode() : 0);
        result = 31 * result + (aliases != null ? aliases.hashCode() : 0);
        result = 31 * result + (interactorTypes != null ? interactorTypes.hashCode() : 0);
        result = 31 * result + (xrefs != null ? xrefs.hashCode() : 0);
        result = 31 * result + (annotations != null ? annotations.hashCode() : 0);
        result = 31 * result + (checksums != null ? checksums.hashCode() : 0);


        return result;
    }

    protected class InteractorIdentifierList extends AbstractListHavingPoperties<Xref> {
        public InteractorIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            CrossReference modified = null;

            if (uniqueIdentifiers.isEmpty()){
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

            if (shortName == null){
                shortName = added.getId();
            }
            else if (fullName == null){
                fullName = added.getId();
            }

            processAddedIdentifierEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {

            CrossReference modified = null;
            if (removed instanceof CrossReference){
                modified = (CrossReference) removed;

                ((InteractorUniqueIdentifierList)uniqueIdentifiers).removeOnly(removed);
                ((InteractorAlternativeIdentifierList)alternativeIdentifiers).removeOnly(removed);
            }
            else {
                modified = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier() != null ? removed.getQualifier().getShortName():null);
                ((InteractorUniqueIdentifierList)uniqueIdentifiers).removeOnly(modified);
                ((InteractorAlternativeIdentifierList)alternativeIdentifiers).removeOnly(modified);
            }

            // reset shortName/fullName using identifiers
            if (shortName != null && shortName.equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (fullName != null && fullName.equals(removed.getId())){
                fullName = shortName;
            }

            processRemovedIdentifierEvent(modified);
        }

        @Override
        protected void clearProperties() {
            ((InteractorUniqueIdentifierList)uniqueIdentifiers).clearOnly();
            ((InteractorAlternativeIdentifierList)alternativeIdentifiers).clearOnly();
            if (mitabAliases.isEmpty()){
                fullName = null;
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
            ((InteractorIdentifierList)identifiers).addOnly(added);

            if (shortName == null){
                shortName = added.getId();
            }
            else if (fullName == null){
                fullName = added.getId();
            }

            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // we removed a alternative identifier, needs to removed identifier
            ((InteractorIdentifierList)identifiers).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (shortName != null && shortName.equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (fullName != null && fullName.equals(removed.getId())){
                fullName = shortName;
            }

            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all identifiers that are not in unique identifiers
            retainAllOnly(uniqueIdentifiers);

            if (mitabAliases.isEmpty()){
                fullName = null;
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
            ((InteractorIdentifierList)identifiers).addOnly(added);

            if (shortName == null){
                shortName = added.getId();
            }
            else if (fullName == null){
                fullName = added.getId();
            }

            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // we removed a unique identifier, needs to removed identifier
            ((InteractorIdentifierList)identifiers).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (shortName != null && shortName.equals(removed.getId())){
                resetShortNameFromIdentifiers();
            }
            else if (fullName != null && fullName.equals(removed.getId())){
                fullName = shortName;
            }

            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all identifiers that are not in alternative identifiers
            retainAllOnly(alternativeIdentifiers);

            if (mitabAliases.isEmpty()){
                fullName = null;
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
                ((InteractorMitabAliasList)mitabAliases).addOnly(modified);
            }
            else {
                modified = new AliasImpl("unknown", added.getName(), added.getType() != null ? added.getType().getShortName() : null);
                ((InteractorMitabAliasList)mitabAliases).addOnly(modified);
            }

            if (shortName == null){
                shortName = added.getName();
            }
            else if (fullName == null){
                fullName = added.getName();
            }
            // the first added alias is the shortname
            else if (size() == 1){
                shortName = added.getName();
            }

            processAddedAliasEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {
            Alias modified = null;
            if (removed instanceof Alias){
                modified = (Alias) removed;
                ((InteractorMitabAliasList)mitabAliases).removeOnly(modified);
            }
            else {
                modified = new AliasImpl("unknown", removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null);
                ((InteractorMitabAliasList)mitabAliases).removeOnly(modified);
            }

            // reset shortName/fullName using identifiers
            if (isEmpty()){
                resetShortNameFromIdentifiers();
                fullName = null;
            }
            else if (shortName != null && shortName.equals(removed.getName())){
                resetShortNameFromAliases();
            }
            else if (fullName != null && fullName.equals(removed.getName())){
                resetFullNameFromAliases();
            }

            processRemovedAliasEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all aliases
            ((InteractorAliasList)aliases).clearOnly();
            fullName = null;
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
            ((InteractorAliasList)aliases).addOnly(added);

            if (shortName == null){
                shortName = added.getName();
            }
            else if (fullName == null){
                fullName = added.getName();
            }
            // the first added alias is the shortname
            else if (size() == 1){
                shortName = added.getName();
            }

            processAddedAliasEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {

            // we removed a alias, needs to remove it in aliases
            ((InteractorAliasList)aliases).removeOnly(removed);

            // reset shortName/fullName using identifiers
            if (isEmpty()){
                resetShortNameFromIdentifiers();
                fullName = null;
            }
            else if (shortName != null && shortName.equals(removed.getName())){
                resetShortNameFromAliases();
            }
            else if (fullName != null && fullName.equals(removed.getName())){
                resetFullNameFromAliases();
            }

            processRemovedAliasEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all aliases
            ((InteractorAliasList)aliases).clearOnly();
            fullName = null;
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
                ((InteractorMitabXrefList)mitabXrefs).addOnly((CrossReference) added);
            }
            else {
                ((InteractorMitabXrefList)mitabXrefs).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (removed instanceof CrossReference){
                ((InteractorMitabXrefList)mitabXrefs).removeOnly(removed);
            }
            else {
                ((InteractorMitabXrefList)mitabXrefs).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier() != null ? removed.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all xrefs
            ((InteractorMitabXrefList)mitabXrefs).clearOnly();
        }
    }

    protected class InteractorMitabXrefList extends AbstractListHavingPoperties<CrossReference> {
        public InteractorMitabXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // we added a xref, needs to add it in xrefs
            ((InteractorXrefList)xrefs).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            // we removed a xref, needs to remove it in xrefs
            ((InteractorXrefList)xrefs).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all xrefs
            ((InteractorXrefList)xrefs).clearOnly();
        }
    }

    protected class InteractorAnnotationList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Annotation> {
        public InteractorAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Annotation){
                ((InteractorMitabAnnotationList)mitabAnnotations).addOnly((Annotation) added);
            }
            else {
                ((InteractorMitabAnnotationList)mitabAnnotations).addOnly(new AnnotationImpl(added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractorMitabAnnotationList)mitabAnnotations).removeOnly(removed);
            }
            else {
                ((InteractorMitabAnnotationList)mitabAnnotations).removeOnly(new AnnotationImpl(removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorMitabAnnotationList)mitabAnnotations).clearOnly();
        }
    }

    protected class InteractorMitabAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractorMitabAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {

            // we added a annotation, needs to add it in annotations
            ((InteractorAnnotationList)annotations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {

            // we removed a annotation, needs to remove it in annotations
            ((InteractorAnnotationList)annotations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorAnnotationList)annotations).clearOnly();
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
                ((InteractorMitabChecksumList)mitabChecksums).addOnly(modified);
            }
            else {
                modified = new ChecksumImpl(added.getMethod().getShortName(), added.getValue());
                ((InteractorMitabChecksumList)mitabChecksums).addOnly(modified);
            }

            processAddedChecksumEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            Checksum modified = null;
            if (removed instanceof Checksum){
                modified = (Checksum) removed;
                ((InteractorMitabChecksumList)mitabChecksums).removeOnly(modified);
            }
            else {
                modified = new ChecksumImpl(removed.getMethod().getShortName(), removed.getValue());
                ((InteractorMitabChecksumList)mitabChecksums).removeOnly(modified);
            }

            processRemovedChecksumEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractorMitabChecksumList)mitabChecksums).clearOnly();
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
            ((InteractorChecksumList)checksums).addOnly(added);
            processRemovedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {

            // we removed a checksum, needs to remove it in checksums
            ((InteractorChecksumList)checksums).removeOnly(removed);
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractorChecksumList)checksums).clearOnly();
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
            if (type == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                type = new DefaultCvTerm(name, name, added);
            }
            else {
                type.getXrefs().add(added);
                // reset shortname
                if (type.getMIIdentifier() != null && type.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        type.setShortName(name);
                    }
                    else {
                        resetInteractorTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractorTypeNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (type != null){
                type.getXrefs().remove(removed);

                if (removed.getText() != null && type.getShortName().equals(removed.getText())){
                    if (type.getMIIdentifier() != null){
                        resetInteractorTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractorTypeNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractorTypeNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                type = null;
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            type = null;
        }
    }
}
