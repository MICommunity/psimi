package psidev.psi.mi.jami.xml.extension;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * Wrapper for XmlParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlParticipantWrapper extends XmlModelledParticipant{

    private Participant participant;

    public XmlParticipantWrapper(Participant part, XmlBasicInteractionComplexWrapper wrapper){
        if (part == null){
            throw new IllegalArgumentException("A participant wrapper needs a non null interaction wrapper");
        }
        this.participant = part;
        setInteraction(wrapper);
    }

    @Override
    public Collection<Alias> getAliases() {
        return this.participant.getAliases();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.participant.getXrefs();
    }

    @Override
    public Interactor getInteractor() {
        return this.participant.getInteractor();
    }

    @Override
    public CausalRelationship getCausalRelationship() {
        return this.participant.getCausalRelationship();
    }

    @Override
    public void setCausalRelationship(CausalRelationship relationship) {
        this.participant.setCausalRelationship(relationship);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.participant.getAnnotations();
    }

    @Override
    public Stoichiometry getStoichiometry() {
        return this.participant.getStoichiometry();
    }

    @Override
    public void setStoichiometry(Integer stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public void setStoichiometry(Stoichiometry stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public void setChangeListener(ParticipantInteractorChangeListener listener) {
        this.participant.setChangeListener(listener);
    }

    @Override
    public ParticipantInteractorChangeListener getChangeListener() {
        return this.participant.getChangeListener();
    }

    @Override
    public CvTerm getBiologicalRole() {
        return this.participant.getBiologicalRole();
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        this.participant.setBiologicalRole(bioRole);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        super.setSourceLocator(sourceLocator);
    }

    @Override
    protected void initialiseFeatureWrapper() {
        super.initialiseFeatureWrapper();
        // initialise wrapper
    }

    @Override
    public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
        super.setInteractionAndAddParticipant(interaction);
    }

    @Override
    public ModelledInteraction getInteraction() {
        return super.getInteraction();
    }

    @Override
    public void setInteraction(ModelledInteraction interaction) {
        super.setInteraction(interaction);
    }

    @Override
    public void setInteractor(Interactor interactor) {
        super.setInteractor(interactor);
    }

    @Override
    public Collection<ModelledFeature> getFeatures() {
        return super.getFeatures();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean addFeature(ModelledFeature feature) {
        return super.addFeature(feature);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeFeature(ModelledFeature feature) {
        return super.removeFeature(feature);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean addAllFeatures(Collection<? extends ModelledFeature> features) {
        return super.addAllFeatures(features);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeAllFeatures(Collection<? extends ModelledFeature> features) {
        return super.removeAllFeatures(features);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int getId() {
        return super.getId();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Locator sourceLocation() {
        return super.sourceLocation();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return super.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setFeatureWrapper(AbstractXmlEntity.JAXBFeatureWrapper<ModelledFeature> jaxbFeatureWrapper) {
        super.setFeatureWrapper(jaxbFeatureWrapper);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void processAddedFeature(ModelledFeature feature) {
        super.processAddedFeature(feature);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void initialiseUnspecifiedInteractor() {
        super.initialiseUnspecifiedInteractor();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void initialiseAnnotationWrapper() {
        super.initialiseAnnotationWrapper();    //To change body of overridden methods use File | Settings | File Templates.
    }
}