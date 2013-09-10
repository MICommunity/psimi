package psidev.psi.mi.jami.bridges.uniprot;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotUtils;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultGene;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensembl.Ensembl;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensemblbacteria.EnsemblBacteria;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensemblfungi.EnsemblFungi;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensemblmetazoa.EnsemblMetazoa;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensemblplants.EnsemblPlants;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensemblprotists.EnsemblProtists;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.refseq.RefSeq;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneNameSynonym;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/08/13
 */
public class UniprotGeneFetcher implements GeneFetcher {


    private UniProtQueryService uniProtQueryService;

    public UniprotGeneFetcher() {
        uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
    }

    /**
     * Finds a gene in uniprot using with the ensembl ID, the refseq ID or the ensemblGenomes ID.
     * The organism is optional, a taxid of 0 or less
     * @param identifier    An identifier for the gene.
     * @param taxID         The taxID of the organism.
     * @return              The matching gene records, or an empty collection if no record was found.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifier(String identifier, int taxID)
            throws BridgeFailedException {

        if(identifier == null)
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        String queryTerms;
        queryTerms = "("+
                "xref.ensembl:"+identifier+" OR "+
                "xref.refseq:"+identifier+" OR "+
                "xref.ensemblbacteria:"+identifier+" OR "+
                "xref.ensemblfungi:"+identifier+" OR "+
                "xref.ensemblmetazoa:"+identifier+" OR "+
                "xref.ensemblprotists:"+identifier+" OR "+
                "xref.ensemblplants:"+identifier+")";
        if(taxID != -3)
            queryTerms = queryTerms +" AND organism.ncbi:"+taxID;

        Query query = UniProtQueryBuilder.buildQuery(queryTerms) ;

        EntryIterator<UniProtEntry> entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        if(entryIterator.hasNext()){
            Gene gene = createGenesFromEntry(entryIterator.next(), identifier);
            genes.add(gene);
        }
        return genes;
    }

    public Collection<Gene> fetchGenesByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        return fetchGenesByIdentifiers(identifiers, -3);
    }

    public Collection<Gene> fetchGenesByIdentifiers(Collection<String> identifiers, int taxID) throws BridgeFailedException {
        if(identifiers == null)
            throw new IllegalArgumentException("Could not perform search on null collection of identifiers.");

        if (!identifiers.isEmpty()){
            Collection<Gene> genes = new ArrayList<Gene>(identifiers.size());
            for (String id : identifiers){
                genes.addAll(fetchGenesByIdentifier(id, taxID));
            }

            return genes;
        }
        return Collections.EMPTY_LIST;
    }

    public Collection<Gene> fetchGenesByIdentifier(String identifier)
            throws BridgeFailedException {
        if(identifier == null)
            throw new IllegalArgumentException("Could not perform search on null identifier.");
        return fetchGenesByIdentifier(identifier, -3);
    }

    private psidev.psi.mi.jami.model.Gene createGenesFromEntry(UniProtEntry entity , String identifier){
        // Using protein id as gene short name ending with _gene:
        psidev.psi.mi.jami.model.Gene jamiGene = new DefaultGene(entity.getUniProtId().getValue()+"_gene");

        String fullName = null;
        for(Field f: entity.getProteinDescription().getRecommendedName().getFields()){
            if(f.getType() == FieldType.FULL){
                fullName = f.getValue();
                break;
            }
        }
        jamiGene.setFullName(fullName);
        jamiGene.setOrganism(UniprotUtils.createOrganismFromEntry(entity));

        for(DatabaseCrossReference crossRef : entity.getDatabaseCrossReferences()){
            switch (crossRef.getDatabase()){
                case REFSEQ:
                    RefSeq xrefRefseq = (RefSeq)crossRef;
                    if(xrefRefseq.hasRefSeqAccessionNumber()) {
                        if(xrefRefseq.getRefSeqAccessionNumber().getValue().equals(identifier))
                            jamiGene.setRefseq(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.REFSEQ, Xref.REFSEQ_MI, xrefRefseq.getRefSeqAccessionNumber().getValue()));
                    }
                    break;
                case ENSEMBL:
                    Ensembl xrefEnsembl = (Ensembl)crossRef;
                    if(xrefEnsembl.hasEnsemblGeneIdentifier()) {
                        if(xrefEnsembl.getEnsemblGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsembl(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, xrefEnsembl.getEnsemblGeneIdentifier().getValue()));
                    }
                    break;
                case ENSEMBLBACTERIA:
                    EnsemblBacteria xrefEnsemblBac = (EnsemblBacteria)crossRef;
                    if(xrefEnsemblBac.hasEnsemblBacteriaGeneIdentifier()) {
                        if(xrefEnsemblBac.getEnsemblBacteriaGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsemblGenome(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, xrefEnsemblBac.getEnsemblBacteriaGeneIdentifier().getValue()));
                    }
                    break;
                case ENSEMBLFUNGI:
                    EnsemblFungi xrefEnsemblFun = (EnsemblFungi)crossRef;
                    if(xrefEnsemblFun.hasEnsemblFungiGeneIdentifier()) {
                        if(xrefEnsemblFun.getEnsemblFungiGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsemblGenome(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, xrefEnsemblFun.getEnsemblFungiGeneIdentifier().getValue()));
                    }
                    break;
                case ENSEMBLMETAZOA:
                    EnsemblMetazoa xrefEnsemblMet = (EnsemblMetazoa)crossRef;
                    if(xrefEnsemblMet.hasEnsemblMetazoaGeneIdentifier()) {
                        if(xrefEnsemblMet.getEnsemblMetazoaGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsemblGenome(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, xrefEnsemblMet.getEnsemblMetazoaGeneIdentifier().getValue()));
                    }
                    break;
                case ENSEMBLPLANTS:
                    EnsemblPlants xrefEnsemblPla = (EnsemblPlants)crossRef;
                    if(xrefEnsemblPla.hasEnsemblPlantsGeneIdentifier()) {
                        if(xrefEnsemblPla.getEnsemblPlantsGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsemblGenome(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, xrefEnsemblPla.getEnsemblPlantsGeneIdentifier().getValue()));
                    }
                    break;
                case ENSEMBLPROTISTS:
                    EnsemblProtists xrefEnsemblPro = (EnsemblProtists)crossRef;
                    if(xrefEnsemblPro.hasEnsemblProtistsGeneIdentifier()) {
                        if(xrefEnsemblPro.getEnsemblProtistsGeneIdentifier().getValue().equals(identifier))
                            jamiGene.setEnsemblGenome(identifier);
                        else
                            jamiGene.getXrefs().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, xrefEnsemblPro.getEnsemblProtistsGeneIdentifier().getValue()));
                    }
                    break;
            }
        }

        for(uk.ac.ebi.kraken.interfaces.uniprot.Gene entityGene : entity.getGenes()){
            if(entityGene.hasGeneName())
                jamiGene.getAliases().add(AliasUtils.createGeneName(entityGene.getGeneName().getValue()));
            for(GeneNameSynonym synonym : entityGene.getGeneNameSynonyms()){
                jamiGene.getAliases().add(AliasUtils.createGeneNameSynonym(synonym.getValue()));
            }
        }
        return jamiGene;
    }
}
