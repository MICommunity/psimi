package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.writer.AbstractMitabWriter;

import java.io.*;

/**
 * The simple MITAB 2.5 writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class Mitab25Writer extends AbstractMitabWriter<Interaction, BinaryInteraction, Participant> {

    private Mitab25ModelledInteractionWriter modelledInteractionWriter;
    private Mitab25InteractionEvidenceWriter interactionEvidenceWriter;
    private Writer writer;

    public Mitab25Writer() {
        super();
    }

    public Mitab25Writer(File file) throws IOException {
        super(file);
    }

    public Mitab25Writer(OutputStream output) {
        super(output);
    }

    public Mitab25Writer(Writer writer) {
        super(writer);
    }

    public Mitab25Writer(File file, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab25Writer(OutputStream output, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public Mitab25Writer(Writer writer, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_5;
    }

    @Override
    public void close() throws DataSourceWriterException {
        try{
            super.close();
        }
        finally {
            this.modelledInteractionWriter = null;
            this.interactionEvidenceWriter = null;
            this.writer = null;
        }
    }

    @Override
    public void reset() throws DataSourceWriterException {
        try{
            super.reset();
        }
        finally {
            this.modelledInteractionWriter = null;
            this.interactionEvidenceWriter = null;
            this.writer = null;
        }
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer) {
        this.writer = writer;
        setBinaryWriter(new psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryWriter(this.writer));
        initialiseSubWriters();
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        this.writer = new OutputStreamWriter(output);
        setBinaryWriter(new psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryWriter(this.writer));
        initialiseSubWriters();
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(file));
        setBinaryWriter(new Mitab25BinaryWriter(this.writer));
        initialiseSubWriters();
    }

    @Override
    public void write(Interaction interaction) throws DataSourceWriterException {
        if (this.interactionEvidenceWriter == null || this.modelledInteractionWriter == null){
            throw new IllegalStateException("The Mitab writer has not been initialised. The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        // did not start yet so need to write the header if required
        else if (!hasWrittenHeader()){
            try{
                getBinaryWriter().writeHeaderIfNotDone();
                this.interactionEvidenceWriter.setHasWrittenHeader(false);
                this.modelledInteractionWriter.setHasWrittenHeader(false);
            }
            catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write MITAB header ", e);
            }
        }
        else{
            this.interactionEvidenceWriter.setHasWrittenHeader(true);
            this.modelledInteractionWriter.setHasWrittenHeader(true);
        }
        if (interaction instanceof InteractionEvidence){
            this.interactionEvidenceWriter.write((InteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledInteraction){
            this.modelledInteractionWriter.write((ModelledInteraction) interaction);
        }
        else{
            super.write(interaction);
        }
    }

    protected void initialiseSubWriters() {

        this.modelledInteractionWriter = new Mitab25ModelledInteractionWriter(writer);
        this.modelledInteractionWriter.setWriteHeader(false);
        this.interactionEvidenceWriter = new Mitab25InteractionEvidenceWriter(writer);
        this.interactionEvidenceWriter.setWriteHeader(false);
    }

    protected void setModelledInteractionWriter(Mitab25ModelledInteractionWriter modelledInteractionWriter) {
        this.modelledInteractionWriter = modelledInteractionWriter;
        this.modelledInteractionWriter.setWriteHeader(false);
    }

    protected void setInteractionEvidenceWriter(Mitab25InteractionEvidenceWriter interactionEvidenceWriter) {
        this.interactionEvidenceWriter = interactionEvidenceWriter;
        this.interactionEvidenceWriter.setWriteHeader(false);
    }

    protected Writer getWriter() {
        return writer;
    }
}
