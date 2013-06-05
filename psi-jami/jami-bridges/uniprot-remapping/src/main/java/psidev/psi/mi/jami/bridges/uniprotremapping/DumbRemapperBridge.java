package psidev.psi.mi.jami.bridges.uniprotremapping;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.results.impl.DefaultIdentificationResults;

import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 16:43
 */
public class DumbRemapperBridge implements RemapperBridge {

    IdentificationResults empty = new DefaultIdentificationResults();

    String sequence = null;

    private TreeMap<Xref, IdentificationResults> xrefList;
    private IdentificationResults sequenceIdentifier = null;

    public DumbRemapperBridge(){
        xrefList = new TreeMap<Xref, IdentificationResults>(new DefaultExternalIdentifierComparator());
        String [][] xrefsraw = {
                {"ensembl", "ENSP00000351524", "P42694"},
                {"ensembl", "ENSG00000198265", "P42694"},
                {"pfam","PF00642", "null"},
                {"ensembl"," ENSG00000197561", "P08246"},
        };
        for(int i = 0; i< xrefsraw.length ;i++){
            String database = xrefsraw[i][0];
            String id = xrefsraw[i][1];
            Xref tempX = new DefaultXref(new DefaultCvTerm(database), id);
            DefaultIdentificationResults tempR = new DefaultIdentificationResults();
            tempR.setFinalUniprotId(xrefsraw[i][2]);
            xrefList.put(tempX,tempR);
        }

        sequenceIdentifier = new DefaultIdentificationResults();
        sequenceIdentifier.setFinalUniprotId("P42694");

    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setOrganism(Organism organism) {
        return;
    }

    public IdentificationResults getIdentifierResult(Xref identifier) {
        if(xrefList.containsKey(identifier)){
            if(xrefList.get(identifier).getFinalUniprotId().equalsIgnoreCase("null")) return null;
            return xrefList.get(identifier);
        }
        else return empty;
    }

    public IdentificationResults getSequenceMapping() {
        if( sequence == null || sequenceIdentifier == null) return empty;
        else return sequenceIdentifier;
    }

    public void clean() {
        sequence = null;

    }
}
