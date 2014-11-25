package psidev.psi.mi.jami.bridges.picr.io;

import psidev.psi.mi.jami.bridges.picr.jaxb.GetUPIForAccessionResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Picr REST xml result parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Mar-2010</pre>
 */

public class PicrRESTParser {

   /**
     * Sets up a logger for this class.
     */
    public static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PicrRESTParser.class.getName());

    // //////////////////////
    // Private methods

    private Unmarshaller getUnmarshaller() throws JAXBException {

        // create a JAXBContext capable of handling classes generated into the
        // jaxb package
        JAXBContext jc = JAXBContext.newInstance( "uk.ac.ebi.intact.bridges.picr.jaxb" );

        // create and return Unmarshaller
        return jc.createUnmarshaller();
    }

    private GetUPIForAccessionResponse unmarshall( URL url ) throws JAXBException, FileNotFoundException {

        if ( url == null ) {
            throw new IllegalArgumentException( "You must give a non null URL." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( url );
    }

    private GetUPIForAccessionResponse unmarshall( File file ) throws JAXBException, FileNotFoundException {

        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( !file.exists() ) {
            throw new IllegalArgumentException( "You must give an existing file. : " + file.getPath() );
        }

        if ( !file.canRead() ) {
            throw new IllegalArgumentException( "You must give a readable file." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.

        if (log.isDebugEnabled()){
            log.debug("unmarshal : " + file.getPath());
        }

        FileInputStream inputStream = new FileInputStream( file );
        try {
            return ( GetUPIForAccessionResponse ) u.unmarshal( inputStream );
        } catch (ClassCastException e){
            if (log.isWarnEnabled()){
                log.warn("ClassCastException: file: " + file.getPath());
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("Inpossible to close the resulting file input stream", e);
            }
        }
      //  return (EBIApplicationResult) ((JAXBElement)u.unmarshal(new FileInputStream(file))).getValue();
        //( CVMappingType ) ( ( JAXBElement ) u.unmarshal( new FileInputStream( file ) ) ).getValue();
        return null;
    }

    private GetUPIForAccessionResponse unmarshall( InputStream is ) throws JAXBException {

        if ( is == null ) {
            throw new IllegalArgumentException( "You must give a non null input stream." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( is );
    }

    private GetUPIForAccessionResponse unmarshall( String s ) throws JAXBException {

        if ( s == null ) {
            throw new IllegalArgumentException( "You must give a non null String." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( new StringReader( s ) );
    }

    // ////////////////////////
    // Public methods

    public GetUPIForAccessionResponse read( String s ) throws PicrParsingException {
        try {
            return unmarshall( s );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        }
    }

    public GetUPIForAccessionResponse read( File file ) throws PicrParsingException {
        try {
            return unmarshall( file );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        } catch ( FileNotFoundException e ) {
            throw new PicrParsingException( "We couldn't find the file " + file.getAbsolutePath(), e );
        }
    }

    public GetUPIForAccessionResponse read( InputStream is ) throws PicrParsingException {
        try {
            return unmarshall( is );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        }
    }

    public GetUPIForAccessionResponse read( URL url ) throws PicrParsingException {
        try {
            return unmarshall( url );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        } catch ( FileNotFoundException e ) {
            throw new PicrParsingException( "We couldn't open the URL " + url.toString(), e );
        }
    }
}
