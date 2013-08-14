package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

import java.util.Date;

/**
 * Factory for publications
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class PublicationUtils {
    
    public static Publication createUnknownBasicPublication(){

        return new DefaultPublication("unknown", "unknown", (Date)null);
    }
}
