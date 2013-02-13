package psidev.psi.mi.tab.processor;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.mock.PsimiTabMockBuilder;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.processor.ClusterInteractorPairProcessor.SimpleInteractor;
import psidev.psi.mi.tab.processor.ClusterInteractorPairProcessor.TwoInteractor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.*;

/**
 * ClusterPerInteractorProcessor Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>10/12/2006</pre>
 */
public class ClusterInteractorPairProcessorTest {

    public Interactor buildInteractorA() {

        List<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add(new CrossReferenceImpl("uniprotkb", "Q98765"));
        Interactor i = new Interactor(identifiers);

        List<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add(new CrossReferenceImpl("interpro", "IPR002099"));
        altIds.add(new CrossReferenceImpl("go", "GO:0005515"));
        i.setAlternativeIdentifiers(altIds);

        List<Alias> aliases = new ArrayList<Alias>();
        aliases.add(new AliasImpl("gene name", "abc"));
        i.setInteractorAliases(aliases);

        i.setOrganism(new OrganismImpl(10032));

        return i;
    }

    public Interactor buildInteractorB() {

        List<CrossReference> identifiers = new ArrayList<CrossReference>();
        identifiers.add(new CrossReferenceImpl("uniprotkb", "P12345"));
        Interactor i = new Interactor(identifiers);

        List<CrossReference> altIds = new ArrayList<CrossReference>();
        altIds.add(new CrossReferenceImpl("interpro", "IPR002099"));
        i.setAlternativeIdentifiers(altIds);

        List<Alias> aliases = new ArrayList<Alias>();
        aliases.add(new AliasImpl("gene name", "def"));
        i.setInteractorAliases(aliases);

        i.setOrganism(new OrganismImpl(9606));

        return i;
    }

    ///////////////
    // Tests

    @Test
    public void hashMap() {
        // Check that A-B and B-A get clustered correctly

        BinaryInteractionImpl bi1 = new BinaryInteractionImpl(buildInteractorA(), buildInteractorB());
        bi1.getAuthors().add(new AuthorImpl("samuel"));
        bi1.getDetectionMethods().add(new CrossReferenceImpl("int-detect", "MI:0123"));
        bi1.getInteractionTypes().add(new CrossReferenceImpl("int-type", "MI:2345"));
        bi1.getPublications().add(new CrossReferenceImpl("pubmed", "123456789"));
        bi1.getSourceDatabases().add(new CrossReferenceImpl("intact", "MI:9999"));

        BinaryInteractionImpl bi2 = new BinaryInteractionImpl(buildInteractorB(), buildInteractorA());
        bi2.getAuthors().add(new AuthorImpl("samuel"));
        bi2.getDetectionMethods().add(new CrossReferenceImpl("int-detect", "MI:0123"));
        bi2.getInteractionTypes().add(new CrossReferenceImpl("int-type", "MI:2345"));
        bi2.getPublications().add(new CrossReferenceImpl("pubmed", "123456789"));
        bi2.getSourceDatabases().add(new CrossReferenceImpl("intact", "MI:9999"));

        ClusterInteractorPairProcessor.TwoInteractor ti1 =
                new ClusterInteractorPairProcessor.TwoInteractor(
                        new ClusterInteractorPairProcessor.SimpleInteractor(bi1.getInteractorA()),
                        new ClusterInteractorPairProcessor.SimpleInteractor(bi1.getInteractorB()));

        ClusterInteractorPairProcessor.TwoInteractor ti2 =
                new ClusterInteractorPairProcessor.TwoInteractor(
                        new ClusterInteractorPairProcessor.SimpleInteractor(bi2.getInteractorA()),
                        new ClusterInteractorPairProcessor.SimpleInteractor(bi2.getInteractorB()));

        Map<ClusterInteractorPairProcessor.TwoInteractor, Integer> map =
                new HashMap<ClusterInteractorPairProcessor.TwoInteractor, Integer>();

        map.put(ti1, 1);

        assertTrue(map.containsKey(ti2));
        assertTrue(map.containsKey(ti1));
    }

