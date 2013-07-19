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
@Deprecated
public class CvTermEnricherLogWriterDetailed
        implements CvTermEnricherListener {

    private String buffer;
    private CvTerm lastObject = null;
    private BufferedWriter successWriter , failureWriter;

    public static final String NEW_LINE = "/n";
    public static final String NEW_EVENT = "/t";


    String[] headers = {
            "CvTerm",
            "Old Short Name", "New ShortName",
            "Old Full Name", "New Full Name",
            "Old MI Identifier" , "New MI Identifier",
            "MOD Identifier",
            "PAR Identifier",
            "Identifier",
            "Xref",
            "Synonym"
    };


    public CvTermEnricherLogWriterDetailed(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        if(successFile.canWrite()){
            successWriter = new BufferedWriter( new FileWriter(successFile) );
        } else throw new IOException("Can not write to the file "+successFile);

        if(failureFile.canWrite()){
            failureWriter = new BufferedWriter( new FileWriter(failureFile) );
        } else throw new IOException("Can not write to the file "+failureFile);

        buffer = "";
    }





    private void writeSuccess() throws IOException {
        successWriter.write(buffer + NEW_LINE);
        buffer = "";
    }

    private void writeFailure() throws IOException {
        failureWriter.write(buffer+NEW_LINE);
        buffer = "";
    }

    public void close() throws IOException {
        successWriter.close();
        failureWriter.close();
    }



    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message){
        buffer = buffer +cvTerm.toString()+" MESSAGE: "+message;
        switch(status){
            case SUCCESS:
                try {
                    writeSuccess();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case FAILED:
                try {
                    writeFailure();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        lastObject = null;
    }

    private void checkObject(CvTerm cvTerm){
        if(lastObject == null) lastObject = cvTerm;
        if(lastObject != cvTerm){ // TODO - check that this makes sense
            onCvTermEnriched(lastObject , EnrichmentStatus.FAILED , "Object changed without an exit status");
        }
    }

    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        checkObject(cv);
        buffer = buffer + "SHORTNAME: "+oldShortName+" => "+cv.getShortName()+ NEW_EVENT;
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        checkObject(cv);
        buffer = buffer + "FULLNAME: "+oldFullName+" => "+cv.getFullName()+ NEW_EVENT;
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        checkObject(cv);
        buffer = buffer + "MIIDENTIFIER: "+oldMI+" => "+cv.getMIIdentifier()+ NEW_EVENT;
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
