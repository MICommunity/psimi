package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ParticipantIdentificationMethod;
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
 * Checks if an experiment have a Participant identification Method and that all the participant identification methods are valid.
 *
 * @author Marine Dumousseau
 */
public class ExperimentParticipantIdentificationMethodRule extends ExperimentRule {

        public ExperimentParticipantIdentificationMethodRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Experiment Participant Detection Method check" );
        setDescription( "Checks that each experiment has a valid Participant Identification Method (MI:0002) that all the participant identification methods are valid." );
    }

    /**
     * Make sure that an experiment has a valid Participant Identification Method that all the participant identification methods are valid.
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

        if (experiment.getParticipantIdentificationMethod() == null){
             messages.add( new ValidatorMessage( " The experiment " + experimentId + " doesn't have a Participant Identification Method ( can be any child of MI:0002)",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );
        }
        else {
            ParticipantIdentificationMethod partMethod = experiment.getParticipantIdentificationMethod();
            Xref partMethodXref = partMethod.getXref();
            if (partMethodXref != null){
                Collection<DbReference> allDbRef = partMethodXref.getAllDbReferences();

                if (!allDbRef.isEmpty()){
                    // search for database : db="psi-mi" dbAc="MI:0488"
                    Collection<DbReference> psiMiReferences = RuleUtils.findByDatabase( allDbRef, "MI:0488", "psi-mi" );

                    // There is only one psi-mi database reference for a ParticipantDetectionMethod
                    if (psiMiReferences.size() == 1){

                        for ( DbReference reference : psiMiReferences ) {
                            String partDetMethId = reference.getId();
                            try {
                                Mi25Ontology ontology = getOntologyFromAccession(partDetMethId);
                                OntologyTermI interactionDetectionMethodRoot = ontology.search( "MI:0002" );
                                OntologyTermI child = ontology.search( partDetMethId );

                                if ( !ontology.isChildOf(interactionDetectionMethodRoot, child) ) {
                                    messages.add( new ValidatorMessage( " The Participant Identification Method " + partDetMethId + " of the experiment " + experimentId + " isn't a valid Participant Detection Method. ( must be any child of MI:0002)",
                                                                    MessageLevel.ERROR,
                                                                    context,
                                                                    this ) );
                                }
                            } catch (Exception e) {
                                messages.add( new ValidatorMessage( " The Participant Identification Method " + partDetMethId + " of the experiment " + experimentId + " doesn't have a valid PSI-MI id. ( can be any child of MI:0002)",
                                                                    MessageLevel.ERROR,
                                                                    context,
                                                                    this ) );
                            }
                        }
                    }
                    else {
                        messages.add( new ValidatorMessage( " The experiment " + experimentId + " has a Participant Identification Method with too many psi-mi cross references (only one is possible).",
                                                            MessageLevel.ERROR,
                                                            context,
                                                            this ) );
                    }
                }
                else {
                    messages.add( new ValidatorMessage( " The experiment " + experimentId + " has a Participant Identification Method which doesn't have any psi-mi cross references.",
                                                        MessageLevel.ERROR,
                                                        context,
                                                        this ) );

                }
            }
            else {
                messages.add( new ValidatorMessage( " The experiment " + experimentId + " has a Participant Identification Method which doesn't have any cross references (at least one psi-mi database reference is mandatory).",
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );

                }

        }

        return messages;
    }
}
