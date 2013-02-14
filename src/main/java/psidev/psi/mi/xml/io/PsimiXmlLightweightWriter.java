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
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Availability;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Source;

import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface PsimiXmlLightweightWriter {
    void writeStartDocument() throws PsimiXmlWriterException;

    void writeStartEntry( Source source, List<Availability> availabilities ) throws PsimiXmlWriterException;

    void writeInteraction( Interaction interaction ) throws PsimiXmlWriterException;

    void writeEndEntry( List<Attribute> attributes ) throws PsimiXmlWriterException;

    void writeEndDocument() throws PsimiXmlWriterException;

    void closeOutputFile() throws PsimiXmlWriterException;
}
