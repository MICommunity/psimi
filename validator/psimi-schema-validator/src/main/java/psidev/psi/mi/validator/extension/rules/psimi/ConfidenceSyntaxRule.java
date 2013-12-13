package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;
import psidev.psi.mi.validator.extension.MiContext;
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
import java.util.List;

/**
 * This rule will check the syntax of a confidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class ConfidenceSyntaxRule extends AbstractMIRule<Confidence> {

    public ConfidenceSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager, Confidence.class);
        setName( "Interaction and Participant's Confidence syntax check" );

        setDescription( "Check that each interaction confidence and participant confidence has a confidence type and a confidence value." +
                "If a MI term is provided for the confidence type, check that is a valid MI term" );
    }

    @Override
    public Collection<ValidatorMessage> check(Confidence confidence) throws ValidatorException {
        if (confidence != null){
            // list of messages to return
            List<ValidatorMessage> messages = Collections.EMPTY_LIST;
            final OntologyAccess access = ontologyManager.getOntologyAccess("MI");

            CvTerm type = confidence.getType();

            if (type == null ||
                    PsiXml25Utils.UNSPECIFIED.equals(type.getShortName()) ||
                    MitabUtils.UNKNOWN_TYPE.equals(type.getShortName())){
                MiContext xrefContext = RuleUtils.buildContext(confidence, "confidence");
                messages = new ArrayList<ValidatorMessage>();
                messages.add( new ValidatorMessage( "Confidences must have a valid confidence type.'",
                        MessageLevel.ERROR,
                        xrefContext,
                        this ) );
            }
            else if (type != null && type.getMIIdentifier() != null){
                final OntologyTermI dbTerm = access.getTermForAccession(type.getMIIdentifier());

                if (dbTerm == null){
                    MiContext context = RuleUtils.buildContext(confidence, "confidence");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The confidence type MI identifier "+type.getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for confidence types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A1064&termName=interaction%20confidence",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:1064".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        MiContext context = RuleUtils.buildContext(confidence, "confidence");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The MI identifier "+type.getMIIdentifier()+" is not a valid MI identifier for confidence types. The valid MI terms for confidence types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A1064&termName=interaction%20confidence",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

            if (confidence.getValue() == null ||
                    confidence.getValue().trim().length() == 0 ||
                    PsiXml25Utils.UNSPECIFIED.equals(confidence.getValue()) ||
                    MitabUtils.UNKNOWN_ID.equals(confidence.getValue())){
                MiContext xrefContext = RuleUtils.buildContext(confidence, "confidence");
                if (messages.isEmpty()){
                    messages = new ArrayList<ValidatorMessage>();
                }
                messages.add( new ValidatorMessage( "Confidences must have a valid and non empty confidence value.'",
                        MessageLevel.ERROR,
                        xrefContext,
                        this ) );
            }

            return messages;
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "R21";
    }
}