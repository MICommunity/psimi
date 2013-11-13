package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.NamedEntity;

/**
 * Extended participant for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Participant<F extends Feature> extends NamedEntity<F> {

    public int getId();
    public void setId(int id);
}
