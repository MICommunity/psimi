package psidev.psi.mi.jami.enricher.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public abstract class LogWritingListener implements EnricherListener{

    private BufferedWriter bufferedWriter;

    public LogWritingListener(File outputFile) throws IOException {
        if(outputFile == null) throw new IllegalArgumentException("Provided a null file to write to.");

        if(outputFile.canWrite()){
            bufferedWriter = new BufferedWriter( new FileWriter(outputFile) );
        }
    }

    public void test() throws IOException {
        bufferedWriter.write("test");
    }



    public void close() throws IOException {
        bufferedWriter.close();
    }

}
