package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class FeatureEnricherStatisticsWriter
        implements FeatureEnricherListener {

    private Feature lastObject = null;
    private Writer successWriter , failureWriter;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";

    private int updateCount = 0, removedCount = 0, additionCount = 0;

    public FeatureEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        failureWriter = new BufferedWriter( new FileWriter(failureFile) );


        successWriter.write("Feature"); successWriter.write(NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(NEW_EVENT);
        successWriter.write("Added"); successWriter.write(NEW_EVENT);
        successWriter.write("File Source");

        failureWriter.write("Feature"); failureWriter.write(NEW_EVENT);
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

    private void checkObject(Feature feature){
        if(lastObject == null) lastObject = feature;
        else if(lastObject != feature){
            updateCount = 0;
            removedCount = 0;
            additionCount = 0;
            lastObject = feature;
        }
    }


    public void onFeatureEnriched(Feature feature, EnrichmentStatus status, String message){
        try{
            switch(status){
                case SUCCESS:
                    successWriter.write(NEW_LINE);
                    successWriter.write(feature.toString());
                    successWriter.write(NEW_EVENT);
                    successWriter.write(updateCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(removedCount);
                    successWriter.write(NEW_EVENT);
                    successWriter.write(additionCount);
                    successWriter.write(NEW_EVENT);
                    if (feature instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) feature;
                        if (context.getSourceLocator() != null)
                            successWriter.write(context.getSourceLocator().toString());
                    }
                    break;

                case FAILED:
                    failureWriter.write(NEW_LINE);
                    failureWriter.write(feature.toString());
                    failureWriter.write(NEW_EVENT);
                    if (feature instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) feature;
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


    public void onShortNameUpdate(Feature feature, String oldShortName) {
        checkObject(feature);
        updateCount++;
    }

    public void onFullNameUpdate(Feature feature, String oldFullName) {
        checkObject(feature);
        updateCount++;
    }

    public void onInterproUpdate(Feature feature, String oldInterpro) {
        checkObject(feature);
        updateCount++;
    }

    public void onTypeAdded(Feature feature, CvTerm oldType) {
        checkObject(feature);
        additionCount++;
    }

    public void onAddedIdentifier(Feature feature, Xref added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedIdentifier(Feature feature, Xref removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedXref(Feature feature, Xref added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedXref(Feature feature, Xref removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedAnnotation(Feature feature, Annotation added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedAnnotation(Feature feature, Annotation removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedRange(Feature feature, Range added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedRange(Feature feature, Range removed) {
        checkObject(feature);
        removedCount++;
    }
}
