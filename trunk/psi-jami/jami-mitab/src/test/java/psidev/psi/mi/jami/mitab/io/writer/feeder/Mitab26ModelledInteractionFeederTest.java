package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab26ModelledInteractionFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Unit tester for Mitab26ModelledInteractionColumn
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab26ModelledInteractionFeederTest {

    @Test
    public void write_complex_expansion() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));

        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("psi-mi:\"MI:1060\"(spoke expansion)", writer.toString());

        binary.setComplexExpansion(null);
        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_biological_role() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeBiologicalRole(participant);
        Assert.assertEquals("psi-mi:\"MI:0499\"(unspecified role)", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeBiologicalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_experimental_role() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeExperimentalRole(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeExperimentalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interactor_type() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeInteractorType(participant);
        Assert.assertEquals("psi-mi:\"MI:0326\"(protein)", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeInteractorType(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_xrefs() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant.getXrefs().add(XrefUtils.createXref("go", "go:xxxx"));
        participant.getInteractor().getXrefs().add(XrefUtils.createXref("interpro", "interpro:xxxx"));
        ModelledParticipant participant_no_participant_xrefs = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant_no_participant_xrefs.getInteractor().getXrefs().add(XrefUtils.createXref("interpro", "interpro:xxxx"));
        ModelledParticipant participant_no_interactor_xrefs = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant_no_interactor_xrefs.getXrefs().add(XrefUtils.createXref("go", "go:xxxx"));
        ModelledParticipant participant_noxrefs = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeParticipantXrefs(participant);
        Assert.assertEquals("interpro:\"interpro:xxxx\"|go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_participant_xrefs);
        Assert.assertEquals("interpro:\"interpro:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_interactor_xrefs);
        Assert.assertEquals("go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        participant.getInteractor().getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ModelledParticipant participant_no_participant_xrefs = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant_no_participant_xrefs.getInteractor().getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ModelledParticipant participant_no_interactor_xrefs = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant_no_interactor_xrefs.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        ModelledParticipant participant_noxrefs = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeParticipantAnnotations(participant);
        Assert.assertEquals("caution: caution1|comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_participant_xrefs);
        Assert.assertEquals("caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_interactor_xrefs);
        Assert.assertEquals("comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ModelledBinaryInteraction interaction_no_annot = new MitabModelledBinaryInteraction();

        feeder.writeInteractionAnnotations(interaction);
        Assert.assertEquals("comment:comment1|caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeInteractionAnnotations(interaction_no_annot);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_host_organism() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();

        feeder.writeHostOrganism(interaction);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getModelledParameters().add(new MitabParameter("kd", "0.5", "molar"));
        interaction.getModelledParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));

        feeder.writeInteractionParameters(interaction);
        Assert.assertEquals("kd:0.5(molar)|ic50:\"5x10^(-1)\"(molar)", writer.toString());
    }

    @Test
    public void write_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeParameter(new MitabParameter("kd", "0.5", "molar"));
        Assert.assertEquals("kd:0.5(molar)", writer.toString());
    }

    @Test
    public void write_date() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeDate(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeDate(MitabUtils.DATE_FORMAT.parse("2013/06/28"));
        Assert.assertEquals("2013/06/28", writer.toString());
    }

    @Test
    public void write_participant_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("crc64", "xxxxx1"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("rogid", "xxxxx2"));
        ModelledParticipant participant_no_checksum = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeParticipantChecksums(participant);
        Assert.assertEquals("crc64:xxxxx1|rogid:xxxxx2", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantChecksums(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeParticipantChecksums(participant_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxxx3"));
        interaction.getChecksums().add(ChecksumUtils.createChecksum("crc", "xxxx4"));
        ModelledBinaryInteraction interaction_no_checksum = new MitabModelledBinaryInteraction();

        feeder.writeInteractionChecksums(interaction);
        Assert.assertEquals("rigid:xxxxx3|crc:xxxx4", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeInteractionChecksums(interaction_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeChecksum(ChecksumUtils.createRigid("xxxxx3"));
        Assert.assertEquals("rigid:xxxxx3", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeChecksum(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_annotation() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeAnnotation(AnnotationUtils.createAnnotation("caution", "\tcaution1|"));
        Assert.assertEquals("caution:\" caution1|\"", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeAnnotation(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_xref() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledInteractionFeeder feeder = new Mitab26ModelledInteractionFeeder(writer);

        feeder.writeXref(new MitabXref("go", "GO:xxxx", "component"));
        Assert.assertEquals("go:\"GO:xxxx\"(component)", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeXref(null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab26ModelledInteractionFeeder(writer);
        feeder.writeXref(new MitabXref("go", "GO:xxxx"));
        Assert.assertEquals("go:\"GO:xxxx\"", writer.toString());
    }
}
