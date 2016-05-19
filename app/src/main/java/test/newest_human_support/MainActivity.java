package test.newest_human_support;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //don't think that I will need this
    Button myButton;
    EditText usernameField;
    EditText passwordField;
    //TextView textViewUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the view layout to activity_main.xml
        setContentView(R.layout.activity_main);

        //grabbing the values from the user and assigning to a variable
        usernameField = (EditText) findViewById(R.id.tf_subject);
        passwordField = (EditText) findViewById(R.id.tf_issue);
        myButton = (Button) findViewById(R.id.btn_send);
        //textViewUserName= (TextView)findViewById(R.id.tv_username);


        //on event listener to myButton aka the submit button
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //username: slong01 password: test123
                //username: zgordon01 password:123test
                //username: bretkillz password:test123test
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                if(password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please try to login again!", Toast.LENGTH_SHORT).show();
                }
                else {


                    String url = "http://198.211.99.235:8080/login/v1";

                    OkHttpHandler handler = new OkHttpHandler(username, password);
                    String result = null;
                    try {
                        result = handler.execute(url).get();

                        //putting the results to a json object
                        JSONObject obj = new JSONObject(result);

                       String sesskey = obj.optString("sesskey");
                       String valid_login = obj.optString("valid");
                       String user_type = obj.optString ("usertype");


                        //textViewUserName.setText(valid_login);

                        //textViewUserName.setText(obj.get("usertype") + "\n" +obj.get("sesskey"));
                        if(user_type.equals("2")||user_type.equals("1")){
                           Toast.makeText(MainActivity.this, "Please try to login again with a different account!", Toast.LENGTH_LONG).show();
                            Intent intent=  new Intent(MainActivity.this, MainActivity.class);
                            }

                        if (valid_login.equals("yes")) {
                            Intent intent = new Intent(MainActivity.this, InputFromUser.class);
                            intent.putExtra("sesskey", sesskey);
                            startActivity(intent);


                            //  Toast.makeText(MainActivity.this, "Please try to login again!", Toast.LENGTH_SHORT).show();

                        } else {
                              obj = null;
                              usernameField.setText("");
                              passwordField.setText("");
                            Toast.makeText(MainActivity.this, "Please try to login again!", Toast.LENGTH_SHORT).show();
                          }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    } catch (org.json.JSONException e) {
                        Toast.makeText(MainActivity.this, "Please try to login again!", Toast.LENGTH_SHORT).show();

                    } catch (android.content.ActivityNotFoundException e) {
                    }


                }

            }
        });


    }

}

