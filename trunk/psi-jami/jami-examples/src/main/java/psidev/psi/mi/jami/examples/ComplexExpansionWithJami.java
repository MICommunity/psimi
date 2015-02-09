package psidev.psi.mi.jami.examples;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.BipartiteExpansion;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.MatrixExpansion;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.xml.model.extension.factory.xml25.XmlBinaryInteractionFactory;

import java.util.Collection;

/**
 * In this code example, we wil do spoke, matrix and bipartite expansion to explode n-ary interactions in binary interactions.
 *
 * - Spoke expansion: This assumes that all molecules in the complex interact with a single designated molecule, usually the bait.
 * -> interaction evidences: A bait is determined based on experimental role of each participant. Order of select :
 * bait > alternative bait (fluorescent donor, suppressor gene, ..) > alternative bait (enzyme, donor, phosphate donor, electron donor, photon donor) > alphabetical order
 * In case of several baits, the first one in alphabetical order is chosen. There will be as many binary interactions as we have 'preys'
 * -> modelled interactions: A bait is determined only bases on biological role :
 * alternative bait (enzyme, donor, phosphate donor, electron donor, photon donor) > alphabetical order
 *
 * - Matrix expansion: This assumes that all molecules in the complex interact with each other
 *
 * - Bipartite expansion: This assumes that all molecules in the complex interact with a single externally designated entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/02/15</pre>
 */

public class ComplexExpansionWithJami {

    public static void main(String[] args) throws Exception{
        // Create test environment
        Interaction i1 = createInteractionWithBait();

        // create spoke expansion
        ComplexExpansionMethod spokeMethod = new SpokeExpansion();
        // check if the interaction can be spoke expanded (for spoke expansion, it does not accept empty interactions)
        if (spokeMethod.isInteractionExpandable(i1)){
            Collection<BinaryInteraction> spokeExpanded = spokeMethod.expand(i1);
        }

        // create matrix expansion
        ComplexExpansionMethod matrixMethod = new MatrixExpansion();
        // check if the interaction can be matrix expanded (for spoke expansion, it does not accept empty interactions)
        if (matrixMethod.isInteractionExpandable(i1)){
            Collection<BinaryInteraction> matrixExpanded = matrixMethod.expand(i1);
        }

        // create bipartite expansion
        ComplexExpansionMethod bipartiteMethod = new BipartiteExpansion();
        // check if the interaction can be bipartite expanded (for spoke expansion, it does not accept empty interactions)
        if (bipartiteMethod.isInteractionExpandable(i1)){
            Collection<BinaryInteraction> bipartiteExpanded = bipartiteMethod.expand(i1);
        }

        // advanced
        // You can set your binary interaction factory in case you want to inject your own implementation of binary interactions.
        // So far, three implementations exist : DefaultBinaryInteractionFactory (based on default implementations) and XmlBinaryInteractionFactory (one for 2.5 and one for 3.0)
        // for XML binary interactions,
        // The XML factory is closely linked to XML interactions and should not be used for other implementations
        //
        spokeMethod.setBinaryInteractionFactory(new XmlBinaryInteractionFactory());

    }

    protected static Interaction createInteractionWithBait() {
        // first create an interaction

        InteractionEvidence nary = new DefaultInteractionEvidence();
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));
        ParticipantEvidence p3 = new DefaultParticipantEvidence(new DefaultProtein("p3"));
        nary.addParticipant(p1);
        nary.addParticipant(p2);
        nary.addParticipant(p3);

        return nary;
    }
}
