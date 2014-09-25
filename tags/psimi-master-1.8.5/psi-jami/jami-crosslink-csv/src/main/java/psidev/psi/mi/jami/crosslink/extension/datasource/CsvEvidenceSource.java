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
 * CrossLink CVS source of interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/08/14</pre>
 */

public class CsvEvidenceSource extends AbstractCsvSource<InteractionEvidence>{

    public CsvEvidenceSource() {
        super();
    }

    public CsvEvidenceSource(File file) throws IOException {
        super(file);
    }

    public CsvEvidenceSource(InputStream input) {
        super(input);
    }

    public CsvEvidenceSource(Reader reader) {
        super(reader);
    }

    public CsvEvidenceSource(URL url) {
        super(url);
    }

    @Override
    protected AbstractCsvInteractionEvidenceParser<InteractionEvidence> instantiateLineParser() {
        return new CsvInteractionEvidenceParser();
    }
}
