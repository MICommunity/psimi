package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.utils.ChecksumUtils;

import java.util.Collection;

/**
 *
 * Find the CRC64 and ROGID checksums in both lists
 * Find the other checksums so they can be added.
 * Only add CRC64/ROGID is the fetcher sequence is in the enriched protein
 * decide if the crc64 or rogid should be removed.
 *
 * Define the line between an enricher and validator.
 * Enricher should assure that nothing incorrect is added.
 * in enriching wrong information can be left
 * in updating wrong information might be removed?
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 11/07/13
 */
public class ChecksumMerger {

    Checksum crc64ChecksumToEnrich = null;
    Checksum rogidChecksumToEnrich = null;
    Checksum fetchedCrc64Checksum = null;
    Checksum fetchedRogidChecksum = null;

    public void merge(Collection<Checksum> checksumsToEnrich , Collection<Checksum> checksumsFetched){


        // Checksums
        // Can only add a checksum if there is a sequence which matches the protein fetched and an organism

        // Find the CRC64 & ROGID in the fetched protein.
        for(Checksum checksum : checksumsFetched){
            if(checksum.getMethod() != null){
                if(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.ROGID_MI, Checksum.ROGID)){
                    rogidChecksumToEnrich = checksum;
                }
                else if(ChecksumUtils.doesChecksumHaveMethod(checksum , null , "CRC64")){
                    crc64ChecksumToEnrich = checksum;
                }
            }
            if(crc64ChecksumToEnrich != null && rogidChecksumToEnrich != null) break;
        }
        // If either were found, look for the crc64 and a the rogid in the enriched
        if(crc64ChecksumToEnrich != null || rogidChecksumToEnrich != null){
            for(Checksum checksum : checksumsToEnrich){
                if(checksum.getMethod() != null){
                    if(ChecksumUtils.doesChecksumHaveMethod(checksum , Checksum.ROGID_MI , Checksum.ROGID)){
                        fetchedRogidChecksum = checksum;
                    }
                    else if(ChecksumUtils.doesChecksumHaveMethod(checksum , null , "CRC64")){
                        fetchedCrc64Checksum = checksum;
                    }
                }
                if(fetchedCrc64Checksum != null && fetchedRogidChecksum != null) break;
            }
        }


    }

    public void test(String sequence){
        /*if(proteinFetched.getSequence() != null
                && proteinToEnrich.getSequence().equalsIgnoreCase(proteinFetched.getSequence())){
            if(fetchedCrc64Checksum != null) {
                if( crc64ChecksumToEnrich != null
                        && ! fetchedCrc64Checksum.getValue().equalsIgnoreCase(crc64ChecksumToEnrich.getValue())) {
                    checksumsToEnrich.remove(crc64ChecksumToEnrich);
                    if(listener != null) listener.onRemovedChecksum(proteinToEnrich, crc64ChecksumToEnrich);
                    crc64ChecksumToEnrich = null;
                }
                if(crc64ChecksumToEnrich == null){
                    checksumsToEnrich.add(fetchedCrc64Checksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, fetchedCrc64Checksum);
                }
            }
            if(fetchedRogidChecksum != null
                    && proteinFetched.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()
                    && proteinToEnrich.getOrganism().getTaxId() != -3){
                if( rogidChecksumToEnrich != null
                        && ! fetchedRogidChecksum.getValue().equalsIgnoreCase(rogidChecksumToEnrich.getValue())) {
                    checksumsToEnrich.remove(rogidChecksumToEnrich);
                    if(listener != null) listener.onRemovedChecksum(proteinToEnrich, rogidChecksumToEnrich);
                    rogidChecksumToEnrich = null;
                }
                if(rogidChecksumToEnrich == null){
                    checksumsToEnrich.add(fetchedRogidChecksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, fetchedRogidChecksum);
                }
            }
        }*/


    }
}
