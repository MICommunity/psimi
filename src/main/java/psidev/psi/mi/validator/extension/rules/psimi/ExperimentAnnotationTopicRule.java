package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

/**
 * Rule to check that an experiment annotation with a MI number is a valid experiment annotation topic
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class ExperimentAnnotationTopicRule extends Mi25ExperimentRule {
    public ExperimentAnnotationTopicRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName( "Experiment annotation topics Check" );
        setDescription( "Checks that the experiment annotations having a MI term are valid experiment annotation topics." );
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0665&termName=experiment%20att%20name for experiment attribute names" );
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0954&termName=curation%20quality for curation quality" );
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A1045&termName=curation%20content for curation content" );
    }

    @Override
    public Collection<ValidatorMessage> check(Experiment experiment) throws ValidatorException {

        if (!experiment.getAnnotations().isEmpty()){
            // list of messages to return
            List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

            for (Annotation annot : experiment.getAnnotations()){
                if (annot.getTopic()!= null && annot.getTopic().getMIIdentifier() != null){
                    final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                    Set<String> dbTerms = RuleUtils.collectAccessions(access.getValidTerms("MI:0665", true, false));
                    dbTerms.addAll(RuleUtils.collectAccessions(access.getValidTerms("MI:1045", true, false)));
                    dbTerms.addAll(RuleUtils.collectAccessions(access.getValidTerms("MI:0954", true, false)));

                    if (!dbTerms.contains(annot.getTopic().getMIIdentifier())){
                        Mi25Context context = RuleUtils.buildContext(annot, "annotation");
                        context.addAssociatedContext(RuleUtils.buildContext(experiment, "experiment"));
                        messages.add( new ValidatorMessage( "The annotation topic "+annot.getTopic()+" is not a valid annotation topic for experiments",
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
        return "R35";
    }
}
