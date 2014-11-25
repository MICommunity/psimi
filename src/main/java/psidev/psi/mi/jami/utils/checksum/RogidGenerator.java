package psidev.psi.mi.jami.utils.checksum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Rogid generator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/09/13</pre>
 */

public class RogidGenerator {

    public RogidGenerator() {
    }

    /**
     * calculates the Seguid for the given protein sequence
     *
     * @param sequence rogid sequence
     * @return Seguid
     * @throws SeguidException handled by  SeguidException class
     */
    public String calculateSeguid( String sequence ) throws SeguidException {

        if ( sequence == null ) {
            throw new NullPointerException( "You must give a non null sequence" );
        }

        return doMessageDigestAndBase64Encoding( sequence );
    }


    /**
     * @param sequence protein sequence
     * @return the MessageDigest based on SHA algorithm with Base64 encoding
     * @throws SeguidException handled by  SeguidException class
     */
    private String doMessageDigestAndBase64Encoding( String sequence ) throws SeguidException {
        if ( sequence == null ) {
            throw new NullPointerException( "You must give a non null sequence" );
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance( "SHA" );
            messageDigest.update( sequence.getBytes() );
            byte[] digest = messageDigest.digest();
            String seguid = Base64.encodeBytes( digest );
            seguid = seguid.replace( "=", "" );

            return seguid;
        } catch ( NoSuchAlgorithmException e ) {
            throw new SeguidException( "Exception thrown when calculating Seguid for " + sequence,e.getCause() );
        }

    }

    /**
     * calculate Rogid for the give protein sequence+taxid
     * Rogid = Sequid + taxid
     *
     * @param sequence protein sequence
     * @param taxid    taxonomy id
     * @return Rogid
     * @throws SeguidException handled by  SeguidException class
     */
    public String computeRogidFrom( String sequence, String taxid ) throws SeguidException {
        if ( sequence == null ) {
            throw new NullPointerException( "You must give a non null sequence" );
        }

        if ( taxid == null ) {
            throw new NullPointerException( "You must give a non null taxid" );
        }
        return doMessageDigestAndBase64Encoding( sequence.trim().toUpperCase()) + taxid;
    }
}
