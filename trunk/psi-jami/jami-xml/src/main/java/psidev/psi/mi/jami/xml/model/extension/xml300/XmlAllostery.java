package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.extension.*;
import psidev.psi.mi.jami.xml.model.reference.AbstractParticipantRef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * XML 3.0 implementation of Allostery
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlAllostery extends AbstractXmlCooperativeEffect implements Allostery<AllostericEffector> {
    private PsiXmLocator sourceLocator;
    private CvTerm allostericMechanism;
    private CvTerm allosteryType;
    private ModelledParticipant allostericMolecule;
    private MoleculeEffector moleculeEffector;
    private FeatureModificationEffector featureEffector;

    public XmlAllostery() {
        super();
    }

    public XmlAllostery(CvTerm outcome) {
        super(outcome);
    }

    public XmlAllostery(CvTerm outcome, CvTerm response) {
        super(outcome, response);
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
        if (this.allostericMolecule == null){
            this.allostericMolecule = new XmlModelledParticipant();
        }
        return this.allostericMolecule;
    }

    public void setAllostericMolecule(ModelledParticipant participant) {
        if (participant == null){
            throw new IllegalArgumentException("The allosteric molecule cannot be null");
        }
        this.allostericMolecule = participant;
    }

    public AllostericEffector getAllostericEffector() {
        if (this.moleculeEffector == null && this.featureEffector == null){
            initialiseDefaultAllostericEffector();
        }
        if (this.moleculeEffector != null){
            return this.moleculeEffector;
        }
        else {
            return this.featureEffector;
        }
    }

    protected void initialiseDefaultAllostericEffector(){
        setJAXBMoleculeEffectorRef(0);
    }

    public void setAllostericEffector(AllostericEffector effector) {
        if (effector == null){
            throw new IllegalArgumentException("The allosteric effector cannot be null");
        }

        switch (effector.getEffectorType()){
            case molecule:
                this.moleculeEffector = (MoleculeEffector)effector;
                break;
            case feature_modification:
                this.featureEffector = (FeatureModificationEffector)effector;
                break;
            default:
                throw new IllegalArgumentException("The allosteric effector can only be a modelled participant (molecule) or a modelled feature (feature_modification)");
        }
    }

    @XmlElement(name = "allostericMoleculeRef", required = true)
    public void setJAXBAllostericMoleculeRef(int ref) {
        this.allostericMolecule = new AllostericMoleculeRef(ref);
    }

    @XmlElement(name = "allostericEffectorRef", required = true)
    public void setJAXBMoleculeEffectorRef(int effector) {
        this.moleculeEffector = new XmlMoleculeEffector(effector, (PsiXmLocator)sourceLocation());
    }

    @XmlElement(name = "allostericModificationRef", required = true)
    public void setJAXBFeatureEffectorRef(int effector) {
        this.featureEffector = new XmlFeatureModificationEffector(effector, (PsiXmLocator)sourceLocation());
    }

    @XmlElement(name = "allostericMechanism")
    public void setJAXBAllostericMechanism(XmlCvTerm mechanism) {
        this.allostericMechanism = mechanism;
    }

    @XmlElement(name = "allosteryType")
    public void setJAXBAllosteryType(XmlCvTerm type) {
        this.allosteryType = type;
    }

    ////////////////////////////////// inner classes

    /**
     * participant ref for allosteric molecule
     */
    private class AllostericMoleculeRef extends AbstractParticipantRef<ModelledInteraction, ModelledFeature> implements ModelledParticipant{
        private PsiXmLocator sourceLocator;

        public AllostericMoleculeRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsParticipant(this.ref)){
                Participant object = parsedObjects.getParticipant(this.ref);
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
                // wrap basic participant
                else {
                    ModelledParticipant participant = new XmlParticipantWrapper(object, null);
                    setAllostericMolecule(participant);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Allosteric molecule Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }

        @Override
        public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
            if (getDelegate() == null){
                initialiseParticipantDelegate();
            }
            getDelegate().setInteractionAndAddParticipant(interaction);
        }

        @Override
        public ModelledInteraction getInteraction() {
            if (getDelegate() == null){
                initialiseParticipantDelegate();
            }
            return getDelegate().getInteraction();
        }

        @Override
        public void setInteraction(ModelledInteraction interaction) {
            if (getDelegate() == null){
                initialiseParticipantDelegate();
            }
            getDelegate().setInteraction(interaction);
        }
    }
}
