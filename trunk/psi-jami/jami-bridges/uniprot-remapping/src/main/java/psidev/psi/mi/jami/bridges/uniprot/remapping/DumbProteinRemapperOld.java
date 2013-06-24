package psidev.psi.mi.jami.bridges.uniprot.remapping;


import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.results.impl.DefaultIdentificationResults;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 */
public class DumbProteinRemapperOld extends AbstractProteinRemapper {

    private HashMap<Xref, IdentificationResults> xrefList;

    String IDENTIFIER = "P42694";
    IdentificationResults empty = new DefaultIdentificationResults();


    public DumbProteinRemapperOld(){
        super();
        setTestXrefs();
    }

    @Override
    protected IdentificationResults getMappingForXref(Protein p, Xref x) {
        if(xrefList.containsKey(x)){
            if(xrefList.get(x).getFinalUniprotId().equalsIgnoreCase("null")) return empty;
            return xrefList.get(x);
        }
        else return empty;
    }

    @Override
    protected IdentificationResults getMappingForSequence(Protein p) {
        if(p.getSequence() == null) return empty;
        else {
            IdentificationResults tempResult = new DefaultIdentificationResults();
            tempResult.setFinalUniprotId(IDENTIFIER);
            return tempResult;
        }
    }

    private void setTestXrefs(){
        xrefList = new HashMap<Xref, IdentificationResults>();
        String [][] xrefsraw = {
                {"ensembl", "ENSP00000351524", "P42694"},
                {"ensembl", "ENSG00000198265", "P42694"},
                {"pfam","PF00642", "null"},
                {"ensembl","ENSG00000197561", "P08246"},
        };
        for(int i = 0; i< xrefsraw.length ;i++){
            addToXrefList(xrefsraw[i][0],xrefsraw[i][1],xrefsraw[i][2]);
        }

    }
    private void addToXrefList(String database, String identifier, String lookupValue){
        Xref tempX = new DefaultXref(new DefaultCvTerm(database), identifier);
        DefaultIdentificationResults tempR = new DefaultIdentificationResults();
        tempR.setFinalUniprotId(lookupValue);
        xrefList.put(tempX,tempR);
    }
}
