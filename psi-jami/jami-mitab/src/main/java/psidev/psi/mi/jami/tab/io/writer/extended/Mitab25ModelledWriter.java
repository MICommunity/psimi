package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.writer.AbstractMitabWriter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The mitab 2.5 writer for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab25ModelledWriter extends AbstractMitabWriter<ModelledInteraction, ModelledBinaryInteraction, ModelledParticipant> {

    public Mitab25ModelledWriter() {
        super();
    }

    public Mitab25ModelledWriter(File file) throws IOException {
        super(file);
    }

    public Mitab25ModelledWriter(OutputStream output) {
        super(output);
    }

    public Mitab25ModelledWriter(Writer writer) {
        super(writer);
    }

    public Mitab25ModelledWriter(File file, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab25ModelledWriter(OutputStream output, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) {
        super(output, expansionMethod);
    }

    public Mitab25ModelledWriter(Writer writer, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_5;
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new ModelledInteractionSpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new Mitab25ModelledBinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab25ModelledBinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab25ModelledBinaryWriter(file));
    }
}
