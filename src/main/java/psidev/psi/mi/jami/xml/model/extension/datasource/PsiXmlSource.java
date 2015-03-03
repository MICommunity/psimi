package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Interface for PSI-MI XML sources that can parse full PSI-MI XML files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface PsiXmlSource<I extends Interaction> extends PsiXmlStreamSource<I>, InteractionSource<I> {
}
