package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The simple MITAB writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class DefaultMitabWriter extends AbstractMitabWriter {

    public DefaultMitabWriter() {
        super();
    }

    public DefaultMitabWriter(File file) throws IOException {
        super(file);
    }

    public DefaultMitabWriter(OutputStream output) throws IOException {
        super(output);
    }

    public DefaultMitabWriter(Writer writer) throws IOException {
        super(writer);
    }

    public DefaultMitabWriter(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public DefaultMitabWriter(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public DefaultMitabWriter(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    protected void writeParticipantAliases(Participant a, Participant b) throws IOException {
        // alias A
        MitabWriterUtils.writeAliases(a, getWriter(), false);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // alias B
        MitabWriterUtils.writeAliases(b, getWriter(), false);
    }

    @Override
    protected void writeParticipantFeatures(Participant a, Participant b) throws IOException {
        // write features A
        MitabWriterUtils.writeParticipantFeatures(a, getWriter(), false);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write features B
        MitabWriterUtils.writeParticipantFeatures(b, getWriter(), false);
    }

    @Override
    protected void writeConfidences(ModelledBinaryInteraction interaction) throws IOException {
        // write interaction confidence
        MitabWriterUtils.writeInteractionConfidences(interaction, getWriter(), false);
    }

    @Override
    protected void writeConfidences(BinaryInteractionEvidence interaction) throws IOException {
        // write interaction confidence
        MitabWriterUtils.writeInteractionConfidences(interaction, getWriter(), false);
    }
}
