package psidev.psi.mi.jami.enricher.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.datasource.FileSourceContext;

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
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  09/07/13
 */
public abstract class StatisticsWriter<T> implements EnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(StatisticsWriter.class.getName());

    /**
     * The last object that was changed
     */
    protected T lastObject = null;
    /**
     * Writers for successes and failures.
     */
    private Writer successWriter , failureWriter;

    /**
     * Character to be used to separate lines.
     */
    public static final String NEW_LINE = "\n";
    /**
     * Character to be used to separate fields
     */
    public static final String NEW_EVENT = "\t";

    /**
     * Counters for the changes made to the current object.
     */
    protected int updateCount = 0, removedCount = 0, additionCount = 0;

    /**
     * A constructor which by default names the files by their object type.
     * @param jamiObject        The name of the object type to be used in the file
     *                          and as the seed for the file names.
     * @throws IOException
     */
    public StatisticsWriter(String jamiObject) throws IOException {
        this(jamiObject, jamiObject);
    }

    /**
     * A constructor which takes the given filename and appends success and failed for the two lists.
     * @param fileName      The seed for name of the files to record enrichments. Can not be null.
     * @param jamiObject    The name of the object type being enriched to be used in the file.
     * @throws IOException
     */
    public StatisticsWriter(String fileName, String jamiObject) throws IOException {
        this(new File("success_"+fileName), new File("failed_"+fileName), jamiObject);
    }

    /**
     * A constructor setting the file type
     * @param successFileName   The name of the file to record successful enrichments. Can not be null.
     * @param failureFileName   The name of file to record failed enrichments. Can not be null.
     * @param jamiObject        The name of the object type being enriched to be used in the file.
     * @throws IOException
     */
    public StatisticsWriter(String successFileName, String failureFileName, String jamiObject) throws IOException {
        this(new File(successFileName), new File(failureFileName), jamiObject);
    }



    /**
     * Opens the files for successful enrichments and failed enrichments.
     *
     * @param successFile   The file to record successful enrichments.
     * @param failureFile   The file to record failed enrichments.
     * @param jamiObject    The name of the objectType being enriched
     * @throws IOException
     */
    public StatisticsWriter(File successFile, File failureFile, String jamiObject) throws IOException {
        if(successFile == null || failureFile == null)
            throw new IllegalArgumentException("Provided a null file to write to.");

        successWriter = new BufferedWriter( new FileWriter(successFile) );
        successWriter.write(jamiObject); successWriter.write(NEW_EVENT);
        successWriter.write("Updated"); successWriter.write(NEW_EVENT);
        successWriter.write("Removed"); successWriter.write(NEW_EVENT);
        successWriter.write("Added"); successWriter.write(NEW_EVENT);
        successWriter.write("File Source");
        successWriter.flush();

        failureWriter = new BufferedWriter( new FileWriter(failureFile) );
        failureWriter.write(jamiObject); failureWriter.write(NEW_EVENT);
        failureWriter.write("File Source"); failureWriter.write(NEW_EVENT);
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
    protected void onObjectEnriched(T obj, EnrichmentStatus status, String message){
        checkObject(obj);

        try{
            switch(status){
                case SUCCESS:
                    if(updateCount == 0 && removedCount == 0 && additionCount == 0)
                        break;
                    if(successWriter == null) break;

                    successWriter.write(NEW_LINE);
                    successWriter.write(obj.toString());
                    successWriter.write(NEW_EVENT);
                    successWriter.write(Integer.toString(updateCount));
                    successWriter.write(NEW_EVENT);
                    successWriter.write(Integer.toString(removedCount));
                    successWriter.write(NEW_EVENT);
                    successWriter.write(Integer.toString(additionCount));
                    successWriter.write(NEW_EVENT);
                    if (obj instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) obj;
                        if (context.getSourceLocator() != null)
                            successWriter.write(context.getSourceLocator().toString());
                    }
                    successWriter.flush();
                    break;

                case FAILED:
                    if(failureWriter == null) break;

                    failureWriter.write(NEW_LINE);
                    failureWriter.write(obj.toString());
                    failureWriter.write(NEW_EVENT);
                    if (obj instanceof FileSourceContext){
                        FileSourceContext context = (FileSourceContext) obj;
                        if (context.getSourceLocator() != null)
                            failureWriter.write(context.getSourceLocator().toString());
                    }
                    failureWriter.write(NEW_EVENT);
                    if(message != null)
                        failureWriter.write(message);
                    failureWriter.flush();
                    break;
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
            e.printStackTrace(); //TODO LOG this
        }

        updateCount = 0;
        removedCount = 0;
        additionCount = 0;
        lastObject = null;
    }
}
