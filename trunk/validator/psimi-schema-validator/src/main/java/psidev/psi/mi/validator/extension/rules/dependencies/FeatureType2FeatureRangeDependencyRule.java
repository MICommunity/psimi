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
 * Rule that allows to check whether the feature type specified matches the feature range status.
 *
 * Rule Id = 14. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureRangeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FeatureType2FeatureRangeDependencyRule extends Mi25InteractionRule {

        private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private static DependencyMapping mapping;

    public FeatureType2FeatureRangeDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
            try {
                // TODO : the resource should be a final private static or should be put as argument of the constructor
                String resource = FeatureType2FeatureRangeDependencyRule.class
                .getResource( "/FeatureType2FeatureRangeStatus.tsv" ).getFile();

                mapping = new DependencyMapping();
                mapping.buildMappingFromFile( mi, resource );

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ValidatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            // describe the rule.
        setName( "Dependency between feature type and feature range status" );
        setDescription( "Checks that each interaction respects the dependencies feature type - feature range status " +
                "stored in FeatureType2FeatureRangeStatus.tsv." );
        addTip( "Search the possible terms for feature type and feature range status on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );

    }

    /**
     * For each participants of the interaction, collect all respective feature types and feature range status and
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
        final Collection<Participant> participants = interaction.getParticipants();

        for ( Participant participant : participants) {

            context.setParticipantId( participant.getId());

            // participants of the interaction
            Collection<Feature> features = participant.getFeatures();

            for (Feature feature : features){
                context.setFeatureId( feature.getId());
                Collection<Range> featureRange = feature.getRanges();

                if (feature.hasFeatureType()){
                    FeatureType featureType = feature.getFeatureType();

                    for (Range r : featureRange){
                        RangeStatus startStatus =  r.getStartStatus();
                        RangeStatus endStatus =  r.getEndStatus();

                        messages.addAll( mapping.check( featureType, startStatus, context, this ) );
                        messages.addAll( mapping.check( featureType, endStatus, context, this ) );
                    }
                }
            }

        } // features

        return messages;
    }
}
