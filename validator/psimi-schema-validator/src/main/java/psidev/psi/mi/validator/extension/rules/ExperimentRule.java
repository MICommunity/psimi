package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.InputStream;
import java.util.Collection;

/**
 * Abstract base class for an Interaction Detection Method check and a Participant Detection Method check.
 *
 * @author Marine Dumousseau
 */
public abstract class ExperimentRule extends Mi25ExperimentRule {

    public ExperimentRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );
    }

    /**
     * Make sure that an experiment has a valid Interaction detection method and/or a valid Participant Detection Method.
     *
     * @param experiment an experiment to check on.
     * @return a collection of validator messages.
     */
    public abstract Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException ;

    protected Mi25Ontology getOntologyFromAccession(String id) throws Exception {

        final String ontoConfig = "config/ontologies.xml";
        InputStream is = OntologyManager.class.getClassLoader().getResourceAsStream( ontoConfig );
        if (is != null){
            OntologyManager om = new OntologyManager();
            om.loadOntologies(is);
            OntologyAccess miAccess = om.getOntologyAccess( "MI" );

            if (miAccess != null){
                return new Mi25Ontology( miAccess );
            }
        }
        is.close();
        return null;
    }
}
