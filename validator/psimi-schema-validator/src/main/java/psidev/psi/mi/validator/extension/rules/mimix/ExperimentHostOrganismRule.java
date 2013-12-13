package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * <b> check every experiment has a host organism. </b>.
 * <p/>
 * Rule Id = 7. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 *
 */
public class ExperimentHostOrganismRule extends Mi25ExperimentRule {

    public ExperimentHostOrganismRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Experiment Host Organism Check" );
        setDescription( "Checks that each experiment has a host organism." );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
    }

    /**
     * Checks that each experiment has an host organisms element.
     *
     * @param experiment an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Experiment experiment ) throws ValidatorException {

        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        // check on host organism
        Organism hostOrganism = experiment.getHostOrganism();
        if ( hostOrganism == null ) {
            Mi25Context context = RuleUtils.buildContext(experiment, "experiment");

            messages=Collections.singleton( new ValidatorMessage( "The experiment does not have a host organism and it is required for MIMIx.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R29";
    }
}