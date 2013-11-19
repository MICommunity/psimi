package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The mitab 2.5 writer for light Interaction  (no experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class LightMitab25Writer extends AbstractMitabWriter<Interaction<? extends Participant>, BinaryInteraction, Participant> {

    public LightMitab25Writer() {
        super();
    }

    public LightMitab25Writer(File file) throws IOException {
        super(file);
    }

    public LightMitab25Writer(OutputStream output) {
        super(output);
    }

    public LightMitab25Writer(Writer writer) {
        super(writer);
    }

    public LightMitab25Writer(File file, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public LightMitab25Writer(OutputStream output, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(output, expansionMethod);
    }

    public LightMitab25Writer(Writer writer, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_5;
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) {
        setExpansionMethod(expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    @Override
    protected void initialiseWriter(Writer writer){
        setBinaryWriter(new LightMitab25BinaryWriter(writer));
    }

    @Override
    protected void initialiseOutputStream(OutputStream output) {
        setBinaryWriter(new LightMitab25BinaryWriter(output));
    }

    @Override
    protected void initialiseFile(File file) throws IOException {
        setBinaryWriter(new LightMitab25BinaryWriter(file));
    }
}
