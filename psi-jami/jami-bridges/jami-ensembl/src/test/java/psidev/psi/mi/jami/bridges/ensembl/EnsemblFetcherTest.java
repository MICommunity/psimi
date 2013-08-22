package psidev.psi.mi.jami.bridges.ensembl;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class EnsemblFetcherTest {


    protected static final Logger log = LoggerFactory.getLogger(EnsemblFetcherTest.class.getName());

    @Test
    public void tests() throws Exception {
        EnsemblFetcher fetcher = new EnsemblFetcher();

        String[] tests = {"ENSG00000139618" , "ENSG00000172115" ,
                //"ENSG00000157764" , "AT3G52430" ,
                "ENSG00000160145" , "ENSG00000148400" , "ENSG00000068305" , "ENSG00000135218" //Coronary heart disease
        };

        for(String test : tests){
            log.info("**************************" );
            log.info("* TESTING "+test);
            log.info("**************************" );

            Collection<Gene> geneTest = fetcher.getGenesByIdentifierOfUnknownType(test);
            readOutGene(geneTest.iterator().next());
            Thread.sleep(1000); // Reduces request rate to <1/second , a rate of >3/second causes the server to refuse.
        }
    }

    public static void readOutGene(Gene gene){
        if(gene == null){
            log.info("null GENE") ;
            return;
        }
        log.info("short name : "+gene.getShortName());
        log.info("full name : "+gene.getFullName());
        //gene.get
        log.info("ensembl genome: "+gene.getEnsembleGenome());
        log.info("ensembl : "+gene.getEnsembl()) ;
        log.info("entrez : "+gene.getEntrezGeneId());
        log.info("refseq : "+gene.getRefseq());
        for(Xref xref : gene.getXrefs()){
            log.info("ID: "+ xref.getId()+" in "+xref.getDatabase().toString());
        }
    }


}
