package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the interaction detection method specified matches the interaction type.
 * @author Marine Dumousseau
 */
public class InteractionDetectionMethod2ExperimentRoleDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2InteractionTypeDependencyRule.class );

    private static DependencyMapping mapping;

    public InteractionDetectionMethod2ExperimentRoleDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
            try {
                // TODO : the resource should be a final private static or should be put as argument of the constructor

                String resource = InteractionDetectionMethod2ExperimentRoleDependencyRule.class
                .getResource( "/InteractionDetectionMethod2ExperimentRole.tsv" ).getFile();

                mapping = new DependencyMapping();
                
                mapping.buildMappingFromFile( mi, resource );

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ValidatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            // describe the rule.
        setName( "Dependency between interaction detection method and experimental role" );
//        addTip( "" );
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their experimental roles and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = new Mi25Context();

        context.setInteractionId( interaction.getId() );
        // experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();
        // participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();

        for ( ExperimentDescription experiment : experiments ) {

            context.setExperimentId( experiment.getId());
            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();

            for ( Participant p : participants ) {
                Collection<ExperimentalRole> roles = p.getExperimentalRoles();
                for (ExperimentalRole role : roles){
                    messages.addAll( mapping.check( method, role, context, this ) );
                }
            }

        } // experiments

        return messages;
    }
}
