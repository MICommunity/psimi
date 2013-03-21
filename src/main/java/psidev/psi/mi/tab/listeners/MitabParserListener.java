package psidev.psi.mi.tab.listeners;

import psidev.psi.mi.tab.events.ClusteredColumnEvent;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.events.MissingCvEvent;

import java.util.EventListener;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface MitabParserListener extends EventListener {

     public void fireOnInvalidFormat(InvalidFormatEvent event);

    public void fireOnClusteredColumnEvent(ClusteredColumnEvent event);

    public void fireOnMissingCvEvent(MissingCvEvent event);
}
