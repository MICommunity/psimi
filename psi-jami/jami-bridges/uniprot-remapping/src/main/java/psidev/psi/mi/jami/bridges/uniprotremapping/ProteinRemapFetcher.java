package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 09:36
 */
public class ProteinRemapFetcher {

    public static final Log log = LogFactory.getLog(ProteinRemapFetcher.class);

    private IntActRemapperBridge bridge = new IntActRemapperBridge();
    private IdentificationResults sequenceMapping;
    private TreeMap<Xref, IdentificationResults> identifierList;

    public ProteinRemapFetcher() {
        identifierList = new TreeMap<Xref, IdentificationResults>(new DefaultExternalIdentifierComparator());
    }

    public void setIdentifierList(Xref identifier){
        identifierList.put(identifier,null);
    }

    public void setOrganism(Organism organism){
        if(organism != null) bridge.setOrganism(organism);
    }

    public void setSequence(String sequence){
        if(sequence != null) bridge.setSequence(sequence);
    }



    public IdentificationResults getIdentifierListEntry(Xref identifier){
        if(identifierList.containsKey(identifier)){
            if(identifierList.get(identifier) == null){
                identifierList.put(identifier, bridge.getIdentifierResult(identifier));
            }
            return identifierList.get(identifier);
        } else {
            log.warn("NOT IN THE LIST: "+identifier);
            return null;
        }
    }

    public IdentificationResults getSequenceMapping(){
        if(sequenceMapping == null) sequenceMapping = bridge.getSequenceMapping();
        return sequenceMapping;
    }

    public void clean(){
        identifierList.clear();
        bridge.clean();
        sequenceMapping = null;
    }

}
