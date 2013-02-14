package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultFeature<T extends Feature, P extends Participant> implements Feature<T, P>, Serializable{

    protected String shortName;
    protected String fullName;
    protected Xref interpro;
    protected Collection<Xref> identifiers;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected CvTerm type;
    protected Collection<Range> ranges;
    protected Collection<T> bindingFeatures;
    protected P participant;

    public DefaultFeature(){

        initializeIdentifiers();
        initializeAnnotations();
        initializeXrefs();
        initializeRanges();
        initializeBindingFeatures();
    }

    public DefaultFeature(String shortName, String fullName){
        this();
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public DefaultFeature(CvTerm type){
        this();
        this.type = type;
    }

    public DefaultFeature(String shortName, String fullName, CvTerm type){
        this(shortName, fullName);
        this.type =type;
    }

    public DefaultFeature(P participant){
        this.participant = participant;

        initializeIdentifiers();
        initializeAnnotations();
        initializeXrefs();
        initializeRanges();
        initializeBindingFeatures();
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

    protected void initializeIdentifiers(){
        this.identifiers = new FeatureIdentifierList();
    }

    protected void initializeAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initializeXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initializeRanges(){
        this.ranges = new ArrayList<Range>();
    }

    protected void initializeBindingFeatures(){
        this.bindingFeatures = new ArrayList<T>();
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

    public String getInterpro() {
        return this.interpro != null ? this.interpro.getId() : null;
    }

    public void setInterpro(String interpro) {
        // add new interpro if not null
        if (interpro != null){
            CvTerm interproDatabase = CvTermFactory.createInterproDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old chebi if not null
            if (this.interpro != null){
                identifiers.remove(this.interpro);
            }
            this.interpro = new DefaultXref(interproDatabase, interpro, identityQualifier);
            this.identifiers.add(this.interpro);
        }
        // remove all interpro if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(xrefs, Xref.INTERPRO_MI, Xref.INTERPRO);
            this.interpro = null;
        }
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
        this.participant = participant;
    }

    public void setParticipantAndAddFeature(P participant) {
        this.participant = participant;

        if (participant != null){
            this.participant.getFeatures().add(this);
        }
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

    protected class FeatureIdentifierList extends AbstractListHavingPoperties<Xref> {
        public FeatureIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            // the added identifier is interpro and it is not the current interpro identifier
            if (interpro != added && XrefUtils.isXrefFromDatabase(added, Xref.INTERPRO_MI, Xref.INTERPRO)){
                // the current interpro identifier is not identity, we may want to set interpro Identifier
                if (!XrefUtils.doesXrefHaveQualifier(interpro, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    // the interpro identifier is not set, we can set the interpro identifier
                    if (interpro == null){
                        interpro = added;
                    }
                    else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        interpro = added;
                    }
                    // the added xref is secondary object and the current interpro identifier is not a secondary object, we reset interpro identifier
                    else if (!XrefUtils.doesXrefHaveQualifier(interpro, Xref.SECONDARY_MI, Xref.SECONDARY)
                            && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                        interpro = added;
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (interpro == removed){
                interpro = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.INTERPRO_MI, Xref.INTERPRO);
            }
        }

        @Override
        protected void clearProperties() {
            interpro = null;
        }
    }
}
