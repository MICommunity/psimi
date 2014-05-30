package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.Participant;

import java.util.List;

/**
 * Extended interaction for PSI-XML 3.0 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlInteraction<T extends Participant> extends psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction<T> {

    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships();
}
