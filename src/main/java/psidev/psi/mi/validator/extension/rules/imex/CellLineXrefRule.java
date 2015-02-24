package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.Collections;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY_MI_REF;

/**
 * Checks that each cell type has at least one cross reference with qualifier identity to CABRI or Cell Ontology. If not, look also at the pubmed primary references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class CellLineXrefRule extends AbstractMIRule<Organism>{

    public CellLineXrefRule( OntologyManager ontologyManager ) {
        super( ontologyManager, Organism.class );

        // describe the rule.
        setName("Cell line XRef Check");
        setDescription("Checks that each organism cell line (if present) has a CABRI or Cell type ontology cross reference with a qualifier 'identity'. " +
                "If no cross references can be found, one pubmed primary-reference should be added to be able to retrieve information about this cell type.");
        addTip( "Search for existing cell type terms at www.cabri.org or http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=CL" );
        addTip( "CABRI accession in the PSI-MI ontology is " + RuleUtils.CABRI_MI_REF );
        addTip( "Cell Ontology accession in the PSI-MI ontology is " + RuleUtils.CELL_ONTOLOGY_MI_REF );
        addTip( "Identity accession in the PSI-MI ontology is " + IDENTITY_MI_REF );
    }

    /**
     * @param organism to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Organism organism) throws ValidatorException {

        if (organism.getCellType() == null){
            return Collections.EMPTY_LIST;
        }

        Collection<ValidatorMessage> messages = Collections.EMPTY_LIST;
        CvTerm cellType = organism.getCellType();

        if (!cellType.getIdentifiers().isEmpty() || !cellType.getXrefs().isEmpty()){

            Collection<psidev.psi.mi.jami.model.Xref> cabriReferences = XrefUtils.collectAllXrefsHavingDatabase(cellType.getIdentifiers(), RuleUtils.CABRI_MI_REF, RuleUtils.CABRI);
            Collection<psidev.psi.mi.jami.model.Xref> cellReferences = XrefUtils.collectAllXrefsHavingDatabase(cellType.getIdentifiers(), RuleUtils.CELL_ONTOLOGY_MI_REF, RuleUtils.CELL_ONTOLOGY);
            Collection<psidev.psi.mi.jami.model.Xref> allPubmeds = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(cellType.getXrefs(), psidev.psi.mi.jami.model.Xref.PUBMED_MI, psidev.psi.mi.jami.model.Xref.PUBMED, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY);

            if (cabriReferences.isEmpty() && cellReferences.isEmpty() && allPubmeds.isEmpty()){
                MiContext context = RuleUtils.buildContext(cellType, "cell type");
                context.addAssociatedContext(RuleUtils.buildContext(organism, "organism"));
                messages=Collections.singleton( new ValidatorMessage( "The cellType " + cellType.getShortName() + " does not have a CABRI or Cell Ontology cross reference with a qualifier 'identity' and it is strongly recommended. " +
                        "If the cell line cannot be identified but one of these databases, at least one pubmed primary reference is necessary.'",
                        MessageLevel.WARN,
                        context,
                        this ) );
            }
        }
        else {
            MiContext context = RuleUtils.buildContext(cellType, "cell type");
            context.addAssociatedContext(RuleUtils.buildContext(organism, "organism"));
            messages=Collections.singleton( new ValidatorMessage( "The cellType " + cellType.getShortName() + " does not have any cross references and at least one cross reference to CABRI or Cell Ontology with " +
                    "qualifier 'identity' is strongly recommended. If the cell line cannot be identified but one of these databases, at least one pubmed primary reference is necessary.'",
                    MessageLevel.WARN,
                    context,
                    this ) );
        }

        return messages;
    }

    public String getId() {
        return "R35";
    }
}
