package psidev.psi.mi.jami.tab.extension.datasource;

import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Interface for MITAB sources that can parse full MITAB files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface MitabSource<I extends Interaction> extends MitabStreamSource<I>, InteractionSource<I> {
}
