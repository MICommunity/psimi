package psidev.psi.mi.jami.enricher.impl;

/**
 * Full updater of a modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullModelledInteractionUpdater extends FullModelledInteractionEnricher{

    public FullModelledInteractionUpdater() {
        super(new FullInteractionUpdater());
    }
}
