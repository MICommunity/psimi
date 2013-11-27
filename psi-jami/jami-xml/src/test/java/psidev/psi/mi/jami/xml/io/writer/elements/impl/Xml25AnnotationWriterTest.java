package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25AnnotationWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class Xml25AnnotationWriterTest extends AbstractXml25WriterTest{
    private String attribute_no_value ="<attribute name=\"imex curation\" nameAc=\"MI:0959\"/>";
    private String attribute_not_topic_ac ="<attribute name=\"imex curation\">test attribute</attribute>";
    private String attribute ="<attribute name=\"imex curation\" nameAc=\"MI:0959\">test attribute</attribute>";

    @Test
    public void test_write_alias_null() throws XMLStreamException, IOException {

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_attribute_no_value() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI));

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        Assert.assertEquals(attribute_no_value, output.toString());
    }

    @Test
    public void test_write_annotation_no_topic_ac() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION), "test attribute");

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        Assert.assertEquals(attribute_not_topic_ac, output.toString());
    }

    @Test
    public void test_write_annotation() throws XMLStreamException, IOException {
        Annotation annot = new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI), "test attribute");

        Xml25AnnotationWriter writer = new Xml25AnnotationWriter(createStreamWriter());
        writer.write(annot);
        streamWriter.flush();

        Assert.assertEquals(attribute, output.toString());
    }
}
