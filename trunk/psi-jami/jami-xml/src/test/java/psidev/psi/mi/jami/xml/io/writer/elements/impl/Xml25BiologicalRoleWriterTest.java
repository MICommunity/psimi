package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Unit tester for Xml25BiologicalRoleWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25BiologicalRoleWriterTest {

    private String bioRole = "<biologicalRole>\n" +
            "<names>\n" +
            "<shortLabel>\n" +
            "</shortLabel>\n"+
            "</names>\n"+
            "</biologicalRole>";
    private StringWriter output;
    private XMLStreamWriter2 streamWriter;

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI));

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        //Assert.assertEquals(attribute_no_value, output.toString());
    }

    @Test
    public void test_write_annotation_no_topic_ac() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION), "test attribute");

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        //Assert.assertEquals(attribute_not_topic_ac, output.toString());
    }

    @Test
    public void test_write_annotation() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI), "test attribute");

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        //Assert.assertEquals(attribute, output.toString());
    }

    private XMLStreamWriter2 createStreamWriter() throws XMLStreamException {
        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        this.output = new StringWriter();
        this.streamWriter = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(this.output);
        return this.streamWriter;
    }
}
