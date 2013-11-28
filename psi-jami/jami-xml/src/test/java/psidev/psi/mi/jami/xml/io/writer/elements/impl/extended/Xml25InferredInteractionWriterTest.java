package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25InferredInteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class Xml25InferredInteractionWriterTest extends AbstractXml25WriterTest {
    private String inferredInteraction = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>1</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>2</participantFeatureRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteraction_several = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>1</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>2</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>3</participantFeatureRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteractionRegistered = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>2</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>3</participantFeatureRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteractionParticipant = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantRef>1</participantRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantRef>2</participantRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteractionParticipant_several = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>1</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantRef>2</participantRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantRef>3</participantRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteractionParticipantRegistered = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantRef>2</participantRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantRef>3</participantRef>\n" +
            "  </participant>\n" +
            "</inferredInteraction>";
    private String inferredInteractionExperiments = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>1</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>2</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <experimentRefList>\n" +
            "    <experimentRef>3</experimentRef>\n" +
            "  </experimentRefList>\n" +
            "</inferredInteraction>";
    private String inferredInteractionExperiment_registered = "<inferredInteraction>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>3</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <participant>\n" +
            "    <participantFeatureRef>4</participantFeatureRef>\n" +
            "  </participant>\n" +
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n" +
            "  </experimentRefList>\n" +
            "</inferredInteraction>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_inferred() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setFeature(new DefaultFeature());
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteraction, output.toString());
    }

    @Test
    public void test_write_inferred_several() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setFeature(new DefaultFeature());
        InferredInteractionParticipant p3 = new InferredInteractionParticipant();
        p3.setFeature(new DefaultFeature());
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        inferred.getParticipants().add(p3);

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteraction_several, output.toString());
    }

    @Test
    public void test_write_inferred_already_registered() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setFeature(new DefaultFeature());
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        elementCache.clear();
        elementCache.extractIdForFeature(new DefaultFeature());
        elementCache.extractIdForFeature(p1.getFeature());
        elementCache.extractIdForFeature(p2.getFeature());

        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionRegistered, output.toString());
    }

    @Test
    public void test_write_inferred_participant() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionParticipant, output.toString());
    }

    @Test
    public void test_write_inferred_several_participant() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        InferredInteractionParticipant p3 = new InferredInteractionParticipant();
        p3.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        inferred.getParticipants().add(p3);

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionParticipant_several, output.toString());
    }

    @Test
    public void test_write_inferred_participant_already_registered() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        elementCache.clear();
        elementCache.extractIdForFeature(new DefaultFeature());
        elementCache.extractIdForParticipant(p1.getParticipant());
        elementCache.extractIdForParticipant(p2.getParticipant());

        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionParticipantRegistered, output.toString());
    }

    @Test
    public void test_write_inferred_experiments() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setFeature(new DefaultFeature());
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        inferred.getExperiments().add(new DefaultExperiment(new DefaultPublication()));

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionExperiments, output.toString());
    }

    @Test
    public void test_write_inferred_experiments_registered() throws XMLStreamException, IOException {
        InferredInteraction inferred = new InferredInteraction();
        InferredInteractionParticipant p1 = new InferredInteractionParticipant();
        p1.setFeature(new DefaultFeature());
        InferredInteractionParticipant p2 = new InferredInteractionParticipant();
        p2.setFeature(new DefaultFeature());
        inferred.getParticipants().add(p1);
        inferred.getParticipants().add(p2);
        inferred.getExperiments().add(new DefaultExperiment(new DefaultPublication()));

        elementCache.clear();
        elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("12345")));
        elementCache.extractIdForExperiment(inferred.getExperiments().iterator().next());

        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionExperiment_registered, output.toString());
    }
}