package co.com.cat.clarodocumentation.services;

import co.com.cat.clarodocumentation.model.FileDto;
import co.com.cat.clarodocumentation.model.RowOfFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.com.cat.clarodocumentation.enums.Enum;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 *
 * @author Cristhian
 */
public class ClaroDocumentationImpl implements IClaroDocumentation {

	Logger logger = LoggerFactory.getLogger(ClaroDocumentationImpl.class);

	@Override
	public String getRoute() {
		JFileChooser fileChooser = new JFileChooser(Enum.FILE.getValue());
		FileFilter filtro = new FileNameExtensionFilter(Enum.DESCRIPTION_FILE.getValue(), Enum.TYPE_FILE.getValue());
		fileChooser.setFileFilter(filtro);
		int valor = fileChooser.showOpenDialog(fileChooser);
		if (valor == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else {
			logger.info(Enum.SELECTED_NONE.getValue());
		}
		return null;
	}

	@Override
	public List<FileDto> readArchive(String route) {
		List<FileDto> fileDtoList = new ArrayList<>();
		try (FileInputStream file = new FileInputStream(new File(route))) {
			try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				Row row;
				while (rowIterator.hasNext()) {
					FileDto fileDto = new FileDto();
					RowOfFile rowOfFile = new RowOfFile();
					row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell celda;
					while (cellIterator.hasNext()) {
						celda = cellIterator.next();
						buildFileDto(celda, rowOfFile);
					}
					fileDto.setRowOfFile(rowOfFile);
					fileDtoList.add(fileDto);
				}
			}
		} catch (Exception ex) {
			logger.error(Enum.ERROR.getValue(), ex.getMessage());
		}
		fileDtoList.remove(0);
		return fileDtoList;
	}

	@Override
	public void createReport(List<FileDto> list) {
		FileOutputStream output;
		try {
			output = new FileOutputStream(Enum.INITIAL_ROUTE.getValue());
			try (XWPFDocument document = new XWPFDocument()) {
				createHeader(document);
				list.stream().forEach(values -> {
					createDescriptionRequestValues(document, values.getRowOfFile());
					createTableValues(document, values.getRowOfFile().getInput());
					createTitle(document, Enum.SUCCESSFULL_RESPONSE.getValue(), ParagraphAlignment.LEFT, false, 12,
							UnderlinePatterns.NONE, Enum.COLOR.getValue(), true);
					createTableValues(document, values.getRowOfFile().getOutput());
					createTableJson(document, values.getRowOfFile().getRequestExample(), Enum.REQUEST.getValue());
					createTableJson(document, values.getRowOfFile().getResponseExample(), Enum.RESPONSE.getValue());
					createTableJson(document, values.getRowOfFile().getResponseExceptionsExample(),
							Enum.RESPONSE_ERROR.getValue());
				});
				document.write(output);
			}
			output.close();
		} catch (IOException ex) {
			logger.error(Enum.ERROR.getValue(), ex.getMessage());
		}

	}

	private void buildFileDto(Cell celda, RowOfFile rowOfFile) {
		switch (celda.getColumnIndex()) {
		case 0:
			rowOfFile.setDescription(celda.getStringCellValue());
			break;
		case 1:
			rowOfFile.setUrl(celda.getStringCellValue());
			break;
		case 2:
			rowOfFile.setOperation(celda.getStringCellValue());
			break;
		case 3:
			rowOfFile.setHeaders(celda.getStringCellValue());
			break;
		case 4:
			rowOfFile.setInput(celda.getStringCellValue());
			break;
		case 5:
			rowOfFile.setOutput(celda.getStringCellValue());
			break;
		case 6:
			rowOfFile.setRequestExample(celda.getStringCellValue());
			break;
		case 7:
			rowOfFile.setResponseExample(celda.getStringCellValue());
			break;
		case 8:
			rowOfFile.setResponseExceptionsExample(celda.getStringCellValue());
			break;
		case 9:
			rowOfFile.setSupplies(celda.getStringCellValue());
			break;
		case 10:
			rowOfFile.setRequestExampleMultiple(celda.getStringCellValue());
			break;
		case 11:
			rowOfFile.setObservations(celda.getStringCellValue());
			break;
		case 12:
			rowOfFile.setState(celda.getStringCellValue());
			break;
		default:
			break;

		}
	}

	private void createHeader(XWPFDocument document) {
		createTitle(document, Enum.TITLE.getValue(), ParagraphAlignment.CENTER, true, 15, UnderlinePatterns.WORDS,
				Enum.BLACK_COLOR.getValue(), true);
		createTitle(document, Enum.SUBTITLE.getValue(), ParagraphAlignment.LEFT, false, 12, UnderlinePatterns.NONE,
				Enum.BLACK_COLOR.getValue(), true);

		XWPFTable table = document.createTable();

		List<String> listText = Arrays.asList(Enum.NAME.getValue(), Enum.DATE.getValue(), Enum.DESCRIPTION.getValue());

		XWPFTableRow tableRowOne = table.getRow(0);
		tableFormat(tableRowOne, table, listText);

		tableRowOne = table.createRow();

		tableRowOne.getCell(0).setText(Enum.CREATOR.getValue());
		tableRowOne.getCell(1).setText(new Date().toString());
		tableRowOne.getCell(2).setText(Enum.CREATOR_DESCRIPTION.getValue());
	}

