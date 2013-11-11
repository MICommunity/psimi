package psidev.psi.mi.jami.viewer.servlet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
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
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.json.MIJsonBinaryWriter;
import psidev.psi.mi.jami.json.MIJsonWriter;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.InMemoryPsiXml25Index;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Test some examples
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public class Playground {
    private MIFileAnalyzer fileAnalyzer;
    private ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod;
    private OntologyTermFetcher fetcher;

    @Before
    public void init() throws BridgeFailedException {
        PsiJami.initialiseInteractionEvidenceSources();
        fileAnalyzer = new MIFileAnalyzer();
        expansionMethod = new InteractionEvidenceSpokeExpansion();
        Properties prop = new Properties();
        String path = null;
        //load a properties file
        try {
            prop.load(MIJsonServlet.class.getResourceAsStream("/viewer.properties"));
            path = prop.getProperty("psi.mi.obo.path");
            if (path == null || (path != null && path.length() == 0)){
                OntologyTermCompositeFetcher compositeFetcher = new OntologyTermCompositeFetcher();
                this.fetcher = compositeFetcher;
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MI, new OboOntologyTermFetcher(CvTermUtils.getPsimi(), MIJsonServlet.class.getResource("/psi-mi25.obo").getFile()));
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MOD, new CachedOlsOntologyTermFetcher());
            }
            else {
                OntologyTermCompositeFetcher compositeFetcher = new OntologyTermCompositeFetcher();
                this.fetcher = compositeFetcher;
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MI, new OboOntologyTermFetcher(CvTermUtils.getPsimi(), path));
                compositeFetcher.addCvTermFetcher(CvTerm.PSI_MOD, new CachedOlsOntologyTermFetcher());
            }
        } catch (IOException e) {
            System.out.println("cannot load the property file /viewer.properties where we can find the psi-mi OBO file path. The ontology fetcher will be null.");
        }catch (BridgeFailedException e) {
            System.out.println("cannot load the cached ontology manager for PSI-MOD.");
        }catch (IllegalArgumentException e) {
            System.out.println("cannot load the psi-mi ontology from the file "+path);
        }
    }

    @Test
    @Ignore
    public void test_play_psicquic() throws IOException {
        String urlString="ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psi25/pmid/2011/21988832_02.xml";
        Writer writer = new PrintWriter(new File("example7.txt"));

        InputStream stream = null;
        InteractionStream miDataSource = null;
        InteractionWriter interactionWriter = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection1 = url.openConnection();
            connection1.setReadTimeout(5000);
            connection1.setConnectTimeout(5000);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);

            // first recognize file and create data source
            stream = connection.getInputStream();
            InputStream dataStream = connection1.getInputStream();
            MIFileType fileType = fileAnalyzer.identifyMIFileTypeFor(stream);
            MIDataSourceOptionFactory optionFactory = MIDataSourceOptionFactory.getInstance();
            MIDataSourceFactory miFactory = MIDataSourceFactory.getInstance();

            switch (fileType){
                case mitab:
                    miDataSource = miFactory.getInteractionSourceWith(optionFactory.getMitabOptions(InteractionObjectCategory.binary_evidence, true, null, dataStream));
                    interactionWriter = new MIJsonBinaryWriter(writer,this.fetcher);
                    break;
                case psi25_xml:
                    miDataSource = miFactory.getInteractionSourceWith(optionFactory.getXml25Options(InteractionObjectCategory.binary_evidence, true, null, dataStream, null, new InMemoryPsiXml25Index(), new InMemoryPsiXml25Index()));
                    interactionWriter = new MIJsonWriter(writer, this.fetcher, this.expansionMethod);
                    break;
                default:
                    dataStream.close();
                    break;
            }

            // then write
            interactionWriter.start();
            interactionWriter.write(miDataSource.getInteractionsIterator());
            interactionWriter.end();
            interactionWriter.flush();
        } finally {
            // close first stream
            if (stream != null){
                try {
                    stream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // close data source
            if (miDataSource != null){
                try {
                    miDataSource.close();
                } catch (MIIOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
