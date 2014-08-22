package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.7 writer for light interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class LightMitab27Writer extends LightMitab26Writer {

    public LightMitab27Writer() {
        super();
    }

    public LightMitab27Writer(File file) throws IOException {
        super(file);
    }

    public LightMitab27Writer(OutputStream output) {
        super(output);
    }

    public LightMitab27Writer(Writer writer) {
        super(writer);
    }

    public LightMitab27Writer(File file, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public LightMitab27Writer(OutputStream output, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        super(output, expansionMethod);
    }

    public LightMitab27Writer(Writer writer, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_7;
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new LightMitab27BinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new LightMitab27BinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new LightMitab27BinaryWriter(file));
    }
}