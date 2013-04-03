package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiCvRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

/**
 * Rule to check that a controlled vocabulary term has annotation topics MI terms that are valid controlled vocabulary annotation topics
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class ControlledVocabularyAnnotationTopicRule extends MiCvRule {
    public ControlledVocabularyAnnotationTopicRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Controlled vocabulary annotation topics Check");
        setDescription("Checks that the controlled vocabulary annotations having a MI term are annotation topics for controlled vocabulary terms.");
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0667&termName=controlled%20vocabulary%20attribute%20name for controlled vocabulary attribute names" );
    }

    @Override
    public Collection<ValidatorMessage> check(CvTerm cvTerm) throws ValidatorException {

        if (!cvTerm.getAnnotations().isEmpty()){
            // list of messages to return
            List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

            for (Annotation annot : cvTerm.getAnnotations()){
                if (annot.getTopic()!= null && annot.getTopic().getMIIdentifier() != null){
                    final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                    Set<String> dbTerms = RuleUtils.collectAccessions(access.getValidTerms("MI:0667", true, false));

                    if (!dbTerms.contains(annot.getTopic().getMIIdentifier())){
                        Mi25Context context = RuleUtils.buildContext(annot, "annotation");

                        context.addAssociatedContext(RuleUtils.buildContext(cvTerm, "controlled vocabulary term"));
                        messages.add( new ValidatorMessage( "The annotation topic "+annot.getTopic()+" is not a topic for controlled vocabulary terms",
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
        return "R36";
    }
}
