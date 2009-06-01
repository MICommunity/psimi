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
package psidev.psi.mi.tab.model.builder;

import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.tab.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility methods to help with parsing fields and columns.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public final class ParseUtils {

    private ParseUtils() {}

    /**
     * <p>Processes an String and splits using a set of delimiters.
     * If the delimiter is in a group surrounded by quotes, don't split that group.
     * Quotes in the String must be escaped using a backlash if that quote does not delimit a group.</p>
     *
     * <pre>
     *       aaaaa|bbbb  (delimiter '|')          = ["aaaaaa", "bbbbbb"]
     *       "aaa|aaa"|bbbbb  (delimiter '|)      = ["aaa|aaa", "bbbbb"]
     *       "aa\"a|a\"aa"|bbbbb  (delimiter '|)  = ["aa"a|a"aa", "bbbbb"]
     * </pre>
     *
     *
     * @param str The string to split
     * @param delimiters The delimiters to use
     * @param removeUnescapedQuotes remove unsecaped quotes.
     * @return An array containing the groups after splitting
     */
    public static String[] quoteAwareSplit(String str, char[] delimiters, boolean removeUnescapedQuotes) {
        if (str == null) {
            throw new NullPointerException("Null String to create Field");
        }

        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty String passed to create Field");
        }

        if (delimiters == null) {
            throw new NullPointerException("Null delimiters to create Field");
        }

        if (delimiters.length == 0) {
            throw new IllegalArgumentException("At least one delimiter char is needed");
        }

        List<String> groups = new LinkedList<String>();

        StringBuilder currGroup = new StringBuilder(str.length());

        final char[] chars = str.toCharArray();

        boolean withinQuotes = false;
        boolean previousCharIsEscape = false;

        for (int i=0; i<chars.length; i++) {
            char c = chars[i];

            boolean markedAsEscape = false;

            if (c == '"') {
                if (withinQuotes) {
                    if (previousCharIsEscape) {
                        if (!removeUnescapedQuotes) {
                            currGroup.append("\\");
                        }
                        currGroup.append(c);
                    } else if (currGroup.length() > 0) {
                        withinQuotes = !withinQuotes;
                    }
                } else {
                    withinQuotes = true;
                }

                if (!removeUnescapedQuotes && !previousCharIsEscape) {
                    currGroup.append(c);
                }

            } else if (arrayContains(delimiters, c)) {
                if (currGroup.length() > 0) {
                    if (!withinQuotes) {
                        groups.add(currGroup.toString());
                        // Note: the length of the stringbuilder can only be smaller, let's reuse the existing one.
                        currGroup.setLength( 0 );
                    } else {
                        currGroup.append(c);
                    }
                } else if (withinQuotes) {
                    currGroup.append(c);
                }
            } else if (c == '\\') {
                if (withinQuotes) {
                    previousCharIsEscape = true;
                    markedAsEscape = true;
                } else {
                    currGroup.append(c);
                }
            } else {
                currGroup.append(c);
            }

            if (!markedAsEscape) {
                previousCharIsEscape = false;
            }
        }

        if (currGroup.length() > 0) {
            groups.add(currGroup.toString());
        }

        return groups.toArray(new String[groups.size()]);
    }


    private static boolean arrayContains(char[] chars, char cToFind) {
        for (char c : chars) {
            if (c == cToFind) {
                return true;
            }
        }

        return false;
    }

    public static String[] columnSplit(String columnString) {
        return quoteAwareSplit(columnString, new char[]{'|'}, false);
    }

    // columns from String
    //////////////////////
    public static Column createColumnFromString(String xrefColumn, FieldBuilder fieldBuilder) {
        String[] fieldStrs = columnSplit(xrefColumn);

        List<Field> fields = new LinkedList<Field>();

        for (String fieldStr : fieldStrs) {
            ParseUtils.addFieldToList(fields, fieldBuilder.createField(fieldStr));
        }

        return new Column(fields);
    }

    // column -> object
    //////////////////////

    public static List<CrossReference> createCrossReferences(Column column) {
        List<CrossReference> xrefs = new LinkedList<CrossReference>();

        for (Field field : column.getFields()) {
            try {
                xrefs.add(createCrossReference(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating cross reference from field: "+field, e);
            }
        }

        return xrefs;
    }

    public static List<InteractionDetectionMethod> createInteractionDetectionMethods(Column column) {
        List<InteractionDetectionMethod> xrefs = new LinkedList<InteractionDetectionMethod>();

        for (Field field : column.getFields()) {
            try {
                xrefs.add(createInteractionDetectionMethod(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating interaction detection method from field: " + field, e);
            }
        }

        return xrefs;
    }

    public static List<InteractionType> createInteractionTypes(Column column) {
        List<InteractionType> xrefs = new ArrayList<InteractionType>();

        for (Field field : column.getFields()) {
            try {
                xrefs.add(createInteractionType(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating interaction type from field: " + field, e);
            }
        }

        return xrefs;
    }

    // field -> object
    //////////////////////////

    private static CrossReference createCrossReference(Field field) {
        CrossReference xref = new CrossReferenceImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    public static InteractionDetectionMethod createInteractionDetectionMethod(Field field) {
        InteractionDetectionMethod xref = new InteractionDetectionMethodImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    public static InteractionType createInteractionType(Field field) {
        InteractionType xref = new InteractionTypeImpl();
        populateCrossReference(field, xref);
        return xref;
    }

    private static void populateCrossReference(Field field, CrossReference xref) {
        xref.setDatabase(field.getType());
        xref.setIdentifier(field.getValue());
        xref.setText(field.getDescription());
    }

    public static List<Alias> createAliases(Column column) {
        List<Alias> aliases = new ArrayList<Alias>();

        for (Field field : column.getFields()) {
            aliases.add(createAlias(field));
        }

        return aliases;
    }

    public static Alias createAlias(Field field) {
        Alias alias = new AliasImpl();
        alias.setDbSource(field.getType());
        alias.setName(field.getValue());
        alias.setAliasType(field.getDescription());

        return alias;
    }

    public static List<Author> createAuthors(Column column) {
        List<Author> authors = new ArrayList<Author>();

        for (Field field : column.getFields()) {
            authors.add(createAuthor(field));
        }

        return authors;
    }

    public static Author createAuthor(Field field) {
        return new AuthorImpl(field.getValue());
    }

    public static Organism createOrganism(Column column) {
        return new OrganismImpl(createCrossReferences(column));
    }

    public static List<Confidence> createConfidenceValues(Column column) {
        List<Confidence> confidences = new ArrayList<Confidence>();

        for (Field field : column.getFields()) {
            try {
                confidences.add(createConfidence(field));
            } catch (Throwable e) {
                throw new IllegalFormatException("Problem creating confidence from field: " + field, e);
            }
        }

        return confidences;
    }

    public static Confidence createConfidence(Field field) {
        return new ConfidenceImpl(field.getType(), field.getValue(), field.getDescription());
    }

    // object or fields -> column
    ////////////////////////

    public static Column createColumnFromOrganism( Organism organism ) {
        Column column;
        
        if( organism != null ) {
            column = createColumnFromCrossReferences( organism.getIdentifiers() );
        } else {
            column = new Column();
        }
        return column;
    }

    public static Column createColumnFromConfidences( Collection<Confidence> confidences) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( Confidence confidence :  confidences) {
            addFieldToList(fields, createFieldFromConfidence( confidence ));
        }
        return new Column( fields );
    }

    public static Field createFieldFromConfidence( Confidence confidence ) {
        return new Field( confidence.getType(), confidence.getValue(), confidence.getText());
    }

    public static Column createColumnFromInteractionTypes( Collection<InteractionType> types ) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( InteractionType type : types ) {
            addFieldToList(fields, createFieldFromCrossReference( type ));
        }
        return new Column( fields );
    }

    public static Column createColumnFromDetectionMethods( Collection<InteractionDetectionMethod> methods ) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( InteractionDetectionMethod method : methods ) {
            addFieldToList(fields, createFieldFromCrossReference( method ));
        }
        return new Column( fields );
    }

    public static Column createColumnFromAuthors( Collection<Author> authors ) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( Author author : authors ) {
            Field field = createFieldFromAuthor(author);
            addFieldToList(fields, field);
        }
        return new Column( fields );
    }

    public static Field createFieldFromAuthor( Author author ) {
        return new Field( author.getName() );
    }

    public static Column createColumnFromCrossReferences( Collection<CrossReference> xrefs ) {
        if (xrefs == null) return new Column();
        return createColumnFromCrossReferences(xrefs.toArray(new CrossReference[xrefs.size()]));
    }

    public static Column createColumnFromCrossReferences( CrossReference ... xrefs ) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( CrossReference xref : xrefs ) {
            addFieldToList(fields, createFieldFromCrossReference( xref ));
        }
        return new Column( fields );
    }

    public static Field createFieldFromCrossReference( CrossReference xref ) {
        if (xref == null) return null;
        return new Field( xref.getDatabase(), xref.getIdentifier(), xref.getText() );
    }

    public static Column createColumnFromAliases( Collection<Alias> aliases ) {
        final LinkedList<Field> fields = new LinkedList<Field>();
        for ( Alias alias : aliases ) {
            addFieldToList(fields, createFieldFromAlias( alias ));
        }
        return new Column( fields );
    }

    public static Field createFieldFromAlias( Alias alias ) {
        return new Field( alias.getDbSource(), alias.getName(), alias.getAliasType() );
    }

    public static void addFieldToList(List<Field> fields, Field field) {
        if (field != null) {
            fields.add(field);
        }
    }
}
