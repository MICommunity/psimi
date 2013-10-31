package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
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

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Abstract class for entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlEntity<F extends Feature> implements ExtendedPsi25Participant<F>, FileSourceContext, Locatable {
    private Interactor interactor;
    private CvTerm biologicalRole;
    private CausalRelationship causalRelationship;
    private PsiXmLocator sourceLocator;
    private NamesContainer namesContainer;
    private XrefContainer xrefContainer;
    private XmlInteractorFactory interactorFactory;
    private ParticipantInteractorChangeListener changeListener;
    private int id;

    private JAXBAttributeWrapper jaxbAttributeWrapper;
    private JAXBFeatureWrapper<F> jaxbFeatureWrapper;

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
        setStoichiometry(stoichiometry);
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        this(interactor, bioRole);
        setStoichiometry(stoichiometry);
        this.interactorFactory = new XmlInteractorFactory();
    }

    @Override
    public String getShortLabel() {
        return this.namesContainer != null ? this.namesContainer.getShortLabel():null;
    }

    @Override
    public void setShortLabel(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setShortLabel(name);
    }

    @Override
    public String getFullName() {
        return this.namesContainer != null ? this.namesContainer.getFullName():null;
    }

    @Override
    public void setFullName(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setFullName(name);
    }

    public List<Alias> getAliases() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
        }
        return this.namesContainer.getAliases();
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new XrefContainer();
        }
        return this.xrefContainer.getXrefs();
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
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public Stoichiometry getStoichiometry() {
        return this.jaxbAttributeWrapper != null ? this.jaxbAttributeWrapper.stoichiometry : null;
    }

    public void setStoichiometry(Integer stoichiometry) {
        if (stoichiometry == null){
            if (this.jaxbAttributeWrapper != null){
                this.jaxbAttributeWrapper.stoichiometry = null;
            }
        }
        else {
            if (this.jaxbAttributeWrapper != null){
                initialiseAnnotationWrapper();
            }
            this.jaxbAttributeWrapper.stoichiometry = new DefaultStoichiometry(stoichiometry, stoichiometry);
        }
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        if (stoichiometry == null){
            if (this.jaxbAttributeWrapper != null){
                this.jaxbAttributeWrapper.stoichiometry = null;
            }
        }
        else {
            if (this.jaxbAttributeWrapper != null){
                initialiseAnnotationWrapper();
            }
            this.jaxbAttributeWrapper.stoichiometry = stoichiometry;
        }
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
        if (jaxbFeatureWrapper == null){
            initialiseFeatureWrapper();
        }
        return this.jaxbFeatureWrapper.features;
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

    public void setJAXBInteractor(XmlInteractor interactor) {
        if (interactor == null){
           this.interactor = null;
        }
        else{
            this.interactor = this.interactorFactory.createInteractorFromXmlInteractorInstance(interactor);
        }
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
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
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
        return interactor.toString() + " ( " + biologicalRole.toString() + ")" + (jaxbAttributeWrapper != null && jaxbAttributeWrapper.stoichiometry != null ? ", stoichiometry: " + jaxbAttributeWrapper.stoichiometry.toString() : "");
    }

    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        this.jaxbAttributeWrapper = jaxbAttributeWrapper;
    }

    public void setFeatureWrapper(JAXBFeatureWrapper<F> jaxbFeatureWrapper) {
        this.jaxbFeatureWrapper = jaxbFeatureWrapper;
        // initialise all features because of back references
        if (this.jaxbFeatureWrapper != null && !this.jaxbFeatureWrapper.features.isEmpty()){
            for (F feature : this.jaxbFeatureWrapper.features){
                processAddedFeature(feature);
            }
        }
    }

    protected void processAddedFeature(F feature){
        feature.setParticipant(this);
    }

    protected void initialiseUnspecifiedInteractor() {
        this.interactor = new XmlInteractor(PsiXmlUtils.UNSPECIFIED);
    }

    protected void initialiseAnnotationWrapper() {
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected void initialiseFeatureWrapper(){
        this.jaxbFeatureWrapper = new JAXBFeatureWrapper();
    }

    private FileSourceLocator getParticipantLocator(){
        return getSourceLocator();
    }

    //////////////////////////////////////////////////////////// classes

    private class InteractionRef extends AbstractComplexReference{
        public InteractionRef(int ref) {
            super(ref);
        }

        @Override
        public boolean resolve(Map<Integer, Object> parsedObjects) {
            // first check if complex not already loaded before
            Map<Integer, Complex> resolvedComplexes = XmlEntryContext.getInstance().getMapOfReferencedComplexes();
            if (resolvedComplexes.containsKey(this.ref)){
                interactor = resolvedComplexes.get(this.ref);
                return true;
            }
            // then take it from existing references
            if (parsedObjects.containsKey(this.ref)){
                Object object = parsedObjects.get(this.ref);
                if (object instanceof Interactor){
                    interactor = (Interactor) object;
                    return true;
                }
                // convert interaction evidence in a complex
                if (object instanceof XmlInteractionEvidence){
                    interactor = new XmlInteractionEvidenceComplexWrapper((XmlInteractionEvidence)object);
                    return true;
                }
                // set the complex
                else if (object instanceof Complex){
                    interactor = (Complex)object;
                    return true;
                }
                // wrap modelled interaction
                else if (object instanceof XmlModelledInteraction){
                    interactor = new XmlModelledInteractionComplexWrapper((XmlModelledInteraction)object);
                    return true;
                }
                // wrap basic interaction
                else if (object instanceof XmlBasicInteraction){
                    interactor = new XmlBasicInteractionComplexWrapper((XmlBasicInteraction)object);
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

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="entityAttributeWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Annotation> annotations;
        private JAXBAttributeList jaxbAttributeList;
        private Stoichiometry stoichiometry;

        public JAXBAttributeWrapper(){
            initialiseAnnotations();
            this.stoichiometry = null;
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseAnnotations(){
            annotations = new ArrayList<Annotation>();
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            if (this.jaxbAttributeList == null){
                this.jaxbAttributeList = new JAXBAttributeList();
            }
            return this.jaxbAttributeList;
        }

        private class JAXBAttributeList extends ArrayList<Annotation> {

            public JAXBAttributeList(){
                super();
                annotations = new ArrayList<Annotation>();
            }

            @Override
            public boolean add(Annotation annotation) {
                if (annotation == null){
                    return false;
                }
                return processAnnotation(null, annotation);
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
                processAnnotation(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends Annotation> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (Annotation a : c){
                    if (processAnnotation(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            protected boolean addAnnotation(Integer index, Annotation annotation) {
                if (index == null){
                    return annotations.add(annotation);
                }
                ((List<Annotation>)annotations).add(index, annotation);
                return true;
            }

            private boolean processAnnotation(Integer index, Annotation annotation) {
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
                                XmlStoichiometry s = new XmlStoichiometry((long)Double.parseDouble(stc));
                                s.setSourceLocator(sourceLocator);
                                stoichiometry = s;
                                return false;
                            }
                            catch (NumberFormatException e){
                                e.printStackTrace();
                                return addAnnotation(index, annotation);
                            }
                        }
                        // we cannot recognize the stoichiometry range, we add that as a simple annotation
                        else {
                            return addAnnotation(index, annotation);
                        }
                    }
                    // simple stoichiometry
                    else {
                        try{
                            XmlStoichiometry s = new XmlStoichiometry((long)Double.parseDouble(stc));
                            s.setSourceLocator(sourceLocator);
                            stoichiometry = s;
                            return false;
                        }
                        // not a number, keep the annotation as annotation
                        catch (NumberFormatException e){
                            e.printStackTrace();
                            return addAnnotation(index, annotation);
                        }
                    }
                }
                else{
                    return addAnnotation(null, annotation);
                }
            }
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="featureWrapper")
    public static class JAXBFeatureWrapper<F extends Feature> implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<F> features;

        public JAXBFeatureWrapper(){
            initialiseFeatures();
        }

        public JAXBFeatureWrapper(List<F> features){
            this.features = features;
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseFeatures(){
            this.features = new ArrayList<F>();
        }

        public List<F> getJAXBFeatures() {
            return this.features;
        }
    }
}
