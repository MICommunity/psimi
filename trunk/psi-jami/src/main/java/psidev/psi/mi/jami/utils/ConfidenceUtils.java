package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Utility class for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/03/13</pre>
 */

public class ConfidenceUtils {

    /**
     * Check the type of a confidence
     * @param conf
     * @param typeId
     * @param typeName
     * @return
     */
    public static boolean doesConfidenceHaveType(Confidence conf, String typeId, String typeName){

        if (conf == null || (typeName == null && typeId == null)){
            return false;
        }

        CvTerm type = conf.getType();
        // we can compare identifiers
        if (typeId != null && type.getMIIdentifier() != null){
            // we have the same topic id
            return type.getMIIdentifier().equals(typeId);
        }
        // we need to compare topic names
        else if (typeName != null) {
            return typeName.toLowerCase().equals(type.getShortName().toLowerCase());
        }

        return false;
    }

    /**
     * Collect all confidences having a specific type
     * @param confs
     * @param typeId
     * @param typeName
     * @return
     */
    public static Collection<Confidence> collectAllConfidencesHavingType(Collection<? extends Confidence> confs, String typeId, String typeName){

        if (confs == null || confs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Confidence> confidences = new ArrayList<Confidence>(confs.size());

        for (Confidence conf : confidences){
            if (doesConfidenceHaveType(conf, typeId, typeName)){
                confidences.add(conf);
            }
        }

        return confidences;
    }
}
