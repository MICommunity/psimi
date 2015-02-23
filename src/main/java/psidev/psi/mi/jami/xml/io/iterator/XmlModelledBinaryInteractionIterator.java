package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 modelled binary interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlModelledBinaryInteractionIterator extends AbstractXmlIterator<ModelledBinaryInteraction> {
    public XmlModelledBinaryInteractionIterator(PsiXmlParser<ModelledBinaryInteraction> lineParser) throws MIIOException {
        super(lineParser);
    }
}
