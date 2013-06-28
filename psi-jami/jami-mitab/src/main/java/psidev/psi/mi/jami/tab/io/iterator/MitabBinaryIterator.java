package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

/**
 * A MItab iterator of BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabBinaryIterator extends AbstractMitabIterator<Interaction, Participant>{
    public MitabBinaryIterator(MitabLineParser<Interaction, Participant> lineParser) {
        super(lineParser);
    }
}
