package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

/**
 * Extended participant for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Participant<F extends Feature> extends Entity<F>{

    public int getId();
    public void setId(int id);
    public String getShortLabel();
    public void setShortLabel(String name);
    public String getFullName();
    public void setFullName(String name);
}