    @Test
    public void process2() {

        // Check that A-B and B-A get clustered correctly

        BinaryInteractionImpl bi1 = new BinaryInteractionImpl(buildInteractorA(), buildInteractorB());
        bi1.getAuthors().add(new AuthorImpl("samuel"));
        bi1.getDetectionMethods().add(new CrossReferenceImpl("int-detect", "MI:0123"));
        bi1.getInteractionTypes().add(new CrossReferenceImpl("int-type", "MI:2345"));
        bi1.getPublications().add(new CrossReferenceImpl("pubmed", "123456789"));
        bi1.getSourceDatabases().add(new CrossReferenceImpl("intact", "MI:9999"));

        BinaryInteractionImpl bi2 = new BinaryInteractionImpl(buildInteractorB(), buildInteractorA());
        bi2.getAuthors().add(new AuthorImpl("samuel"));
        bi2.getDetectionMethods().add(new CrossReferenceImpl("int-detect", "MI:0123"));
        bi2.getInteractionTypes().add(new CrossReferenceImpl("int-type", "MI:2345"));
        bi2.getPublications().add(new CrossReferenceImpl("pubmed", "123456789"));
        bi2.getSourceDatabases().add(new CrossReferenceImpl("intact", "MI:9999"));

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>(2);
        interactions.add(bi1);
        interactions.add(bi2);

        PostProcessorStrategy processor = new ClusterInteractorPairProcessor();
        Collection<BinaryInteraction> processedInteractions = processor.process(interactions);

        assertNotNull(processedInteractions);
        assertEquals(1, processedInteractions.size());
    }

    @Test
    public void process() {

        BinaryInteractionImpl bi1 = new BinaryInteractionImpl(buildInteractorA(), buildInteractorB());
        bi1.getAuthors().add(new AuthorImpl("samuel"));
        bi1.getDetectionMethods().add(new CrossReferenceImpl("int-detect", "MI:0123"));
        bi1.getInteractionTypes().add(new CrossReferenceImpl("int-type", "MI:2345"));
        bi1.getPublications().add(new CrossReferenceImpl("pubmed", "123456789"));
        bi1.getSourceDatabases().add(new CrossReferenceImpl("intact", "MI:9999"));

        BinaryInteractionImpl bi2 = new BinaryInteractionImpl(buildInteractorA(), buildInteractorB());
        bi2.getAuthors().add(new AuthorImpl("Jeannot"));
        bi2.getDetectionMethods().add(new CrossReferenceImpl("int-detect2", "MI:1111"));
        bi2.getInteractionTypes().add(new CrossReferenceImpl("int-type2", "MI:2345"));
        bi2.getPublications().add(new CrossReferenceImpl("pubmed", "765432100"));
        bi2.getSourceDatabases().add(new CrossReferenceImpl("mint", "MI:8888"));

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>(2);
        interactions.add(bi1);
        interactions.add(bi2);

        PostProcessorStrategy processor = new ClusterInteractorPairProcessor();
        Collection<BinaryInteraction> processedInteractions = processor.process(interactions);

        assertNotNull(processedInteractions);
        assertEquals(1, processedInteractions.size());

        BinaryInteraction bi3 = processedInteractions.iterator().next();
        Interactor a = buildInteractorA();
        Interactor b = buildInteractorB();
        assertTrue((bi3.getInteractorA().equals(a) && bi3.getInteractorB().equals(b))
                ||
                (bi3.getInteractorB().equals(a) && bi3.getInteractorA().equals(b)));

        // author merge
        assertEquals(2, bi3.getAuthors().size());
        assertTrue(bi3.getAuthors().contains(new AuthorImpl("samuel")));
        assertTrue(bi3.getAuthors().contains(new AuthorImpl("Jeannot")));

        // interaction type merge
        assertEquals(2, bi3.getInteractionTypes().size());
        assertTrue(bi3.getInteractionTypes().contains(new CrossReferenceImpl("int-type", "MI:2345")));
        assertTrue(bi3.getInteractionTypes().contains(new CrossReferenceImpl("int-type2", "MI:2345")));

        // interaction detection merge
        assertEquals(2, bi3.getDetectionMethods().size());
        assertTrue(bi3.getDetectionMethods().contains(new CrossReferenceImpl("int-detect", "MI:0123")));
        assertTrue(bi3.getDetectionMethods().contains(new CrossReferenceImpl("int-detect2", "MI:1111")));

        // publication merge
        assertEquals(2, bi3.getPublications().size());
        assertTrue(bi3.getPublications().contains(new CrossReferenceImpl("pubmed", "123456789")));
        assertTrue(bi3.getPublications().contains(new CrossReferenceImpl("pubmed", "765432100")));

        // source database merge
        assertEquals(2, bi3.getSourceDatabases().size());
        assertTrue(bi3.getSourceDatabases().contains(new CrossReferenceImpl("intact", "MI:9999")));
        assertTrue(bi3.getSourceDatabases().contains(new CrossReferenceImpl("mint", "MI:8888")));
    }

