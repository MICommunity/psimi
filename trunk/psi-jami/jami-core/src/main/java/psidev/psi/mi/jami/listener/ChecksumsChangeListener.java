package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Checksum;

import java.util.EventListener;

/**
 * Listener for changes in checkusms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/09/13</pre>
 */

public interface ChecksumsChangeListener<T extends Object> extends EventListener {

    /**
     * Listen to the event where a checksum has been added to the interactor checksums.
     * @param interactor        The interactor which has changed.
     * @param added             The added checksum.
     */
    public void onAddedChecksum(T interactor , Checksum added);

    /**
     * Listen to the event where a checksum has been removed from the interactor checksums.
     * @param interactor        The interactor which has changed.
     * @param removed           The removed checksum.
     */
    public void onRemovedChecksum(T interactor , Checksum removed);
}
