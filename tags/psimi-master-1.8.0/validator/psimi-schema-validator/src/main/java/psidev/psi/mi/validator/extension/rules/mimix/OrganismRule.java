package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> check every experiment host organism has an attribute taxid and that is is defined in NEWT. </b>.
 * <p/>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class OrganismRule extends ObjectRule<Organism> {

    public OrganismRule(OntologyManager ontologyMaganer) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Organism Check" );
        setDescription( "Checks that each organism has a valid NCBI taxid" );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
        addTip( "By convention, the taxid for 'in silico' is -5" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Organism){
            return true;
        }

        return false;
    }

    /**
     * Checks that each experiment has an host organisms element with a valid tax id as attribute.
     * Tax id must be a positive integer or -1
     *
     * @param organism to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Organism organism ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();

        // check on host organism
        RuleUtils.checkOrganism(ontologyManager, organism, context, messages, this,
                "Experiment or Interactor", "host organism");

        return messages;
    }
}