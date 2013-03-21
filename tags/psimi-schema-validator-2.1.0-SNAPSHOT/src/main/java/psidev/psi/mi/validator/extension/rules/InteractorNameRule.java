package psidev.psi.mi.validator.extension.rules;

import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Names;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <b> Check that each interactor has at least fullname or shortlabel.</b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 1.0
 */
public class InteractorNameRule extends ObjectRule<Interactor> {

    public InteractorNameRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interactor Name check" );
        setDescription( "Check that each interactor has at least a name or a short label" );
        addTip( "Provide a fullname or shortlabel using for instance the gene name or a systematic orf name or " +
                "a acession number or any meaningfyl acronyme to name the interactor" );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof Interactor){
             return true;
        }

        return false;
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interactor to check on.
     * @return a collection of validator messages.
     * @exception ValidatorException if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interactor interactor ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...
        final Names names = interactor.getNames();

        String fullname = names.getFullName();
        String shortlabel = names.getShortLabel();
        int interactorId = interactor.getId();

        if ( StringUtils.isEmpty( fullname ) && StringUtils.isEmpty( shortlabel ) ) {

            Mi25Context context = new Mi25Context();
            context.setId( interactorId );
            context.setObjectLabel("interactor");

            messages.add( new ValidatorMessage( "Interactor should have either a shortlabel and/or a fullname ",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return null;
    }
}