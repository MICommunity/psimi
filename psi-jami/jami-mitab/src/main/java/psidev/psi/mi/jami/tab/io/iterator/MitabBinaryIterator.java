package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

/**
 * A MItab iterator of BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabBinaryIterator extends AbstractMitabIterator<BinaryInteraction, Participant, Feature>{
    public MitabBinaryIterator(MitabLineParser<BinaryInteraction, Participant, Feature> lineParser) throws MIIOException {
        super(lineParser);
    }
}
