package psidev.psi.mi.jami.examples.core;

import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.jami.analysis.graph.BindingSiteCliqueFinder;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.util.Collection;
import java.util.Set;

/**
 * In this code example, we can build a clique graph of binding sites in an interactions.
 *
 * An example of this use case is for writing inferred interactions in PSI-XML standard formats.
 *
 * For instance, an interaction I1 has 3 participants P1, P2 and P3.
 * P1 has two features F1 and F2, P2 has one feature F3 and P3 has one feature F4
 * We know that F1 has two linked features F3 and F4, F2 binds to F3, F3 binds to F1, F4 and F2 and F4 binds to F1, F3
 * The clique graph would give two sets of binding features:
 * - F1,F4,F3
 * - F3, F2
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/02/15</pre>
 */

public class BuildBindingSitesCliqueGraph {

    public static void main(String[] args) throws Exception{
         // Create test environment
        Interaction i1 = createInteractionWithLinkedFeatures();

        // create clique graph
        BindingSiteCliqueFinder<Interaction, Feature> cliqueFinder = new BindingSiteCliqueFinder<Interaction, Feature>(i1);

        // collect all maximal cliques
        Collection<Set<Feature>> inferredInteractions = cliqueFinder.getAllMaximalCliques();

        int index = 1;
        for (Set<Feature> inferredInteraction : inferredInteractions){
            System.out.println("Inferred interaction "+index+"involves: ");
            System.out.println(StringUtils.join(inferredInteraction, ", "));
            index++;
        }
    }

    protected static Interaction createInteractionWithLinkedFeatures() {
        // first create an interaction
        Interaction interaction = new DefaultInteractionEvidence();

        // add three participants
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("test protein 1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("test protein 2"));
        ParticipantEvidence p3 = new DefaultParticipantEvidence(new DefaultProtein("test protein 3"));
        interaction.addParticipant(p1);
        interaction.addParticipant(p2);
        interaction.addParticipant(p3);

        // add features
        FeatureEvidence f1 = new DefaultFeatureEvidence("test feature 1", null);
        f1.getRanges().add(RangeUtils.createCertainRange(3));
        p1.addFeature(f1);
        FeatureEvidence f2 = new DefaultFeatureEvidence("test feature 2", null);
        p1.addFeature(f2);
        f2.getRanges().add(RangeUtils.createCertainRange(4));
        FeatureEvidence f3 = new DefaultFeatureEvidence("test feature 3", null);
        p2.addFeature(f3);
        f3.getRanges().add(RangeUtils.createCertainRange(5));
        FeatureEvidence f4 = new DefaultFeatureEvidence("test feature 4", null);
        f4.getRanges().add(RangeUtils.createCertainRange(6));
        p3.addFeature(f4);

        // add linked features
        f1.getLinkedFeatures().add(f3);
        f1.getLinkedFeatures().add(f4);
        f2.getLinkedFeatures().add(f3);
        f3.getLinkedFeatures().add(f1);
        f3.getLinkedFeatures().add(f2);
        f3.getLinkedFeatures().add(f4);
        f4.getLinkedFeatures().add(f1);
        f4.getLinkedFeatures().add(f3);
        return interaction;
    }
}
