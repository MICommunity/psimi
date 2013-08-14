package psidev.psi.mi.validator.extension.rules;

import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is for displaying a validator FATAL message when an exception is thrown by the psi xml parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23-Sep-2010</pre>
 */

public class PsimiXmlSchemaRule extends ObjectRule<Object>{
    public PsimiXmlSchemaRule(OntologyManager ontologyManager) {
        super(ontologyManager);

        setName( "PSI-XML file check" );

        setDescription( "Check that the xml file doesn't contain FATAL errors." );

        addTip( "check that you don't mix the expanded PSI MI xml with the compact one. Use only experimentRef and interactorRef when all the experiments and interactors " +
                "are defined at the beginning of the file (not at the level of the interaction)" );
        addTip( "If the error is too obscure, contact the intact team (intact-help@ebi.ac.uk) with your file attached to the e-mail." );
    }

    @Override
    public boolean canCheck(Object t) {
        return true;
    }

    @Override
    public Collection<ValidatorMessage> check(Object o) throws ValidatorException {
        return new ArrayList<ValidatorMessage>();
    }

    public StringBuffer createMessageFromException(Exception e) throws ValidatorException {


        StringBuffer bufferMessage = new StringBuffer(1064);

        bufferMessage.append("The validation could not finish properly because the file contains errors. If you don't understand" +
                " the exception messages, please contact the intact team (intact-help@ebi.ac.uk) and attach your file to the e-mail. \n");

        if (e.getMessage() != null){
            bufferMessage.append( e.getMessage() + "\n");
        }

        Throwable cause = e.getCause();

        while (cause != null){
            bufferMessage.append( cause.getMessage() + "\n");
            cause = cause.getCause();
        }

        return bufferMessage;
    }

    public String getId() {
        return "R41";
    }
}
