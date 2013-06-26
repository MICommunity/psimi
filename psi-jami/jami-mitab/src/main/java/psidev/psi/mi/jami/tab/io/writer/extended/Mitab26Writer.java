package psidev.psi.mi.jami.tab.io.writer.extended;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.tab.MitabVersion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The simple MITAB 2.6 writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public class Mitab26Writer extends Mitab25Writer {

    public Mitab26Writer() {
        super();
    }

    public Mitab26Writer(File file) throws IOException {
        super(file);
    }

    public Mitab26Writer(OutputStream output) {
        super(output);
    }

    public Mitab26Writer(Writer writer) {
        super(writer);
    }

    public Mitab26Writer(File file, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab26Writer(OutputStream output, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(output, expansionMethod);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Mitab26Writer(Writer writer, ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    public MitabVersion getVersion() {
        return MitabVersion.v2_6;
    }

    @Override
    protected void initialiseSubWriters() {
        setModelledInteractionWriter(new Mitab26ModelledInteractionWriter(getWriter()));
        setInteractionEvidenceWriter(new Mitab26InteractionEvidenceWriter(getWriter()));
    }
}
