package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlInteraction;

import java.util.Collection;
import java.util.List;

/**
 * Extended interaction for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlModelledInteraction extends PsiXmlInteraction<ModelledParticipant>, Complex {

    public List<BindingFeatures> getBindingFeatures();

    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships();

    @Override
    public Collection<Alias> getAliases();
}
