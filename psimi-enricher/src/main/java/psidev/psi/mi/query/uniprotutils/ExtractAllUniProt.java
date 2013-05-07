package psidev.psi.mi.query.uniprotutils;

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
public class ExtractAllUniProt {

    public static void uniprotToJami (UniProtEntry entry){

        ArrayList<Organism> organism = new OrganismExtractor().getOrganismFromEntry(entry);
        ArrayList<Xref> xref = new XrefExtractor().getXrefs(entry);


        //return null;
    }



}
