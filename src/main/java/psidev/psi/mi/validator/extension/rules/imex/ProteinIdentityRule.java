package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.MiInteractorRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * <b> Checks that proteins have UNIPROT and REFSEQ identity.</b>
 * <p/>
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 2.0
 */
public class ProteinIdentityRule extends MiInteractorRule {

    public ProteinIdentityRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        // describe the rule.
        setName( "Protein identity check" );

        setDescription( "Check that each protein has an identity cross reference to the sequence database: UniProtKB and RefSeq" );

        addTip( "UniProtKb accession in the PSI-MI ontology is " + UNIPROTKB_MI_REF );
        addTip( "RefSeq accession in the PSI-MI ontology is " + REFSEQ_MI_REF );
        addTip( "Identity accession in the PSI-MI ontology is " + IDENTITY_MI_REF );
    }

    /**
     * check that each interactor has at least name or a short label.
     *
     * @param interactor to check on.
     * @return a collection of validator messages.
     * @exception psidev.psi.tools.validator.ValidatorException if we fail to retrieve the MI Ontology.
     */
    public Collection<ValidatorMessage> check( psidev.psi.mi.jami.model.Interactor interactor ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        if( interactor.getType() != null && RuleUtils.isProtein( ontologyManager, interactor )) {

            final Collection<Xref> identities =
                    XrefUtils.searchAllXrefsHavingDatabase(interactor.getIdentifiers(), Arrays.asList(Xref.UNIPROTKB_MI, Xref.REFSEQ_MI), Arrays.asList(Xref.UNIPROTKB, Xref.REFSEQ));

            final Collection<Xref> identitiesUniprot = XrefUtils.collectAllXrefsHavingDatabase(interactor.getIdentifiers(),
                    Xref.UNIPROTKB_MI, Xref.UNIPROTKB);

            final Collection<Xref> identitiesRefseq = XrefUtils.collectAllXrefsHavingDatabase(interactor.getIdentifiers(),
                    Xref.REFSEQ_MI, Xref.REFSEQ);

            if( identities.isEmpty() ) {

                if (interactor instanceof Polymer && ((Polymer)interactor).getSequence() != null){
                    Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );
                    messages.add( new ValidatorMessage( "Proteins should have a Xref to UniProtKB and RefSeq with a ref type 'identity' ",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                else {
                    Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );
                    messages.add( new ValidatorMessage( "Proteins should have a Xref to UniProtKB and RefSeq with a ref type 'identity'. If no identity cross references " +
                            "are given, the protein sequence is strongly recommended.",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
            else{
                if (identitiesUniprot.isEmpty()){
                    Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );
                    messages.add( new ValidatorMessage( "Proteins should have a Xref to UniProtKB with a ref type 'identity' ",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
                if (identitiesRefseq.isEmpty()){
                    Mi25Context context = RuleUtils.buildContext( interactor, "interactor" );
                    messages.add( new ValidatorMessage( "Proteins should have a Xref to RefSeq with a ref type 'identity' ",
                            MessageLevel.WARN,
                            context,
                            this ) );
                }
            }
        }

        return messages;
    }

    public String getId() {
        return "R74";
    }
}