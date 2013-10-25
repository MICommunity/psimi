package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.AbstractComplexReference;
import psidev.psi.mi.jami.xml.AbstractInteractorReference;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.extension.factory.XmlInteractorFactory;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

/**
 * Abstract class for entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlEntity<F extends Feature> implements Entity<F>, FileSourceContext, Locatable {
    private Interactor interactor;
    private CvTerm biologicalRole;
    private Collection<Annotation> annotations;
    private Stoichiometry stoichiometry;
    private CausalRelationship causalRelationship;
    private Collection<F> features;
    private PsiXmLocator sourceLocator;
    private NamesContainer namesContainer;
    private XrefContainer xrefContainer;
    private XmlInteractorFactory interactorFactory;
    private ParticipantInteractorChangeListener changeListener;
    private int id;

    private JAXBAttributeList jaxbAttributeList;
    private JAXBFeatureList jaxbFeatureList;

    public AbstractXmlEntity(){
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = CvTermUtils.createUnspecifiedRole();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, CvTerm bioRole){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = bioRole != null ? bioRole : CvTermUtils.createUnspecifiedRole();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, Stoichiometry stoichiometry){
        this(interactor);
        this.stoichiometry = stoichiometry;
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        this(interactor, bioRole);
        this.stoichiometry = stoichiometry;
        this.interactorFactory = new XmlInteractorFactory();
    }

    public Collection<Alias> getAliases() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
        }
        return this.namesContainer.getAliases();
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new XrefContainer();
        }
        return this.xrefContainer.getAllXrefs();
    }

    public Interactor getInteractor() {
        if (this.interactor == null){
            initialiseUnspecifiedInteractor();
        }
        return this.interactor;
    }

    public void setInteractor(Interactor interactor) {
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        Interactor oldInteractor = this.interactor;

        this.interactor = interactor;
        if (this.changeListener != null){
            this.changeListener.onInteractorUpdate(this, oldInteractor);
        }
    }

    public CausalRelationship getCausalRelationship() {
        return this.causalRelationship;
    }

    public void setCausalRelationship(CausalRelationship relationship) {
        this.causalRelationship = relationship;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
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

    public ParticipantInteractorChangeListener getChangeListener() {
        return this.changeListener;
    }

    public void setChangeListener(ParticipantInteractorChangeListener listener) {
        this.changeListener = listener;
    }

    /**
     * Gets the value of the biologicalRole property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getBiologicalRole() {
        return this.biologicalRole;
    }

    /**
     * Sets the value of the biologicalRole property.
     *
     * @param bioRole
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setBiologicalRole(CvTerm bioRole) {
        if (bioRole == null){
            this.biologicalRole = new XmlCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        }
        else {
            biologicalRole = bioRole;
        }
    }

    public Collection<F> getFeatures() {
        if (features == null){
            initialiseFeatures();
        }
        return this.features;
    }

    public boolean addFeature(F feature) {

        if (feature == null){
            return false;
        }

        if (getFeatures().add(feature)){
            feature.setParticipant(this);
            return true;
        }
        return false;
    }

    public boolean removeFeature(F feature) {

        if (feature == null){
            return false;
        }

        if (getFeatures().remove(feature)){
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

        boolean added = false;
        for (F feature : features){
            if (removeFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    /**
     * Gets the value of the namesContainer property.
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
     * Sets the value of the namesContainer property.
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
     *     {@link XrefContainer }
     *
     */
    public XrefContainer getJAXBXref() {
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
     *     {@link XrefContainer }
     *
     */
    public void setJAXBXref(XrefContainer value) {
        this.xrefContainer = value;
    }

    public XmlInteractor getJAXBInteractor() {
        if (this.interactor instanceof XmlInteractor){
            return (XmlInteractor)this.interactor;
        }
        return null;
    }

    public void setJAXBInteractor(XmlInteractor interactor) {
        if (interactor == null){
           this.interactor = null;
        }
        else{
            this.interactor = this.interactorFactory.createInteractorFromXmlInteractorInstance(interactor);
        }
    }

    /**
     * Gets the value of the interactionRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getJAXBInteractionRef() {
        if (interactor instanceof AbstractXmlEntity.InteractionRef){
            return ((AbstractXmlEntity.InteractionRef)interactor).getRef();
        }
        return null;
    }

    /**
     * Sets the value of the interactionRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBInteractionRef(Integer value) {
        if (value != null){
            this.interactor = new InteractionRef(value);
        }
    }

    /**
     * Gets the value of the interactorRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getJAXBInteractorRef() {
        if (interactor instanceof AbstractXmlEntity.InteractorRef){
            return ((AbstractXmlEntity.InteractorRef)interactor).getRef();
        }
        return null;
    }

    /**
     * Sets the value of the interactorRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBInteractorRef(Integer value) {
        if (value != null){
            this.interactor = new InteractorRef(value);
        }
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return
     *     possible object is
     *     {@link AbstractXmlFeature }
     *
     */
    public JAXBFeatureList<F> getJAXBFeatures() {
        return this.jaxbFeatureList;
    }

    /**
     * Sets the value of the featureList property.
     *
     * @param value
     *     allowed object is
     *     {@link AbstractXmlFeature }
     *
     */
    public void setJAXBFeatures(JAXBFeatureList<F> value) {
        this.jaxbFeatureList = value;
        if (value != null){
            this.jaxbFeatureList.parent = this;
        }
    }

    /**
     * Gets the value of the biologicalRole property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getJAXBBiologicalRole() {
        return this.biologicalRole;
    }

    /**
     * Sets the value of the biologicalRole property.
     *
     * @param bioRole
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setJAXBBiologicalRole(CvTerm bioRole) {
        setBiologicalRole(bioRole);
    }

    /**
     * Gets the value of the jaxbAttributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    public JAXBAttributeList getJAXBAttributes() {
        return this.jaxbAttributeList;
    }

    /**
     * Sets the value of the jaxbAttributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlAnnotation }
     *
     */
    public void setJAXBAttributes(JAXBAttributeList value) {
        this.jaxbAttributeList = value;
        if (value != null){
            this.jaxbAttributeList.parent = this;
        }
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
            this.sourceLocator.setObjectId(this.id);
        }
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

    @Override
    public String toString() {
        return interactor.toString() + " ( " + biologicalRole.toString() + ")" + (stoichiometry != null ? ", stoichiometry: " + stoichiometry.toString() : "");
    }

    protected void initialiseUnspecifiedInteractor() {
        this.interactor = new XmlInteractor(PsiXmlUtils.UNSPECIFIED);
    }

    protected void initialiseAnnotations() {
        if (jaxbAttributeList != null){
            this.annotations = new ArrayList<Annotation>(jaxbAttributeList);
            this.jaxbAttributeList = null;
        }else{
            this.annotations = new ArrayList<Annotation>();
        }
    }

    protected void initialiseFeatures(){
        if (jaxbFeatureList != null){
            this.features = new ArrayList<F>(jaxbFeatureList);
            this.jaxbFeatureList = null;
        }else{
            this.features = new ArrayList<F>();
        }
    }

    protected void initialiseFeaturesWith(ArrayList<F> features) {
        if (features == null){
            this.features = Collections.EMPTY_LIST;
        }
        else {
            this.features = features;
        }
    }

    protected void processAddedFeature(F feature){
        feature.setParticipant(this);
    }

    private FileSourceLocator getParticipantLocator(){
        return getSourceLocator();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate participant annotations
     */
    public static class JAXBAttributeList extends ArrayList<Annotation>{

        private AbstractXmlEntity parent;

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
            // we have stoichiometry
            if(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.COMMENT_MI, Annotation.COMMENT)
                    && annotation.getValue() != null && annotation.getValue().trim().toLowerCase().startsWith(PsiXmlUtils.STOICHIOMETRY_PREFIX)){
                String stc = annotation.getValue().substring(annotation.getValue().indexOf(PsiXmlUtils.STOICHIOMETRY_PREFIX) + PsiXmlUtils.STOICHIOMETRY_PREFIX.length()).trim();

                // we have stoichiometry range
                if (stc.contains("-") && !stc.startsWith("-")){
                    String [] stcs = stc.split("-");
                    // we recognize the stoichiometry range
                    if (stcs.length == 2){
                        try{
                            XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stcs[0]), Long.parseLong(stcs[1]));
                            s.setSourceLocator((PsiXmLocator)parent.getSourceLocator());
                            parent.stoichiometry = s;
                            return false;
                        }
                        catch (NumberFormatException e){
                            e.printStackTrace();
                            return super.add(annotation);
                        }
                    }
                    // we cannot recognize the stoichiometry range, we add that as a simple annotation
                    else {
                        return super.add(annotation);
                    }
                }
                // simple stoichiometry
                else {
                    try{
                        XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stc));
                        s.setSourceLocator((PsiXmLocator)parent.getSourceLocator());
                        parent.stoichiometry = s;
                        return false;
                    }
                    // not a number, keep the annotation as annotation
                    catch (NumberFormatException e){
                        e.printStackTrace();
                        return super.add(annotation);
                    }
                }
            }
            else{
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
            if (element != null){
                // we have stoichiometry
                if(AnnotationUtils.doesAnnotationHaveTopic(element, Annotation.COMMENT_MI, Annotation.COMMENT)
                        && element.getValue() != null && element.getValue().trim().toLowerCase().startsWith(PsiXmlUtils.STOICHIOMETRY_PREFIX)){
                    String stc = element.getValue().substring(element.getValue().indexOf(PsiXmlUtils.STOICHIOMETRY_PREFIX) + PsiXmlUtils.STOICHIOMETRY_PREFIX.length()).trim();

                    // we have stoichiometry range
                    if (stc.contains("-") && !stc.startsWith("-")){
                        String [] stcs = stc.split("-");
                        // we recognize the stoichiometry range
                        if (stcs.length == 2){
                            try{
                                XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stcs[0]), Long.parseLong(stcs[1]));
                                s.setSourceLocator((PsiXmLocator)parent.getSourceLocator());
                                parent.stoichiometry = s;
                                return false;
                            }
                            catch (NumberFormatException e){
                                e.printStackTrace();
                                super.add(index, element);
                                return true;
                            }
                        }
                        // we cannot recognize the stoichiometry range, we add that as a simple annotation
                        else {
                            super.add(index, element);
                            return true;
                        }
                    }
                    // simple stoichiometry
                    else {
                        try{
                            XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stc));
                            s.setSourceLocator((PsiXmLocator)parent.getSourceLocator());
                            parent.stoichiometry = s;
                            return false;
                        }
                        // not a number, keep the annotation as annotation
                        catch (NumberFormatException e){
                            e.printStackTrace();
                            super.add(index, element);
                            return true;
                        }
                    }
                }
                else{
                    super.add(index, element);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * The feature list used by JAXB to populate participant features
     */
    public static class JAXBFeatureList<F extends Feature> extends ArrayList<F>{
        private AbstractXmlEntity<F> parent;

        public JAXBFeatureList(){
        }

        public JAXBFeatureList(int initialCapacity) {
            super(initialCapacity);
        }

        public JAXBFeatureList(Collection<? extends F> c) {
            super(c);
        }

        @Override
        public boolean add(F feature) {
            if (feature == null){
                return false;
            }

            if (super.add(feature)){
                parent.processAddedFeature(feature);
                return true;
            }
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends F> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (F a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, F element) {
            super.add(index, element);
            element.setParticipant(parent);
        }

        @Override
        public boolean addAll(int index, Collection<? extends F> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (F a : c){
                add(newIndex, a);
                newIndex++;
                add = true;
            }
            return add;
        }
    }

    private class InteractionRef extends AbstractComplexReference{
        public InteractionRef(int ref) {
            super(ref);
        }

        @Override
        public boolean resolve(Map<Integer, Object> parsedObjects) {
            if (parsedObjects.containsKey(this.ref)){
                Object object = parsedObjects.get(this.ref);
                if (object instanceof Interactor){
                    interactor = (Interactor) object;
                    return true;
                }
                // convert interaction evidence in a complex
                if (object instanceof InteractionEvidence){
                    interactor = new XmlInteractionEvidenceWrapper((InteractionEvidence)object);
                    return true;
                }
                // set the complex
                else if (object instanceof Complex){
                    interactor = (Complex)object;
                    return true;
                }
                // wrap modelled interaction
                else if (object instanceof ModelledInteraction){
                    interactor = new XmlModelledInteractionWrapper((ModelledInteraction)object);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Interaction reference: "+ref+" in participant "+(getParticipantLocator() != null? getParticipantLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return getParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an interaction ref");
        }
    }

    private class InteractorRef extends AbstractInteractorReference{
        public InteractorRef(int ref) {
            super(ref);
        }

        @Override
        public boolean resolve(Map<Integer, Object> parsedObjects) {
            if (parsedObjects.containsKey(this.ref)){
                Object obj = parsedObjects.get(this.ref);
                if (obj instanceof Interactor){
                    interactor = (Interactor) obj;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Interactor reference: "+ref+" in participant "+(getParticipantLocator() != null? getParticipantLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return getParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an interactor ref");
        }
    }
}
