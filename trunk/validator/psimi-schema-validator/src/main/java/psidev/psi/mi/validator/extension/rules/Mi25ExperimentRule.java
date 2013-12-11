package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * <b> PSI-MI 2.5 Specific Rule </b>.
 * <p/>
 * That class is only meant to be checking on Experiments. </p>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public abstract class Mi25ExperimentRule extends AbstractMIRule<Experiment> {

    //////////////////
    // Constructors

    public Mi25ExperimentRule( OntologyManager ontologyManager ) {
        super( ontologyManager, Experiment.class );
    }

    ///////////////////////
    // ObjectRule

    public abstract Collection<ValidatorMessage> check( Experiment experiment ) throws ValidatorException;

    ///////////////////////
    // Utility methods

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