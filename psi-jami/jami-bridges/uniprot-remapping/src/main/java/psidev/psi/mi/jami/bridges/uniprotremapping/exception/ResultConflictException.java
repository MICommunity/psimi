package psidev.psi.mi.jami.bridges.uniprotremapping.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 15:21
 */
public class ResultConflictException extends Exception {

    public ResultConflictException(){
        super();
    }

    public ResultConflictException(String msg){
        super(msg);
    }

    public ResultConflictException(Throwable throwable){
        super(throwable);
    }

    public ResultConflictException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}
