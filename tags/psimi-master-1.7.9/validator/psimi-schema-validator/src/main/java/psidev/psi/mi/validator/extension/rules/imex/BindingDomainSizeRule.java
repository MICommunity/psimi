package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.FeatureUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.Range;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This rule checks that each binding domain contains more than three amino acids
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class BindingDomainSizeRule extends ObjectRule<Feature> {
    public BindingDomainSizeRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        // describe the rule.
        setName("Binding domain Check");
        setDescription("Checks that each binding domain contains more than three amino acids. ");
        addTip("when the feature type is any children of binding site (MI:0117), the range should contain at least three amino acids, otherwise it is considered as a mutant.");
        addTip( "Mutant accessions in the PSI-MI ontology can be found at http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A0118&termName=mutation");
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Feature){
            return true;
        }

        return false;
    }

    private long getMaxRangeLength(Range range, boolean isStartDefined, boolean isEndDefined){
        long startPos = 0;
        long endPos = 0;

        if (!isStartDefined){
            startPos = 1;
        }
        else {

            if (range.getBeginInterval() != null){
                startPos = range.getBeginInterval().getBegin();
            }
            else if (range.getBegin() != null){
                startPos = range.getBegin().getPosition();
            }
        }

        if (isEndDefined) {
            if (range.getEndInterval() != null){
                endPos = range.getEndInterval().getEnd();
            }
            else if (range.getEnd() != null){
                endPos = range.getEnd().getPosition();
            }
        }

        if (FeatureUtils.isLessThan(range.getStartStatus())){
            startPos =  1;
        }
        else if (FeatureUtils.isMoreThan(range.getStartStatus())){
            startPos =  Math.min(startPos + 1, endPos);
        }

        if (FeatureUtils.isLessThan(range.getEndStatus())){
            endPos =  Math.max(endPos - 1, startPos);
        }
        else if (FeatureUtils.isMoreThan(range.getStartStatus())){
            endPos =  endPos + 1;
        }
        return endPos - startPos + 1;
    }

    private long getMinRangeLength(Range range, boolean isStartDefined, boolean isEndDefined){

        long startPos = 0;
        long endPos = 0;

        if (isStartDefined){

            if (range.getBeginInterval() != null){
                startPos = range.getBeginInterval().getEnd();
            }
            else if (range.getBegin() != null){
                startPos = range.getBegin().getPosition();
            }
        }

        if (isEndDefined) {
            if (range.getEndInterval() != null){
                endPos = range.getEndInterval().getBegin();
            }
            else if (range.getEnd() != null){
                endPos = range.getEnd().getPosition();
            }
        }

        if (FeatureUtils.isLessThan(range.getStartStatus())){
            startPos =  Math.max(1, startPos - 1);
        }
        else if (FeatureUtils.isMoreThan(range.getStartStatus())){
            startPos =  Math.min(startPos + 1, endPos);
        }

        if (FeatureUtils.isLessThan(range.getEndStatus())){
            endPos =  Math.max(endPos - 1, startPos);
        }
        else if (FeatureUtils.isMoreThan(range.getStartStatus())){
            endPos =  endPos + 1;
        }

        if (!isStartDefined){
            startPos = endPos;
        }
        else if (!isEndDefined){
            endPos = startPos;
        }

        return endPos - startPos + 1;
    }

    @Override
    public Collection<ValidatorMessage> check(Feature feature) throws ValidatorException {
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();
        context.setFeatureId(feature.getId());

        if (feature.hasFeatureType()){
            if (RuleUtils.isBindingSite(ontologyManager, feature)){
                Collection<Range> ranges = feature.getRanges();

                int minSize = 0;
                int maxSize = 0;
                boolean isFeatureSiteDefined = false;

                for (Range range : ranges){
                    if (range.getStartStatus() == null || range.getEndStatus() == null){
                        break;
                    }
                    else {
                        long startPos = 0;
                        long endPos = 0;

                        boolean isStartDefined = true;
                        boolean isEndDefined = true;

                        if (FeatureUtils.isUndetermined(range.getStartStatus()) || FeatureUtils.isNTerminalRegion(range.getStartStatus()) || FeatureUtils.isCTerminalRegion(range.getStartStatus())){
                            isStartDefined = false;
                        }

                        if (FeatureUtils.isUndetermined(range.getEndStatus()) || FeatureUtils.isNTerminalRegion(range.getEndStatus()) || FeatureUtils.isCTerminalRegion(range.getEndStatus())){
                            isEndDefined = false;
                        }

                        isFeatureSiteDefined = isStartDefined && isEndDefined;

                        if (!isFeatureSiteDefined){
                            break;
                        }

                        minSize += getMinRangeLength(range, isStartDefined, isEndDefined);
                        maxSize += getMaxRangeLength(range, isStartDefined, isEndDefined);
                    }
                }

                if (minSize < 3 && maxSize < 3 && isFeatureSiteDefined){
                    messages.add( new ValidatorMessage( "The binding site does not contain more than three amino acids. For one or two amino acids, the feature type should be any children of mutation instead of binding site.'",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else if (minSize < 3 && maxSize > 3 && isFeatureSiteDefined){
                    messages.add( new ValidatorMessage( "The minimal size of this binding site does not contain more than three amino acids.'",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
        }

        return messages;
    }
}
