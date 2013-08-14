package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.io.writer.AbstractMitab26BinaryWriter;
import psidev.psi.mi.jami.tab.io.writer.feeder.extended.ExtendedMitabInteractionEvidenceFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.6 writer for binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26BinaryEvidenceWriter extends AbstractMitab26BinaryWriter<BinaryInteractionEvidence, ParticipantEvidence> {

    public Mitab26BinaryEvidenceWriter() {
        super();
    }

    public Mitab26BinaryEvidenceWriter(File file) throws IOException {
        super(file);
    }

    public Mitab26BinaryEvidenceWriter(OutputStream output) {
        super(output);
    }

    public Mitab26BinaryEvidenceWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new ExtendedMitabInteractionEvidenceFeeder(getWriter()));
    }
}
