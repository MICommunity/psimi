package psidev.psi.mi.validator.extension.rules.imex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that each feature has a valid feature type.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class FeatureTypeRule extends ObjectRule<Feature> {

    private static final Log log = LogFactory.getLog(FeatureTypeRule.class);

    public FeatureTypeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Participant's feature Type Check");
        setDescription("Checks that each participant's feature has a feature type with " +
                "a valid PSI MI cross reference.");
        addTip( "See http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0116&termName=feature%20type for the existing feature types" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Feature){
            return true;
        }

        return false;
    }

    @Override
    public Collection<ValidatorMessage> check(Feature feature) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();
        context.setFeatureId(feature.getId());

        if (feature.hasFeatureType()){

            Mi25Ontology ontology = getMiOntology();

            OntologyTermI featureTypemi = ontology.search(RuleUtils.FEATURE_TYPE);

            if (featureTypemi == null){
                log.error("The term "+ RuleUtils.FEATURE_TYPE +" for 'feature type' is not recognized so this rule will be ignored.");
            }
            else {
                if (feature.getFeatureType().getXref() != null){
                    Xref ref = feature.getFeatureType().getXref();

                    Collection<DbReference> psiRef = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.PSI_MI_REF, RuleUtils.PSI_MI, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY, messages, context, this);

                    if (psiRef.isEmpty()){
                        messages.add( new ValidatorMessage( "The feature type " + (feature.getFeatureType().getNames() != null ? feature.getFeatureType().getNames().getShortLabel() : "") + " has "+ref.getAllDbReferences().size()+" cross references but none of them is a PSI-MI cross reference with a qualifier 'identity' and it is required by IMEx.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                    else if (psiRef.size() > 1) {
                        messages.add( new ValidatorMessage( "The feature type " + (feature.getFeatureType().getNames() != null ? feature.getFeatureType().getNames().getShortLabel() : "") + " has "+psiRef.size()+" PSI-MI cross reference with a qualifier 'identity' and only one is accepted.",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                    else{
                        DbReference psimi = psiRef.iterator().next();

                        OntologyTermI currentType = ontology.search(psimi.getId());

                        if (currentType == null){
                            messages.add( new ValidatorMessage( "The feature type " + (feature.getFeatureType().getNames() != null ? feature.getFeatureType().getNames().getShortLabel() : "") + "("+psimi.getId()+") is not recognized.",
                                    MessageLevel.ERROR,
                                    context,
                                    this ) );
                        }
                        else if(!ontology.isChildOf(featureTypemi, currentType)){
                            messages.add( new ValidatorMessage( "The feature type " + (feature.getFeatureType().getNames() != null ? feature.getFeatureType().getNames().getShortLabel() : "") + "("+psimi.getId()+") is not a valid feature type.",
                                    MessageLevel.ERROR,
                                    context,
                                    this ) );
                        }
                    }
                }
                else {
                    messages.add( new ValidatorMessage( "The feature type " + (feature.getFeatureType().getNames() != null ? feature.getFeatureType().getNames().getShortLabel() : "") + " does not have any cross references. A PSI-MI cross reference with qualifier 'identity' is required.",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
            }

        }
        else {
            messages.add( new ValidatorMessage( "The feature does not have a feature type. It is required by IMEx.'",
                    MessageLevel.ERROR,
                    context,
                    this ) );
        }

        return messages;
    }

    /**
     * Return the MI ontology
     *
     * @return the ontology
     * @throws psidev.psi.tools.validator.ValidatorException
     */
    public Mi25Ontology getMiOntology() throws ValidatorException {
        return new Mi25Ontology( ontologyManager.getOntologyAccess( "MI" ) );
    }
}
