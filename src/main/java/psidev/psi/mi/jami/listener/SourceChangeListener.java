package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;

/**
 * Source change listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface SourceChangeListener extends CvTermChangeListener {

    /**
     * Listen to the event where the URL of a source has been changed.
     * If oldURL is null, it means that a url has been added to the source.
     * If the URL of the source is null, it means that the URL of the source has been removed
     * @param cv
     * @param oldUrl
     */
    public void onUrlUpdate(Source cv, String oldUrl);

    /**
     * Listen to the event where the postal address of a source has been changed.
     * If oldPostalAddress  is null, it means that a postal address  has been added to the source.
     * If the postal address  of the source is null, it means that the postal address  of the source has been removed
     * @param cv
     * @param oldPostalAddress
     */
    public void onPostalAddressUpdate(Source cv, String oldPostalAddress);

    /**
     * Listen to the event where the publication of a source has been changed.
     * If oldPublication  is null, it means that a publication  has been added to the source.
     * If the publication of the source is null, it means that the publication of the source has been removed
     * @param cv
     * @param oldPublication
     */
    public void onPublicationUpdate(Source cv, Publication oldPublication);
}
