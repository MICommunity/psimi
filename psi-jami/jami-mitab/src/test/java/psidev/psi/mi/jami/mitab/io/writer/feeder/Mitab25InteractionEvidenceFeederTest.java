package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.extension.MitabBinaryInteractionEvidence;
import psidev.psi.mi.jami.tab.extension.MitabParticipantEvidence;
import psidev.psi.mi.jami.tab.extension.MitabProtein;
import psidev.psi.mi.jami.tab.extension.MitabXref;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab25InteractionEvidenceFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ExperimentUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Unit tester for Mitab25InteractionEvidenceFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab25InteractionEvidenceFeederTest {

    @Test
    public void write_detection_method() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());

        feeder.writeInteractionDetectionMethod(binary);
        Assert.assertEquals("psi-mi:\"MI:0686\"(unspecified method)", writer.toString());
    }

    @Test
    public void write_first_author() throws IOException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().getAuthors().add("author1");
        binary.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));

        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("author1 et al.(2006)", writer.toString());

        binary.getExperiment().getPublication().getAuthors().add(0, "author2 et al.");
        binary.getExperiment().getPublication().setPublicationDate(null);
        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("author2 et al.", writer.toString());

        binary.getExperiment().getPublication().getAuthors().clear();
        binary.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));
        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("2006", writer.toString());
    }

    @Test
    public void write_publication_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setPubmedId("12345");
        binary.getExperiment().getPublication().setDoi("xxxx");
        binary.getExperiment().getPublication().assignImexId("IM-1");

        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("pubmed:12345|doi:xxxx|imex:IM-1", writer.toString());

        binary.getExperiment().getPublication().getXrefs().clear();
        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("pubmed:12345|doi:xxxx", writer.toString());

        binary.getExperiment().getPublication().getIdentifiers().clear();
        binary.getExperiment().getPublication().assignImexId("IM-1");
        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_source() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

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
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        ParticipantEvidence participant_null = null;
        ParticipantEvidence participant_no_aliases = new MitabParticipantEvidence(new MitabProtein("p12345_human", "protein test"));
        ParticipantEvidence participant_with_aliases = new MitabParticipantEvidence(new MitabProtein("p12345_human", "protein:test"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createGeneName("test gene"));
        participant_with_aliases.getInteractor().getAliases().add(AliasUtils.createAlias("synonym", "test synonym"));
        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence(participant_no_aliases, participant_with_aliases);
        binary.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        binary.getExperiment().getPublication().setSource(new DefaultSource("intact"));
        binary.getExperiment().getPublication().getSource().setMIIdentifier("MI:xxxx");
        participant_no_aliases.setInteractionEvidence(binary);
        participant_with_aliases.setInteractionEvidence(binary);

        feeder.writeAliases(participant_null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|uniprotkb:test gene(gene name)|intact:test synonym(synonym)", writer.toString());
    }

    @Test
    public void write_interaction_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.getXrefs().add(new MitabXref("imex", "IM-1", "imex-primary"));
        binary.getIdentifiers().add(new MitabXref("intact", "EBI-xxxxx"));
        binary.getIdentifiers().add(new MitabXref("mint", "MINT-xxxxx"));

        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("intact:EBI-xxxxx|mint:MINT-xxxxx|imex:IM-1", writer.toString());

        binary.getIdentifiers().clear();
        writer = new StringWriter();
        feeder = new Mitab25InteractionEvidenceFeeder(writer);
        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_confidences() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25InteractionEvidenceFeeder feeder = new Mitab25InteractionEvidenceFeeder(writer);

        MitabBinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence();
        binary.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("mi-score"), "0.5"));
        binary.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));

        feeder.writeInteractionConfidences(binary);
        Assert.assertEquals("mi-score:0.5|author-score:high", writer.toString());
    }
}
