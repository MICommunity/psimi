package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the feature type specified matches the feature range status.
 *
 * Rule Id = 14. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureRangeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FeatureType2FeatureRangeDependencyRule extends ObjectRule<FeatureEvidence> {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private DependencyMapping mapping;

    public FeatureType2FeatureRangeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getFeatureType2FeatureRange();

        try {
            URL resource = FeatureType2FeatureRangeDependencyRule.class
                    .getResource( fileName );

            mapping = new DependencyMapping();
            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Dependency Check : Participant's feature type and participant's feature range status" );
        setDescription( "Checks association participant's feature type - participant's feature range status is valid and respects IMEx curation rules.");
        addTip( "Search the possible terms for feature type and feature range status on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/featureType2FeatureRangeStatus.tsv for the possible dependencies feature type - feature range status" );

    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof FeatureEvidence){
            return true;
        }
        return false;
    }

    /**
     * For each participants of the interaction, collect all respective feature types and feature range status and
     * check if the dependencies are correct.
     *
     * @param feature to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( FeatureEvidence feature) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = RuleUtils.buildContext(feature, "feature");

        if (feature.getType() != null){
            Collection<psidev.psi.mi.jami.model.Range> featureRange = feature.getRanges();
            CvTerm featureType = feature.getType();

            for (psidev.psi.mi.jami.model.Range r : featureRange){
                CvTerm startStatus =  r.getStart().getStatus();
                CvTerm endStatus =  r.getEnd().getStatus();

                messages.addAll( mapping.check( featureType, startStatus, context, this ) );
                messages.addAll( mapping.check( featureType, endStatus, context, this ) );
            }
        }

        return messages;
    }

    public String getId() {
        return "R89";
    }
}
