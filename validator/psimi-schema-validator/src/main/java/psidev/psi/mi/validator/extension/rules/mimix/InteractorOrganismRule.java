package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

/**
 * This class checks that each interactor has a organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractorOrganismRule extends AbstractMIRule<Interactor> {
    public InteractorOrganismRule(OntologyManager ontologyManager) {
        super(ontologyManager, Interactor.class);

        // describe the rule.
        setName( "Interactor Organism Check" );
        setDescription( "Checks that each protein and gene has an organism." );
        addTip( "Search http://www.ebi.ac.uk/newt/display with an organism name to retrieve its taxid" );
        addTip( "By convention, the taxid for 'in vitro' is -1" );
        addTip( "By convention, the taxid for 'chemical synthesis' is -2" );
        addTip( "By convention, the taxid for 'unknown' is -3" );
        addTip( "By convention, the taxid for 'in vivo' is -4" );
    }

    @Override
    public Collection<ValidatorMessage> check(Interactor interactor) throws ValidatorException {

        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        if (interactor.getInteractorType() != null && RuleUtils.isProtein(ontologyManager, interactor) && interactor.getOrganism() == null){
            Mi25Context context = RuleUtils.buildContext(interactor, "interactor");

            messages=Collections.singleton(new ValidatorMessage("The protein does not have an organism and it is required for MIMIx.",
                    MessageLevel.ERROR,
                    context,
                    this));
        }
        else if ( InteractorUtils.doesInteractorHaveType(interactor, Gene.GENE_MI, Gene.GENE) && interactor.getOrganism() == null){
            Mi25Context context = RuleUtils.buildContext(interactor, "interactor");

            messages=Collections.singleton( new ValidatorMessage( "The gene does not have an organism and it is required for MIMIx.",
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
