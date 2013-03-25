package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.PublicationRuleUtils;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks if the experiment has at least one cross-reference type set to 'imex-primary'. Then check if the imex
 * imex ID(s) is(are) valid.
 *
 * Rule Id = 2. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class ExperimentImexPrimaryRule extends ObjectRule<Publication> {

    public ExperimentImexPrimaryRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Imex-primary cross reference check" );
        setDescription( "Checks that each experiment has a at least one cross reference 'imex-primary' and that all the imex" +
                "IDs are correct." );
        addTip( "All records must have an IMEx ID (IM-xxx) when there is a cross reference type: imex-primary" );
        addTip( "The PSI-MI identifier for imex-primary is: MI:0662" );
    }

    @Override
    public boolean canCheck(Object t) {
        return ( t != null && t instanceof Publication);

    }

    /**
     * Make sure that an experiment has a valid IMEX id in its xref.
     *
     * @param pub a publication to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Publication pub ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(pub, "publication");

        // Check xRef
        if (pub.getImexId() != null){
            PublicationRuleUtils.checkImexId(pub.getImexId(), messages, context, this);

        }
        else {
            messages.add( new ValidatorMessage( "The experiment does not have an IMEx primary cross reference. An IMEx cross reference with a reference type set" +
                    " to 'imex-primary' (MI:0662) is required for IMEx.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R31";
    }
}
