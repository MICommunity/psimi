package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25ValidatorConfig;
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
        String fileName = Mi25ValidatorConfig.getFeatureType2FeatureRange();
        
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
        setName( "Feature type and feature range status check" );
        setDescription( "Checks that each interaction doesn't have any conflicts between the feature type of a participant and its feature range status.");
        addTip( "Search the possible terms for feature type and feature range status on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file resources/featureType2FeatureRangeStatus.tsv for the possible dependencies feature type - feature range status" );

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

        final Collection<Participant> participants = interaction.getParticipants();

        for ( Participant participant : participants) {

            // participants of the interaction
            Collection<Feature> features = participant.getFeatures();

            for (Feature feature : features){

                // build a context in case of error
                Mi25Context context = new Mi25Context();
                context.setInteractionId( interaction.getId() );
                context.setParticipantId( participant.getId());
                context.setFeatureId( feature.getId());

                if (feature.hasFeatureType()){
                    Collection<Range> featureRange = feature.getRanges();                    
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
