package psidev.psi.mi.fetcher.uniprot;

import psidev.psi.mi.enricher.organismenricher.OrganismFetcher;
import psidev.psi.mi.enricher.proteinenricher.ProteinFetcher;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.fetcher.uniprot.uniprotutil.UniprotToJAMI;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:09
 */
public class UniprotFetcher
        implements ProteinFetcher { //, OrganismFetcher {

    UniprotBridge bridge;

    public UniprotFetcher() throws BridgeFailedException {
        bridge = new UniprotBridge();
    }

    public Protein getProteinByID(String ID) throws BridgeFailedException {
        UniProtEntry e = bridge.fetchEntryByID(ID);
        Protein p = UniprotToJAMI.getProteinFromEntry(e);
        return p;
    }
    /*
    public Organism getOrganismByID(String ID) throws BridgeFailedException {
        UniProtEntry e = bridge.fetchEntryByID(ID);
        Organism o = UniprotToJAMI.getOrganismFromEntry(e);
        return o;
    }  */
}
