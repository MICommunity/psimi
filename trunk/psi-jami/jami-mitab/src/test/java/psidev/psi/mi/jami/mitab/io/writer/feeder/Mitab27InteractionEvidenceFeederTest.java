package psidev.psi.mi.jami.mitab.io.writer.feeder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.extension.MitabFeature;
import psidev.psi.mi.jami.tab.extension.MitabParticipantEvidence;
import psidev.psi.mi.jami.tab.extension.MitabProtein;
import psidev.psi.mi.jami.tab.extension.MitabStoichiometry;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab27InteractionEvidenceFeeder;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Unit tester for Mitab27InteractionEvidenceFeeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab27InteractionEvidenceFeederTest {

    @Test
    public void write_feature() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab27InteractionEvidenceFeeder feeder = new Mitab27InteractionEvidenceFeeder(writer);

        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");

        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:1..3-6..7,>9->9(\"interpro:xxxx\")", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeFeature(null);
        Assert.assertEquals("", writer.toString());

        // no ranges
        feature.getRanges().clear();
        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?(\"interpro:xxxx\")", writer.toString());

        // no interpro
        feature.setInterpro(null);
        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("binding site region:?-?", writer.toString());

        // no feature type
        feature.setType(null);
        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeFeature(feature);
        Assert.assertEquals("unknown:?-?", writer.toString());
    }

    @Test
    public void write_participant_identification() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab27InteractionEvidenceFeeder feeder = new Mitab27InteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));

        feeder.writeParticipantIdentificationMethod(participant);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantIdentificationMethod(null);
        Assert.assertEquals("-", writer.toString());
    }

    @Test
    public void write_participant_stoichiometry() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab27InteractionEvidenceFeeder feeder = new Mitab27InteractionEvidenceFeeder(writer);

        ParticipantEvidence participant = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant.setStoichiometry(1);
        ParticipantEvidence participant_no_stc = new MitabParticipantEvidence(new MitabProtein("p12345"));
        ParticipantEvidence participant_stc_range = new MitabParticipantEvidence(new MitabProtein("p12345"));
        participant_stc_range.setStoichiometry(new MitabStoichiometry(1, 5));

        feeder.writeParticipantStoichiometry(participant);
        Assert.assertEquals("1", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(null);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_no_stc);
        Assert.assertEquals("-", writer.toString());

        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantStoichiometry(participant_stc_range);
        Assert.assertEquals("1-5", writer.toString());
    }

    @Test
    public void write_participant_features() throws IOException {
        StringWriter writer = new StringWriter();
        Mitab27InteractionEvidenceFeeder feeder = new Mitab27InteractionEvidenceFeeder(writer);

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
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantFeatures(null);
        Assert.assertEquals("-", writer.toString());

        // no features
        writer = new StringWriter();
        feeder = new Mitab27InteractionEvidenceFeeder(writer);
        feeder.writeParticipantFeatures(participant_no_features);
        Assert.assertEquals("-", writer.toString());
    }
}
