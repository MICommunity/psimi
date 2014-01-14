package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

/**
 * Interface for psi25 xml features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Feature<P extends Entity, F extends Feature> extends Feature<P,F> {
    public int getId();
    public void setId(int id);
}
