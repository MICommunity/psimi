package psidev.psi.mi.jami.crosslink.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import psidev.psi.mi.jami.commons.MIWriterOptionFactory;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.crosslink.CrossLinkCsv;
import psidev.psi.mi.jami.crosslink.CrossLinkCsvOptionFactory;
import psidev.psi.mi.jami.crosslink.CsvType;
import psidev.psi.mi.jami.crosslink.listener.CsvParserLogger;
import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.PsiXmlVersion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that can read crosslink CSV file and export in a standard format
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MICrosslinkServlet extends HttpServlet{

    public final static String ORGANISM_NAME_PARAM="organismName";
    public final static String ORGANISM_TAXID_PARAM="organismTaxId";
    public final static String PUBLICATION_PARAM="publicationId";
    public final static String SOURCE_NAME_PARAM="institutionName";
    public final static String SOURCE_MI_PARAM="institutionMI";
    public final static String OUTPUT_PARAM="output";
    public final static String FILE_PARAM="file";
    public final static String XML25_OUTPUT="xml25_mix";
    public final static String XML25_NARY_OUTPUT="xml25_single";
    public final static String XML25_BINARY_OUTPUT="xml25_binary";
    public final static String TAB25_OUTPUT="tab25";
    public final static String TAB26_OUTPUT="tab26";
    public final static String TAB27_OUTPUT="tab27";
    private static final Logger logger = Logger.getLogger("MICrosslinkServlet");
    private int timeOut = 30000;

    public void init() throws ServletException
    {
        PsiJami.initialiseAllInteractionWriters();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Actual logic goes here.
        processFile(req, resp);
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    private void processMIData(String request, HttpServletResponse resp,
                               Writer writer, InputStream stream,
                               Organism organism,
                               Publication publication,
                               String output) throws IOException {
        InteractionSource csvSource=null;
        InteractionWriter interactionWriter = null;
        try{

            // initialise writers
            CrossLinkCsv.initialiseAllCrossLinkCsvSources();
            CrossLinkCsvOptionFactory csvOptionFactory = CrossLinkCsvOptionFactory.getInstance();
            MIDataSourceFactory sourceFactory = MIDataSourceFactory.getInstance();

            InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();
            MIWriterOptionFactory optionsFactory = MIWriterOptionFactory.getInstance();

            Map<String, Object> options;
            // xml 2.5
            if (output.equalsIgnoreCase(XML25_OUTPUT)){
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.mix, ComplexType.n_ary, null, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for xml25_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for xml25_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".xml");
                options = optionsFactory.getDefaultXmlOptions(writer, InteractionCategory.evidence, ComplexType.n_ary,
                        PsiXmlType.compact, PsiXmlVersion.v2_5_4);
            }
            else if (output.equalsIgnoreCase(XML25_BINARY_OUTPUT)){
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.binary_only, ComplexType.binary, false, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for xml25_binary_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for xml25_binary_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".xml");
                options = optionsFactory.getDefaultXmlOptions(writer, InteractionCategory.evidence, ComplexType.binary,
                        PsiXmlType.compact, PsiXmlVersion.v2_5_4);
            }
            else if (output.equalsIgnoreCase(XML25_NARY_OUTPUT)){
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.single_nary, ComplexType.n_ary, null, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for xml25_nary_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for xml25_nary_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".xml");
                options = optionsFactory.getDefaultXmlOptions(writer, InteractionCategory.evidence, ComplexType.n_ary,
                        PsiXmlType.compact, PsiXmlVersion.v2_5_4);
            }
            // mitab 2.5
            else if (output.equalsIgnoreCase(TAB25_OUTPUT)){
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.binary_only, ComplexType.binary, false, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for tab25_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for tab25_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".txt");
                options = optionsFactory.getMitabOptions(writer, InteractionCategory.evidence, ComplexType.binary,
                        null, true, MitabVersion.v2_5, false);
            }
            // mitab 2.6
            else if (output.equalsIgnoreCase(TAB26_OUTPUT)){
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.binary_only, ComplexType.binary, false, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for tab26_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for tab26_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".txt");
                options = optionsFactory.getMitabOptions(writer, InteractionCategory.evidence, ComplexType.binary,
                        null, true, MitabVersion.v2_6, false);
            }
            // mitab 2.7
            else {
                csvSource = (InteractionSource)sourceFactory.getInteractionSourceWith(csvOptionFactory.
                        getCsvOptions(CsvType.binary_only, ComplexType.binary, false, new CsvParserLogger(), stream));

                if (csvSource == null){
                    logger.log(Level.SEVERE, "Cannot find a Crosslink csv datasource for tab27_output.");
                    resp.sendError(400, "Cannot find a Crosslink csv datasource for tab27_output.");
                }

                // set attachment header
                resp.setHeader("Content-disposition","attachment; filename="+request+".txt");
                options = optionsFactory.getMitabOptions(writer, InteractionCategory.evidence, ComplexType.binary,
                        null, true, MitabVersion.v2_7, false);
            }

            interactionWriter = writerFactory.getInteractionWriterWith(options);

            if (interactionWriter == null){
                logger.log(Level.SEVERE, "The output " + request + " is not a valid MI output.");
                resp.sendError(400, "The output " + request + " is not a valid MI output.");
            }

            // then write
            interactionWriter.start();

            Collection interactions = csvSource.getInteractions();
            for (Object obj : interactions){
                InteractionEvidence interaction = (InteractionEvidence)obj;
                interaction.getExperiment().setPublication(publication);
                interaction.getExperiment().setHostOrganism(organism);
            }
            interactionWriter.write(interactions);

            interactionWriter.flush();


            interactionWriter.end();
            resp.setStatus(200);
        }
        finally {
            if (csvSource != null){
                csvSource.close();
            }
            stream.close();
            if (interactionWriter != null){
                interactionWriter.close();
            }
        }
    }

    private void processFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();

        InputStream stream = null;
        InteractionStream miDataSource = null;
        Organism organism = null;
        Publication publication = null;
        String output = null;

        try {
            String filename = null;
            String organismName = null;
            int organismTaxid=-3;
            String publicationId = null;
            String sourceName = null;
            String sourceMI = null;

            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem item : items) {
                // process only files
                if (!item.isFormField()) {
                    // Process form file field (input type="file").
                    String fieldname = item.getFieldName();
                    if (fieldname != null && fieldname.equals(FILE_PARAM)){
                        filename = FilenameUtils.getName(item.getName());
                        stream = item.getInputStream();
                    }
                }
                else{
                    String fieldname = item.getFieldName();
                    if (fieldname != null && fieldname.equals(ORGANISM_NAME_PARAM)){
                        organismName = item.getString();
                    }
                    else if (fieldname != null && fieldname.equals(ORGANISM_TAXID_PARAM)){
                        try{
                            organismTaxid = Integer.parseInt(item.getString());
                        }
                        catch (NumberFormatException e){
                            resp.sendError(400, "The host organism taxId is not a valid taxId.");
                            return;
                        }
                    }
                    else if (fieldname != null && fieldname.equals(PUBLICATION_PARAM)){
                        publicationId = item.getString();
                    }
                    else if (fieldname != null && fieldname.equals(SOURCE_NAME_PARAM)){
                        sourceName = item.getString();
                    }
                    else if (fieldname != null && fieldname.equals(SOURCE_MI_PARAM)){
                        sourceMI = item.getString();
                    }
                    else if (fieldname != null && fieldname.equals(OUTPUT_PARAM)){
                        output = item.getString();
                    }
                }
            }

            if ( publicationId == null || publicationId.length() == 0 || sourceName == null || sourceName.length() == 0
                    || output == null || output.length() == 0){
                resp.sendError(400, "The host organism taxId, publication identifiers, source name (and MI identifier recommended) and output format are required parameters to generate " +
                        "valid PSI-MI XML or MITAB files.");
                return;
            }

            // Set response content type
            if (output.equalsIgnoreCase(XML25_OUTPUT) || output.equalsIgnoreCase(XML25_BINARY_OUTPUT) || output.equalsIgnoreCase(XML25_NARY_OUTPUT)){
                resp.setContentType("application/xml");
            }
            else {
                resp.setContentType("text/plain");
            }

            // Actual logic goes here.
            organism = new DefaultOrganism(organismTaxid, organismName);
            publication = new DefaultPublication(publicationId);
            Source source = new DefaultSource(sourceName);
            if (sourceMI != null && sourceMI.length() > 0){
                source.setMIIdentifier(sourceMI);
            }
            publication.setSource(source);

            // read CSV and export to file
            processMIData(filename, resp, writer, stream, organism, publication, output);

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
}
