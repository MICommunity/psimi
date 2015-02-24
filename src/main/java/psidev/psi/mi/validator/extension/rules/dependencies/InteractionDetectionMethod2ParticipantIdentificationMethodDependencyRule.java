package psidev.psi.mi.validator.extension.rules.dependencies;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.MiValidatorContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Rule that allows to check whether the interaction detection method specified matches the participant detection methods.
 *
 * Rule Id = 9. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule extends AbstractMIRule<ParticipantEvidence> {

    //private static DependencyMappingInteractionDetectionMethod2InteractionType mapping;
    private DependencyMapping mapping = new DependencyMapping();

    public InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager,ParticipantEvidence.class );
        MiValidatorContext validatorContext = MiValidatorContext.getCurrentInstance();

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

    /**
     * For each experiment associated with this interaction, collect all respective participants and their identification method and
     * check if the dependencies are correct.
     *
     * @param participant a participant to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( ParticipantEvidence participant ) throws ValidatorException {

        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        if (participant.getInteraction() != null){
            InteractionEvidence interaction = participant.getInteraction();
            Experiment exp = interaction.getExperiment();

            if (exp != null){
                CvTerm detMethod = exp.getInteractionDetectionMethod();
                if (detMethod != null){

                    if (!participant.getIdentificationMethods().isEmpty()){
                        messages = new ArrayList<ValidatorMessage>(participant.getIdentificationMethods().size());
                        for (CvTerm method : participant.getIdentificationMethods()){
                            // build a context in case of error
                            MiContext context = RuleUtils.buildContext(participant, "participant");
                            context.addAssociatedContext(RuleUtils.buildContext(exp, "experiment"));
                            messages.addAll( mapping.check( detMethod, method, context, this ) );
                        }
                    }
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R47";
    }
}
