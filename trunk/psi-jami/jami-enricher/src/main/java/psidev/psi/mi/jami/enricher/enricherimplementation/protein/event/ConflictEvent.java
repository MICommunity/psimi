package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;

import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 11:56
 */
public class ConflictEvent {

    private String field;
    private Object newValue;
    //private String message;
    private Protein protein;

    public ConflictEvent(String field, Object newValue, Protein protein){
        this.field = field;
        this.newValue = newValue;
        this.protein = protein;
    }

   // public ConflictEvent(String field, Object newValue, Protein protein, String message){
      //  this(field, newValue, protein);
       // this.message = message;
   // }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }


    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }



}
