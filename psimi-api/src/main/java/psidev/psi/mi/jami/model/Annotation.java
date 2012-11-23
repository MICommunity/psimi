package psidev.psi.mi.jami.model;

/**
 * An annotations is composed of a topic and a value which is free text.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Annotation {

    public CvTerm getTopic();
    public void setTopic(CvTerm topic);

    public String getValue();
    public void setValue(String value);
}
