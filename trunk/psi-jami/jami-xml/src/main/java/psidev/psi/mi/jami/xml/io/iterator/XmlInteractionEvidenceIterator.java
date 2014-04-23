package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 interaction evidence iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlInteractionEvidenceIterator extends AbstractXmlIterator<InteractionEvidence> {
    public XmlInteractionEvidenceIterator(PsiXmlParser<InteractionEvidence> lineParser) throws MIIOException {
        super(lineParser);
    }
}
