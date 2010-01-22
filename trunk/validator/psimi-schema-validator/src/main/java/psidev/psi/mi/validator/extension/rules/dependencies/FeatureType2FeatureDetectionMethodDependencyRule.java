package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Rule that allows to check whether the feature type specified matches the feature detection method.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureDetectionMethodDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FeatureType2FeatureDetectionMethodDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private static DependencyMapping mapping;

    /**
     *
     * @param ontologyMaganer
     */
    public FeatureType2FeatureDetectionMethodDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
            try {
                // TODO : the resource should be a final private static or should be put as argument of the constructor
                String resource = FeatureType2FeatureDetectionMethodDependencyRule.class
                .getResource( "/FeatureType2FeatureDetectionMethod.tsv" ).getFile();

                mapping = new DependencyMapping();

                mapping.buildMappingFromFile( mi, resource );

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ValidatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            // describe the rule.
        setName( "Dependency between feature type and feature detection method" );
//        addTip( "" );
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

        // build a context in case of error
        Mi25Context context = new Mi25Context();

        context.setInteractionId( interaction.getId() );
        // The participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();
        // The experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();

        for ( Participant participant : participants) {

            context.setParticipantId( participant.getId());

            // Features of a participant
            Collection<Feature> features = participant.getFeatures();

            for (Feature feature : features){
                context.setFeatureId( feature.getId());
                FeatureDetectionMethod method = null;
                boolean hasFeatureDetectionMethod = false;

                if (feature.hasFeatureType()){
                    FeatureType featureType = feature.getFeatureType();

                    if (feature.hasFeatureDetectionMethod()){
                        hasFeatureDetectionMethod = true;

                        method = feature.getFeatureDetectionMethod();
                        messages.addAll( mapping.check( featureType, method, context, this ) );
                    }
                    else {
                        for (ExperimentDescription experiment : experiments){
                            if (experiment.hasFeatureDetectionMethod()){
                                hasFeatureDetectionMethod = true;

                                method = experiment.getFeatureDetectionMethod();
                                messages.addAll( mapping.check( featureType, method, context, this ) );
                            }
                        }
                    }

                    if (!hasFeatureDetectionMethod){                        
                        messages.addAll( mapping.check( featureType, method, context, this ) );
                    }
                }
            }

        } // features

        return messages;
    }
}
