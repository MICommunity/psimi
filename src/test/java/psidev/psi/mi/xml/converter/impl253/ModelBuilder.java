/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.model.*;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18-Jun-2006</pre>
 */
public class ModelBuilder {

    public static Unit buildUnit() {
        Unit unit = new Unit();

        unit.setNames( buildNames() );
        unit.setXref( buildXref() );

        return unit;
    }

    public static Xref buildXref() {
        Xref xref = new Xref();

        xref.setPrimaryRef( buildDbReference( "123456", "database", "MI:1111" ) );
        xref.getSecondaryRef().add( buildDbReference( "WSD234", "other_db", "MI:1234" ) );

        return xref;
    }

    public static DbReference buildDbReference( String id, String db, String dbAc ) {
        DbReference ref = new DbReference();

        ref.setId( id );
        ref.setDb( db );
        ref.setDbAc( dbAc );

        return ref;
    }

    public static Names buildNames() {
        Names names = new Names();

        names.setShortLabel( "short" );
        names.setFullName( "full" );

        Alias alias1 = new Alias();
        alias1.setType( "type1" );
        alias1.setTypeAc( "MI:xxxx" );
        alias1.setValue( "1234" );
        names.getAliases().add( alias1 );

        return names;
    }

    public static CellType buildCellType() {
        CellType cellType = new CellType();

        cellType.setNames( buildNames() );
        cellType.setXref( buildXref() );
        cellType.getAttributes().add( buildAttribute() );

        return cellType;
    }

    public static Attribute buildAttribute() {
        Attribute att = new Attribute();

        att.setName( "name" );
        att.setNameAc( "nameAc" );
        att.setValue( "value" );

        return att;
    }
}