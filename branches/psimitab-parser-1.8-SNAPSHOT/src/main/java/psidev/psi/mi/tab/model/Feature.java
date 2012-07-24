package psidev.psi.mi.tab.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public interface Feature extends Serializable {

    /**
     * Getter for 'featureType' property.
     *
     * @return a String with the CVTerm name for the feature type in the PSI-MI ontology
     */
    String getFeatureType();

    /**
     * Setter for 'featureType' property.
     *
     * @param featureType String with the CVTerm name for the feature type in the PSI-MI ontology
     */
    void setFeatureType(String featureType);

    /**
     *  Getter for 'ranges' property
     *
     * @return a list of string with the different ranges.
     */
    List<String> getRanges();

    /**
     * Setter for 'ranges' property.
     *
     * @param ranges a list of string with the different ranges.
     */
    void setRange(List<String> ranges);

    /**
     *  Getter for 'text' property
     *
     * @return the optional text, features type names, interpro cross references, etc.
     */
    String getText();

    /**
     * Setter for 'text' property.
     *
     * @param text the optional text, features type names, interpro cross references, etc.
     */
    void setText(String text);

}
