package psidev.psi.mi.jami.xml.model.reference;

import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlEntity;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract entity reference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractEntityRef<T extends Feature> extends AbstractXmlIdReference implements ExtendedPsiXmlEntity<T> {
    private static final Logger logger = Logger.getLogger("AbstractEntityRef");
    private ExtendedPsiXmlEntity<T> delegate;

    public AbstractEntityRef(int ref) {
        super(ref);
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

    public Collection<CausalRelationship> getCausalRelationships() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseParticipantDelegate();
        }
        return this.delegate.getCausalRelationships();
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
    public String toString() {
        return "Participant Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }

    protected abstract void initialiseParticipantDelegate();

    protected ExtendedPsiXmlEntity<T> getDelegate() {
        return delegate;
    }

    protected void setDelegate(ExtendedPsiXmlEntity<T> delegate) {
        this.delegate = delegate;
    }
}
