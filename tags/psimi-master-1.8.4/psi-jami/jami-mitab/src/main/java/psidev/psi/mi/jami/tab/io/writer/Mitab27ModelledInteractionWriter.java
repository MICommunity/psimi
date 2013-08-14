package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.7 writer for Modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab27ModelledInteractionWriter extends Mitab26ModelledInteractionWriter {

    public Mitab27ModelledInteractionWriter() {
        super();
    }

    public Mitab27ModelledInteractionWriter(File file) throws IOException {
        super(file);
    }

    public Mitab27ModelledInteractionWriter(OutputStream output) {
        super(output);
    }

    public Mitab27ModelledInteractionWriter(Writer writer) {
        super(writer);
    }

    public Mitab27ModelledInteractionWriter(File file, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab27ModelledInteractionWriter(OutputStream output, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) {
        super(output, expansionMethod);
    }

    public Mitab27ModelledInteractionWriter(Writer writer, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_7;
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new Mitab27ModelledBinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new Mitab27ModelledBinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new Mitab27ModelledBinaryWriter(file));
    }
}