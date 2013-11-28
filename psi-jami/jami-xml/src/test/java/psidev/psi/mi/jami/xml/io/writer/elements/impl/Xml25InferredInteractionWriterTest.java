package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_inferred() throws XMLStreamException, IOException {
        Set<Feature> inferred = new HashSet<Feature>();
        inferred.add(new DefaultFeature());
        inferred.add(new DefaultFeature());

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteraction, output.toString());
    }

    @Test
    public void test_write_inferred_several() throws XMLStreamException, IOException {
        Set<Feature> inferred = new HashSet<Feature>();
        inferred.add(new DefaultFeature());
        inferred.add(new DefaultFeature());
        inferred.add(new DefaultFeature());

        elementCache.clear();
        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteraction_several, output.toString());
    }

    @Test
    @Ignore
    public void test_write_inferred_already_registered() throws XMLStreamException, IOException {
        Set<Feature> inferred = new HashSet<Feature>();
        Feature f1 = new DefaultFeature();
        Feature f2 = new DefaultFeature();
        inferred.add(f1);
        inferred.add(f2);
        elementCache.clear();
        elementCache.extractIdForFeature(new DefaultFeature());
        elementCache.extractIdForFeature(f1);
        elementCache.extractIdForFeature(f2);

        Xml25InferredInteractionWriter writer = new Xml25InferredInteractionWriter(createStreamWriter(), elementCache);
        writer.write(inferred);
        streamWriter.flush();

        Assert.assertEquals(inferredInteractionRegistered, output.toString());
    }
}