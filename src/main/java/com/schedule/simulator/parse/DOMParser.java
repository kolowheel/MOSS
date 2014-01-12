package com.schedule.simulator.parse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.schedule.simulator.bean.LoggingSProcess;
import com.schedule.simulator.bean.LoggingSimulation;
import com.schedule.simulator.bean.SProcess;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {
    List<SProcess> processes = new ArrayList<SProcess>();
    LoggingSimulation simulation = new LoggingSimulation();

    public LoggingSimulation getSimulation() {
        return simulation;
    }

    public List<SProcess> getProcesses() {
        return processes;
    }

    public void parse(String file) throws SAXException, ParserConfigurationException, IOException {
        validate(file);
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        String schemaLang = "http://www.w3.org/2001/XMLSchema";
        SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
        f.setSchema(factory.newSchema(new File("Input.xsd")));
        f.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(file);
        Node root = doc.getFirstChild();
        simulation.setDurationOfSimulation(Integer.parseInt(((Element) root).getAttribute("DurationOfSimulation")));
        parseSprocess(root.getChildNodes());
    }

    private void parseSprocess(NodeList childNodes) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNodes.item(i);
                SProcess sProcess = new LoggingSProcess();
                sProcess.setCpuBurst(Integer.parseInt(element.getAttribute("CPUBurstTime")));
                sProcess.setId(Integer.parseInt(element.getAttribute("id")));
                sProcess.setIoBurst(Integer.parseInt(element.getAttribute("IOBurstTime")));
                sProcess.setCpuTotalBurstTime(Integer.parseInt(element.getAttribute("CpuTotalWorkTime")));
                processes.add(sProcess);
            }
        }
    }

    void validate(String file) throws IOException, SAXException {
        Source xmlFile = new StreamSource(new File(file));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("Input.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);
    }
}
