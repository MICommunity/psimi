package psidev.psi.mi.tab.converter.tab2graphml;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.PsimiTabException;

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
    public void convertNoHeader(){
        InputStream inputStream = getClass().getResourceAsStream("/mitab-samples/12167173_noHeader.txt");
        String output = null;
        Tab2Cytoscapeweb tab2Cytoscapeweb = new Tab2Cytoscapeweb();
        try {
            output = tab2Cytoscapeweb.convert(inputStream);
        } catch (PsimiTabException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // make sure some data was transformed
        Assert.assertTrue((GraphmlBuilder.GRAPHML_FOOTER.length() + GraphmlBuilder.GRAPHML_HEADER.length()) < output.length());

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
                "     <node id=\"1\">\n" +
                "        <data key=\"label\">pop1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#P87060</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <node id=\"2\">\n" +
                "        <data key=\"label\">pop2</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O14170</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <node id=\"3\">\n" +
                "        <data key=\"label\">pip1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O13959</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <node id=\"4\">\n" +
                "        <data key=\"label\">cul1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O13790</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"3\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <node id=\"5\">\n" +
                "        <data key=\"label\">skp1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#Q9Y709</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"2\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <node id=\"6\">\n" +
                "        <data key=\"label\">rum1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#P40380</data>\n" +
                "        <data key=\"specie\">Schizosaccharomyces pombe 972h-</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"2\" target=\"6\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"2\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <edge source=\"2\" target=\"4\">\n" +
                "     </edge>\n" +
                "     <edge source=\"2\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"2\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"5\">\n" +
                "     </edge>\n" +
                "  </graph>\n" +
                "</graphml>";

        Assert.assertEquals(expected, output);
    }

    @Test
    public void convertWithHeader(){
        InputStream inputStream = getClass().getResourceAsStream("/mitab-samples/12167173.txt");
        String output = null;
        Tab2Cytoscapeweb tab2Cytoscapeweb = new Tab2Cytoscapeweb();
        try {
            output = tab2Cytoscapeweb.convert(inputStream);
        } catch (PsimiTabException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // make sure some data was transformed
        Assert.assertTrue((GraphmlBuilder.GRAPHML_FOOTER.length() + GraphmlBuilder.GRAPHML_HEADER.length()) < output.length());

        String expected = GraphmlBuilder.GRAPHML_HEADER +
                "     <node id=\"1\">\n" +
                "        <data key=\"label\">SPBC2G2.18</data>\n" +
                "        <data key=\"identifier\">uniprotkb#P87060</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <node id=\"2\">\n" +
                "        <data key=\"label\">sud1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O14170</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"1\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <node id=\"3\">\n" +
                "        <data key=\"label\">pcu1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O13790</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"2\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <node id=\"4\">\n" +
                "        <data key=\"label\">SPAC23H4.18c</data>\n" +
                "        <data key=\"identifier\">uniprotkb#O13959</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"4\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <node id=\"5\">\n" +
                "        <data key=\"label\">SPBC32F12.09</data>\n" +
                "        <data key=\"identifier\">uniprotkb#P40380</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"2\" target=\"5\">\n" +
                "     </edge>\n" +
                "     <node id=\"6\">\n" +
                "        <data key=\"label\">skp1</data>\n" +
                "        <data key=\"identifier\">uniprotkb#Q9Y709</data>\n" +
                "        <data key=\"specie\">schpo</data>\n" +
                "        <data key=\"type\">protein</data>\n" +
                "     </node>\n" +
                "     <edge source=\"2\" target=\"6\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"6\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"2\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"1\">\n" +
                "     </edge>\n" +
                "     <edge source=\"1\" target=\"3\">\n" +
                "     </edge>\n" +
                "     <edge source=\"4\" target=\"6\">\n" +
                "     </edge>\n" +
                "     <edge source=\"3\" target=\"6\">\n" +
                "     </edge>\n" + GraphmlBuilder.GRAPHML_FOOTER;

        Assert.assertEquals(expected,output);
    }
}
