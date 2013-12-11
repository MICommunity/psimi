package psidev.psi.mi.validator;

import psidev.psi.mi.validator.extension.Mi25ClusteredContext;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;

import java.util.*;

/**
 * Utility class for validator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/13</pre>
 */

public class ValidatorUtils {

    public static Collection<ValidatorMessage> clusterByMessagesAndRules (Collection<ValidatorMessage> messages){
        if (messages == null || messages.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        Collection<ValidatorMessage> clusteredMessages = new ArrayList<ValidatorMessage>( messages.size() );

        // build a first clustering by message and rule
        Map<String, Map<Rule, Set<ValidatorMessage>>> clustering = new HashMap<String, Map<Rule, Set<ValidatorMessage>>>(messages.size());
        for (ValidatorMessage message : messages){
            // we cluster by identical message
            if (clustering.containsKey(message.getMessage())){
                Map<Rule, Set<ValidatorMessage>> messagesCluster = clustering.get(message.getMessage());
                // cluster the rule
                if (messagesCluster.containsKey(message.getRule())){
                    messagesCluster.get(message.getRule()).add(message);
                }
                else{
                    Set<ValidatorMessage> validatorContexts = new HashSet<ValidatorMessage>();
                    validatorContexts.add(message);
                    messagesCluster.put(message.getRule(), validatorContexts);
                }
            }
            else {
                Map<Rule, Set<ValidatorMessage>> messagesCluster = new HashMap<Rule, Set<ValidatorMessage>>();

                Set<ValidatorMessage> validatorContexts = new HashSet<ValidatorMessage>();
                validatorContexts.add(message);
                messagesCluster.put(message.getRule(), validatorContexts);

                clustering.put(message.getMessage(), messagesCluster);
            }
        }

        // build a second cluster by message level
        Map<MessageLevel, Mi25ClusteredContext> clusteringByMessageLevel = new HashMap<MessageLevel, Mi25ClusteredContext>(clustering.size());
        for (Map.Entry<String, Map<Rule, Set<ValidatorMessage>>> entry : clustering.entrySet()){

            String message = entry.getKey();
            Map<Rule, Set<ValidatorMessage>> ruleCluster = entry.getValue();

            // cluster by message level and create proper validatorMessage
            for (Map.Entry<Rule, Set<ValidatorMessage>> ruleEntry : ruleCluster.entrySet()){
                clusteringByMessageLevel.clear();

                Rule rule = ruleEntry.getKey();
                Set<ValidatorMessage> validatorMessages = ruleEntry.getValue();

                for (ValidatorMessage validatorMessage : validatorMessages){

                    if (clusteringByMessageLevel.containsKey(validatorMessage.getLevel())){
                        Mi25ClusteredContext clusteredContext = clusteringByMessageLevel.get(validatorMessage.getLevel());

                        clusteredContext.getContexts().add(validatorMessage.getContext());
                    }
                    else{
                        Mi25ClusteredContext clusteredContext = new Mi25ClusteredContext();

                        clusteredContext.getContexts().add(validatorMessage.getContext());

                        clusteringByMessageLevel.put(validatorMessage.getLevel(), clusteredContext);
                    }
                }

                for (Map.Entry<MessageLevel, Mi25ClusteredContext> levelEntry : clusteringByMessageLevel.entrySet()){

                    ValidatorMessage validatorMessage = new ValidatorMessage(message, levelEntry.getKey(), levelEntry.getValue(), rule);
                    clusteredMessages.add(validatorMessage);

                }
            }
        }

        return clusteredMessages;
    }
}
