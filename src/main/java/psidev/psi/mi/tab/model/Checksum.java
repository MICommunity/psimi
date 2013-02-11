package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */

public interface Checksum extends Serializable {

    /**
     * Getter fot property 'methodName'.
     *
     * @return a String with the CVTerm name for the checksum method in the PSI-MI ontology.
     */
    String getMethodName();

    /**
     * Setter for property 'methodName'.
     *
     * @param methodName String with the CVTerm name for the checksum method in the PSI-MI ontology
     */
    void setMethodName(String methodName);

    /**
     *  Getter for property 'checksum'.
     *
     * @return a String with the value of the property 'checksum'.
     */
    String getChecksum();

    /**
     *  Setter for property 'checksum'.
     *
     * @param checksum Value to set for property 'checksum' in the annotation.
     */
    void setChecksum(String checksum);
}
