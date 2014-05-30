package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.Participant;

/**
 * Extended causal relationship for XML 3.0.
 *
 * It contains a backreference to the source participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/14</pre>
 */

public interface ExtendedPsiXmlCausalRelationship extends CausalRelationship{

    /**
     *
     * @return  the participant source which cannot be null
     */
    public Participant getSource();
}
