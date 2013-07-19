package psidev.psi.mi.jami.enricher.impl.organism.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class OrganismEnricherStatisticsWriter
        implements OrganismEnricherListener {

    private Organism lastObject = null;
    private Writer successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";

    private int updateCount = 0, removedCount = 0, additionCount = 0;

    public OrganismEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        failureWriter = new BufferedWriter( new FileWriter(failureFile) );


        successWriter.write("Organism"); successWriter.write(NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(NEW_EVENT);
        successWriter.write("Added"); successWriter.write(NEW_EVENT);
        successWriter.write("File Source");

        failureWriter.write("Organism"); failureWriter.write(NEW_EVENT);
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

    private void checkObject(Organism organism){
        if(lastObject == null) lastObject = organism;
        else if(lastObject != organism){
            updateCount = 0;
            removedCount = 0;
            additionCount = 0;
            lastObject = organism;
        }
    }


    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message){
        try{
            switch(status){
                case SUCCESS:
                    successWriter.write(NEW_LINE);
                    successWriter.write(organism.toString());
                    successWriter.write(NEW_EVENT);
                    successWriter.write(updateCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(removedCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(additionCount);
                    successWriter.write(NEW_EVENT);
                    if (organism instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) organism;
                        if (context.getSourceLocator() != null)
                            successWriter.write(context.getSourceLocator().toString());
                    }
                    break;

                case FAILED:
                    failureWriter.write(NEW_LINE);
                    failureWriter.write(organism.toString());
                    failureWriter.write(NEW_EVENT);
                    if (organism instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) organism;
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

    public void onCommonNameUpdate(Organism organism, String oldCommonName) {
        checkObject(organism);
        updateCount++;
    }

    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
        checkObject(organism);
        updateCount++;
    }

    public void onTaxidUpdate(Organism organism, String oldTaxid) {
        checkObject(organism);
        updateCount++;
    }

    public void onAddedAlias(Organism organism, Alias added) {
        checkObject(organism);
        additionCount++;
    }

    public void onRemovedAlias(Organism organism, Alias removed) {
        checkObject(organism);
        removedCount++;
    }
}
