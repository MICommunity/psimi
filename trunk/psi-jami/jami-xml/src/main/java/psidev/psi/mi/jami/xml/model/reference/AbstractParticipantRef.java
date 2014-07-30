package psidev.psi.mi.jami.xml.model.reference;

import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlParticipant;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract participant reference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractParticipantRef<I extends Interaction, T extends Feature> extends AbstractXmlIdReference implements ExtendedPsiXmlParticipant<I,T> {
    private static final Logger logger = Logger.getLogger("AbstractParticipantRef");
    private ExtendedPsiXmlParticipant<I,T> delegate;

    public AbstractParticipantRef(int ref) {
        super(ref);
    }

    @Override
    public void setInteractionAndAddParticipant(I interaction) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setInteractionAndAddParticipant(interaction);
    }

    @Override
    public I getInteraction() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getInteraction();
    }

    @Override
    public void setInteraction(I interaction) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setInteraction(interaction);
    }

    public Interactor getInteractor() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getInteractor();
    }

    public void setInteractor(Interactor interactor) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setInteractor(interactor);
    }

    public CvTerm getBiologicalRole() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getBiologicalRole();
    }

    public void setBiologicalRole(CvTerm bioRole) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setBiologicalRole(bioRole);
    }

    public Collection<CausalRelationship> getCausalRelationships() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getCausalRelationships();
    }

    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getAnnotations();
    }

    public Collection<Alias> getAliases() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getAliases();
    }

    public Stoichiometry getStoichiometry() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getStoichiometry();
    }

    public void setStoichiometry(Integer stoichiometry) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setStoichiometry(stoichiometry);
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setStoichiometry(stoichiometry);
    }

    public Collection<T> getFeatures() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getFeatures();
    }

    public EntityInteractorChangeListener getChangeListener() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getChangeListener();
    }

    public void setChangeListener(EntityInteractorChangeListener listener) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setChangeListener(listener);
    }

    public boolean addFeature(T feature) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.addFeature(feature);
    }

    public boolean removeFeature(T feature) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.removeFeature(feature);
    }

    public boolean addAllFeatures(Collection<? extends T> features) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.addAllFeatures(features);
    }

    public boolean removeAllFeatures(Collection<? extends T> features) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.removeAllFeatures(features);
    }

    @Override
    public void setId(int id) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setId(id);
    }

    @Override
    public int getId() {
        return this.delegate != null ? this.delegate.getId() : this.ref;
    }

    @Override
    public String getShortName() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getShortName();
    }

    @Override
    public void setShortName(String name) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setShortName(name);
    }

    @Override
    public String getFullName() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getFullName();
    }

    @Override
    public void setFullName(String name) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        this.delegate.setFullName(name);
    }

    @Override
    public String toString() {
        return "Participant Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }

    protected abstract void initialiseParticipantDelegate();

    protected ExtendedPsiXmlParticipant<I,T> getDelegate() {
        return delegate;
    }

    protected void setDelegate(ExtendedPsiXmlParticipant<I,T> delegate) {
        this.delegate = delegate;
    }
}
