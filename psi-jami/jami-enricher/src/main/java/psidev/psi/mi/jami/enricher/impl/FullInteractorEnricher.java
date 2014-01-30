package psidev.psi.mi.jami.enricher.impl;

/**
 * Full interactor enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorEnricher extends MinimalInteractorEnricher {

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
