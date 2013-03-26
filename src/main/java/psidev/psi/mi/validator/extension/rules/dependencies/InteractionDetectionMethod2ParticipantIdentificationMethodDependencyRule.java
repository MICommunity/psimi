package psidev.psi.mi.validator.extension.rules.dependencies;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

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
public class InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule extends ObjectRule<ParticipantEvidence> {

    //private static DependencyMappingInteractionDetectionMethod2InteractionType mapping;
    private DependencyMapping mapping = new DependencyMapping();

    public InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionDetectionMethod2ParticipantIdentificationMethod();
        
            try {

                URL resource = InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule.class
                    .getResource( fileName );

                mapping = new DependencyMapping();
                mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
            // describe the rule.
        setName( "Dependency Check : Interaction detection method and participant identification method" );
        setDescription( "Checks that each association interaction detection method - participant identification methods is valid and respects IMEx curation rules." );
        addTip( "Search the possible terms for interaction detection method and participant identification method on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/InteractionDetectionMethod2ParticipantIdentificationMethod.tsv for the possible dependencies interaction detection method - participant identification method" );                                
    }

    public boolean canCheck(Object o) {
        return ( o != null && o instanceof ParticipantEvidence);
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their identification method and
     * check if the dependencies are correct.
     *
     * @param participant a participant to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( ParticipantEvidence participant ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = RuleUtils.buildContext(participant, "participant");

        if (participant.getInteractionEvidence() != null){
            InteractionEvidence interaction = participant.getInteractionEvidence();
            context.addAssociatedContext(RuleUtils.buildContext(interaction, "interaction"));

            if (interaction.getExperiment() != null){
                Experiment exp = interaction.getExperiment();
                context.addAssociatedContext(RuleUtils.buildContext(exp, "experiment"));
                CvTerm detMethod = exp.getInteractionDetectionMethod();
                if (detMethod != null){
                    context.addAssociatedContext(RuleUtils.buildContext(detMethod, "interaction detection method"));

                    if (participant.getIdentificationMethod() != null){
                        context.addAssociatedContext(RuleUtils.buildContext(participant.getIdentificationMethod(), "participant identification method"));
                        CvTerm participantMethod = participant.getIdentificationMethod();

                        messages.addAll( mapping.check( detMethod, participantMethod, context, this ) );
                    }
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R49";
    }
}
