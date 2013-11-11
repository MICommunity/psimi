package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Wrapper for complexes that were loaded as modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlTransient
public class XmlModelledInteractionComplexWrapper implements Complex, FileSourceContext, ExtendedPsi25Interaction<ModelledParticipant>{

    private XmlModelledInteraction modelledInteraction;
    private Organism organism;
    private CvTerm interactorType;

    public XmlModelledInteractionComplexWrapper(XmlModelledInteraction modelled){
        if (modelled == null){
            throw new IllegalArgumentException("The complex wrapper needs a non null xmlModelledInteraction");
        }
        this.modelledInteraction = modelled;
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, Complex.COMPLEX_MI);
        // add the new generated complex in the referenced complexes
        XmlEntryContext.getInstance().registerComplex(modelled.getId(), this);
    }

    public Date getUpdatedDate() {
        return this.modelledInteraction.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        this.modelledInteraction.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return this.modelledInteraction.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        this.modelledInteraction.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return this.modelledInteraction.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        this.modelledInteraction.setInteractionType(term);
    }

    public boolean addParticipant(ModelledParticipant part) {
        return this.modelledInteraction.addParticipant(part);
    }

    public boolean removeParticipant(ModelledParticipant part) {
        return this.modelledInteraction.removeParticipant(part);
    }

    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.modelledInteraction.addAllParticipants(participants);
    }

    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.modelledInteraction.removeAllParticipants(participants);
    }

    public Collection<ModelledParticipant> getParticipants() {
        return this.modelledInteraction.getParticipants();
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        return this.modelledInteraction.getInteractionEvidences();
    }

    public Source getSource() {
        return this.modelledInteraction.getSource();
    }

    public void setSource(Source source) {
        this.modelledInteraction.setSource(source);
    }

    @Override
    public boolean isIntraMolecular() {
        return this.modelledInteraction.isIntraMolecular();
    }

    @Override
    public void setIntraMolecular(boolean intra) {
        this.modelledInteraction.setIntraMolecular(intra);
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        return this.modelledInteraction.getModelledConfidences();
    }

    public Collection<ModelledParameter> getModelledParameters() {
        return this.modelledInteraction.getModelledParameters();
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        return this.modelledInteraction.getCooperativeEffects();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.modelledInteraction.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.modelledInteraction.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.modelledInteraction.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.modelledInteraction.getIdentifiers();
    }

    @Override
    public String getShortName() {
        return this.modelledInteraction.getShortName() != null ? this.modelledInteraction.getShortName() : PsiXml25Utils.UNSPECIFIED;
    }

    @Override
    public void setShortName(String name) {
        this.modelledInteraction.setShortName(name);
    }

    @Override
    public String getFullName() {
        return this.modelledInteraction.getFullName();
    }

    @Override
    public void setFullName(String name) {
        this.modelledInteraction.setFullName(name);
    }

    @Override
    public Xref getPreferredIdentifier() {
        return !this.modelledInteraction.getIdentifiers().isEmpty() ? this.modelledInteraction.getIdentifiers().iterator().next() : null;
    }

    @Override
    public Organism getOrganism() {
        return this.organism;
    }

    @Override
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    @Override
    public CvTerm getInteractorType() {
        return this.interactorType;
    }

    @Override
    public void setInteractorType(CvTerm type) {
        if (type == null){
            this.interactorType = new XmlCvTerm(Complex.COMPLEX, Complex.COMPLEX_MI);
        }
        else{
            this.interactorType = type;
        }
    }

    @Override
    public String getRigid() {
        return this.modelledInteraction.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.modelledInteraction.setRigid(rigid);
    }

    @Override
    public String getPhysicalProperties() {
        Annotation properties = AnnotationUtils.collectFirstAnnotationWithTopic(this.modelledInteraction.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        return properties != null ? properties.getValue() : null;
    }

    @Override
    public void setPhysicalProperties(String properties) {
        Annotation propertiesAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(this.modelledInteraction.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        if (propertiesAnnot != null){
            propertiesAnnot.setValue(properties);
        }
        else{
            this.modelledInteraction.getAnnotations().add(new XmlAnnotation(CvTermUtils.createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI), properties));
        }
    }

    @Override
    public List<Alias> getAliases() {
        return this.modelledInteraction.getAliases();
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        return this.modelledInteraction.getInteractionTypes();
    }

    @Override
    public XmlEntry getEntry() {
        return this.modelledInteraction.getEntry();
    }

    @Override
    public void setEntry(XmlEntry entry) {
        this.modelledInteraction.setEntry(entry);
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
        return this.modelledInteraction.getInferredInteractions();
    }

    @Override
    public int getId() {
        return this.modelledInteraction.getId();
    }

    @Override
    public void setId(int id) {
        this.modelledInteraction.setId(id);
        XmlEntryContext.getInstance().registerComplex(id, this);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return this.modelledInteraction.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        this.modelledInteraction.setSourceLocator(locator);
    }

    @Override
    public String toString() {
        return this.modelledInteraction.toString();
    }
}
