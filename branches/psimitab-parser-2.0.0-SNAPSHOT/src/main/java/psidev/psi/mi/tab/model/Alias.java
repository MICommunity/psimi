package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface Alias extends Serializable {

    /**
     * Getter for property 'data base'.
     *
     * @return Value for property 'data base'.
     */
    String getDbSource();

    /**
     * Setter for property 'data base'.
     *
     * @param dbSource Value to set for property 'data base'.
     */
    void setDbSource( String dbSource );

    /**
     * Getter for property 'name'.
     *
     * @return Value for property 'name'.
     */
    String getName();

    /**
     * Setter for property 'name'.
     *
     * @param name Value to set for property 'name'.
     */
    void setName( String name );

    /**
     * Getter for property 'type'.
     *
     * @return Value for property 'type'.
     */
    String getAliasType();

    /**
     * Setter for property 'type'.
     *
     * @param aliasType Value to set for property 'type'.
     */
    void setAliasType( String aliasType );
}
