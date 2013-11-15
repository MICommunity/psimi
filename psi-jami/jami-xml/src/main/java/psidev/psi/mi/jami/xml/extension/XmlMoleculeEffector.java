package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25IdIndex;
import psidev.psi.mi.jami.xml.reference.AbstractParticipantRef;

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
    private PsiXmLocator sourceLocator;
    private ModelledParticipant participant;

    public XmlMoleculeEffector(int participant, PsiXmLocator locator){
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
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator locator) {
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
    private class MoleculeEffectorRef extends AbstractParticipantRef<ModelledFeature> implements ModelledParticipant{
        private PsiXmLocator sourceLocator;

        public MoleculeEffectorRef(int ref, PsiXmLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXml25IdIndex parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Object object = parsedObjects.get(this.ref);
                // convert participant evidence in a modelled participant
                if (object instanceof ParticipantEvidence){
                    participant = new XmlParticipantEvidenceWrapper((ParticipantEvidence)object, null);
                    return true;
                }
                // use modelled participant
                else if (object instanceof ModelledParticipant){
                    participant = (ModelledParticipant)object;
                }
                // wrap basic participant
                else if (object instanceof Participant){
                    participant = new XmlParticipantWrapper((Participant)object, null);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Allosteric molecule effector Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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
