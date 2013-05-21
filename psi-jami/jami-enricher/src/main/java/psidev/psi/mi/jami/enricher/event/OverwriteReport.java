package psidev.psi.mi.jami.enricher.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:40
 */
public class OverwriteReport extends EnricherEvent{
    private String oldValue;
    private String field;
    private String newValue;

    public OverwriteReport(){
    }

    public OverwriteReport(String field, String newValue, String oldValue){
        setOverwriteValues(field, newValue, oldValue);

    }

    public void setOverwriteValues(String field, String newValue, String oldValue){
        this.field = field;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    public String getOldValue() {return oldValue;}

    public void setOldValue(String oldValue) {this.oldValue = oldValue;}





    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
