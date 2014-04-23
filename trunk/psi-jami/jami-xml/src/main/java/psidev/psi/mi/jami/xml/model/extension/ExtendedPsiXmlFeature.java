package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * Interface for psi25 xml features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsiXmlFeature<P extends Participant, F extends Feature> extends Feature<P,F> {
    public int getId();
    public void setId(int id);
}
