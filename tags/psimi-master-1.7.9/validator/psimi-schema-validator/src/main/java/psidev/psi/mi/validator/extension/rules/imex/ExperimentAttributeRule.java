package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
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

    private List<String> acceptedAttributes;

    public ExperimentAttributeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        acceptedAttributes = new ArrayList<String>();
        initializeAcceptedAttributes();

        // describe the rule.
        setName( "Experiment Annotations Check" );
        setDescription( "Checks that each annotation has a name associated with a valid PSI-MI term and/or is a valid annotation for an experiment according to the IMEx curation rules." );
        addTip( "Existing experiment's attribute terms can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0590&termName=attribute%20name." );
    }

    private void initializeAcceptedAttributes(){
        acceptedAttributes.add(RuleUtils.URL);
        acceptedAttributes.add(RuleUtils.LIBRARY_USED);
        acceptedAttributes.add(RuleUtils.EXP_MODIFICATION);
        acceptedAttributes.add(RuleUtils.DATASET);
        acceptedAttributes.add(RuleUtils.DATA_PROCESSING);
        acceptedAttributes.add(RuleUtils.COMMENT);
        acceptedAttributes.add(RuleUtils.CAUTION);
        acceptedAttributes.add(RuleUtils.AUTHOR_CONFIDENCE_MI_REF);
        acceptedAttributes.add(RuleUtils.ANTIBODIES);
        acceptedAttributes.add(RuleUtils.AUTHOR_LIST);
        acceptedAttributes.add(RuleUtils.JOURNAL);
        acceptedAttributes.add(RuleUtils.PUBLICATION_YEAR);
        acceptedAttributes.add(RuleUtils.AUTHOR_SUBMITTED);
        acceptedAttributes.add(RuleUtils.CONTACT_EMAIL_MI_REF);
        acceptedAttributes.add(RuleUtils.CURATION_REQUEST);
        acceptedAttributes.add(RuleUtils.IMEX_CURATION);
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

            for (Attribute attribute : attributes){
                if (!attribute.hasNameAc()){
                    if (attribute.getName() != null && !attribute.getName().equalsIgnoreCase(COPYRIGHT)){
                        messages.add( new ValidatorMessage( "The attribute " + attribute.getName() + " does not have any nameAc. " +
                                "All experiment's attributes should have a nameAc pointing to a valid PSI MI term (Excepted '"+COPYRIGHT+"' attribute)." +
                                " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }
                }
                else {

                    if (!acceptedAttributes.contains(attribute.getNameAc())){
                        messages.add( new ValidatorMessage( "The attribute " + attribute.getName() + " is not a child of 'experiment attribute name' (MI:0665). " +
                                " All experiment's attributes should be children of 'experiment attribute name' (MI:0665)",
                                MessageLevel.WARN,
                                context,
                                this ) );
                    }
                }
            }

        }

        return messages;
    }
}
