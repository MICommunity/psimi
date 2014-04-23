package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 basic interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlInteractionIterator extends AbstractXmlIterator<Interaction<? extends Participant>> {
    public XmlInteractionIterator(PsiXmlParser<Interaction<? extends Participant>> lineParser) throws MIIOException {
        super(lineParser);
    }
}
