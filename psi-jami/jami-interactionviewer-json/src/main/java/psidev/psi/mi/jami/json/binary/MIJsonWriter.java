package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The jsonWriter which writes all interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonWriter extends AbstractMIJsonWriter<Interaction<? extends Participant>,BinaryInteraction> {


    public MIJsonWriter(){
        super();
    }

    public MIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public MIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public MIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public MIJsonWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(file, fetcher, expansionMethod);
    }

    public MIJsonWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(output, fetcher, expansionMethod);
    }

    public MIJsonWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(writer, fetcher, expansionMethod);
    }

    @Override
    protected void initialiseDefaultExpansionMethod() {
        super.setExpansionMethod(new SpokeExpansion());
    }

    @Override
    protected void initialiseBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super.setBinaryWriter(new MIJsonBinaryWriter(file, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonBinaryWriter(output, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonBinaryWriter(writer, fetcher));
    }

    @Override
    protected void initialiseDefaultBinaryWriter() {
        super.setBinaryWriter(new MIJsonBinaryWriter());

    }
}
