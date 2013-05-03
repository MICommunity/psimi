package psidev.psi.mi.query.uniprot;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/04/13
 * Time: 14:01
 */
public class OrganismExtractor extends Extractor{

    public Organism getOrganism(UniProtEntry entry){
        report = null;
        if(entry == null){
            report.add("No entry to extract organism from");
            return null;
        }

        Organism organism;

        int taxidSize = entry.getNcbiTaxonomyIds().size();


        if(taxidSize == 0){
            report.add("No taxid available");
            organism = new DefaultOrganism(-3);
        } else if(taxidSize == 1){
            organism = new DefaultOrganism(Integer.parseInt(entry.getNcbiTaxonomyIds().get(0).getValue()));
        }else{
            organism = new DefaultOrganism(Integer.parseInt(entry.getNcbiTaxonomyIds().get(0).getValue()));
            String test = entry.getNcbiTaxonomyIds().get(0).getValue();
            for(int i = 1; i < entry.getNcbiTaxonomyIds().size(); i++) {
                if(! test.equals(entry.getNcbiTaxonomyIds().get(i).getValue())) {
                    report.add("Conflicting taxid "+test+" and "+entry.getNcbiTaxonomyIds().get(i).getValue());
                    organism = new DefaultOrganism(-3);
                    break;
                }
            }


        }

        organism.setCommonName(entry.getOrganism().getCommonName().getValue());
        organism.setScientificName(entry.getOrganism().getScientificName().getValue());

        //setCellType(CvTerm cellType);
        //setCompartment(CvTerm compartment);
        //CvTerm getTissue();

        return organism;
    }
}
