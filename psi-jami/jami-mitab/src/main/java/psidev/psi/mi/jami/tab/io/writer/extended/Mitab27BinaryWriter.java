package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.tab.io.writer.feeder.extended.DefaultExtendedMitabColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * The basic Mitab 2.7 writer for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab27BinaryWriter extends psidev.psi.mi.jami.tab.io.writer.Mitab27BinaryWriter{

    public Mitab27BinaryWriter() {
        super();
    }

    public Mitab27BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab27BinaryWriter(OutputStream output){
        super(output);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab27BinaryWriter(Writer writer) {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    @Override
    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultExtendedMitabColumnFeeder(getWriter()));
    }

    @Override
    protected void initialiseSubWritersWith(Writer writer) {

        setModelledBinaryWriter(new Mitab27ModelledBinaryWriter(writer));
        setBinaryEvidenceWriter(new Mitab27BinaryEvidenceWriter(writer));
    }
}