package com.lxl.valvedemo.page.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.config.ReportBuildConfig;
import com.lxl.valvedemo.model.buildModel.ReportBuildModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportSubTypeModel;
import com.lxl.valvedemo.model.buildModel.type2.InspectionReportTypeModel;
import com.lxl.valvedemo.service.BuildType2Service;
import com.lxl.valvedemo.util.DateUtil;
import com.lxl.valvedemo.util.StringUtil;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ReportRecordType2Fragment extends BaseBuildFragment {

    LinearLayout mReportHeaderContainer;
    EditText mReportHeaderWorkArea;
    EditText mReportHeaderStation;
    EditText mReportHeaderChecker;
    EditText mReportConfirmText;
    TextView mReportHeaderDate;

    LinearLayout mReportFillContanier;
    BuildType2Service tyeTwoService = new BuildType2Service();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fill_type_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindDataType1(view);
    }

    private void initView(View view) {
        mReportHeaderContainer = (LinearLayout) view.findViewById(R.id.report_header_type1);
        mReportHeaderWorkArea = (EditText) view.findViewById(R.id.report_header_workarea);//厂区
        mReportHeaderStation = (EditText) view.findViewById(R.id.report_header_station);
        mReportHeaderChecker = (EditText) view.findViewById(R.id.report_header_checker);
        mReportConfirmText = (EditText) view.findViewById(R.id.confirm_text);
        mReportHeaderDate = (TextView) view.findViewById(R.id.report_header_date);
        mReportFillContanier = (LinearLayout) view.findViewById(R.id.report_fill_contanier);
    }


    private void bindDataType1(View view) {
        mReportHeaderDate.setText(DateUtil.getCurrentDate());
        //绑定数据
        for (InspectionReportTypeModel typeModel : mReportBuildModel.inspectionReportModel.typeModelList) {
            View typeView = View.inflate(getContext(), R.layout.report_fill_type_2_type_view, null);
            TextView typeTextView = (TextView) typeView.findViewById(R.id.filltwo_type_name);
            LinearLayout typeContainer = (LinearLayout) typeView.findViewById(R.id.filltwo_type_container);
            typeTextView.setText(typeModel.typeName);
            for (InspectionReportSubTypeModel subTypeModel : typeModel.subTypeModelList) {
                View subTypeView = View.inflate(getContext(), R.layout.report_fill_type_2_subtype_view, null);
                TextView subTypeTextView = (TextView) subTypeView.findViewById(R.id.filltwo_subtype_name);
                LinearLayout subTypeContainer = (LinearLayout) subTypeView.findViewById(R.id.filltwo_subtype_container);
                subTypeTextView.setText(subTypeModel.subTypeName);
                for (InspectionReportSubTypeModel.InspectionReportCellModel cellModel : subTypeModel.cellModelList) {
                    View subTypeValueView = View.inflate(getContext(), R.layout.report_fill_type_2_subtypevvalue_view, null);
                    TextView subTypeTextValueTV = (TextView) subTypeValueView.findViewById(R.id.filltwo_subtypevalue_require);
//                    EditText recordEdit = (EditText) subTypeValueView.findViewById(R.id.check_record_edit);
//                    EditText descEdit = (EditText) subTypeValueView.findViewById(R.id.check_desc_edit);
                    subTypeTextValueTV.setText(cellModel.requireDesc);
                    subTypeContainer.addView(subTypeValueView);
                }
                typeContainer.addView(subTypeView);
            }
            mReportFillContanier.addView(typeView);
        }
    }

    @Override
    public ReportBuildModel getBuildModel() {
        mReportBuildModel = new ReportBuildModel();
        mReportBuildModel.buildType = ReportBuildModel.BUILD_TYPE_TWO;
        //读取execl
        try {
            InputStream open = getActivity().getAssets().open(mPath + ReportBuildConfig.Execl_Suffix);
            InspectionReportModel inspectionReportModel = tyeTwoService.readReportTypeTwo(open);
            mReportBuildModel.inspectionReportModel = inspectionReportModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReportBuildModel;
    }

    @Override
    public ReportBuildModel buildBuildModel() {
        ReportBuildModel model = mReportBuildModel;
        String workArea = mReportHeaderWorkArea.getText().toString();
        String station = mReportHeaderStation.getText().toString();
        String checker = mReportHeaderChecker.getText().toString();
        String reportConfirmText = mReportConfirmText.getText().toString();
        String data = mReportHeaderDate.getText().toString();
        model.inspectionReportModel.workAreaText = workArea;
        model.inspectionReportModel.stationText = station;
        model.inspectionReportModel.checkerText = checker;
        model.inspectionReportModel.dateText = data;
        model.inspectionReportModel.confirmText = reportConfirmText;

        //如果值为空则不展示
        for (int i = 0; i < mReportFillContanier.getChildCount(); i++) {
            InspectionReportTypeModel typeModel = model.inspectionReportModel.typeModelList.get(i);
            LinearLayout typeView = (LinearLayout) mReportFillContanier.getChildAt(i);
            TextView typeName = (TextView) typeView.findViewById(R.id.filltwo_type_name);
            LinearLayout typeContainer = (LinearLayout) typeView.findViewById(R.id.filltwo_type_container);
            for (int j = 0; j < typeContainer.getChildCount(); j++) {
                InspectionReportSubTypeModel reportSubTypeModel = typeModel.subTypeModelList.get(j);
                LinearLayout subTypeView = (LinearLayout) typeContainer.getChildAt(j);
                if (subTypeView == null) {
                    Log.i("lxltest", "test");
                    continue;
                }
                TextView subTypeName = (TextView) subTypeView.findViewById(R.id.filltwo_subtype_name);
                LinearLayout subTypeContainer = (LinearLayout) subTypeView.findViewById(R.id.filltwo_subtype_container);
                boolean recordIsNull = true;
                for (int k = 0; k < subTypeContainer.getChildCount(); k++) {
                    View subTypeValueContainer = subTypeContainer.getChildAt(k);
                    InspectionReportSubTypeModel.InspectionReportCellModel inspectionReportCellModel = reportSubTypeModel.cellModelList.get(k);
                    TextView requireText = (TextView) subTypeValueContainer.findViewById(R.id.filltwo_subtypevalue_require);
                    EditText recordEdit = (EditText) subTypeValueContainer.findViewById(R.id.check_record_edit);
                    EditText descEdit = (EditText) subTypeValueContainer.findViewById(R.id.check_desc_edit);
                    inspectionReportCellModel.checkRecord = recordEdit.getText().toString();
                    if (!StringUtil.emptyOrNull(inspectionReportCellModel.checkRecord)) {
                        recordIsNull = false;
                    }
                    inspectionReportCellModel.checkDesc = descEdit.getText().toString();
                }
                reportSubTypeModel.isNotCreate = recordIsNull;
            }
        }
        //获取值
        return model;
    }
}
