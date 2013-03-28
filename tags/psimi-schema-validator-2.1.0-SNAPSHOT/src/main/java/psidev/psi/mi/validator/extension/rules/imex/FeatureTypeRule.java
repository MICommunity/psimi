package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
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
 * This rule checks that each feature has a valid feature type.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class FeatureTypeRule extends ObjectRule<FeatureEvidence> {

    private static final Log log = LogFactory.getLog(FeatureTypeRule.class);

    public FeatureTypeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Participant's feature Type Check");
        setDescription("Checks that each participant's feature has a feature type with " +
                "a valid PSI MI cross reference.");
        addTip( "See http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0116&termName=feature%20type for the existing feature types" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof FeatureEvidence){
            return true;
        }

        return false;
    }

    @Override
    public Collection<ValidatorMessage> check(FeatureEvidence feature) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = RuleUtils.buildContext(feature, "feature");

        if (feature.getType() != null){

            RuleUtils.checkUniquePsiMIOrModXRef(feature.getType(), messages, context, this, RuleUtils.FEATURE_TYPE);

        }
        else {
            messages.add( new ValidatorMessage( "The feature does not have a feature type. It is required by IMEx.'",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R67";
    }
}
