package psidev.psi.mi.jami.bridges.uniprotremapping;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.model.contexts.UpdateContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.results.impl.DefaultIdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithIdentifier;
import uk.ac.ebi.intact.protein.mapping.strategies.StrategyWithSequence;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 16:30
 */
public interface RemapperBridge {

    public void setSequence(String sequence);
    public void setOrganism(Organism organism);
    public IdentificationResults getIdentifierResult(Xref identifier);
    public IdentificationResults getSequenceMapping();
    public void clean();

}
