package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The jsonWriter which writes the modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonModelledWriter extends AbstractMIJsonWriter<ModelledInteraction,ModelledBinaryInteraction> {


    public MIJsonModelledWriter(){
        super();
    }

    public MIJsonModelledWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public MIJsonModelledWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public MIJsonModelledWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public MIJsonModelledWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> expansionMethod) throws IOException {
        super(file, fetcher, expansionMethod);
    }

    public MIJsonModelledWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> expansionMethod) {
        super(output, fetcher, expansionMethod);
    }

    public MIJsonModelledWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> expansionMethod) {
        super(writer, fetcher, expansionMethod);
    }

    @Override
    protected void initialiseDefaultExpansionMethod() {
        super.setExpansionMethod(new ModelledInteractionSpokeExpansion());
    }

    @Override
    protected void initialiseBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super.setBinaryWriter(new MIJsonModelledBinaryWriter(file, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonModelledBinaryWriter(output, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new MIJsonModelledBinaryWriter(writer, fetcher));
    }

    @Override
    protected void initialiseDefaultBinaryWriter() {
        super.setBinaryWriter(new MIJsonModelledBinaryWriter());

    }
}
