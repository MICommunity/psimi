package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledConfidence;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.feeder.MitabModelledInteractionFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.*;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Unit tester for MitabModelledInteractionFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class MitabModelledInteractionFeederTest {

    @Test
    public void write_detection_method() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writeInteractionDetectionMethod(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_first_author() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_publication_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_source() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setSource(new DefaultSource("intact"));
        binary.getSource().setMIIdentifier("MI:xxxx");

        feeder.writeSource(binary);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(intact)", writer.toString());
    }

    @Test
    public void write_aliases() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant_null = null;
        ModelledParticipant participant_no_aliases = new MitabModelledParticipant(new MitabProtein("p12345_human", "protein test"));
        ModelledParticipant participant_with_aliases = new MitabModelledParticipant(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createGeneName("test gene"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));
        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction(participant_no_aliases, participant_with_aliases);
        binary.setSource(new DefaultSource("intact"));
        binary.getSource().setMIIdentifier("MI:xxxx");
        participant_no_aliases.setModelledInteraction(binary);
        participant_with_aliases.setModelledInteraction(binary);

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|uniprotkb:test gene(gene name)|intact:test synonym(synonym)", writer.toString());
    }

    @Test
    public void write_interaction_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.getXrefs().add(new MitabXref("imex", "IM-1", "imex-primary"));
        binary.getIdentifiers().add(new MitabXref("intact", "EBI-xxxxx"));
        binary.getIdentifiers().add(new MitabXref("mint", "MINT-xxxxx"));

        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("intact:EBI-xxxxx|mint:MINT-xxxxx|imex:IM-1", writer.toString());

        binary.getIdentifiers().clear();
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_confidences() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("mi-score"), "0.5"));
        binary.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));

        feeder.writeInteractionConfidences(binary);
        Assert.assertEquals("mi-score:0.5|author-score:high", writer.toString());
    }

    @Test
    public void write_complex_expansion() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));

        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("psi-mi:\"MI:1060\"(spoke expansion)", writer.toString());

        binary.setComplexExpansion(null);
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_biological_role() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeBiologicalRole(participant);
        Assert.assertEquals("psi-mi:\"MI:0499\"(unspecified role)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeBiologicalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_experimental_role() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeExperimentalRole(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeExperimentalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interactor_type() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeInteractorType(participant);
        Assert.assertEquals("psi-mi:\"MI:0326\"(protein)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeInteractorType(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_xrefs() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

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
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_participant_xrefs);
        Assert.assertEquals("interpro:\"interpro:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_interactor_xrefs);
        Assert.assertEquals("go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantXrefs(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

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
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_participant_xrefs);
        Assert.assertEquals("caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_interactor_xrefs);
        Assert.assertEquals("comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantAnnotations(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ModelledBinaryInteraction interaction_no_annot = new MitabModelledBinaryInteraction();

        feeder.writeInteractionAnnotations(interaction);
        Assert.assertEquals("comment:comment1|caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeInteractionAnnotations(interaction_no_annot);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_host_organism() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();

        feeder.writeHostOrganism(interaction);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getModelledParameters().add(new MitabParameter("kd", "0.5", "molar"));
        interaction.getModelledParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));

        feeder.writeInteractionParameters(interaction);
        Assert.assertEquals("kd:0.5(molar)|ic50:\"5x10^(-1)\"(molar)", writer.toString());
    }

    @Test
    public void write_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeParameter(new MitabParameter("kd", "0.5", "molar"));
        Assert.assertEquals("kd:0.5(molar)", writer.toString());
    }

    @Test
    public void write_date() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeDate(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeDate(MitabUtils.DATE_FORMAT.parse("2013/06/28"));
        Assert.assertEquals("2013/06/28", writer.toString());
    }

    @Test
    public void write_participant_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("crc64", "xxxxx1"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("rogid", "xxxxx2"));
        ModelledParticipant participant_no_checksum = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeParticipantChecksums(participant);
        Assert.assertEquals("crc64:xxxxx1|rogid:xxxxx2", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantChecksums(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantChecksums(participant_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledBinaryInteraction interaction = new MitabModelledBinaryInteraction();
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxxx3"));
        interaction.getChecksums().add(ChecksumUtils.createChecksum("crc", "xxxx4"));
        ModelledBinaryInteraction interaction_no_checksum = new MitabModelledBinaryInteraction();

        feeder.writeInteractionChecksums(interaction);
        Assert.assertEquals("rigid:xxxxx3|crc:xxxx4", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeInteractionChecksums(interaction_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeChecksum(ChecksumUtils.createRigid("xxxxx3"));
        Assert.assertEquals("rigid:xxxxx3", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeChecksum(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_annotation() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeAnnotation(AnnotationUtils.createAnnotation("caution", "\tcaution1|"));
        Assert.assertEquals("caution:\" caution1|\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeAnnotation(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_xref() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        feeder.writeXref(new MitabXref("go", "GO:xxxx", "component"));
        Assert.assertEquals("go:\"GO:xxxx\"(component)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeXref(null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeXref(new MitabXref("go", "GO:xxxx"));
        Assert.assertEquals("go:\"GO:xxxx\"", writer.toString());
    }

    @Test
    public void write_feature() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");

        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeFeature(null);
        Assert.assertEquals("", writer.toString());

        // no ranges
        feature.getRanges().clear();
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?(\"interpro:xxxx\")", writer.toString());

        // no interpro
        feature.setInterpro(null);
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?", writer.toString());

        // no feature type
        feature.setType(null);
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("unknown:?-?", writer.toString());
    }

    @Test
    public void write_participant_identification() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("p12345"));

        feeder.writeParticipantIdentificationMethod(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantIdentificationMethod(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_stoichiometry() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("p12345"));
        participant.setStoichiometry(1);
        ModelledParticipant participant_no_stc = new MitabModelledParticipant(new MitabProtein("p12345"));
        ModelledParticipant participant_stc_range = new MitabModelledParticipant(new MitabProtein("p12345"));
        participant_stc_range.setStoichiometry(new MitabStoichiometry(1, 5));

        feeder.writeParticipantStoichiometry(participant);
        Assert.assertEquals("1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantStoichiometry(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_no_stc);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_stc_range);
        Assert.assertEquals("1-5", writer.toString());
    }

    @Test
    public void write_participant_features() throws IOException {
        StringWriter writer = new StringWriter();
        MitabModelledInteractionFeeder feeder = new MitabModelledInteractionFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("p12345"));
        ModelledParticipant participant_no_features = new MitabModelledParticipant(new MitabProtein("p12345"));
        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participant.addModelledFeature(feature);
        MitabFeature feature2 = new MitabFeature(new DefaultCvTerm("binding site"));
        feature2.getRanges().add(RangeUtils.createCertainRange(10));
        participant.addModelledFeature(feature2);

        feeder.writeParticipantFeatures(participant);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")|binding site:10-10", writer.toString());

        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantFeatures(null);
        Assert.assertEquals("-", writer.toString());

        // no features
        writer = new StringWriter();
        feeder = new MitabModelledInteractionFeeder(writer);
        feeder.writeParticipantFeatures(participant_no_features);
        Assert.assertEquals("-", writer.toString());
    }
}
