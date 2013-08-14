package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * Basic interface for Mitab features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface MitabFeature<P extends Participant, F extends Feature> extends Feature<P,F>, FileSourceContext {

    public String getText();

    public void setText(String text);
}
