/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.tab.util;

/**
 * Escapes columns and elements for the MITAB format.
 * <p>
 * In MITAB we find columns, delimited by tab. Each column is formed by one or more fields,
 * delimited by pipe. Fields can have different formats, but they are constituted by elements.
 * </p>
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: MitabEscapeUtils.java 2 2009-06-01 15:46:10Z brunoaranda $
 */
public class MitabEscapeUtils {

    private MitabEscapeUtils() {
        // nothing
    }

    /**
     * Replaces tabs by spaces, because the tab is a delimiter.
     * @param columnText The column text to escape
     * @return The escaped text
     */
    public static String escapeColumn(String columnText) {
        if (columnText == null) return null;

        String escapedColumn = columnText.replaceAll("\\t", " ");
        escapedColumn = escapedColumn.replaceAll("\\n", " ");
        return escapedColumn;
    }

    /**
     * Escapes an element, surrounding it with quotes, if one of the reserver characters are used (braces, quotes or pipes).
     * @param text The text to escape
     * @return The escaped text
     */
    public static String escapeFieldElement(String text) {
        if (text == null) return null;

        String escapedText = text.replaceAll("\\t", " ");

        boolean escape = false;

        if (escapedText.contains("\"")) {
            escape = true;
            escapedText = escapedText.replaceAll("\"", "\\\\\"");
        }

        if (escapedText.contains("|") ||
            escapedText.contains("(") ||
            escapedText.contains(")") ||
            escapedText.contains(":")) {
            escape = true;
        }

        if (escape) {
            escapedText = "\""+escapedText+"\"";
        }

        return escapedText;
    }

}

