package psidev.psi.mi.validator.extension.rules.dependencies;

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
 * Rule that allows to check whether the interaction detection method specified matches the participant detection methods.
 *
 * Rule Id = 9. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule extends Mi25InteractionRule {

    //private static DependencyMappingInteractionDetectionMethod2InteractionType mapping;
    private DependencyMapping mapping = new DependencyMapping();

    public InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionDetectionMethod2ParticipantIdentificationMethod();
        
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
        setName( "Interaction detection method and participant identification method check" );
        setDescription( "Checks that each interaction does not have any conflicts between the interaction detection method and the participant identification methods." );
        addTip( "Search the possible terms for interaction detection method and participant identification method on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/InteractionDetectionMethod2ParticipantIdentificationMethod.tsv for the possible dependencies interaction detection method - participant identification method" );                                
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their identification method and
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
            ParticipantIdentificationMethod participantMethod = experiment.getParticipantIdentificationMethod();

            for ( Participant p : participants ) {
                // build a context in case of error
                Mi25Context context = new Mi25Context();
                context.setInteractionId( interaction.getId() );
                context.setExperimentId( experiment.getId());
                context.setParticipantId( p.getId());

                if (p.hasParticipantIdentificationMethods()){
                    Collection<ParticipantIdentificationMethod> participantIdentification = p.getParticipantIdentificationMethods();

                    for (ParticipantIdentificationMethod pm : participantIdentification){
                        messages.addAll( mapping.check( method, pm, context, this ) );
                    }
                }
                else if (participantMethod != null) {
                    messages.addAll( mapping.check( method, participantMethod, context, this ) );
                }
            }

        } // experiments

        return messages;
    }
}
