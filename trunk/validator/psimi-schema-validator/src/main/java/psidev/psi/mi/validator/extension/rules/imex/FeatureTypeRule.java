package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This rule checks that each feature has a valid feature type.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class FeatureTypeRule extends AbstractMIRule<Feature> {

    private static final Log log = LogFactory.getLog(FeatureTypeRule.class);

    public FeatureTypeRule(OntologyManager ontologyManager) {
        super(ontologyManager, Feature.class);

        // describe the rule.
        setName("Participant's feature Type Check");
        setDescription("Checks that each participant's feature has a feature type with " +
                "a valid PSI MI/PSI-MOD cross reference.");
        addTip( "See http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0116&termName=feature%20type for the existing feature types" );
    }

    @Override
    public Collection<ValidatorMessage> check(Feature feature) throws ValidatorException {
        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;

        if (feature.getType() != null){

            if (feature.getType().getMODIdentifier() != null){
                final OntologyAccess access = ontologyManager.getOntologyAccess("MOD");
                final OntologyTermI dbTerms = access.getTermForAccession(feature.getType().getMODIdentifier());

                if (dbTerms == null){
                    Mi25Context context = RuleUtils.buildContext(feature, "feature");
                    messages = new ArrayList<ValidatorMessage>();
                    messages.add( new ValidatorMessage( "The feature type is not a valid MOD term.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }
            if (feature.getType().getMIIdentifier() != null){
                final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                final OntologyTermI dbTerms = access.getTermForAccession(feature.getType().getMIIdentifier());

                if (dbTerms == null){
                    Mi25Context context = RuleUtils.buildContext(feature, "feature");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The feature type is not a valid MI term. The valid MI terms for alias types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0116&termName=feature%20type",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerms);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:0116".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        Mi25Context context = RuleUtils.buildContext(feature, "feature");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The feature type is not a valid MI term. The valid MI terms for alias types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0116&termName=feature%20type",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }
        }
        else {
            Mi25Context context = RuleUtils.buildContext(feature, "feature");

            messages=Collections.singleton(new ValidatorMessage("The feature does not have a feature type. It is required by IMEx.'",
                    MessageLevel.ERROR,
                    context,
                    this));
        }

        return messages;
    }

    public String getId() {
        return "R33";
    }
}
