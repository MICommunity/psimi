package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiParticipantRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

/**
 * Rule to check that participant annotation topics MI terms are valid participant annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class ParticipantAnnotationTopicRule extends MiParticipantRule {
    public ParticipantAnnotationTopicRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Participant annotation topics Check");
        setDescription("Checks that the participant annotations having a MI term are valid participant annotation topics.");
        addTip( "Check http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0666&termName=participant%20att%20name for participant attribute names" );
    }

    @Override
    public Collection<ValidatorMessage> check(ParticipantEvidence participantEvidence) throws ValidatorException {

        if (!participantEvidence.getAnnotations().isEmpty()){
            // list of messages to return
            List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

            for (Annotation annot : participantEvidence.getAnnotations()){
                if (annot.getTopic()!= null && annot.getTopic().getMIIdentifier() != null){
                    final OntologyAccess access = ontologyManager.getOntologyAccess("MI");
                    Set<String> dbTerms = RuleUtils.collectAccessions(access.getValidTerms("MI:0666", true, false));

                    if (!dbTerms.contains(annot.getTopic().getMIIdentifier())){
                        Mi25Context context = RuleUtils.buildContext(annot, "annotation");

                        context.addAssociatedContext(RuleUtils.buildContext(participantEvidence, "participant"));
                        messages.add( new ValidatorMessage( "The annotation topic "+annot.getTopic()+" is not a valid participant annotation topic",
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
        return "R37";
    }
}
