package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.reference.AbstractEntityRef;

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
    private ModelledEntity participant;

    public XmlMoleculeEffector(int participant, PsiXmlLocator locator){
        this.participant = new MoleculeEffectorRef(participant, locator);
    }

    public ModelledEntity getMolecule() {
        return participant;
    }

    public AllostericEffectorType getEffectorType() {
        return AllostericEffectorType.molecule;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        if (locator == null){
            this.sourceLocator = null;
        }
        else if (locator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)locator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getCharNumber(), null);
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
    private class MoleculeEffectorRef extends AbstractEntityRef<ModelledFeature> implements ModelledEntity{
        private PsiXmlLocator sourceLocator;

        public MoleculeEffectorRef(int ref, PsiXmlLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            // have a complex participant, load it
            if (parsedObjects.containsComplexParticipant(this.ref)){
                ModelledEntity object = parsedObjects.getComplexParticipant(this.ref);
                if (object == null){
                    return false;
                }
                // use complex participant
                else {
                    participant = object;
                    return true;
                }
            }
            // have a participant evidence, load the interaction as complex and then set participant
            else if (parsedObjects.containsParticipant(this.ref)){
                Entity object = parsedObjects.getParticipant(this.ref);
                if (object == null){
                    return false;
                }
                // convert participant evidence in a modelled participant and load previous complex
                else {
                    ModelledEntity reloadedObject = parsedObjects.registerModelledParticipantLoadedFrom(object);
                    if (reloadedObject != null){
                        participant = reloadedObject;
                        return true;
                    }
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
    }
}
