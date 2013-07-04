package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.*;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Unit tester for DefaultMitabColumnFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class DefaultMitabColumnFeederTest {
    
    @Test
    public void write_unique_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_no_identifiers = new MitabParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant_with_identifiers = new MitabParticipant(new MitabProtein("protein test"));
        participant_with_identifiers.getInteractor().getIdentifiers().add(XrefUtils.createChebiIdentity("CHEBI:xxx"));
        participant_with_identifiers.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12345"));

        feeder.writeUniqueIdentifier(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeUniqueIdentifier(participant_no_identifiers);
        Assert.assertEquals("-", writer.toString());

        // only write first identifier
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeUniqueIdentifier(participant_with_identifiers);
        Assert.assertEquals("uniprotkb:P12345", writer.toString());
    }

    @Test
    public void write_alternative_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_no_identifiers = new MitabParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant_with_identifiers = new MitabParticipant(new MitabProtein("protein test"));
        participant_with_identifiers.getInteractor().getIdentifiers().add(XrefUtils.createChebiIdentity("CHEBI:xxx"));
        participant_with_identifiers.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12345"));

        feeder.writeAlternativeIdentifiers(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAlternativeIdentifiers(participant_no_identifiers);
        Assert.assertEquals("-", writer.toString());

        // excludes first identifier
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAlternativeIdentifiers(participant_with_identifiers);
        Assert.assertEquals("chebi:\"CHEBI:xxx\"", writer.toString());
    }

    @Test
    public void write_aliases() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_no_aliases = new MitabParticipant(new MitabProtein("p12345_human", "protein test"));
        Participant participant_with_aliases = new MitabParticipant(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createGeneName("test gene"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|uniprotkb:test gene(gene name)|unknown:test synonym(synonym)", writer.toString());
    }

    @Test
    public void write_interactor_organism() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Participant participant_null = null;
        Participant participant_no_organism = new MitabParticipant(new MitabProtein("p12345_human", "protein test"));
        Participant participant_with_organism = new MitabParticipant(new MitabProtein("p12345_human", "protein:test"));
        participant_with_organism.getInteractor().setOrganism(new DefaultOrganism(9606, "human", "Homo Sapiens"));
        participant_with_organism.getInteractor().getOrganism().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));

        feeder.writeInteractorOrganism(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractorOrganism(participant_no_organism);
        Assert.assertEquals("-", writer.toString());

        // write common name and scientific name
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractorOrganism(participant_with_organism);
        Assert.assertEquals("taxid:9606(human)|taxid:9606(Homo Sapiens)", writer.toString());
    }

    @Test
    public void write_interaction_type() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        BinaryInteraction type_null = new MitabBinaryInteraction();
        BinaryInteraction interaction_type = new MitabBinaryInteraction("test interaction", CvTermUtils.createMICvTerm("association", "MI:xxxx"));

        feeder.writeInteractionType(type_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractionType(interaction_type);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(association)", writer.toString());
    }

    @Test
    public void write_confidence() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Confidence confidence_null = null;
        Confidence confidence = new DefaultConfidence(new DefaultCvTerm("mi-score"), "0.5");
        Confidence confidence_with_text = new MitabConfidence(new DefaultCvTerm("mi-score"), "0.5", "text");

        feeder.writeConfidence(confidence_null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeConfidence(confidence);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeConfidence(confidence_with_text);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_organism() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Organism organism_null = null;
        Organism organism = new DefaultOrganism(9606, "human", "Homo Sapiens");
        organism.getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));
        Organism organism_only_common_name = new DefaultOrganism(9606, "human");
        Organism organism_only_scientificName = new DefaultOrganism(9606, null, "Homo Sapiens");
        Organism organism_no_name = new DefaultOrganism(9606);

        feeder.writeOrganism(organism_null);
        Assert.assertEquals("-", writer.toString());

        // write common name and scientific name
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeOrganism(organism);
        Assert.assertEquals("taxid:9606(human)|taxid:9606(Homo Sapiens)", writer.toString());

        // write common name
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeOrganism(organism_only_common_name);
        Assert.assertEquals("taxid:9606(human)", writer.toString());

        // write scientific name
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeOrganism(organism_only_scientificName);
        Assert.assertEquals("taxid:9606(Homo Sapiens)", writer.toString());

        // write no name
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeOrganism(organism_no_name);
        Assert.assertEquals("taxid:9606", writer.toString());
    }

    @Test
    public void write_cv_term() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        CvTerm type_null = null;
        CvTerm type = CvTermUtils.createMICvTerm("association", "MI:xxxx");
        type.setFullName("physical association");
        CvTerm type_no_id = new DefaultCvTerm("association");
        CvTerm type_mod = CvTermUtils.createMODCvTerm("association", "MOD:xxxx");
        CvTerm type_par = CvTermUtils.createPARCvTerm("association", "PAR:xxxx");
        CvTerm type_other = new DefaultCvTerm("association");
        type_other.getIdentifiers().add(XrefUtils.createXref("test database", "full test database", "TEST:xxxx"));

        feeder.writeCvTerm(type_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeCvTerm(type_no_id);
        Assert.assertEquals("unknown:unknown(association)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeCvTerm(type);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(physical association)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeCvTerm(type_mod);
        Assert.assertEquals("psi-mod:\"MOD:xxxx\"(association)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeCvTerm(type_par);
        Assert.assertEquals("psi-par:\"PAR:xxxx\"(association)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeCvTerm(type_other);
        Assert.assertEquals("test database:\"TEST:xxxx\"(association)", writer.toString());
    }

    @Test
    public void write_alias() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Alias alias_null = null;
        Alias alias = AliasUtils.createGeneName("test gene");
        Alias alias_no_type = new DefaultAlias("test:name");

        feeder.writeAlias(alias_null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAlias(alias);
        Assert.assertEquals("uniprotkb:test gene(gene name)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAlias(alias_no_type);
        Assert.assertEquals("unknown:\"test:name\"", writer.toString());
    }

    @Test
    public void write_identifier() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        Xref identifier_null = null;
        Xref identifier = XrefUtils.createUniprotSecondary("P12345");

        feeder.writeIdentifier(identifier_null);
        Assert.assertEquals("", writer.toString());

        // identifier
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeIdentifier(identifier);
        Assert.assertEquals("uniprotkb:P12345", writer.toString());
    }

    @Test
    public void escape_and_write_string() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        String strintToEscape = "a \" nice \" protein : \t easy to write (almost!) \n";

        feeder.escapeAndWriteString(strintToEscape);
        Assert.assertEquals("\"a \\\" nice \\\" protein :   easy to write (almost!)  \"", writer.toString());
    }

    @Test
    public void write_detection_method() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());

        feeder.writeInteractionDetectionMethod(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_first_author() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().getAuthors().add("author1");

        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_publication_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setPubmedId("12345");

        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_source() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setSource(new DefaultSource("dip"));

        feeder.writeSource(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_confidences() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("mi-score"), "0.5"));

        feeder.writeInteractionConfidences(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.assignImexId("IM-1");
        binary.getIdentifiers().add(new MitabXref("intact", "EBI-xxxxx"));
        binary.getIdentifiers().add(new MitabXref("mint", "MINT-xxxxx"));

        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("intact:EBI-xxxxx|mint:MINT-xxxxx|imex:IM-1", writer.toString());

        binary.getIdentifiers().clear();
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_complex_expansion() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));

        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("psi-mi:\"MI:1060\"(spoke expansion)", writer.toString());

        binary.setComplexExpansion(null);
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeComplexExpansion(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_biological_role() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeBiologicalRole(participant);
        Assert.assertEquals("psi-mi:\"MI:0499\"(unspecified role)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeBiologicalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_experimental_role() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("P12345"));

        feeder.writeExperimentalRole(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeExperimentalRole(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interactor_type() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeInteractorType(participant);
        Assert.assertEquals("psi-mi:\"MI:0326\"(protein)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractorType(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_xrefs() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

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
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantXrefs(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_participant_xrefs);
        Assert.assertEquals("interpro:\"interpro:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantXrefs(participant_no_interactor_xrefs);
        Assert.assertEquals("go:\"go:xxxx\"", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantXrefs(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

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
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantAnnotations(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_participant_xrefs);
        Assert.assertEquals("caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantAnnotations(participant_no_interactor_xrefs);
        Assert.assertEquals("comment:comment1", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantAnnotations(participant_noxrefs);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_annotations() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("comment", "comment1"));
        interaction.getAnnotations().add(AnnotationUtils.createAnnotation("caution", "\tcaution1"));
        BinaryInteractionEvidence interaction_no_annot = new MitabBinaryInteractionEvidence();

        feeder.writeInteractionAnnotations(interaction);
        Assert.assertEquals("comment:comment1|caution: caution1", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractionAnnotations(interaction_no_annot);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_host_organism() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        interaction.getExperiment().setHostOrganism(new MitabOrganism(-1, "in vitro"));

        feeder.writeHostOrganism(interaction);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getParameters().add(new MitabParameter("kd", "0.5", "molar"));

        feeder.writeInteractionParameters(interaction);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_parameters() throws IOException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeParameter(new MitabParameter("kd", "0.5", "molar"));
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_date() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeDate(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeDate(MitabUtils.DATE_FORMAT.parse("2013/06/28"));
        Assert.assertEquals("2013/06/28", writer.toString());
    }

    @Test
    public void write_negative() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeNegativeProperty(new DefaultBinaryInteraction());
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ModelledParticipant participant = new MitabModelledParticipant(new MitabProtein("P12345"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("crc64", "xxxxx1"));
        participant.getInteractor().getChecksums().add(ChecksumUtils.createChecksum("rogid", "xxxxx2"));
        ModelledParticipant participant_no_checksum = new MitabModelledParticipant(new MitabProtein("P12345"));

        feeder.writeParticipantChecksums(participant);
        Assert.assertEquals("crc64:xxxxx1|rogid:xxxxx2", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantChecksums(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantChecksums(participant_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_interaction_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        BinaryInteractionEvidence interaction = new MitabBinaryInteractionEvidence();
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxxx3"));
        interaction.getChecksums().add(ChecksumUtils.createChecksum("crc", "xxxx4"));
        BinaryInteractionEvidence interaction_no_checksum = new MitabBinaryInteractionEvidence();

        feeder.writeInteractionChecksums(interaction);
        Assert.assertEquals("rigid:xxxxx3|crc:xxxx4", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeInteractionChecksums(interaction_no_checksum);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_checksum() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeChecksum(ChecksumUtils.createRigid("xxxxx3"));
        Assert.assertEquals("rigid:xxxxx3", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeChecksum(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_annotation() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeAnnotation(AnnotationUtils.createAnnotation("caution", "\tcaution1|"));
        Assert.assertEquals("caution:\" caution1|\"", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeAnnotation(null);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void write_xref() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        feeder.writeXref(new MitabXref("go", "GO:xxxx", "component"));
        Assert.assertEquals("go:\"GO:xxxx\"(component)", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeXref(null);
        Assert.assertEquals("", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeXref(new MitabXref("go", "GO:xxxx"));
        Assert.assertEquals("go:\"GO:xxxx\"", writer.toString());
    }

    @Test
    public void write_feature() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");

        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeFeature(null);
        Assert.assertEquals("", writer.toString());

        // no ranges
        feature.getRanges().clear();
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?(\"interpro:xxxx\")", writer.toString());

        // no interpro
        feature.setInterpro(null);
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?", writer.toString());

        // no feature type
        feature.setType(null);
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("unknown:?-?", writer.toString());
    }

    @Test
    public void write_participant_identification() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));

        feeder.writeParticipantIdentificationMethod(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantIdentificationMethod(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_stoichiometry() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant.setStoichiometry(1);
        ParticipantEvidence participant_no_stc = new MitabParticipantEvidence(new MitabProtein("p12345"));
        ParticipantEvidence participant_stc_range = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant_stc_range.setStoichiometry(new MitabStoichiometry(1, 5));

        feeder.writeParticipantStoichiometry(participant);
        Assert.assertEquals("1", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantStoichiometry(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_no_stc);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_stc_range);
        Assert.assertEquals("1-5", writer.toString());
    }

    @Test
    public void write_participant_features() throws IOException {
        StringWriter writer = new StringWriter();
        DefaultMitabColumnFeeder feeder = new DefaultMitabColumnFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        ParticipantEvidence participant_no_features = new MitabParticipantEvidence(new MitabProtein("p12345"));
        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participant.addFeatureEvidence(feature);
        MitabFeature feature2 = new MitabFeature(new DefaultCvTerm("binding site"));
        feature2.getRanges().add(RangeUtils.createCertainRange(10));
        participant.addFeatureEvidence(feature2);

        feeder.writeParticipantFeatures(participant);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")|binding site:10-10", writer.toString());

        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantFeatures(null);
        Assert.assertEquals("-", writer.toString());

        // no features
        writer = new StringWriter();
        feeder = new DefaultMitabColumnFeeder(writer);
        feeder.writeParticipantFeatures(participant_no_features);
        Assert.assertEquals("-", writer.toString());
    }
}
