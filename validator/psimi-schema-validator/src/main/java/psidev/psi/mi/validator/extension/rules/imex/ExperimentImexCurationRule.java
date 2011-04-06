package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks if an experiment have an attribute name set to 'imex-curation'.
 *
 * Rule id = 3. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ExperimentImexCurationRule extends Mi25ExperimentRule {

     public ExperimentImexCurationRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment imex-curation attribute check" );
        setDescription( "Checks that each experiment has at least one attribute 'imex-curation' (MI:0959)." );
        addTip( "Your experiment must have an attribute with a name 'imex-curation'" );
        addTip( "The PSI-MI identifier for imex-curation is: MI:0959" );
    }

    /**
     * Make sure that an experiment has at least one attribute name set to 'imex curation'.
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

        // An experiment must have at least one attribute 'imex-curation'
        RuleUtils.checkPresenceOfAttributeInExperiment(experiment, messages, context, this, "MI:0959", "imex curation");
  
        return messages;
    } 
}
