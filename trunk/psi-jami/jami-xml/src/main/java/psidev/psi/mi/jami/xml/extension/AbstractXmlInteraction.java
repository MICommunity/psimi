package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Abstract class for xml interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlInteraction<T extends Participant> implements Interaction<T>, FileSourceContext, Locatable{

    private NamesContainer namesContainer;
    private InteractionXrefContainer xrefContainer;
    private ArrayList<InferredInteraction> inferredInteractions;
    private Boolean intraMolecular;
    private int id;
    private ArrayList<CvTerm> interactionTypes;
    private Checksum rigid;
    private Collection<Checksum> checksums;
    private Collection<Annotation> annotations;
    private Date updatedDate;
    private Date createdDate;
    private Collection<T> participants;
    private PsiXmLocator sourceLocator;
    private XmlEntry entry;

    private JAXBAttributeList jaxbAttributeList;
    private JAXBParticipantList jaxbParticipantList;

    public AbstractXmlInteraction(){
        XmlEntryContext context = XmlEntryContext.getInstance();
        this.entry = context.getCurrentEntry();
    }

    public AbstractXmlInteraction(String shortName){
        XmlEntryContext context = XmlEntryContext.getInstance();
        this.entry = context.getCurrentEntry();
        setShortName(shortName);
    }

    public AbstractXmlInteraction(String shortName, CvTerm type){
        this(shortName);
        setInteractionType(type);
    }

    public String getShortName() {
        return this.namesContainer != null ? this.namesContainer.getShortLabel() : null;
    }

    public void setShortName(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setShortLabel(name);
    }

    public String getRigid() {
        return this.rigid != null ? this.rigid.getValue() : null;
    }

    public void setRigid(String rigid) {
        Collection<Checksum> checksums = getChecksums();
        if (rigid != null){
            CvTerm rigidMethod = CvTermUtils.createRigid();
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
        if (xrefContainer == null){
            xrefContainer = new InteractionXrefContainer();
        }
        return this.xrefContainer.getAllIdentifiers();
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new InteractionXrefContainer();
        }
        return this.xrefContainer.getAllXrefs();
    }

    public Collection<Checksum> getChecksums() {
        if (checksums == null){
            initialiseChecksums();
        }
        return this.checksums;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
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

    public CvTerm getInteractionType() {
        return (this.interactionTypes != null && !this.interactionTypes.isEmpty())? this.interactionTypes.iterator().next() : null;
    }

    public void setInteractionType(CvTerm term) {
        if (this.interactionTypes == null && term != null){
            this.interactionTypes = new ArrayList<CvTerm>();
            this.interactionTypes.add(term);
        }
        else if (this.interactionTypes != null){
            if (!this.interactionTypes.isEmpty() && term == null){
                this.interactionTypes.remove(0);
            }
            else if (term != null){
                this.interactionTypes.remove(0);
                this.interactionTypes.add(0, term);
            }
        }
    }

    public Collection<T> getParticipants() {
        if (participants == null){
            initialiseParticipants();
        }
        return participants;
    }

    public boolean addParticipant(T part) {
        if (part == null){
            return false;
        }
        if (getParticipants().add(part)){
            part.setInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeParticipant(T part) {
        if (part == null){
            return false;
        }
        if (getParticipants().remove(part)){
            part.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllParticipants(Collection<? extends T> participants) {
        if (participants == null){
            return false;
        }

        boolean added = false;
        for (T p : participants){
            if (addParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllParticipants(Collection<? extends T> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (T p : participants){
            if (removeParticipant(p)){
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    public NamesContainer getJAXBNames() {
        if (namesContainer != null && namesContainer.isEmpty()){
            return null;
        }
        return namesContainer;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    public void setJAXBNames(NamesContainer value) {
        this.namesContainer = value;
    }

    /**
     * Gets the value of the xrefContainer property.
     *
     * @return
     *     possible object is
     *     {@link InteractorXrefContainer }
     *
     */
    public InteractionXrefContainer getJAXBXref() {
        if (xrefContainer != null && xrefContainer.isEmpty()){
            return null;
        }
        return xrefContainer;
    }

    /**
     * Sets the value of the xrefContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link InteractorXrefContainer }
     *
     */
    public void setJAXBXref(InteractionXrefContainer value) {
        this.xrefContainer = value;
    }

    /**
     * Gets the value of the inferredInteractionList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<InferredInteraction> }
     *
     */
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return inferredInteractions;
    }

    /**
     * Sets the value of the inferredInteractionList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<InferredInteraction> }
     *
     */
    public void setJAXBInferredInteractions(ArrayList<InferredInteraction> value) {
        this.inferredInteractions = value;
    }

    /**
     * Gets the value of the intraMolecular property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getJAXBIntraMolecular() {
        return intraMolecular;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setJAXBIntraMolecular(Boolean value) {
        this.intraMolecular = value;
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Annotation> }
     *
     */
    public JAXBAttributeList getJAXBAttributes() {
        return jaxbAttributeList;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Annotation>  }
     *
     */
    public void setJAXBAttributes(JAXBAttributeList  value) {
        this.jaxbAttributeList = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    public int getJAXBId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setJAXBId(int value) {
        this.id = value;
        XmlEntryContext.getInstance().getMapOfReferencedObjects().put(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    /**
     * Gets the value of the interactionTypeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public ArrayList<CvTerm> getJAXBInteractionTypes() {
        return this.interactionTypes;
    }

    /**
     * Sets the value of the interactionTypeList property.
     *
     * @param value
     *     allowed object is
     *     {@link HostOrganism }
     *
     */
    public void setJAXBInteractionTypes(ArrayList<CvTerm> value) {
        this.interactionTypes = value;
    }

    /**
     * Gets the value of the participantList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Participant> }
     *
     */
    public JAXBParticipantList getJAXBParticipants() {
        return jaxbParticipantList;
    }

    public void setJAXBParticipants(JAXBParticipantList jaxbParticipantList) {
        this.jaxbParticipantList = jaxbParticipantList;
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = (PsiXmLocator)sourceLocator;
    }

    public XmlEntry getEntry() {
        return entry;
    }

    public void setEntry(XmlEntry entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return (getShortName() != null ? getShortName()+", " : "") + (getInteractionType() != null ? getInteractionType().toString() : "");
    }

    public void setJAXBIdOnly(int value) {
        this.id = value;
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

    protected void initialiseAnnotations(){
        if (jaxbAttributeList != null){
            this.annotations = new ArrayList<Annotation>(jaxbAttributeList);
            this.jaxbAttributeList = null;
        }else{
            this.annotations = new ArrayList<Annotation>();
        }
    }

    protected void initialiseParticipants(){
        if (jaxbParticipantList != null){
            this.participants = new ArrayList<T>(jaxbParticipantList);
            this.jaxbParticipantList = null;
        }else{
            this.participants = new ArrayList<T>();
        }
    }

    protected void initialiseChecksums(){
        this.checksums = new InteractionChecksumList();
    }

    protected void processAddedParticipant(T participant) {
        participant.setInteraction(getCurrentInstance());
    }

    private AbstractXmlInteraction<T> getCurrentInstance(){
        return this;
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate interaction annotations
     */
    public class JAXBAttributeList extends ArrayList<Annotation>{

        public JAXBAttributeList(){
        }

        public JAXBAttributeList(int initialCapacity) {
            super(initialCapacity);
        }

        public JAXBAttributeList(Collection<? extends Annotation> c) {
            super(c);
        }

        @Override
        public boolean add(Annotation annotation) {
            if (annotation == null){
                return false;
            }
            if (AnnotationUtils.doesAnnotationHaveTopic(annotation, Checksum.CHECKSUM_MI, Checksum.CHECKUM)
                    || AnnotationUtils.doesAnnotationHaveTopic(annotation, null, Checksum.RIGID)){
                XmlChecksum checksum = new XmlChecksum(annotation.getTopic(), annotation.getValue() != null ? annotation.getValue() : PsiXmlUtils.UNSPECIFIED);
                checksum.setSourceLocator(((FileSourceContext)annotation).getSourceLocator());
                getChecksums().add(checksum);
                return false;
            }
            else {
                return super.add(annotation);
            }
        }

        @Override
        public boolean addAll(Collection<? extends Annotation> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Annotation a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, Annotation element) {
            addToSpecificIndex(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Annotation> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (Annotation a : c){
                if (addToSpecificIndex(newIndex, a)){
                    newIndex++;
                    add = true;
                }
            }
            return add;
        }

        private boolean addToSpecificIndex(int index, Annotation element) {
            if (element == null){
                return false;
            }
            if (AnnotationUtils.doesAnnotationHaveTopic(element, Checksum.CHECKSUM_MI, Checksum.CHECKUM)
                    || AnnotationUtils.doesAnnotationHaveTopic(element, null, Checksum.RIGID)){
                XmlChecksum checksum = new XmlChecksum(element.getTopic(), element.getValue() != null ? element.getValue() : PsiXmlUtils.UNSPECIFIED);
                checksum.setSourceLocator((PsiXmLocator)((FileSourceContext)element).getSourceLocator());
                getChecksums().add(checksum);
                return false;
            }
            else {
                super.add(index, element);
                return true;
            }
        }
    }

    /**
     * The participant list used by JAXB to populate interaction participants
     */
    public class JAXBParticipantList extends ArrayList<T>{

        public JAXBParticipantList(){
        }

        public JAXBParticipantList(int initialCapacity) {
            super(initialCapacity);
        }

        public JAXBParticipantList(Collection<? extends T> c) {
            super(c);
        }

        @Override
        public boolean add(T participant) {
            if (participant == null){
                return false;
            }

            if (super.add(participant)){
                processAddedParticipant(participant);
                return true;
            }
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (T a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, T element) {
            super.add(index, element);
            processAddedParticipant(element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends T> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (T a : c){
                add(newIndex, a);
                newIndex++;
                add = true;
            }
            return add;
        }
    }

    private class InteractionChecksumList extends AbstractListHavingProperties<Checksum> {
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
