package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.model.extension.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Abstract class for XML Participant pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlParticipantPool<I extends Interaction, F extends Feature, P extends ParticipantCandidate>
        implements ExtendedPsiXmlParticipant<I,F>, ParticipantPool<I,F,P>, EntityInteractorChangeListener, FileSourceContext,
        Locatable{
    private Collection<P> candidates;
    private CvTerm type;

    private AbstractXmlParticipant<I,F> delegate;
    private InteractorPool generatedInteractor;
    private PsiXmlLocator sourceLocator;

    private I interaction;

    public AbstractXmlParticipantPool(){
        initialiseComponentCandidatesSet();
    }

    public AbstractXmlParticipantPool(AbstractXmlParticipant<I,F> delegate){
        this.delegate = delegate;
        initialiseComponentCandidatesSet();
        for (F feature : this.delegate.getFeatures()){
            feature.setParticipant(this);
        }
    }

    protected void initialiseComponentCandidatesSet() {
        this.candidates = new ArrayList<P>();
    }

    protected void initialiseComponentCandidatesSetWith(Collection<P> candidates) {
        if (candidates == null){
            this.candidates = Collections.EMPTY_LIST;
        }
        else {
            this.candidates = candidates;
        }
    }

    @Override
    public InteractorPool getInteractor() {
        if (this.generatedInteractor == null){
            this.generatedInteractor = new XmlInteractorPool(getShortName());
            ((XmlInteractorPool)this.generatedInteractor).setSourceLocator(getSourceLocator());
        }
        return this.generatedInteractor;
    }

    @Override
    public void setInteractor(Interactor interactor) {
        throw new UnsupportedOperationException("Cannot set the interactor of an ParticipantPool as it is an interactorSet that is related to the interactors in the set of entities");
    }

    public CvTerm getType() {
        return type;
    }

    /**
     * Sets the component set type.
     * Sets the type to molecule set (MI:1304) if the given type is null
     */
    public void setType(CvTerm type) {
        if (type == null){
            this.type = CvTermUtils.createMoleculeSetType();
        }
        else {
            this.type = type;
        }
        getInteractor().setInteractorType(this.type);
    }

    public int size() {
        return candidates.size();
    }

    public boolean isEmpty() {
        return candidates.isEmpty();
    }

    public boolean contains(Object o) {
        return candidates.contains(o);
    }

    public Iterator<P> iterator() {
        return candidates.iterator();
    }

    public Object[] toArray() {
        return candidates.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return candidates.toArray(ts);
    }

    public boolean add(P interactor) {
        if (candidates.add(interactor)){
            interactor.setChangeListener(this);
            getInteractor().add(interactor.getInteractor());
            interactor.setParentPool(this);
            return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (candidates.remove(o)){
            ParticipantCandidate entity = (ParticipantCandidate)o;
            entity.setChangeListener(null);
            getInteractor().remove(entity.getInteractor());
            entity.setParentPool(null);
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection<?> objects) {
        return candidates.containsAll(objects);
    }

    public boolean addAll(Collection<? extends P> interactors) {
        boolean added = this.candidates.addAll(interactors);
        if (added){
            for (P entity : this){
                entity.setChangeListener(this);
                getInteractor().add(entity.getInteractor());
                entity.setParentPool(this);
            }
        }
        return added;
    }

    public boolean retainAll(Collection<?> objects) {
        boolean retain = candidates.retainAll(objects);
        if (retain){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                interactors.add(((Participant)o).getInteractor());
            }
            getInteractor().retainAll(interactors);
        }
        return retain;
    }

    public boolean removeAll(Collection<?> objects) {
        boolean remove = candidates.removeAll(objects);
        if (remove){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                Participant entity = (Participant)o;
                entity.setChangeListener(null);
                interactors.add(entity.getInteractor());
                entity.setInteraction(null);
            }
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (P entity : this){
                interactors.remove(entity.getInteractor());
            }
            getInteractor().removeAll(interactors);
        }
        return remove;
    }

    public void clear() {
        for (P entity : this){
            entity.setChangeListener(null);
            entity.setParentPool(null);
        }
        candidates.clear();
        getInteractor().clear();
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        // check that the listener still makes sensr
        if (contains(entity)){
            boolean needsToRemoveOldInteractor = true;
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (P e : this){
                // we want to check if an interactor is the same as old interactor in another entry
                if (e != entity){
                    if (oldInteractor.equals(e.getInteractor())){
                        needsToRemoveOldInteractor = false;
                    }
                }
            }
            if (!needsToRemoveOldInteractor){
                getInteractor().remove(oldInteractor);
            }
            getInteractor().add(entity.getInteractor());
        }
    }

    /**
     * Sets the value of the molecule set type property.
     *
     * @param type
     *     allowed object is
     *     {@link psidev.psi.mi.jami.xml.model.extension.XmlCvTerm }
     *
     */
    public void setJAXBType(XmlCvTerm type) {
        setType(type);
    }

    public Collection<P> getJAXBInteractorCandidates() {
        return candidates;
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
    public int getId() {
        return getDelegate().getId();
    }

    @Override
    public void setId(int id) {
        getDelegate().setId(id);
        XmlEntryContext.getInstance().registerParticipant(id, this);
    }

    @Override
    public String getShortName() {
        return getDelegate().getShortName();
    }

    @Override
    public void setShortName(String name) {
        getDelegate().setShortName(name);
    }

    @Override
    public String getFullName() {
        return getDelegate().getFullName();
    }

    @Override
    public void setFullName(String name) {
        getDelegate().setFullName(name);
    }

    @Override
    public void setInteractionAndAddParticipant(I interaction) {
        if (this.interaction != null){
            this.interaction.removeParticipant(this);
        }

        if (interaction != null){
            interaction.addParticipant(this);
        }
    }

    @Override
    public I getInteraction() {
        return this.interaction;
    }

    @Override
    public void setInteraction(I interaction) {
        this.interaction = interaction;
    }

    @Override
    public CvTerm getBiologicalRole() {
        return getDelegate().getBiologicalRole();
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        getDelegate().setBiologicalRole(bioRole);
    }

    @Override
    public Collection<Xref> getXrefs() {
        return getDelegate().getXrefs();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return getDelegate().getAnnotations();
    }

    @Override
    public Collection<Alias> getAliases() {
        return getDelegate().getAliases();
    }

    @Override
    public Collection<CausalRelationship> getCausalRelationships() {
        return getDelegate().getCausalRelationships();
    }

    @Override
    public Collection<F> getFeatures() {
        return getDelegate().getFeatures();
    }

    @Override
    public EntityInteractorChangeListener getChangeListener() {
        return getDelegate().getChangeListener();
    }

    @Override
    public void setChangeListener(EntityInteractorChangeListener listener) {
        getDelegate().setChangeListener(listener);
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

    @Override
    public Stoichiometry getStoichiometry() {
        return getDelegate().getStoichiometry();
    }

    @Override
    public void setStoichiometry(Integer stoichiometry) {
        getDelegate().setStoichiometry(stoichiometry);
    }

    @Override
    public void setStoichiometry(Stoichiometry stoichiometry) {
        getDelegate().setStoichiometry(stoichiometry);
    }

    protected AbstractXmlParticipant<I, F> getDelegate() {
        if (this.delegate == null){
           initialiseDefaultDelegate();
        }
        return delegate;
    }

    protected abstract void initialiseDefaultDelegate();

    public void setDelegate(AbstractXmlParticipant<I, F> delegate) {
        this.delegate = delegate;
    }
}
