package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.xml.io.parser.PsiXml25Parser;

/**
 * Xml 2.5 modelled binary interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25ModelledBinaryInteractionIterator extends AbstractXml25Iterator<ModelledBinaryInteraction>{
    public Xml25ModelledBinaryInteractionIterator(PsiXml25Parser<ModelledBinaryInteraction> lineParser) throws MIIOException {
        super(lineParser);
    }
}
