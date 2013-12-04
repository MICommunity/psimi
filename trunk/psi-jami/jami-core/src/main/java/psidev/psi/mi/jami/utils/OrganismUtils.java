package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public class OrganismUtils {

    /**
     * If the TaxId is one of the reserved numbers (-5 to -1), it denotes a non-real organism.
     * An organism with these names will be produced if there is match.
     * Otherwise null is returned.
     *
     * -1 = In Vitro
     * -2 = Chemical synthesis
     * -3 = Unknown
     * -4 = In vivo
     * -5 = In Silico
     *
     * @param taxID     A taxId which might match one of the reserved terms.
     * @return          A complete organism record if the taxID matches reserved term. Null if it could not be found.
     */
    public static Organism createSpecialistOrganism(int taxID){
        Organism organism;
        switch (taxID){
            case -1:
                organism = new DefaultOrganism( -1 );
                organism.setScientificName( "In vitro" );
                organism.setCommonName( "In vitro" );
                return organism;
            case -2:
                organism = new DefaultOrganism( -2 );
                organism.setScientificName( "Chemical synthesis" );
                organism.setCommonName( "Chemical synthesis" );
                return organism;
            case -3:
                organism = new DefaultOrganism( -3 );
                organism.setScientificName( "Unknown" );
                organism.setCommonName( "Unknown" );
                return organism;
            case -4:
                organism = new DefaultOrganism( -4 );
                organism.setScientificName( "In vivo" );
                organism.setCommonName( "In vivo" );
                return organism;
            case -5:
                organism = new DefaultOrganism( -5 );
                organism.setScientificName( "In Silico" );
                organism.setCommonName( "In Silico" );
                return organism;
            default:
                return null;
        }
    }

    public static Organism createUnknownOrganism(){
        return createSpecialistOrganism(-3);
    }

    public static Organism createInVitroOrganism() {
        return createSpecialistOrganism(-1);
    }

    public static Organism createChemicalSynthesisOrganism() {
        return createSpecialistOrganism(-2);
    }

    public static Organism createInVivoOrganism() {
        return createSpecialistOrganism(-4);
    }

    public static Organism createInSilicoOrganism() {
        return createSpecialistOrganism(-5);
    }

}
