package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.writer.feeder.MitabColumnFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Abstract class for MITAB 2.6 writer of binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public abstract class AbstractMitab26BinaryWriter<T extends BinaryInteraction, P extends Participant> extends AbstractMitab25BinaryWriter<T,P> {

    public AbstractMitab26BinaryWriter() {
        super();
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26BinaryWriter(File file) throws IOException {
        super(file);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26BinaryWriter(OutputStream output) {
        super(output);
        setVersion(MitabVersion.v2_6);
    }

    public AbstractMitab26BinaryWriter(Writer writer) {
        super(writer);
        setVersion(MitabVersion.v2_6);
    }

    @Override
    /**
     * Writes the binary interaction and its participants in MITAB 2.6
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinary(T interaction, P a, P b) throws IOException {
        // write tab 25 columns first
        super.writeBinary(interaction, a, b);

        MitabColumnFeeder<T, P> columnFeeder = getColumnFeeder();
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // complex expansion
        columnFeeder.writeComplexExpansion(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // biorole A
        columnFeeder.writeBiologicalRole(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // biorole B
        columnFeeder.writeBiologicalRole(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip exprole A
        columnFeeder.writeExperimentalRole(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip exprole B
        columnFeeder.writeExperimentalRole(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // interactor type A
        columnFeeder.writeInteractorType(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // interactor type B
        columnFeeder.writeInteractorType(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // xref A
        columnFeeder.writeParticipantXrefs(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // xref B
        columnFeeder.writeParticipantXrefs(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // xref
        columnFeeder.writeInteractionXrefs(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // annotation A
        columnFeeder.writeParticipantAnnotations(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // annotation B
        columnFeeder.writeParticipantAnnotations(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // annotation
        columnFeeder.writeInteractionAnnotations(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip host organism
        columnFeeder.writeHostOrganism(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip interaction parameter
        columnFeeder.writeInteractionParameters(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // created date
        columnFeeder.writeDate(interaction.getCreatedDate());
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // update date
        columnFeeder.writeDate(interaction.getUpdatedDate());
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // checksum A
        columnFeeder.writeParticipantChecksums(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // checksum B
        columnFeeder.writeParticipantChecksums(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // checksum I
        columnFeeder.writeInteractionChecksums(interaction);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip negative
        columnFeeder.writeNegativeProperty(interaction);
    }
}
