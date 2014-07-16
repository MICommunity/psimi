package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * JSON writer for light interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class LightMIJsonBinaryWriter extends AbstractMIJsonBinaryWriter<BinaryInteraction> {

    public LightMIJsonBinaryWriter(){
        super();
    }

    public LightMIJsonBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
    }

    public LightMIJsonBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
    }

    public LightMIJsonBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
    }

    @Override
    protected void writeFeatureProperties(Feature object) throws IOException {
        // nothing to do
    }

    @Override
    protected void writeParticipantProperties(Participant object) throws IOException {
        // nothing to do
    }

    @Override
    protected boolean writeInteractionProperties(BinaryInteraction interaction) throws IOException {
        return false;
    }


    @Override
    protected void writeParameters(BinaryInteraction binary) throws IOException {
        // nothing to do
    }

    @Override
    protected void writeConfidences(BinaryInteraction binary) throws IOException {
        // nothing to do
    }

    @Override
    protected String extractImexIdFrom(BinaryInteraction binary) {
        return null;
    }
}
