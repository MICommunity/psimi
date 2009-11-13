/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.stylesheets;


import psidev.psi.mi.xml.util.PsimiXmlVersionDetector;
import psidev.psi.mi.xml.PsimiXmlVersion;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Utility to run an XSLT script on PSI-MI XML data.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class XslTransformerUtils {

    private static final String PSI_10_EXPAND = "/META-INF/stylesheets/v1_0/MIF_expand.xsl";
    private static final String PSI_10_COMPACT = "/META-INF/stylesheets/v1_0/MIF_compact.xsl";
    private static final String PSI_10_HTML = "/META-INF/stylesheets/v1_0/MIF_view.xsl";

    private static final String PSI_25_EXPAND = "/META-INF/stylesheets/v2_5/MIF25_expand.xsl";
    private static final String PSI_25_COMPACT = "/META-INF/stylesheets/v2_5/MIF25_compact.xsl";
    private static final String PSI_25_HTML = "/META-INF/stylesheets/v2_5/MIF25_view.xsl";

    private static final String PSI_254_EXPAND = "/META-INF/stylesheets/v2_5/MIF254_expand.xsl";
    private static final String PSI_254_COMPACT = "/META-INF/stylesheets/v2_5/MIF254_compact.xsl";
    private static final String PSI_254_HTML = "/META-INF/stylesheets/v2_5/MIF254_view.xsl";

    private static final String NAMESPACE_FROM_254 = "http://psi.hupo.org/mi/mif";
    private static final String NAMESPACE_BEFORE_254 = "net:sf:psidev:mi";

    /////////////////////
    // Constructor

    private XslTransformerUtils() {
    }

    ///////////////////////
    // Utility

    private static InputStream loadFromMetaInf( String resource ) throws XslTransformException {
        try {
            return XslTransformerUtils.class.getResource( resource ).openStream();
        } catch ( IOException e ) {
            throw new XslTransformException( "An error occured while retrieving XSL located at: " + resource, e );
        }
    }

    ////////////////////////
    // Static methods

    public static void runXslt( InputStream inputXml, InputStream xslt, OutputStream outputXml ) throws XslTransformException {
        runXslt(new InputStreamReader(inputXml), xslt, new OutputStreamWriter(outputXml));
    }

    public static void runXslt( Reader inputReader, InputStream xslt, Writer outputWriter ) throws XslTransformException {
        // JAXP reads data using the Source interface
        Source xmlSource = new StreamSource( inputReader );
        Source xsltSource = new StreamSource( xslt );

        // the factory pattern supports different XSLT processors
        TransformerFactory transFact = TransformerFactory.newInstance();
        try {
            Transformer trans = transFact.newTransformer( xsltSource );
            trans.transform( xmlSource, new StreamResult( outputWriter ) );
        } catch ( Exception e ) {
            throw new XslTransformException( "An error occured while transforming the XML", e );
        }
    }

    public static void expandPsiMi10( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);
        try {
            expandPsiMi10(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem expanding file: "+inputXML, e);
        }
    }

    public static void expandPsiMi10( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        transform(new PushbackReader(new InputStreamReader(inputXML)), outputXML, PSI_10_EXPAND);
    }

    public static void compactPsiMi10( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);
        try {
            compactPsiMi10(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem compacting file: "+inputXML, e);
        }
    }

    public static void compactPsiMi10( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        transform(new PushbackReader(new InputStreamReader(inputXML)), outputXML, PSI_10_COMPACT);
    }

    public static void viewPsiMi10( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);
        try {
            viewPsiMi10(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem transforming file to HTML: "+inputXML, e);
        }
    }

    public static void viewPsiMi10( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        transform(new PushbackReader(new InputStreamReader(inputXML)), outputXML, PSI_10_HTML);
    }

    public static void expandPsiMi25( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);
        try {
            expandPsiMi25(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem expanding file: "+inputXML, e);
        }
    }

    public static void expandPsiMi25( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        PushbackReader pReader = new PushbackReader(new InputStreamReader(inputXML), PsimiXmlVersionDetector.BUFFER_SIZE);

        String namespace = detectNamespace(pReader);

        String xsl;

        if (NAMESPACE_FROM_254.equals(namespace)) {
            xsl = PSI_254_EXPAND;
        } else {
            if (NAMESPACE_BEFORE_254.equals(namespace)) {
                xsl = PSI_25_EXPAND;
            } else {
                throw new IllegalArgumentException("Namespace not supported: "+namespace);
            }
        }

        transform(pReader, outputXML, xsl);
    }

    public static void compactPsiMi25( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);

        try {
            compactPsiMi25(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem compacting file: "+inputXML, e);
        }
    }

    public static void compactPsiMi25( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        PushbackReader pReader = new PushbackReader(new InputStreamReader(inputXML), PsimiXmlVersionDetector.BUFFER_SIZE);

        String namespace = detectNamespace(pReader);

        String xsl;

        if (NAMESPACE_FROM_254.equals(namespace)) {
            xsl = PSI_254_COMPACT;
        } else {
            if (NAMESPACE_BEFORE_254.equals(namespace)) {
                xsl = PSI_25_COMPACT;
            } else {
                throw new IllegalArgumentException("Namespace not supported: "+namespace);
            }
        }

        transform(pReader, outputXML, xsl);
    }

    public static void viewPsiMi25( File inputXML, File outputXML ) throws XslTransformException {
        checkFiles(inputXML, outputXML);

        try {
            viewPsiMi25(new FileInputStream(inputXML), new FileOutputStream(outputXML));
        } catch (FileNotFoundException e) {
            throw new XslTransformException("Problem transforming to HTML: "+inputXML, e);
        }
    }

    public static void viewPsiMi25( InputStream inputXML, OutputStream outputXML ) throws XslTransformException {
        PushbackReader pReader = new PushbackReader(new InputStreamReader(inputXML), PsimiXmlVersionDetector.BUFFER_SIZE);

        String namespace = detectNamespace(pReader);

        String xsl;

        if (NAMESPACE_FROM_254.equals(namespace)) {
            xsl = PSI_254_HTML;
        } else {
            if (NAMESPACE_BEFORE_254.equals(namespace)) {
                xsl = PSI_25_HTML;
            } else {
                throw new IllegalArgumentException("Namespace not supported: "+namespace);
            }
        }

        transform(pReader, outputXML, xsl);
    }

    private static void transform(PushbackReader pReader, OutputStream outputXML, String xsl) throws XslTransformException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputXML);
        runXslt( pReader, loadFromMetaInf( xsl ), outputStreamWriter);

        try {
            outputStreamWriter.close();
        } catch (IOException e) {
            throw new XslTransformException("Problem closing OutputStreamWriter", e);
        }
    }

    private static void checkFiles(File inputXML, File outputXML) {
        if ( inputXML == null ) {
            throw new IllegalArgumentException( "The given input file was null, Abort." );
        }

        if ( outputXML == null ) {
            throw new IllegalArgumentException( "The given output file was null, Abort." );
        }

        if ( !inputXML.exists() ) {
            throw new IllegalArgumentException( "The given input file didn't exist. Abort." );
        }

        if ( outputXML.exists() ) {
            throw new IllegalArgumentException( "The given output file exist. Abort." );
        }
    }

    private static String detectNamespace(PushbackReader pReader) throws XslTransformException {
        PsimiXmlVersionDetector detector = new PsimiXmlVersionDetector();
        String namespace = null;

        try {
            namespace = detector.detectNamespace(pReader);
        } catch (IOException e) {
            throw new XslTransformException("Problem reading stream", e);
        }
        return namespace;
    }
}
