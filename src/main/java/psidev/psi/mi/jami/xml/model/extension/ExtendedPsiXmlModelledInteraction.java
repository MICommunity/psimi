package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;

import java.util.Collection;
import java.util.List;

/**
 * Extended modelled interaction for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlModelledInteraction extends ExtendedPsiXmlInteraction<ModelledParticipant>, ModelledInteraction{
    public List<Experiment> getExperiments();

    @Override
    public Collection<Alias> getAliases();
}
