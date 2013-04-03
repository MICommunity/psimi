package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiInteractorRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule will check that each interactor type is not set to 'small molecule' or 'nucleic acid'
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/11</pre>
 */

public class InteractorTypeRule extends MiInteractorRule{

        public InteractorTypeRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interactor type check" );

        setDescription( "Interactor's type cannot be set to 'nucleic acid' or 'small molecule' as it is currently outside " +
                "of the remit of IMEx." );

        addTip( "The possible interactor types can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0313&termName=interactor%20type" );
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interactor to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retrieve the MI Ontology.
     */
    public Collection<ValidatorMessage> check( psidev.psi.mi.jami.model.Interactor interactor ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        if (interactor.getType() == null){
            Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );

            messages.add( new ValidatorMessage( "The interactor does not have an interactor type and it is required by IMEx.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }
        else {
            if( RuleUtils.isNucleicAcid(ontologyManager, interactor) || RuleUtils.isSmallMolecule(ontologyManager, interactor)) {
                Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );

                messages.add( new ValidatorMessage( "'nucleic acids' and 'small molecules' are currently outside of the remit of IMEx and " +
                        "should be removed from the record.",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
            else {
                Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );

                RuleUtils.checkUniquePsiMIXRef(interactor.getType(), messages, context, this);
            }
        }

        return messages;
    }

    public String getId() {
        return "R72";
    }
}
