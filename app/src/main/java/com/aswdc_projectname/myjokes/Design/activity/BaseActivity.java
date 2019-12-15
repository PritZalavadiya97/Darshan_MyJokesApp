package com.aswdc_projectname.myjokes.Design.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.OnTextChange;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initVariables();

    public abstract void bindWidgetEvents();

    public void removeElevation() {
        try {
            getSupportActionBar().setElevation(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchBarCode(final EditText searchBar, final OnTextChange onTextChange) {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onTextChange.onChange(s.toString());
            }
        });
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBar.clearFocus();
                    hideKeyboard(searchBar);
                    return true;
                }
                return false;
            }
        });
    }

    public void hideKeyboard(View v) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        bindWidgetEvents();
    }

}
