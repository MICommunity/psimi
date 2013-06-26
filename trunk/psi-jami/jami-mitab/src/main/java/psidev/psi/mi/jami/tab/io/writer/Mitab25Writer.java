package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

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

    public Mitab25Writer() {
        super();
    }

    public Mitab25Writer(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
    }

    public Mitab25Writer(OutputStream output) {
        super(output);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
    }

    public Mitab25Writer(Writer writer) {
        super(writer);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
    }

    public Mitab25Writer(File file, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
    }

    public Mitab25Writer(OutputStream output, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(output, expansionMethod);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
    }

    public Mitab25Writer(Writer writer, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(writer, expansionMethod);
        initialiseSubWritersWith(getBinaryWriter().getWriter());
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

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_5;
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer) {
        setBinaryWriter(new Mitab25BinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab25BinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab25BinaryWriter(file));
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
