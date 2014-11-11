package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;

/**
 * Factory for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class InteractorUtils {

    public static Interactor createUnknownBasicInteractor(){
        return new DefaultInteractor("unknown", CvTermUtils.createMICvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI));
    }

    /**
     * To know if an interactor have a specific interactor type.
     * @param interactor
     * @param typeId
     * @param typeName
     * @return true if the interactor has the type with given name/identifier
     */
    public static boolean doesInteractorHaveType(Interactor interactor, String typeId, String typeName){

        if (interactor == null){
            return false;
        }
        return CvTermUtils.isCvTerm(interactor.getInteractorType(), typeId, typeName);
    }

    public static Protein createProteinUniprot(String name, String uniprot){
        return new DefaultProtein(name, XrefUtils.createUniprotIdentity(uniprot));
    }

    public static Protein createProteinRefseq(String name, String refseq){
        return new DefaultProtein(name, XrefUtils.createRefseqIdentity(refseq));
    }

    public static Protein createProteinGeneName(String name, String geneName){
        Protein protein = new DefaultProtein(name);
        protein.setGeneName(geneName);
        return protein;
    }

    public static Protein createProteinRogid(String name, String rogid){
        Protein protein = new DefaultProtein(name);
        protein.setRogid(rogid);
        return protein;
    }

    public static BioactiveEntity createBioactiveEntityChebi(String name, String chebi){
        return new DefaultBioactiveEntity(name, XrefUtils.createChebiIdentity(chebi));
    }

    public static BioactiveEntity createBioactiveEntitySmile(String name, String smile){
        BioactiveEntity entity = new DefaultBioactiveEntity(name);
        entity.setSmile(smile);
        return entity;
    }

    public static BioactiveEntity createBioactiveEntityStandardInchi(String name, String standard){
        BioactiveEntity entity = new DefaultBioactiveEntity(name);
        entity.setStandardInchi(standard);
        return entity;
    }

    public static BioactiveEntity createBioactiveEntityStandardInchiKey(String name, String key){
        BioactiveEntity entity = new DefaultBioactiveEntity(name);
        entity.setStandardInchiKey(key);
        return entity;
    }

    public static NucleicAcid createNucleicAcidDdbjEmblGenbank(String name, String identity){
        return new DefaultNucleicAcid(name, XrefUtils.createDdbjEmblGenbankIdentity(identity));
    }

    public static NucleicAcid createNucleicAcidRefseq(String name, String identity){
        return new DefaultNucleicAcid(name, XrefUtils.createRefseqIdentity(identity));
    }

    public static Gene createGeneRefseq(String name, String identity){
        return new DefaultGene(name, XrefUtils.createRefseqIdentity(identity));
    }

    public static Gene createGeneEnsembl(String name, String identity){
        return new DefaultGene(name, XrefUtils.createEnsemblIdentity(identity));
    }
    public static Gene createGeneEnsemblGenomes(String name, String identity){
        return new DefaultGene(name, XrefUtils.createEnsemblGenomesIdentity(identity));
    }
    public static Gene createGeneEntrezGene(String name, String identity){
        return new DefaultGene(name, XrefUtils.createEntrezGeneIdIdentity(identity));
    }

    public static Polymer createPolymer(String name, String sequence){
        Polymer polymer = new DefaultPolymer(name);
        polymer.setSequence(sequence);
        return polymer;
    }
}
