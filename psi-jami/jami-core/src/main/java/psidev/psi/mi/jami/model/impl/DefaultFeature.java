package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultFeature<T extends Feature> implements Feature, Serializable{

    private String shortName;
    private String fullName;
    private Xref interpro;
    private Collection<Xref> identifiers;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm type;
    private Collection<Range> ranges;

    private CvTerm interactionEffect;
    private CvTerm interactionDependency;

    public DefaultFeature(){
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

    protected void initialiseIdentifiers(){
        this.identifiers = new FeatureIdentifierList();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseRanges(){
        this.ranges = new ArrayList<Range>();
    }

    protected void initialiseIdentifiersWith(Collection<Xref> identifiers){
        if (identifiers == null){
            this.identifiers = Collections.EMPTY_LIST;
        }
        else {
            this.identifiers = identifiers;
        }
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

    protected void initialiseRangesWith(Collection<Range> ranges){
        if (ranges == null){
            this.ranges = Collections.EMPTY_LIST;
        }
        else {
            this.ranges = ranges;
        }
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
        Collection<Xref> featureIdentifiers = getIdentifiers();

        // add new interpro if not null
        if (interpro != null){
            CvTerm interproDatabase = CvTermUtils.createInterproDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old chebi if not null
            if (this.interpro != null){
                featureIdentifiers.remove(this.interpro);
            }
            this.interpro = new DefaultXref(interproDatabase, interpro, identityQualifier);
            featureIdentifiers.add(this.interpro);
        }
        // remove all interpro if the collection is not empty
        else if (!featureIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(featureIdentifiers, Xref.INTERPRO_MI, Xref.INTERPRO);
            this.interpro = null;
        }
    }

    public Collection<Xref> getIdentifiers() {
        if (identifiers == null){
            initialiseIdentifiers();
        }
        return this.identifiers;
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

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm type) {
        this.type = type;
    }

    public Collection<Range> getRanges() {
        if (ranges == null){
            initialiseRanges();
        }
        return this.ranges;
    }

    public CvTerm getInteractionEffect() {
        return this.interactionEffect;
    }

    public void setInteractionEffect(CvTerm effect) {
        this.interactionEffect = effect;
    }

    public CvTerm getInteractionDependency() {
        return this.interactionDependency;
    }

    public void setInteractionDependency(CvTerm interactionDependency) {
        this.interactionDependency = interactionDependency;
    }

    protected void processAddedIdentifierEvent(Xref added) {
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

    protected void processRemovedIdentifierEvent(Xref removed) {
        if (interpro != null && interpro.equals(removed)){
            interpro = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.INTERPRO_MI, Xref.INTERPRO);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        interpro = null;
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
           processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();
        }
    }
}
