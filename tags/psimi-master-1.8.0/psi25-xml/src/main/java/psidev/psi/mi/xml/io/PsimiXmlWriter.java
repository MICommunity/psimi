/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.PsimiXmlWriterException;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.EntrySet;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface PsimiXmlWriter {
    
    void write( EntrySet mEntrySet, File file ) throws PsimiXmlWriterException;

    void write( EntrySet mEntrySet, OutputStream os ) throws PsimiXmlWriterException;

    void write( EntrySet mEntrySet, java.io.Writer writer ) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException;

    void write( EntrySet mEntrySet, PrintStream ps ) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException;

    String getAsString( EntrySet mEntrySet ) throws PsimiXmlWriterException;
}
