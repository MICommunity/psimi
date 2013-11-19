package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.6 writer for light binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class LightMitab26BinaryWriter extends AbstractMitab26BinaryWriter<BinaryInteraction, Participant> {

    public LightMitab26BinaryWriter() {
        super();
    }

    public LightMitab26BinaryWriter(File file) throws IOException {
        super(file);
    }

    public LightMitab26BinaryWriter(OutputStream output) {
        super(output);
    }

    public LightMitab26BinaryWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultMitabColumnFeeder(getWriter()));
    }
}
