package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The updated event stores all updates made over the course of the enrichment.
 *
 * An enrichment is any addition which requires data to be deleted from the proteinToEnrich.
 * The updatedEvent stores the values which were removed.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 13:46
 */
public class UpdatedEvent {
    private Map<String,ArrayList<Object>> updatedElements = new HashMap<String,ArrayList<Object>>();
    private QueryDetails queryDetails;


    public Map<String, ArrayList<Object>> getUpdatedElements(){
        return updatedElements;
    }

    public void addElement(String field,Object value){
        if(!updatedElements.containsKey(field)){
            updatedElements.put(field,new ArrayList<Object>());
        }
        updatedElements.get(field).add(value);
    }

    public void setQueryDetails(QueryDetails queryDetails){
        this.queryDetails = queryDetails;
    }
}
