package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Rule that checks that each attribute has a valid attribute ac (psi-mi number)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/11</pre>
 */

public class ExperimentAttributeRule extends Mi25ExperimentRule{

    private static final String COPYRIGHT = "Copyright";

    private static final Log log = LogFactory.getLog(ExperimentAttributeRule.class);

    public ExperimentAttributeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName( "Experiment Annotations Check" );
        setDescription( "Checks that each annotation has a name associated with a valid PSI-MI term and/or is a valid annotation for an experiment." );
        addTip( "Existing experiment's attribute terms can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0590&termName=attribute%20name." );
    }

    @Override
    public Collection<ValidatorMessage> check(ExperimentDescription experiment) throws ValidatorException {
        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        if (experiment.hasAttributes()){
            Collection<Attribute> attributes = experiment.getAttributes();
            Mi25Context context = new Mi25Context();
            context.setExperimentId(experiment.getId());

            final Mi25Ontology ontology = getMiOntology();

            OntologyTermI parent = ontology.search(RuleUtils.EXPERIMENT_ATTRIBUTE_MI_REF);

            if (parent != null){
                for (Attribute attribute : attributes){
                    if (!attribute.hasNameAc()){
                        if (attribute.getName() != null && !attribute.getName().equalsIgnoreCase(COPYRIGHT)){
                            messages.add( new ValidatorMessage( "The attribute " + attribute.getName() + " does not have any MI number attached to it (using nameAc attribute). " +
                                    "All experiment's attributes should have a nameAc pointing to a valid PSI MI term (Excepted '"+COPYRIGHT+"' attribute)." +
                                    " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                    }
                    else {
                         OntologyTermI nameAc = ontology.search(attribute.getNameAc());

                        if (nameAc == null){
                            messages.add( new ValidatorMessage( "The attribute " + attribute.getName() + " is not a valid PSI-MI term. " +
                                    " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                        else if (!ontology.isChildOf(parent, nameAc)){
                             messages.add( new ValidatorMessage( "The attribute " + attribute.getName() + " is not a child of 'experiment attribute name' (MI:0665). " +
                                    " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                    }
                }
            }
            else{
                log.error("No MI term has been found for 'experiment attribute name' " + RuleUtils.EXPERIMENT_ATTRIBUTE_MI_REF + " so the current rule will be skipped. ");
            }

        }

        return messages;
    }
}
