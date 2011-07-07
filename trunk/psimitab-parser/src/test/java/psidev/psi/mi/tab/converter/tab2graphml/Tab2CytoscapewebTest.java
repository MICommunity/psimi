package psidev.psi.mi.tab.converter.tab2graphml;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.converter.ConverterException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cjandras
 * Date: 07/07/11
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class Tab2CytoscapewebTest{
    @Test
    public void convert(){
        InputStream inputStream = getClass().getResourceAsStream("/mitab-samples/12167173.txt");
        String ouput = null;
        Tab2Cytoscapeweb tab2Cytoscapeweb = new Tab2Cytoscapeweb();
        try {
            ouput = tab2Cytoscapeweb.convert(inputStream);
        } catch (ConverterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"  \n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n" +
                "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n" +
                "  <key id=\"label\" for=\"node\" attr.name=\"label\" attr.type=\"string\"/>\n" +
                "  <key id=\"identifier\" for=\"node\" attr.name=\"identifier\" attr.type=\"string\"/>\n" +
                "  <key id=\"specie\" for=\"node\" attr.name=\"specie\" attr.type=\"string\"/>\n" +
                "  <key id=\"type\" for=\"node\" attr.name=\"type\" attr.type=\"string\"/>\n" +
                "  <key id=\"shape\" for=\"node\" attr.name=\"shape\" attr.type=\"string\">\n" +
                "    <default>ELLIPSE</default>\n" +
                "  </key>\n" +
                "  <graph id=\"G\" edgedefault=\"undirected\">\n" +
                "  </graph>\n" +
                "</graphml>";

        Assert.assertEquals(expected,ouput);
        System.out.println(ouput);
    }
}
