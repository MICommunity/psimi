package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.util.List;

/**
 * Extended interaction evidence for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsi25InteractionEvidence extends ExtendedPsi25Interaction<ParticipantEvidence>, InteractionEvidence{

    public Availability getXmlAvailability();
    public void setXmlAvailability(Availability availability);
    public boolean isModelled();
    public void setModelled(boolean modelled);
    public List<Experiment> getExperiments();
}
