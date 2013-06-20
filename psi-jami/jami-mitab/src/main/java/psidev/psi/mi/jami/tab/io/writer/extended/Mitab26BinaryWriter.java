package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.tab.io.writer.feeder.extended.DefaultExtendedMitab26ColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The basic Mitab 2.6 writer for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26BinaryWriter extends psidev.psi.mi.jami.tab.io.writer.Mitab26BinaryWriter{

    public Mitab26BinaryWriter() {
        super();
    }

    public Mitab26BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab26BinaryWriter(OutputStream output) throws IOException {
        super(output);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab26BinaryWriter(Writer writer) throws IOException {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultExtendedMitab26ColumnFeeder(getWriter()));
    }
}
