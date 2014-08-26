package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.crosslink.io.parser.AbstractCsvInteractionEvidenceParser;
import psidev.psi.mi.jami.crosslink.io.parser.CsvBinaryInteractionEvidenceParser;

/**
 * CrossLink CVS stream source of binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CvsBinaryEvidenceStreamSource extends AbstractCsvStreamSource<BinaryInteractionEvidence>{
    @Override
    protected AbstractCsvInteractionEvidenceParser<BinaryInteractionEvidence> instantiateLineParser() {
        return new CsvBinaryInteractionEvidenceParser();
    }
}
