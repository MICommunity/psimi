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
 * Rule that allows to check whether the interaction detection method specified matches the biological rule.
 *
 * Rule Id = 12. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2BiologicalRoleDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2BiologicalRoleDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2BiologicalRoleDependencyRule.class );

    private static DependencyMapping mapping;

    public InteractionDetectionMethod2BiologicalRoleDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        Mi25Ontology ontology = new Mi25Ontology(mi);
            try {
                // TODO : the resource should be a final private static or should be put as argument of the constructor

                URL resource = InteractionDetectionMethod2ExperimentRoleDependencyRule.class
                .getResource( "/InteractionDetectionMethod2BiologicalRole.tsv" );
                mapping = new DependencyMapping();

                mapping.buildMappingFromFile( ontology, mi, resource );

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ValidatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            // describe the rule.
        setName( "Dependency between interaction detection method and Biological rule" );
        setDescription( "Checks that each interaction respects the dependencies interaction detection method - participant biological role " +
                "stored in InteractionDetectionMethod2BiologicalRole.tsv." );
        addTip( "Search the possible terms for interaction detection method and biological role on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants and their biological roles and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = new Mi25Context();

        context.setInteractionId( interaction.getId() );
        // experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();
        // participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();

        for ( ExperimentDescription experiment : experiments ) {

            context.setExperimentId( experiment.getId());
            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();

            for ( Participant p : participants ) {

                if (p.hasBiologicalRole()){
                    BiologicalRole biolRole = p.getBiologicalRole();

                    messages.addAll( mapping.check( method, biolRole, context, this ) );
                }
            }

        } // experiments

        return messages;
    }

}
