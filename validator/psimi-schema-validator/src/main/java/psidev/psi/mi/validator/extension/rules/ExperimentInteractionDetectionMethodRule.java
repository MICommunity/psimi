package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.InteractionDetectionMethod;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Checks if an experiment have a at least one Interaction Detection Method and that all the interaction detection methods are valid.
 *
 * @author Marine Dumousseau
 */
public class ExperimentInteractionDetectionMethodRule extends ExperimentRule {

    public ExperimentInteractionDetectionMethodRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Interaction Detection Method check" );
        setDescription( "Checks that each experiment has at least one Interaction Detection Method (MI:0001) and that all Interaction Detection Methods are valid." );
    }

    /**
     * Make sure that an experiment has an Interaction Detection Method and that all Interaction Detection Methods are valid.
     *
     * @param experiment an experiment to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( ExperimentDescription experiment ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        int experimentId = experiment.getId();

        Mi25Context context = new Mi25Context();
        context.setExperimentId( experimentId );

        if (experiment.getInteractionDetectionMethod() == null){
             messages.add( new ValidatorMessage( " The experiment " + experimentId + " doesn't have an Interaction Detection Method ( can be any child of MI:0001)",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );
        }
        else {
            InteractionDetectionMethod intMethod = experiment.getInteractionDetectionMethod();
            Xref intMethodXref = intMethod.getXref();
            if (intMethodXref != null){
                Collection<DbReference> allDbRef = intMethodXref.getAllDbReferences();

                if (!allDbRef.isEmpty()){
                    // search for database : db="psi-mi" dbAc="MI:0488"
                    Collection<DbReference> psiMiReferences = RuleUtils.findByDatabase( allDbRef, "MI:0488", "psi-mi" );

                    // There is only one psi-mi database reference for an InteractionDetectionMethod
                    if (psiMiReferences.size() == 1){

                        for ( DbReference reference : psiMiReferences ) {
                            String intDetMethId = reference.getId();
                            try {
                                Mi25Ontology ontology = getOntologyFromAccession(intDetMethId);
                                OntologyTermI interactionDetectionMethodRoot = ontology.search( "MI:0001" );
                                OntologyTermI child = ontology.search( intDetMethId );

                                if ( !ontology.isChildOf(interactionDetectionMethodRoot, child) ) {
                                    messages.add( new ValidatorMessage( " The Interaction Detection Method " + intDetMethId + " of the experiment " + experimentId + " isn't a valid Interaction Detection Method. ( must be any child of MI:0001)",
                                                                    MessageLevel.ERROR,
                                                                    context,
                                                                    this ) );
                                }
                            } catch (Exception e) {
                                messages.add( new ValidatorMessage( " The Interaction Detection Method " + intDetMethId + " of the experiment " + experimentId + " doesn't have a valid PSI-MI id. ( can be any child of MI:0001)",
                                                                    MessageLevel.ERROR,
                                                                    context,
                                                                    this ) );
                            }
                        }
                    }
                    else {
                        messages.add( new ValidatorMessage( " The experiment " + experimentId + " has an Interaction Detection Method with too many psi-mi cross references (only one is possible).",
                                                            MessageLevel.ERROR,
                                                            context,
                                                            this ) );                       
                    }
                }
                else {
                    messages.add( new ValidatorMessage( " The experiment " + experimentId + " has an Interaction Detection Method which doesn't have any psi-mi cross references.",
                                                        MessageLevel.ERROR,
                                                        context,
                                                        this ) );

                }
            }
            else {
                messages.add( new ValidatorMessage( " The experiment " + experimentId + " has an Interaction Detection Method which doesn't have any cross references (at least one psi-mi database reference is mandatory).",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );

                }

        }

        return messages;
    }
}
