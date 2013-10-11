package psidev.psi.mi.jami.xml.extension;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Abstract class for entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/07/13</pre>
 */
@XmlTransient
public class AbstractXmlEntity<F extends Feature> implements Entity<F>, FileSourceContext {
    private Interactor interactor;
    private CvTerm biologicalRole;
    private Collection<Annotation> annotations;
    private Stoichiometry stoichiometry;
    private CausalRelationship causalRelationship;
    private Collection<F> features;

    private Map<Integer, Object> mapOfReferencedObjects;

    private PsiXmLocator sourceLocator;

    private NamesContainer namesContainer;
    private XrefContainer xrefContainer;

    private Integer interactionRef;
    private Integer interactorRef;
    private XmlInteractor xmlInteractor;
    private XmlInteractorFactory interactorFactory;
    private ParticipantInteractorChangeListener changeListener;
    private int id;

    public AbstractXmlEntity(){
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = CvTermUtils.createUnspecifiedRole();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, CvTerm bioRole){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.biologicalRole = bioRole != null ? bioRole : CvTermUtils.createUnspecifiedRole();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, Stoichiometry stoichiometry){
        this(interactor);
        this.stoichiometry = stoichiometry;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        this(interactor, bioRole);
        this.stoichiometry = stoichiometry;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        this.interactorFactory = new XmlInteractorFactory();
    }

    public Collection<Alias> getAliases() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
        }
        return this.namesContainer.getJAXBAliases();
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new XrefContainer();
        }
        return this.xrefContainer.getAllXrefs();
    }

    public Interactor getInteractor() {
        if (this.interactor == null){
            if (this.interactorRef != null && this.mapOfReferencedObjects.containsKey(this.interactorRef)){
                Object object = this.mapOfReferencedObjects.get(this.interactorRef);
                if (object instanceof Interactor){
                    this.interactor = (Interactor) object;
                }
                else {
                    initialiseUnspecifiedInteractor();
                }
            }
            else if (this.interactionRef != null && this.mapOfReferencedObjects.containsKey(this.interactionRef)){
                Object object = this.mapOfReferencedObjects.get(this.interactionRef);
                if (object instanceof Interactor){
                    this.interactor = (Interactor) object;
                }
                // convert interaction evidence in a complex
                else if (object instanceof InteractionEvidence){
                    this.interactor = new XmlInteractionEvidenceWrapper((InteractionEvidence)object);
                }
                // set the complex
                else if (object instanceof Complex){
                    this.interactor = (Complex)object;
                }
                // wrap modelled interaction
                else if (object instanceof ModelledInteraction){
                    this.interactor = new XmlModelledInteractionWrapper((ModelledInteraction)object);
                }
                else {
                    this.interactor = new XmlComplex(PsiXmlUtils.UNSPECIFIED);
                }
            }
            else {
                initialiseUnspecifiedInteractor();
            }
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
        return this.xmlInteractor;
    }

    public void setJAXBInteractor(XmlInteractor interactor) {
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = this.interactorFactory.createInteractorFromInteractorType(interactor.getJAXBInteractorType(), interactor.getShortName());
        Xref primary = interactor.getPreferredIdentifier();
        if (this.interactor == null && primary != null){
            this.interactor = this.interactorFactory.createInteractorFromDatabase(primary.getDatabase(), interactor.getShortName());
        }
        else{
            this.interactor = interactor;
        }
        this.xmlInteractor = (XmlInteractor) this.interactor;
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
        return interactionRef;
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
        this.interactionRef = value;
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
        return interactorRef;
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
        this.interactorRef = value;
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return
     *     possible object is
     *     {@link AbstractXmlFeature }
     *
     */
    public ArrayList<F> getJAXBFeatures() {
        if (this.features == null || (this.features != null && this.features.isEmpty())){
            return null;
        }
        return new ArrayList<F>(this.features);
    }

    /**
     * Sets the value of the featureList property.
     *
     * @param value
     *     allowed object is
     *     {@link AbstractXmlFeature }
     *
     */
    public void setJAXBFeatures(ArrayList<F> value) {
        removeAllFeatures(getFeatures());
        if (value != null && !value.isEmpty()){
            addAllFeatures(value);
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
    public void setJAXBBiologicalRole(XmlCvTerm bioRole) {
        setBiologicalRole(bioRole);
    }

    protected void initialiseAnnotations() {
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseFeatures(){
        this.features = new ArrayList<F>();
    }

    protected void initialiseFeaturesWith(Collection<F> features) {
        if (features == null){
            this.features = Collections.EMPTY_LIST;
        }
        else {
            this.features = features;
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

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    public ArrayList<Annotation> getJAXBAttributes() {
        if (this.annotations == null || (this.annotations != null && this.annotations.isEmpty())){
            return null;
        }

        ArrayList<Annotation> annots = new ArrayList<Annotation>(this.annotations);

        if (stoichiometry != null){
            if (stoichiometry.getMaxValue() == stoichiometry.getMinValue()){
                annots.add(new XmlAnnotation(new XmlCvTerm(Annotation.COMMENT, Annotation.COMMENT_MI), Long.toString(stoichiometry.getMinValue())));
            }
            else {
                annots.add(new XmlAnnotation(new XmlCvTerm(Annotation.COMMENT, Annotation.COMMENT_MI), Long.toString(stoichiometry.getMinValue()) + "-" + Long.toString(stoichiometry.getMaxValue())));
            }
        }
        return annots;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlAnnotation }
     *
     */
    public void setJAXBAttributes(ArrayList<XmlAnnotation> value) {
        getAnnotations().clear();
        if (value != null && !value.isEmpty()){
            for (Annotation annot : annotations){
                // we have stoichiometry
                if(AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.COMMENT_MI, Annotation.COMMENT)
                        && annot.getValue() != null && annot.getValue().trim().toLowerCase().startsWith(PsiXmlUtils.STOICHIOMETRY_PREFIX)){
                    String stc = annot.getValue().substring(annot.getValue().indexOf(PsiXmlUtils.STOICHIOMETRY_PREFIX) + PsiXmlUtils.STOICHIOMETRY_PREFIX.length()).trim();

                    // we have stoichiometry range
                    if (stc.contains("-") && !stc.startsWith("-")){
                        String [] stcs = stc.split("-");
                        // we recognize the stoichiometry range
                        if (stcs.length == 2){
                            try{
                                this.stoichiometry = new XmlStoichiometry(Long.parseLong(stcs[0]), Long.parseLong(stcs[1]));
                            }
                            catch (NumberFormatException e){
                                e.printStackTrace();
                                getAnnotations().add(annot);
                            }
                        }
                        // we cannot recognize the stoichiometry range, we add that as a simple annotation
                        else {
                            getAnnotations().add(annot);
                        }
                    }
                    // simple stoichiometry
                    else {
                        try{
                            this.stoichiometry = new XmlStoichiometry(Long.parseLong(stc));
                        }
                        // not a number, keep the annotation as annotation
                        catch (NumberFormatException e){
                            e.printStackTrace();
                            getAnnotations().add(annot);
                        }
                    }
                }
                else{
                    getAnnotations().add(annot);
                }
            }
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
        this.mapOfReferencedObjects.put(this.id, this);
        if (sourceLocator != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), this.id);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), this.id);
    }

    @Override
    public String toString() {
        return interactor.toString() + " ( " + biologicalRole.toString() + ")" + (stoichiometry != null ? ", stoichiometry: " + stoichiometry.toString() : "");
    }

    protected void initialiseUnspecifiedInteractor() {
        this.interactor = new XmlInteractor(PsiXmlUtils.UNSPECIFIED);
    }
}
