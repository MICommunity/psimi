package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 binary interaction evidence iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */
public class XmlBinaryInteractionEvidenceIterator extends AbstractXmlIterator<BinaryInteractionEvidence> {
    public XmlBinaryInteractionEvidenceIterator(PsiXmlParser<BinaryInteractionEvidence> lineParser) throws MIIOException {
        super(lineParser);
    }
}
