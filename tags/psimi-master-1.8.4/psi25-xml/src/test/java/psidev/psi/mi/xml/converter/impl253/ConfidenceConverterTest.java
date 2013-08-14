/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.inMemory.InMemoryDAOFactory;
import psidev.psi.mi.xml.model.Confidence;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Unit;

/**
 * ConfidenceConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/18/2006</pre>
 */
public class ConfidenceConverterTest {

    @Test
    public void fromJaxb() {

        ConfidenceConverter confidenceConverter = new ConfidenceConverter();

        try {
            confidenceConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void toJaxb() throws ConverterException {

        ConfidenceConverter confidenceConverter = new ConfidenceConverter();

        try {
            DAOFactory factory = new InMemoryDAOFactory();
            confidenceConverter.setDAOFactory( factory );
            confidenceConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        Confidence mConfidence = new Confidence();

        Unit unit = new Unit();
        unit.setNames( ModelBuilder.buildNames() );
        unit.setXref( ModelBuilder.buildXref() );
        mConfidence.setUnit( unit );
        mConfidence.setValue( "high" );

        // build an experiment
        ExperimentDescription experiment = new ExperimentDescription();
        experiment.setId( 12 );
        experiment.setNames( ModelBuilder.buildNames() );

        mConfidence.getExperiments().add( experiment );

        psidev.psi.mi.xml253.jaxb.ConfidenceListType.Confidence jConfidence = confidenceConverter.toJaxb( mConfidence );

        Assert.assertNotNull( jConfidence );
        Assert.assertNotNull( jConfidence.getUnit().getXref().getPrimaryRef() );
        assertEquals( 1, jConfidence.getUnit().getXref().getSecondaryReves().size() );

        assertEquals( "high", jConfidence.getValue() );

        Integer integer = jConfidence.getExperimentRefList().getExperimentReves().get( 0 );
        assertEquals( 12, integer.intValue() );
    }
}
