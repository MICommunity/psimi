package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

/**
 * Extended participant for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsiXmlEntity<F extends Feature> extends Entity<F> {

    public int getId();
    public void setId(int id);
}
