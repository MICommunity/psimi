package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface Confidence extends psidev.psi.mi.jami.model.Confidence, Serializable {
    String getComfidenceType();

    void setConfidenceType(String type);

    String getText();

    void setText( String text );
}
