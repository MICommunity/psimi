package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Mitab 2.6 writer for light interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class LightMitab26Writer extends LightMitab25Writer {

    public LightMitab26Writer() {
        super();
    }

    public LightMitab26Writer(File file) throws IOException {
        super(file);
    }

    public LightMitab26Writer(OutputStream output) {
        super(output);
    }

    public LightMitab26Writer(Writer writer) {
        super(writer);
    }

    public LightMitab26Writer(File file, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public LightMitab26Writer(OutputStream output, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(output, expansionMethod);
    }

    public LightMitab26Writer(Writer writer, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_6;
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new LightMitab26BinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new LightMitab26BinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new LightMitab26BinaryWriter(file));
    }
}