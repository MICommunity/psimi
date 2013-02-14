package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactInteractionComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Default implementation for interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteraction<P extends Participant> implements Interaction<P>, Serializable {

    protected String shortName;
    protected Checksum rigid;
    protected Collection<Checksum> checksums;
    protected Collection<Xref> identifiers;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected Collection<P> participants;
    protected boolean isNegative;
    protected Date updatedDate;
    protected Date createdDate;
    protected Collection<Confidence> confidences;
    protected CvTerm type;

    public DefaultInteraction(){
        initializeXrefs();
        initializeAnnotations();
        initializeParticipants();
        initializeChecksum();
        initializeConfidences();
        initializeIdentifiers();
    }

    public DefaultInteraction(String shortName){
        this();
        this.shortName = shortName;
    }

    public DefaultInteraction(String shortName, CvTerm type){
        this(shortName);
        this.type = type;
    }

    protected void initializeAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initializeXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initializeParticipants(){
        this.participants = new ArrayList<P>();
    }

    protected void initializeConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initializeIdentifiers(){
        this.identifiers = new ArrayList<Xref>();
    }

    protected void initializeChecksum(){
        this.checksums = new InteractionChecksumList();
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    public String getRigid() {
        return this.rigid != null ? this.rigid.getValue() : null;
    }

    public void setRigid(String rigid) {
        if (rigid != null){
            CvTerm rigidMethod = CvTermFactory.createRigid();
            // first remove old rigid
            if (this.rigid != null){
                this.checksums.remove(this.rigid);
            }
            this.rigid = new DefaultChecksum(rigidMethod, rigid);
            this.checksums.add(this.rigid);
        }
        // remove all smiles if the collection is not empty
        else if (!this.checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksums, Checksum.RIGID_MI, Checksum.RIGID);
            this.rigid = null;
        }
    }

    public Collection<Xref> getIdentifiers() {
        return this.identifiers;
    }

    public Collection<Xref> getXrefs() {
        return this.xrefs;
    }

    public Collection<Checksum> getChecksums() {
        return this.checksums;
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<P> getParticipants() {
        return this.participants;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date created) {
        this.createdDate = created;
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

    private class InteractionChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractionChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {
            if (rigid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.RIGID_MI, Checksum.RIGID)){
                // the rigid is not set, we can set the rigid
                rigid = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {
            if (rigid == removed){
                rigid = ChecksumUtils.collectFirstChecksumWithMethod(this, Checksum.RIGID_MI, Checksum.RIGID);
            }
        }

        @Override
        protected void clearProperties() {
            rigid = null;
        }
    }
}
