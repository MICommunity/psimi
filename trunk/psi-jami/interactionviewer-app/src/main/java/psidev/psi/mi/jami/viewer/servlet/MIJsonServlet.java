package psidev.psi.mi.jami.viewer.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.commons.MIDataSourceOptionFactory;
import psidev.psi.mi.jami.commons.MIFileAnalyzer;
import psidev.psi.mi.jami.commons.MIFileType;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionEvidenceSource;
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

    public void init() throws ServletException
    {
        PsiJami.initialiseInteractionEvidenceSources();
        fileAnalyzer = new MIFileAnalyzer();
        expansionMethod = new InteractionEvidenceSpokeExpansion();
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
        InteractionEvidenceSource miDataSource = null;
        InteractionWriter interactionWriter = null;
        try {
            URL url = new URL(urlString);

            // first recognize file and create data source
            stream = url.openStream();
            miDataSource = processMIData(urlString, url.openStream(), resp, writer, stream, miDataSource, interactionWriter);
            interactionWriter.end();

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

    private InteractionEvidenceSource processMIData(String request, InputStream dataStream, HttpServletResponse resp, Writer writer, InputStream stream, InteractionEvidenceSource miDataSource, InteractionWriter interactionWriter) throws IOException {
        MIFileType fileType = fileAnalyzer.identifyMIFileTypeFor(stream);
        MIDataSourceOptionFactory optionFactory = MIDataSourceOptionFactory.getInstance();
        MIDataSourceFactory miFactory = MIDataSourceFactory.getInstance();

        switch (fileType){
            case mitab:
               miDataSource = (InteractionEvidenceSource) miFactory.getInteractionSourceWith(optionFactory.getMitabOptions(InteractionObjectCategory.binary_evidence, true, null, dataStream));
               interactionWriter = new MIJsonBinaryWriter(writer, this.fetcher);
               break;
            case psi25_xml:
                miDataSource = (InteractionEvidenceSource) miFactory.getInteractionSourceWith(optionFactory.getXmlOptions(InteractionObjectCategory.binary_evidence, true, dataStream));
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
        interactionWriter.flush();
        resp.setStatus(200);
        return miDataSource;
    }

    private void processFile(String fileParam, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionEvidenceSource miDataSource = null;
        InteractionWriter interactionWriter = null;
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
                        miDataSource = processMIData(filename, item.getInputStream(), resp, writer, stream, miDataSource, interactionWriter);
                    }
                }
            }
            interactionWriter.end();

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
    }
}
