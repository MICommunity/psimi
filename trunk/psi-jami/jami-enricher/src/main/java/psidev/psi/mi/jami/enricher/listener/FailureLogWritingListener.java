package psidev.psi.mi.jami.enricher.listener;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class FailureLogWritingListener extends LogWritingListener{


    public FailureLogWritingListener(File outputFile) throws IOException {
        super(outputFile);
    }

    public void writeFailure(){

    }



}
