package com.lxl.valvedemo.config;

import com.lxl.valvedemo.entity.ReportSelectionItemEntity;
import com.lxl.valvedemo.entity.ReportSelectionSubItemEntity;
import com.lxl.valvedemo.entity.SingleSelectionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataConfig {

    static Map<String, SingleSelectionEntity> map = new HashMap<String, SingleSelectionEntity>();
    static Map<String, List<ReportSelectionItemEntity>> reportmap = new HashMap<String, List<ReportSelectionItemEntity>>();

    public static void init() {
        initSelect();
        initReport();
    }

    public static void initReport() {

        if (reportmap.size() > 0) {
            return;
        }

        List<ReportSelectionItemEntity> list = new ArrayList<ReportSelectionItemEntity>();

        ReportSelectionItemEntity itemEntity1 = new ReportSelectionItemEntity();
        ReportSelectionItemEntity itemEntity2 = new ReportSelectionItemEntity();
        ReportSelectionItemEntity itemEntity3 = new ReportSelectionItemEntity();
        ReportSelectionItemEntity itemEntity4 = new ReportSelectionItemEntity();
        ReportSelectionItemEntity itemEntity5 = new ReportSelectionItemEntity();

        itemEntity1.itemTitle = "流量计算机检测";
        itemEntity1.reportSelectionList.add(new ReportSelectionSubItemEntity("检查流量计算机中的相关参数，标准状况参比条件、压力温度的量程设置、气体组分、管道内径、键盘值压力、温度等是否正常，并记录。"));
        itemEntity1.reportSelectionList.add(new ReportSelectionSubItemEntity("用网线将笔记本电脑与流量计算机连接，对报警信息、事件记录进行下载存档。"));

        itemEntity2.itemTitle = "压力变送器检测";
        itemEntity2.reportSelectionList.add(new ReportSelectionSubItemEntity("检查压力变送器引压管路是否有堵塞（通过三阀组放空检测气路是否畅通）。"));
        itemEntity2.reportSelectionList.add(new ReportSelectionSubItemEntity("检查压力变送器三阀组、根部阀上的铅封是否破坏（做好铅封或锁定）。"));
        itemEntity2.reportSelectionList.add(new ReportSelectionSubItemEntity("检查压力变动送器是否绝压变送器，放空后观察示值是否为0.1MPa。"));

        itemEntity3.itemTitle = "温度变送器检测";
        itemEntity3.reportSelectionList.add(new ReportSelectionSubItemEntity("抽检温度变送器套管内是否有导热油，插入深度是否符合要求。"));
        itemEntity3.reportSelectionList.add(new ReportSelectionSubItemEntity("检查温度变送器是否与一次温度表一致。"));

        itemEntity4.itemTitle = "流量计检测";
        itemEntity4.reportSelectionList.add(new ReportSelectionSubItemEntity("检查超声波流量计外观是否完好。"));
        itemEntity4.reportSelectionList.add(new ReportSelectionSubItemEntity("检查流量计电缆连接、探头安装有无异常情况，注油孔油位是否正常。"));
        itemEntity4.reportSelectionList.add(new ReportSelectionSubItemEntity("每年9月至10月份，对流量计进行声速核查，确定流量计处于完好状态(一年一次，做好备份)。"));
        itemEntity4.reportSelectionList.add(new ReportSelectionSubItemEntity("可选项四"));


        itemEntity5.itemTitle = "色谱分析仪检测";
        itemEntity5.reportSelectionList.add(new ReportSelectionSubItemEntity("检查载气（110psi）、标气（20psi）、样气（20psi）压力值是否正常。"));
        itemEntity5.reportSelectionList.add(new ReportSelectionSubItemEntity("电脑连接色谱分析仪，基线走势是否异常。"));
        itemEntity5.reportSelectionList.add(new ReportSelectionSubItemEntity("管线连接处是否完好，有无泄漏点。"));
        itemEntity5.reportSelectionList.add(new ReportSelectionSubItemEntity("加热器与通风风扇是否正常工作。"));
        itemEntity5.reportSelectionList.add(new ReportSelectionSubItemEntity("色谱分析仪是否有报警。"));

        list.add(itemEntity1);
        list.add(itemEntity2);
        list.add(itemEntity3);
        list.add(itemEntity4);
        list.add(itemEntity5);
        reportmap.put("2", list);
    }

    public static void initSelect() {
        if (map.size() > 0) {
            return;
        }

        SingleSelectionEntity value = new SingleSelectionEntity();
        value.level = 0;
        value.key = "root";
        value.anwserStr = "选择种类";
        value.isCanSelect = true;

        SingleSelectionEntity value0 = new SingleSelectionEntity();
        SingleSelectionEntity value1 = new SingleSelectionEntity();
        SingleSelectionEntity value2 = new SingleSelectionEntity();

        value0.key = "0";
        value0.level = 1;
        value0.isCanSelect = false;
        value0.itemStr = "机械设备";
//        value0.anwserStr = "请选择专业";

        value1.key = "1";
        value1.level = 1;
        value1.itemStr = "电气";
        value1.isCanSelect = false;

        value2.key = "2";
        value2.level = 1;
        value2.itemStr = "仪表航空通信";
        value2.isCanSelect = false;
        value2.isCanJump = true;

        value.selectList.add(value0);
        value.selectList.add(value1);
        value.selectList.add(value2);

//        SingleSelectionEntity value00 = new SingleSelectionEntity();
//        SingleSelectionEntity value01 = new SingleSelectionEntity();
//        SingleSelectionEntity value02 = new SingleSelectionEntity();
//        value00.key = "0_0";
//        value00.level = 2;
//        value00.isCanSelect = true;
//        value00.itemStr = "设备专业";
//        value00.anwserStr = "请选择站点";
//
//        value01.key = "0_1";
//        value01.level = 2;
//        value01.isCanSelect = false;
//        value01.itemStr = "电气专业";
//
//        value02.key = "0_2";
//        value02.level = 2;
//        value02.isCanSelect = false;
//        value02.itemStr = "仪表自动化专业";
//
//        value0.selectList.add(value00);
//        value0.selectList.add(value01);
//        value0.selectList.add(value02);
//
//        SingleSelectionEntity value000 = new SingleSelectionEntity();
//        SingleSelectionEntity value001 = new SingleSelectionEntity();
//
//        value000.key = "0_0_0";
//        value000.level = 3;
//        value000.isCanJump = true;
//        value000.itemStr = "枣庄站";
//
//        value001.key = "0_0_1";
//        value001.level = 3;
//        value001.isCanJump = false;
//        value001.itemStr = "临沂站";
//
//        value00.selectList.add(value000);
//        value00.selectList.add(value001);

        map.put(value.key, value);
        map.put(value0.key, value0);
        map.put(value1.key, value1);
//        map.put(value00.key, value00);
//        map.put(value01.key, value01);
//        map.put(value02.key, value02);
//        map.put(value000.key, value000);
//        map.put(value001.key, value001);
    }

    public static SingleSelectionEntity getSingleSelctionEntity(String key) {

        SingleSelectionEntity singleSelctionEntity = map.get(key);

        if (singleSelctionEntity == null) {
            singleSelctionEntity = new SingleSelectionEntity();
        }

        return singleSelctionEntity;
    }

    public static List<ReportSelectionItemEntity> getReportSelectionEntity(
            String key) {

        List<ReportSelectionItemEntity> list = reportmap.get(key);
        if (list == null) {
            list = new ArrayList<ReportSelectionItemEntity>();
        }
        return list;
    }
}
