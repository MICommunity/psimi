package psidev.psi.mi.validator.extension.rules.imex;

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
 * <b> check every experiment host organism has a valid attribute taxid. </b>.
 * <p/>
 * Rule Id = 8. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 *
 */
public class TaxIdHostOrganismRule extends ObjectRule<Organism> {

    public TaxIdHostOrganismRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "TaxId Organism Check" );
        setDescription( "Checks that each organism has a valid NCBI taxid" );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        //addTip( "By convention, the taxid for 'unknown' is -3" );
        //addTip( "By convention, the taxid for 'in vivo' is -4" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Organism){
            return true;
        }
        return false;
    }

    /**
     * Checks that each organisms element has a valid tax id as attribute.
     * Tax id must be a positive integer or -1
     *
     * @param organism an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Organism organism ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();

        RuleUtils.checkImexOrganism( ontologyManager, organism, context, messages, this,
                                          "Experiment or Interactor", "organism" );

        return messages;
    }

    public String getId() {
        return "R33";
    }
}