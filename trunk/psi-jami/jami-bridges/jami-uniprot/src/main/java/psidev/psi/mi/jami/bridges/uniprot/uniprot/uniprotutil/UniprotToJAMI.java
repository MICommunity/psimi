package psidev.psi.mi.jami.bridges.uniprot.uniprot.uniprotutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.factory.ChecksumFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:41
 */
public class UniprotToJAMI {

    private final static Logger log = LoggerFactory.getLogger(UniprotToJAMI.class.getName());

    public static Protein getProteinFromEntry(UniProtEntry e){
        if(e == null){
            return null;
        }

        String shortName = null;
        String fullName = null;

        //THIS ID HAS BEEN TAKEN FROM THE 'ID' name
        List<Field> fields =  e.getProteinDescription().getRecommendedName().getFields();
        for(Field f: fields){
            if(f.getType() == FieldType.SHORT){
                if(shortName == null){
                    shortName = f.getValue();
                }
                else{log.debug("MULTINAMED UNIPROT: "+shortName+", "+f.getValue());}
            }
            if(f.getType() == FieldType.FULL){
                if(fullName == null){
                    fullName = f.getValue();
                }
                else{log.debug("MULTINAMED UNIPROT: "+fullName+", "+f.getValue());}
            }
        }

        Protein p;
        //SHORTNAME
        if(shortName != null){
            p = new DefaultProtein(shortName);
        }else if(fullName != null){
            p = new DefaultProtein(fullName);
        }else {
            p = new DefaultProtein(e.getUniProtId().getValue());
        }

        //FULLNAME
        if(fullName != null){
            p.setFullName(fullName);
        }

        //PRIMARY ACCESSION
        p.setUniprotkb(e.getPrimaryUniProtAccession().getValue());

        //SEQUENCE // CHECKSUMS
        p.setSequence(e.getSequence().getValue());
        ChecksumFactory cf = new ChecksumFactory();
        //todo implement crc64 checksums
        p.getChecksums().add(cf.createAnnotation("methodName", "methodMi", e.getSequence().getCRC64()));
        //Rogid will be calculated at enrichment - the equation need not be applied in an organism conflict

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
            String id = e.getNcbiTaxonomyIds().get(0).getValue();
            o = new DefaultOrganism( Integer.parseInt( id ) );
        }

        o.setCommonName(e.getOrganism().getCommonName().getValue());
        o.setScientificName(e.getOrganism().getScientificName().getValue());

        return o;
    }

}
