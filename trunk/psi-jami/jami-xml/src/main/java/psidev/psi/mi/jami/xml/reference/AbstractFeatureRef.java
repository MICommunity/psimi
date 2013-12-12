package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Feature;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract feature reference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractFeatureRef<E extends Entity, F extends Feature> extends AbstractXmlIdReference implements ExtendedPsi25Feature<E,F>{
    private static final Logger logger = Logger.getLogger("AbstractFeatureRef");
    private ExtendedPsi25Feature<E,F> delegate;

    public AbstractFeatureRef(int ref) {
        super(ref);
    }

    public String getShortName() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getShortName();
    }

    public void setShortName(String name) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setShortName(name);
    }

    public String getFullName() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getFullName();
    }

    public void setFullName(String name) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setFullName(name);
    }

    public String getInterpro() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getInterpro();
    }

    public void setInterpro(String interpro) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setInterpro(interpro);
    }

    public Collection<Xref> getIdentifiers() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getIdentifiers();
    }

    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getAnnotations();
    }

    public CvTerm getType() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getType();
    }

    public void setType(CvTerm type) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setType(type);
    }

    public Collection<Range> getRanges() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getRanges();
    }

    public CvTerm getInteractionEffect() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getInteractionEffect();
    }

    public void setInteractionEffect(CvTerm effect) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setInteractionEffect(effect);
    }

    public CvTerm getInteractionDependency() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getInteractionDependency();
    }

    public void setInteractionDependency(CvTerm interactionDependency) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setInteractionDependency(interactionDependency);
    }

    public E getParticipant() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getParticipant();
    }

    public void setParticipant(E participant) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setParticipant(participant);
    }

    public void setParticipantAndAddFeature(E participant) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setParticipantAndAddFeature(participant);
    }

    public Collection<F> getLinkedFeatures() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getLinkedFeatures();
    }

    @Override
    public void setId(int id) {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        this.delegate.setId(id);
    }

    @Override
    public int getId() {
        return this.delegate != null ? this.delegate.getId() : this.ref;
    }

    @Override
    public Collection<Alias> getAliases() {
        logger.log(Level.WARNING, "The feature reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (this.delegate == null){
            initialiseFeatureDelegate();
        }
        return this.delegate.getAliases();
    }

    @Override
    public String toString() {
        return "Feature Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
    protected abstract void initialiseFeatureDelegate();

    protected ExtendedPsi25Feature<E, F> getDelegate() {
        return delegate;
    }

    protected void setDelegate(ExtendedPsi25Feature<E, F> delegate) {
        this.delegate = delegate;
    }
}
