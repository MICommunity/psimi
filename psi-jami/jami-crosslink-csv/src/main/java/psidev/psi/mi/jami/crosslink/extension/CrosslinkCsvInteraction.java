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
     * @return the bait name used for this interactions
     */
    public String getBait();

    /**
     *
     * @param bait : bait name used for this interaction
     */
    public void setBait(String bait);
}
