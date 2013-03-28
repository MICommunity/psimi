package psidev.psi.mi.validator.extension.rules.mimix;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.*;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.BIOACTIVE_ENTITY_DATABASE_MI_REF;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.SEQUENCE_DATABASE_MI_REF;

/**
 * <b> Check that each interactor has a cross reference to an appropriate reference database.</b>
 * <p/>
 *
 * @author Samuel Kerrien, Luisa Montecchi
 * @version $Id$
 * @since 1.0
 */
public class InteractorIdentityRule extends ObjectRule<psidev.psi.mi.jami.model.Interactor> {

    public InteractorIdentityRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Interactor database reference check" );
        setDescription( "Check that each interactor has a cross reference to an appropriate reference database. If not, a sequence should be provided." );
        addTip( "Sequence databases can be found in the PSI-MI ontology under term MI:0683" );
        addTip( "Bioactive entity databases can be found in the PSI-MI ontology under term MI:2054" );
        addTip( "The term " );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof psidev.psi.mi.jami.model.Interactor){
            return true;
        }

        return false;
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interactor to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( psidev.psi.mi.jami.model.Interactor interactor ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        final OntologyAccess mi = getMiOntology();

        // write the rule here ...
        if( interactor.getType() != null && (RuleUtils.isSmallMolecule( ontologyManager, interactor ) || RuleUtils.isPolysaccharide( ontologyManager, interactor ))) {

            // TODO cache these MI refs
            final Set<OntologyTermI> dbs = mi.getValidTerms( BIOACTIVE_ENTITY_DATABASE_MI_REF, true, false );

            final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );
            final Set<String> dbRefs = RuleUtils.collectNames( dbs );

            //final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabaseAndQualifier(interactor.getIdentifiers(), Arrays.asList(Xref.IDENTITY_MI), dbMiRefs);
            final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabase(interactor.getIdentifiers(), dbMiRefs, dbRefs);

            if( identities.isEmpty() ) {
                Mi25Context context = RuleUtils.buildContext(interactor, "bioactive entity");
                messages.add( new ValidatorMessage( "Interactor should have an Xref to a bioactive entity database with a ref type 'identity' ",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }

        }
        else if( interactor.getType() != null && RuleUtils.isBiopolymer(ontologyManager, interactor)) {

            // TODO cache these MI refs
            final Set<OntologyTermI> dbs = mi.getValidTerms( SEQUENCE_DATABASE_MI_REF, true, false );

            final Set<String> dbMiRefs = RuleUtils.collectAccessions( dbs );
            final Set<String> dbRefs = RuleUtils.collectNames( dbs );

            //final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabaseAndQualifier(interactor.getIdentifiers(), Arrays.asList(Xref.IDENTITY_MI), dbMiRefs);
            final Collection<Xref> identities = XrefUtils.searchAllXrefsHavingDatabase(interactor.getIdentifiers(), dbMiRefs, dbRefs);
            if( identities.isEmpty() ) {
                Mi25Context context = RuleUtils.buildContext(interactor, "biopolymer");
                    messages.add( new ValidatorMessage( "Interactor should have a Xref to a sequence database with a ref type 'identity' ",
                            MessageLevel.ERROR,
                            context,
                            this ) );

                if (interactor instanceof Polymer && ((Polymer) interactor).getSequence() == null){
                    Mi25Context context2 = RuleUtils.buildContext( interactor, "biopolymer" );
                    messages.add( new ValidatorMessage( "Biopolymer without a sequence and without any Xrefs to a sequence database with a ref type 'identity'.",
                            MessageLevel.WARN,
                            context2,
                            this ) );
                }
            }

        }

        return messages;
    }

    public OntologyAccess getMiOntology() throws ValidatorException {
        return ontologyManager.getOntologyAccess( "MI" );
    }

    public String getId() {
        return "R57";
    }
}