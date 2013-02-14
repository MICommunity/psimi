package psidev.psi.mi.xml;

import psidev.psi.mi.xml.io.impl.PsimiXmlLightweightReader253;
import psidev.psi.mi.xml.io.impl.PsimiXmlLightweightReader254;
import psidev.psi.mi.xml.util.PsimiXmlVersionDetector;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * Provides for parsing of PSI-MI XML 2.5 content using indexing and random file access technology.
 * <p/>
 * This feature aims at providing user with high performance parsing of large data file keeping a small memory
 * footprint.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlLightweightReader implements psidev.psi.mi.xml.io.PsimiXmlLightweightReader {

    private psidev.psi.mi.xml.io.PsimiXmlLightweightReader readerPsimiXml;

    public PsimiXmlLightweightReader(File xmlDataFile) throws PsimiXmlReaderException {
        this(xmlDataFile, null);
    }

    public PsimiXmlLightweightReader(URL url) throws PsimiXmlReaderException {
        this(url, null);
    }

    public PsimiXmlLightweightReader(InputStream is) throws PsimiXmlReaderException {
        this(is, null);
    }

    public PsimiXmlLightweightReader(File xmlDataFile, PsimiXmlVersion version) throws PsimiXmlReaderException {
        if (xmlDataFile == null) throw new NullPointerException("Null xmlDataFile passed");

        if (version == null) {
            try {
                version = detectVersion( createPushBackReader(xmlDataFile) );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Problem detecting version from file: "+xmlDataFile, e);
            }
        }

        switch (version) {
            case VERSION_254:
                this.readerPsimiXml = new PsimiXmlLightweightReader254(xmlDataFile);
                break;
            case VERSION_253:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(xmlDataFile);
                break;
            case VERSION_25_UNDEFINED:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(xmlDataFile);
                break;
        }
    }

    private PushbackReader createPushBackReader( File xmlDataFile) throws FileNotFoundException {
        return new PushbackReader( new InputStreamReader ( new FileInputStream(xmlDataFile) ),
                                   PsimiXmlVersionDetector.BUFFER_SIZE );
    }

    protected File saveOnDisk( InputStream is ) throws IOException {

        // TODO if 'is' is already an FileInputStream, we are already reading from the local filesystem ... no need to save again.

        File tmpFile = File.createTempFile( "psi25-xml.", ".xml" );
        tmpFile.deleteOnExit();

        BufferedWriter out = new BufferedWriter( new FileWriter( tmpFile ) );
        BufferedReader in = new BufferedReader( new InputStreamReader( is ) );

        char[] buf = new char[8192];
        int read = -2;
        while ( ( read = in.read( buf, 0, 8192 ) ) != -1 ) {
            out.write( buf, 0, read );
        }

        in.close();
        out.flush();
        out.close();

        return tmpFile;
    }

    public PsimiXmlLightweightReader(URL url, PsimiXmlVersion version) throws PsimiXmlReaderException {
        if (url == null) throw new NullPointerException("Null URL passed");

        File f = null;
        try {
            f = saveOnDisk( url.openStream() );
        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "Could not write URL content into a file: " + url, e );
        }

        if (version == null) {
            try {
                version = detectVersion( createPushBackReader(f) );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Problem detecting version from URL: "+url, e);
            }
        }

        switch (version) {
            case VERSION_254:
                this.readerPsimiXml = new PsimiXmlLightweightReader254(f);
                break;
            case VERSION_253:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(f);
                break;
            case VERSION_25_UNDEFINED:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(f);
                break;
        }
    }

    public PsimiXmlLightweightReader(InputStream is, PsimiXmlVersion version) throws PsimiXmlReaderException {
        if (is == null) throw new NullPointerException("Null InputStream passed");

        File f = null;
        try {
            f = saveOnDisk( is );
        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "Could not write InputStream content into a file", e );
        }

        if (version == null) {
            try {
                version = detectVersion( createPushBackReader(f) );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Problem detecting version from InputStream", e);
            }
        }

        switch (version) {
            case VERSION_254:
                this.readerPsimiXml = new PsimiXmlLightweightReader254(f);
                break;
            case VERSION_253:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(f);
                break;
            case VERSION_25_UNDEFINED:
                this.readerPsimiXml = new PsimiXmlLightweightReader253(f);
                break;
        }
    }

    public List<IndexedEntry> getIndexedEntries() throws PsimiXmlReaderException {
        return readerPsimiXml.getIndexedEntries();
    }

    private PsimiXmlVersion detectVersion(PushbackReader reader) throws PsimiXmlReaderException {
        PsimiXmlVersion version = null;

        if (version == null) {
            PsimiXmlVersionDetector detector = new PsimiXmlVersionDetector();
            try {
                version = detector.detectVersion( reader );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Problem detecting version", e);
            }
        }

        return version;
    }
}