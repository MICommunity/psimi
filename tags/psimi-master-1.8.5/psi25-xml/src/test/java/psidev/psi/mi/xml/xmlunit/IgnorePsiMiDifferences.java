/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.xmlunit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.*;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Sep-2006</pre>
 */
public class IgnorePsiMiDifferences implements DifferenceListener {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( IgnorePsiMiDifferences.class );


    private static final Set tagNamesToIgnore = new HashSet() {
        {
            // under featureRange
            add( "isLink" );

            // under interaction
            add( "modelled" );
            add( "intraMolecular" );
            add( "negative" );
        }
    };


    public int differenceFound( Difference difference ) {

        log.info( "========================= differenceFound ======================" );
        int response = RETURN_ACCEPT_DIFFERENCE;

        int differenceId = difference.getId();

        log.info( "Received DifferenceConstants." + differenceId );


        if ( DifferenceConstants.ATTR_VALUE_ID == differenceId ) { // #3
            log.info( "Reason: " + DifferenceConstants.ATTR_VALUE );

            Node nodeControl = difference.getControlNodeDetail().getNode();
            Node nodeTest = difference.getTestNodeDetail().getNode();

            if ( nodeControl.getNodeName().equals( "xsi:schemaLocation" ) ) {
                // xsi:schemaLocation
                log.info( "Ignore schema location difference." );
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }

        } else if ( DifferenceConstants.ELEMENT_TAG_NAME_ID == differenceId ) { // #10

            log.info( "Reason: " + DifferenceConstants.ELEMENT_TAG_NAME );
            // ignore when a tag value is compared our special tags having a value false.

            Node nodeControl = difference.getControlNodeDetail().getNode();
            Node nodeTest = difference.getTestNodeDetail().getNode();
            if ( isOptionalNode( nodeControl ) || isOptionalNode( nodeTest ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }

        } else if ( DifferenceConstants.ELEMENT_NUM_ATTRIBUTES_ID == differenceId ) { // #11
            log.info( "Reason: " + DifferenceConstants.ELEMENT_NUM_ATTRIBUTES );

            Node nodeControl = difference.getControlNodeDetail().getNode();
            Node nodeTest = difference.getTestNodeDetail().getNode();
            if ( isOptionalNode( nodeControl ) || isOptionalNode( nodeTest ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            } else {
                // if <entrySet> lacks minorVersion, that no big deal
                if ( nodeControl.getNodeName().equals( "entrySet" ) ) {

                    log.info( "Checking if the attribute minorVersion is the cause of the problem..." );
                    NamedNodeMap attrCtrl = nodeControl.getAttributes();
                    int ctrlCount = attrCtrl.getLength();

                    NamedNodeMap attrTest = nodeTest.getAttributes();
                    int testlCount = attrTest.getLength();

                    if ( Math.abs( ctrlCount - testlCount ) == 1 ) {
                        // meaning +1 or -1
                        if ( ( ctrlCount - testlCount ) == 1 ) {
                            // control has one more
                            if ( hasMinorVersion( nodeControl ) ) {
                                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                                log.info( "Test is lacking minorVersion, well we can live without it ;) " );
                            }
                        } else {
                            // test has one more
                            if ( hasMinorVersion( nodeTest ) ) {
                                log.info( "Control is lacking minorVersion, well we can live without it ;) " );
                                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                            }
                        }
                    }
                } else if ( nodeControl.getNodeName().equals( "parameter" ) ) {
                    //       OK - unit="picomolar" term="kd"
                    // OPTIONAL - uncertainty="0" factor="0"
                    // DEFAULT  - exponent="0" base="10"




                }
            }

        } else if ( DifferenceConstants.TEXT_VALUE_ID == differenceId ) { // #14

            log.info( "Reason: " + DifferenceConstants.TEXT_VALUE );
            // ignore when a tag value is compared our special tags having a value false.

            // NodeDetails gives the value, not the node
            Node nodeControl = difference.getControlNodeDetail().getNode().getParentNode();
            log.info( "Value control: " + nodeControl.getNodeName() );
            if ( isOptionalNode( nodeControl ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            } else {

                Node nodeTest = difference.getTestNodeDetail().getNode().getParentNode();
                if ( isOptionalNode( nodeTest ) ) {
                    response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }
            }

        } else if ( DifferenceConstants.NODE_TYPE_ID == differenceId ) { // #17
            log.info( "Reason: " + DifferenceConstants.NODE_TYPE );

            Node nodeControl = difference.getControlNodeDetail().getNode().getParentNode();
            Node nodeTest = difference.getTestNodeDetail().getNode().getParentNode();
            if ( isOptionalNode( nodeControl ) || isOptionalNode( nodeTest ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }

        } else if ( DifferenceConstants.HAS_CHILD_NODES_ID == differenceId ) { // #18
            log.info( "Reason: " + DifferenceConstants.HAS_CHILD_NODES );

            Node nodeControl = difference.getControlNodeDetail().getNode();
            Node nodeTest = difference.getTestNodeDetail().getNode();
            if ( isOptionalNode( nodeControl ) || isOptionalNode( nodeTest ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            } else if ( isOptionalNode( nodeControl.getParentNode() ) || isOptionalNode( nodeTest.getParentNode() ) ) {
                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }

        } else if ( DifferenceConstants.CHILD_NODELIST_LENGTH_ID == differenceId ) { // #19

            // count of children node is different
            Node nodeControl = difference.getControlNodeDetail().getNode();
            Node nodeTest = difference.getTestNodeDetail().getNode();
            log.info( "Reason: " + DifferenceConstants.CHILD_NODELIST_LENGTH );

            log.info( "Control node: " + nodeControl.getNodeName() );
            log.info( "Test node: " + nodeTest.getNodeName() );

            log.info( "Parent node of control: " + nodeControl.getParentNode().getNodeName() );
            log.info( "Parent node of test: " + nodeTest.getParentNode().getNodeName() );

            if ( isOptionalNode( nodeControl ) || isOptionalNode( nodeTest ) ) {

                response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;

            } else {

                int controlCount = nodeControl.getChildNodes().getLength();
                int testCount = nodeTest.getChildNodes().getLength();

//                log.info( "Before checking on control children" );
//                log.info( "controlCount = " + controlCount );
//                log.info( "testCount = " + testCount );

                // find which nodes are missing from the test
                NodeList controlChildren = nodeControl.getChildNodes();
                NodeList testChildren = nodeTest.getChildNodes();


//                log.info( "Control nodes (" + controlChildren.getLength() + "):" );
//                log.info( "-------------" );
//                for ( int i = 0; i < controlChildren.getLength(); i++ ) {
//                    Node ctrlChild = controlChildren.item( i );
//                    log.info( i + ": " + ctrlChild.getNodeName() );
//                }

//                log.info( "Test nodes (" + testChildren.getLength() + "):" );
//                log.info( "-------------" );
//                for ( int i = 0; i < testChildren.getLength(); i++ ) {
//                    Node testChild = testChildren.item( i );
//                    log.info( i + ": " + testChild.getNodeName() );
//                }


                for ( int i = 0; i < controlChildren.getLength(); i++ ) {
                    Node childCtrl = controlChildren.item( i );
//                    log.info( "---------------- CTRL Vs Test ------------------" );
//                    log.info( "childCtrl.getNodeName() = " + childCtrl.getNodeName() );

                    if ( childCtrl instanceof Text ) {
//                        log.info( "Text node, controlCount--" );
                        controlCount--;
                        continue;
                    }

                    // has test got this one ?
                    boolean found = false;

                    for ( int j = 0; j < testChildren.getLength() && ! found; j++ ) {
                        Node childTest = testChildren.item( j );
//                        log.info( "childTest.getNodeName() = " + childTest.getNodeName() );

                        // has test got this one ?
                        if ( childTest.getNodeName().equals( childCtrl.getNodeName() ) ) {
                            // found it
                            found = true;
//                            log.info( "Found it !" );
                        }
                    }

                    if ( ! found ) {

//                        log.info( "Not found" );
                        // was in control but not in test, fails only if not from the list
                        if ( isOptionalNode( childCtrl ) ) {
//                            log.info( "controlCount--" );
                            controlCount--;
                        }
                    }
                } // check on control children

//                log.info( "Before checking on test children" );
//                log.info( "controlCount = " + controlCount );
//                log.info( "testCount = " + testCount );

                for ( int i = 0; i < testChildren.getLength(); i++ ) {
                    Node testChild = testChildren.item( i );
//                    log.info( "---------------- Test Vs CTRL ------------------" );
//                    log.info( "testChild.getNodeName() = " + testChild.getNodeName() );

                    if ( testChild instanceof Text ) {
//                        log.info( "Text node, controlCount--" );
                        testCount--;
                        continue;
                    }

                    // has control got this one ?
                    boolean found = false;

                    for ( int j = 0; j < controlChildren.getLength() && ! found; j++ ) {
                        Node controlChild = controlChildren.item( j );
//                        log.info( "controlChild.getNodeName() = " + controlChild.getNodeName() );

                        // has control got this one ?
                        if ( testChild.getNodeName().equals( controlChild.getNodeName() ) ) {
                            // found it
                            found = true;
//                            log.info( "Found it !" );
                        }
                    }

                    if ( ! found ) {

//                        log.info( "Not found" );
                        // was in control but not in test, fails only if not from the list
                        if ( isOptionalNode( testChild ) ) {
//                            log.info( "testCount--" );
                            testCount--;
                        }
                    }
                } // check on test children

//                log.info( "After checkings :" );
//                log.info( "controlCount = " + controlCount );
//                log.info( "testCount = " + testCount );

                if ( 0 == ( controlCount - testCount ) ) {
                    log.info( ">>>>>>> False alarm, we let this one go ;)" );
                    response = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }
            }

        } else if ( DifferenceConstants.CHILD_NODELIST_SEQUENCE_ID == differenceId ) { // #20
            log.info( "Reason: " + DifferenceConstants.CHILD_NODELIST_SEQUENCE );
            // it doesn't seem to break ... so leave it alone.
        }

        return response;
    }

    public void skippedComparison( Node expectedNode, Node actualNode ) {
        // Intentionally do nothing
        log.info( "!!!!!!!!!!!! Calling skippedComparison(" + expectedNode.getNodeName() + ", " + actualNode.getNodeName() + ") ..." );
    }

    //////////////////////////
    // Utility methods

    private boolean hasMinorVersion( Node node ) {
        NamedNodeMap attr = node.getAttributes();
        return attr.getNamedItem( "minorVersion" ) != null;
    }

    public boolean isOptionalNode( Node node ) {
        boolean optional = false;

        if ( tagNamesToIgnore.contains( node.getNodeName() ) ) {
//            log.info( "'" + node.getNodeName() + "' found in the list" );

            // if node's value is false
            String value = node.getTextContent();
            if ( value.equalsIgnoreCase( "FALSE" ) ) {
//                log.info( "Value was false => ignore" );
                optional = true;
            } else {
//                log.info( "Value was: " + value + " cannot ignore it." );
            }
        } else {
//            log.info( "'" + node.getNodeName() + "' NOT found in the list" );
        }

        return optional;
    }

    public Attr getCurrentAttribute( Difference difference ) {
        Node currentNode = difference.getControlNodeDetail().getNode();
        return (Attr) currentNode;
    }

    public String getCurrentTagName( Difference difference ) {
        Node currentNode = difference.getControlNodeDetail().getNode();
        return getTagName( currentNode );
    }

    public String getTagName( Node currentNode ) {
        if ( currentNode instanceof Text ) {
            return currentNode.getParentNode().getNodeName();
        } else {
            return currentNode.getNodeName();
        }
    }
}