package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.datasource.InteractionEvidenceSource;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Interface for Crosslink CSV sources that can parse full CSV files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface CsvSource<I extends InteractionEvidence> extends CsvStreamSource<I>, InteractionEvidenceSource<I> {
}
