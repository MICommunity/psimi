/**
 *
 */
package psidev.psi.mi.search.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.DefaultRowReader;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;
import org.hupo.psi.calimocho.tab.io.IllegalColumnException;
import org.hupo.psi.calimocho.tab.io.RowReader;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;

import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract interaction document builder.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractInteractionDocumentBuilder<T extends BinaryInteraction> implements DocumentBuilder<T> {

    private static final Log log = LogFactory.getLog( AbstractInteractionDocumentBuilder.class );

    protected static final String DEFAULT_COL_SEPARATOR = "\t";

    private ColumnBasedDocumentDefinition documentDefinition;

    public static final int DEFAULT_DOCUMENT_BUFFER_SIZE = 8196;
    private RowReader rowReader;
    private PsimiTabReader mitabReader;

    protected AbstractInteractionDocumentBuilder( ColumnBasedDocumentDefinition documentDefinition ) {
        this.documentDefinition = documentDefinition;
        this.rowReader = new DefaultRowReader(this.documentDefinition);
        this.mitabReader = new PsimiTabReader();
    }

    /**
     * @deprecated Use createDocument() methods instead
     */
    @Deprecated
    public Document createDocumentFromPsimiTabLine( String psiMiTabLine ) throws MitabLineException {
        Row row = null;
        try {
            row = rowReader.readLine(psiMiTabLine);
        } catch (IllegalRowException e) {
            throw new MitabLineException(e);
        } catch (IllegalColumnException e) {
            throw new MitabLineException(e);
        } catch (IllegalFieldException e) {
            throw new MitabLineException(e);
        }

        return createDocument( row );
    }

    public Document createDocument( T binaryInteraction ) throws MitabLineException {
        String mitabLine = MitabWriterUtils.buildLine(binaryInteraction, PsimiTabVersion.v2_5);
        final Row row;
        try {
            row = rowReader.readLine(mitabLine);
        } catch (IllegalRowException e) {
            throw new MitabLineException(e);
        } catch (IllegalColumnException e) {
            throw new MitabLineException(e);
        } catch (IllegalFieldException e) {
            throw new MitabLineException(e);
        }

        return createDocument( row );
    }

    public Document createDocument( Row row ) {
        // raw fields
        Collection<org.hupo.psi.calimocho.model.Field> interactorsA = row.getFields(InteractionKeys.KEY_ID_A);
        Collection<org.hupo.psi.calimocho.model.Field> interactorsB = row.getFields(InteractionKeys.KEY_ID_B);
        Collection<org.hupo.psi.calimocho.model.Field> altIdA = row.getFields(InteractionKeys.KEY_ALTID_A);
        Collection<org.hupo.psi.calimocho.model.Field> altIdB = row.getFields(InteractionKeys.KEY_ALTID_B);
        Collection<org.hupo.psi.calimocho.model.Field> aliasesA = row.getFields(InteractionKeys.KEY_ALIAS_A);
        Collection<org.hupo.psi.calimocho.model.Field> aliasesB = row.getFields(InteractionKeys.KEY_ALIAS_B);
        Collection<org.hupo.psi.calimocho.model.Field> detMethod = row.getFields(InteractionKeys.KEY_DETMETHOD);
        Collection<org.hupo.psi.calimocho.model.Field> pubAuthors = row.getFields(InteractionKeys.KEY_PUBAUTH);
        Collection<org.hupo.psi.calimocho.model.Field> pubId = row.getFields(InteractionKeys.KEY_PUBID);
        Collection<org.hupo.psi.calimocho.model.Field> taxidA = row.getFields(InteractionKeys.KEY_TAXID_A);
        Collection<org.hupo.psi.calimocho.model.Field> taxidB = row.getFields(InteractionKeys.KEY_TAXID_B);
        Collection<org.hupo.psi.calimocho.model.Field> interactionTypes = row.getFields(InteractionKeys.KEY_INTERACTION_TYPE);
        Collection<org.hupo.psi.calimocho.model.Field> sourceDbs = row.getFields(InteractionKeys.KEY_SOURCE);
        Collection<org.hupo.psi.calimocho.model.Field> interactionAcs = row.getFields(InteractionKeys.KEY_INTERACTION_ID);
        Collection<org.hupo.psi.calimocho.model.Field> confidence = row.getFields(InteractionKeys.KEY_CONFIDENCE);


        Document doc = new Document();

        doc.add( new Field( "id", isolateValue( interactorsA ) + " " + isolateValue( interactorsB ),
                            Field.Store.NO,
                            Field.Index.ANALYZED ) );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition( 0 ), interactorsA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(1), interactorsB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(2), altIdA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(3), altIdB );

        doc.add( new Field( "alias", isolateValue( aliasesA ) + " " + isolateValue( aliasesB ),
                            Field.Store.NO,
                            Field.Index.ANALYZED ) );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(4), aliasesA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(5), aliasesB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(6), detMethod );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(7), pubAuthors );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(8), pubId );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(9), taxidA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(10), taxidB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(11), interactionTypes );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(12), sourceDbs );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnByPosition(13), interactionAcs );
        addNonIndexedAndSortableField( doc, documentDefinition.getColumnByPosition(14), confidence );

        addAdvancedSearchFields( doc, row );

        return doc;
    }

    protected void addNonIndexedAndSortableField( Document doc, ColumnDefinition columnDefinition, Collection<org.hupo.psi.calimocho.model.Field> column ) {
        String val = buildColumn(columnDefinition, column);
        doc.add(new Field(columnDefinition.getKey(),
                val,
                Field.Store.YES,
                Field.Index.NO));
        addHelperFields( doc, columnDefinition, val, column, false );
    }

    private String buildColumn(ColumnDefinition columnDefinition, Collection<org.hupo.psi.calimocho.model.Field> column) {
        StringBuffer columnValue = new StringBuffer();

        if (column == null){
            columnValue.append(columnDefinition.getEmptyValue());
        }
        else if (column.isEmpty()){
            columnValue.append(columnDefinition.getEmptyValue());
        }
        else {
            Iterator<org.hupo.psi.calimocho.model.Field> fieldIterator = column.iterator();
            while (fieldIterator.hasNext()){
                org.hupo.psi.calimocho.model.Field field = fieldIterator.next();
                FieldFormatter formatter = columnDefinition.getFieldFormatter();
                try {
                    columnValue.append(formatter.format(field));
                } catch (IllegalFieldException e) {
                    e.printStackTrace();
                    columnValue.append(columnDefinition.getEmptyValue());
                }

                if (fieldIterator.hasNext()){
                    columnValue.append(columnDefinition.getFieldSeparator());
                }
            }
        }

        return columnValue.toString();
    }

    protected void addTokenizedAndSortableField( Document doc, ColumnDefinition columnDefinition,  Collection<org.hupo.psi.calimocho.model.Field> columnFields ) {
        if ( doc == null ) throw new NullPointerException( "doc is null" );
        if ( columnDefinition == null ) throw new NullPointerException( "columnDefinition doc is null" );

        String column = buildColumn(columnDefinition, columnFields);
        if ( column != null && (columnDefinition.getKey().equals("detmethod") || columnDefinition.getKey().equals("type"))) {
            doc.add( new Field( columnDefinition.getKey()+"_exact",
                                column,
                                Field.Store.YES,
                                Field.Index.ANALYZED ) );
            addHelperFields( doc, columnDefinition, column, columnFields, true );
        }
        else if ( column != null && !columnDefinition.getKey().equals("detmethod") && !columnDefinition.getKey().equals("type")) {
            doc.add( new Field( columnDefinition.getKey(),
                    column,
                    Field.Store.YES,
                    Field.Index.ANALYZED ) );
            addHelperFields( doc, columnDefinition, column, columnFields, false );
        }
    }

    protected void addHelperFields( Document doc, ColumnDefinition columnDefinition, String columnValue, Collection<org.hupo.psi.calimocho.model.Field> column, boolean isExact ) {
        doc.add( new Field( columnDefinition.getKey()+"_s",
                            isolateValue( column ),
                            Field.Store.NO,
                            Field.Index.NOT_ANALYZED ) );

        if (isExact) {
            doc.add( new Field(columnDefinition.getKey(),
                                columnValue,
                                Field.Store.NO,
                                Field.Index.ANALYZED ) );
            doc.add( new Field(columnDefinition.getKey(),
                                isolateValue( column ),
                                Field.Store.NO,
                                Field.Index.NOT_ANALYZED ) );
        }
    }

    protected void addAdvancedSearchFields( Document doc, Row row ) {
        String identifiers = columnsToString( row.getFields(InteractionKeys.KEY_ID_A),
                                              row.getFields(InteractionKeys.KEY_ID_B),

                                              row.getFields(InteractionKeys.KEY_ALTID_A),
                                              row.getFields(InteractionKeys.KEY_ALTID_B),

                                              row.getFields(InteractionKeys.KEY_ALIAS_A),
                                              row.getFields(InteractionKeys.KEY_ALIAS_B) );

        String species = columnsToString( row.getFields(InteractionKeys.KEY_TAXID_A),
                                          row.getFields(InteractionKeys.KEY_TAXID_B) );

        doc.add( new Field( "identifier",
                            identifiers,
                            Field.Store.NO,
                            Field.Index.ANALYZED ) );
        // this fields will be removed eventually, as we will use singular names for the searches
        doc.add( new Field( "identifiers",
                            identifiers,
                            Field.Store.NO,
                            Field.Index.ANALYZED ) );
        doc.add( new Field( "species",
                            species,
                            Field.Store.NO,
                            Field.Index.ANALYZED ) );
    }

    public String createPsimiTabLine( Document doc ) {
        if ( doc == null ) {
            throw new NullPointerException( "Document is null" );
        }

        StringBuilder sb = new StringBuilder( DEFAULT_DOCUMENT_BUFFER_SIZE );

        for ( int i = 0; i < documentDefinition.getHighestColumnPosition(); i++ ) {
            ColumnDefinition columnDefinition = documentDefinition.getColumnByPosition( i );

            if ( i > 0 ) {
                sb.append( DEFAULT_COL_SEPARATOR );
            }

            if (columnDefinition.getKey().equals("detmethod") || columnDefinition.getKey().equals("type")){
                sb.append( doc.get( columnDefinition.getKey()+"_exact" ) );
            }
            else {
                sb.append( doc.get( columnDefinition.getKey() ) );
            }
        }

        if ( log.isTraceEnabled() ) {
            if( sb.length() > DEFAULT_DOCUMENT_BUFFER_SIZE ) {
                log.trace( "StringBuilder( "+ DEFAULT_DOCUMENT_BUFFER_SIZE +" ) holding string of size " + sb.length() );
                log.trace( "\n" + sb.toString() );
            }
        }

        return sb.toString();
    }

    /**
     * Creates a BinaryInteraction from a lucene document
     *
     * @param doc the Document to use
     * @return the binary interaction
     * @throws psidev.psi.mi.tab.converter.txt2tab.MitabLineException
     *          thrown if there are syntax or other problems parsing the document/line
     */
    @Deprecated
    public T createBinaryInteraction( Document doc ) throws MitabLineException {
        try {
            return createData( doc );
        } catch (PsimiTabException e) {
            throw new MitabLineException(e);
        }
    }

    /**
     * Creates a BinaryInteraction from a lucene document
     *
     * @param doc the Document to use
     * @return the binary interaction
     * @throws psidev.psi.mi.tab.converter.txt2tab.MitabLineException
     *          thrown if there are syntax or other problems parsing the document/line
     */
    public T createData( Document doc ) throws PsimiTabException {
        String line = createPsimiTabLine( doc );
        return ( T ) this.mitabReader.readLine(line);
    }

    protected String columnsToString( Collection<org.hupo.psi.calimocho.model.Field>... columns ) {
        int estimatedLength = 0;
        for ( int i = 0; i < columns.length; i++ ) {
            Collection<org.hupo.psi.calimocho.model.Field> column = columns[i];
            if (column != null){
                estimatedLength += column.size() * 32;
            }
        }
        final StringBuilder sb = new StringBuilder( estimatedLength );

        for ( Collection<org.hupo.psi.calimocho.model.Field> column : columns ) {
            if ( column == null ) continue;

            for ( org.hupo.psi.calimocho.model.Field field : column ) {
                sb.append( " " );
                String value = field.get(CalimochoKeys.VALUE);
                sb.append( value != null ? value : "-" );

                String text = field.get(CalimochoKeys.TEXT);
                if ( text != null ) {
                    sb.append( " " );
                    sb.append( text );
                }
            }
        }

        if ( log.isTraceEnabled() ) {
            if( sb.length() > estimatedLength )
                log.trace( "StringBuilder( "+ estimatedLength +" ) holding string of size " + sb.length() );
        }

        return sb.toString().trim();
    }

    /**
     * Gets only the value part of a column
     *
     * @param column
     * @return
     */
    protected String isolateValue( Collection<org.hupo.psi.calimocho.model.Field> column ) {
        if (column == null){
           return "-";
        }
        final int estimatedSize = column.size() * 128;
        final StringBuilder sb = new StringBuilder( estimatedSize );

        for ( Iterator<org.hupo.psi.calimocho.model.Field> iterator = column.iterator(); iterator.hasNext(); ) {
            org.hupo.psi.calimocho.model.Field field = iterator.next();

            String value = field.get(CalimochoKeys.VALUE);

            if (value != null){
                sb.append( field.get(CalimochoKeys.VALUE) );

                if ( iterator.hasNext() ) {
                    sb.append( " " );
                }
            }
        }
        if ( log.isTraceEnabled() ) {
            if( sb.length() > estimatedSize )
                log.trace( "StringBuilder( "+ estimatedSize +" ) holding string of size " + sb.length() );
        }
        return sb.toString();
    }

    public ColumnBasedDocumentDefinition getDocumentDefinition() {
        return documentDefinition;
    }

    public RowReader getRowReader() {
        return rowReader;
    }

    public PsimiTabReader getMitabReader() {
        return mitabReader;
    }
}