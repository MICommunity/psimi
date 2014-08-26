package psidev.psi.mi.jami.crosslink.io.parser;

import psidev.psi.mi.jami.crosslink.extension.CsvBinaryInteractionEvidence;
import psidev.psi.mi.jami.crosslink.extension.CsvSourceLocator;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Binary Interaction evidence crosslink CSV parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CsvBinaryInteractionEvidenceParser extends AbstractCsvInteractionEvidenceParser<InteractionEvidence> {
    @Override
    protected InteractionEvidence instantiateInteractionEvidence(int linePosition) {
        CsvBinaryInteractionEvidence interaction = new CsvBinaryInteractionEvidence();
        interaction.setSourceLocator(new CsvSourceLocator(linePosition, 1, -1));
        return interaction;
    }
}
