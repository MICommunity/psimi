package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.parser.PsiXml25Parser;

/**
 * Xml 2.5 modelled interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25ModelledInteractionIterator extends AbstractXml25Iterator<ModelledInteraction>{
    public Xml25ModelledInteractionIterator(PsiXml25Parser<ModelledInteraction> lineParser) throws MIIOException {
        super(lineParser);
    }
}
