package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherLogWriterBasic
        implements CvTermEnricherListener {

    // private String buffer;
    private CvTerm lastObject = null;
    private BufferedWriter successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";


    private int updateCount = 0, removedCount = 0, additionCount = 0;// ,
            //successCount = 0, failureCount = 0;

    public CvTermEnricherLogWriterBasic(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        //if (!successFile.exists()) successFile.createNewFile();

        //if(successFile.canWrite()){
            successWriter = new BufferedWriter( new FileWriter(successFile) );
        //} else throw new IOException("Can not write to the file "+successFile);

        //if (!successFile.exists()) successFile.createNewFile();

        //if(failureFile.canWrite()){
            failureWriter = new BufferedWriter( new FileWriter(failureFile) );
       // } else throw new IOException("Can not write to the file "+failureFile);

        String header = "CvTerm"+NEW_EVENT+"Updated"+NEW_EVENT+"Removed"+NEW_EVENT+"Added"+NEW_EVENT+"Message";
        successWriter.write(header);
        failureWriter.write(header);
    }

    public void close() throws IOException {
        if(successWriter != null) successWriter.close();
        if(failureWriter != null) failureWriter.close();
    }

    private void checkObject(CvTerm cvTerm){
        if(lastObject == null) lastObject = cvTerm;
        else if(lastObject != cvTerm){ // TODO - check that this makes sense
            onCvTermEnriched(lastObject , EnrichmentStatus.FAILED , "New CvTerm started before last finished without an exit status");
        }
    }


    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message){
        try{
            BufferedWriter writer = null;
            switch(status){
                case SUCCESS: writer = successWriter; break;
                case FAILED: writer = failureWriter; break;
            }

            if(writer != null) {
                writer.write(NEW_LINE + cvTerm.toString());
                writer.write(NEW_EVENT + updateCount);
                writer.write(NEW_EVENT + removedCount);
                writer.write(NEW_EVENT + additionCount);
                if(message != null) writer.write(NEW_EVENT + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateCount = 0;
        removedCount = 0;
        additionCount = 0;
        lastObject = null;
    }


    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        checkObject(cv);
        updateCount ++;
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        checkObject(cv);
        updateCount ++;
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        checkObject(cv);
        updateCount ++;
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        checkObject(cv);
        updateCount ++;
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        checkObject(cv);
        updateCount ++;
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {

        checkObject(cv);
        removedCount ++;
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        checkObject(cv);
        removedCount ++;
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        checkObject(cv);
        removedCount ++;
    }
}
