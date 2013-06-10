package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 11:13
 */
public class AddedEvent {

    private Map<String,ArrayList<Object>> addedElements = new HashMap<String,ArrayList<Object>>();
    private QueryDetails queryDetails;

    public Map<String, ArrayList<Object>> getAddedElements(){
        return addedElements;
    }

    public void addElement(String field,Object value){
        if(!addedElements.containsKey(field)){
            addedElements.put(field,new ArrayList<Object>());
        }
        addedElements.get(field).add(value);
    }

    public void setQueryDetails(QueryDetails queryDetails){
        this.queryDetails = queryDetails;
    }
}
