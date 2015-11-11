package com.droidmaniac.datacore;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccount extends AppCompatActivity {


    EditText etUsername,etPassword,etRetype,etMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etRetype=(EditText)findViewById(R.id.etRetype);
        etMail=(EditText)findViewById(R.id.etMail);
    }

    public  void btnCreateAccount_onClick(View v)
    {
        if(etUsername.getText().toString()==null || etUsername.getText().toString().equals("") ||
        etPassword.getText().toString()==null || etPassword.getText().toString().equals("") ||
           etRetype.getText().toString()==null || etRetype.getText().toString().equals("") ||
                etMail.getText().toString()==null || etMail.getText().toString().equals(""))
        {
            Toast.makeText(CreateAccount.this,"Please Enter All Fields!",Toast.LENGTH_LONG).show();
        }

        else
        {
            if(!etPassword.getText().toString().equals(etRetype.getText().toString()))
            {

                Toast.makeText(CreateAccount.this,"Password do not match!",Toast.LENGTH_LONG).show();
            }
            else
            {
                final ProgressDialog pdia=new ProgressDialog(CreateAccount.this);
                pdia.setMessage("Creating Account... Please Hold on...");
                pdia.show();

                ParseUser user=new ParseUser();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail(etMail.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            pdia.dismiss();
                            Toast.makeText(CreateAccount.this, "Account created successfully", Toast.LENGTH_LONG).show();
                            CreateAccount.this.finish();
                        } else {
                            pdia.dismiss();
                            Toast.makeText(CreateAccount.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
