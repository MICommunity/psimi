package psidev.psi.mi.jami.crosslink.io.parser;

import psidev.psi.mi.jami.crosslink.extension.CsvInteractionEvidence;
import psidev.psi.mi.jami.crosslink.extension.CsvSourceLocator;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Interaction evidence crosslink CSV parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CsvInteractionEvidenceParser extends AbstractCsvInteractionEvidenceParser<InteractionEvidence> {
    @Override
    protected InteractionEvidence instantiateInteractionEvidence(int linePosition, String bait) {
        CsvInteractionEvidence interaction = new CsvInteractionEvidence();
        interaction.setSourceLocator(new CsvSourceLocator(linePosition, 1, -1));
        interaction.setBait(bait);
        return interaction;
    }
}
