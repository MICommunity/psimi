package psidev.psi.mi.jami.bridges.remapper.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Protein;

import java.io.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 23/07/13
 */
public class ProteinRemapperStatisticsWriter implements ProteinRemapperListener {


    protected static final Logger log = LoggerFactory.getLogger(ProteinRemapperStatisticsWriter.class.getName());

    private Writer writer ;

    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";

    //protected int updateCount = 0, removedCount = 0, additionCount = 0;


    public ProteinRemapperStatisticsWriter(String fileName) throws IOException {
        if(fileName == null || fileName.length() == 0)
            throw new IllegalArgumentException("Provided a no file to write to.");


        writer = new BufferedWriter( new FileWriter(new File(fileName)) );


        writer.write("Protein"); writer.write(NEW_EVENT);
        writer.write("Status"); writer.write(NEW_EVENT);
        writer.write("Message"); writer.write(NEW_EVENT);
        writer.write("File Source");
        writer.flush();


    }

    /**
     * Close both files.
     * @throws IOException
     */
    public void close() throws IOException {
        if(writer != null) writer.close();
    }


    public void onRemappingSuccessful(Protein p, Collection<String> report) {
        try{
            writer.write(NEW_LINE);
            writer.write(p.toString());
            writer.write(NEW_EVENT);
            writer.write("Success");
            if(report.iterator().hasNext())
                writer.write(report.iterator().next());
            writer.write(NEW_EVENT);
            if (p instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) p;
                if (context.getSourceLocator() != null)
                    writer.write(context.getSourceLocator().toString());
            }
            writer.flush();
        } catch (IOException e) {
            log.warn(e.getMessage());
            e.printStackTrace(); //TODO LOG this
        }
    }



    public void onRemappingFailed(Protein p, Collection<String> report) {
        try{
            writer.write(NEW_LINE);
            writer.write(p.toString()); writer.write(NEW_EVENT);
            writer.write("Failed");     writer.write(NEW_EVENT);
            if(report.iterator().hasNext())
                writer.write(report.iterator().next());
            writer.write(NEW_EVENT);
            if (p instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) p;
                if (context.getSourceLocator() != null)
                    writer.write(context.getSourceLocator().toString());
            }
            writer.flush();
        } catch (IOException e) {
            log.warn(e.getMessage());
            e.printStackTrace(); //TODO LOG this
        }
    }
}
