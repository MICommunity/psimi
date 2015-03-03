package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlParticipant;
import psidev.psi.mi.jami.xml.model.extension.XmlParticipantEvidence;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * Xml implementation of participant pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/09/14</pre>
 */
@XmlTransient
public class XmlExperimentalParticipantPool extends AbstractXmlParticipantPool<InteractionEvidence, FeatureEvidence, ExperimentalParticipantCandidate>
implements ExperimentalParticipantPool{

    public XmlExperimentalParticipantPool() {
        super();
    }

    public XmlExperimentalParticipantPool(AbstractXmlParticipant<InteractionEvidence, FeatureEvidence> delegate) {
        super(delegate);
    }

    @Override
    protected void initialiseDefaultDelegate() {
        super.setDelegate(new XmlParticipantEvidence());
    }

    @Override
    public CvTerm getExperimentalRole() {
        return ((ParticipantEvidence)getDelegate()).getExperimentalRole();
    }

    @Override
    public void setExperimentalRole(CvTerm expRole) {
        ((ParticipantEvidence)getDelegate()).setExperimentalRole(expRole);
    }

    @Override
    public Collection<CvTerm> getIdentificationMethods() {
        return ((ParticipantEvidence)getDelegate()).getIdentificationMethods();
    }

    @Override
    public Collection<CvTerm> getExperimentalPreparations() {
        return ((ParticipantEvidence)getDelegate()).getExperimentalPreparations();
    }

    @Override
    public Organism getExpressedInOrganism() {
        return ((ParticipantEvidence)getDelegate()).getExpressedInOrganism();
    }

    @Override
    public void setExpressedInOrganism(Organism organism) {
        ((ParticipantEvidence)getDelegate()).setExpressedInOrganism(organism);
    }

    @Override
    public Collection<Confidence> getConfidences() {
        return ((ParticipantEvidence)getDelegate()).getConfidences();
    }

    @Override
    public Collection<Parameter> getParameters() {
        return ((ParticipantEvidence)getDelegate()).getParameters();
    }

}
