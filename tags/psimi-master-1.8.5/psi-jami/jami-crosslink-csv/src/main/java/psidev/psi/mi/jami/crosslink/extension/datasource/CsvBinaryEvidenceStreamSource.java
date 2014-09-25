package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.crosslink.io.parser.AbstractCsvInteractionEvidenceParser;
import psidev.psi.mi.jami.crosslink.io.parser.CsvBinaryInteractionEvidenceParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * CrossLink CVS stream source of binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CsvBinaryEvidenceStreamSource extends AbstractCsvStreamSource<BinaryInteractionEvidence>{

    public CsvBinaryEvidenceStreamSource() {
        super();
    }

    public CsvBinaryEvidenceStreamSource(InputStream input) {
        super(input);
    }

    public CsvBinaryEvidenceStreamSource(File file) throws IOException {
        super(file);
    }

    public CsvBinaryEvidenceStreamSource(Reader reader) {
        super(reader);
    }

    public CsvBinaryEvidenceStreamSource(URL url) {
        super(url);
    }

    @Override
    protected AbstractCsvInteractionEvidenceParser<BinaryInteractionEvidence> instantiateLineParser() {
        return new CsvBinaryInteractionEvidenceParser();
    }
}
