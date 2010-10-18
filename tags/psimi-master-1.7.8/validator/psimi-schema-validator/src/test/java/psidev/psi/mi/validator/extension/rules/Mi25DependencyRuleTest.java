package psidev.psi.mi.validator.extension.rules;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.dependencies.InteractionDetectionMethod2ExperimentRoleDependencyRule;
import psidev.psi.mi.validator.extension.rules.dependencies.InteractionDetectionMethod2ExperimentalRoleDependencyRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Iterator;

/**
 * Mi25DependencyRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public class Mi25DependencyRuleTest extends AbstractRuleTest {

    public Mi25DependencyRuleTest() throws OntologyLoaderException {
        super( Mi25DependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check() throws Exception {

        // This interaction only contains experimental role that are int he inclusion list.
        Interaction interaction = buildInteractionDeterministic();

        final InteractionDetectionMethod2ExperimentalRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check2() throws Exception {

        // This interaction uses an experimental role that not it neither inclusion or exclision lists
        Interaction interaction = buildInteraction( buildDetectionMethod( "MI:0018", "2hybrid" ),
                                                    buildExperimentalRole( "MI:0496", "bait" ),
                                                    buildExperimentalRole( "MI:0503", "self" )
        );

        final InteractionDetectionMethod2ExperimentalRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        // self is not supposed to be there
        for ( ValidatorMessage message : messages ) {
            System.out.println( message );
        }
        Assert.assertEquals( 1, messages.size() );
        Assert.assertEquals( MessageLevel.INFO, messages.iterator().next().getLevel() );
    }

    @Test
    public void check3() throws Exception {

        // This interaction uses an experimental role that is in the exclusion list
        Interaction interaction = buildInteraction( buildDetectionMethod( "MI:0018", "2hybrid" ),
                                                    buildExperimentalRole( "MI:0496", "bait" ),
                                                    buildExperimentalRole( "MI:0583", "fluorescent donor" )
        );

        final InteractionDetectionMethod2ExperimentalRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        // self is not supposed to be there
        for ( ValidatorMessage message : messages ) {
            System.out.println( message );
        }
        Assert.assertEquals( 1, messages.size() );
        Assert.assertEquals( MessageLevel.ERROR, messages.iterator().next().getLevel() );
    }

    @Test
    public void check4() throws Exception {

        // This interaction uses an experimental role that is in the exclusion list
        Interaction interaction = buildInteraction( buildDetectionMethod( "MI:0018", "2hybrid" ),
                                                    buildExperimentalRole( "MI:0496", "bait" ),
                                                    buildExperimentalRole( "MI:0583", "fluorescent donor" ),
                                                    buildExperimentalRole( "MI:0584", "fluorescent acceptor" )
        );

        final InteractionDetectionMethod2ExperimentalRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        // self is not supposed to be there
        for ( ValidatorMessage message : messages ) {
            System.out.println( message );
        }
        Assert.assertEquals( 2, messages.size() );
        final Iterator<ValidatorMessage> iterator = messages.iterator();
        Assert.assertEquals( MessageLevel.ERROR, iterator.next().getLevel() );
        Assert.assertEquals( MessageLevel.ERROR, iterator.next().getLevel() );
    }
}
