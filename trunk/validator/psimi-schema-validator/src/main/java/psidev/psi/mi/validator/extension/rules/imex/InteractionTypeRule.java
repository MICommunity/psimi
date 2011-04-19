package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.InteractionType;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that each interaction has at least one interaction type and all the interaction types have valid psi-MI cross references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class InteractionTypeRule extends Mi25InteractionRule{

    private static final Log log = LogFactory.getLog(InteractionTypeRule.class);

    public InteractionTypeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Interaction Type Check");
        setDescription("Checks that each interaction has at least one interaction type and all the interactions types should have " +
                "a valid PSI MI cross reference.");
        addTip( "See http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0190&termName=interaction%20type for the existing interaction types" );
    }

    @Override
    public Collection<ValidatorMessage> check(Interaction interaction) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();
        context.setInteractionId(interaction.getId());

        if (interaction.hasInteractionTypes()){
            Collection<InteractionType> interactionTypes = interaction.getInteractionTypes();

            Mi25Ontology ontology = getMi25Ontology();

            OntologyTermI interactionTypeMi = ontology.search(RuleUtils.INTERACTION_TYPE);

            if (interactionTypeMi == null){
                log.error("The term "+ RuleUtils.INTERACTION_TYPE +" for 'interaction type' is not recognized so this rule will be ignored.");
            }
            else {
                for (InteractionType type : interactionTypes){
                    if (type.getXref() != null){
                        Xref ref = type.getXref();

                        Collection<DbReference> psiRef = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.PSI_MI_REF, RuleUtils.PSI_MI, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY, messages, context, this);

                        if (psiRef.isEmpty()){
                            messages.add( new ValidatorMessage( "The interaction type " + (type.getNames() != null ? type.getNames().getShortLabel() : "") + " has "+ref.getAllDbReferences().size()+" cross references but none of them is a PSI-MI cross reference with a qualifier 'identity' and it is required by IMEx.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                        }
                        else if (psiRef.size() > 1) {
                            messages.add( new ValidatorMessage( "The interaction type " + (type.getNames() != null ? type.getNames().getShortLabel() : "") + " has "+psiRef.size()+" PSI-MI cross reference with a qualifier 'identity' and only one is accepted.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                        }
                        else{
                           DbReference psimi = psiRef.iterator().next();

                           OntologyTermI currentType = ontology.search(psimi.getId());

                           if (currentType == null){
                               messages.add( new ValidatorMessage( "The interaction type " + (type.getNames() != null ? type.getNames().getShortLabel() : "") + "("+psimi.getId()+") is not recognized.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                           }
                            else if(!ontology.isChildOf(interactionTypeMi, currentType)){
                                messages.add( new ValidatorMessage( "The interaction type " + (type.getNames() != null ? type.getNames().getShortLabel() : "") + "("+psimi.getId()+") is not a valid interaction type.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                           }
                        }
                    }
                    else {
                        messages.add( new ValidatorMessage( "The interaction type " + (type.getNames() != null ? type.getNames().getShortLabel() : "") + " does not have any cross references. A PSI-MI cross reference with qualifier 'identity' is required.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

        }
        else {
            messages.add( new ValidatorMessage( "The interaction does not have any interaction types. At least one interaction type is required by IMEx.'",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }
}
