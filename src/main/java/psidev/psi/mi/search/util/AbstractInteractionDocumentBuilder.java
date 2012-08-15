/**
 *
 */
package psidev.psi.mi.search.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.*;

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

    private DocumentDefinition<?> documentDefinition;

    public static final int DEFAULT_DOCUMENT_BUFFER_SIZE = 8196;

    protected AbstractInteractionDocumentBuilder( DocumentDefinition<?> documentDefinition ) {
        this.documentDefinition = documentDefinition;
    }

    /**
     * @deprecated Use createDocument() methods instead
     */
    @Deprecated
    public Document createDocumentFromPsimiTabLine( String psiMiTabLine ) throws MitabLineException {
        final RowBuilder rowBuilder = documentDefinition.createRowBuilder();
        final Row row = rowBuilder.createRow( psiMiTabLine );

        return createDocument( row );
    }

    public Document createDocument( T binaryInteraction ) {
        final InteractionRowConverter interactionRowConverter = documentDefinition.createInteractionRowConverter();
        final Row row = interactionRowConverter.createRow( binaryInteraction );

        return createDocument( row );
    }

    public Document createDocument( Row row ) {
        // raw fields
        Column interactorsA = row.getColumnByIndex( MitabDocumentDefinition.ID_INTERACTOR_A );
        Column interactorsB = row.getColumnByIndex( MitabDocumentDefinition.ID_INTERACTOR_B );
        Column altIdA = row.getColumnByIndex( MitabDocumentDefinition.ALTID_INTERACTOR_A );
        Column altIdB = row.getColumnByIndex( MitabDocumentDefinition.ALTID_INTERACTOR_B );
        Column aliasesA = row.getColumnByIndex( MitabDocumentDefinition.ALIAS_INTERACTOR_A );
        Column aliasesB = row.getColumnByIndex( MitabDocumentDefinition.ALIAS_INTERACTOR_B );
        Column detMethod = row.getColumnByIndex( MitabDocumentDefinition.INT_DET_METHOD );
        Column pubAuthors = row.getColumnByIndex( MitabDocumentDefinition.PUB_AUTH );
        Column pubId = row.getColumnByIndex( MitabDocumentDefinition.PUB_ID );
        Column taxidA = row.getColumnByIndex( MitabDocumentDefinition.TAXID_A );
        Column taxidB = row.getColumnByIndex( MitabDocumentDefinition.TAXID_B );
        Column interactionTypes = row.getColumnByIndex( MitabDocumentDefinition.INT_TYPE );
        Column sourceDbs = row.getColumnByIndex( MitabDocumentDefinition.SOURCE );
        Column interactionAcs = row.getColumnByIndex( MitabDocumentDefinition.INTERACTION_ID );
        Column confidence = row.getColumnByIndex( MitabDocumentDefinition.CONFIDENCE );


        Document doc = new Document();

        doc.add( new Field( "id", isolateValue( interactorsA ) + " " + isolateValue( interactorsB ),
                            Field.Store.NO,
                            Field.Index.TOKENIZED ) );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ID_INTERACTOR_A ), interactorsA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ID_INTERACTOR_B ), interactorsB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ALTID_INTERACTOR_A ), altIdA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ALTID_INTERACTOR_B ), altIdB );

        doc.add( new Field( "alias", isolateValue( aliasesA ) + " " + isolateValue( aliasesB ),
                            Field.Store.NO,
                            Field.Index.TOKENIZED ) );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ALIAS_INTERACTOR_A ), aliasesA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.ALIAS_INTERACTOR_B ), aliasesB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.INT_DET_METHOD ), detMethod );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.PUB_AUTH ), pubAuthors );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.PUB_ID ), pubId );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.TAXID_A ), taxidA );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.TAXID_B ), taxidB );

        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.INT_TYPE ), interactionTypes );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.SOURCE ), sourceDbs );
        addTokenizedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.INTERACTION_ID ), interactionAcs );
        addNonIndexedAndSortableField( doc, documentDefinition.getColumnDefinition( MitabDocumentDefinition.CONFIDENCE ), confidence );

        addAdvancedSearchFields( doc, row );

        return doc;
    }

    protected void addNonIndexedAndSortableField( Document doc, ColumnDefinition columnDefinition, Column column ) {
        doc.add( new Field( columnDefinition.getShortName(),
                            column.toString(),
                            Field.Store.YES,
                            Field.Index.NO ) );
        addHelperFields( doc, columnDefinition, column );
    }

    protected void addTokenizedAndSortableField( Document doc, ColumnDefinition columnDefinition, Column column ) {
        if ( doc == null ) throw new NullPointerException( "doc is null" );
        if ( columnDefinition == null ) throw new NullPointerException( "columnDefinition doc is null" );

        if ( column != null ) {
            doc.add( new Field( columnDefinition.getShortName(),
                                column.toString(),
                                Field.Store.YES,
                                Field.Index.TOKENIZED ) );
            addHelperFields( doc, columnDefinition, column );
        }
    }

    protected void addHelperFields( Document doc, ColumnDefinition columnDefinition, Column column ) {
        doc.add( new Field( columnDefinition.getSortableColumnName(),
                            isolateValue( column ),
                            Field.Store.NO,
                            Field.Index.UN_TOKENIZED ) );

        if (columnDefinition.getShortName().endsWith("_exact")) {
            String fieldName = columnDefinition.getShortName().replaceAll("_exact", "");
            doc.add( new Field(fieldName,
                                column.toString(),
                                Field.Store.NO,
                                Field.Index.TOKENIZED ) );
            doc.add( new Field(fieldName,
                                isolateValue( column ),
                                Field.Store.NO,
                                Field.Index.UN_TOKENIZED ) );
        }
    }

    protected void addAdvancedSearchFields( Document doc, Row row ) {
        String identifiers = columnsToString( row.getColumnByIndex( MitabDocumentDefinition.ID_INTERACTOR_A ),
                                              row.getColumnByIndex( MitabDocumentDefinition.ID_INTERACTOR_B ),

                                              row.getColumnByIndex( MitabDocumentDefinition.ALTID_INTERACTOR_A ),
                                              row.getColumnByIndex( MitabDocumentDefinition.ALTID_INTERACTOR_B ),

                                              row.getColumnByIndex( MitabDocumentDefinition.ALIAS_INTERACTOR_A ),
                                              row.getColumnByIndex( MitabDocumentDefinition.ALIAS_INTERACTOR_B ) );

        String species = columnsToString( row.getColumnByIndex( MitabDocumentDefinition.TAXID_A ),
                                          row.getColumnByIndex( MitabDocumentDefinition.TAXID_B ) );

        doc.add( new Field( "identifier",
                            identifiers,
                            Field.Store.NO,
                            Field.Index.TOKENIZED ) );
        // this fields will be removed eventually, as we will use singular names for the searches
        doc.add( new Field( "identifiers",
                            identifiers,
                            Field.Store.NO,
                            Field.Index.TOKENIZED ) );
        doc.add( new Field( "species",
                            species,
                            Field.Store.NO,
                            Field.Index.TOKENIZED ) );
    }

    public String createPsimiTabLine( Document doc ) {
        if ( doc == null ) {
            throw new NullPointerException( "Document is null" );
        }

        StringBuilder sb = new StringBuilder( DEFAULT_DOCUMENT_BUFFER_SIZE );

        for ( int i = 0; i < documentDefinition.getColumnsCount(); i++ ) {
            ColumnDefinition columnDefinition = documentDefinition.getColumnDefinition( i );

            if ( i > 0 ) {
                sb.append( DEFAULT_COL_SEPARATOR );
            }

            sb.append( doc.get( columnDefinition.getShortName() ) );
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
        return createData( doc );
    }

    /**
     * Creates a BinaryInteraction from a lucene document
     *
     * @param doc the Document to use
     * @return the binary interaction
     * @throws psidev.psi.mi.tab.converter.txt2tab.MitabLineException
     *          thrown if there are syntax or other problems parsing the document/line
     */
    public T createData( Document doc ) {
        String line = createPsimiTabLine( doc );
        return ( T ) documentDefinition.interactionFromString( line );
    }

    protected String columnsToString( Column... columns ) {
        int estimatedLength = 0;
        for ( int i = 0; i < columns.length; i++ ) {
            Column column = columns[i];
            estimatedLength += column.getFields().size() * 32;
        }
        final StringBuilder sb = new StringBuilder( estimatedLength );

        for ( Column column : columns ) {
            if ( column == null ) continue;

            for ( psidev.psi.mi.tab.model.builder.Field field : column.getFields() ) {
                sb.append( " " );
                sb.append( field.getValue() );

                if ( field.getDescription() != null ) {
                    sb.append( " " );
                    sb.append( field.getDescription() );
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
    protected String isolateValue( Column column ) {
        final int estimatedSize = column.getFields().size() * 128;
        final StringBuilder sb = new StringBuilder( estimatedSize );

        for ( Iterator<psidev.psi.mi.tab.model.builder.Field> iterator = column.getFields().iterator(); iterator.hasNext(); ) {
            psidev.psi.mi.tab.model.builder.Field field = iterator.next();

            sb.append( field.getValue() );

            if ( iterator.hasNext() ) {
                sb.append( " " );
            }
        }
        if ( log.isTraceEnabled() ) {
            if( sb.length() > estimatedSize )
                log.trace( "StringBuilder( "+ estimatedSize +" ) holding string of size " + sb.length() );
        }
        return sb.toString();
    }

    public DocumentDefinition<?> getDocumentDefinition() {
        return documentDefinition;
    }
}