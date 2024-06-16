package com.siteexample.attestation.services;

import com.siteexample.attestation.models.Post;
import com.siteexample.attestation.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttestationService {

    private final PostRepository postRepository;

    public void generateExcelAtt(HttpServletResponse response, List<Post> attestations) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet =  workbook.createSheet();
        HSSFRow row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);


        Cell cell0 = row.createCell(0); cell0.setCellValue("№ п/п"); cell0.setCellStyle(cellStyle);
        Cell cell1 = row.createCell(1); cell1.setCellValue("ДИ"); cell1.setCellStyle(cellStyle);
        Cell cell2 = row.createCell(2); cell2.setCellValue("Служба"); cell2.setCellStyle(cellStyle);
        Cell cell3 = row.createCell(3); cell3.setCellValue("Структурное подразделение"); cell3.setCellStyle(cellStyle);
        Cell cell4 = row.createCell(4); cell4.setCellValue("Категория профессии"); cell4.setCellStyle(cellStyle);
        Cell cell5 = row.createCell(5); cell5.setCellValue("Должность"); cell5.setCellStyle(cellStyle);
        Cell cell6 = row.createCell(6); cell6.setCellValue("Фамилия"); cell6.setCellStyle(cellStyle);
        Cell cell7 = row.createCell(7); cell7.setCellValue("Имя"); cell7.setCellStyle(cellStyle);
        Cell cell8 = row.createCell(8); cell8.setCellValue("Отчество"); cell8.setCellStyle(cellStyle);
        Cell cell9 = row.createCell(9); cell9.setCellValue("Номер последнего протокола"); cell9.setCellStyle(cellStyle);
        Cell cell10 = row.createCell(10); cell10.setCellValue("Удостоверение А.1."); cell10.setCellStyle(cellStyle);
        Cell cell11 = row.createCell(11); cell11.setCellValue("Удостоверение Б.8.3."); cell11.setCellStyle(cellStyle);
        Cell cell12 = row.createCell(12); cell12.setCellValue("Удостоверение Б.9.3."); cell12.setCellStyle(cellStyle);
        Cell cell13 = row.createCell(13); cell13.setCellValue("Дата последнего протокола"); cell13.setCellStyle(cellStyle);
        Cell cell14 = row.createCell(14); cell14.setCellValue("Номер предыдущего протокола"); cell14.setCellStyle(cellStyle);
        Cell cell15 = row.createCell(15); cell15.setCellValue("Удостоверение А.1."); cell15.setCellStyle(cellStyle);
        Cell cell16 = row.createCell(16); cell16.setCellValue("Удостоверение Б.8.3."); cell16.setCellStyle(cellStyle);
        Cell cell17 = row.createCell(17); cell17.setCellValue("Удостоверение Б.9.3."); cell17.setCellStyle(cellStyle);
        Cell cell18 = row.createCell(18); cell18.setCellValue("Дата предыдущего протокола"); cell18.setCellStyle(cellStyle);
        Cell cell19 = row.createCell(19); cell19.setCellValue("Дата предаттестационной подготовки"); cell19.setCellStyle(cellStyle);

        // Для форматирования даты в строку
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        int dataRowIndex = 1;
        for (Post attestation : attestations) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);

            dataRow.createCell(0).setCellValue(dataRowIndex);
            dataRow.createCell(1).setCellValue(attestation.getDirectorate()); sheet.autoSizeColumn(0);
            dataRow.createCell(2).setCellValue(attestation.getDepartment()); sheet.autoSizeColumn(1);
            dataRow.createCell(3).setCellValue(attestation.getStructureUnit()); sheet.autoSizeColumn(2);
            dataRow.createCell(4).setCellValue(attestation.getProfessionCategory()); sheet.autoSizeColumn(3);
            dataRow.createCell(5).setCellValue(attestation.getPosition()); sheet.autoSizeColumn(4);
            dataRow.createCell(6).setCellValue(attestation.getSurname()); sheet.autoSizeColumn(5);
            dataRow.createCell(7).setCellValue(attestation.getName()); sheet.autoSizeColumn(6);
            dataRow.createCell(8).setCellValue(attestation.getPatronymic()); sheet.autoSizeColumn(7);
            dataRow.createCell(9).setCellValue(attestation.getNumFirstprotocol()); sheet.autoSizeColumn(8);
            dataRow.createCell(10).setCellValue(attestation.getA1_firstprotocol()); sheet.autoSizeColumn(9);
            dataRow.createCell(11).setCellValue(attestation.getB83_firstprotocol()); sheet.autoSizeColumn(10);
            dataRow.createCell(12).setCellValue(attestation.getB93_firstprotocol()); sheet.autoSizeColumn(11);
            if (attestation.getDateFirstprotocol() != null) dataRow.createCell(13).setCellValue(df.format(attestation.getDateFirstprotocol())); sheet.autoSizeColumn(12);
            dataRow.createCell(14).setCellValue(attestation.getNumSecondprotocol()); sheet.autoSizeColumn(13);
            dataRow.createCell(15).setCellValue(attestation.getA1_secondprotocol()); sheet.autoSizeColumn(14);
            dataRow.createCell(16).setCellValue(attestation.getB83_secondprotocol()); sheet.autoSizeColumn(15);
            dataRow.createCell(17).setCellValue(attestation.getB93_secondprotocol()); sheet.autoSizeColumn(16);
            if (attestation.getDateSecondprotocol() != null) dataRow.createCell(18).setCellValue(df.format(attestation.getDateSecondprotocol())); sheet.autoSizeColumn(17);
            if (attestation.getDatePreparation() != null) dataRow.createCell(19).setCellValue(df.format(attestation.getDatePreparation())); sheet.autoSizeColumn(18);

            dataRowIndex++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
