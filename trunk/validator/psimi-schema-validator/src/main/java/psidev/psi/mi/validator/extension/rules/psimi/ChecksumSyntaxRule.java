package psidev.psi.mi.validator.extension.rules.psimi;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Rule to check that each checksum has a value and a method
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class ChecksumSyntaxRule extends AbstractMIRule<Checksum> {


    public ChecksumSyntaxRule(OntologyManager ontologyManager) {
        super(ontologyManager, Checksum.class);
        setName( "Checksum syntax check" );

        setDescription( "Check that each checksum has a valid name and if it has a MI method, it must have a valid MI term for checksum method." );
    }

    @Override
    public Collection<ValidatorMessage> check(Checksum checksum) throws ValidatorException {
        if (checksum != null){
            // list of messages to return
            List<ValidatorMessage> messages = Collections.EMPTY_LIST;
            final OntologyAccess access = ontologyManager.getOntologyAccess("MI");

            CvTerm method = checksum.getMethod();

            if (method == null ||
                    PsiXml25Utils.UNSPECIFIED.equals(method.getShortName()) ||
                    MitabUtils.UNKNOWN_TYPE.equals(method.getShortName())){
                MiContext xrefContext = RuleUtils.buildContext(checksum, "checksum");
                messages = new ArrayList<ValidatorMessage>();
                messages.add( new ValidatorMessage( "Checksums must have a valid method.'",
                        MessageLevel.ERROR,
                        xrefContext,
                        this ) );
            }
            else if (method != null && method.getMIIdentifier() != null){
                final OntologyTermI dbTerm = access.getTermForAccession(method.getMIIdentifier());

                if (dbTerm == null){
                    MiContext context = RuleUtils.buildContext(checksum, "checksum");
                    if (messages.isEmpty()){
                        messages = new ArrayList<ValidatorMessage>();
                    }
                    messages.add( new ValidatorMessage( "The checksum method MI identifier "+method.getMIIdentifier()+"  does not exist in the PSI-MI ontology. The valid MI terms for checksum methods are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A1212&termName=checksum",
                            MessageLevel.ERROR,
                            context,
                            this ) );
                }
                else {
                    Collection<OntologyTermI> parents = access.getAllParents(dbTerm);

                    boolean foundParent = false;

                    for (OntologyTermI p : parents){
                        if ("MI:1212".equals(p.getTermAccession())){
                            foundParent = true;
                            break;
                        }
                    }

                    if (!foundParent){
                        MiContext context = RuleUtils.buildContext(checksum, "checksum");
                        if (messages.isEmpty()){
                            messages = new ArrayList<ValidatorMessage>();
                        }
                        messages.add( new ValidatorMessage( "The MI identifier "+method.getMIIdentifier()+" is not a valid MI identifier for checksum methods. The valid MI terms for checksum methods are available here: http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI%3A1212&termName=checksum",
                                MessageLevel.ERROR,
                                context,
                                this ) );
                    }
                }
            }

            if (checksum.getValue() == null ||
                    checksum.getValue().trim().length() == 0 ||
                    PsiXml25Utils.UNSPECIFIED.equals(checksum.getValue()) ||
                    MitabUtils.UNKNOWN_ID.equals(checksum.getValue())){
                MiContext xrefContext = RuleUtils.buildContext(checksum, "checksum");
                if (messages.isEmpty()){
                    messages = new ArrayList<ValidatorMessage>();
                }
                messages.add( new ValidatorMessage( "Checksums must have a valid and non empty value.'",
                        MessageLevel.ERROR,
                        xrefContext,
                        this ) );
            }

            return messages;
        }
        return Collections.EMPTY_LIST;
    }


    public String getId() {
        return "R24";
    }
}
