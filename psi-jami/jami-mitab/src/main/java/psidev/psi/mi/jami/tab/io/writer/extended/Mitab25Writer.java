package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.AbstractMitab25Writer;

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

public class Mitab25Writer extends AbstractMitab25Writer<Interaction, BinaryInteraction, Participant> {

    private Mitab25ModelledInteractionWriter modelledInteractionWriter;
    private Mitab25InteractionEvidenceWriter interactionEvidenceWriter;
    private Writer writer;

    public Mitab25Writer() {
        super();
    }

    public Mitab25Writer(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(writer);
    }

    public Mitab25Writer(OutputStream output) {
        super(output);
        initialiseSubWritersWith(writer);
    }

    public Mitab25Writer(Writer writer) {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    public Mitab25Writer(File file, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
        initialiseSubWritersWith(writer);
    }

    public Mitab25Writer(OutputStream output, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(output, expansionMethod);
        initialiseSubWritersWith(writer);
    }

    public Mitab25Writer(Writer writer, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(writer, expansionMethod);
        initialiseSubWritersWith(writer);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer) {
        this.writer = writer;
        setBinaryWriter(new psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryWriter(this.writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        this.writer = new OutputStreamWriter(output);
        setBinaryWriter(new psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryWriter(this.writer));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(file));
        setBinaryWriter(new Mitab25BinaryWriter(this.writer));
    }

    @Override
    public void write(Interaction interaction) throws DataSourceWriterException {
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

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledInteractionWriter = new Mitab25ModelledInteractionWriter(writer);
        this.interactionEvidenceWriter = new Mitab25InteractionEvidenceWriter(writer);
    }

    protected void setModelledInteractionWriter(Mitab25ModelledInteractionWriter modelledInteractionWriter) {
        this.modelledInteractionWriter = modelledInteractionWriter;
    }

    protected void setInteractionEvidenceWriter(Mitab25InteractionEvidenceWriter interactionEvidenceWriter) {
        this.interactionEvidenceWriter = interactionEvidenceWriter;
    }
}
