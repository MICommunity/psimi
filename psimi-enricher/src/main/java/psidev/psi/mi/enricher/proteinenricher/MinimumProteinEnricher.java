package psidev.psi.mi.enricher.proteinenricher;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher extends AbstractProteinEnricher {



    public void enrichOrgnaism(Organism masterOrganism, Organism enrichedOrganism){

        if(masterOrganism.getTaxId() > 0){
            if(masterOrganism.getTaxId() != enrichedOrganism.getTaxId()) {
                //Todo orgnaism conflict
            }
        }
        if(masterOrganism.getTaxId() == -3){
            //todo unknown, consider adding
        }

       // todo compare the common and scientific names
        //o.setCommonName(e.getOrganism().getCommonName().toString());
       // o.setScientificName(e.getOrganism().getScientificName().toString());

    }
}
