package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultFeature<T extends Feature, P extends Participant> implements Feature<T, P>, Serializable{

    private String shortName;
    private String fullName;
    private Set<ExternalIdentifier> identifiers;
    private Set<Xref> xrefs;
    private Set<Annotation> annotations;
    private CvTerm type;
    private Collection<Range> ranges;
    private Collection<T> bindingFeatures;
    private P participant;

    public DefaultFeature(P participant){
        if (participant == null){
            throw new IllegalArgumentException("The participant cannot be null");
        }
        this.participant = participant;

        this.identifiers = new HashSet<ExternalIdentifier>();
        this.annotations = new HashSet<Annotation>();
        this.xrefs = new HashSet<Xref>();
        this.ranges = new ArrayList<Range>();
        this.bindingFeatures = new ArrayList<T>();
    }

    public DefaultFeature(P participant, String shortName, String fullName){
        this(participant);
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public DefaultFeature(P participant, CvTerm type){
        this(participant);
        this.type = type;
    }

    public DefaultFeature(P participant, String shortName, String fullName, CvTerm type){
        this(participant, shortName, fullName);
        this.type =type;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public Set<ExternalIdentifier> getIdentifiers() {
        return this.identifiers;
    }

    public Set<Xref> getXrefs() {
        return this.xrefs;
    }

    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm type) {
        this.type = type;
    }

    public Collection<Range> getRanges() {
        return this.ranges;
    }

    public Collection<T> getBindingFeatures() {
        return this.bindingFeatures;
    }

    public P getParticipant() {
        return this.participant;
    }

    public void setParticipant(P participant) {
        if (participant == null){
            throw new IllegalArgumentException("The participant cannot be null");
        }
        this.participant = participant;
    }

    @Override
    public int hashCode() {
        // use UnambiguousFeatureBase comparator for hashcode to avoid instance of calls. It is possible that
        // the method equals will return false and the hashcode will be the same but it is not a big issue
        return UnambiguousFeatureBaseComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Feature)){
            return false;
        }

        // use UnambiguousFeature comparator for equals
        return UnambiguousFeatureComparator.areEquals(this, (Feature) o);
    }

    @Override
    public String toString() {
        return type != null ? type.toString() : (!ranges.isEmpty() ? "("+ranges.iterator().next().toString()+"...)" : " (-)");
    }
}
