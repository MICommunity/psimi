package psidev.psi.mi.jami.analysis.graph;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Unit tester for BindingSiteCliqueFinder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class BindingSiteCliqueFinderTest {

    @Test
    public void test_finder(){
        // create small interaction with 4 participants and 4 different features
        // two inferred interactiosn f1, f2, f3 and f3,f4
        Feature f1 = new DefaultFeature();
        Feature f2 = new DefaultFeature();
        Feature f3 = new DefaultFeature();
        Feature f4 = new DefaultFeature();
        f1.getLinkedFeatures().add(f2);
        f1.getLinkedFeatures().add(f3);
        f2.getLinkedFeatures().add(f1);
        f2.getLinkedFeatures().add(f3);
        f3.getLinkedFeatures().add(f1);
        f3.getLinkedFeatures().add(f2);
        f3.getLinkedFeatures().add(f4);
        f4.getLinkedFeatures().add(f3);
        Interaction interaction = new DefaultInteraction();
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));
        Participant p3 = new DefaultParticipant(new DefaultProtein("p3"));
        Participant p4 = new DefaultParticipant(new DefaultProtein("p4"));
        p1.addFeature(f1);
        p2.addFeature(f2);
        p3.addFeature(f3);
        p4.addFeature(f4);
        interaction.addParticipant(p1);
        interaction.addParticipant(p2);
        interaction.addParticipant(p3);
        interaction.addParticipant(p4);

        BindingSiteCliqueFinder<Interaction,Feature> cliqueFinder = new BindingSiteCliqueFinder<Interaction, Feature>(interaction);
        Collection<Set<Feature>> inferredInteractions = cliqueFinder.getAllMaximalCliques();

        Assert.assertEquals(2, inferredInteractions.size());
        Iterator<Set<Feature>> inferredInteractionIterator = inferredInteractions.iterator();
        Set<Feature> set1 = inferredInteractionIterator.next();
        Set<Feature> set2 = inferredInteractionIterator.next();

        Assert.assertEquals(3, set1.size());
        Assert.assertEquals(2, set2.size());
        Assert.assertTrue(set1.contains(f1) && set1.contains(f2) && set1.contains(f3));
        Assert.assertTrue(set2.contains(f4) && set1.contains(f3));
    }

    @Test
    public void test_empty(){
        // create small interaction with 4 participants and 4 different features
        // no inferred interactions because no linked features
        Feature f1 = new DefaultFeature();
        Feature f2 = new DefaultFeature();
        Feature f3 = new DefaultFeature();
        Feature f4 = new DefaultFeature();
        Interaction interaction = new DefaultInteraction();
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));
        Participant p3 = new DefaultParticipant(new DefaultProtein("p3"));
        Participant p4 = new DefaultParticipant(new DefaultProtein("p4"));
        p1.addFeature(f1);
        p2.addFeature(f2);
        p3.addFeature(f3);
        p4.addFeature(f4);
        interaction.addParticipant(p1);
        interaction.addParticipant(p2);
        interaction.addParticipant(p3);
        interaction.addParticipant(p4);

        BindingSiteCliqueFinder<Interaction,Feature> cliqueFinder = new BindingSiteCliqueFinder<Interaction, Feature>(interaction);
        Collection<Set<Feature>> inferredInteractions = cliqueFinder.getAllMaximalCliques();

        Assert.assertEquals(0, inferredInteractions.size());
    }
}
