package psidev.psi.mi.jami.enricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/05/13
 * Time: 16:35
 */
public class MismatchEvent {

    private String field;
    private String oldValue;
    private String newValue;

    public MismatchEvent(String field, String oldValue, String newValue){
        setMismatchValues(field, oldValue, newValue);
    }

    /*public AdditionEvent(String queryID, String queryIDType, String field, String newValue){
        super(queryID, queryIDType);
        setAdditionValues(field, newValue);
    }   */

    public void setMismatchValues(String field, String oldValue, String newValue){
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public void setField(String field) {this.field = field;}
    public void setOldValue(String oldValue){this.oldValue = oldValue;}
    public void setNewValue(String newValue) {this.newValue = newValue;}

    public String getField() {return field;}
    public String getOldValue(){return oldValue;}
    public String getNewValue() {return newValue;}

}
