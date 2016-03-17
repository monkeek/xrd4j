package com.pkrete.xrd4j.server.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.message.ServiceRequest;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.Map;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import junit.framework.TestCase;

/**
 * Test cases for CustomRequestDeserializer class.
 *
 * @author Petteri Kivimäki
 */
public class CustomRequestDeserializerTest extends TestCase {

    /**
     * Request from subsystem to service with service version included.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        customDeserializer.deserialize(request, "http://producer.x-road.ee");

        assertEquals("1234567890", request.getRequestData());
        assertEquals("4.0", request.getProtocolVersion());
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
    }

    /**
     * Request from subsystem to service with service version included.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1WithWrappers() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        request.setProcessingWrappers(true);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        customDeserializer.deserialize(request, "http://producer.x-road.ee");

        assertEquals("1234567890", request.getRequestData());
        assertEquals("4.0", request.getProtocolVersion());
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
    }

    /**
     * Request from subsystem to service with service version included.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test1WithoutWrappers() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><data>1234567890</data></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        request.setProcessingWrappers(false);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        customDeserializer.deserialize(request, "http://producer.x-road.ee");

        assertEquals("1234567890", request.getRequestData());
        assertEquals("4.0", request.getProtocolVersion());
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
    }

    /**
     * Request from subsystem to service with service version included.
     * Request with namespace prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test2WithoutWrappers() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><ns1:request><ns1:data>1234567890</ns1:data></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        customDeserializer.deserialize(request);

        assertEquals("1234567890", request.getRequestData());
        assertEquals("4.5", request.getProtocolVersion());
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
    }

    /**
     * Request from subsystem to service with service version included.
     * Multiple request child elements.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test3() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><request><field1>Field 1</field1><field2>Field 2</field2><field3>Field 3</field3></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<Map> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl1();
        customDeserializer.deserialize(request);

        assertEquals("Field 1", request.getRequestData().get("field1"));
        assertEquals("Field 2", request.getRequestData().get("field2"));
        assertEquals("Field 3", request.getRequestData().get("field3"));
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
        assertEquals("4.0", request.getProtocolVersion());
    }

    /**
     * Request from subsystem to service with service version included.
     * Multiple request child elements and namespace prefix.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test4() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>6.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><ns1:request><ns1:field1>Field 1</ns1:field1><ns1:field2>Field 2</ns1:field2><ns1:field3>Field 3</ns1:field3></ns1:request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<Map> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl1();
        customDeserializer.deserialize(request);

        assertEquals("Field 1", request.getRequestData().get("field1"));
        assertEquals("Field 2", request.getRequestData().get("field2"));
        assertEquals("Field 3", request.getRequestData().get("field3"));
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
        assertEquals("6.0", request.getProtocolVersion());
    }

    /**
     * Request from subsystem to service with service version included.
     * Line breaks inside the request element.
     * Deserialize with namespace specified.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void test5() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\">\n<request>\n<data>1234567890</data>\n</request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        customDeserializer.deserialize(request, "http://producer.x-road.ee");

        assertEquals("1234567890", request.getRequestData());
        assertEquals("http://producer.x-road.ee", request.getProducer().getNamespaceUrl());
        assertEquals("ns1", request.getProducer().getNamespacePrefix());
        assertEquals("4.0", request.getProtocolVersion());
    }

    /**
     * Request from subsystem to service with service version included.
     * Deserialize with wrong namespace specified.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testException1() throws XRd4JException, SOAPException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>6.5</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://producer.x-road.ee\"><request><data>1234567890</data></request></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        try {
            customDeserializer.deserialize(request, "http://test.com");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
        assertEquals(null, request.getRequestData());
        assertEquals(null, request.getProducer().getNamespaceUrl());
        assertEquals(null, request.getProducer().getNamespacePrefix());

        assertEquals("FI", request.getConsumer().getXRoadInstance());
        assertEquals("GOV", request.getConsumer().getMemberClass());
        assertEquals("MEMBER1", request.getConsumer().getMemberCode());
        assertEquals("subsystem", request.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, request.getConsumer().getObjectType());

        assertEquals("FI", request.getProducer().getXRoadInstance());
        assertEquals("COM", request.getProducer().getMemberClass());
        assertEquals("MEMBER2", request.getProducer().getMemberCode());
        assertEquals("subsystem", request.getProducer().getSubsystemCode());
        assertEquals("getRandom", request.getProducer().getServiceCode());
        assertEquals("v1", request.getProducer().getServiceVersion());
        assertEquals("ID11234", request.getId());
        assertEquals("EE1234567890", request.getUserId());
        assertEquals("6.5", request.getProtocolVersion());
        assertEquals(ObjectType.SERVICE, request.getProducer().getObjectType());
        assertEquals(true, request.getSoapMessage() != null);
    }

    /**
     * Different service code in header and body.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testException2() throws SOAPException, XRd4JException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.0</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom2 xmlns:ns1=\"http://test.com\"><request><data>1234567890</data></request></ns1:getRandom2></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        try {
            customDeserializer.deserialize(request, "http://test.com");
            fail("Should not reach this");
        } catch (XRd4JException ex) {
            // OK
        }
        assertEquals(null, request.getRequestData());
        assertEquals(null, request.getProducer().getNamespaceUrl());
        assertEquals(null, request.getProducer().getNamespacePrefix());

        assertEquals("FI", request.getConsumer().getXRoadInstance());
        assertEquals("GOV", request.getConsumer().getMemberClass());
        assertEquals("MEMBER1", request.getConsumer().getMemberCode());
        assertEquals("subsystem", request.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, request.getConsumer().getObjectType());

        assertEquals("FI", request.getProducer().getXRoadInstance());
        assertEquals("COM", request.getProducer().getMemberClass());
        assertEquals("MEMBER2", request.getProducer().getMemberCode());
        assertEquals("subsystem", request.getProducer().getSubsystemCode());
        assertEquals("getRandom", request.getProducer().getServiceCode());
        assertEquals("v1", request.getProducer().getServiceVersion());
        assertEquals("ID11234", request.getId());
        assertEquals("EE1234567890", request.getUserId());
        assertEquals("4.0", request.getProtocolVersion());
        assertEquals(ObjectType.SERVICE, request.getProducer().getObjectType());
        assertEquals(true, request.getSoapMessage() != null);
    }

    /**
     * No request element.
     * @throws XRd4JException
     * @throws SOAPException
     */
    public void testException3() throws SOAPException, XRd4JException {
        String soapString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:id=\"http://x-road.eu/xsd/identifiers\" xmlns:xrd=\"http://x-road.eu/xsd/xroad.xsd\"><SOAP-ENV:Header><xrd:client id:objectType=\"SUBSYSTEM\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>GOV</id:memberClass><id:memberCode>MEMBER1</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode></xrd:client><xrd:service id:objectType=\"SERVICE\"><id:xRoadInstance>FI</id:xRoadInstance><id:memberClass>COM</id:memberClass><id:memberCode>MEMBER2</id:memberCode><id:subsystemCode>subsystem</id:subsystemCode><id:serviceCode>getRandom</id:serviceCode><id:serviceVersion>v1</id:serviceVersion></xrd:service><xrd:userId>EE1234567890</xrd:userId><xrd:id>ID11234</xrd:id><xrd:protocolVersion>4.1</xrd:protocolVersion></SOAP-ENV:Header><SOAP-ENV:Body><ns1:getRandom xmlns:ns1=\"http://test.com\"><request1><data>1234567890</data></request1></ns1:getRandom></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        SOAPMessage msg = SOAPHelper.toSOAP(soapString);

        ServiceRequestDeserializer deserializer = new ServiceRequestDeserializerImpl();
        ServiceRequest<String> request = deserializer.deserialize(msg);
        CustomRequestDeserializer customDeserializer = new CustomRequestDeserializerImpl();
        try {
            customDeserializer.deserialize(request, "http://test.com");
            fail("Should not reach this");
        } catch (NullPointerException ex) {
            // OK
        }
        assertEquals(null, request.getRequestData());
        assertEquals(null, request.getProducer().getNamespaceUrl());
        assertEquals(null, request.getProducer().getNamespacePrefix());

        assertEquals("FI", request.getConsumer().getXRoadInstance());
        assertEquals("GOV", request.getConsumer().getMemberClass());
        assertEquals("MEMBER1", request.getConsumer().getMemberCode());
        assertEquals("subsystem", request.getConsumer().getSubsystemCode());
        assertEquals(ObjectType.SUBSYSTEM, request.getConsumer().getObjectType());

        assertEquals("FI", request.getProducer().getXRoadInstance());
        assertEquals("COM", request.getProducer().getMemberClass());
        assertEquals("MEMBER2", request.getProducer().getMemberCode());
        assertEquals("subsystem", request.getProducer().getSubsystemCode());
        assertEquals("getRandom", request.getProducer().getServiceCode());
        assertEquals("v1", request.getProducer().getServiceVersion());
        assertEquals("ID11234", request.getId());
        assertEquals("EE1234567890", request.getUserId());
        assertEquals("4.1", request.getProtocolVersion());
        assertEquals(ObjectType.SERVICE, request.getProducer().getObjectType());
        assertEquals(true, request.getSoapMessage() != null);
    }

    private class CustomRequestDeserializerImpl extends AbstractCustomRequestDeserializer<String> {

        protected String deserializeRequest(Node requestNode, SOAPMessage message) throws SOAPException {
            for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
                if (requestNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE
                        && requestNode.getChildNodes().item(i).getLocalName().equals("data")) {
                    return requestNode.getChildNodes().item(i).getTextContent();
                }
            }
            return null;
        }
    }

    private class CustomRequestDeserializerImpl1 extends AbstractCustomRequestDeserializer<Map> {

        protected Map deserializeRequest(Node requestNode, SOAPMessage message) throws SOAPException {
            if (requestNode == null) {
                return null;
            }
            return SOAPHelper.nodesToMap(requestNode.getChildNodes());
        }
    }
}
