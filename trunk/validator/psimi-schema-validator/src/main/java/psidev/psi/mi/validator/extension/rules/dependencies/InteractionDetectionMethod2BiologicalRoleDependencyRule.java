package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the interaction detection method specified matches the biological rule.
 *
 * Rule Id = 12. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2BiologicalRoleDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2BiologicalRoleDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private DependencyMapping mapping;

    public InteractionDetectionMethod2BiologicalRoleDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionDetectionMethod2BiologicalRole();
        
        try {

            URL resource = InteractionDetectionMethod2ExperimentRoleDependencyRule.class
                    .getResource( fileName );
            mapping = new DependencyMapping();

            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Interaction detection method and biological role check" );
        setDescription( "Checks that each interaction does not have any conflicts between the interaction detection method and the biological role of the participants");
        addTip( "Search the possible terms for interaction detection method and biological role on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file resources/interactionDetectionMethod2BiologicalRole.tsv for the possible dependencies interaction detection method - biological role" );        
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their biological roles and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();
        // participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();

        for ( ExperimentDescription experiment : experiments ) {

            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();

            for ( Participant p : participants ) {

                if (p.hasBiologicalRole()){

                    // build a context in case of error
                    Mi25Context context = new Mi25Context();
                    context.setInteractionId( interaction.getId() );
                    context.setExperimentId( experiment.getId());
                    context.setParticipantId( p.getId() );

                    BiologicalRole biolRole = p.getBiologicalRole();

                    messages.addAll( mapping.check( method, biolRole, context, this ) );
                }
            }

        } // experiments

        return messages;
    }

}
