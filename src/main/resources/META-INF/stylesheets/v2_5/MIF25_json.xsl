<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" 
    xmlns:psi253="net:sf:psidev:mi"
    xmlns:psi254="http://psi.hupo.org/mi/mif"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:json="http://json.org/"
    xmlns:fn="http://www.w3.org/2005/xpath-functions">
    
    <xsl:import href="xml-to-json.xsl"/>
    <xsl:output method="text"/>
    
    <xsl:param name="skip-root" as="xs:boolean" select="true()"/>
    <xsl:param name="force-string" as="xs:boolean" select="true()"/>
    
    
    <xsl:template match="/">
        <xsl:text>{"interactors":[</xsl:text>
        <xsl:for-each select="//psi253:interactor|//psi254:interactor">
                <xsl:call-template name="generate">
                    <xsl:with-param name="input" select="."/>
                </xsl:call-template>            
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>
        </xsl:for-each> 
               <xsl:text>],"interactions":[</xsl:text>
        <xsl:for-each select="//psi253:interaction|//psi254:interaction">
                <xsl:call-template name="generate">
                    <xsl:with-param name="input" select="."/>
                </xsl:call-template> 
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>
        </xsl:for-each>
        <xsl:text>]}</xsl:text>
    </xsl:template>
           
    <xsl:template name="generate">
        <xsl:param name="input"/>
        <xsl:value-of select="json:generate($input)"/>
    </xsl:template>
</xsl:stylesheet>
