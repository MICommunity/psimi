package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherStatistcsWriter
        implements CvTermEnricherListener {

    private CvTerm lastObject = null;
    private Writer successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";

    private int updateCount = 0, removedCount = 0, additionCount = 0;

    public CvTermEnricherStatistcsWriter(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        failureWriter = new BufferedWriter( new FileWriter(failureFile) );


        successWriter.write("CvTerm"); successWriter.write(NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(NEW_EVENT);
        successWriter.write("Added"); successWriter.write(NEW_EVENT);
        successWriter.write("File Source");

        failureWriter.write("CvTerm"); failureWriter.write(NEW_EVENT);
        failureWriter.write("File Source"); failureWriter.write(NEW_EVENT);
        failureWriter.write("Message");
    }

    public void close() throws IOException {
       try{
           if(successWriter != null) successWriter.close();
       }
       finally {
           if(failureWriter != null) failureWriter.close();
       }
    }

    private void checkObject(CvTerm cvTerm){
        if(lastObject == null) lastObject = cvTerm;
        else if(lastObject != cvTerm){
            updateCount = 0;
            removedCount = 0;
            additionCount = 0;
            lastObject = cvTerm;
            //onCvTermEnriched(lastObject , EnrichmentStatus.FAILED , "New CvTerm started before last finished without an exit status");
        }
    }


    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message){
        try{
            switch(status){
                case SUCCESS:
                    successWriter.write(NEW_LINE);
                    successWriter.write(cvTerm.toString());
                    successWriter.write(NEW_EVENT);
                    successWriter.write(updateCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(removedCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(additionCount);
                    successWriter.write(NEW_EVENT);
                    if (cvTerm instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) cvTerm;
                        if (context.getSourceLocator() != null)
                            successWriter.write(context.getSourceLocator().toString());
                    }
                    break;

                case FAILED:
                    failureWriter.write(NEW_LINE);
                    failureWriter.write(cvTerm.toString());
                    failureWriter.write(NEW_EVENT);
                    if (cvTerm instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) cvTerm;
                        if (context.getSourceLocator() != null)
                            failureWriter.write(context.getSourceLocator().toString());
                    }
                    failureWriter.write(NEW_EVENT);
                    if(message != null)
                        failureWriter.write(message);

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(); //TODO LOG this
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
