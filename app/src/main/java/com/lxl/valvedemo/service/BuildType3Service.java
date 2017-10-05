package com.lxl.valvedemo.service;

import android.util.Log;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportItemModel;
import com.lxl.valvedemo.model.buildModel.type1.MaintainReportModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportByAreaModel;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByBase;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByCPU;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByESD;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportBySCADA;
import com.lxl.valvedemo.model.buildModel.type3.MaintainReportSubByNormal;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/9/28.
 */
public class

BuildType3Service extends BuildTypeBaseService {

    public BuildType3Service() {

    }

    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {
        MaintainReportByAreaModel maintainReportByArea = reportBuildModel.maintainReportByArea;

        //拷贝这种类型文件到到指定的目录
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Sheet1");

        //title
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(maintainReportByArea.tableName);
        titleCell.setCellStyle(StyleUtil.createTitleStyle(wb));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        //desc
        HSSFRow areaRow = sheet.createRow(1);
        HSSFCell stationNameCell = areaRow.createCell(0);
        HSSFCell stationTextCell = areaRow.createCell(1);
        HSSFCell dateNameCell = areaRow.createCell(3);
        HSSFCell dateTextCell = areaRow.createCell(4);
        stationNameCell.setCellValue(maintainReportByArea.stationName);
        stationTextCell.setCellValue(maintainReportByArea.stationText);
        dateNameCell.setCellValue(maintainReportByArea.dateName);
        dateTextCell.setCellValue(maintainReportByArea.dateText);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

        //desc
        HSSFRow headerRow = sheet.createRow(2);
        HSSFCell cell0 = headerRow.createCell(0);
        HSSFCell cell1 = headerRow.createCell(1);
        HSSFCell cell5 = headerRow.createCell(5);
        cell0.setCellValue("检查项目");
        cell1.setCellValue("检查内容");
        cell5.setCellValue("检查结论（如有故障详细记录故障状态）");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 4));

        if (maintainReportByArea.scadaList.size() == 0) {
            FileOutputStream fileOut = new FileOutputStream(outFile);
            wb.write(fileOut);
            fileOut.close();
            return;
        }
        HSSFRow row;
        for (int i = 0; i < maintainReportByArea.scadaList.size(); i++) {
            MaintainReportBySCADA reportBySCADA = maintainReportByArea.scadaList.get(i);
            int rowNum = sheet.getLastRowNum() + 1;
            row = sheet.createRow(rowNum);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(reportBySCADA.scadaTitle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
            for (int j = 0; j < reportBySCADA.subList.size(); j++) {
                MaintainReportSubByBase subByBase = reportBySCADA.subList.get(j);
                if (subByBase instanceof MaintainReportSubByCPU) {
                    MaintainReportSubByCPU subByCPU = (MaintainReportSubByCPU) subByBase;

                    //title


                } else if (subByBase instanceof MaintainReportSubByESD) {
                    MaintainReportSubByESD subByESD = (MaintainReportSubByESD) subByBase;

                } else {
                    MaintainReportSubByNormal subByNormal = (MaintainReportSubByNormal) subByBase;
                    for (int k = 0; k < subByNormal.normalItemValueList.size(); k++) {
                        MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue = subByNormal.normalItemValueList.get(k);
                        rowNum = sheet.getLastRowNum() + 1;
                        row = sheet.createRow(rowNum);
                        HSSFCell subCell0 = row.createCell(0);
                        HSSFCell subCell1 = row.createCell(1);
                        HSSFCell subCell4 = row.createCell(4);

                        subCell0.setCellValue(subByNormal.subNormalTitle);
                        subCell1.setCellValue(normalItemValue.columDesc);
                        subCell4.setCellValue(normalItemValue.columText);
                    }
                }
            }

        }

        //维护保养人员  + 日期
        int nextRow = sheet.getLastRowNum() + 1;
        HSSFRow bottomRow = sheet.createRow(nextRow);
        HSSFCell checkerCell = bottomRow.createCell(0);
        HSSFCell dataCell = bottomRow.createCell(4);
        checkerCell.setCellValue(maintainReportModel.checkerText);
        dataCell.setCellValue(maintainReportModel.dateText);
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(nextRow, nextRow, 4, 5));

        FileOutputStream fileOut = new FileOutputStream(outFile);
        wb.write(fileOut);
        fileOut.close();

        if (inter != null) {
            inter.buildSucess(outFile.getPath());
        }
    }


    public MaintainReportByAreaModel readReportType(InputStream open) throws IOException {
        MaintainReportByAreaModel mainAreaModel = new MaintainReportByAreaModel();
        HSSFWorkbook wb = new HSSFWorkbook(open);
        HSSFSheet sheet1 = wb.getSheet("Sheet1");
        setSCADAValue(mainAreaModel, sheet1);
        return mainAreaModel;
    }

    public void setSCADAValue(MaintainReportByAreaModel mainAreaModel, HSSFSheet sheet1) {
        List<Integer[]> allCloumRowNumList = findAllNextAllRow(mainAreaModel.tableName, sheet1);
        //仪表设备   SCADA系统   消防系统
        for (Integer[] rowNum : allCloumRowNumList) {
            MaintainReportBySCADA scada = new MaintainReportBySCADA();
            setSubSCADAValue(mainAreaModel, scada, sheet1, rowNum[0], rowNum[1]);
            mainAreaModel.scadaList.add(scada);
        }
        Log.i("lxltest", "size:" + allCloumRowNumList.size());
    }

    public void setSubSCADAValue(MaintainReportByAreaModel mainAreaModel, MaintainReportBySCADA scada, HSSFSheet sheet, int startRowNum, int endRowNum) {
        scada.scadaTitle = sheet.getRow(startRowNum).getCell(0).getStringCellValue();//仪表设备
        startRowNum++;
        while (true) {
            HSSFRow row = sheet.getRow(startRowNum);
            HSSFCell cell = row.getCell(0);//CPU机架 or 远程机架
            String stringCellValue = cell.getStringCellValue();
            int position = 0;
            if (stringCellValue.equals("CPU机架")) {
                MaintainReportSubByCPU reportSubByCPU = new MaintainReportSubByCPU(position++);
                scada.subList.add(reportSubByCPU);
                reportSubByCPU.cpuTitle = stringCellValue;
                if (mainAreaModel.tableName.contains("冀宁线")) {
                    reportSubByCPU.cpuColumName1 = "A机架";
                    reportSubByCPU.cpuColumName2 = "B机架";
                    reportSubByCPU.cpuColumName0 = "B机架";
                } else {
                    reportSubByCPU.cpuColumName1 = "PLC-A";
                    reportSubByCPU.cpuColumName2 = "PLC-B";
                    reportSubByCPU.cpuColumName3 = "ESD-A";
                    reportSubByCPU.cpuColumName4 = "ESD-B";
                    reportSubByCPU.cpuColumName0 = "B机架";
                }
                int[] rowHeightByIndex1 = getRowHeightByIndex(sheet, cell);//cpu机架的高度
                int k = rowHeightByIndex1[0];
                k++;//忽略第一行
                while (k <= rowHeightByIndex1[1]) {
                    HSSFRow cpuSubRow = sheet.getRow(k);
                    HSSFCell cpuSubCell = cpuSubRow.getCell(1);
                    int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuSubCell);
                    int cpuFirstRow = rowHeightByIndex[0];
                    int cpuLastRow = rowHeightByIndex[1];//CPU模块的高度
                    MaintainReportSubByCPU.MaintainReportByCPUSubValue cpuSubValue = new MaintainReportSubByCPU.MaintainReportByCPUSubValue();
                    cpuSubValue.cpuSubName = cpuSubCell.getStringCellValue();
                    reportSubByCPU.cpuSubList.add(cpuSubValue);
                    for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                        HSSFRow lineRow = sheet.getRow(i);
                        HSSFCell cell1 = lineRow.getCell(2);
                        MaintainReportSubByCPU.MaintainReportByCPUItemValue cpuItemValue = new MaintainReportSubByCPU.MaintainReportByCPUItemValue();
                        cpuItemValue.subItemName = cell1.getStringCellValue();
                        cpuSubValue.cpuItemValueList.add(cpuItemValue);
                    }
                    k = rowHeightByIndex[1] + 1;
                }
            } else if (stringCellValue.equals("ESD系统（Himatrix）")) {
                MaintainReportSubByESD maintainReportByESD = new MaintainReportSubByESD(position++);
                scada.subList.add(maintainReportByESD);
                maintainReportByESD.cpuColumName1 = "记录指示灯";
                maintainReportByESD.cpuColumName2 = "F30";
                maintainReportByESD.cpuColumName3 = "IO1";
                maintainReportByESD.cpuColumName4 = "IO2";
                HSSFRow cpuRow = sheet.getRow(startRowNum);
                HSSFCell cpuCell = cpuRow.getCell(0);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, cpuCell);
                int cpuFirstRow = rowHeightByIndex[0] + 1;
                int cpuLastRow = rowHeightByIndex[1];
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(i);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByESD.MaintainReportByESDItemValue cpuItemValue = new MaintainReportSubByESD.MaintainReportByESDItemValue();
                    cpuItemValue.rowTitle = cell1.getStringCellValue();
                    maintainReportByESD.esdItemValueList.add(cpuItemValue);
                }
            } else if (stringCellValue.equals("远程机架") && mainAreaModel.tableName.contains("平泰线")) {
                //暂不处理

            } else {
                MaintainReportSubByNormal maintainReportSubByNormal = new MaintainReportSubByNormal(position++);
                scada.subList.add(maintainReportSubByNormal);
                HSSFRow normalRow = sheet.getRow(startRowNum);
                HSSFCell normalCell = normalRow.getCell(0);
                int[] rowHeightByIndex = getRowHeightByIndex(sheet, normalCell);
                int cpuFirstRow = rowHeightByIndex[0];
                int cpuLastRow = rowHeightByIndex[1];
                maintainReportSubByNormal.subNormalTitle = normalCell.getStringCellValue();
                for (int i = cpuFirstRow; i <= cpuLastRow; i++) {
                    HSSFRow lineRow = sheet.getRow(i);
                    HSSFCell cell1 = lineRow.getCell(1);
                    MaintainReportSubByNormal.MaintainReportSubByNormalItemValue normalItemValue = new MaintainReportSubByNormal.MaintainReportSubByNormalItemValue();
                    normalItemValue.columDesc = cell1.getStringCellValue();
                    maintainReportSubByNormal.normalItemValueList.add(normalItemValue);
                }
            }

            int[] rowHeightByIndex = getRowHeightByIndex(sheet, cell);
            int lastRow = rowHeightByIndex[1];
            startRowNum = lastRow + 1;
            if (lastRow >= endRowNum) {
                break;
            }
        }
        Log.i("lxltest", "scada.subList:" + scada.subList.size());
    }

    public void setSubSubSCADAValue(MaintainReportBySCADA scada, HSSFSheet sheet, int startRowNum) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (firstRow == startRowNum && lastColumn == 0) {
                scada.scadaTitle = sheet.getRow(firstRow).getCell(0).getStringCellValue();
                scada.firstRow = firstRow;
                scada.lastRow = lastRow;
            }
        }
    }

    public List<Integer[]> findAllNextAllRow(String tableName, Sheet sheet) {
        List<Integer[]> allRowList = new ArrayList<>();
        if (tableName.contains("冀宁线")) {
            Integer[] integers1 = new Integer[]{3, 8};
            Integer[] integers2 = new Integer[]{9, 54};
            Integer[] integers3 = new Integer[]{55, 60};
            allRowList.add(integers1);
            allRowList.add(integers2);
            allRowList.add(integers3);
        } else {
            Integer[] integers3 = new Integer[]{4, 8};
            Integer[] integers1 = new Integer[]{10, 45};
            Integer[] integers2 = new Integer[]{47, 51};
            allRowList.add(integers1);
            allRowList.add(integers2);
            allRowList.add(integers3);
        }

        //寻找合并横行所有单元格的
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();
//            int lastColumn = range.getLastColumn();
//            int lastRow = range.getLastRow();
//            int firstRow = range.getFirstRow();
//            if (firstColumn == 0 && lastColumn == 5 && firstRow != 0) {
//                nextAllRowList.add(firstRow);
//            }
//        }
//        //去掉最大的三个
//        Collections.sort(nextAllRowList, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer lhs, Integer rhs) {
//                return lhs > rhs ? 1 : -1;
//            }
//        });
//        return nextAllRowList.subList(0, nextAllRowList.size() - 3);

        return allRowList;
    }

    @Override
    public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException {

    }
}
