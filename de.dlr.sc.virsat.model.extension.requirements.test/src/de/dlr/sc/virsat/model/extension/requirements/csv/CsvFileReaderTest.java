/*******************************************************************************
 * Copyright (c) 2008-2019 German Aerospace Center (DLR), Simulation and Software Technology, Germany.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package de.dlr.sc.virsat.model.extension.requirements.csv;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test for the CSV file reader
 *
 */
public class CsvFileReaderTest {
	
	private static final String HEADER_1 = "Header1";
	private static final String HEADER_2 = "Header2";
	private static final String HEADER_3 = "Header3";
	
	private static final String REQ_ATT_1 = "att1";
	private static final String REQ_ATT_2 = "att2";
	private static final String REQ_ATT_3 = "att3";
	
	private static final String SEPERATOR = ";";
	
	private static final String JSON_FILE_NAME = "dummy.csv";
	
	@Test
	public void testReadCsvFile() throws IOException {
		
		Path inputPath = Files.createTempDirectory("csvTest");
		Path csvFilePath = Paths.get(inputPath.toString() + File.separator + JSON_FILE_NAME);
		
		List<String> csvContent = Arrays.asList(HEADER_1 + SEPERATOR + HEADER_2 + SEPERATOR + HEADER_3,
				REQ_ATT_1 + SEPERATOR + REQ_ATT_2 + SEPERATOR + REQ_ATT_3);
		
		Files.write(csvFilePath, csvContent);
		
		CsvFileReader reader = new CsvFileReader();
		
		List<List<String>> importedCsvContent = reader.readCsvFile(csvFilePath.toString(), 0, -1);
		
		assertEquals("Number of lines correct", 2, importedCsvContent.size());
		
		List<String> header = importedCsvContent.get(0);
		assertEquals("Header correct", header.get(0), HEADER_1);
		assertEquals("Header correct", header.get(1), HEADER_2);
		assertEquals("Header correct", header.get(2), HEADER_3);
		
		List<String> reqLine = importedCsvContent.get(1);
		assertEquals("Req att correct", reqLine.get(0), REQ_ATT_1);
		assertEquals("Req att correct", reqLine.get(1), REQ_ATT_2);
		assertEquals("Req att correct", reqLine.get(2), REQ_ATT_3);
	}
	
	@Test
	public void testReadCsvFileWithNewLine() throws IOException {
		
		Path inputPath = Files.createTempDirectory("csvTest");
		Path csvFilePath = Paths.get(inputPath.toString() + File.separator + JSON_FILE_NAME);
		
		//Requirement is spread over two rows:
		List<String> csvContent = Arrays.asList(HEADER_1 + SEPERATOR + HEADER_2 + SEPERATOR + HEADER_3,
				REQ_ATT_1 + SEPERATOR, REQ_ATT_2 + SEPERATOR + REQ_ATT_3);
		
		Files.write(csvFilePath, csvContent);
		
		CsvFileReader reader = new CsvFileReader();
		
		List<List<String>> importedCsvContent = reader.readCsvFile(csvFilePath.toString(), 0, -1);
		
		assertEquals("Number of lines correct", 2, importedCsvContent.size());
		
		List<String> header = importedCsvContent.get(0);
		assertEquals("Header correct", header.get(0), HEADER_1);
		assertEquals("Header correct", header.get(1), HEADER_2);
		assertEquals("Header correct", header.get(2), HEADER_3);
		
		List<String> reqLine = importedCsvContent.get(1);
		assertEquals("Req att correct", reqLine.get(0), REQ_ATT_1);
		assertEquals("Req att correct", reqLine.get(1), REQ_ATT_2);
		assertEquals("Req att correct", reqLine.get(2), REQ_ATT_3);
	}
	
	@Test
	public void testReadCsvHeadline() throws IOException {
		
		Path inputPath = Files.createTempDirectory("csvTest");
		Path csvFilePath = Paths.get(inputPath.toString() + File.separator + JSON_FILE_NAME);
		
		List<String> csvContent = Arrays.asList(HEADER_1 + SEPERATOR + HEADER_2 + SEPERATOR + HEADER_3,
				REQ_ATT_1 + SEPERATOR + REQ_ATT_2 + SEPERATOR + REQ_ATT_3);
		
		Files.write(csvFilePath, csvContent);
		
		CsvFileReader reader = new CsvFileReader(";", 0, 1, 2);
		
		List<String> importedCsvHeader = reader.readCsvHeadline(csvFilePath.toString());
		
		assertEquals("Header correct", importedCsvHeader.get(0), HEADER_1);
		assertEquals("Header correct", importedCsvHeader.get(1), HEADER_2);
		assertEquals("Header correct", importedCsvHeader.get(2), HEADER_3);
		
	}
	
	@Test
	public void testReadCsvData() throws IOException {
		
		Path inputPath = Files.createTempDirectory("csvTest");
		Path csvFilePath = Paths.get(inputPath.toString() + File.separator + JSON_FILE_NAME);
		
		List<String> csvContent = Arrays.asList(HEADER_1 + SEPERATOR + HEADER_2 + SEPERATOR + HEADER_3,
				REQ_ATT_1 + SEPERATOR + REQ_ATT_2 + SEPERATOR + REQ_ATT_3);
		
		Files.write(csvFilePath, csvContent);
		
		CsvFileReader reader = new CsvFileReader(";", 0, 0, 2);  //Asume header is also data for now
		
		List<List<String>> importedCsvContent = reader.readCsvData(csvFilePath.toString());
		
		List<String> header = importedCsvContent.get(0);
		
		assertEquals("Header correct", header.get(0), HEADER_1);
		assertEquals("Header correct", header.get(1), HEADER_2);
		assertEquals("Header correct", header.get(2), HEADER_3);

		List<String> reqLine = importedCsvContent.get(1);
		assertEquals("Req att correct", reqLine.get(0), REQ_ATT_1);
		assertEquals("Req att correct", reqLine.get(1), REQ_ATT_2);
		assertEquals("Req att correct", reqLine.get(2), REQ_ATT_3);
	}

}
