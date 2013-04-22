package psidev.psi.mi.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 14:14
 */
public class UnrecognizedCriteriaException extends Exception {
    public UnrecognizedCriteriaException(){
        super();
    }

    public UnrecognizedCriteriaException(String s){
        super(s);
    }

    public UnrecognizedCriteriaException(String s, Throwable throwable){
        super(s, throwable);
    }

    public UnrecognizedCriteriaException(Throwable throwable){
        super(throwable);
    }
}

