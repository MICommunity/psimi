package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Binary Wrapper for an Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BinaryInteractionWrapper extends AbstractBinaryInteractionWrapper<Interaction<Participant>, Participant>{

    public BinaryInteractionWrapper(Interaction interaction){
        super(interaction);
    }

    public BinaryInteractionWrapper(Interaction interaction, CvTerm complexExpansion){
        super(interaction, complexExpansion);
    }
}
