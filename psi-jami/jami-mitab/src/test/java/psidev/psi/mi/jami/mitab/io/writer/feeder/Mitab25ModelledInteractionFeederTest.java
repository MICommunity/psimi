package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledConfidence;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.extension.MitabModelledBinaryInteraction;
import psidev.psi.mi.jami.tab.extension.MitabModelledParticipant;
import psidev.psi.mi.jami.tab.extension.MitabProtein;
import psidev.psi.mi.jami.tab.extension.MitabXref;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab25ModelledInteractionFeeder;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Unit tester for Mitab25ModelledInteractionFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab25ModelledInteractionFeederTest {

    @Test
    public void write_detection_method() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writeInteractionDetectionMethod(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_first_author() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writeFirstAuthor(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_publication_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();

        feeder.writePublicationIdentifiers(binary);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_source() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.setSource(new DefaultSource("intact"));
        binary.getSource().setMIIdentifier("MI:xxxx");

        feeder.writeSource(binary);
        Assert.assertEquals("psi-mi:\"MI:xxxx\"(intact)", writer.toString());
    }

    @Test
    public void write_aliases() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

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
        feeder = new Mitab25ModelledInteractionFeeder(writer);
        feeder.writeAliases(participant_no_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:protein test(display_long)", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab25ModelledInteractionFeeder(writer);
        feeder.writeAliases(participant_with_aliases);
        Assert.assertEquals("psi-mi:p12345_human(display_short)|psi-mi:\"protein:test\"(display_long)|uniprotkb:test gene(gene name)|intact:test synonym(synonym)", writer.toString());
    }

    @Test
    public void write_interaction_identifiers() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.getXrefs().add(new MitabXref("imex", "IM-1", "imex-primary"));
        binary.getIdentifiers().add(new MitabXref("intact", "EBI-xxxxx"));
        binary.getIdentifiers().add(new MitabXref("mint", "MINT-xxxxx"));

        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("intact:EBI-xxxxx|mint:MINT-xxxxx|imex:IM-1", writer.toString());

        binary.getIdentifiers().clear();
        writer = new StringWriter();
        feeder = new Mitab25ModelledInteractionFeeder(writer);
        feeder.writeInteractionIdentifiers(binary);
        Assert.assertEquals("imex:IM-1", writer.toString());
    }

    @Test
    public void write_confidences() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionFeeder feeder = new Mitab25ModelledInteractionFeeder(writer);

        MitabModelledBinaryInteraction binary = new MitabModelledBinaryInteraction();
        binary.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("mi-score"), "0.5"));
        binary.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));

        feeder.writeInteractionConfidences(binary);
        Assert.assertEquals("mi-score:0.5|author-score:high", writer.toString());
    }
}
