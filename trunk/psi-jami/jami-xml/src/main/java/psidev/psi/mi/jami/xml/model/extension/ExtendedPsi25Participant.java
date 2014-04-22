package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.NamedParticipant;

/**
 * Extended participant for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Participant<I extends Interaction, F extends Feature> extends NamedParticipant<I,F> {

    public int getId();
    public void setId(int id);
}
