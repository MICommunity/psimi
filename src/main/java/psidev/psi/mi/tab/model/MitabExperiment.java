package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.impl.DefaultExperiment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Default MITAB experiment implementation which is a patch for backward compatibility.
 * It only contains experiment information such as interaction detection method and host organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class MitabExperiment extends DefaultExperiment{

    /**
     * Detection method for that interaction.
     */
    private List<CrossReference> detectionMethods
            = new ArrayList<CrossReference>();

    /**
     * Organism where the interaction happens.
     */
    private Organism hostOrganism;

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        return detectionMethods;
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {
        this.detectionMethods = detectionMethods;
    }

    /**
     * {@inheritDoc}
     */
    public Organism getHostOrganism() {
        return hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public void setHostOrganism(Organism hostOrganism) {
        this.hostOrganism = hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasHostOrganism() {
        return hostOrganism != null;
    }
}
