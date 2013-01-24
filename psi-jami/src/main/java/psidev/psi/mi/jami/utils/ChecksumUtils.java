package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Utility class for Checksums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class ChecksumUtils {

    /**
     * Retrives a unique Checksum having a method that matches the method id (if set) or the method name.
     * It will return null if it cannot find a single Checksum with a method match or if it finds more than one checksum with a method
     * match.
     * @param checksums
     * @param methodId
     * @param methodName
     * @return the unique Xref, null if not unique
     */
    public static Checksum collectUniqueChecksumMethodIfExists(Collection<Checksum> checksums, String methodId, String methodName){

        if (checksums == null || (methodName == null && methodId == null)){
            return null;
        }

        Checksum uniqueChecksum = null;
        for (Checksum checksum : checksums){
            CvTerm method = checksum.getMethod();
            // we can compare identifiers
            if (methodId != null && method.getOntologyIdentifier() != null){
                // we have the same database id
                if (method.getOntologyIdentifier().getId().equals(methodId)){
                    // it is a unique checksum
                    if (uniqueChecksum == null){
                        uniqueChecksum = checksum;
                    }
                    // we could not find a unique checksum with this method so we return null
                    else {
                        return null;
                    }
                }
            }
            // we need to compare methodNames
            else if (methodName != null && methodName.toLowerCase().equals(method.getShortName().toLowerCase())) {
                // it is a unique checksum
                if (uniqueChecksum == null){
                    uniqueChecksum = checksum;
                }
                // we could not find a unique checksum with this method so we return null
                else {
                    return null;
                }
            }
        }

        return uniqueChecksum;
    }
}
