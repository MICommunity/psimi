/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.xml;

import psidev.psi.mi.xml.io.impl.PsimiXmlLightweightWriter253;
import psidev.psi.mi.xml.io.impl.PsimiXmlLightweightWriter254;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Availability;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Source;

import java.io.File;
import java.io.Writer;
import java.util.List;

/**
 * Lightweight XML writter that allows to build piece by piece an XML file. Providing memory efficient alternative to
 * having the wholeobject model loaded at once. This works particularly well when using the PsimiXmlLightweightReader.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlLightweightWriter implements psidev.psi.mi.xml.io.PsimiXmlLightweightWriter {

    private static final PsimiXmlVersion DEFAULT_VERSION = PsimiXmlVersion.VERSION_254;

    private psidev.psi.mi.xml.io.PsimiXmlLightweightWriter writer;

    public PsimiXmlLightweightWriter( File outputFile ) throws PsimiXmlWriterException {
        this(outputFile, DEFAULT_VERSION);
    }

    public PsimiXmlLightweightWriter( Writer writer ) {
        this(writer, DEFAULT_VERSION);
    }

    public PsimiXmlLightweightWriter( File outputFile, PsimiXmlVersion version ) throws PsimiXmlWriterException {
        switch (version) {
            case VERSION_254:
                this.writer = new PsimiXmlLightweightWriter254(outputFile);
                break;
            case VERSION_253:
                this.writer = new PsimiXmlLightweightWriter253(outputFile);
                break;
            case VERSION_25_UNDEFINED:
                this.writer = new PsimiXmlLightweightWriter253(outputFile);
                break;
        }
    }

    public PsimiXmlLightweightWriter( Writer writer, PsimiXmlVersion version ) {
         switch (version) {
            case VERSION_254:
                this.writer = new PsimiXmlLightweightWriter254(writer);
                break;
            case VERSION_253:
                this.writer = new PsimiXmlLightweightWriter253(writer);
                break;
            case VERSION_25_UNDEFINED:
                this.writer = new PsimiXmlLightweightWriter253(writer);
                break;
        }
    }

    public void writeStartDocument() throws PsimiXmlWriterException {
        writer.writeStartDocument();
    }

    public void writeStartEntry(Source source, List<Availability> availabilities) throws PsimiXmlWriterException {
        writer.writeStartEntry(source, availabilities);
    }

    public void writeInteraction(Interaction interaction) throws PsimiXmlWriterException {
        writer.writeInteraction(interaction);
    }

    public void writeEndEntry(List<Attribute> attributes) throws PsimiXmlWriterException {
        writer.writeEndEntry(attributes);
    }

    public void writeEndDocument() throws PsimiXmlWriterException {
        writer.writeEndDocument();
    }

    public void closeOutputFile() throws PsimiXmlWriterException {
        writer.closeOutputFile();
    }
}