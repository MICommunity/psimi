package org.hupo.psi.tab.io;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.tab.AbstractCalimochoTabTest;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.tab.io.DefaultRowReader;
import org.hupo.psi.tab.io.DefaultRowWriter;
import org.hupo.psi.tab.io.RowReader;
import org.hupo.psi.tab.io.RowWriter;
import org.hupo.psi.tab.model.ColumnBasedDocumentDefinition;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * DefaultRowWriter Tester.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultRowWriterTest extends AbstractCalimochoTabTest {

    @Test
    public void writeLine_singleLine() throws Exception {

        String aLine = "LSM7|9606" + NEW_LINE;

        final ColumnBasedDocumentDefinition documentDefinition = buildGeneListDefinition();

        RowReader reader = new DefaultRowReader( documentDefinition );
        final List<Row> rows = reader.read( new ByteArrayInputStream( aLine.getBytes() ) );
        Assert.assertNotNull( rows );
        Assert.assertEquals( 1, rows.size() );

        Row row = rows.get( 0 );

        RowWriter writer = new DefaultRowWriter( documentDefinition );
        final String outputLine = writer.writeLine( row );

        Assert.assertEquals( aLine, outputLine );
    }

    @Test
    public void write_multipleLine() throws Exception {

        String aLine = "LSM7|9606" + NEW_LINE + "LSM2|9606" + NEW_LINE + "BRCA2|10032";

        final ColumnBasedDocumentDefinition documentDefinition = buildGeneListDefinition();

        RowReader reader = new DefaultRowReader( documentDefinition );
        final List<Row> rows = reader.read( new ByteArrayInputStream( aLine.getBytes() ) );
        Assert.assertNotNull( rows );
        Assert.assertEquals( 3, rows.size() );

        RowWriter writer = new DefaultRowWriter( documentDefinition );
        final StringWriter sw = new StringWriter();
        writer.write( sw, rows );

        final String[] lines = StringUtils.split( sw.getBuffer().toString(), NEW_LINE );
        Assert.assertNotNull( lines );
        Assert.assertEquals(3, lines.length);

        Assert.assertEquals( "LSM7|9606", lines[0] );
        Assert.assertEquals( "LSM2|9606", lines[1] );
        Assert.assertEquals( "BRCA2|10032", lines[2] );
    }
}
