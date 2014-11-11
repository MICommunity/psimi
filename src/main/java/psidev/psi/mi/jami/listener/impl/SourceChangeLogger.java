package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.SourceChangeListener;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just log Source change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class SourceChangeLogger extends CvTermChangeLogger implements SourceChangeListener {

    private static final Logger sourceChangeLogger = Logger.getLogger("SourceChangeLogger");

    public void onUrlUpdate(Source cv, String oldUrl) {
        if (oldUrl == null){
            sourceChangeLogger.log(Level.INFO, "The URL " + cv.getUrl() + " has been added to the source " + cv.toString());
        }
        else if (cv.getUrl() == null){
            sourceChangeLogger.log(Level.INFO, "The URL " + oldUrl + " has been removed from the source " + cv.toString());
        }
        else {
            sourceChangeLogger.log(Level.INFO, "The URL " + oldUrl + " has been updated with " + cv.getUrl() + " in the source " + cv.toString());
        }
    }

    public void onPostalAddressUpdate(Source cv, String oldPostalAddress) {
        if (oldPostalAddress == null){
            sourceChangeLogger.log(Level.INFO, "The postal address " + cv.getPostalAddress() + " has been added to the source " + cv.toString());
        }
        else if (cv.getPostalAddress() == null){
            sourceChangeLogger.log(Level.INFO, "The postal address " + oldPostalAddress + " has been removed from the source " + cv.toString());
        }
        else {
            sourceChangeLogger.log(Level.INFO, "The postal address " + oldPostalAddress + " has been updated with " + cv.getPostalAddress() + " in the source " + cv.toString());
        }
    }

    public void onPublicationUpdate(Source cv, Publication oldPublication) {
        if (oldPublication == null){
            sourceChangeLogger.log(Level.INFO, "The publication " + cv.getPublication() + " has been added to the source " + cv.toString());
        }
        else if (cv.getPublication() == null){
            sourceChangeLogger.log(Level.INFO, "The publication " + oldPublication + " has been removed from the source " + cv.toString());
        }
        else {
            sourceChangeLogger.log(Level.INFO, "The publication " + oldPublication + " has been updated with " + cv.getPublication() + " in the source " + cv.toString());
        }
    }
}