    @Test
    public void simpleInteractor() {

        List<CrossReference> identifiersA = new ArrayList<CrossReference>(2);
        identifiersA.add(new CrossReferenceImpl("uniprotkb", "P12345"));
        identifiersA.add(new CrossReferenceImpl("gi", "12345"));

        List<CrossReference> identifiersB = new ArrayList<CrossReference>(2);
        identifiersB.add(new CrossReferenceImpl("uniprotkb", "Q98765"));
        identifiersB.add(new CrossReferenceImpl("gi", "12345"));

        Interactor a = new Interactor(identifiersA);
        Interactor b = new Interactor(identifiersB);

        ClusterInteractorPairProcessor.SimpleInteractor sia = new ClusterInteractorPairProcessor.SimpleInteractor(a);
        ClusterInteractorPairProcessor.SimpleInteractor sib = new ClusterInteractorPairProcessor.SimpleInteractor(b);

        assertEquals(sia, sib);

        identifiersA.remove(new CrossReferenceImpl("gi", "12345"));
        assertEquals(1, identifiersA.size());

        assertNotSame(sia, sib);
    }

    @Test
    public void twoInteractor() {

        List<CrossReference> identifiersA = new ArrayList<CrossReference>(2);
        identifiersA.add(new CrossReferenceImpl("uniprotkb", "P12345"));
        identifiersA.add(new CrossReferenceImpl("gi", "12345"));

        List<CrossReference> identifiersB = new ArrayList<CrossReference>(2);
        identifiersB.add(new CrossReferenceImpl("uniprotkb", "Q98765"));
        identifiersB.add(new CrossReferenceImpl("gi", "12345"));

        List<CrossReference> identifiersC = new ArrayList<CrossReference>(2);
        identifiersC.add(new CrossReferenceImpl("uniprotkb", "A00000"));

        Interactor a = new Interactor(identifiersA);
        Interactor b = new Interactor(identifiersB);
        Interactor c = new Interactor(identifiersC);

        ClusterInteractorPairProcessor.SimpleInteractor sia = new ClusterInteractorPairProcessor.SimpleInteractor(a);
        ClusterInteractorPairProcessor.SimpleInteractor sib = new ClusterInteractorPairProcessor.SimpleInteractor(b);
        ClusterInteractorPairProcessor.SimpleInteractor sic = new ClusterInteractorPairProcessor.SimpleInteractor(c);

        ClusterInteractorPairProcessor.TwoInteractor ti = new ClusterInteractorPairProcessor.TwoInteractor(sia, sib);
        ClusterInteractorPairProcessor.TwoInteractor ti2 = new ClusterInteractorPairProcessor.TwoInteractor(sia, sib);
        ClusterInteractorPairProcessor.TwoInteractor ti3 = new ClusterInteractorPairProcessor.TwoInteractor(sib, sia);
        ClusterInteractorPairProcessor.TwoInteractor ti4 = new ClusterInteractorPairProcessor.TwoInteractor(sia, sic);

        assertEquals(ti, ti);
        assertEquals(ti, ti2);
        assertEquals(ti, ti3);
        assertNotSame(ti, ti4);
        assertNotSame(ti2, ti4);
        assertNotSame(ti3, ti4);
    }

    public Collection<BinaryInteraction> parseMitab(File mitab) throws IOException {
        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();
        boolean hasFileHeader = true;
        PsimiTabReader mitabReader = new PsimiTabReader();
//        mitabReader.setUnparseableLineBehaviour( new FailFastUnparseableLine() );
        Iterator<BinaryInteraction> iterator;
        iterator = mitabReader.iterate(mitab);

        while (iterator.hasNext()) {
            final BinaryInteraction binaryInteraction = iterator.next();
            if (binaryInteraction != null) interactions.add(binaryInteraction);
        }

        return interactions;
    }

