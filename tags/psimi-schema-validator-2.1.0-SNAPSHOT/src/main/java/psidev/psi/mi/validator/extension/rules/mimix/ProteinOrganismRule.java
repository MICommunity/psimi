package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class checks that each interactor has a organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class ProteinOrganismRule extends ObjectRule<Interactor> {
    public ProteinOrganismRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName( "Protein Organism Check" );
        setDescription( "Checks that each protein has an organism." );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Interactor){
            return true;
        }

        return false;
    }

    @Override
    public Collection<ValidatorMessage> check(Interactor interactor) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int interactorId = interactor.getId();

        Mi25Context context = new Mi25Context();
        context.setId(interactorId);
        context.setObjectLabel("interactor");

        if (RuleUtils.isProtein(ontologyManager, interactor) && !interactor.hasOrganism()){
            messages.add( new ValidatorMessage( "The protein does not have an organism and it is required for MIMIx.",
                                                MessageLevel.ERROR,
                                                context,
                                                this ) );
        }

        return messages;
    }

    public String getId() {
        return "R24";
    }
}
