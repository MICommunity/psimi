package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.MiExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * This rule is for checking that all experiment have a publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MissingExperimentPublicationRule extends MiExperimentRule {


    public MissingExperimentPublicationRule(OntologyManager ontologyManager) {
        super(ontologyManager);
        setName( "Experiment's publication check" );

        setDescription( "Check that each experiment has a publication." );
    }

    @Override
    public Collection<ValidatorMessage> check(Experiment experiment) throws ValidatorException {

        if (experiment.getPublication() == null){
            // list of messages to return
            MiContext experimentContext = RuleUtils.buildContext(experiment, "experiment");
            return Collections.singletonList(new ValidatorMessage( "Experiment must have a publication.'",
                    MessageLevel.ERROR,
                    experimentContext,
                    this ) );
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R15";
    }
}
