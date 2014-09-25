/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.CellType;
import psidev.psi.mi.xml253.jaxb.*;

/**
 * OpenCvTypeConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/18/2006</pre>
 */
public class OpenCvTypeConverterTest {

    public OpenCvType buildJaxbOpenCvType() {
        OpenCvType jOpenCvType = new OpenCvType();

        NamesType jNames = new NamesType();
        jNames.setShortLabel( "yeast two hybrid" );
        jNames.setFullName( "yeast two hybrid method..." );
        jOpenCvType.setNames( jNames );

        XrefType jXref = new XrefType();
        DbReferenceType jPrimaryRef = new DbReferenceType();
        jPrimaryRef.setId( "MI:0123" );
        jPrimaryRef.setDb( "psi-mi" );
        jPrimaryRef.setDbAc( "MI:1111" );
        jXref.setPrimaryRef( jPrimaryRef );
        jOpenCvType.setXref( jXref );

        AttributeListType jAttributeList = new AttributeListType();

        AttributeListType.Attribute jAtt = new AttributeListType.Attribute();
        jAtt.setName( "definition" );
        jAtt.setNameAc( "MI:0001" );
        jAtt.setValue( "a nice definition of Y2H" );
        jAttributeList.getAttributes().add( jAtt );
        jOpenCvType.setAttributeList( jAttributeList );

        return jOpenCvType;
    }

    @Test
    public void fromJaxb() {

        OpenCvTypeConverter openCvTypeConverter = new OpenCvTypeConverter();

        try {
            openCvTypeConverter.fromJaxb( null, null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        OpenCvType jOpenCvType = buildJaxbOpenCvType();

        CellType cellType = null;
        try {
            cellType = openCvTypeConverter.fromJaxb( jOpenCvType, CellType.class );
        } catch ( ConverterException e ) {
            fail();
        }

        Assert.assertNotNull( cellType );

        assertEquals( "yeast two hybrid", cellType.getNames().getShortLabel() );
        assertEquals( "yeast two hybrid method...", cellType.getNames().getFullName() );

        Assert.assertNotNull( cellType.getXref() );
        Assert.assertNotNull( cellType.getXref().getPrimaryRef() );
        assertEquals( "MI:0123", cellType.getXref().getPrimaryRef().getId() );
        assertEquals( "psi-mi", cellType.getXref().getPrimaryRef().getDb() );
        assertEquals( "MI:1111", cellType.getXref().getPrimaryRef().getDbAc() );

        assertTrue( cellType.hasAttributes() );
        Attribute att = cellType.getAttributes().iterator().next();
        assertEquals( "definition", att.getName() );
        assertEquals( "MI:0001", att.getNameAc() );
        assertEquals( "a nice definition of Y2H", att.getValue() );
    }

    @Test
    public void toJaxb() {

        OpenCvTypeConverter openCvTypeConverter = new OpenCvTypeConverter();

        try {
            openCvTypeConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        CellType cellType = ModelBuilder.buildCellType();

        OpenCvType jOpenCvType = openCvTypeConverter.toJaxb( cellType );

        Assert.assertNotNull( jOpenCvType );

        Assert.assertNotNull( jOpenCvType.getNames() );
        assertEquals( "short", jOpenCvType.getNames().getShortLabel() );
        assertEquals( "full", jOpenCvType.getNames().getFullName() );
        assertEquals( 1, jOpenCvType.getNames().getAlias().size() );

        Assert.assertNotNull( jOpenCvType.getXref() );
        DbReferenceType p = jOpenCvType.getXref().getPrimaryRef();
        Assert.assertNotNull( p );
        DbReferenceType s = jOpenCvType.getXref().getSecondaryReves().get( 0 );
        Assert.assertNotNull( s );

        assertEquals( 1, jOpenCvType.getAttributeList().getAttributes().size() );
    }
}
