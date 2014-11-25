package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.7 writer for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab27EvidenceWriter extends Mitab26EvidenceWriter {

    public Mitab27EvidenceWriter() {
    super();
}

    public Mitab27EvidenceWriter(File file) throws IOException {
        super(file);
    }

    public Mitab27EvidenceWriter(OutputStream output) {
        super(output);
    }

    public Mitab27EvidenceWriter(Writer writer) {
        super(writer);
    }

    public Mitab27EvidenceWriter(OutputStream output, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(output, expansionMethod);
    }

    public Mitab27EvidenceWriter(File file, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab27EvidenceWriter(Writer writer, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_7;
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new Mitab27BinaryEvidenceWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab27BinaryEvidenceWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab27BinaryEvidenceWriter(file));
    }
}