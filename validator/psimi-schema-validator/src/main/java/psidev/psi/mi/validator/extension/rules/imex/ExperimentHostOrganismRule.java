package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> check every experiment host organism has an attribute taxid and that is is defined in NEWT. </b>.
 * <p/>
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 *
 */
public class ExperimentHostOrganismRule extends Mi25ExperimentRule {

    public ExperimentHostOrganismRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Host Organism Check" );
        setDescription( "Checks that each experiment has a host organism with a valid NCBI taxid" );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
    }

    /**
     * Checks that each experiment has an host organisms element with a valid tax id as attribute.
     * Tax id must be a positive integer or -1
     *
     * @param experiment an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int experimentId = experiment.getId();

        Mi25Context context = new Mi25Context();
        context.setExperimentId( experimentId );

        // check on host organism
        Collection<Organism> hostOrganisms = experiment.getHostOrganisms();
        if ( hostOrganisms.isEmpty() ) {

            messages.add( new ValidatorMessage( "Experiment without host organism",
                                                MessageLevel.ERROR,
                                                context,
                                                this ) );
        } else {

            for ( Organism hostOrganism : hostOrganisms ) {

                 RuleUtils.checkImexOrganism( ontologyManager, hostOrganism, context, messages, this,
                                          "Experiment", "host organism" );

            } //for
        } // else

        return messages;
    }
}