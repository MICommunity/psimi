package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The jsonWriter which writes the interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonEvidenceWriter extends AbstractMIJsonWriter<InteractionEvidence,BinaryInteractionEvidence> {


    public MIJsonEvidenceWriter(){
        super();
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException {
        super(file, fetcher, expansionMethod);
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(output, fetcher, expansionMethod);
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        super(writer, fetcher, expansionMethod);
    }

    @Override
    protected void initialiseDefaultExpansionMethod() {
        super.setExpansionMethod(new InteractionEvidenceSpokeExpansion());
    }

    @Override
    protected void initialiseBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super.setBinaryWriter(new MIJsonBinaryEvidenceWriter(file, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonBinaryEvidenceWriter(output, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonBinaryEvidenceWriter(writer, fetcher));
    }

    @Override
    protected void initialiseDefaultBinaryWriter() {
        super.setBinaryWriter(new MIJsonBinaryEvidenceWriter());

    }
}
