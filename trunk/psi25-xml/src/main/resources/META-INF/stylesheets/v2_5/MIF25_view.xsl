<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:psi="net:sf:psidev:mi"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!--
    @id: $Id$
    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    This XSLT-Script was designed for generating HTML out of a PSI-MI-XML-File,
    which satisfies MIF.xsd.
    Its only a visualisation and does not show all details included in PSI !

    The implementation is highly recursive - a lot of templates are used at
    different Levels.
    This XSLT-Script was developed and tested using SAXON 6.5.2.

    Author: Henning Mersch (hmersch@ebi.ac.uk)
    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    Updated 2006-02-17 by Antony Quinn [aquinn@ebi.ac.uk] to
    conform with MIF version 2.5
    Notes:
    - style should be in separate CSS file
    - replace if statements with templates where possible
    - replace value-of with apply-templates where possible
    - encoding should be UTF-8
    - URLs to database should be obtained from PSI-MI ontology file (ideally OWL)
    - search for text marked 'TODO' for all outstanding tasks
    - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    -->

    <xsl:output method="html"
                encoding="iso-8859-1"
                indent="yes"
                media-type="text/html"
                standalone="yes"/>

    <xsl:param name="base" select="'http://psidev.info'"/>

    <xsl:param name="word.wrap" select="10"/>
    <xsl:param name="word.sep"  select="' '"/>

    <xsl:param name="swissProtUrl"
               select="'http://www.expasy.org/cgi-bin/sprot-search-ac?'"/>
    <xsl:param name="uniProtUrl"
               select="'http://www.uniprot.org/uniprot/'"/>
    <xsl:param name="interProUrl"
               select="'http://www.ebi.ac.uk/interpro/IEntry?ac='"/>
    <xsl:param name="goUrl"
               select="'http://www.ebi.ac.uk/ego/DisplayGoTerm?id='"/>
    <xsl:param name="intactUrl"
               select="'http://www.ebi.ac.uk/intact/pages/interactions/interactions.xhtml?query='"/>
    <xsl:param name="pubmedUrl"
               select="'http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?cmd=Retrieve&amp;db=PubMed&amp;&amp;dopt=Citation&amp;list_uids='"/>
    <xsl:param name="newtUrl"
               select="'http://www.ebi.ac.uk/newt/display?search='"/>
    <xsl:param name="drugbankUrl"
               select="'http://www.drugbank.ca/drugs/'"/>
    <xsl:param name="pubchemUrl"
               select="'http://www.ncbi.nlm.nih.gov/sites/entrez?db=pccompound&amp;term='"/>
    <xsl:param name="pdbUrl"
               select="'http://www.rcsb.org/pdb/explore/explore.do?structureId='"/>
    '"/>


    <xsl:template match="psi:entrySet">
        <html>
            <head>
                <title>
                    HUPO Proteomics Standards Initiative
                    Molecular Interaction
                </title>
                <style>
                    table   {
                    width:              100%;
                    }
                    .title  {
                    background-color:   #ddd;
                    font-weight:        bold;
                    }
                    .table-title    {
                    background-color:   #ddd;
                    width:              20%;
                    text-align:         right;
                    padding-right:       5px;
                    color: #666;
                    font-weight: bold;
                    }
                    .table-subtitle    {
                    background-color:   #ddd;
                    font-style:         italic;
                    }
                    .normal-cell {
                    background-color:   #eee;
                    text-align: left;
                    padding-left: 5px;
                    }
                    .sequence   {
                    font-family:        "Courier New", monospace;
                    font-size:          11px;
                    background-color:   #eee;
                    }
                </style>
            </head>
            <body>
                <!--<div id="header">-->
                <!--<a href="{$base}">-->
                <!--<img src="{$base}/images/psi.gif" border="0" align="left"/>-->
                <!--</a>-->
                <!--<a href="http://www.hupo.org/">-->
                <!--<img src="{$base}/images/hupo.gif" border="0" align="right"/>-->
                <!--</a>-->
                <!--<h2 align="center">-->
                <!--<a href="{$base}">Proteomics Standards Initiative</a>-->
                <!--</h2>-->
                <!--<h2 align="center">-->
                <!--Molecular Interaction Version-->
                <!--<xsl:value-of select="concat(@level, '.', @version)"/>-->
                <!--</h2>-->
                <!--</div>-->
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="psi:entry">
        <xsl:apply-templates mode="title"/>
    </xsl:template>

    <!-- TODO: find out why matching elements with no name (text nodes?) -->
    <xsl:template match="node()" mode="title">
        <xsl:comment><xsl:value-of select="name(.)"/></xsl:comment>
        <xsl:if test="string-length(name(.)) > 0">
            <xsl:comment><xsl:value-of select="name(.)"/></xsl:comment>
            <div id="{name(.)}">
                <table style="border-bottom: 1px solid #fff" cellspacing="1">
                    <tr>
                        <td class="title" colspan="2">
                            <xsl:apply-templates select="current()" mode="name"/>
                        </td>
                    </tr>
                    <xsl:apply-templates/>
                </table>
                <br/>
            </div>
        </xsl:if>
    </xsl:template>

    <!-- Convert first character to upper-case -->
    <xsl:template match="node()" mode="name">
        <xsl:param name="str" select="name(.)"/>
        <xsl:value-of select="translate(substring($str, 1, 1), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
        <xsl:value-of select="substring($str, 2, string-length($str) - 1)" />
    </xsl:template>

    <xsl:template match="@releaseDate">
        <tr>
            <td class="table-title">Release Date:</td>
            <td class="normal-cell"><xsl:value-of select="text()"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:names">
        <tr>
            <td class="table-title">Name:</td>
            <td class="normal-cell">
                <xsl:apply-templates select="current()" mode="no-title"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:names" mode="no-title">
        <xsl:apply-templates select="psi:shortLabel"/>
        <xsl:apply-templates select="psi:fullName[. != ../psi:shortLabel]"/>
        <xsl:apply-templates select="psi:alias"/>
    </xsl:template>

    <xsl:template match="psi:shortLabel">
        <xsl:value-of select="."/>
    </xsl:template>

    <!-- On one line so colon appears correctly -->
    <xsl:template match="psi:fullName">: <xsl:value-of select="."/></xsl:template>

    <xsl:template match="psi:alias">
        (<xsl:apply-templates select="@type"/><xsl:apply-templates select="text()"/>)
    </xsl:template>

    <xsl:template match="psi:alias/@type">
        <xsl:value-of select="."/>:
    </xsl:template>

    <xsl:template match="psi:bibref | psi:xref">
        <xsl:apply-templates>
            <!-- Sort Xrefs by database-->
            <xsl:sort select="@db" order="descending"/>
            <xsl:sort select="@refType"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:primaryRef | psi:secondaryRef">
        <tr>
            <xsl:variable name="url">
                <xsl:apply-templates select="current()" mode="url"/>
            </xsl:variable>
            <td class="table-title">
                <xsl:value-of select="@db"/>
                <!--<xsl:apply-templates select="@version"/>-->
            </td>
            <td class="normal-cell">
                <xsl:choose>
                    <xsl:when test="string-length($url) > 0">
                        <a href="{$url}"
                           title="{@refType}">
                            <xsl:value-of select="@id"/>
                        </a>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@id"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:apply-templates select="@secondary"/>
                <xsl:choose>
                    <xsl:when test="@refType">
                        <font color="lightgray"> [<xsl:apply-templates select="@refType"/>]</font>
                    </xsl:when>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="@secondary">
        (<xsl:value-of select="."/>)
    </xsl:template>

    <xsl:template match="@version">
        (<xsl:value-of select="."/>)
    </xsl:template>

    <xsl:template match="psi:primaryRef | psi:secondaryRef" mode="url">
        <xsl:choose>
            <xsl:when test="@db = 'Swiss-Prot'">
                <xsl:value-of select="concat($swissProtUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'pubmed' or @dbAc = 'MI:0446'">
                <xsl:value-of select="concat($pubmedUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'go' or @dbAc = 'MI:0448'">
                <xsl:value-of select="concat($goUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'interpro' or @dbAc = 'MI:0449'">
                <xsl:value-of select="concat($interProUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'intact' or @dbAc = 'MI:0469'">
                <xsl:value-of select="concat($intactUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'uniprotkb' or @dbAc = 'MI:0486'">
                <xsl:value-of select="concat($uniProtUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'pubchem' or @dbAc = 'MI:0730'">
                <xsl:value-of select="concat($pubchemUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'drugbank' or @dbAc = 'MI:2002'">
                <xsl:value-of select="concat($drugbankUrl, @id)"/>
            </xsl:when>
            <xsl:when test="@db = 'rcsb pdb' or @dbAc = 'MI:0460'">
                <xsl:value-of select="concat($pdbUrl, @id)"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="psi:confidenceList">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:confidence">
        <tr>
            <td class="table-title">Confidence</td>
            <td class="normal-cell">
                <xsl:apply-templates select="psi:unit/psi:names/psi:shortLabel"/>:
                <xsl:apply-templates select="psi:value"/>
            </td>
        </tr>
    </xsl:template>

    <!--<xsl:template match="psi:availabilityList">-->
    <!--<tr>-->
    <!--<td class="table-title">Availability</td>-->
    <!--<td>-->
    <!--<table cellspacing="0" style="border: 1px solid rgb(238, 238, 238);">-->
    <!--<tbody>-->
    <!--<xsl:apply-templates/>-->
    <!--</tbody>-->
    <!--</table>-->
    <!--</td>-->
    <!--</tr>-->
    <!--</xsl:template>-->

    <xsl:template match="psi:availability">
        <tr>
            <td class="table-title">Availability</td>
            <td class="normal-cell">
                <a name="a{@id}"><xsl:value-of select="@id"/></a>:
                <xsl:value-of select="."/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:experimentDescription">
        <tr>
            <td class="table-subtitle" colspan="2">
                <a name="e{@id}">Experiment #<xsl:value-of select="@id"/></a>
            </td>
        </tr>
        <tr>
            <td class="table-title">Name:</td>
            <td class="normal-cell">
                <xsl:value-of select="psi:names/psi:shortLabel"/>
            </td>
        </tr>
        <tr>
            <td class="table-title">Description:</td>
            <td class="normal-cell">
                <xsl:value-of select="psi:names/psi:fullName"/>
            </td>
        </tr>
        <xsl:apply-templates select="psi:bibref"/>
        <xsl:apply-templates select="psi:xref"/>
        <xsl:apply-templates select="psi:hostOrganismList/psi:hostOrganism"/>
        <tr><xsl:apply-templates select="psi:interactionDetectionMethod"/></tr>
        <tr><xsl:apply-templates select="psi:participantIdentificationMethod"/></tr>
        <tr><xsl:apply-templates select="psi:featureDetectionMethod"/></tr>
        <xsl:apply-templates select="psi:attributeList"/>
    </xsl:template>

    <xsl:template match="psi:interactor">
        <tr>
            <td class="table-subtitle" colspan="2">
                <a name="i{@id}">Interactor #<xsl:value-of select="@id"/></a>
            </td>
        </tr>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:interaction">
        <tr>
            <td class="table-subtitle" colspan="2">
                <a name="ix{@id}">Interaction #<xsl:value-of select="@id"/></a>
            </td>
        </tr>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:modelled | psi:intraMolecular | psi:negative">
        <tr>
            <td class="table-title">
                <xsl:if test="local-name() = 'modelled'">Modelled</xsl:if>
                <xsl:if test="local-name() = 'intraMolecular'">Intra molecular</xsl:if>
                <xsl:if test="local-name() = 'negative'">Negative</xsl:if>
            </td>
            <td class="normal-cell">
                <xsl:apply-templates select="text()"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:experimentList">
        <tr>
            <td class="table-title">Experiments:</td>
            <td class="table-subtitle">
                <xsl:apply-templates/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:participantList">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:participant">
        <tr>
            <td class="table-title"><a name="p{@id}">Participant #<xsl:value-of select="@id"/></a></td>
            <td class="normal-cell">
                <table style="border: 1px solid #eee" cellspacing="1">
                    <tr>
                        <!--<td colspan="2">
                            <xsl:apply-templates select="psi:interactorRef" mode="participant"/>
                            <xsl:apply-templates select="psi:interactor"    mode="participant"/>
                        </td>-->
                        <xsl:apply-templates select="psi:interactorRef" mode="participant"/>
                        <xsl:apply-templates select="psi:interactor" mode="participant"/>
                    </tr>
                    <tr>
                        <xsl:apply-templates select="psi:biologicalRole"/>
                    </tr>
                    <tr>
                        <xsl:apply-templates select="psi:experimentalRoleList"/>
                    </tr>
                    <xsl:apply-templates select="psi:featureList/psi:feature"/>
                    <xsl:apply-templates select="psi:hostOrganismList/psi:hostOrganism"/>
                    <xsl:apply-templates select="psi:parameterList"/>
                    <tr>
                        <xsl:apply-templates select="psi:experimentalInteractorList"/>
                    </tr>
                    <tr>
                        <xsl:apply-templates select="psi:experimentalPreparationList"/>
                    </tr>
                </table>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:biologicalRole">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Biological Role'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:experimentalRoleList">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Experimental Role'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:experimentalInteractorList">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Experimental Interactor'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:experimentalPreparationList">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Experimental Preparation'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:experimentalInteractor">
        <xsl:apply-templates select="psi:interactorRef" mode="experimentalInteractor"/>
        <xsl:apply-templates select="psi:interactor"    mode="experimentalInteractor"/>
    </xsl:template>

    <xsl:template match="psi:experimentalPreparation">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:interactorRef" mode="experimentalInteractor">
        <a href="#i{.}">
            <xsl:apply-templates select="/psi:entrySet/psi:entry/psi:interactionList/psi:interaction/psi:participantList/psi:participant/psi:interactor[@id = current()/text()]"
                                 mode="ref">
                <xsl:with-param name="label" select="'Interactor'"/>
            </xsl:apply-templates>
            <xsl:apply-templates select="/psi:entrySet/psi:entry/psi:interactorList/psi:interactor[@id = current()/text()]"
                                 mode="ref">
                <xsl:with-param name="label" select="'Interactor'"/>
            </xsl:apply-templates>
        </a>
    </xsl:template>

    <xsl:template match="psi:interactor" mode="experimentalInteractor">
        <a name="i{@id}">Interactor #<xsl:value-of select="@id"/></a>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:feature">
        <tr>
            <td class="table-title">
                <a name="f{@id}">Feature #<xsl:value-of select="@id"/></a>
            </td>
            <td class="normal-cell">
                <xsl:apply-templates select="psi:featureType/psi:names/psi:shortLabel"/>
                (<xsl:apply-templates select="psi:names/psi:shortLabel"/>)
                [
                <xsl:apply-templates select="psi:featureRangeList/psi:featureRange/psi:begin/@position"/>
                ..
                <xsl:apply-templates select="psi:featureRangeList/psi:featureRange/psi:end/@position"/>
                ]
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:experimentalRole">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:experimentRef">
        <!--<td class="normal-cell">-->
        <a href="#e{.}">
            <xsl:apply-templates select="/psi:entrySet/psi:entry/psi:experimentList/psi:experimentDescription[@id = current()/text()]"
                                 mode="ref">
                <xsl:with-param name="label" select="'Experiment'"/>
            </xsl:apply-templates>
        </a>
        <!--</td>-->
    </xsl:template>

    <xsl:template match="psi:availabilityRef">
        <tr>
            <td class="table-title">Availability</td>
            <td class="normal-cell">
                <a href="#a{.}"><xsl:value-of select="."/></a>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:interactorRef" mode="participant">
        <a href="#i{.}">
            <xsl:apply-templates select="/psi:entrySet/psi:entry/psi:interactorList/psi:interactor[@id = current()/text()]"
                                 mode="ref">
                <xsl:with-param name="label" select="'Interactor'"/>
            </xsl:apply-templates>
        </a>
    </xsl:template>

    <xsl:template match="node()" mode="ref">
        <xsl:param name="label"/>
        <xsl:choose>
            <xsl:when test="psi:names/psi:shortLabel = ''">
                <xsl:value-of select="$label"/>
                #<xsl:apply-templates select="@id"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="title">
                    <xsl:apply-templates select="psi:names/psi:fullName/text()"/>
                </xsl:attribute>
                <xsl:apply-templates select="psi:names/psi:shortLabel"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="psi:interactor" mode="participant">
        <a name="i{@id}">Interactor #<xsl:value-of select="@id"/></a>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="node()" mode="cellrow">
        <xsl:param name="title"/>
        <td class="table-title"><xsl:value-of select="$title"/>:</td>
        <td class="normal-cell"><table style="border: 1px solid #eee" cellspacing="0"><xsl:apply-templates/></table></td>
    </xsl:template>

    <xsl:template match="psi:interactionType">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Interaction Type'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:interactionDetectionMethod">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Interaction detection method'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:participantIdentificationMethod">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Participant identification method'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:featureDetectionMethod">
        <xsl:apply-templates select="current()"  mode="cellrow">
            <xsl:with-param name="title" select="'Feature detection method'"/>
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="psi:interactorType">
        <tr>
            <xsl:apply-templates select="current()"  mode="cellrow">
                <xsl:with-param name="title" select="'Interactor Type'"/>
            </xsl:apply-templates>
        </tr>
    </xsl:template>

    <xsl:template match="psi:parameterList">
        <tr>
            <td class="table-title">Parameters</td>
            <td>
                <table cellspacing="0" style="border: 1px solid rgb(238, 238, 238);">
                    <tbody>
                        <xsl:apply-templates/>
                    </tbody>
                </table>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:parameter">
        <xsl:param name="term" select="@term"/>
        <xsl:param name="unit" select="@unit"/>
        <xsl:param name="pbase" select="@base"/>
        <xsl:param name="factor" select="@factor"/>
        <xsl:param name="exponent" select="@exponent"/>
        <xsl:param name="uncertainty" select="@uncertainty"/>

        <tr>
            <td class="table-title"><xsl:value-of select="$term"/>:</td>
            <td class="normal-cell">

                <xsl:value-of select="$factor"/>
                <xsl:choose>
                    <xsl:when test="$pbase">
                        x <xsl:value-of select="$pbase"/>
                    </xsl:when>
                    <xsl:otherwise>
                        x 10
                    </xsl:otherwise>
                </xsl:choose>

                <xsl:choose>
                    <xsl:when test="$exponent">
                        <sup><xsl:value-of select="$exponent"/></sup>
                    </xsl:when>
                    <xsl:otherwise>
                        <sup>0</sup>
                    </xsl:otherwise>
                </xsl:choose>

                <xsl:choose>
                    <xsl:when test="$uncertainty">
                        +/- <xsl:value-of select="$uncertainty"/>
                    </xsl:when>
                </xsl:choose>

                <xsl:choose>
                    <xsl:when test="$unit">
                        (<xsl:value-of select="$unit"/>)
                    </xsl:when>
                </xsl:choose>
            </td>
        </tr>

    </xsl:template>

    <xsl:template match="psi:organism | psi:hostOrganism">
        <xsl:param name="taxid" select="@ncbiTaxId"/>
        <tr>
            <td class="table-title">
                <xsl:if test="local-name() = 'hostOrganism'">Host</xsl:if>
                Organism:
            </td>
            <td class="normal-cell">
                <xsl:choose>
                    <xsl:when test="$taxid > 0">
                        <a href="{$newtUrl}{@ncbiTaxId}" title="Tax ID: {@ncbiTaxId}">
                            <xsl:apply-templates select="psi:names" mode="no-title"/>
                        </a>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:apply-templates select="psi:names" mode="no-title"/>
                        (taxid: <xsl:apply-templates select="@ncbiTaxId" mode="no-title"/>)
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:sequence">
        <tr>
            <td class="table-title">Sequence:</td>
            <td class="sequence">
                <xsl:call-template name="split-string">
                    <xsl:with-param name="str" select="."/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:attributeList">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="psi:attribute">
        <tr>
            <td class="table-title">
                <xsl:apply-templates select="@name"/>
            </td>
            <td class="normal-cell"><xsl:apply-templates select="current()" mode="text"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="psi:attribute[@name = 'url']" mode="text">
        <a href="{text()}">
            <xsl:apply-templates select="text()"/>
        </a>
    </xsl:template>

    <xsl:template match="psi:attribute[@name = 'contact-email']" mode="text">
        <a href="mailto:{text()}">
            <xsl:apply-templates select="text()"/>
        </a>
    </xsl:template>

    <xsl:template match="psi:attribute" mode="text">
        <xsl:apply-templates select="text()"/>
    </xsl:template>

    <!-- TODO: copied from PRIDE common.xsl - should use common.xsl here -->
    <!-- Split long strings to allow text wrapping -->
    <xsl:template name="split-string">
        <xsl:param name="str"/>
        <xsl:param name="max" select="$word.wrap"/>
        <xsl:param name="sep" select="$word.sep"/>
        <xsl:choose>
            <xsl:when test="string-length($str) > $max">
                <xsl:variable name="substr" select="substring($str, 1, $max)"/>
                <xsl:value-of select="substring($substr, 1, $max)"/>
                <xsl:copy-of select="$sep"/>
                <xsl:variable name="subsubstr"
                              select="substring($str, number($max + 1), string-length($str))"/>
                <xsl:choose>
                    <xsl:when test="string-length($subsubstr) > $max">
                        <!-- Recursion -->
                        <xsl:call-template name="split-string">
                            <xsl:with-param name="str" select="$subsubstr"/>
                            <xsl:with-param name="max" select="$max"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$subsubstr"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$str"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>