package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.Interaction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The jsonWriter which writes the light interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class LightMIJsonWriter extends AbstractMIJsonWriter<Interaction,BinaryInteraction> {


    public LightMIJsonWriter(){
        super();
    }

    public LightMIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
    }

    public LightMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
    }

    public LightMIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
    }

    public LightMIJsonWriter(File file, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction,BinaryInteraction> expansionMethod) throws IOException {
        super(file, fetcher, expansionMethod);
    }

    public LightMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction,BinaryInteraction> expansionMethod) {
        super(output, fetcher, expansionMethod);
    }

    public LightMIJsonWriter(Writer writer, OntologyTermFetcher fetcher, ComplexExpansionMethod<Interaction,BinaryInteraction> expansionMethod) {
        super(writer, fetcher, expansionMethod);
    }

    @Override
    protected void initialiseDefaultExpansionMethod() {
        super.setExpansionMethod(new SpokeExpansion());
    }

    @Override
    protected void initialiseBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super.setBinaryWriter(new LightMIJsonBinaryWriter(file, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new LightMIJsonBinaryWriter(output, fetcher));
    }

    @Override
    protected void initialiseBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super.setBinaryWriter(new LightMIJsonBinaryWriter(writer, fetcher));
    }

    @Override
    protected void initialiseDefaultBinaryWriter() {
        super.setBinaryWriter(new LightMIJsonBinaryWriter());

    }
}
