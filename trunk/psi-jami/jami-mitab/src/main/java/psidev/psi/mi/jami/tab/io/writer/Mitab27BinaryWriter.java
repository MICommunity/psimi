package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitab27ColumnFeeder;

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

public class Mitab27BinaryWriter extends AbstractMitab26BinaryWriter<BinaryInteraction, Participant>{

    private Mitab27ModelledBinaryWriter modelledBinaryWriter;
    private Mitab27BinaryEvidenceWriter binaryEvidenceWriter;

    public Mitab27BinaryWriter() {
        super();
    }

    public Mitab27BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab27BinaryWriter(OutputStream output) {
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
    public void flush() throws DataSourceWriterException {
        super.flush();
        this.binaryEvidenceWriter.flush();
        this.modelledBinaryWriter.flush();
    }

    @Override
    public void close() throws DataSourceWriterException {
        super.close();
    }

    @Override
    public void write(BinaryInteraction interaction) throws DataSourceWriterException {
        if (interaction instanceof BinaryInteractionEvidence){
            this.binaryEvidenceWriter.write((BinaryInteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledBinaryInteraction){
            this.modelledBinaryWriter.write((ModelledBinaryInteraction) interaction);
        }
        else {
            super.write(interaction);
        }
    }

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledBinaryWriter = new Mitab27ModelledBinaryWriter(writer);
        this.binaryEvidenceWriter = new Mitab27BinaryEvidenceWriter(writer);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultMitab27ColumnFeeder(getWriter()));
    }
}