    @Test
    public void dipExample() throws MalformedURLException, UnsupportedEncodingException {

        // load sample lines...
        final String filename = "/mitab-samples/dip.test1.mitab";
        URL url = TestHelper.getURLByResources(filename, ClusterInteractorPairProcessorTest.class);
        assertNotNull("Could not initialize test, file " + filename + " could not be found.", url);

        ClusterInteractorPairProcessor prc = new ClusterInteractorPairProcessor();
        assertNotNull(prc);

        // parse interactions...
        Collection<BinaryInteraction> mitabInteractions = null;
        try {
            mitabInteractions = parseMitab(new File(url.getPath()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }

        assertNotNull("Could not initialize test, parsing mitab file " + filename + " failed.", mitabInteractions);
        assertEquals(mitabInteractions.size(), 2);

        final Iterator<BinaryInteraction> interactionIterator = mitabInteractions.iterator();

        BinaryInteraction interaction1 = interactionIterator.next();
        assertNotNull(interaction1);
        assertEquals(mitabInteractions.contains(interaction1), true);

        BinaryInteraction interaction2 = interactionIterator.next();
        assertNotNull(interaction2);
        assertEquals(mitabInteractions.contains(interaction2), true);

        // interactions should have same detection method, same interactor, ...
        // here there is a specific smaller Object just to test interactor equality
        assertEquals(interaction1.equals(interaction2), false);

        TwoInteractor ti1 = new TwoInteractor(new SimpleInteractor(interaction1.getInteractorA()),
                new SimpleInteractor(interaction1.getInteractorB()));
        assertNotNull(ti1);
        TwoInteractor ti2 = new TwoInteractor(new SimpleInteractor(interaction2.getInteractorA()),
                new SimpleInteractor(interaction2.getInteractorB()));
        assertNotNull(ti2);

        assertEquals(ti1.equals(ti2), true);
        assertEquals(ti2.equals(ti1), true);
        assertTrue(interaction1.getInteractorA().hashCode() == interaction2.getInteractorB().hashCode());

        assertTrue(ti1.hashCode() == ti2.hashCode());

        Map<TwoInteractor, BinaryInteraction> map = new HashMap<TwoInteractor, BinaryInteraction>(mitabInteractions.size());
        assertEquals(map.containsKey(ti1), false);
        map.put(ti1, interaction1);
        assertEquals(map.containsKey(ti1), true);
        assertEquals(map.size(), 1);

        // we have map containsKey ti1 and ti1=ti2
        assertEquals(map.containsKey(ti2), true);

        mitabInteractions = prc.process(mitabInteractions);
        assertEquals(mitabInteractions.size(), 1);
    }

    @Test
    @Ignore
    /* Ignored because in the current state we caanot get it to work.
       Short explanation: upon interaction merge, if some collections are empty, we cannot know from which interaction
                          the data came from.
       Longer explanation: In IntAct, we have decided that the PSIMITAB format we export only contains a single row
                           for 2 given interactors. All interactions (be them binary or spoke expanded n-ary), get merged
                           into a single BinaryInteraction and the index of collections such as interactionAc, publication
                           interaction type can be used to identify different evidences. Unfortunately, if we do not have
                           exactly the same count of objects in these collections, as we have interactionAc, we cannot
                           trace which one is attached to which original intact interaction.
     */
    public void checkMerge() throws Exception {

        PsimiTabMockBuilder mockBuilder = new PsimiTabMockBuilder();

        BinaryInteraction bi1 = mockBuilder.createInteractionRandom();
        bi1.getAuthors().clear();
        bi1.getConfidenceValues().clear();
        bi1.getDetectionMethods().clear();
        bi1.getInteractionTypes().clear();
        bi1.getPublications().clear();

        BinaryInteraction bi2 = mockBuilder.createInteractionRandom();

        ClusterInteractorPairProcessor cipp = new ClusterInteractorPairProcessor();
        cipp.mergeCollections(bi1, bi2);

        Assert.assertEquals(2, bi2.getAuthors().size());
        Assert.assertEquals(2, bi2.getConfidenceValues().size());
        Assert.assertEquals(2, bi2.getDetectionMethods().size());
        Assert.assertEquals(2, bi2.getInteractionTypes().size());
        Assert.assertEquals(2, bi2.getPublications().size());
    }
}