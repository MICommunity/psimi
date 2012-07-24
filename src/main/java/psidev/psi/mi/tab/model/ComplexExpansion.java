package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public interface ComplexExpansion extends Serializable {

    /**
     * Getter fot property 'databaseName'.
     *
     * @return  Getter fot property 'databaseName'.
     */
    String getDatabaseName();

    /**
     * Setter for property 'databaseName'
     *
     * @param databaseName Value to set for property 'databaseName'.
     */
    void setDatabaseName(String databaseName);

    /**
     * Getter fot property 'identifier'.
     *
     * @return  Getter fot property 'identifier'.
     */
    String getIdentifier();

    /**
     * Setter for property 'identifier'
     *
     * @param identifier Value to set for property 'identifier'.
     */
    void setIdentifier(String identifier);

    /**
     * Getter fot property 'expansionName'.
     *
     * @return  Getter fot property 'expansionName'.
     */
    String getExpansionName();

    /**
     * Setter for property 'expansionName'
     *
     * @param expansionName Value to set for property 'expansionName'.
     */
    void setExpansionName(String expansionName);

    /**
     * Checks if has a expansion name or not
     *
     * @return The boolean with the checking result
     */
    boolean hasExpansionName();
}
