package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCooperativeEffect;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.reference.AbstractComplexReference;
import psidev.psi.mi.jami.xml.reference.AbstractParticipantRef;

/**
 * XML implementation of Allostery
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class XmlAllostery<T extends AllostericEffector> extends DefaultCooperativeEffect implements FileSourceContext {
    private PsiXmLocator sourceLocator;
    private CvTerm allostericMechanism;
    private CvTerm allosteryType;
    private ModelledParticipant allostericMolecule;
    private T allostericEffector;

    public XmlAllostery(CvTerm outcome) {
        super(outcome);
    }

    public XmlAllostery(CvTerm outcome, CvTerm response) {
        super(outcome, response);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator locator) {
        this.sourceLocator = locator;
    }

    @Override
    public String toString() {
        return "Allostery: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    public CvTerm getAllostericMechanism() {
        return this.allostericMechanism;
    }

    public void setAllostericMechanism(CvTerm mechanism) {
        this.allostericMechanism = mechanism;
    }

    public CvTerm getAllosteryType() {
        return this.allosteryType;
    }

    public void setAllosteryType(CvTerm type) {
        this.allosteryType = type;
    }

    public ModelledParticipant getAllostericMolecule() {
        return this.allostericMolecule;
    }

    public void setAllostericMolecule(ModelledParticipant participant) {
        if (participant == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null");
        }
        this.allostericMolecule = participant;
    }

    public T getAllostericEffector() {
        return this.allostericEffector;
    }

    public void setAllostericEffector(T effector) {
        if (effector == null){
            throw new IllegalArgumentException("The allosteric effector cannot be null");
        }
        this.allostericEffector = effector;
    }

    public void addAffectedInteractionRef(int affectedInteraction, PsiXmLocator locator){
        getAffectedInteractions().add(new ModelledInteractionRef(affectedInteraction, locator));
    }

    public void setAllostericMoleculeRef(int ref, PsiXmLocator locator){
       this.allostericMolecule = new AllostericMoleculeRef(ref, locator);
    }

    public void setAllostericPTMRef(int ref, PsiXmLocator locator){
        this.allostericEffector = (T)new XmlFeatureModificationEffector(ref, locator);
    }

    public void setAllostericEffectorRef(int ref, PsiXmLocator locator){
        this.allostericEffector = (T)new XmlMoleculeEffector(ref, locator);
    }

    ////////////////////////////////// inner classes

    /**
     * participant ref for allosteric molecule
     */
    private class AllostericMoleculeRef extends AbstractParticipantRef<ModelledFeature> implements ModelledParticipant{
        private PsiXmLocator sourceLocator;

        public AllostericMoleculeRef(int ref, PsiXmLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXml25IdCache parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Object object = parsedObjects.get(this.ref);
                // convert participant evidence in a complex
                if (object instanceof ParticipantEvidence){
                    ModelledParticipant participant = new XmlParticipantEvidenceWrapper((ParticipantEvidence)object, null);
                    setAllostericMolecule(participant);
                    return true;
                }
                // wrap modelled interaction
                else if (object instanceof ModelledParticipant){
                    setAllostericMolecule((ModelledParticipant) object);
                }
                // wrap basic interaction
                else if (object instanceof Participant){
                    ModelledParticipant participant = new XmlParticipantWrapper((Participant)object, null);
                    setAllostericMolecule(participant);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Affected modelled interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }

        @Override
        public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
            throw new IllegalStateException("The participant reference is not resolved and we cannot set the interaction of participant "+ref);
        }

        @Override
        public ModelledInteraction getInteraction() {
            throw new IllegalStateException("The participant reference is not resolved and we don't have an interaction for participant id "+ref);
        }

        @Override
        public void setInteraction(ModelledInteraction interaction) {
            throw new IllegalStateException("The participant reference is not resolved and we cannot set the interaction of participant "+ref);
        }
    }

    /**
     * interaction ref for affected cooperative interaction
     */
    private class ModelledInteractionRef extends AbstractComplexReference {
        private PsiXmLocator sourceLocator;

        public ModelledInteractionRef(int ref, PsiXmLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXml25IdCache parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Object object = parsedObjects.get(this.ref);
                // convert interaction evidence in a complex
                if (object instanceof AbstractXmlInteractionEvidence){
                    ModelledInteraction interaction = new XmlInteractionEvidenceComplexWrapper((AbstractXmlInteractionEvidence)object);
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add(interaction);
                    return true;
                }
                // wrap modelled interaction
                else if (object instanceof ModelledInteraction){
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add((ModelledInteraction)object);
                }
                // wrap basic interaction
                else if (object instanceof AbstractXmlBasicInteraction){
                    ModelledInteraction interaction = new XmlBasicInteractionComplexWrapper((AbstractXmlBasicInteraction)object);
                    getAffectedInteractions().remove(this);
                    getAffectedInteractions().add(interaction);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Affected modelled interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }
    }
}
