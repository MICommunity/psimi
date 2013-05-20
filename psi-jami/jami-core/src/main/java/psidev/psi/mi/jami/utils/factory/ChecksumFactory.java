package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;

/**
 * Factory for checksums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class ChecksumFactory {

    public Checksum createAnnotation(String methodName, String methodMi, String checksum){
        return new DefaultChecksum(CvTermFactory.createMICvTerm(methodName, methodMi), checksum);
    }

    public Checksum createAnnotation(String methodName, String checksum){
        return new DefaultChecksum(CvTermFactory.createMICvTerm(methodName, null), checksum);
    }

    public Checksum createRogid(String checksum){
        return new DefaultChecksum(CvTermFactory.createRogid(), checksum);
    }

    public Checksum createRigid(String checksum){
        return new DefaultChecksum(CvTermFactory.createRigid(), checksum);
    }

    public Checksum createStandardInchiKey(String checksum){
        return new DefaultChecksum(CvTermFactory.createStandardInchiKey(), checksum);
    }

    public Checksum createStandardInchi(String checksum){
        return new DefaultChecksum(CvTermFactory.createStandardInchi(), checksum);
    }

    public Checksum createSmile(String checksum){
        return new DefaultChecksum(CvTermFactory.createSmile(), checksum);
    }
}
