package com.eNach_Cancellation.ServiceImpl;

import com.eNach_Cancellation.Entity.StatusManage;
import com.eNach_Cancellation.Model.CommonResponse;
import com.eNach_Cancellation.Model.SaveStatusRequest;
import com.eNach_Cancellation.Repository.StatusRepository;
import com.eNach_Cancellation.Service.Service;
import com.eNach_Cancellation.Utility.SendEmailUtility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service

@EnableScheduling
public class ServiceImpl implements Service {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private SendEmailUtility sendEmailUtility;

    private final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Override
    public CommonResponse statusRequest(SaveStatusRequest statusRequest) throws Exception {

        StatusManage statusManage = new StatusManage();
        CommonResponse commonResponse = new CommonResponse();

        statusRequest.validate();
        statusManage.setApplicationNo(statusRequest.getApplicationNo());
        statusManage.setCancelCause(statusRequest.getCancelCause());
        statusManage.setLoanNo(statusRequest.getLoanNo());
        statusManage.setCancellationTime(Timestamp.valueOf(LocalDateTime.now()));

        statusRepository.save(statusManage);
        logger.info("Cancellation status upload");
        commonResponse.setCode("0000");
        commonResponse.setMsg("Cancel status save successfully");

        return commonResponse;
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public ResponseEntity<?> generateReportForOfStatus() throws Exception {
        try {
            List<StatusManage> statusManage = statusRepository.findAll();
            if (statusManage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            generateExcel(statusManage);
            logger.info("Get data for generate excel.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void generateExcel(List<StatusManage> statusManageList) {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("status-Details");
            int rowCount = 0;

            String[] header = {"Application Number", "Loan Number", "Cancellation Cause", "Cancellation Time"};
            Row headerRow = sheet.createRow(rowCount++);
            int cellCount = 0;

            for (String headerValue : header) {
                headerRow.createCell(cellCount++).setCellValue(headerValue);
            }
            for (StatusManage list : statusManageList) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(list.getApplicationNo());
                row.createCell(1).setCellValue(list.getLoanNo());
                row.createCell(2).setCellValue(list.getCancelCause());
                row.createCell(3).setCellValue(list.getCancellationTime());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            byte[] excelData = outputStream.toByteArray();

            String email = "saurabhsingh2757@gmail.com";
            sendEmailUtility.sendEmailWithAttachment(email, excelData);

        } catch (Exception e) {
            System.out.println("Error while executing report query :" + e.getMessage());
        }
    }
}