package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for participant
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the participant object is a complex object.
 * To compare participant objects, you can use some comparators provided by default:
 * - DefaultParticipantBaseComparator
 * - UnambiguousParticipantBaseComparator
 * - DefaultExactParticipantBaseComparator
 * - UnambiguousExactParticipantBaseComparator
 * - ParticipantBaseComparator
 * - DefaultParticipantComparator
 * - UnambiguousParticipantComparator
 * - DefaultExactParticipantComparator
 * - UnambiguousExactParticipantComparator
 * - ParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipant<T extends Interactor> implements Participant<T>, Serializable {

    private T interactor;
    private CvTerm biologicalRole;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private Collection<Alias> aliases;
    private Stoichiometry stoichiometry;
    private CausalRelationship causalRelationship;

    public DefaultParticipant(T interactor){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipant(T interactor, CvTerm bioRole){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = bioRole != null ? bioRole : CvTermUtils.createUnspecifiedRole();
    }

    public DefaultParticipant(T interactor, Stoichiometry stoichiometry){
        this(interactor);
        this.stoichiometry = stoichiometry;
    }

    public DefaultParticipant(T interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        this(interactor, bioRole);
        this.stoichiometry = stoichiometry;
    }

    protected void initialiseXrefs() {
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAnnotations() {
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs) {
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else {
            this.xrefs = xrefs;
        }
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations) {
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
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

    public T getInteractor() {
        return this.interactor;
    }

    public void setInteractor(T interactor) {
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
    }

    public CvTerm getBiologicalRole() {
        return this.biologicalRole;
    }

    public void setBiologicalRole(CvTerm bioRole) {
        if (bioRole == null){
            this.biologicalRole = CvTermUtils.createUnspecifiedRole();
        }
        else {
            biologicalRole = bioRole;
        }
    }

    public CausalRelationship getCausalRelationship() {
        return this.causalRelationship;
    }

    public void setCausalRelationship(CausalRelationship relationship) {
        this.causalRelationship = relationship;
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

    public Stoichiometry getStoichiometry() {
        return this.stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        if (stoichiometry == null){
            this.stoichiometry = null;
        }
        else {
            this.stoichiometry = new DefaultStoichiometry(stoichiometry, stoichiometry);
        }
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    @Override
    public String toString() {
        return interactor.toString() + " ( " + biologicalRole.toString() + ")" + (stoichiometry != null ? ", stoichiometry: " + stoichiometry.toString() : "");
    }
}
