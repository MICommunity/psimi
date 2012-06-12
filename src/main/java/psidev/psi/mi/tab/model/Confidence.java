package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface Confidence extends Serializable {
    String getType();

    void setType( String type );

    String getValue();

    void setValue( String value );

    String getText();

    void setText( String text );
}
