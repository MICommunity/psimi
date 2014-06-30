package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.reference.AbstractParticipantRef;

import javax.xml.bind.annotation.XmlTransient;

/**
 * XML implementation of allosteric molecule effector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */
@XmlTransient
public class XmlMoleculeEffector implements MoleculeEffector, FileSourceContext {
    private PsiXmlLocator sourceLocator;
    private ModelledParticipant participant;

    public XmlMoleculeEffector(int participant, PsiXmlLocator locator){
        this.participant = new MoleculeEffectorRef(participant, locator);
    }

    public ModelledParticipant getMolecule() {
        return participant;
    }

    public AllostericEffectorType getEffectorType() {
        return AllostericEffectorType.molecule;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
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

    public void setSourceLocator(PsiXmlLocator locator) {
        this.sourceLocator = locator;
    }

    @Override
    public String toString() {
        return "Allosteric molecule effector: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    ////////////////////////////////// inner classes
    /**
     * participant ref for allosteric effector
     */
    private class MoleculeEffectorRef extends AbstractParticipantRef<ModelledInteraction, ModelledFeature> implements ModelledParticipant{
        private PsiXmlLocator sourceLocator;

        public MoleculeEffectorRef(int ref, PsiXmlLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsParticipant(this.ref)){
                Participant object = parsedObjects.getParticipant(this.ref);
                if (object == null){
                    return false;
                }
                // convert participant evidence in a modelled participant
                else if (object instanceof ParticipantEvidence){
                    participant = new XmlParticipantEvidenceWrapper((ParticipantEvidence)object, null);
                    return true;
                }
                // use modelled participant
                else if (object instanceof ModelledParticipant){
                    participant = (ModelledParticipant)object;
                    return true;
                }
                // wrap basic participant
                else {
                    participant = new XmlParticipantWrapper(object, null);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Allosteric molecule effector Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        @Override
        protected void initialiseParticipantDelegate() {
            XmlModelledParticipant modelled = new XmlModelledParticipant();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
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

        public void setSourceLocator(PsiXmlLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }

        @Override
        public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
            throw new IllegalStateException("The molecule effector reference is not resolved and we cannot set the interaction of participant "+ref);
        }

        @Override
        public ModelledInteraction getInteraction() {
            throw new IllegalStateException("The molecule effector reference is not resolved and we don't have an interaction for participant id "+ref);
        }

        @Override
        public void setInteraction(ModelledInteraction interaction) {
            throw new IllegalStateException("The molecule effector reference is not resolved and we cannot set the interaction of participant "+ref);
        }
    }
}
