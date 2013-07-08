package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

/**
 * A MItab iterator of modelled BinaryInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabModelledBinaryIterator extends AbstractMitabIterator<ModelledBinaryInteraction, ModelledParticipant>{
    public MitabModelledBinaryIterator(MitabLineParser<ModelledBinaryInteraction, ModelledParticipant> lineParser) throws MIIOException {
        super(lineParser);
    }
}
