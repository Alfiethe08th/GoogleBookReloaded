package com.example.tentsering.googlebookreloaded;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText bookInput;
    public static final String EXTRA_MESSAGE = "My_UNIQUE_QUERY_MESSAGE_TO_BE_SEND_TO_BOOKSEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookInput = (EditText) findViewById(R.id.bookInput);
    }

    public void searchBook(View view) {
        //hide the keyboard after use.
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //store user input in a string
        String q = bookInput.getText().toString();
        if(q.length()!=0){

            Toast.makeText(getApplicationContext(), "You have entered: " + q, Toast.LENGTH_SHORT).show();

            //start a new activity, along with a string to start the other activity
            Intent intent = new Intent(MainActivity.this, BookSearch.class);
            intent.putExtra(EXTRA_MESSAGE, q);
            startActivity(intent);
        }
        else{

            Toast.makeText(getApplicationContext(), "You have entered nothing" + q,Toast.LENGTH_SHORT).show();
        }

}
}
