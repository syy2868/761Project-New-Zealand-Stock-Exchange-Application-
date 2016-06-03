package com.sprinters.bullzx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RemoveStockParser {

	private static final void saveResult(Document xml, String dataFileName)
			throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new File(dataFileName));
		tf.transform(new DOMSource(xml), result);
	}

	public void removeStock(List<String> toDelete, String dataFileName)
			throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		File file = new File(dataFileName);
		Document doc = db.parse(new FileInputStream(file));
		doc.normalize();
		NodeList del = doc.getElementsByTagName("code");
		for (int i = 0; i < del.getLength(); i++) {
			for (String stockCode : toDelete) {
				if (del.item(i).getTextContent().equals(stockCode)) {
					del.item(i).getParentNode().getParentNode()
							.removeChild(del.item(i).getParentNode());
				}
			}
		}
		doc.normalize();
		saveResult(doc, dataFileName);
	}
}