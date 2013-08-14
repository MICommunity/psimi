package org.hupo.psi.calimocho.model;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class AbstractDefined implements Defined {

    private String name;
    private String definition;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition( String definition ) {
        this.definition = definition;
    }
}
