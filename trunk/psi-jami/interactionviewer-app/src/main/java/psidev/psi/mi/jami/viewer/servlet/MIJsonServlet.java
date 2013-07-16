package psidev.psi.mi.jami.viewer.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.CachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.commons.MIDataSourceOptionFactory;
import psidev.psi.mi.jami.commons.MIFileAnalyzer;
import psidev.psi.mi.jami.commons.MIFileType;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.json.MIJsonBinaryWriter;
import psidev.psi.mi.jami.json.MIJsonWriter;
import psidev.psi.mi.jami.model.InteractionEvidence;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that can read MI standard files and urls and return MI JSON to an interaction viewer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MIJsonServlet extends HttpServlet{

    private OntologyTermFetcher fetcher;
    private ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod;
    public final static String URL_PARAM="url";
    public final static String FILE_PARAM="file";
    private MIFileAnalyzer fileAnalyzer;
    private static final Logger logger = Logger.getLogger("MIJsonServlet");
    private int timeOut = 30000;

    public void init() throws ServletException
    {
        PsiJami.initialiseInteractionEvidenceSources();
        fileAnalyzer = new MIFileAnalyzer();
        expansionMethod = new InteractionEvidenceSpokeExpansion();
        /*try {
            this.fetcher = new CachedOntologyOLSFetcher();
        } catch (BridgeFailedException e) {
            logger.log(Level.SEVERE, "cannot load the cached ontology manager.");
        }*/
        this.fetcher = null;
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
            String fileParameter = req.getParameter(FILE_PARAM);
            if (fileParameter == null){
                resp.sendError(400, "Could not find the expected 'url' parameter or 'file' parameter in the request.");
            }
            else{
                processFile(fileParameter, req, resp);
            }
        }
    }

    private void processURL(String urlString, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionSource miDataSource = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection1 = url.openConnection();
            connection1.setReadTimeout(5000);
            connection1.setConnectTimeout(5000);
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

    protected OntologyTermFetcher getFetcher() {
        return fetcher;
    }

    protected void setFetcher(OntologyTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> getExpansionMethod() {
        return expansionMethod;
    }

    protected void setExpansionMethod(ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) {
        this.expansionMethod = expansionMethod;
    }

    protected MIFileAnalyzer getFileAnalyzer() {
        return fileAnalyzer;
    }

    protected void setFileAnalyzer(MIFileAnalyzer fileAnalyzer) {
        this.fileAnalyzer = fileAnalyzer;
    }

    protected int getTimeOut() {
        return timeOut;
    }

    protected void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    private InteractionSource processMIData(String request, InputStream dataStream, HttpServletResponse resp, Writer writer, InputStream stream, InteractionSource miDataSource) throws IOException {
        MIFileType fileType = fileAnalyzer.identifyMIFileTypeFor(stream);
        MIDataSourceOptionFactory optionFactory = MIDataSourceOptionFactory.getInstance();
        MIDataSourceFactory miFactory = MIDataSourceFactory.getInstance();
        InteractionWriter interactionWriter = null;

        switch (fileType){
            case mitab:
               miDataSource = miFactory.getInteractionSourceWith(optionFactory.getMitabOptions(InteractionObjectCategory.binary_evidence, true, null, dataStream));
               interactionWriter = new MIJsonBinaryWriter(writer, this.fetcher);
               break;
            case psi25_xml:
                miDataSource = miFactory.getInteractionSourceWith(optionFactory.getXmlOptions(InteractionObjectCategory.binary_evidence, true, dataStream));
                interactionWriter = new MIJsonWriter(writer, this.fetcher, this.expansionMethod);
                break;
            default:
                dataStream.close();
                break;
        }

        if (miDataSource == null){
            logger.log(Level.SEVERE, "The input " + request + " is not a valid MI data source.");
            resp.sendError(400, "The input " + request + " is not a valid MI data source.");
        }

        // then write
        interactionWriter.start();
        interactionWriter.write(miDataSource.getInteractionsIterator());
        interactionWriter.end();
        interactionWriter.flush();
        resp.setStatus(200);
        return miDataSource;
    }

    private void processFile(String fileParam, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionSource miDataSource = null;
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                // process only files
                if (!item.isFormField()) {
                    // Process form file field (input type="file").
                    String fieldname = item.getFieldName();
                    if (fieldname != null && fieldname.equals(fileParam)){
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
