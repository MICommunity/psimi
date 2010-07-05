package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.PublicationRuleUtils;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

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
public class ExperimentImexPrimaryRule extends Mi25ExperimentRule {

    // The good syntax of an Imex ID.
    Pattern IMEx_ID = Pattern.compile( "IM-[0-9]+" );

    public ExperimentImexPrimaryRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Imex-primary cross reference check" );
        setDescription( "Checks that each experiment has a at least one publication reference type set to 'imex-primary' and that all the imex" +
                "IDs are correct." );
        addTip( "All records must have an IMEx ID (IM-xxx) when there is a cross reference type: imex-primary" );
        addTip( "The PSI-MI identifier for imex-primary is: MI:0662" );
    }

    /**
     * Make sure that an experiment has a valid IMEX id in its xref.
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

        // Check xRef
        if (experiment.hasXref()){
            Collection<DbReference> dbReferences = experiment.getXref().getAllDbReferences();

            // search for reference type: imex-primary (should not be empty)
            Collection<DbReference> imexReferences = RuleUtils.findByReferenceType( dbReferences, "MI:0662", "imex-primary" );

            // At least one cross reference type 'imex-primary' is required and the Imex ID must be valid.
            if (imexReferences.isEmpty()){
                messages.add( new ValidatorMessage( "The experiment "+ experimentId +" has " + dbReferences.size() + " cross references but no one has a reference type set to 'imex-primary'. It is required for IMEx.",
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }
            else {
                PublicationRuleUtils.checkImexId(imexReferences, messages, context, this);
            }

        }
        else {
            messages.add( new ValidatorMessage( "The experiment "+ experimentId +" does not have any cross references. At least one cross reference with a reference type set" +
                    " to 'imex-primary' (MI:0662) is required for IMEx.",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

}
