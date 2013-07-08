package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

/**
 * A MItab iterator of modelled BinaryInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabModelledInteractionIterator extends AbstractMitabIterator<ModelledInteraction, ModelledParticipant>{
    public MitabModelledInteractionIterator(MitabLineParser<ModelledInteraction, ModelledParticipant> lineParser) throws MIIOException {
        super(lineParser);
    }
}
