package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
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

    private static DependencyMapping mapping;

    /**
     *
     * @param ontologyMaganer
     */
    public FeatureType2FeatureDetectionMethodDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        Mi25Ontology ontology = new Mi25Ontology(mi);
            try {
                // TODO : the resource should be a final private static or should be put as argument of the constructor
                URL resource = FeatureType2FeatureDetectionMethodDependencyRule.class
                .getResource( "/FeatureType2FeatureDetectionMethod.tsv" );

                mapping = new DependencyMapping();

                mapping.buildMappingFromFile( ontology, mi, resource );

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ValidatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            // describe the rule.
        setName( "Dependency between feature type and feature detection method" );
        setDescription( "Checks that each interaction respects the dependencies feature type - feature detection method " +
                "stored in FeatureType2FeatureDetectionMethod.tsv." );
        addTip( "Search the possible terms for feature type and feature detection method on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );

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

        // TODO MOVE IT
        Mi25Context context = new Mi25Context();

        context.setInteractionId( interaction.getId() );
        // The participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();
        // The experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();

//        experiments.iterator().next().getFeatureDetectionMethod();

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
                        // TODO look at feature.getExperimentRefs()
                        //Collection<ExperimentDescription> experiments = collectExperiment( interaction, feature.getExperimentRefs() );
                        for (ExperimentDescription experiment : experiments){
                            if (experiment.hasFeatureDetectionMethod()){
                                hasFeatureDetectionMethod = true;

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

    // TODO move somewhere you can use it statically
    private Collection<ExperimentDescription> collectExperiment(Interaction interaction, Collection<ExperimentRef> experimentRefs) {
        ArrayList<ExperimentDescription> collectedExps = new ArrayList<ExperimentDescription>();

        if( experimentRefs != null ) {
            for (ExperimentRef ref : experimentRefs) {
                for (ExperimentDescription ed : interaction.getExperiments()) {
                    if( ed.getId() == ref.getRef() ) {
                        collectedExps.add( ed );
                    }
                }
            }
        } else {
            collectedExps.addAll( interaction.getExperiments() );
        }

        return collectedExps;
    }
}
