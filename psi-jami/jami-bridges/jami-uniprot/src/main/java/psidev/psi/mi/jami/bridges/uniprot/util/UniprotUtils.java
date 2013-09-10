package psidev.psi.mi.jami.bridges.uniprot.util;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.OrganismUtils;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

import java.util.regex.Pattern;

/**
 * Uniprot utils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/09/13</pre>
 */

public class UniprotUtils {

    // PRO regular expression: "PRO" followed by "-" OR "_" then any number of digits.
    public static final Pattern UNIPROT_PRO_REGEX = Pattern.compile("PRO[_|-][0-9]+");
    // Isoform regular expression: "-" followed by any number of digits.
    public static final Pattern UNIPROT_ISOFORM_REGEX = Pattern.compile("-[0-9]");
    public final static String FEATURE_CHAIN_FIELD = "feature.chain:";
    public final static String FEATURE_PEPTIDE_FIELD = "feature.peptide:";
    public final static String FEATURE_PRO_PEPTIDE_FIELD = "feature.propep:";

    public static psidev.psi.mi.jami.model.Organism createOrganismFromEntry(UniProtEntry entity){

        if(entity.getNcbiTaxonomyIds() == null
                || entity.getNcbiTaxonomyIds().isEmpty()){
            return OrganismUtils.createUnknownOrganism();
        } else if(entity.getNcbiTaxonomyIds().size() > 1){
            throw new IllegalArgumentException(
                    "Uniprot entry ["+entity.getPrimaryUniProtAccession().getValue()+"] "
                            +"has multiple organisms.");
        } else {
            String id = entity.getNcbiTaxonomyIds().get(0).getValue();
            try{
                psidev.psi.mi.jami.model.Organism o = new DefaultOrganism( Integer.parseInt( id ) );
                if(entity.getOrganism().hasCommonName())
                    o.setCommonName(entity.getOrganism().getCommonName().getValue());
                if(entity.getOrganism().getScientificName() != null)
                    o.setScientificName(entity.getOrganism().getScientificName().getValue());
                if(entity.getOrganism().hasSynonym())
                    o.getAliases().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, entity.getOrganism().getSynonym().getValue()));

                return o;
            }catch(NumberFormatException n){
                throw new IllegalArgumentException("Uniprot entry ["+entity.getPrimaryUniProtAccession().getValue()+"] " +
                        "has a TaxonomyID which could not be cast to an integer: ("+id+").",n);
            }
        }
    }
}
