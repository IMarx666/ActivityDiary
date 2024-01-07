/*
 * ActivityDiary
 *
 * Copyright (C) 2024 Raphael Mack http://www.raphael-mack.de
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

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import de.rampro.activitydiary.db.LocalDBHelper;

public class FeedbackHistoryActivity extends AppCompatActivity {

    ListView listViewFeedbackHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_history);

        listViewFeedbackHistory = findViewById(R.id.lvFeedbackHistory);
        loadFeedbackHistory();
    }



    private void loadFeedbackHistory() {
        LocalDBHelper dbHelper = new LocalDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "feedback",   // The table to query
                null,         // The array of columns to return (pass null to get all)
                null,         // The columns for the WHERE clause
                null,         // The values for the WHERE clause
                null,         // don't group the rows
                null,         // don't filter by row groups
                "timestamp DESC"   // The sort order
        );

        // 定义列名和对应的视图ID
        String[] fromColumns = {
                "userName", "userEmail", "useFrequency", "deviceType", "commonFunction",
                "featureEaseOfUse", "experienceProblems", "designAesthetics", "uiEaseOfUse",
                "designSuggestions", "appSpeedAndResponsiveness", "crashOrErrorDescription",
                "loadingTime", "overallSatisfaction", "favoriteFeatures", "dissatisfactions",
                "featureRequests", "existingFeatureImprovements", "otherImprovements",
                "otherFeedback", "timestamp"
        };
        int[] toViews = {
                R.id.tvUserName, R.id.tvUserEmail, R.id.tvUseFrequency, R.id.tvDeviceType, R.id.tvCommonFunction,
                R.id.tvFeatureEaseOfUse, R.id.tvExperienceProblems, R.id.tvDesignAesthetics, R.id.tvUiEaseOfUse,
                R.id.tvDesignSuggestions, R.id.tvAppSpeedAndResponsiveness, R.id.tvCrashOrErrorDescription,
                R.id.tvLoadingTime, R.id.tvOverallSatisfaction, R.id.tvFavoriteFeatures, R.id.tvDissatisfactions,
                R.id.tvFeatureRequests, R.id.tvExistingFeatureImprovements, R.id.tvOtherImprovements,
                R.id.tvOtherFeedback, R.id.tvTimestamp
        };

        // 创建并设置适配器
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.feedback_list_item,
                cursor,
                fromColumns,
                toViews,
                0
        );

        ListView listView = findViewById(R.id.lvFeedbackHistory);
        listView.setAdapter(adapter);
    }
}
