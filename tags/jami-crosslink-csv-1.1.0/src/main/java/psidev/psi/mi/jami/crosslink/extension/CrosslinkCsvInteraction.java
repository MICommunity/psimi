package psidev.psi.mi.jami.crosslink.extension;

import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Interaction evidence extension from crosslinking CSV files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/08/14</pre>
 */

public interface CrosslinkCsvInteraction extends InteractionEvidence{

    /**
     *
     * @return the n-ary group name used for this interactions
     */
    public String getNaryGroup();

    /**
     *
     * @param bait : n-ary group name used for this interaction
     */
    public void setNaryGroup(String bait);
}
