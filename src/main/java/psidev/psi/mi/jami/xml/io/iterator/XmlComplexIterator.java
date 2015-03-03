package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.xml.io.parser.PsiXmlParser;

/**
 * Xml 2.5 complex iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlComplexIterator extends AbstractXmlIterator<Complex> {
    public XmlComplexIterator(PsiXmlParser<Complex> lineParser) throws MIIOException {
        super(lineParser);
    }
}
