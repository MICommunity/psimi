package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.model.Interactor;

/**
 * Full updater of interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorUpdater extends AbstractInteractorUpdater<Interactor>{
    public FullInteractorUpdater() {
        super(new FullInteractorEnricher());
    }
}
