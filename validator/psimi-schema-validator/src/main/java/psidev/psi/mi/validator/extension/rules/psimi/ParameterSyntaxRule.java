package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;
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
import java.util.List;

/**
 * Rule to check syntax of parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/03/13</pre>
 */

public class ParameterSyntaxRule extends AbstractMIRule<Parameter> {

    public ParameterSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager, Parameter.class);
        setName( "Parameter's syntax check" );

        setDescription( "Check that each parameter (interaction's parameters and participant's parameters) have a parameter type and a parameter factor." +
                "If the parameter's type/unit MI identifier is provided, check that it is a valid MI identifier" );
    }

    @Override
    public Collection<ValidatorMessage> check(Parameter parameter) throws ValidatorException {
        if (parameter != null){
            // list of messages to return
            List<ValidatorMessage> messages = Collections.EMPTY_LIST;
            final OntologyAccess access = ontologyManager.getOntologyAccess("MI");

            CvTerm type = parameter.getType();
            CvTerm unit = parameter.getUnit();

            if (type == null ||
                    PsiXml25Utils.UNSPECIFIED.equals(type.getShortName()) ||
                    MitabUtils.UNKNOWN_TYPE.equals(type.getShortName())){
                Mi25Context xrefContext = RuleUtils.buildContext(parameter, "parameter");
                messages = new ArrayList<ValidatorMessage>();
                messages.add( new ValidatorMessage( "Parameters must have a valid parameter type.'",
                        MessageLevel.ERROR,
                        xrefContext,
                        this ) );
            }
            else if (type != null && type.getMIIdentifier() != null){
                final OntologyTermI dbTerm = access.getTermForAccession(type.getMIIdentifier());

                if (dbTerm == null){
                    Mi25Context context = RuleUtils.buildContext(parameter, "parameter");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The parameter type MI identifier "+type.getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for parameter types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0640&termName=parameter%20type",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:0640".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        Mi25Context context = RuleUtils.buildContext(parameter, "parameter");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The MI identifier "+type.getMIIdentifier()+" is not a valid MI identifier for parameter types. The valid MI terms for parameter types are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0640&termName=parameter%20type",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

            if (unit != null && unit.getMIIdentifier() != null){
                final OntologyTermI dbTerm = access.getTermForAccession(unit.getMIIdentifier());

                if (dbTerm == null){
                    Mi25Context context = RuleUtils.buildContext(parameter, "parameter");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The parameter unit MI identifier "+unit.getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for parameter units are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0647&termName=parameter%20unit",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:0647".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        Mi25Context context = RuleUtils.buildContext(parameter, "parameter");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The MI identifier "+unit.getMIIdentifier()+" is not a valid MI identifier for parameter units. The valid MI terms for parameter units are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0647&termName=parameter%20unit",
                                MessageLevel.ERROR,
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
        return "R19";
    }
}
