package psidev.psi.mi.jami.xml.listener;

import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.xml.XmlIdReference;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface PsiXmlParserListener extends MIFileParserListener{

    public void onUnresolvedReference(XmlIdReference ref, String message);
}
