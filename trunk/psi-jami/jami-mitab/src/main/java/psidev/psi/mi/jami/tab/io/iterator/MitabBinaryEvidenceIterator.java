package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

/**
 * A MItab iterator of BinaryInteraction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabBinaryEvidenceIterator extends AbstractMitabIterator<InteractionEvidence, BinaryInteractionEvidence, ParticipantEvidence>{
    public MitabBinaryEvidenceIterator(MitabLineParser<BinaryInteractionEvidence, ParticipantEvidence> lineParser) {
        super(lineParser);
    }
}
