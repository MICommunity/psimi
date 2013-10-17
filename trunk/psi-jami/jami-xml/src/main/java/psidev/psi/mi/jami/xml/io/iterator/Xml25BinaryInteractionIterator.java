package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.io.parser.PsiXml25Parser;

/**
 * Xml 2.5 basic binary interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25BinaryInteractionIterator extends AbstractXml25Iterator<BinaryInteraction>{
    public Xml25BinaryInteractionIterator(PsiXml25Parser<BinaryInteraction> lineParser) throws MIIOException {
        super(lineParser);
    }
}
