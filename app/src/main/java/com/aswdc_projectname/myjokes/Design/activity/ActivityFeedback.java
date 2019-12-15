package com.aswdc_projectname.myjokes.Design.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aswdc_projectname.myjokes.BuildConfig;
import com.aswdc_projectname.myjokes.R;
import com.aswdc_projectname.myjokes.api.ApiClient;
import com.aswdc_projectname.myjokes.api.ApiInterface;
import com.aswdc_projectname.myjokes.model.Model_ApiResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFeedback extends AppCompatActivity {

    TextView tvName;
    TextView tvMobile;
    TextView tvRemark;
    TextView tvEmail;
    Button btnSend;
    Button btnClear;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    String emailPattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Feedback");
        setContentView(R.layout.activity_feedback);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tvName = (TextView) findViewById(R.id.feedback_tv_name);
        tvMobile = (TextView) findViewById(R.id.feedback_tv_mobile);
        tvRemark = (TextView) findViewById(R.id.feedback_tv_remark);
        tvEmail = (TextView) findViewById(R.id.feedback_tv_email);
        btnSend = (Button) findViewById(R.id.feedback_btn_send);
        btnClear = (Button) findViewById(R.id.feedback_btn_clear);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Sending...");

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                    if (CheckNetwork.checkInternet(ActivityFeedback.this))
                        sendFeedBack();
                    else {
                        new AlertDialog.Builder(ActivityFeedback.this)
                                .setMessage(R.string.error_message_internet_connection)
                                .setPositiveButton("Ok", null)
                                .show();
                    }

                }
            }
        });

    }

    boolean validation() {

        boolean flage = true;
        if (tvRemark.getText().toString().length() <= 0) {
            tvRemark.setError("Enter Remark");
            tvRemark.requestFocus();
            flage = false;
        } else if (tvRemark.getText().toString().equalsIgnoreCase(".")) {
            tvRemark.setError("Invalid Input");
            tvRemark.requestFocus();
            flage = false;
        }
//        if (tvEmail.getText().toString().length() <= 0) {
//            tvEmail.setError("Enter Email");
//            tvEmail.requestFocus();
//            flage = false;
//        } else
        if (tvEmail.getText().toString().equalsIgnoreCase(".")) {
            tvEmail.setError("Invalid Input");
            tvEmail.requestFocus();
            flage = false;
        } else if (tvEmail.getText().toString().length() != 0) {
            if ((!tvEmail.getText().toString().matches(emailPattern))) {
                tvEmail.setError("Enter Valid Email Id");
                tvEmail.requestFocus();
                flage = false;
            }
        }
//        if (tvMobile.getText().toString().length() <= 0) {
//            tvMobile.setError("Enter Mobile Number");
//            tvMobile.requestFocus();
//            flage = false;
//        } else
        if (tvMobile.getText().toString().equalsIgnoreCase(".")) {
            tvMobile.setError("Invalid Input");
            tvMobile.requestFocus();
            flage = false;
        } else if (tvMobile.getText().toString().length() != 10 && tvMobile.getText().toString().length() != 12 && tvMobile.getText().toString().length() != 0) {
            tvMobile.setError("Enter Valid Mobile Number");
            tvMobile.requestFocus();
            flage = false;
        }
        if (tvName.getText().toString().length() <= 0) {
            tvName.setError("Enter Name");
            tvName.requestFocus();
            flage = false;
        } else if (tvName.getText().toString().equalsIgnoreCase(".")) {
            tvName.setError("Invalid Input");
            tvName.requestFocus();
            flage = false;
        }
        return flage;
    }

    void clear() {
        tvName.setText("");
        tvName.setError(null);
        tvEmail.setText("");
        tvEmail.setError(null);
        tvMobile.setText("");
        tvMobile.setError(null);
        tvRemark.setText("");
        tvRemark.setError(null);
        tvName.requestFocus();
    }

    public void sendFeedBack() {
        mProgressDialog.show();
        apiService = ApiClient.getClientContact().create(ApiInterface.class);
        Call<Model_ApiResponse> call = apiService.insertFeedback(BuildConfig.app_key_feedback, getResources().getString(R.string.app_name), BuildConfig.VERSION_CODE + "", "Android", tvName.getText().toString() + "", tvMobile.getText().toString() + "", tvEmail.getText().toString() + "", tvRemark.getText().toString() + "", "");
        call.enqueue(new Callback<Model_ApiResponse>() {
            @Override
            public void onResponse(Call<Model_ApiResponse> call, Response<Model_ApiResponse> response) {

                if (response.body().getIsResult() == 1)
                    Toast.makeText(ActivityFeedback.this, "Your message has been send successfully.", Toast.LENGTH_LONG).show();
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                btnClear.performClick();
                finish();
            }

            @Override
            public void onFailure(Call<Model_ApiResponse> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast.makeText(ActivityFeedback.this, "Check your connection", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (view instanceof EditText) {
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP &&
                    !getLocationOnScreen(innerView).contains(x, y)) {

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }

        return handleReturn;
    }

    protected Rect getLocationOnScreen(View mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }
}
