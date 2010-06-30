package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25ValidatorConfig;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the feature type specified matches the feature detection method.
 *
 * Rule Id = 13. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureDetectionMethodDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FeatureType2FeatureDetectionMethodDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private DependencyMapping mapping;

    /**
     *
     * @param ontologyMaganer
     */
    public FeatureType2FeatureDetectionMethodDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getFeatureType2FeatureDetectionMethod();
        
        try {
            URL resource = FeatureType2FeatureDetectionMethodDependencyRule.class
                    .getResource( fileName );

            mapping = new DependencyMapping();

            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Feature type and feature detection method check" );
        setDescription( "Checks that each interaction doesn't have any conflicts between the feature type of a participant and its feature detection method.");
        addTip( "Search the possible terms for feature type and feature detection method on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file resources/featureType2FeatureDetectionMethod.tsv for the possible dependencies feature type - feature detection method" );
    }

    /**
     * For each participants of the interaction, collect all respective feature detection methods and feature types and
     * check if the dependencies are correct.
     *
     * @param interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // The participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();

//        experiments.iterator().next().getFeatureDetectionMethod();

        for ( Participant participant : participants) {

            // Features of a participant
            Collection<Feature> features = participant.getFeatures();

            for (Feature feature : features){

                FeatureDetectionMethod method = null;

                if (feature.hasFeatureType()){
                    FeatureType featureType = feature.getFeatureType();

                    if (feature.hasFeatureDetectionMethod()){
                        Mi25Context context = new Mi25Context();
                        context.setInteractionId( interaction.getId() );
                        context.setParticipantId( participant.getId());
                        context.setFeatureId( feature.getId());

                        method = feature.getFeatureDetectionMethod();
                        messages.addAll( mapping.check( featureType, method, context, this ) );
                    }
                    else {
                        // The experiment refs of the feature
                        final Collection<ExperimentRef> experimentRefs = feature.getExperimentRefs();

                        // The experiments for detecting the interaction
                        Collection<ExperimentDescription> experiments = RuleUtils.collectExperiment(interaction, experimentRefs);

                        //Collection<ExperimentDescription> experiments = collectExperiment( interaction, feature.getExperimentRefs() );
                        for (ExperimentDescription experiment : experiments){

                            Mi25Context context = new Mi25Context();
                            context.setInteractionId( interaction.getId() );
                            context.setParticipantId( participant.getId());
                            context.setFeatureId( feature.getId());
                            context.setExperimentId(experiment.getId());

                            if (experiment.hasFeatureDetectionMethod()){
                                method = experiment.getFeatureDetectionMethod();
                                messages.addAll( mapping.check( featureType, method, context, this ) );
                            }
                        }
                    }
                }
            }

        } // features

        return messages;
    }

}
