package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.io.writer.feeder.MitabModelledInteractionFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.5 writer for modelled binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class Mitab25ModelledBinaryWriter extends AbstractMitab25BinaryWriter<ModelledBinaryInteraction, ModelledParticipant>{

    public Mitab25ModelledBinaryWriter() {
        super();
    }

    public Mitab25ModelledBinaryWriter(File file) throws IOException {
        super(file);
    }

    public Mitab25ModelledBinaryWriter(OutputStream output) {
        super(output);
    }

    public Mitab25ModelledBinaryWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new MitabModelledInteractionFeeder(getWriter()));
    }
}
