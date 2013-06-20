package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.tab.io.writer.feeder.extended.DefaultExtendedMitab25ColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The basic Mitab 2.5 writer for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class Mitab25BinaryWriter extends psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryWriter{

    public Mitab25BinaryWriter() {
        super();
    }

    public Mitab25BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab25BinaryWriter(OutputStream output) throws IOException {
        super(output);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab25BinaryWriter(Writer writer) throws IOException {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultExtendedMitab25ColumnFeeder(getWriter()));
    }
}
