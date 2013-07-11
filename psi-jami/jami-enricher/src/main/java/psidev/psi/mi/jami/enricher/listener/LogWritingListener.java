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

    public static final String NEW_LINE = "/n";
    public static final String NEW_ENTRY = "/t";

    public LogWritingListener(File outputFile) throws IOException {
        if(outputFile == null) throw new IllegalArgumentException("Provided a null file to write to.");

        if(outputFile.canWrite()){
            bufferedWriter = new BufferedWriter( new FileWriter(outputFile) );
        }
    }

    protected void write(String string) throws IOException {
        bufferedWriter.write(string);
    }



    public void close() throws IOException {
        bufferedWriter.close();
    }

}
