package psidev.psi.mi.validator.client.gui.view.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.client.gui.view.messages.ReportFrame;
import psidev.psi.tools.validator.Validator;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

/**
 * JTable displaying the result of a Validator run. <br/> The adapted source code was found at:
 * http://iharder.sourceforge.net/filedrop/
 *
 * @author Robert Harder, Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17-Jan-2006</pre>
 */
public class ValidatorTable extends JPanel {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( ValidatorTable.class );

    ///////////////////////////////
    // Constants

    private static final boolean DEBUG = false;

    //////////////////////////////
    // Instance variables

    private ValidatorTableModel model;

    private Validator validator;

    ///////////////////////////////
    // Constructor

    public ValidatorTable() {
        super( new GridLayout( 1, 0 ) );

        model = new ValidatorTableModel();

        // setup tooltip for validation report
        final JTable table = new JTable( model ) {
            public Component prepareRenderer( TableCellRenderer renderer, int rowIndex, int vColIndex ) {

                Component c = super.prepareRenderer( renderer, rowIndex, vColIndex );

                ValidatorTableRow tableRow = model.getTableRowAt( rowIndex );
                if ( tableRow.getFilteredMessages().size() > 0 ) {

                    if ( c instanceof JComponent ) {
                        JComponent jc = ( JComponent ) c;

                        jc.setToolTipText( "Click on the cell to open validation report" );
                    }
                }
                return c;
            }
        };

        // setup cell renderer for error level
        // if there are any messages, then display the cell in red, otherwise in green.
        table.setDefaultRenderer( Integer.class, new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent( JTable table,
                                                            Object value,
                                                            boolean isSelected,
                                                            boolean hasFocus,
                                                            int row,
                                                            int column ) {
                Component cell = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );

                if ( value instanceof Integer ) {

                    // set alignement to center
                    setHorizontalAlignment( JTextField.CENTER );

                    ValidatorTableRow tableRow = model.getTableRowAt( row );
                    ValidationStatus status = tableRow.getStatus();

                    Integer amount = ( Integer ) value;
                    if ( amount.intValue() > 0 || status.equals( ValidationStatus.FAILED ) ) {
                        // at least one validation message OR failed validation.
                        cell.setBackground( new Color( 238, 0, 0 ) ); // dark red
                    } else if ( amount.intValue() == 0 ) {
                        cell.setBackground( new Color( 0, 134, 100 ) ); // dark green
                    }

                    cell.setForeground( Color.white );
                }

                return cell;
            }
        } );

        table.setPreferredScrollableViewportSize( new Dimension( 700, 150 ) );

        // Enable cell selection in that table
        table.setColumnSelectionAllowed( false );
        table.setRowSelectionAllowed( false );
        table.setCellSelectionEnabled( true );
        table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        // setup selection listener
        table.addMouseListener( new MouseAdapter() {
            public void mouseClicked
                    ( MouseEvent
                            e ) {
                // get row index
                int row = table.getSelectedRow();
                // get column index
                int col = table.getSelectedColumn();

                if ( DEBUG ) {
                    log.info( "MouseListener[ row:" + row + " col:" + col + " ]" );
                }

                if ( col == 2 ) {
                    // open a report frame when there are more than 0 message.
                    Integer count = ( Integer ) model.getValueAt( row, col );
                    if ( count > 0 ) {
                        // open report
                        ValidatorTableRow tableRow = model.getTableRowAt( row );
                        JFrame reportFrame = new ReportFrame( tableRow.getFile().getAbsolutePath(), tableRow.getFilteredMessages() );
                        reportFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                    }
                }
            }
        } );

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane( table );

        //Add the scroll pane to this panel.
        add( scrollPane );
    }

    public ValidatorTable( Validator validator ) {
        this();
        this.validator = validator;
    }

    public void removeAllRows() {
        model.removeAllRows();
        model.fireTableDataChanged();
    }

    public void addTableRow( ValidatorTableRow row ) {
        model.addRow( row );

        // refresh the table content
        updateRow( model.getRowCount() );

        // Start validation if it has not been done yet.
        if ( row.getStatus() == ValidationStatus.NOT_STARTED ) {

            row.setStatus( ValidationStatus.IN_PROGRESS );
            // refresh row in the view
            updateRow( row );

            try {

                FileInputStream f = new FileInputStream( row.getFile() );
                Collection<ValidatorMessage> validatorMessages = validator.validate( f );

                if ( DEBUG ) {
                    log.info( validatorMessages.size() + " messages found." );
                }

                row.setMessages( validatorMessages );

                row.setStatus( ValidationStatus.COMPLETED );
                // refresh row in the view
                updateRow( row );

                if ( row.isOpen() && row.getFilteredMessages().size() > 0 ) {
                    // if open requested and any message, then open report.
                    JFrame reportFrame = new ReportFrame( row.getFile().getAbsolutePath(), validatorMessages );
                    reportFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                }

            } catch ( FileNotFoundException e ) {
                // refresh row in the view
                row.setStatus( ValidationStatus.FAILED );
                updateRow( row );

                log.error( "Could not find the file", e );

                JOptionPane msg = new JOptionPane( "Could not find file: " + row.getFile().getName(), JOptionPane.ERROR_MESSAGE );
                JDialog dialog = msg.createDialog( this, "IO Error" );
                dialog.setResizable( true );
                dialog.pack();
                dialog.setVisible( true );

            } catch ( ValidatorException e ) {
                // refresh row in the view
                row.setStatus( ValidationStatus.FAILED );
                updateRow( row );

                log.error( "A validation error occured.", e );

                JOptionPane msg = new JOptionPane( e.getMessage(), JOptionPane.ERROR_MESSAGE );
                JDialog dialog = msg.createDialog( this, "Validator Error - " + row.getFile().getName() );
                dialog.setResizable( true );
                dialog.pack();
                dialog.setVisible( true );

            } catch ( Exception e ) {
                row.setStatus( ValidationStatus.FAILED );
                updateRow( row );

                log.error( "An unexpected error occured, see nested messages.", e );

                JOptionPane msg = new JOptionPane( e.getMessage(), JOptionPane.ERROR_MESSAGE );
                JDialog dialog = msg.createDialog( this, "Unexpected Error - " + row.getFile().getName() );
                dialog.setResizable( true );
                dialog.pack();
                dialog.setVisible( true );
            }
        }
    }

    public void updateRow( int index ) {
        // refresh the table content (a specific row)
        model.fireTableRowsInserted( index, index );
    }

    public int getIndexOfRow( ValidatorTableRow row ) {
        return model.getIndexOfRow( row );
    }

    public void updateRow( ValidatorTableRow row ) {
        if ( DEBUG ) {
            log.info( "=====================================================================" );
            log.info( "Updating row for file: " + row.getFile().getAbsolutePath() );
            log.info( row );
        }
        updateRow( model.getIndexOfRow( row ) );
    }
}