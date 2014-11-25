package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.feeder.MitabInteractionEvidenceFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.*;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Unit tester for MitabInteractionEvidenceFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class MitabInteractionEvidenceFeederTest {

    @Test
    public void write_detection_method() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());

        feeder.writeInteractionDetectionMethod(binary);
        Assert.assertEquals("psi-mi:\"MI:0686\"(unspecified method)", writer.toString());
    }

    @Test
    public void write_first_author() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().getAuthors().add("author1");
        binary.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));

        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("author1 et al.(2006)", writer.toString());

        binary.getExperiment().getPublication().getAuthors().add(0, "author2 et al.");
        binary.getExperiment().getPublication().setPublicationDate(null);
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("author2 et al.", writer.toString());

        binary.getExperiment().getPublication().getAuthors().clear();
        binary.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("2006", writer.toString());
    }

    @Test
    public void write_publication_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setPubmedId("12345");
        binary.getExperiment().getPublication().setDoi("xxxx");
        binary.getExperiment().getPublication().assignImexId("IM-1");

        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("pubmed:12345|doi:xxxx|imex:IM-1", writer.toString());

        binary.getExperiment().getPublication().getXrefs().clear();
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("pubmed:12345|doi:xxxx", writer.toString());

        binary.getExperiment().getPublication().getIdentifiers().clear();
        binary.getExperiment().getPublication().assignImexId("IM-1");
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_source() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setSource(new DefaultSource("intact"));
        binary.getExperiment().getPublication().getSource().setMIIdentifier("MI:xxxx");

        feeder.writeSource(binary);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(intact)", writer.toString());
    }

    @Test
    public void write_aliases() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant_null = null;
        ParticipantEvidence participant_no_aliases = new MitabParticipantEvidence(new MitabProtein("p12345_human", "protein test"));
        ParticipantEvidence participant_with_aliases = new MitabParticipantEvidence(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createGeneName("test gene"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));
        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence(participant_no_aliases, participant_with_aliases);
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setSource(new DefaultSource("intact"));
        binary.getExperiment().getPublication().getSource().setMIIdentifier("MI:xxxx");
        participant_no_aliases.setInteraction(binary);
        participant_with_aliases.setInteraction(binary);

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|uniprotkb:test gene(gene name)|intact:test synonym(synonym)", writer.toString());
    }

    @Test
    public void write_interaction_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.getXrefs().add(new MitabXref("imex", "IM-1", "imex-primary"));
        binary.getIdentifiers().add(new MitabXref("intact", "EBI-xxxxx"));
        binary.getIdentifiers().add(new MitabXref("mint", "MINT-xxxxx"));

        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("intact:EBI-xxxxx|mint:MINT-xxxxx|imex:IM-1", writer.toString());

        binary.getIdentifiers().clear();
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_confidences() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("mi-score"), "0.5"));
        binary.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));

        feeder.writeInteractionConfidences(binary);
        Assert.assertEquals("mi-score:0.5|author-score:high", writer.toString());
    }

    @Test
    public void write_complex_expansion() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));

        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("psi-mi:\"MI:1060\"(spoke expansion)", writer.toString());

        binary.setComplexExpansion(null);
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_biological_role() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeBiologicalRole(participant);
        Assert.assertEquals("psi-mi:\"MI:0499\"(unspecified role)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeBiologicalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_experimental_role() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeExperimentalRole(participant);
        Assert.assertEquals("psi-mi:\"MI:0499\"(unspecified role)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeExperimentalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interactor_type() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeInteractorType(participant);
        Assert.assertEquals("psi-mi:\"MI:0326\"(protein)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeInteractorType(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_xrefs() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant.getXrefs().add(XrefUtils.createXref("go", "go:xxxx"));
        participant.getInteractor().getXrefs().add(XrefUtils.createXref("interpro", "interpro:xxxx"));
        ParticipantEvidence participant_no_participant_xrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant_no_participant_xrefs.getInteractor().getXrefs().add(XrefUtils.createXref("interpro", "interpro:xxxx"));
        ParticipantEvidence participant_no_interactor_xrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant_no_interactor_xrefs.getXrefs().add(XrefUtils.createXref("go", "go:xxxx"));
        ParticipantEvidence participant_noxrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeParticipantXrefs(participant);
        Assert.assertEquals("interpro:\"interpro:xxxx\"|go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantXrefs(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_participant_xrefs);
        Assert.assertEquals("interpro:\"interpro:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_interactor_xrefs);
        Assert.assertEquals("go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantXrefs(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        participant.getInteractor().getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ParticipantEvidence participant_no_participant_xrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant_no_participant_xrefs.getInteractor().getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        ParticipantEvidence participant_no_interactor_xrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant_no_interactor_xrefs.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        ParticipantEvidence participant_noxrefs = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeParticipantAnnotations(participant);
        Assert.assertEquals("caution: caution1|comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantAnnotations(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_participant_xrefs);
        Assert.assertEquals("caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_interactor_xrefs);
        Assert.assertEquals("comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantAnnotations(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        BinaryInteractionEvidence interaction_no_annot = new MitabBinaryInteractionEvidence();

        feeder.writeInteractionAnnotations(interaction);
        Assert.assertEquals("comment:comment1|caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeInteractionAnnotations(interaction_no_annot);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_host_organism() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        interaction.getExperiment().setHostOrganism(new MitabOrganism(-1, "in vitro", "in (vitro)"));

        feeder.writeHostOrganism(interaction);
        Assert.assertEquals("taxid:-1(in vitro)|taxid:-1(\"in (vitro)\")", writer.toString());
    }

    @Test
    public void write_interaction_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getParameters().add(new MitabParameter("kd", "0.5", "molar"));
        interaction.getParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));

        feeder.writeInteractionParameters(interaction);
        Assert.assertEquals("kd:0.5(molar)|ic50:\"5x10^(-1)\"(molar)", writer.toString());
    }

    @Test
    public void write_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeParameter(new MitabParameter("kd", "0.5", "molar"));
        Assert.assertEquals("kd:0.5(molar)", writer.toString());
    }

    @Test
    public void write_date() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeDate(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeDate(MitabUtils.DATE_FORMAT.parse("2013/06/28"));
        Assert.assertEquals("2013/06/28", writer.toString());
    }

    @Test
    public void write_participant_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("crc64", "xxxxx1"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("rogid", "xxxxx2"));
        ParticipantEvidence participant_no_checksum = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeParticipantChecksums(participant);
        Assert.assertEquals("crc64:xxxxx1|rogid:xxxxx2", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantChecksums(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantChecksums(participant_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxxx3"));
        interaction.getChecksums().add(ChecksumUtils.createChecksum("crc", "xxxx4"));
        BinaryInteractionEvidence interaction_no_checksum = new MitabBinaryInteractionEvidence();

        feeder.writeInteractionChecksums(interaction);
        Assert.assertEquals("rigid:xxxxx3|crc:xxxx4", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeInteractionChecksums(interaction_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeChecksum(ChecksumUtils.createRigid("xxxxx3"));
        Assert.assertEquals("rigid:xxxxx3", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeChecksum(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_annotation() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeAnnotation(AnnotationUtils.createAnnotation("caution", "\tcaution1|"));
        Assert.assertEquals("caution:\" caution1|\"", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeAnnotation(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_xref() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        feeder.writeXref(new MitabXref("go", "GO:xxxx", "component"));
        Assert.assertEquals("go:\"GO:xxxx\"(component)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeXref(null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeXref(new MitabXref("go", "GO:xxxx"));
        Assert.assertEquals("go:\"GO:xxxx\"", writer.toString());
    }

    @Test
    public void write_feature() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        DefaultMitabFeature feature = new DefaultMitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");

        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFeature(null);
        Assert.assertEquals("", writer.toString());

        // no ranges
        feature.getRanges().clear();
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?(\"interpro:xxxx\")", writer.toString());

        // no interpro
        feature.setInterpro(null);
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?", writer.toString());

        // no feature type
        feature.setType(null);
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("unknown:?-?", writer.toString());
    }

    @Test
    public void write_participant_identification() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant.getIdentificationMethods().add(new MitabCvTerm("western blot", "MI:xxxx"));

        feeder.writeParticipantIdentificationMethod(participant);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(western blot)", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantIdentificationMethod(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_stoichiometry() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant.setStoichiometry(1);
        ParticipantEvidence participant_no_stc = new MitabParticipantEvidence(new MitabProtein("p12345"));
        ParticipantEvidence participant_stc_range = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant_stc_range.setStoichiometry(new MitabStoichiometry(1, 5));

        feeder.writeParticipantStoichiometry(participant);
        Assert.assertEquals("1", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_no_stc);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_stc_range);
        Assert.assertEquals("1-5", writer.toString());
    }

    @Test
    public void write_participant_features() throws IOException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        ParticipantEvidence participant_no_features = new MitabParticipantEvidence(new MitabProtein("p12345"));
        MitabFeatureEvidence feature = new MitabFeatureEvidence(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participant.addFeature(feature);
        MitabFeatureEvidence feature2 = new MitabFeatureEvidence(new DefaultCvTerm("binding site"));
        feature2.getRanges().add(RangeUtils.createCertainRange(10));
        participant.addFeature(feature2);

        feeder.writeParticipantFeatures(participant);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")|binding site:10-10", writer.toString());

        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantFeatures(null);
        Assert.assertEquals("-", writer.toString());

        // no features
        writer = new StringWriter();
        feeder = new MitabInteractionEvidenceFeeder(writer);
        feeder.writeParticipantFeatures(participant_no_features);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_negative() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        MitabInteractionEvidenceFeeder feeder = new MitabInteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence evidence = new MitabBinaryInteractionEvidence();
        evidence.setNegative(true);
        feeder.writeNegativeProperty(evidence);
        Assert.assertEquals("true", writer.toString());
    }
}