	private void createDescriptionRequestValues(XWPFDocument document, RowOfFile rowOfFile) {
		createTitle(document, Enum.SERVICE.getValue().concat(rowOfFile.getOperation()), ParagraphAlignment.CENTER, true,
				15, UnderlinePatterns.WORDS, Enum.BLACK_COLOR.getValue(), false);
		createTitle(document, Enum.OPERATION.getValue() + rowOfFile.getOperation(), ParagraphAlignment.LEFT, false, 12,
				UnderlinePatterns.NONE, Enum.COLOR.getValue(), false);
		createTitle(document, Enum.VERB.getValue(), ParagraphAlignment.LEFT, false, 12, UnderlinePatterns.NONE,
				Enum.COLOR.getValue(), false);
		createTitle(document, Enum.URL.getValue(), ParagraphAlignment.LEFT, false, 12, UnderlinePatterns.NONE,
				Enum.COLOR.getValue(), false);
		createTitle(document, Enum.PATH.getValue() + rowOfFile.getUrl().split(Enum.TEXT_PATH.getValue())[1],
				ParagraphAlignment.LEFT, false, 12, UnderlinePatterns.NONE, Enum.COLOR.getValue(), false);
		createTitle(document, Enum.REQUEST_VALUES.getValue(), ParagraphAlignment.LEFT, false, 12,
				UnderlinePatterns.NONE, Enum.COLOR.getValue(), false);
	}

	private void createTableValues(XWPFDocument document, String row) {
		XWPFTable table = document.createTable();

		List<String> listText = Arrays.asList(Enum.ORDER.getValue(), Enum.NAME.getValue(), Enum.TYPE.getValue(),
				Enum.DESCRIPTION.getValue());

		XWPFTableRow tableRowOne = table.getRow(0);
		tableFormat(tableRowOne, table, listText);
		tableRowOne = table.createRow();
		int w = 1;

		String[] input = row.replace(Enum.GREATER_SYMBOL.getValue(), Enum.SPACE_NONE.getValue())
				.replace(Enum.NUMBER.getValue(), Enum.LONG.getValue()).split(Enum.LESS_SYMBOL.getValue());
		int lengthStringResponse = input.length / 3;
		for (int z = 0; z < lengthStringResponse; z++) {
			for (int x = 0; x < 4; x++) {
				if (x == 0) {
					tableRowOne.getCell(x).setText(String.valueOf(z + 1));
				} else {
					tableRowOne.getCell(x).setText(input[w]);
					w++;
				}
			}
			tableRowOne = table.createRow();
		}
		table.removeRow(lengthStringResponse + 1);
	}

	private void createTableJson(XWPFDocument document, String json, String tableTittle) {
		createTitle(document, tableTittle, ParagraphAlignment.LEFT, false, 12, UnderlinePatterns.NONE,
				Enum.COLOR.getValue(), true);
		XWPFTable table = document.createTable();
		XWPFTableRow tableRowOne = table.getRow(0);
		tableFormat(tableRowOne, table, new ArrayList<>());
		tableRowOne.getCell(0).setText(json);
	}

	@JsonCreator
	private void createTitle(XWPFDocument document, String text, ParagraphAlignment alignment, boolean bold, int size,
			UnderlinePatterns underline, String color, boolean lineBreak) {
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun runTitle = paragraph.createRun();
		if (text.contains(Enum.SERVICE.getValue())) {
			paragraph.setPageBreak(true);
		}
		if (lineBreak) {
			runTitle.addBreak();
		}
		paragraph.setAlignment(alignment);
		runTitle.setBold(bold);
		runTitle.setFontSize(size);
		runTitle.setUnderline(underline);
		runTitle.setText(text);
		runTitle.setColor(color);

	}

	private void tableFormat(XWPFTableRow tableRowOne, XWPFTable table, List<String> listText) {
		CTTbl ctTblRequest = table.getCTTbl();
		CTTblPr tblPrRequest = ctTblRequest.getTblPr();
		CTTblWidth tblWRequest = tblPrRequest.getTblW();
		tblWRequest.setW(BigInteger.valueOf(5000));
		tblWRequest.setType(STTblWidth.PCT);

		for (int i = 0; i < listText.size(); i++) {
			tableRowOne.getCell(i).setText(listText.get(i));
			if (i < listText.size() - 1) {
				tableRowOne.addNewTableCell();
			}
		}
	}

}
