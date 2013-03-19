package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.Collection;
import java.util.List;
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
     * Checks if the pubmed ids in a collection of pubmed references are valid. Add new ValidatorMessage to messages if not.
     * @param pubmeds
     * @param messages
     * @param context
     * @param experimentRule
     */
    public static void checkPubmedId(Collection<DbReference> pubmeds, List<ValidatorMessage> messages, Mi25Context context, Mi25ExperimentRule experimentRule){

        int emptyPmids = 0;
        for ( DbReference pubmed : pubmeds ) {
            final String pmid = pubmed.getId();
            if( pmid != null) {
                if (pmid.trim().length() > 0){
                    try {
                        Integer.parseInt( pmid );
                    } catch ( NumberFormatException e ) {
                        messages.add( new ValidatorMessage( "You have specified an invalid pubmed id: '" + pmid + "'",
                                MessageLevel.WARN,
                                context,
                                experimentRule) );
                    }
                }

            } else {
                emptyPmids++;
            }
        }

        if( emptyPmids > 0 ) {
            messages.add( new ValidatorMessage( "You have specified "+ emptyPmids +" pubmed identifier with an empty value.",
                    MessageLevel.WARN,
                    context,
                    experimentRule) );
        }
    }

    /**
     * Checks if the imex ids in a collection of imex-primary references are valid. Add new ValidatorMessage to messages if not.
     * @param imexPrimaryReferences
     * @param messages
     * @param context
     * @param experimentRule
     */
    public static void checkImexId(Collection<DbReference> imexPrimaryReferences, List<ValidatorMessage> messages, Mi25Context context, ObjectRule experimentRule){

        // If there is a reference type set to 'imex-primary'
        if (!imexPrimaryReferences.isEmpty()){
            for ( DbReference imex  : imexPrimaryReferences ) {
                final String imexId = imex.getId();
                if( imexId != null) {
                    if (imexId.trim().length() > 0 ){
                        if (!IMEx_ID.matcher(imexId).matches()){
                            messages.add( new ValidatorMessage( "The IMEx ID " + imexId + " is not a valid IMEX id (IM-xxx).",
                                    MessageLevel.ERROR,
                                    context,
                                    experimentRule ) );
                        }
                    }

                }
                else {
                    messages.add( new ValidatorMessage( "The IMEx ID " + imexId + " is not a valid IMEX id (IM-xxx).",
                            MessageLevel.ERROR,
                            context,
                            experimentRule ) );
                }
            }
        }
    }

    public static void checkImexInteractionId(Collection<DbReference> imexPrimaryReferences, List<ValidatorMessage> messages, Mi25Context context, ObjectRule experimentRule){

        // If there is a reference type set to 'imex-primary'
        if (!imexPrimaryReferences.isEmpty()){
            for ( DbReference imex  : imexPrimaryReferences ) {
                final String imexId = imex.getId();
                if( imexId != null) {
                    if (imexId.trim().length() > 0 ){
                        if (!IMEx_INTERACTION_ID.matcher(imexId).matches()){
                            messages.add( new ValidatorMessage( "The IMEx ID " + imexId + " is not a valid IMEX id (IM-xxx-xx).",
                                    MessageLevel.ERROR,
                                    context,
                                    experimentRule ) );
                        }
                    }

                }
                else {
                    messages.add( new ValidatorMessage( "The IMEx ID " + imexId + " is not a valid IMEX id (IM-xxx-xx).",
                            MessageLevel.ERROR,
                            context,
                            experimentRule ) );
                }
            }
        }
    }
}