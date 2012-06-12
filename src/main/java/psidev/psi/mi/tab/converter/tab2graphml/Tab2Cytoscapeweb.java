package psidev.psi.mi.tab.converter.tab2graphml;

import psidev.psi.mi.xml.converter.ConverterException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cjandras
 * Date: 07/07/11
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class Tab2Cytoscapeweb {

    GraphmlBuilder graphmlBuilder;

    public Tab2Cytoscapeweb(){
         graphmlBuilder = new GraphmlBuilder();
    }

    public GraphmlBuilder getGraphmlBuilder(){
        return graphmlBuilder;
    }

    public void setGraphmlBuilder(GraphmlBuilder graphmlBuilder){
        this.graphmlBuilder = graphmlBuilder;
    }

    /**
     * Converts a stream  of psimitab data to a String containing the GraphML
     * @param inputStream
     * @return
     * @throws ConverterException
     * @throws IOException
     */

    public String convert(InputStream inputStream) throws ConverterException, IOException {
        return graphmlBuilder.build(inputStream);
    }
}
