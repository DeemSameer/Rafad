package com.example.rafad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.onesignal.OneSignal;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class signupBen extends AppCompatActivity {
    public static final String TAG = "TAG";

    EditText signUpEmail,userName,SignUpPassword1,SignUpPassword2,signUpPhone,signUpssn,signUpTotalincome,number,location;
    Button signup_button;
    RadioButton radioButton0,radioButton1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView textView1, type1;
    String sEmail,sPassword;

    String[]CountryList={"المدينة المنورة","مكة المكرمة","الرياض","القصيم","الشرقية","عسير","تبوك","الجوف","الباحة","نجران","جازان","الحدود الشمالية","حائل"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ben);
        final String adminEmail = "nadafjj@gmail.com";

        signUpEmail = findViewById(R.id.signUpEmail);
        SignUpPassword1 = findViewById(R.id.SignUpPassword1);
        SignUpPassword2 = findViewById(R.id.SignUpPassword2);
        signUpPhone = findViewById(R.id.signUpPhone);
        signup_button = findViewById(R.id.signup_button);
        userName= findViewById(R.id.userName);
        signUpssn= findViewById(R.id.signUpsn);
        signUpTotalincome= findViewById(R.id.signUpTotalincome);
        radioButton0=findViewById(R.id.radioButton3);
        radioButton1=findViewById(R.id.radioButton4);

        number=findViewById(R.id.signUpssn);
        type1 = findViewById(R.id.type);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, CountryList);
        final MaterialBetterSpinner betterSpinner=(MaterialBetterSpinner) findViewById(R.id.loc);
        betterSpinner.setAdapter(arrayAdapter);
// Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
//        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
      //  OneSignal.sendTag("User_ID",LoggedIn_User_Email);

        Button button4 = (Button) findViewById(R.id.but2);


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupBen.this, signUpBase.class));
                finish();
            }
        });



        //have account?
        textView1=findViewById(R.id.textView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupBen.this , login.class));
                finish();
            }
        });


        signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                final String email = signUpEmail.getText().toString().trim();
                final String Password = SignUpPassword1.getText().toString();
                final String Password2 = SignUpPassword2.getText().toString();
                final String Phone = signUpPhone.getText().toString();
                final String userName2= userName.getText().toString();
                final String location=betterSpinner.getText().toString();
                final String type= "beneficiary";
                final String signUpssn2= signUpssn.getText().toString();
                final String signUpTotalincome2=signUpTotalincome.getText().toString();
                final String flag = "Admin";
                String rB0= radioButton0.getText().toString();
                String rB1= radioButton1.getText().toString();
                String TOR;
                final String Number=number.getText().toString();//الضمان الاجتماعي



                if (!(radioButton0.isChecked()|| radioButton1.isChecked())){
                    type1.setError(" نوع السكن مطلوب ");
                    return;
                }

                if (radioButton0.isChecked())
                    TOR="ملك";
                else
                    TOR="ايجار";

                final String typeOfResidence=TOR;





                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError(" البريد الإلكتروني مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    SignUpPassword1.setError(" كلمة المرور مطلوبة ");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    userName.setError("اسم المستخدم مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    userName.setError("اسم المستخدم مطلوب");
                    return;
                }

                if(TextUtils.isEmpty(Phone)){
                    signUpPhone.setError(" رقم الجوال مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(signUpTotalincome2)){
                    signUpTotalincome.setError(" اجمالي الدخل مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(signUpssn2)){
                    signUpssn.setError("الهوية الوطنية مطلوبة");
                    return;
                }
                if (Password.length()<=7){
                    SignUpPassword1.setError(" كلمة المرور يجب أن تحتوي على 8 رموز ");
                    return;
                }
                if (!Password.equals(Password2)){
                    SignUpPassword2.setError(" كلمة المرور غير متطابقة مع تأكيد كلمة المرور ");
                    return;
                }
                if (Phone.length()!=10){
                    signUpPhone.setError("يجب أن يتكون رقم الجوال من 10 أرقام");
                    return;
                }
                if (!Phone.substring(0,2).equals("05")){
                    signUpPhone.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }
                if (signUpssn2.length()!=10){
                    signUpssn.setError(" رقم الهوية الوطنية غير صالح ");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull final Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signupBen.this, " تم إنشاء الحساب ", Toast.LENGTH_LONG).show();
                                        sendNot1();

                                        userID = fAuth.getCurrentUser().getUid();
                                        final DocumentReference documentrefReference = fStore.collection("beneficiaries").document(userID);
                                        //store data
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("phoneNumber", Phone);
                                        user.put("userName", userName2);
                                        user.put("type", type);
                                        user.put("email", email);
                                        user.put("SSN", signUpssn2);
                                        user.put("TotalIncome", signUpTotalincome2);
                                        user.put("flag", flag);
                                        user.put("typeOfResidence", typeOfResidence);
                                        user.put("securityNumber", Number);///check ittt -----
                                        user.put("location",location);




                                        //check the add if it's success or not
                                        documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, " تم إنشاء الحساب بنجاح " + userID);
                                                sendNot1();

                                                /////////////////////////////////////////////////////////////////

                                                documentrefReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String email = documentSnapshot.getString("email");
                                                        String name = documentSnapshot.getString("userName");

                                                        sendMail.sendMail(email, "أهلًا بك ! " , "<html>\n" +
                                                                "<head>\n" +
                                                                "  <style>\n" +
                                                                "html {\n" +
                                                                "  height: 100%;\n" +
                                                                "}\n" +
                                                                "body {\n" +
                                                                "  position: relative;\n" +
                                                                "  height: 100%;\n" +
                                                                "  margin: 0;\n" +
                                                                " box-shadow: inset 33.33vw 0px 0px #523B60, inset 66.66vw 0px 0px white, inset 99.99vw 0px 0px  #EF8D6E;\n" +
                                                                "\n" +
                                                                "\n" +
                                                                "\n" +
                                                                "  color: #523B60;\n" +
                                                                "\n" +
                                                                "\n" +
                                                                "}\n" +
                                                                "\n" +
                                                                ".inner{\n" +
                                                                "position: relative;\n" +
                                                                "  height: 100%;\n" +
                                                                "  margin: 0;\n" +
                                                                "\t\tbackground: -webkit-linear-gradient(left, #523B60, #EF8D6E 80%);\n" +
                                                                "}\n" +
                                                                "\n" +
                                                                ".center {\n" +
                                                                "    text-align: center;\n" +
                                                                "\n" +
                                                                "\twidth: 50%;\n" +
                                                                "  height: 50%;\n" +
                                                                "  overflow: auto;\n" +
                                                                "  margin: auto;\n" +
                                                                "  position: absolute;\n" +
                                                                "  top: 0; left: 0; bottom: 0; right: 0;\n" +
                                                                "  background:  white;\n" +
                                                                "  opacity:0.4;\n" +
                                                                "      border-radius:10px;\n" +
                                                                "\t  padding:10px;\n" +
                                                                "\n" +
                                                                "\n" +
                                                                "}\n" +
                                                                "\n" +
                                                                "  </style>\n" +
                                                                "</head>\n" +
                                                                "<body>\n" +

                                                                "<div class=\"inner\">\n" +
                                                                "<br>\n" +
                                                                "<br>\n" +
                                                                "<br>\n" +
                                                                "<div class=\"center\">\n" +
                                                                "<br><br>\n" +
                                                                "  <h1>مرحبًا بك عزيزنا المستفيد " +name+
                                                                "</h1>\n" +
                                                                "  <br>\n" +
                                                                "\n" +
                                                                "    <h4>يسعد تطبيق رَفَد بانضمامك لعائلته\n" +
                                                                "\n" +
                                                                "ولكن يجب عليك الإنتظار حتى يقيم المشرف بياناتك المُدخلة ومن ثم سيتم إبلاغك في حالة القبول أو الرفض \n" +
                                                                "</h4>\n" +
                                                                "  <br>\n" +
                                                                "\n" +
                                                                "  <p>يرحب تطبيق رفد بك دوماً، دمت بود </p>\n" +"<br>"+
                                                                "<p>Email: rafad.app@gmail.com</p>"+
                                                                "\n" +
                                                                "</div>\n" +
                                                                "<br>\n" +
                                                                "<br>\n" +
                                                                "<br>\n" +
                                                                "</div>\n" +

                                                                "\n" +
                                                                "</body>\n" +
                                                                "</html>");
                                                    }
                                                });



                                                //////////////////////////////////////////////////////////////////










                                                //////////////////////////////////////////////////////
                                                sendMail.sendMail("deemsameer.ds@gmail.com", "لقد انضم مستفيد جديد !" , "<html>\n" +
                                                        "<head>\n" +
                                                        "  <style>\n" +
                                                        "html {\n" +
                                                        "  height: 100%;\n" +
                                                        "}\n" +
                                                        "body {\n" +
                                                        "  position: relative;\n" +
                                                        "  height: 100%;\n" +
                                                        "  margin: 0;\n" +
                                                        " box-shadow: inset 33.33vw 0px 0px #523B60, inset 66.66vw 0px 0px white, inset 99.99vw 0px 0px  #EF8D6E;\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "  color: #523B60;\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "}\n" +
                                                        "\n" +
                                                        ".inner{\n" +
                                                        "position: relative;\n" +
                                                        "  height: 100%;\n" +
                                                        "  margin: 0;\n" +
                                                        "\t\tbackground: -webkit-linear-gradient(left, #523B60, #EF8D6E 80%);\n" +
                                                        "}\n" +
                                                        "\n" +
                                                        ".center {\n" +
                                                        "    text-align: center;\n" +
                                                        "\n" +
                                                        "\twidth: 50%;\n" +
                                                        "  height: 50%;\n" +
                                                        "  overflow: auto;\n" +
                                                        "  margin: auto;\n" +
                                                        "  position: absolute;\n" +
                                                        "  top: 0; left: 0; bottom: 0; right: 0;\n" +
                                                        "  background:  white;\n" +
                                                        "  opacity:0.4;\n" +
                                                        "      border-radius:10px;\n" +
                                                        "\t  padding:10px;\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "}\n" +
                                                        "\n" +
                                                        "  </style>\n" +
                                                        "</head>\n" +
                                                        "<body>\n" +
                                                        "\n" +
                                                        "<div class=\"inner\">\n" +
                                                        "<hr>\n" +
                                                        "<br>\n" +
                                                        "<br>\n" +
                                                        "<div class=\"center\">\n" +
                                                        "<br><br>\n" +
                                                        "  <h1>مرحبًا بك ديم !\n" +
                                                        "  </h1>\n" +
                                                        "  <br>\n" +
                                                        "\n" +
                                                        "    <h4>يسعدنا اخبارك بأنه هناك مستفيد جديد بحاجة لتفعيل حسابه\n" +
                                                        "</h4>\n" +
                                                        "<h6>نرجو الإطلاع عليه بأقرب فرصة</h6>\n" +
                                                        "  <br>\n" +
                                                        "\n" +
                                                        "  <p>دمت بود </p>\n" +
                                                        "<br>\n" +
                                                        "<p>Email: rafad.app@gmail.com</p>"+

                                                        "\n" +
                                                        "</div>\n" +
                                                        "<br>\n" +
                                                        "<br>\n" +
                                                        "<br>\n" +
                                                        "</div>\n" +
                                                        "\n" +
                                                        "\n" +
                                                        "</body>\n" +
                                                        "</html>"
                                                       );

                                                ////////////////////////////////////////////////////////
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(),login.class) );
                                        finish();

                                    }//end if
                                    else {
                                        Toast.makeText(signupBen.this, " حصل خطأ ما ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }//end else
                                }//oncomplete
                            });

                        }//end if1
                        else{
                            if (task.getException().getMessage().equals("The email address is already in use by another account."))
                                Toast.makeText(signupBen.this, " الايميل موجود لدينا يرجى تسجيل دخول " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The email address is badly formatted."))
                                Toast.makeText(signupBen.this, " يرجى كتابة الايميل بشكل صحيح " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]"))
                                Toast.makeText(signupBen.this, " تم حجب تسجيل جديد للمستخدم لتجاوز الحد المسموح من المحاولات عاود التسجيل بعد فترة  " , Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(signupBen.this, " حصل خطأ ما ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }//end else
                    }//end on complete
                });
            }// end method on click

        });

    }
    private void sendNot1()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    OneSignal.sendTag("User_ID",LoggedIn_User_Email);
                    send_email="deemsameer.ds@gmail.com";
                    /*
                    if (MainActivity.LoggedIn_User_Email.equals("user1@gmail.com")) {
                        send_email = "user2@gmail.com";
                    } else {
                        send_email = "user1@gmail.com";
                    }*/
                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NTVjOWIxNmYtYjI0YS00NjU0LWE1YmEtYjM5YTM2OWQxZjIx");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"c12cdbbf-7bd8-4c4f-b5ba-df37e6cc2d36\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"تم تسجيل مستفيد جديد!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        ((OutputStream) outputStream).write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}
