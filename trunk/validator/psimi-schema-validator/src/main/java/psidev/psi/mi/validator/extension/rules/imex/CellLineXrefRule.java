package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.CellType;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY_MI_REF;

/**
 * Checks that each cell type has at least one cross reference with qualifier identity to CABRI or Cell Ontology. If not, look also at the pubmed primary references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class CellLineXrefRule extends ObjectRule<CellType>{

    public CellLineXrefRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName("Cell line XRef Check");
        setDescription("Checks that each organism cell line (if it is present) has a CABRI or Cell type onotology cross reference with a qualifier 'identity'. " +
                "If no cross references can be given, one pubmed primary-reference should be added to be able to retrieve information about this cell type.");
        addTip( "Search for existing cell type terms at www.cabri.org or http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=CL" );
        addTip( "CABRI accession in the PSI-MI ontology is " + RuleUtils.CABRI_MI_REF );
        addTip( "Cell Ontology accession in the PSI-MI ontology is " + RuleUtils.CELL_ONTOLOGY_MI_REF );
        addTip( "Identity accession in the PSI-MI ontology is " + IDENTITY_MI_REF );
    }

    @Override
    public boolean canCheck(Object t) {
        if (t instanceof CellType){
            return true;
        }
        return false;
    }

    /**
     * @param cellType to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( CellType cellType) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();

        if (cellType.getXref() != null){
            Xref ref = cellType.getXref();

            Collection<DbReference> cabriReferences = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.CABRI_MI_REF, RuleUtils.CABRI, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY);
            Collection<DbReference> cellReferences = RuleUtils.findByDatabaseAndReferenceType(ref.getAllDbReferences(), RuleUtils.CELL_ONTOLOGY_MI_REF, RuleUtils.CELL_ONTOLOGY, RuleUtils.IDENTITY_MI_REF, RuleUtils.IDENTITY);
            Collection<DbReference> allPubmeds = RuleUtils.findByDatabaseAndReferenceType( ref.getAllDbReferences(), "MI:0446", "pubmed", "MI:0358", "primary-reference" );

            if (cabriReferences.isEmpty() && cellReferences.isEmpty() && allPubmeds.isEmpty()){
                messages.add( new ValidatorMessage( "The cellType " + (cellType.getNames() != null ? cellType.getNames().getShortLabel() : "") + " has "+ref.getAllDbReferences().size()+" cross references but no one is a CABRI or Cell Ontology cross reference with a qualifier 'identity' and it is strongly recommended. " +
                        "If the cell line cannot be identified but one of these databases, at least one pubmed primary reference is necessary.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "The cellType " + (cellType.getNames() != null ? cellType.getNames().getShortLabel() : "") + " doesn't have any cross references and at least one cross reference to CABRI or Cell Ontology with " +
                    "qualifier 'identity' is strongly recommended. If the cell line cannot be identified but one of these databases, at least one pubmed primary reference is necessary.'",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }
}
