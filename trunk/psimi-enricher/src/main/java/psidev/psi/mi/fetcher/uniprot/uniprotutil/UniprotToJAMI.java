package psidev.psi.mi.fetcher.uniprot.uniprotutil;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:41
 */
public class UniprotToJAMI {

    public static Protein getProteinFromEntry(UniProtEntry e){
        if(e == null){
            return null;
        }

        //todo
        Protein p = new DefaultProtein(e.getUniProtId().toString());

        p.setOrganism(getOrganismFromEntry(e));

        return p;
    }



    public static Organism getOrganismFromEntry(UniProtEntry e){
        Organism o;

        if(e.getNcbiTaxonomyIds().isEmpty()){
            o = new DefaultOrganism(-3); //Unknown
        } else if(e.getNcbiTaxonomyIds().size() > 1){
            o = new DefaultOrganism(-3); //Unknown
        } else {
            String id = e.getNcbiTaxonomyIds().get(0).toString();
            o = new DefaultOrganism( Integer.parseInt( id ) );
        }

        o.setCommonName(e.getOrganism().getCommonName().toString());
        o.setScientificName(e.getOrganism().getScientificName().toString());

        return o;
    }

}
