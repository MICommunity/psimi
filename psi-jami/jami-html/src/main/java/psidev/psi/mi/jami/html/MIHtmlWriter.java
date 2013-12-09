package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

/**
 * MI writer for a mix of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/12/13</pre>
 */

public class MIHtmlWriter extends AbstractMIHtmlWriter<Interaction, Participant, Feature>{

    private MIEvidenceHtmlWriter evidenceWriter;
    private LightMIHtmlWriter lightWriter;

    public MIHtmlWriter() {
        super(new MIModelledHtmlWriter());
        this.evidenceWriter = new MIEvidenceHtmlWriter();
        this.lightWriter = new LightMIHtmlWriter();
    }

    public MIHtmlWriter(File file) throws IOException {
        super(file, new MIModelledHtmlWriter());
        this.evidenceWriter = new MIEvidenceHtmlWriter(getWriter());
        this.lightWriter = new LightMIHtmlWriter(getWriter());
    }

    public MIHtmlWriter(OutputStream output) {
        super(output, new MIModelledHtmlWriter());
        this.evidenceWriter = new MIEvidenceHtmlWriter(getWriter());
        this.lightWriter = new LightMIHtmlWriter(getWriter());
    }

    public MIHtmlWriter(Writer writer) {
        super(writer, new MIModelledHtmlWriter());
        this.evidenceWriter = new MIEvidenceHtmlWriter(getWriter());
        this.lightWriter = new LightMIHtmlWriter(getWriter());
    }

    @Override
    public void close() throws MIIOException {
        super.close();
        this.evidenceWriter = null;
        this.lightWriter = null;
    }

    @Override
    public void reset() throws MIIOException {
        super.reset();
        this.evidenceWriter = null;
        this.lightWriter = null;
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        this.evidenceWriter = new MIEvidenceHtmlWriter(getWriter());
        this.lightWriter = new LightMIHtmlWriter(getWriter());
    }

    @Override
    public void write(Iterator<? extends Interaction> interactions) throws MIIOException {
        if (getComplexWriter() == null || this.evidenceWriter == null || this.lightWriter == null){
            throw new IllegalStateException("The MI HTML writer was not initialised. The options for the HTML writer should contains at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    @Override
    public void write(Collection<? extends Interaction> interactions) throws MIIOException {
        write(interactions.iterator());
    }

    @Override
    public void write(Interaction interaction) throws MIIOException {
        if (getComplexesToWrite() == null || this.evidenceWriter == null || this.lightWriter == null){
            throw new IllegalStateException("The MI HTML writer was not initialised. The options for the HTML writer should contains at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        if (InteractionEvidence.class.isAssignableFrom(interaction.getClass())){
            this.evidenceWriter.write((InteractionEvidence)interaction);
        }
        else if (ModelledInteraction.class.isAssignableFrom(interaction.getClass())){
            getComplexWriter().write((ModelledInteraction)interaction);
        }
        else{
            this.lightWriter.write(interaction);
        }
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
