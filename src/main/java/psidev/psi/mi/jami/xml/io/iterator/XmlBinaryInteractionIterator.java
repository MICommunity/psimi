package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 basic binary interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlBinaryInteractionIterator extends AbstractXmlIterator<BinaryInteraction> {
    public XmlBinaryInteractionIterator(PsiXmlParser<BinaryInteraction> lineParser) throws MIIOException {
        super(lineParser);
    }
}
