package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface Author extends Serializable {
    String getName();

    void setName( String name );
}
