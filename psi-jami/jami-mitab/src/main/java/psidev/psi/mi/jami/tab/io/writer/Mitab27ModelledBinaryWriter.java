package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab27ModelledInteractionFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.7 writer for modelled binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab27ModelledBinaryWriter extends AbstractMitab27BinaryWriter<ModelledBinaryInteraction, ModelledParticipant> {

    public Mitab27ModelledBinaryWriter() {
        super();
    }

    public Mitab27ModelledBinaryWriter(File file) throws IOException {
        super(file);
    }

    public Mitab27ModelledBinaryWriter(OutputStream output) {
        super(output);
    }

    public Mitab27ModelledBinaryWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new Mitab27ModelledInteractionFeeder(getWriter()));
    }
}