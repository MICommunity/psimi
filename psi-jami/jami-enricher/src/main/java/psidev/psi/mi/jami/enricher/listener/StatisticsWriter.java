package psidev.psi.mi.jami.enricher.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;

import java.io.*;

/**
 * Listens to the enrichment, making two lists of elements:
 * those which have been successfully enriched and those which have failed.
 * <p>
 * For the successfully enriched elements, only those where changes were made are featured.
 * Statistics are also given for the number of updates and added or removed fields.
 *
 * In both cases, if the object has a fileContext,this will be included for easier identification.
 *
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  09/07/13
 * @param <T>   The type of JAMI object that is being enriched.
 */
public abstract class StatisticsWriter<T> implements EnricherListener<T>{

    private static final Logger log = LoggerFactory.getLogger(StatisticsWriter.class.getName());

    /* The last object that was changed */
    private T lastObject = null;

    /* Writers for successes and failures.  */
    private Writer successWriter;
    private Writer failureWriter;

    /* Counters for the changes made to the current object. */
    private int updateCount = 0;
    private int removedCount = 0;
    private int additionCount = 0;

    public static final String SUCCESS_SUFFIX="_success.csv";
    public static final String FAILURE_SUFFIX="_failed.csv";

    private String fileName;

    /**
     * A constructor which takes the given filename and appends success and failed for the two lists.
     * @param fileName      The seed for name of the files to record enrichments. Can not be null.
     * @throws IOException
     */
    public StatisticsWriter(String fileName) throws IOException {
        this(new File(fileName+SUCCESS_SUFFIX), new File(fileName+FAILURE_SUFFIX));
    }

    /**
     * A constructor setting the file type
     * @param successFileName   The name of the file to record successful enrichments. Can not be null.
     * @param failureFileName   The name of file to record failed enrichments. Can not be null.
     * @throws IOException
     */
    public StatisticsWriter(String successFileName, String failureFileName) throws IOException {
        this(new File(successFileName), new File(failureFileName));
    }



    /**
     * Opens the files for successful enrichments and failed enrichments.
     *
     * @param successFile   The file to record successful enrichments.
     * @param failureFile   The file to record failed enrichments.
     * @throws IOException
     */
    public StatisticsWriter(File successFile, File failureFile) throws IOException {
        /**
         * Removing the following statement would allow the creation of a stats writer with a null file.
         * You may want to do this in future to bypass the creation of empty success stats files,
         * caused by fetcher-less enrichers which generate no stats and therefore have nothing to record..
         * */
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        successWriter.write("File Source"); successWriter.write(EnricherUtils.NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(EnricherUtils.NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(EnricherUtils.NEW_EVENT);
        successWriter.write("Added");

        failureWriter = new BufferedWriter( new FileWriter(failureFile) );
        failureWriter.write("File Source"); failureWriter.write(EnricherUtils.NEW_EVENT);
        failureWriter.write("Message");
        failureWriter.flush();
    }

    /**
     * Close both files.
     * @throws IOException
     */
    public void close() throws IOException {
        try{
            if(successWriter != null) successWriter.close();
        }
        finally {
            if(failureWriter != null) failureWriter.close();
        }
        this.lastObject = null;
        this.updateCount =0;
        this.additionCount = 0;
        this.removedCount = 0;
    }

    /**
     * Compares the object to the last object enriched.
     * If they are different, the new object is taken and all stats reset.
     * @param obj   The object to compare to the last object changed.
     */
    protected void checkObject(T obj){
        if(lastObject == null) lastObject = obj;
        else if(lastObject != obj){
            updateCount = 0;
            removedCount = 0;
            additionCount = 0;
            lastObject = obj;
        }
    }

    /**
     * Writes to the appropriate file when the enrichment is complete.
     * @param obj       The object which has finished enriching.
     * @param status    The exit status of the finished enrichment.
     * @param message   The message given to accompany the last enrichment.
     */
    public void onEnrichmentComplete(T obj, EnrichmentStatus status, String message) {
        checkObject(obj);

        try{
            String fileSource = obj.toString();
            switch(status){
                case SUCCESS:
                    if(updateCount == 0 && removedCount == 0 && additionCount == 0)
                        break;
                    successWriter.write(EnricherUtils.NEW_EVENT);
                    successWriter.write(Integer.toString(updateCount));
                    successWriter.write(EnricherUtils.NEW_EVENT);
                    successWriter.write(Integer.toString(removedCount));
                    successWriter.write(EnricherUtils.NEW_EVENT);
                    successWriter.write(Integer.toString(additionCount));
                    successWriter.write(EnricherUtils.NEW_EVENT);
                    if (obj instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) obj;
                        if (context.getSourceLocator() != null)
                            fileSource = context.getSourceLocator().toString();
                    }
                    successWriter.write(fileSource);
                    successWriter.flush();
                    break;

                case FAILED:
                    failureWriter.write(EnricherUtils.NEW_EVENT);
                    if (obj instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) obj;
                        if (context.getSourceLocator() != null)
                            fileSource = context.getSourceLocator().toString();
                    }
                    failureWriter.write(fileSource);
                    failureWriter.write(EnricherUtils.NEW_EVENT);
                    if(message != null)
                        failureWriter.write(message);
                    else
                        failureWriter.write(EnricherUtils.BLANK_SPACE);
                    failureWriter.flush();
                    break;
            }
        } catch (IOException e) {
            log.error("Cannot write enrichment event", e.getMessage());
        }

        updateCount = 0;
        removedCount = 0;
        additionCount = 0;
        lastObject = null;
    }

    public void onEnrichmentError(T obj, String message, Exception e) {
        checkObject(obj);

        try{
            String fileSource = obj.toString();
            failureWriter.write(EnricherUtils.NEW_EVENT);
            if (obj instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) obj;
                if (context.getSourceLocator() != null)
                    fileSource = context.getSourceLocator().toString();
            }
            failureWriter.write(fileSource);
            failureWriter.write(EnricherUtils.NEW_EVENT);
            if(message != null) {
                failureWriter.write(message);
                failureWriter.write(": ");
                failureWriter.write(e.getClass().getCanonicalName());
            }
            else {
                failureWriter.write(e.getClass().getCanonicalName());
                failureWriter.write(": ");
                failureWriter.write(e.getMessage());
            }
            failureWriter.flush();
        } catch (IOException e2) {
            log.error("Cannot write failed enrichment event", e2.getMessage());
        }

        updateCount = 0;
        removedCount = 0;
        additionCount = 0;
        lastObject = null;
    }

    protected Writer getSuccessWriter() {
        return successWriter;
    }

    protected Writer getFailureWriter() {
        return failureWriter;
    }

    protected void incrementUpdateCount() {
        updateCount++;
    }

    protected void incrementRemovedCount() {
        removedCount++;
    }

    protected void incrementAdditionCount() {
        additionCount++;
    }
}
