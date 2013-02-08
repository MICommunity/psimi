package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionComparator;

import java.io.Serializable;
import java.util.*;

/**
 * Default implementation for interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteraction<P extends Participant> implements Interaction<P>, Serializable {

    protected String shortName;
    protected Collection<Xref> identifiers;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected Collection<P> participants;
    protected Source source;
    protected boolean isNegative;
    protected Date updatedDate;
    protected Collection<Confidence> confidences;
    protected CvTerm type;

    public DefaultInteraction(){
        initializeXrefs();
        initializeAnnotations();
        this.participants = new ArrayList<P>();
        this.confidences = new ArrayList<Confidence>();
        this.identifiers = new ArrayList<Xref>();
    }

    public DefaultInteraction(String shortName){
        this();
        this.shortName = shortName;
    }

    public DefaultInteraction(String shortName, Source source){
        this(shortName);
        this.source = source;
    }

    public DefaultInteraction(String shortName, CvTerm type){
        this(shortName);
        this.type = type;
    }

    public DefaultInteraction(String shortName, Source source, CvTerm type){
        this(shortName, source);
        this.type = type;
    }

    protected void initializeAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initializeXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }


    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    public Collection<Xref> getIdentifiers() {
        return this.identifiers;
    }

    public Collection<Xref> getXrefs() {
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<P> getParticipants() {
        return this.participants;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updated) {
        this.updatedDate = updated;
    }

    public Collection<Confidence> getConfidences() {
        return this.confidences;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm term) {
       this.type = term;
    }

    @Override
    public int hashCode() {
        // use UnambiguousExactInteractionBase comparator for hashcode
        return UnambiguousExactInteractionBaseComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Interaction)){
            return false;
        }

        // use UnambiguousExactInteraction comparator for equals
        return UnambiguousExactInteractionComparator.areEquals(this, (Interaction) o);
    }

    @Override
    public String toString() {
        return (shortName != null ? shortName+", " : "") + type != null ? type.toString() : "" + ", negative = " + isNegative;
    }
}
