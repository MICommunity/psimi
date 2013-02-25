package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantBaseComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipant<I, T extends Interactor, F extends Feature> implements Participant<I, T, F>, Serializable {

    private I interaction;
    private T interactor;
    private CvTerm biologicalRole;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private Collection<Alias> aliases;
    private Collection<F> features;
    private Integer stoichiometry;

    public DefaultParticipant(I interaction, T interactor){
        this.interaction = interaction;
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipant(I interaction, T interactor, CvTerm bioRole){
        this.interaction = interaction;
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = bioRole != null ? CvTermFactory.createUnspecifiedRole() : bioRole;
    }

    public DefaultParticipant(I interaction, T interactor, Integer stoichiometry){
        this(interaction, interactor);
        this.stoichiometry = stoichiometry;
    }

    public DefaultParticipant(I interaction, T interactor, CvTerm bioRole, Integer stoichiometry){
        this(interaction, interactor, bioRole);
        this.stoichiometry = stoichiometry;
    }

    public DefaultParticipant(T interactor){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = CvTermFactory.createUnspecifiedRole();
    }

    public DefaultParticipant(T interactor, CvTerm bioRole){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = bioRole != null ? CvTermFactory.createUnspecifiedRole() : bioRole;
    }

    public DefaultParticipant(T interactor, Integer stoichiometry){
        this(interactor);
        this.stoichiometry = stoichiometry;
    }

    public DefaultParticipant(T interactor, CvTerm bioRole, Integer stoichiometry){
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

    protected void initialiseFeatures(){
        this.features = new ArrayList<F>();
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

    protected void initialiseFeaturesWith(Collection<F> features){
        if (features == null){
            this.features = Collections.EMPTY_LIST;
        }
        else {
            this.features = features;
        }
    }

    public I getInteraction() {
        return this.interaction;
    }

    public void setInteraction(I interaction) {
        this.interaction = interaction;
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
            this.biologicalRole = CvTermFactory.createUnspecifiedRole();
        }
        else {
            biologicalRole = bioRole;
        }
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

    public Collection<? extends F> getFeatures() {
        if (features == null){
            initialiseFeatures();
        }
        return this.features;
    }

    public Integer getStoichiometry() {
        return this.stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public boolean addFeature(F feature) {
        if (feature == null){
            return false;
        }

        if (features.add(feature)){
            feature.setParticipant(this);
            return true;
        }
        return false;
    }

    public boolean removeFeature(F feature) {
        if (feature == null){
            return false;
        }

        if (features.remove(feature)){
            feature.setParticipant(null);
            return true;
        }
        return false;
    }

    public boolean addAllFeatures(Collection<? extends F> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (F feature : features){
            if (addFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllFeatures(Collection<? extends F> features) {
        if (features == null){
            return false;
        }

        boolean removed = false;
        for (F feature : features){
            if (removeFeature(feature)){
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Participant)){
            return false;
        }

        // use UnambiguousExactParticipant comparator for equals
        return UnambiguousExactParticipantComparator.areEquals(this, (Participant) o);
    }

    @Override
    public String toString() {
        return interactor.toString() + " ( " + biologicalRole.toString() + ")";
    }

    @Override
    public int hashCode() {
        // use UnambiguousExactParticipantBase comparator for hashcode to avoid instance of calls. It is possible that
        // the method equals will return false and the hashcode will be the same but it is not a big issue
        return UnambiguousExactParticipantBaseComparator.hashCode(this);
    }
}
