package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The mitab 2.5 writer for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab25InteractionEvidenceWriter extends AbstractMitabWriter<InteractionEvidence, BinaryInteractionEvidence, ParticipantEvidence> {

    public Mitab25InteractionEvidenceWriter() {
        super();
    }

    public Mitab25InteractionEvidenceWriter(File file) throws IOException {
        super(file);
    }

    public Mitab25InteractionEvidenceWriter(OutputStream output) {
        super(output);
    }

    public Mitab25InteractionEvidenceWriter(Writer writer) {
        super(writer);
    }

    public Mitab25InteractionEvidenceWriter(OutputStream output, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(output, expansionMethod);
    }

    public Mitab25InteractionEvidenceWriter(File file, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab25InteractionEvidenceWriter(Writer writer, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_5;
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new InteractionEvidenceSpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer) {
        setBinaryWriter(new Mitab25BinaryEvidenceWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab25BinaryEvidenceWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab25BinaryEvidenceWriter(file));
    }
}
