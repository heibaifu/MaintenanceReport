package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.util.StringUtil;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class BuildType1Service extends BuildTypeBaseService {

    public BuildType1Service() {

    }

    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        MaintainReportModel maintainReportModel = reportBuildModel.maintainReportModel;
        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(maintainReportModel.tableTile);
        titleCell.setCellStyle(StyleUtil.createTitleSmallFontStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        areaRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell areaCell = createDescBoldCell(wb, areaRow, 0);
        HSSFCell stationCell = createDescBoldCell(wb, areaRow, 4);
        stationCell.setCellValue(maintainReportModel.stationName + ":" + maintainReportModel.stationText);
        areaCell.setCellValue(maintainReportModel.workAreaName + ":" + maintainReportModel.workAreaText);
        stationCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        areaCell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        mergedRegion(wb, sheet, 1, 1, 0, 3);
        mergedRegion(wb, sheet, 1, 1, 4, 5);

        if (maintainReportModel.maintainList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        for (int i = -1; i < maintainReportModel.maintainList.size(); i++) {
            String positionCellStr = "序号";
            String equipmentNameCellStr = "设备名称";
            String specificationsCellStr = "规格型号";
            String equipmentIdCellStr = "设备编号";
            String maintainInfoCellStr = "检查与维护保养情况";
            String maintainDescCellStr = "备注";
            HSSFRow headerRow = sheet.createRow(i + 3);
            HSSFCell positionCell;
            HSSFCell equipmentNameCell;
            ;
            HSSFCell specificationsCell;
            HSSFCell equipmentIdCell;
            HSSFCell maintainInfoCell;
            HSSFCell maintainDescCell;
            if (i >= 0) {
                positionCell = createBaseCell(wb, headerRow, 0);
                equipmentNameCell = createBaseCell(wb, headerRow, 1);
                specificationsCell = createBaseCell(wb, headerRow, 2);
                equipmentIdCell = createBaseCell(wb, headerRow, 3);
                maintainInfoCell = createBaseCell(wb, headerRow, 4);
                maintainDescCell = createBaseCell(wb, headerRow, 5);
                MaintainReportItemModel maintainReportItemModel = maintainReportModel.maintainList.get(i);
                positionCellStr = String.valueOf(i + 1);
                equipmentNameCellStr = maintainReportItemModel.equipmentName;
                specificationsCellStr = maintainReportItemModel.specificationsName;
                equipmentIdCellStr = maintainReportItemModel.equipmentId;
                maintainInfoCellStr = maintainReportItemModel.maintainInfo;
                maintainDescCellStr = maintainReportItemModel.maintainDesc;

                //需要计算最大行高
                short excelCellAutoHeight = StyleUtil.getExcelCellAutoHeight(maintainInfoCellStr, 15, 17);
                short excelCellAutoHeight1 = StyleUtil.getExcelCellAutoHeight(maintainDescCellStr, 15, 4);
                excelCellAutoHeight = excelCellAutoHeight > excelCellAutoHeight1 ? excelCellAutoHeight : excelCellAutoHeight1;
                headerRow.setHeight(excelCellAutoHeight);
            } else {
                positionCell = createDescCenterFontCell(wb, headerRow, 0);
                equipmentNameCell = createDescCenterFontCell(wb, headerRow, 1);
                specificationsCell = createDescCenterFontCell(wb, headerRow, 2);
                equipmentIdCell = createDescCenterFontCell(wb, headerRow, 3);
                maintainInfoCell = createDescCenterFontCell(wb, headerRow, 4);
                maintainDescCell = createDescCenterFontCell(wb, headerRow, 5);
                areaRow.setHeight(StyleUtil.getRowHeight(28.5));
            }

            positionCell.setCellValue(positionCellStr);
            equipmentNameCell.setCellValue(equipmentNameCellStr);
            specificationsCell.setCellValue(specificationsCellStr);
            equipmentIdCell.setCellValue(equipmentIdCellStr);
            maintainInfoCell.setCellValue(maintainInfoCellStr);
            maintainDescCell.setCellValue(maintainDescCellStr);
        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        bottomRow.setHeight(StyleUtil.getRowHeight(28.5));
        HSSFCell checkCell = bottomRow.createCell(0);
        checkCell.setCellStyle(StyleUtil.createFont10BoldCenterNoBorderStyle(wb));
        checkCell.setCellValue("维护保养人员：" + maintainReportModel.checkerText + "      " + "确认人：" + maintainReportModel.confirmText + "      " + "日期：" + maintainReportModel.dateText);
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 0, 5));

        sheet.setColumnWidth(0, StyleUtil.getColumnWidth(4.5));
        sheet.setColumnWidth(1, StyleUtil.getColumnWidth(13));
        sheet.setColumnWidth(2, StyleUtil.getColumnWidth(13));
        sheet.setColumnWidth(3, StyleUtil.getColumnWidth(13));
        sheet.setColumnWidth(4, StyleUtil.getColumnWidth(35.4));
        sheet.setColumnWidth(5, StyleUtil.getColumnWidth(8.3));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }

    @Override
    public String checkInfo(ReportBuildModel reportBuildModel) {
        MaintainReportModel maintainReportModel = reportBuildModel.maintainReportModel;
        StringBuilder builder = new StringBuilder();
        if (StringUtil.emptyOrNull(maintainReportModel.workAreaText)) {
            builder.append("作业区，");
        }
        if (StringUtil.emptyOrNull(maintainReportModel.stationText)) {
            builder.append("补全场站，");
        }
        if (StringUtil.emptyOrNull(maintainReportModel.checkerText)) {
            builder.append("补全维护保养人员，");
        }
        return builder.toString();
    }


}
