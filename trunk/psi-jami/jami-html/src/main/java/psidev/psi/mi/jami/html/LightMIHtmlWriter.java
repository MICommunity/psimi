package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Writer for light interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/12/13</pre>
 */

public class LightMIHtmlWriter extends AbstractMIHtmlWriter<Interaction, Participant, Feature>{

    public LightMIHtmlWriter() {
    }

    public LightMIHtmlWriter(File file) throws IOException {
        super(file);
    }

    public LightMIHtmlWriter(OutputStream output) {
        super(output);
    }

    public LightMIHtmlWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void writeCooperativeEffects(Interaction interaction) {
        // do nothing
    }

    @Override
    protected void writeConfidences(Interaction interaction) throws IOException {
        // do nothing
    }

    @Override
    protected void writeParameters(Interaction interaction) throws IOException {
        // do nothing
    }

    @Override
    protected void writeDetectionMethods(Feature feature) throws IOException {
        // do nothing
    }

    @Override
    protected void writeGeneralProperties(Interaction interaction) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExperiment(Interaction interaction) throws IOException {
        // do nothing
    }

    @Override
    protected void writeConfidences(Participant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeParameters(Participant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExperimentalPreparations(Participant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExpressedInOrganism(Participant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExperimentalRole(Participant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeParticipantIdentificationMethods(Participant participant) throws IOException {
        // do nothing
    }
}
