package psidev.psi.mi.jami.viewer.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.OntologyTermCompositeFetcher;
import psidev.psi.mi.jami.bridges.obo.OboOntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ols.CachedOlsOntologyTermFetcher;
import psidev.psi.mi.jami.commons.MIDataSourceOptionFactory;
import psidev.psi.mi.jami.commons.MIFileAnalyzer;
import psidev.psi.mi.jami.commons.MIFileType;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.json.InteractionViewerJson;
import psidev.psi.mi.jami.json.MIJsonOptionFactory;
import psidev.psi.mi.jami.json.MIJsonType;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryPsiXmlCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that can read MI standard files and urls and return MI JSON to an interaction viewer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MIJsonBinaryServlet extends HttpServlet{

    public final static String URL_PARAM="url1";
    public final static String FILE_PARAM="file1";
    public final static String MI_FILE_PATH_PROPERTY="psi.mi.obo.path";
    private MIFileAnalyzer fileAnalyzer;
    private static final Logger logger = Logger.getLogger("MIJsonBinaryServlet");
    private int timeOut = 30000;
    private OntologyTermFetcher fetcher;
    private ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod;

    public void init() throws ServletException
    {
        PsiJami.initialiseAllMIDataSources();
        fileAnalyzer = new MIFileAnalyzer();
        expansionMethod = new SpokeExpansion();

        Properties prop = new Properties();
        String path = null;
        //load a properties file
        try {
            prop.load(MIJsonBinaryServlet.class.getResourceAsStream("/viewer.properties"));
            path = prop.getProperty(MI_FILE_PATH_PROPERTY);
            if (path == null || (path != null && path.length() == 0)){
                OntologyTermCompositeFetcher compositeFetcher = new OntologyTermCompositeFetcher();
                this.fetcher = compositeFetcher;
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MI, new OboOntologyTermFetcher(CvTermUtils.getPsimi(), MIJsonBinaryServlet.class.getResource("/psi-mi25.obo").getFile()));
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MOD, new CachedOlsOntologyTermFetcher());
            }
            else {
                OntologyTermCompositeFetcher compositeFetcher = new OntologyTermCompositeFetcher();
                this.fetcher = compositeFetcher;
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MI, new OboOntologyTermFetcher(CvTermUtils.getPsimi(), path));
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MOD, new CachedOlsOntologyTermFetcher());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "cannot load the property file /viewer.properties where we can find the psi-mi OBO file path. The ontology fetcher will be null.");
        }catch (BridgeFailedException e) {
            logger.log(Level.SEVERE, "cannot load the cached ontology manager for PSI-MOD.");
        }catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "cannot load the psi-mi ontology from the file "+path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("application/json");

        // Actual logic goes here.
        String url = req.getParameter(URL_PARAM);
        if (url != null){
            processURL(url, resp);
        }
        else {
            processFile(req, resp);
        }
    }

    private void processURL(String urlString, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionStream miDataSource = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection1 = url.openConnection();
            connection1.setReadTimeout(timeOut);
            connection1.setConnectTimeout(timeOut);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(timeOut);
            connection.setConnectTimeout(timeOut);

            // first recognize file and create data source
            stream = connection.getInputStream();
            miDataSource = processMIData(urlString, connection1.getInputStream(), resp, writer, stream, miDataSource);

        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "The url " + urlString + " is not a valid url.", e);
            resp.sendError(400, "The url " + urlString + " is not a valid url.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot read the url " + urlString + ".", e);
            resp.sendError(400, "Cannot read the url " + urlString + ".");

        }
        finally {
            // close first stream
            if (stream != null){
                try {
                    stream.close();
                }
                catch (IOException e) {
                    logger.log(Level.SEVERE, "Cannot close the url " + urlString + ".", e);
                }
            }

            // close data source
            if (miDataSource != null){
                try {
                    miDataSource.close();
                } catch (MIIOException e) {
                    logger.log(Level.SEVERE, "Cannot close the mi data source " + urlString + ".", e);
                }
            }
        }
    }

    public OntologyTermFetcher getFetcher() {
        return fetcher;
    }

    public void setFetcher(OntologyTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public ComplexExpansionMethod<Interaction, BinaryInteraction> getExpansionMethod() {
        return expansionMethod;
    }

    public void setExpansionMethod(ComplexExpansionMethod<Interaction, BinaryInteraction> expansionMethod) {
        this.expansionMethod = expansionMethod != null ? expansionMethod : new SpokeExpansion();
    }

    public MIFileAnalyzer getFileAnalyzer() {
        return fileAnalyzer;
    }

    public void setFileAnalyzer(MIFileAnalyzer fileAnalyzer) {
        this.fileAnalyzer = fileAnalyzer != null ? fileAnalyzer : new MIFileAnalyzer();
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    private InteractionStream processMIData(String request, InputStream dataStream, HttpServletResponse resp, Writer writer, InputStream stream, InteractionStream miDataSource) throws IOException {
        InteractionWriter interactionWriter = null;
        try{
            MIFileType fileType = fileAnalyzer.identifyMIFileTypeFor(stream);
            MIDataSourceOptionFactory optionFactory = MIDataSourceOptionFactory.getInstance();
            MIDataSourceFactory miFactory = MIDataSourceFactory.getInstance();

            // initialise writers
            InteractionViewerJson.initialiseAllMIJsonWriters();
            MIJsonOptionFactory jsonOptionFactory = MIJsonOptionFactory.getInstance();
            InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

            switch (fileType){
                case mitab:
                    miDataSource = miFactory.getInteractionSourceWith(optionFactory.getMitabOptions(InteractionCategory.evidence,
                            ComplexType.binary, true, null, dataStream));
                    interactionWriter = writerFactory.getInteractionWriterWith(jsonOptionFactory.getJsonOptions(writer, InteractionCategory.evidence,
                            ComplexType.binary, MIJsonType.binary_only, this.fetcher, null));
                    break;
                case psimi_xml:
                    miDataSource = miFactory.getInteractionSourceWith(optionFactory.getXmlOptions(InteractionCategory.mixed,
                            ComplexType.n_ary, true, null, dataStream, null, new InMemoryPsiXmlCache()));
                    interactionWriter = writerFactory.getInteractionWriterWith(jsonOptionFactory.getJsonOptions(writer, InteractionCategory.mixed,
                            ComplexType.n_ary, MIJsonType.binary_only, this.fetcher, this.expansionMethod));
                    break;
                default:
                    dataStream.close();
                    break;
            }

            if (miDataSource == null){
                logger.log(Level.SEVERE, "The input " + request + " is not a valid MI data source.");
                resp.sendError(400, "The input " + request + " is not a valid MI data source.");
            }
            if (interactionWriter == null){
                logger.log(Level.SEVERE, "The interaction writer does not exist and cannot be instantiated.");
                resp.sendError(400, "The interaction writer does not exist and cannot be instantiated.");
            }

            // then write
            interactionWriter.start();
            interactionWriter.write(miDataSource.getInteractionsIterator());
            interactionWriter.end();
            interactionWriter.flush();
            resp.setStatus(200);
        }
        finally {
            stream.close();
            if (interactionWriter != null){
                interactionWriter.close();
            }
        }
        return miDataSource;
    }

    private void processFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionStream miDataSource = null;
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                // process only files
                if (!item.isFormField()) {
                    // Process form file field (input type="file").
                    String fieldname = item.getFieldName();
                    if (fieldname != null && fieldname.equals(FILE_PARAM)){
                        String filename = FilenameUtils.getName(item.getName());
                        stream = item.getInputStream();
                        // first recognize file and create data source
                        miDataSource = processMIData(filename, item.getInputStream(), resp, writer, stream, miDataSource);
                    }
                }
            }

        } catch (FileUploadException e) {
            logger.log(Level.SEVERE, "The uploaded file is not a valid file.", e);
            resp.sendError(400, "The uploaded file is not a valid file.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "The uploaded file is not a valid file.", e);
            resp.sendError(400, "The uploaded file is not a valid file.");

        }
        finally {
            // close first stream
            if (stream != null){
                try {
                    stream.close();
                }
                catch (IOException e) {
                    logger.log(Level.SEVERE, "Cannot close the file.", e);
                }
            }

            // close data source
            if (miDataSource != null){
                try {
                    miDataSource.close();
                } catch (MIIOException e) {
                    logger.log(Level.SEVERE, "Cannot close the mi data source.", e);
                }
            }
        }
    }

    public void destroy()
    {
        fileAnalyzer = null;
        expansionMethod = null;
        if (this.fetcher instanceof CachedFetcher){
            CachedFetcher cachedFetcher = (CachedFetcher) fetcher;
            cachedFetcher.clearCache();
            cachedFetcher.shutDownCache();
        }
        this.fetcher = null;
    }
}
