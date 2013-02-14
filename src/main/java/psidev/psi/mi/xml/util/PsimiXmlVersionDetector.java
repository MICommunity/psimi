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
package psidev.psi.mi.xml.util;

import psidev.psi.mi.xml.PsimiXmlVersion;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * XML version detector.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsimiXmlVersionDetector {

    private static final String LEVEL_ATT = "level";
    private static final String VERSION_ATT = "version";
    private static final String MINOR_VERSION_ATT = "minorVersion";
    public static final int BUFFER_SIZE = 512;

    public PsimiXmlVersionDetector() {
    }

    public PsimiXmlVersion detectVersion( PushbackReader reader ) throws IOException {

        char[] buffer = new char[BUFFER_SIZE];

        // read BUFFER_SIZE into the buffer
        int c = reader.read( buffer, 0, BUFFER_SIZE );

        // build a string representation for it
        final String sb = String.valueOf( buffer );

        String levelStr = readElementValue(sb, LEVEL_ATT);
        String versionStr = readElementValue(sb, VERSION_ATT);
        String minorVersionStr = readElementValue(sb, MINOR_VERSION_ATT);

        reader.unread( buffer, 0, c );

        if ("2".equals(levelStr) && "5".equals(versionStr)) {

            if ( "3".equals( minorVersionStr ) ) {
                return PsimiXmlVersion.VERSION_253;
            } else if ( "4".equals( minorVersionStr ) ) {
                return PsimiXmlVersion.VERSION_254;
            } else {
                return PsimiXmlVersion.VERSION_25_UNDEFINED;
            }
        } else {
            throw new IllegalArgumentException("Version not supported: Level="+levelStr+" Version="+versionStr+" MinorVersion="+minorVersionStr);
        }
    }

    public String detectNamespace( PushbackReader reader ) throws IOException {

        char[] buffer = new char[BUFFER_SIZE];

        // read BUFFER_SIZE into the buffer
        int c = reader.read( buffer, 0, BUFFER_SIZE );

        // build a string representation for it
        final String sb = String.valueOf( buffer );
        String namespace = readElementValue(sb, "xmlns");

        reader.unread( buffer, 0, c );

        return namespace;
    }

    private String readElementValue(String sb, String elementName) {
        String value = null;

        if ( sb.indexOf( elementName ) > -1 ) {
            int verindex = sb.lastIndexOf( elementName+"=" ) + ( elementName + "=\"" ).length();
            String textFromElement = sb.substring( verindex );
            value = textFromElement.substring(0, textFromElement.indexOf("\""));
        }
        return value;
    }
}
