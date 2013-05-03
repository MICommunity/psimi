package psidev.psi.mi.query.uniprot;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 16:38
 */
public class UniProtUtil {

    public void uniProtToJami (UniProtEntry entry){

        Organism organism = new OrganismExtractor().getOrganism(entry);
        ArrayList<Xref> xref = new XrefExtractor().getXrefs(entry);


        //return null;
    }



}
