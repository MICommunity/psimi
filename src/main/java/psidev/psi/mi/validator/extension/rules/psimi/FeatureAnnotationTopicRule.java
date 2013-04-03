package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiFeatureRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

/**
 * Rule to check that feature annotation topics are valid feature annotation topics
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class FeatureAnnotationTopicRule extends MiFeatureRule {
    public FeatureAnnotationTopicRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Feature annotation topics Check");
        setDescription("Checks that the feature annotations having a MI term are valid feature annotation topics.");
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0668&termName=feature%20att%20name for feature attribute names" );

    }

    @Override
    public Collection<ValidatorMessage> check(FeatureEvidence featureEvidence) throws ValidatorException {

        if (!featureEvidence.getAnnotations().isEmpty()){
            // list of messages to return
            List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
            Mi25Context context = RuleUtils.buildContext(featureEvidence, "feature");

            for (Annotation annot : featureEvidence.getAnnotations()){
                if (annot.getTopic()!= null && annot.getTopic().getMIIdentifier() != null){
                    final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                    Set<String> dbTerms = RuleUtils.collectAccessions(access.getValidTerms("MI:0668", true, false));

                    if (!dbTerms.contains(annot.getTopic().getMIIdentifier())){
                        context.addAssociatedContext(RuleUtils.buildContext(annot, "annotation"));
                        messages.add( new ValidatorMessage( "The annotation topic "+annot.getTopic()+" is not a valid annotation topic for features",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }
                }
            }

            return messages;
        }

        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R39";
    }
}
