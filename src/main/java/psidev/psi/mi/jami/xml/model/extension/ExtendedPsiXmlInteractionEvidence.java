package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.util.Collection;
import java.util.List;

/**
 * Extended interaction evidence for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlInteractionEvidence extends ExtendedPsiXmlInteraction<ParticipantEvidence>, InteractionEvidence{

    public AbstractAvailability getXmlAvailability();
    public void setXmlAvailability(AbstractAvailability availability);
    public boolean isModelled();
    public void setModelled(boolean modelled);
    public List<Experiment> getExperiments();
    public List<ExtendedPsiXmlExperiment> getOriginalExperiments();
    @Override
    public Collection<Alias> getAliases();
}
