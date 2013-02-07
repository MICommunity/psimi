package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class for Checksums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class ChecksumUtils {

    public static boolean doesChecksumHaveMethod(Checksum checksum, String methodId, String methodName){

        if (checksum == null || (methodName == null && methodId == null)){
            return false;
        }

        CvTerm method = checksum.getMethod();
        // we can compare identifiers
        if (methodId != null && method.getMIIdentifier() != null){
            // we have the same method id
            return method.getMIIdentifier().equals(methodId);
        }
        // we need to compare methodNames
        else if (methodName != null) {
            return methodName.toLowerCase().equals(method.getShortName().toLowerCase());
        }

        return false;
    }

    /**
     * This method will return the first Checksum having this methodId/method name
     * It will return null if there are no Checksums with this method id/name
     * @param checksums : the collection of Checksum
     * @param methodId : the method id to look for
     * @param methodName : the method name to look for
     * @return the first checksum having this method name/id, null if no Checksum with this method name/id
     */
    public static Checksum collectFirstChecksumWithMethod(Collection<Checksum> checksums, String methodId, String methodName){

        if (checksums == null || (methodName == null && methodId == null)){
            return null;
        }

        for (Checksum checksum : checksums){
            CvTerm method = checksum.getMethod();
            // we can compare method ids
            if (methodId != null && method.getMIIdentifier() != null){
                // we have the same method id
                if (method.getMIIdentifier().equals(methodId)){
                    return checksum;
                }
            }
            // we need to compare methodName
            else if (methodName != null && methodName.toLowerCase().equals(method.getShortName().toLowerCase())) {
                // we have the same method name
                if (method.getShortName().toLowerCase().trim().equals(methodName)){
                    return checksum;
                }
            }
        }

        return null;
    }

    /**
     * Remove all Checksum having this method name/method id from the collection of checksums
     * @param checksums : the collection of Checksum
     * @param methodId : the method id to look for
     * @param methodName : the method name to look for
     */
    public static void removeAllChecksumWithMethod(Collection<Checksum> checksums, String methodId, String methodName){

        if (checksums != null){
            Iterator<Checksum> checksumIterator = checksums.iterator();

            while (checksumIterator.hasNext()){
                if (doesChecksumHaveMethod(checksumIterator.next(), methodId, methodName)){
                    checksumIterator.remove();
                }
            }
        }
    }
}
