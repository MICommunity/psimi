package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.BinaryInteractionEvidenceSource;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * A mitab datasource that loads interaction evidences (full experimental details).
 *
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class MitabBinaryEvidenceSource extends AbstractMitabSource<BinaryInteractionEvidence, ParticipantEvidence, FeatureEvidence> implements BinaryInteractionEvidenceSource{
    public MitabBinaryEvidenceSource() {
        super(new MitabBinaryEvidenceStreamSource());
    }

    public MitabBinaryEvidenceSource(File file) throws IOException {
        super(new MitabBinaryEvidenceStreamSource(file));
    }

    public MitabBinaryEvidenceSource(InputStream input) {
        super(new MitabBinaryEvidenceStreamSource(input));
    }

    public MitabBinaryEvidenceSource(Reader reader) {
        super(new MitabBinaryEvidenceStreamSource(reader));
    }
}
