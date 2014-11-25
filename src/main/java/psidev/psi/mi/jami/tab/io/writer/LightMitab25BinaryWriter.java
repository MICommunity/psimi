package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.5 writer for light binary interactions (no experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class LightMitab25BinaryWriter extends AbstractMitab25BinaryWriter<BinaryInteraction, Participant>{

    public LightMitab25BinaryWriter() {
        super();
    }

    public LightMitab25BinaryWriter(File file) throws IOException {
        super(file);
    }

    public LightMitab25BinaryWriter(OutputStream output) {
        super(output);
    }

    public LightMitab25BinaryWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultMitabColumnFeeder(getWriter()));
    }
}
