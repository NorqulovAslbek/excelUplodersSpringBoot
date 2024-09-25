package com.example.excelfileuploaders.controller;

import com.example.excelfileuploaders.entity.Student;
import com.example.excelfileuploaders.repository.StudentRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {  // XSSFWorkbook .xlsx fayllar uchun ishlatiladi

            Sheet sheet = workbook.getSheetAt(0);  // Birinchi sahifani olish

            StringBuilder time = new StringBuilder();
            StringBuilder number1 = new StringBuilder();
            StringBuilder number2 = new StringBuilder();
            StringBuilder shot = new StringBuilder();
            StringBuilder summa1 = new StringBuilder();
            StringBuilder summa2 = new StringBuilder();

            int rowCount = 0;
            for (Row row : sheet) {

                if (rowCount % 3 == 0 && rowCount != 0) {
                    Student student = new Student();
//   ---------------------------- BU SANASINI SAQLASH UCHUN   --------------------------------
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(time.toString().trim(), formatter);
                    student.setLocalDateTime(localDateTime);
//   ---------------------------- BU raqam1 SAQLASH UCHUN   --------------------------------
                    student.setNumber1(Integer.valueOf(number1.toString()));
//   ---------------------------- BU raqam2 SAQLASH UCHUN   ---------------------------------
                    student.setNumber2(Integer.valueOf(number2.toString()));
//   ---------------------------- BU shot  SAQLASH UCHUN   ---------------------------------
                    student.setShot(shot.toString());
//   ---------------------------- BU summa1  SAQLASH UCHUN   ---------------------------------
                    student.setSumma1(Double.valueOf(summa1.toString().replace(",", "")));
//   ---------------------------- BU summa2  SAQLASH UCHUN   ---------------------------------
                    student.setSumma2(Double.valueOf(summa2.toString().replace(",", "")));

                    studentRepository.save(student);
                    time.setLength(0); // StringBuilderni tozalash
                    number1.setLength(0);
                    number2.setLength(0);
                    shot.setLength(0);
                    summa1.setLength(0);
                    summa2.setLength(0);
                }

                Cell column0 = row.getCell(0);
                Cell column1 = row.getCell(1);
                Cell column2 = row.getCell(2);
                Cell column3 = row.getCell(3);
                Cell column4 = row.getCell(4);
                Cell column5 = row.getCell(5);

                if (column0 != null) {
                    time.append(column0.toString()).append(" ");
                }
                if (column1 != null) {
                    number1.append(column1.toString());
                }
                if (column2 != null) {
                    number2.append(column2.toString());
                }
                if (column3 != null) {
                    shot.append(column3.toString()).append(" ");
                }
                if (column4 != null) {
                    summa1.append(column4.toString());
                }
                if (column5 != null) {
                    summa2.append(column5.toString());
                }

                rowCount++;
            }


            return new ResponseEntity<>("Fayl muvaffaqiyatli yuklandi va saqlandi.", HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Xato yuz berdi: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

