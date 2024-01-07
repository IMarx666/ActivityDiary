/*
 * ActivityDiary
 *
 * Copyright (C) 2023 Raphael Mack http://www.raphael-mack.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.rampro.activitydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.rampro.activitydiary.db.LocalDBHelper;
import de.rampro.activitydiary.ui.main.MainActivity;

public class feedback extends AppCompatActivity {



    private EditText userName;
    private EditText userEmail;
    private Spinner spinnerUseFrequency;
    private Spinner useType;
    private Spinner useFunction;
    private RatingBar featureEaseOfUse;
    private EditText experienceProblems;
    private RatingBar designAesthetics;
    private RatingBar uiEaseOfUse;
    private EditText designSuggestions;
    private RatingBar appSpeedAndResponsiveness;
    private EditText crashOrErrorDescription;
    private EditText loadingTime;
    private RatingBar overallSatisfaction;
    private EditText favoriteFeatures;
    private EditText dissatisfactions;
    private EditText featureRequests;
    private EditText existingFeatureImprovements;
    private EditText otherImprovements;
    private EditText otherFeedback;
    private TextView privacyPolicy;
    private Button submitFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);








        // 初始化控件
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        spinnerUseFrequency = findViewById(R.id.spinnerUseFrequency);
        useType = findViewById(R.id.UseType);
        useFunction = findViewById(R.id.UseFunction);
        featureEaseOfUse = findViewById(R.id.featureEaseOfUse);
        experienceProblems = findViewById(R.id.experienceProblems);
        designAesthetics = findViewById(R.id.designAesthetics);
        uiEaseOfUse = findViewById(R.id.uiEaseOfUse);
        designSuggestions = findViewById(R.id.designSuggestions);
        appSpeedAndResponsiveness = findViewById(R.id.appSpeedAndResponsiveness);
        crashOrErrorDescription = findViewById(R.id.crashOrErrorDescription);
        loadingTime = findViewById(R.id.loadingTime);
        overallSatisfaction = findViewById(R.id.overallSatisfaction);
        favoriteFeatures = findViewById(R.id.favoriteFeatures);
        dissatisfactions = findViewById(R.id.dissatisfactions);
        featureRequests = findViewById(R.id.featureRequests);
        existingFeatureImprovements = findViewById(R.id.existingFeatureImprovements);
        otherImprovements = findViewById(R.id.otherImprovements);
        otherFeedback = findViewById(R.id.otherFeedback);
        privacyPolicy = findViewById(R.id.privacyPolicy);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);









        // 添加TextWatcher来监听EditText的文本变化
        userEmail.addTextChangedListener(new TextWatcher() {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String phonePattern = "\\d{11}|(?:\\d{3}-){2}\\d{4}";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 这里可以留空
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 这里可以留空
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(emailPattern) && !s.toString().matches(phonePattern)) {
                    userEmail.setError("请输入有效的电子邮件地址或电话号码");
                }
            }
        });


        //隐私政策

        // 在您的Activity中找到隐私政策的TextView
        TextView privacyPolicyLink = findViewById(R.id.privacyPolicy);
    // 设置监听器
        privacyPolicyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用LayoutInflater来加载dialog_privacy_policy.xml布局
                LayoutInflater inflater = LayoutInflater.from(feedback.this);
                View view = inflater.inflate(R.layout.dialog_privacy_policy, null);

                // 创建AlertDialog并设置布局
                AlertDialog.Builder builder = new AlertDialog.Builder(feedback.this);
                builder.setView(view);

                // 创建AlertDialog
                final AlertDialog dialog = builder.create();

                // 设置点击事件，使得用户可以关闭对话框
                view.findViewById(R.id.privacy_policy_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // 显示对话框
                dialog.show();
            }
        });




        //提交反馈监听器

        Button submitButton = findViewById(R.id.submitFeedbackButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateForm()) {
                    // 如果表单有效，则将数据插入数据库
                    insertFeedbackIntoDatabase();
                    // 关闭当前的Activity
                    finish();

               }
//                else {
//                    // 如果表单无效，则显示一个Toast消息
//                    showToast("请填写所有必填字段");
//                }







            }
        });

        Button viewHistoryButton = findViewById(R.id.btnViewFeedbackHistory);
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback.this, FeedbackHistoryActivity.class);
                startActivity(intent);
            }
        });








    }




    private boolean validateForm() {
        // 用户姓名验证
        if (isEmpty(userName)) {
            userName.setError("请输入姓名");
            showToast("姓名没输入");
            return false;
        }

        // 用户电子邮件或电话验证
        if (isEmpty(userEmail)) {
            userEmail.setError("请输入电子邮件或电话");
            showToast("电话或电子邮件没输入");
            return false;
        }

        // 使用频率验证
        if (isSpinnerUnselected(spinnerUseFrequency)) {
            showToast("请选择使用频率");
            return false;
        }

        // 设备类型验证
        if (isSpinnerUnselected(useType)) {
            showToast("请选择设备类型");
            return false;
        }

        // 常用功能验证
        if (isSpinnerUnselected(useFunction)) {
            showToast("请选择常用功能");
            return false;
        }

        // 体验问题描述验证
        if (isEmpty(experienceProblems)) {
            experienceProblems.setError("请输入体验问题描述");
            showToast("请输入体验问题描述");
            return false;
        }

        // 设计改进建议验证
        if (isEmpty(designSuggestions)) {
            designSuggestions.setError("请输入设计改进建议");
            showToast("请输入设计改进建议");
            return false;
        }

        // 崩溃或错误问题描述验证
        if (isEmpty(crashOrErrorDescription)) {
            crashOrErrorDescription.setError("请输入崩溃或错误问题描述");
            showToast("请输入崩溃或错误问题描述");
            return false;
        }

        // 功能或页面加载时间验证
        if (isEmpty(loadingTime)) {
            loadingTime.setError("请输入功能或页面加载时间");
            showToast("请输入功能或页面加载时间");
            return false;
        }

        // 最喜欢的功能或特点验证
        if (isEmpty(favoriteFeatures)) {
            favoriteFeatures.setError("请输入您最喜欢的功能或特点");
            showToast("请输入您最喜欢的功能或特点");
            return false;
        }

        // 不满意的方面验证
        if (isEmpty(dissatisfactions)) {
            dissatisfactions.setError("请输入不满意的方面");
            showToast("请输入不满意的方面");
            return false;
        }

        // 对新功能的需求或建议验证
        if (isEmpty(featureRequests)) {
            featureRequests.setError("请输入对新功能的需求或建议");
            showToast("请输入对新功能的需求或建议");
            return false;
        }

        // 对现有功能的改进建议验证
        if (isEmpty(existingFeatureImprovements)) {
            existingFeatureImprovements.setError("请输入对现有功能的改进建议");
            showToast("请输入对现有功能的改进建议");
            return false;
        }

        // 其他希望改进的方面验证
        if (isEmpty(otherImprovements)) {
            otherImprovements.setError("请输入其他希望改进的方面");
            showToast("请输入其他希望改进的方面");
            return false;
        }

        // 其他想法或建议验证
        if (isEmpty(otherFeedback)) {
            otherFeedback.setError("请输入其他想法或建议");
            showToast("请输入其他想法或建议");
            return false;
        }

        // 所有验证通过
        return true;
    }



    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    // 辅助方法来检查Spinner是否未选择
    private boolean isSpinnerUnselected(Spinner spinner) {
        return spinner.getSelectedItemPosition() == 0; // 假设第一个选项是提示性的，如“请选择”
    }

    // 显示简短的提示信息
    private void showToast(String message) {
        Toast.makeText(feedback.this, message, Toast.LENGTH_SHORT).show();
    }






    //插入数据库
    private void insertFeedbackIntoDatabase() {
        LocalDBHelper dbHelper = new LocalDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userName", userName.getText().toString());
        values.put("userEmail", userEmail.getText().toString());
        values.put("useFrequency", spinnerUseFrequency.getSelectedItem().toString());
        values.put("deviceType", useType.getSelectedItem().toString());
        values.put("commonFunction", useFunction.getSelectedItem().toString());
        values.put("featureEaseOfUse", featureEaseOfUse.getRating());
        values.put("experienceProblems", experienceProblems.getText().toString());
        values.put("designAesthetics", designAesthetics.getRating());
        values.put("uiEaseOfUse", uiEaseOfUse.getRating());
        values.put("designSuggestions", designSuggestions.getText().toString());
        values.put("appSpeedAndResponsiveness", appSpeedAndResponsiveness.getRating());
        values.put("crashOrErrorDescription", crashOrErrorDescription.getText().toString());
        values.put("loadingTime", loadingTime.getText().toString());
        values.put("overallSatisfaction", overallSatisfaction.getRating());
        values.put("favoriteFeatures", favoriteFeatures.getText().toString());
        values.put("dissatisfactions", dissatisfactions.getText().toString());
        values.put("featureRequests", featureRequests.getText().toString());
        values.put("existingFeatureImprovements", existingFeatureImprovements.getText().toString());
        values.put("otherImprovements", otherImprovements.getText().toString());
        values.put("otherFeedback", otherFeedback.getText().toString());

        long newRowId = db.insert("feedback", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "反馈提交成功！", Toast.LENGTH_SHORT).show();

            db.close();

        } else {
            Toast.makeText(this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
        }


    }

}