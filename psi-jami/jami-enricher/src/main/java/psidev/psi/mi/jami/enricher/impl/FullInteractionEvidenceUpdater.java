package psidev.psi.mi.jami.enricher.impl;

/**
 * Full updater for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionEvidenceUpdater extends FullInteractionEvidenceEnricher{

    public FullInteractionEvidenceUpdater() {
        super(new FullInteractionUpdater());
    }
}