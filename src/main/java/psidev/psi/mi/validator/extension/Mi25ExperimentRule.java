package psidev.psi.mi.validator.extension;

import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;

import java.util.Map;
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
public abstract class Mi25ExperimentRule extends ObjectRule<ExperimentDescription> {

    //////////////////
    // Constructors

    public Mi25ExperimentRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
    }

    ///////////////////////
    // ObjectRule

    public boolean canCheck(Object o) {
       return ( o != null && o instanceof ExperimentDescription );
    }

    public abstract Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException;

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