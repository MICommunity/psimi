package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Interactor
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the Interactor object is a complex object.
 * To compare Interactor objects, you can use some comparators provided by default:
 * - DefaultInteractorBaseComparator
 * - UnambiguousInteractorBaseComparator
 * - DefaultExactInteractorBaseComparator
 * - UnambiguousExactInteractorBaseComparator
 * - DefaultInteractorComparator
 * - UnambiguousInteractorComparator
 * - DefaultExactInteractorComparator
 * - UnambiguousExactInteractorComparator
 * - AbstractInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultInteractor implements Interactor, Serializable {

    private String shortName;
    private String fullName;
    private Collection<Xref> identifiers;
    private Collection<Checksum> checksums;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private Collection<Alias> aliases;
    private Organism organism;
    private CvTerm interactorType;

    public DefaultInteractor(String name, CvTerm type){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.shortName = name;
        if (type == null){
            this.interactorType = CvTermUtils.createUnknownInteractorType();
        }
        else {
            this.interactorType = type;
        }
    }

    public DefaultInteractor(String name, String fullName, CvTerm type){
        this(name, type);
        this.fullName = fullName;
    }

    public DefaultInteractor(String name, CvTerm type, Organism organism){
        this(name, type);
        this.organism = organism;
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Organism organism){
        this(name, fullName, type);
        this.organism = organism;
    }

    public DefaultInteractor(String name, CvTerm type, Xref uniqueId){
        this(name, type);
        getIdentifiers().add(uniqueId);
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Xref uniqueId){
        this(name, fullName, type);
        getIdentifiers().add(uniqueId);
    }

    public DefaultInteractor(String name, CvTerm type, Organism organism, Xref uniqueId){
        this(name, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public DefaultInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId){
        this(name, fullName, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public DefaultInteractor(String name){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.shortName = name;
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public DefaultInteractor(String name, String fullName){
        this(name);
        this.fullName = fullName;
    }

    public DefaultInteractor(String name, Organism organism){
        this(name);
        this.organism = organism;
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public DefaultInteractor(String name, String fullName, Organism organism){
        this(name, fullName);
        this.organism = organism;
    }

    public DefaultInteractor(String name, Xref uniqueId){
        this(name);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public DefaultInteractor(String name, String fullName, Xref uniqueId){
        this(name, fullName);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public DefaultInteractor(String name, Organism organism, Xref uniqueId){
        this(name, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public DefaultInteractor(String name, String fullName, Organism organism, Xref uniqueId){
        this(name, fullName, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initialiseIdentifiers(){
        this.identifiers = new ArrayList<Xref>();
    }

    protected void initialiseChecksums(){
        this.checksums = new ArrayList<Checksum>();
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
           this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
        }
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs){
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else {
            this.xrefs = xrefs;
        }
    }

    protected void initialiseAliasesWith(Collection<Alias> aliases){
        if (aliases == null){
            this.aliases = Collections.EMPTY_LIST;
        }
        else {
            this.aliases = aliases;
        }
    }

    protected void initialiseIdentifiersWith(Collection<Xref> identifiers){
        if (identifiers == null){
            this.identifiers = Collections.EMPTY_LIST;
        }
        else {
            this.identifiers = identifiers;
        }
    }

    protected void initialiseChecksumsWith(Collection<Checksum> checksums){
        if (checksums == null){
            this.checksums = Collections.EMPTY_LIST;
        }
        else {
            this.checksums = checksums;
        }
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        if (name == null || (name != null && name.length() == 0)){
             throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.shortName = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public Collection<Xref> getIdentifiers() {
        if (identifiers == null){
           initialiseIdentifiers();
        }
        return this.identifiers;
    }

    /**
     *
     * @return the first identifier in the list of identifiers or null if the list is empty
     */
    public Xref getPreferredIdentifier() {
        return !identifiers.isEmpty() ? identifiers.iterator().next() : null;
    }

    public Collection<Checksum> getChecksums() {
        if (checksums == null){
            initialiseChecksums();
        }
        return this.checksums;
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null){
            initialiseXrefs();
        }
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Alias> getAliases() {
        if (aliases == null){
            initialiseAliases();
        }
        return this.aliases;
    }

    public Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public CvTerm getInteractorType() {
        return this.interactorType;
    }

    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            this.interactorType = CvTermUtils.createUnknownInteractorType();
        }
        else {
            this.interactorType = interactorType;
        }
    }

    @Override
    public String toString() {
        return shortName + (organism != null ? ", " + organism.toString() : "") + (interactorType != null ? ", " + interactorType.toString() : "")  ;
    }
}
