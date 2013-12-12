package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ParticipantIdentificationMethodRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ParticipantIdentificationMethodRuleTest extends AbstractRuleTest {

    public ParticipantIdentificationMethodRuleTest() throws OntologyLoaderException {
        super( ParticipantIdentificationMethodRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void validate_OneParticipantDetectionMethod() throws Exception {

        CvTerm partDetMet = CvTermUtils.createMICvTerm("predetermined participant", "MI:0396");

        InteractionEvidence interaction = new DefaultInteractionEvidence();

        populatesParticipants(interaction, partDetMet);

        ParticipantIdentificationMethodRule rule = new ParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction .getParticipants().iterator().next());
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_NoParticipantDetectionMethod() throws Exception {

        ParticipantIdentificationMethodRule rule = new ParticipantIdentificationMethodRule( ontologyMaganer );

        InteractionEvidence interaction = new DefaultInteractionEvidence();

        populatesParticipants(interaction, null);

        final Collection<ValidatorMessage> messages = rule.check( interaction .getParticipants().iterator().next() );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_OneParticipantDetectionMethodAtTheParticipantLevel() throws Exception {

        CvTerm partDetMet = CvTermUtils.createMICvTerm("predetermined participant", "MI:0396");

        InteractionEvidence interaction = new DefaultInteractionEvidence();

        populatesParticipants(interaction, partDetMet);

        ParticipantIdentificationMethodRule rule = new ParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction .getParticipants().iterator().next() );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_NoParticipantDetectionCrossReferences() throws Exception {


        CvTerm partDetMet = new DefaultCvTerm("test");

        ParticipantIdentificationMethodRule rule = new ParticipantIdentificationMethodRule( ontologyMaganer );

        InteractionEvidence interaction = new DefaultInteractionEvidence();

        populatesParticipants(interaction, partDetMet);

        final Collection<ValidatorMessage> messages = rule.check( interaction .getParticipants().iterator().next() );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    private void populatesParticipants(InteractionEvidence interaction, CvTerm partMethod){
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        if (partMethod != null){
            p1.getIdentificationMethods().add(partMethod);
        }
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));
        if (partMethod != null){
            p2.getIdentificationMethods().add(partMethod);
        }
        interaction.addParticipant(p1);
        interaction.addParticipant(p2);
    }
}
