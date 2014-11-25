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

public abstract class AbstractParticipantRef<I extends Interaction, T extends Feature> extends AbstractEntityRef<T> implements ExtendedPsiXmlParticipant<I,T> {
    private static final Logger logger = Logger.getLogger("AbstractParticipantRef");

    public AbstractParticipantRef(int ref) {
        super(ref);
    }

    @Override
    public void setInteractionAndAddParticipant(I interaction) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setInteractionAndAddParticipant(interaction);
    }

    @Override
    public I getInteraction() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getInteraction();
    }

    @Override
    public void setInteraction(I interaction) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setInteraction(interaction);
    }

    public CvTerm getBiologicalRole() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getBiologicalRole();
    }

    public void setBiologicalRole(CvTerm bioRole) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setBiologicalRole(bioRole);
    }

    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getAnnotations();
    }

    public Collection<Alias> getAliases() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getAliases();
    }

    public Stoichiometry getStoichiometry() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getStoichiometry();
    }

    public void setStoichiometry(Integer stoichiometry) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setStoichiometry(stoichiometry);
    }

    public void setStoichiometry(Stoichiometry stoichiometry) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setStoichiometry(stoichiometry);
    }

    public Collection<T> getFeatures() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getFeatures();
    }

    public EntityInteractorChangeListener getChangeListener() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getChangeListener();
    }

    public void setChangeListener(EntityInteractorChangeListener listener) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setChangeListener(listener);
    }

    public boolean addFeature(T feature) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().addFeature(feature);
    }

    public boolean removeFeature(T feature) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().removeFeature(feature);
    }

    public boolean addAllFeatures(Collection<? extends T> features) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().addAllFeatures(features);
    }

    public boolean removeAllFeatures(Collection<? extends T> features) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().removeAllFeatures(features);
    }

    @Override
    public void setId(int id) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setId(id);
    }

    @Override
    public int getId() {
        return getDelegate() != null ? getDelegate().getId() : this.ref;
    }

    @Override
    public String getShortName() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getShortName();
    }

    @Override
    public void setShortName(String name) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setShortName(name);
    }

    @Override
    public String getFullName() {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        return getDelegate().getFullName();
    }

    @Override
    public void setFullName(String name) {
        logger.log(Level.WARNING, "The participant reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseParticipantDelegate();
        }
        getDelegate().setFullName(name);
    }

    @Override
    public String toString() {
        return "Participant Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }

    @Override
    protected ExtendedPsiXmlParticipant<I,T> getDelegate() {
        return (ExtendedPsiXmlParticipant<I,T>)super.getDelegate();
    }
}
