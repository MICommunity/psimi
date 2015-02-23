package psidev.psi.mi.jami.utils.checksum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rigid generator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class RigidGenerator {

    private List<String> rogidCollection = new ArrayList<String>();
    private RogidGenerator rogidGenerator;

    public RigidGenerator() {
        rogidCollection = new ArrayList<String>();
        this.rogidGenerator = new RogidGenerator();
    }

    /**
     * First get all the sequences to be processed,
     * Calcuate Rigid (seguid+taxid) for them
     * Sort them lexicographically
     * Concatinate them
     * Generate a Seguid for the resulting String
     *
     * @return  Rogid
     * @throws SeguidException  handled by SeguidException class
     */
    public String calculateRigid() throws SeguidException {

        if ( this.rogidCollection != null && rogidCollection.size() > 0 ) {
            //sort them
            Collections.sort(rogidCollection);
            //concatenate them
            StringBuffer allRigIds = new StringBuffer();
            for (String rogid : rogidCollection){
                allRigIds.append(rogid);
            }
            return this.rogidGenerator.calculateSeguid( allRigIds.toString() );
        } else {
            return null;
        }

    }

    public List<String> getRogids() {
        return rogidCollection;
    }
}
