package psidev.psi.mi.jami.tab.io.writer.extended;

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
 * The simple MITAB 2.7 writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and DefaultMitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public class Mitab27Writer extends Mitab26Writer {

    public Mitab27Writer() {
        super();
    }

    public Mitab27Writer(File file) throws IOException {
        super(file);
    }

    public Mitab27Writer(OutputStream output) {
        super(output);
    }

    public Mitab27Writer(Writer writer) {
        super(writer);
    }

    public Mitab27Writer(File file, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab27Writer(OutputStream output, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public Mitab27Writer(Writer writer, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_7;
    }

    @Override
    protected void initialiseSubWriters() {
        setBinaryWriter(new Mitab27BinaryWriter(getWriter()));
        setModelledInteractionWriter(new Mitab27ModelledInteractionWriter(getWriter()));
        setInteractionEvidenceWriter(new Mitab27InteractionEvidenceWriter(getWriter()));
    }
}