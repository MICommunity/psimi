package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Contains some useful methods for publication id checking.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class PublicationRuleUtils {

    private static Pattern IMEx_ID = Pattern.compile( "IM-[0-9]+" );
    private static Pattern IMEx_INTERACTION_ID = Pattern.compile( "IM-[0-9]+-[0-9]+" );

    /**
     * Checks if the imex ids in a collection of imex-primary references are valid. Add new ValidatorMessage to messages if not.
     * @param imexPrimaryReference
     * @param pub
     * @param experimentRule
     */
    public static Collection<ValidatorMessage> checkImexId(String imexPrimaryReference, Publication pub, ObjectRule experimentRule){

        // If there is a reference type set to 'imex-primary'
        if (imexPrimaryReference != null){
            if (imexPrimaryReference.trim().length() > 0 ){
                if (!IMEx_ID.matcher(imexPrimaryReference).matches()){
                    Mi25Context context = RuleUtils.buildContext(pub, "publication");

                    return Collections.singletonList(new ValidatorMessage("The IMEx ID " + imexPrimaryReference + " is not a valid IMEX id (IM-xxx).",
                            MessageLevel.ERROR,
                            context,
                            experimentRule));
                }
            }
            else {
                Mi25Context context = RuleUtils.buildContext(pub, "publication");

                return Collections.singletonList( new ValidatorMessage( "The IMEx ID " + imexPrimaryReference + " is not a valid IMEX id (IM-xxx).",
                        MessageLevel.ERROR,
                        context,
                        experimentRule ) );
            }
        }
        return Collections.EMPTY_LIST;
    }

    public static Collection<ValidatorMessage> checkImexInteractionId(String imexPrimaryReference, InteractionEvidence interaction, ObjectRule experimentRule){

        // If there is a reference type set to 'imex-primary'
        if (!imexPrimaryReference.isEmpty()){
            if (imexPrimaryReference.trim().length() > 0 ){
                if (!IMEx_INTERACTION_ID.matcher(imexPrimaryReference).matches()){
                    Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

                    return Collections.singletonList( new ValidatorMessage( "The IMEx ID " + imexPrimaryReference + " is not a valid IMEX id (IM-xxx-xx).",
                            MessageLevel.ERROR,
                            context,
                            experimentRule ) );
                }
            }
            else {
                Mi25Context context = RuleUtils.buildContext(interaction, "interaction");

                return Collections.singletonList( new ValidatorMessage( "The IMEx ID " + imexPrimaryReference + " is not a valid IMEX id (IM-xxx-xx).",
                        MessageLevel.ERROR,
                        context,
                        experimentRule ) );
            }
        }
        return Collections.EMPTY_LIST;
    }
}