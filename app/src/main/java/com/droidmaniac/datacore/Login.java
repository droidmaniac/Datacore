package com.droidmaniac.datacore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class Login extends AppCompatActivity
{


    EditText etUsername,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ParseUser user = ParseUser.getCurrentUser();

        if (user == null)
        {
            setContentView(R.layout.activity_login);
            etUsername=(EditText) findViewById(R.id.etUsername);
            etPassword=(EditText) findViewById(R.id.etPassword);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Login");
        }

        else
        {
            Intent intent = new Intent(this,ContactList.class);
            startActivity(intent);
            this.finish();
        }

    }

    public void btnLogin_OnClick(View v){
        if (etUsername.getText().toString() == null || etUsername.getText().toString().equals("") ||
            etPassword.getText().toString() == null || etPassword.getText().toString().equals("") )
        {
            Toast.makeText(Login.this,"Please enter both fields!",Toast.LENGTH_SHORT).show();
        }

        else
        {
            final ProgressDialog pdia = new ProgressDialog(Login.this);
            pdia.setMessage("Busy logging you in...Please hold tight...");
            pdia.show();
            ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(),

                    new LogInCallback()
                    {
                        @Override
                        public void done(ParseUser parseUser, ParseException e)
                        {
                            if (parseUser !=null)
                            {
                                boolean verified = parseUser.getBoolean("emailVerified");
                                
                                if (!verified)
                                {
                                    Toast.makeText(Login.this, "Please verify your email address first" +
                                             " before logging in", Toast.LENGTH_SHORT).show();
                                    pdia.dismiss();
                                    ParseUser.logOut();
                                }

                                else
                                {
                                    Toast.makeText(Login.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,ContactList.class);
                                    startActivity(intent);
                                    pdia.dismiss();
                                    Login.this.finish();

                                }
                            }

                            else
                            {
                                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                pdia.dismiss();
                            }

                        }
                    });
        }

    }


    public void btnCreate_OnClick(View v){
        Intent intent=new Intent(this,CreateAccount.class);
        startActivity(intent);
    }

    public void btnReset_OnClick(View v){
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.reset_password_popup, null);
        final EditText etMail = (EditText) layout.findViewById(R.id.etMail);

        AlertDialog.Builder dlgReset = new AlertDialog.Builder(Login.this);
        dlgReset.setView(layout);
        dlgReset.setTitle("Reset Password");

        dlgReset.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog pdia = new ProgressDialog(Login.this);
                pdia.setMessage("Sending message to yuor mail address for reset...");
                pdia.show();;
                ParseUser.requestPasswordResetInBackground(etMail.getText().toString(), new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if ( e == null)
                        {
                            Toast.makeText(Login.this,"Reset Instructions sent to your mail address!",Toast.LENGTH_SHORT).show();
                            pdia.dismiss();
                        }
                        else
                        {
                            Toast.makeText(Login.this,e.getMessage()+"!",Toast.LENGTH_SHORT).show();
                            pdia.dismiss();
                        }
                    }
                });
            }
        });


        dlgReset.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dlgReset.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
