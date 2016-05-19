package test.newest_human_support;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class InputFromUser extends AppCompatActivity {

    Button send;
    Button cancel;
    Button logout;
    EditText textFieldSubject;
    EditText textFieldIssue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_from_user);

        textFieldSubject = (EditText) findViewById(R.id.tf_subject);
        textFieldIssue = (EditText) findViewById(R.id.tf_issue);
        send = (Button) findViewById(R.id.btn_send);
        cancel = (Button) findViewById(R.id.btn_cancel);
        logout = (Button) findViewById(R.id.btn_logout);


        Intent intent = getIntent();
        final String sesskey = intent.getStringExtra("sesskey");
        //String password = intent.getStringExtra("password");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //String user_type = extras.getString("user_type");

                //grabbing the characters from the user and casting it to a string
                String issue = textFieldIssue.getText().toString();
                String subject = textFieldSubject.getText().toString();


                if(issue.isEmpty() || subject.isEmpty() ){
                    Toast.makeText(InputFromUser.this, "Please try your entries again!", Toast.LENGTH_LONG).show();
                }else {
                //post request with session key getting sent to the server
                String url_session = "http://198.211.99.235:8080/ticket/v1/" + sesskey + "/put";
                OkHttpHandler1 handler = new OkHttpHandler1(subject, issue);
                String result1 = null;
                try {
                    result1 = handler.execute(url_session).get();
                    if (result1 != null) {
                        Toast.makeText(InputFromUser.this, "Your ticket was sent!", Toast.LENGTH_LONG).show();
                        textFieldIssue.setText("");
                        textFieldSubject.setText("");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }}
        });

    cancel.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            textFieldSubject.setText("");
            textFieldIssue.setText("");
        }});
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputFromUser.this, MainActivity.class);
                startActivity(intent);
            }});

}}


