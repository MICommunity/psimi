package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks if an experiment have an attribute name set to 'imex-curation' and an attribute name set to 'full coverage'.
 *
 * @author Marine Dumousseau
 */
public class ExperimentAttributesRule extends Mi25ExperimentRule {

    public ExperimentAttributesRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment attributes ImeX check" );
        setDescription( "Checks that each experiment has at least one attribute name set to 'imex-curation' (MI:0959) and one attribute name set to 'full coverage' (MI:0957)." );
    }

    /**
     * Make sure that an experiment has at least one attribute name set to 'imex curation' and one attribute name set to 'full coverage'.
     *
     * @param experiment an experiment to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int experimentId = experiment.getId();

        Mi25Context context = new Mi25Context();
        context.setExperimentId( experimentId );

        // An experiment must have at least one attribute 'imex-curation' and one attribute 'full coverage'
        if (experiment.hasAttributes()){
            // The attributes of the experiment
            Collection<Attribute> attributes = experiment.getAttributes();
            // The attributes with a name 'imex-curation'
            Collection<Attribute> imexCuration = RuleUtils.findByAttributeName(attributes, "MI:0959", "imex curation");
            // The attributes with a name 'full coverage'
            Collection<Attribute> fullCoverage = RuleUtils.findByAttributeName(attributes, "MI:0957", "full coverage");

            if (imexCuration.isEmpty()){
                messages.add( new ValidatorMessage( "At least one attribute name set to 'imex curation' (MI:0959) is required ",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) ); 
            }

            if (fullCoverage.isEmpty()){
                messages.add( new ValidatorMessage( "At least one attribute name set to 'full coverage' (MI:0957) is required ",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "At least one attribute name set to 'imex curation' (MI:0959) and one attribute name set to 'full coverage' (MI:0957) are required ",
                                                MessageLevel.ERROR,
                                                context,
                                                this ) );
        }

        return messages;
    }
}
