package psidev.psi.mi.jami.enricher.impl.protein.listener;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ProteinEnricherLogWriterBasic
        implements ProteinEnricherListener {


    private Protein lastObject = null;
    private BufferedWriter successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";


    private int updateCount = 0, removedCount = 0, additionCount = 0;

    public ProteinEnricherLogWriterBasic(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );

        failureWriter = new BufferedWriter( new FileWriter(failureFile) );

        String header = "Protein"+NEW_EVENT+"Updated"+NEW_EVENT+"Removed"+NEW_EVENT+"Added"+NEW_EVENT+"Message";
        successWriter.write(header);
        failureWriter.write(header);
    }

    public void close() throws IOException {
        if(successWriter != null) successWriter.close();
        if(failureWriter != null) failureWriter.close();
    }

    private void checkObject(Protein protein){
        if(lastObject == null) lastObject = protein;
        else if(lastObject != protein){ // TODO - check that this makes sense
            onProteinEnriched(lastObject, EnrichmentStatus.FAILED, "New Protein started before last finished without an exit status");
        }
    }


    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message){
        try{
            BufferedWriter writer = null;
            switch(status){
                case SUCCESS: writer = successWriter; break;
                case FAILED: writer = failureWriter; break;
            }

            if(writer != null) {
                writer.write(NEW_LINE + protein.toString());
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


    public void onProteinRemapped(Protein protein, String oldUniprot) {
        checkObject(protein);
        updateCount ++;
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        checkObject(protein);
        updateCount ++;
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        checkObject(protein);
        updateCount ++;
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        checkObject(protein);
        updateCount ++;
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        checkObject(protein);
        updateCount ++;
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onAddedInteractorType(Protein protein) {
        checkObject(protein);
        additionCount ++;
    }

    public void onAddedOrganism(Protein protein) {
        checkObject(protein);
        additionCount ++;
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedXref(Protein protein, Xref added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedAlias(Protein protein, Alias added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        checkObject(protein);
        removedCount ++;
    }
}
