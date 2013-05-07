package psidev.psi.mi.query.uniprotbridge.uniprotextractors;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/04/13
 * Time: 14:01
 */
public class OrganismExtractorUniprot {

    public ArrayList<Organism> getOrganismFromEntry(UniProtEntry entry) {
        if(entry == null){
            return null;
        }

        ArrayList<Organism> organism = new ArrayList<Organism>();
        int taxidSize = entry.getNcbiTaxonomyIds().size();

        if(taxidSize == 0){
            organism.add(new DefaultOrganism(-3));
        } else {
            for(int i = 0; i < taxidSize; i++){
                Organism organismEntry = new DefaultOrganism(Integer.parseInt(
                        entry.getNcbiTaxonomyIds().get(0).getValue()));
                organismEntry.setCommonName(entry.getOrganism().getCommonName().getValue());
                organismEntry.setScientificName(entry.getOrganism().getScientificName().getValue());

                //setCellType(CvTerm cellType);
                //setCompartment(CvTerm compartment);
                //CvTerm getTissue();

                organism.add(organismEntry);
            }
        }
        return organism;
    }
}
