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
    String getDbSource();

    void setDbSource( String dbSource );

    String getName();

    void setName( String name );

    String getAliasType();

    void setAliasType( String aliasType );
}
