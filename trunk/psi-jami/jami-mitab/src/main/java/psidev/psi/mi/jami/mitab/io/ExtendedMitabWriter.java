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
 * The MITAB extended writer will write interactions and make the assumptions that all objects are MITAB extended objects.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with MitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * The default Complex expansion method is spoke expansion.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ExtendedMitabWriter extends AbstractMitabWriter{

    public ExtendedMitabWriter() {
        super();
    }

    public ExtendedMitabWriter(File file) throws IOException {
        super(file);
    }

    public ExtendedMitabWriter(OutputStream output) throws IOException {
        super(output);
    }

    public ExtendedMitabWriter(Writer writer) throws IOException {
        super(writer);
    }

    public ExtendedMitabWriter(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public ExtendedMitabWriter(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public ExtendedMitabWriter(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    protected void writeParticipantAliases(Participant a, Participant b) throws IOException {
        // alias A
        MitabWriterUtils.writeAliases(a, getWriter(), true);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // alias B
        MitabWriterUtils.writeAliases(b, getWriter(), true);
    }

    @Override
    protected void writeParticipantFeatures(Participant a, Participant b) throws IOException {
        // write features A
        MitabWriterUtils.writeParticipantFeatures(a, getWriter(), true);
        getWriter().write(MitabWriterUtils.COLUMN_SEPARATOR);
        // write features B
        MitabWriterUtils.writeParticipantFeatures(b, getWriter(), true);
    }

    @Override
    protected void writeConfidences(ModelledBinaryInteraction interaction) throws IOException {
        // write interaction confidence
        MitabWriterUtils.writeInteractionConfidences(interaction, getWriter(), true);
    }

    @Override
    protected void writeConfidences(BinaryInteractionEvidence interaction) throws IOException {
        // write interaction confidence
        MitabWriterUtils.writeInteractionConfidences(interaction, getWriter(), true);
    }
}
