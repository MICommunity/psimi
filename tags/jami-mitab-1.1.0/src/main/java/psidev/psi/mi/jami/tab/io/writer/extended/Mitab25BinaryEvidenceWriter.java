package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.io.writer.AbstractMitab25BinaryWriter;
import psidev.psi.mi.jami.tab.io.writer.feeder.extended.ExtendedMitabInteractionEvidenceFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Extended Mitab 2.5 writer for binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class Mitab25BinaryEvidenceWriter extends AbstractMitab25BinaryWriter<BinaryInteractionEvidence, ParticipantEvidence> {

    public Mitab25BinaryEvidenceWriter() {
        super();
    }

    public Mitab25BinaryEvidenceWriter(File file) throws IOException {
        super(file);
    }

    public Mitab25BinaryEvidenceWriter(OutputStream output) {
        super(output);
    }

    public Mitab25BinaryEvidenceWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new ExtendedMitabInteractionEvidenceFeeder(getWriter()));
    }


}
