package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.model.extension.factory.XmlInteractorFactory;
import psidev.psi.mi.jami.xml.model.reference.AbstractInteractorRef;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class for XmlEntity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlEntity<F extends Feature> implements ExtendedPsiXmlEntity<F>, FileSourceContext, Locatable{

    private Interactor interactor;
    private Collection<CausalRelationship> causalRelationships;
    private PsiXmlLocator sourceLocator;
    private XmlInteractorFactory interactorFactory;
    private EntityInteractorChangeListener changeListener;
    private int id;

    private JAXBFeatureWrapper<F> jaxbFeatureWrapper;

    private Stoichiometry stoichiometry;

    public AbstractXmlEntity(){
        this.interactorFactory = XmlEntryContext.getInstance().getInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor){
        if (interactor == null){
            throw new IllegalArgumentException("The interactor cannot be null.");
        }
        this.interactor = interactor;
        this.interactorFactory = XmlEntryContext.getInstance().getInteractorFactory();
    }

    public AbstractXmlEntity(Interactor interactor, Stoichiometry stoichiometry){
        this(interactor);
        setStoichiometry(stoichiometry);
        this.interactorFactory =  XmlEntryContext.getInstance().getInteractorFactory();
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

    public Collection<CausalRelationship> getCausalRelationships() {
        if (this.causalRelationships == null){
            this.causalRelationships = new ArrayList<CausalRelationship>();
        }
        return this.causalRelationships;
    }

    public Stoichiometry getStoichiometry() {
        return this.stoichiometry;
    }

    public void setStoichiometry(Integer stoichiometry) {
        if (stoichiometry == null){
            this.stoichiometry = null;
        }
        else {
            this.stoichiometry = new XmlStoichiometry(stoichiometry);
        }
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        if (stoichiometry == null){
            this.stoichiometry = null;
        }
        else {
            this.stoichiometry= stoichiometry;
        }
    }

    public EntityInteractorChangeListener getChangeListener() {
        return this.changeListener;
    }

    public void setChangeListener(EntityInteractorChangeListener listener) {
        this.changeListener = listener;
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

    public void setJAXBInteractor(XmlInteractor interactor) {
        if (interactor == null){
            this.interactor = null;
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onParticipantWithoutInteractor(null, this);
            }
        }
        else{
            this.interactor = this.interactorFactory.createInteractorFromXmlInteractorInstance(interactor);
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
        XmlEntryContext.getInstance().registerParticipant(this.id, this);
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
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
            this.sourceLocator.setObjectId(getId());
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getId());
        }
    }

    @Override
    public String toString() {
        return (getSourceLocator() != null ? "Participant: "+getSourceLocator().toString():super.toString());
    }

    protected void setFeatureWrapper(JAXBFeatureWrapper<F> jaxbFeatureWrapper) {
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

    protected void initialiseFeatureWrapper(){
        this.jaxbFeatureWrapper = new JAXBFeatureWrapper();
    }

    protected FileSourceLocator getParticipantLocator(){
        return getSourceLocator();
    }

    //////////////////////////////////////////////////////////// classes

    private class InteractorRef extends AbstractInteractorRef {
        public InteractorRef(int ref) {
            super(ref);
        }

        @Override
        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsInteractor(this.ref)){
                Interactor i = parsedObjects.getInteractor(this.ref);
                if (i != null){
                    interactor = i;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Participant Interactor Reference: "+(ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString()));
        }

        public FileSourceLocator getSourceLocator() {
            return getParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an interactor ref");
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="featureWrapper")
    public static class JAXBFeatureWrapper<F extends Feature> implements Locatable, FileSourceContext{
        private PsiXmlLocator sourceLocator;
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
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseFeatures(){
            this.features = new ArrayList<F>();
        }

        public List<F> getJAXBFeatures() {
            return this.features;
        }

        @Override
        public String toString() {
            return "Participant Feature List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
