package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.util.List;

/**
 * Extended participant evidence for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25ParticipantEvidence extends ExtendedPsi25Participant<FeatureEvidence>,ParticipantEvidence {
    public List<Organism> getHostOrganisms();
    public List<ExperimentalInteractor> getExperimentalInteractors();
    public List<CvTerm> getExperimentalRoles();
}
