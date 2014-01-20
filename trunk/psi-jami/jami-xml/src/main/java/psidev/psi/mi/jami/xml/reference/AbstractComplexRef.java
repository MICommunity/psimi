package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.Entry;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlComplex;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for references to a complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractComplexRef extends AbstractInteractorRef implements Complex, ExtendedPsi25Interaction<ModelledParticipant>{
    private static final Logger logger = Logger.getLogger("AbstractComplexRef");

    public AbstractComplexRef(int ref) {
        super(ref);
    }

    public String getPhysicalProperties() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getPhysicalProperties();
    }

    public void setPhysicalProperties(String properties) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setPhysicalProperties(properties);
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getInteractionEvidences();
    }

    public Source getSource() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getSource();
    }

    public void setSource(Source source) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setSource(source);
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getModelledConfidences();
    }

    public Collection<ModelledParameter> getModelledParameters() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getModelledParameters();
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getCooperativeEffects();
    }

    public String getRigid() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getRigid();
    }

    public void setRigid(String rigid) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setRigid(rigid);
    }

    public Date getUpdatedDate() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setInteractionType(term);
    }

    public Collection<ModelledParticipant> getParticipants() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getParticipants();
    }

    public boolean addParticipant(ModelledParticipant part) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().addParticipant(part);
    }

    public boolean removeParticipant(ModelledParticipant part) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().removeParticipant(part);
    }

    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().addAllParticipants(participants);
    }

    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().removeAllParticipants(participants);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getXrefs();
    }

    @Override
    public Collection<Alias> getAliases() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getAliases();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getIdentifiers();
    }

    @Override
    public void setIntraMolecular(boolean intra) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setIntraMolecular(intra);
    }

    @Override
    public boolean isIntraMolecular() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().isIntraMolecular();
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getInferredInteractions();
    }

    @Override
    public void setEntry(Entry entry) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setEntry(entry);
    }

    @Override
    public Entry getEntry() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getEntry();
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getInteractionTypes();
    }

    @Override
    public void setSystematicName(String name) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setSystematicName(name);
    }

    @Override
    public String getSystematicName() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getSystematicName();
    }

    @Override
    public void setRecommendedName(String name) {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        getDelegate().setRecommendedName(name);
    }

    @Override
    public String getRecommendedName() {
        logger.log(Level.WARNING, "The interaction reference "+ref+" is not resolved. Some default properties will be initialised by default");
        if (getDelegate() == null){
            initialiseInteractorDelegate();
        }
        return getDelegate().getRecommendedName();
    }

    @Override
    public String toString() {
        return "Interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }

    @Override
    protected void initialiseInteractorDelegate() {
        XmlComplex complex = new XmlComplex(PsiXml25Utils.UNSPECIFIED);
        complex.setId(this.ref);
        setDelegate(complex);
    }

    @Override
    protected XmlComplex getDelegate() {
        return (XmlComplex) super.getDelegate();
    }
}
