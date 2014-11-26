package psidev.psi.mi.jami.crosslink.extension.datasource;

import psidev.psi.mi.jami.crosslink.io.parser.AbstractCsvInteractionEvidenceParser;
import psidev.psi.mi.jami.crosslink.io.parser.CsvInteractionEvidenceParser;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * CrossLink CVS stream source of interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CsvEvidenceStreamSource extends AbstractCsvStreamSource<InteractionEvidence>{

    public CsvEvidenceStreamSource() {
        super();
    }

    public CsvEvidenceStreamSource(File file) throws IOException {
        super(file);
    }

    public CsvEvidenceStreamSource(InputStream input) {
        super(input);
    }

    public CsvEvidenceStreamSource(URL url) {
        super(url);
    }

    public CsvEvidenceStreamSource(Reader reader) {
        super(reader);
    }

    @Override
    protected AbstractCsvInteractionEvidenceParser<InteractionEvidence> instantiateLineParser() {
        return new CsvInteractionEvidenceParser();
    }
}
