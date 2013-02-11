/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.model.Alias;
import psidev.psi.mi.xml.model.Names;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xref search utility.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03-Oct-2006</pre>
 */
public class AliasUtils {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( AliasUtils.class );

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";

    public static final String GENE_NAME = "gene name";
    public static final String GENE_NAME_MI_REF = "MI:0301";

    /**
     * Collect AliasImpl that match the given type.
     *
     * @param aliases a non null collection of aliases
     * @param type    type we are searching for.
     * @param miRef   MI reference of the type we are searching for.
     *
     * @return a non null collection.
     */
    public static Collection<Alias> searchByType( Collection<Alias> aliases, String type, String miRef ) {

        if ( type == null && miRef == null ) {
            throw new IllegalArgumentException( "You must give either a type of an MI reference (or both)." );
        }

        List<Alias> collected = new ArrayList<Alias>( 2 );

        for ( Alias alias : aliases ) {

            if ( type != null ) {
                if ( type.equalsIgnoreCase( alias.getType() ) ) {
                    collected.add( alias );
                }
            } else if ( miRef != null ) {
                if ( miRef.equalsIgnoreCase( alias.getTypeAc() ) ) {
                    collected.add( alias );
                }
            }
        }

        return collected;
    }

    public static Alias getGeneName( Collection<Alias> aliases ) {
        for ( Alias alias : aliases ) {
            if ( GENE_NAME.equalsIgnoreCase( alias.getType() )
                    ||
                    GENE_NAME_MI_REF.equalsIgnoreCase( alias.getTypeAc() ) ) {
                return alias;
            }
        }

        return null;
    }

    /**
     * Collect all DbReference under an xref.
     *
     * @param names aliases holder.
     *
     * @return a non null collection.
     */
    @Deprecated
    public static Collection<Alias> getAllAliases( Names names ) {
        ArrayList<Alias> aliases = new ArrayList<Alias>( names.getAliases().size() );

        for ( Alias alias : names.getAliases() ) {
            aliases.add( alias );
        }

        return aliases;
    }
}