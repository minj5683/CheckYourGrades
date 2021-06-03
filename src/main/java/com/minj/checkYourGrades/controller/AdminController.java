package com.minj.checkYourGrades.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minj.checkYourGrades.model.FileBucket;
import com.minj.checkYourGrades.model.Grade;
import com.minj.checkYourGrades.model.User;
import com.minj.checkYourGrades.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @RequestMapping
    public String adminPage(Model model) {

        logger.info("Load admin page");

        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);

        return "admin";
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/all")
    public JSONObject showAllUsers(Model model) {

        logger.info("Send database data");

        JSONObject resultObj = new JSONObject();
        resultObj.put("data", userService.getAllUsersJSON());

        return resultObj;
    }


    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject adminUpload(@Valid FileBucket fileBucket, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        logger.info("[upload] Upload excel data");

        JSONObject resultObj = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (result.hasErrors()) {
            logger.error("[upload] Form data has some errors");
            List<ObjectError> errors = result.getAllErrors();

            for (ObjectError error : errors) {
                logger.error("[upload] {}", error.getDefaultMessage());
            }

            resultObj.put("status", "empty");
            return resultObj;
        }

        MultipartFile file = fileBucket.getFile();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        //Path dirPath = Paths.get(rootDirectory + "\\resources\\files\\");
        //Path savePath = Paths.get(rootDirectory + "\\resources\\files\\" + file.getOriginalFilename());
        Path dirPath = Paths.get(rootDirectory + "resources/files/");
        Path savePath = Paths.get(rootDirectory + "resources/files/" + file.getOriginalFilename());

        File dir = new File(dirPath.toString());
        File[] fileList = dir.listFiles();

        if (file.isEmpty()) {
            logger.error("[upload] File is empty");

            resultObj.put("status", "empty");
            return resultObj;

        } else {
            logger.info("------------- file start -------------");
            logger.info("name: {}", file.getName());
            logger.info("filename: {}", file.getOriginalFilename());
            logger.info("size: {}", file.getSize());
            logger.info("savePath: {}", savePath);
            logger.info("-------------- file end --------------");
        }

        if (fileList != null && fileList.length > 0) {
            logger.info("[upload] Delete files in the files directory");
            try {
                for (int i = 0; i < fileList.length; i++) {
                    FileInputStream dfis = new FileInputStream(fileList[i]);
                    dfis.close();
                    fileList[i].delete();
                }
            } catch (IOException e) {
                logger.error("[upload] Error deleting files");
                e.printStackTrace();
            }
        }

        if (file != null && !file.isEmpty()) {

            File f = new File(savePath.toString());
            try {
                file.transferTo(f);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }

            Workbook workbook = null;
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(savePath.toString());

                try {
                    OPCPackage opcPackage = OPCPackage.open(fis);
                    workbook = WorkbookFactory.create(opcPackage);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null || row.getCell(0).getStringCellValue() == "") break;

                try {
                    row.getCell(2).getStringCellValue();
                    row.getCell(3).getStringCellValue();

                    row.getCell(0).getStringCellValue();
                    row.getCell(1).getNumericCellValue();

                } catch (IllegalStateException exp) {
                    logger.error("[upload] File format error");

                    try {
                        fis.close();
                        Files.delete(savePath);
                        logger.info("[upload] Delete file");
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error("[upload] Error deleting file");
                    }

                    resultObj.put("status", "error");
                    return resultObj;
                }
            }

            isUploading = true;

            //파일 형식에 문제가 없다면, 기존의 데이터를 삭제하고 새로 삽입
            userService.deleteAllUsers();
            userService.addAdmin();

            totalRowCount = sheet.getLastRowNum();
            logger.info("[upload] Total {}", totalRowCount);

            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);

                if (row == null || row.getCell(0).getStringCellValue() == "") break;

                User user = new User();
                Grade grade = new Grade();
                grade.setTotal(row.getCell(2).getStringCellValue());
                grade.setDetail(row.getCell(3).getStringCellValue());

                user.setGrade(grade);
                user.setUsername(row.getCell(0).getStringCellValue());
                user.setEnabled(true);
                user.setPassword((int) row.getCell(1).getNumericCellValue());
                //user.setPassword(Integer.toString((int) row.getCell(1).getNumericCellValue()));
                user.setAuthority("ROLE_USER");

                userService.addUser(user);
                dataArray.add(user);

                currentStateCount++;

                //System.out.println(row.getCell(0) + " | " + row.getCell(1) + " | " + row.getCell(2) + " | " + row.getCell(3));
            }

            try {
                fis.close();
                Files.delete(savePath);
                logger.info("[upload] Delete file");
            } catch (IOException e) {
                logger.error("[upload] Error deleting file");
                e.printStackTrace();
            }
        }

        logger.info("[upload] File upload successful");
        resultObj.put("status", "success");
        resultObj.put("data", dataArray);
        return resultObj;
    }


    private int totalRowCount = 0;
    private int currentStateCount = 0;
    private boolean isUploading = false;

    @ResponseBody
    @RequestMapping(value = "/uploadProgress", method = RequestMethod.POST)
    public String excelUploadProgress(HttpServletRequest request) throws Exception {
        int resultData = 0;

        if (isUploading && currentStateCount > 0 && totalRowCount > 0) {
            resultData = (int) (((double) currentStateCount / (double) totalRowCount) * 100);
            if (resultData > 100) {
                resultData = 100;
            }

            //System.out.println("resultData>>>>>>>>>>" + resultData);

        } else {
            resultData = -1;
        }

        return Integer.toString(resultData);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadSuccess", method = RequestMethod.POST)
    public void excelUploadSuccess(HttpServletRequest request) throws Exception {
        logger.info("[upload success] Clear data");
        totalRowCount = 0;
        currentStateCount = 0;
        isUploading = false;
    }
}
