package org.lgraf.xml;

import java.io.StringReader;
import java.security.CodeSource;
import java.text.MessageFormat;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;


class JAXPImplementationInfo
{
	private static final String XSLT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"></xsl:stylesheet>";


	public static void main( String[] args ) throws Exception
	{
		printJAXPImplementationInfo();
	}


	// Inspired from: https://stackoverflow.com/a/1804281/1019811
	private static void printJAXPImplementationInfo() throws Exception
	{
		System.setProperty( "jaxp.debug", "1" );

		// http://www.oracle.com/technetwork/java/javase/8u161-relnotes-4021379.html#JDK-8186080
		//System.setProperty( "jdk.xml.overrideDefaultParser", "true" );
		printWithSeparator( "jdk.xml.overrideDefaultParser: " + System.getProperty( "jdk.xml.overrideDefaultParser" ) );

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		printWithSeparator( getJAXPImplementationInfo( "TransformerFactory", transformerFactory.getClass() ) );
		Transformer transformer = transformerFactory.newTransformer( new StreamSource( new StringReader( XSLT ) ) );
		printWithSeparator( getJAXPImplementationInfo( "Transformer", transformer.getClass() ) );

		SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		printWithSeparator( getJAXPImplementationInfo( "SchemaFactory", schemaFactory.getClass() ) );
		Schema schema = schemaFactory.newSchema();
		printWithSeparator( getJAXPImplementationInfo( "Schema", schema.getClass() ) );

		Validator validator = schema.newValidator();
		printWithSeparator( getJAXPImplementationInfo( "Validator", validator.getClass() ) );

		XPathFactory xPathFactory = XPathFactory.newInstance();
		printWithSeparator( getJAXPImplementationInfo( "XPathFactory", xPathFactory.getClass() ) );
		XPath xPath = xPathFactory.newXPath();
		printWithSeparator( getJAXPImplementationInfo( "XPath", xPath.getClass() ) );

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		printWithSeparator( getJAXPImplementationInfo( "DocumentBuilderFactory", documentBuilderFactory.getClass() ) );
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		printWithSeparator( getJAXPImplementationInfo( "DocumentBuilder", documentBuilder.getClass() ) );

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		printWithSeparator( getJAXPImplementationInfo( "SAXParserFactory", saxParserFactory.getClass() ) );
		SAXParser saxParser = saxParserFactory.newSAXParser();
		printWithSeparator( getJAXPImplementationInfo( "SAXParser", saxParser.getClass() ) );
	}


	private static String getJAXPImplementationInfo( String componentName, Class componentClass )
	{
		CodeSource source = componentClass.getProtectionDomain().getCodeSource();
		return MessageFormat.format( "{0} implementation: {1} loaded from: {2}", componentName,
				componentClass.getName(), source == null ? "Java Runtime" : source.getLocation() );
	}


	private static void printWithSeparator( String msg )
	{
		System.out.println( msg );
		System.out.println( "------------------------------------------------" );
	}
}
