package com.lxl.valvedemo.service;

import com.lxl.valvedemo.inter.BuildResultInter;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.util.StyleUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/4 0004.
 */

public abstract class BuildTypeBaseService {

    abstract public void writeReport(File outFile, ReportBuildModel reportBuildModel, BuildResultInter inter) throws IOException;


    public int[] getRowHeightByIndex(Sheet sheet, HSSFCell cell) {
        int[] merge = new int[2];
        merge[0] = cell.getRowIndex();
        merge[1] = cell.getRowIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            int columnIndex = cell.getColumnIndex();
            int rowIndex = cell.getRowIndex();
            if (firstColumn == columnIndex && firstRow == rowIndex) {
                merge[0] = firstRow;
                merge[1] = lastRow;
                return merge;
            }
        }
        return merge;
    }

    public abstract String checkInfo(ReportBuildModel buildModel);

    public HSSFCell createBaseCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont10LeftStyle(wb));
        return cell;
    }

    public HSSFCell createBase12Cell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont12LeftStyle(wb));
        return cell;
    }

    public HSSFCell createBaseNoBorderCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont10LeftNoBorderStyle(wb));
        return cell;
    }

    public HSSFCell createDescBoldCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont12BoldLeftStyle(wb));
        return cell;
    }

    public HSSFCell createDescBoldNoBorderCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont12BoldLeftNoBorderStyle(wb));
        return cell;
    }

    public HSSFCell createDescCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont12LeftStyle(wb));
        return cell;
    }

    public HSSFCell createDescCenterFontCell(HSSFWorkbook wb, HSSFRow row, int position) {
        HSSFCell cell = row.createCell(position);
        cell.setCellStyle(StyleUtil.createFont12BoldCenterStyle(wb));
        return cell;
    }

    /**
     * 核心逻辑，
     * 如果没有单元格就创建并只添加边框样式
     * 如果有就直接合并
     */
    public void mergedRegion(HSSFWorkbook wb, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        for (int i = firstRow; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            for (int j = firstCol; j <= lastCol; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellStyle(StyleUtil.createBorderStyle(wb));
                }
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 核心逻辑，
     * 如果没有单元格就创建并只添加边框样式
     * 如果有就直接合并
     */
    public void mergedRegionNoBorder(HSSFWorkbook wb, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

}
