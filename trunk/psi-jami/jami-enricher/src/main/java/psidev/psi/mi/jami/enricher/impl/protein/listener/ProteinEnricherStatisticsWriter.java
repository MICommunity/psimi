package psidev.psi.mi.jami.enricher.impl.protein.listener;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ProteinEnricherStatisticsWriter
        implements ProteinEnricherListener {

    private Protein lastObject = null;
    private Writer successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";

    private int updateCount = 0, removedCount = 0, additionCount = 0;

    public ProteinEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        failureWriter = new BufferedWriter( new FileWriter(failureFile) );


        successWriter.write("Protein"); successWriter.write(NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(NEW_EVENT);
        successWriter.write("Added"); successWriter.write(NEW_EVENT);
        successWriter.write("File Source");

        failureWriter.write("Protein"); failureWriter.write(NEW_EVENT);
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

    private void checkObject(Protein protein){
        if(lastObject == null) lastObject = protein;
        else if(lastObject != protein){
            updateCount = 0;
            removedCount = 0;
            additionCount = 0;
            lastObject = protein;
            //onCvTermEnriched(lastObject , EnrichmentStatus.FAILED , "New CvTerm started before last finished without an exit status");
        }
    }


    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message){
        try{
            switch(status){
                case SUCCESS:
                    successWriter.write(NEW_LINE);
                    successWriter.write(protein.toString());
                    successWriter.write(NEW_EVENT);
                    successWriter.write(updateCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(removedCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(additionCount);
                    successWriter.write(NEW_EVENT);
                    if (protein instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) protein;
                        if (context.getSourceLocator() != null)
                            successWriter.write(context.getSourceLocator().toString());
                    }
                    break;

                case FAILED:
                    failureWriter.write(NEW_LINE);
                    failureWriter.write(protein.toString());
                    failureWriter.write(NEW_EVENT);
                    if (protein instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) protein;
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
