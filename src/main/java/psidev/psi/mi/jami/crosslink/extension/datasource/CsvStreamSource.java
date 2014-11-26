package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.crosslink.listener.CsvParserListener;
import psidev.psi.mi.jami.datasource.InteractionEvidenceStream;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Interface for Crosslink CSV datasources that can stream CSV files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface CsvStreamSource<I extends InteractionEvidence> extends MIFileDataSource, InteractionEvidenceStream<I>, CsvParserListener {
}
