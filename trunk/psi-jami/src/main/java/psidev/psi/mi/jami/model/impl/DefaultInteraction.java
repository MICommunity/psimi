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
import java.util.Collections;
import java.util.Date;

/**
 * Default implementation for interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteraction<P extends Participant> implements Interaction, Serializable {

    private String shortName;
    private Checksum rigid;
    private Collection<Checksum> checksums;
    private Collection<Xref> identifiers;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private boolean isNegative;
    private Date updatedDate;
    private Date createdDate;
    private Collection<Confidence> confidences;
    private CvTerm type;

    public DefaultInteraction(){
    }

    public DefaultInteraction(String shortName){
        this();
        this.shortName = shortName;
    }

    public DefaultInteraction(String shortName, CvTerm type){
        this(shortName);
        this.type = type;
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseIdentifiers(){
        this.identifiers = new ArrayList<Xref>();
    }

    protected void initialiseChecksum(){
        this.checksums = new InteractionChecksumList();
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs){
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else {
            this.xrefs = xrefs;
        }
    }

    protected void initialiseConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
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

    protected void initialiseChecksumWith(Collection<Checksum> checksums){
        if (checksums == null){
            this.checksums = Collections.EMPTY_LIST;
        }
        else {
            this.checksums = checksums;
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
        Collection<Checksum> checksums = getChecksums();
        if (rigid != null){
            CvTerm rigidMethod = CvTermFactory.createRigid();
            // first remove old rigid
            if (this.rigid != null){
                checksums.remove(this.rigid);
            }
            this.rigid = new DefaultChecksum(rigidMethod, rigid);
            checksums.add(this.rigid);
        }
        // remove all smiles if the collection is not empty
        else if (!checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksums, Checksum.RIGID_MI, Checksum.RIGID);
            this.rigid = null;
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

    public Collection<Checksum> getChecksums() {
        if (checksums == null){
           initialiseChecksum();
        }
        return this.checksums;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
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
        if (confidences == null){
            initialiseConfidences();
        }
        return this.confidences;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm term) {
       this.type = term;
    }

    protected void processAddedChecksumEvent(Checksum added) {
        if (rigid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.RIGID_MI, Checksum.RIGID)){
            // the rigid is not set, we can set the rigid
            rigid = added;
        }
    }

    protected void processRemovedChecksumEvent(Checksum removed) {
        if (rigid == removed){
            rigid = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.RIGID_MI, Checksum.RIGID);
        }
    }

    protected void clearPropertiesLinkedToChecksums() {
        rigid = null;
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
            processAddedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToChecksums();
        }
    }
}
