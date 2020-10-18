package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

                                        userID = fAuth.getCurrentUser().getUid();
                                        final DocumentReference documentrefReference = fStore.collection("beneficiaries").document(userID);
                                        //store data
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("phoneNumber", Phone);
                                        user.put("userName", userName2);
                                        //user.put("type", type);
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
                                                /////////////////////////////////////////////////////////////////

                                                documentrefReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String email = documentSnapshot.getString("email");
                                                        sendMail.sendMail(email, "أهلًا بك ! " , "   <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                                                "\n" +
                                                                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                                                                "<head>\n" +
                                                                "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                                                                "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                                                                "<meta content=\"width=device-width\" name=\"viewport\"/>\n" +
                                                                "<!--[if !mso]><!-->\n" +
                                                                "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<title></title>\n" +
                                                                "<!--[if !mso]><!-->\n" +
                                                                "<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<style type=\"text/css\">\n" +
                                                                "\t\tbody {\n" +
                                                                "\t\t\tmargin: 0;\n" +
                                                                "\t\t\tpadding: 0;\n" +
                                                                "\t\t}\n" +
                                                                "\n" +
                                                                "\t\ttable,\n" +
                                                                "\t\ttd,\n" +
                                                                "\t\ttr {\n" +
                                                                "\t\t\tvertical-align: top;\n" +
                                                                "\t\t\tborder-collapse: collapse;\n" +
                                                                "\t\t}\n" +
                                                                "\n" +
                                                                "\t\t* {\n" +
                                                                "\t\t\tline-height: inherit;\n" +
                                                                "\t\t}\n" +
                                                                "\n" +
                                                                "\t\ta[x-apple-data-detectors=true] {\n" +
                                                                "\t\t\tcolor: inherit !important;\n" +
                                                                "\t\t\ttext-decoration: none !important;\n" +
                                                                "\t\t}\n" +
                                                                "\t</style>\n" +
                                                                "<style id=\"media-query\" type=\"text/css\">\n" +
                                                                "\t\t@media (max-width: 620px) {\n" +
                                                                "\n" +
                                                                "\t\t\t.block-grid,\n" +
                                                                "\t\t\t.col {\n" +
                                                                "\t\t\t\tmin-width: 320px !important;\n" +
                                                                "\t\t\t\tmax-width: 100% !important;\n" +
                                                                "\t\t\t\tdisplay: block !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.block-grid {\n" +
                                                                "\t\t\t\twidth: 100% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.col {\n" +
                                                                "\t\t\t\twidth: 100% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.col>div {\n" +
                                                                "\t\t\t\tmargin: 0 auto;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\timg.fullwidth,\n" +
                                                                "\t\t\timg.fullwidthOnMobile {\n" +
                                                                "\t\t\t\tmax-width: 100% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col {\n" +
                                                                "\t\t\t\tmin-width: 0 !important;\n" +
                                                                "\t\t\t\tdisplay: table-cell !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack.two-up .col {\n" +
                                                                "\t\t\t\twidth: 50% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num2 {\n" +
                                                                "\t\t\t\twidth: 16.6% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num3 {\n" +
                                                                "\t\t\t\twidth: 25% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num4 {\n" +
                                                                "\t\t\t\twidth: 33% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num5 {\n" +
                                                                "\t\t\t\twidth: 41.6% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num6 {\n" +
                                                                "\t\t\t\twidth: 50% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num7 {\n" +
                                                                "\t\t\t\twidth: 58.3% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num8 {\n" +
                                                                "\t\t\t\twidth: 66.6% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num9 {\n" +
                                                                "\t\t\t\twidth: 75% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.no-stack .col.num10 {\n" +
                                                                "\t\t\t\twidth: 83.3% !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.video-block {\n" +
                                                                "\t\t\t\tmax-width: none !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.mobile_hide {\n" +
                                                                "\t\t\t\tmin-height: 0px;\n" +
                                                                "\t\t\t\tmax-height: 0px;\n" +
                                                                "\t\t\t\tmax-width: 0px;\n" +
                                                                "\t\t\t\tdisplay: none;\n" +
                                                                "\t\t\t\toverflow: hidden;\n" +
                                                                "\t\t\t\tfont-size: 0px;\n" +
                                                                "\t\t\t}\n" +
                                                                "\n" +
                                                                "\t\t\t.desktop_hide {\n" +
                                                                "\t\t\t\tdisplay: block !important;\n" +
                                                                "\t\t\t\tmax-height: none !important;\n" +
                                                                "\t\t\t}\n" +
                                                                "\t\t}\n" +
                                                                "\t</style>\n" +
                                                                "</head>\n" +
                                                                "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #397a96;\">\n" +
                                                                "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                                                                "<table bgcolor=\"#397a96\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #397a96; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                                "<tbody>\n" +
                                                                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                                "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n" +
                                                                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#397a96\"><![endif]-->\n" +
                                                                "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                                "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: transparent;\">\n" +
                                                                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                                                                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;\"><![endif]-->\n" +
                                                                "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                                "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: #FFFFFF;\">\n" +
                                                                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                                                                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                                                                "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                                "<div style=\"color:#5f9fbe;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                                                                "<div style=\"font-size: 12px; line-height: 1.2; color: #5f9fbe; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 26px;\"><strong><span style=\"font-size: 26px;\"> مرحبًا عزيزنا المستفيد </span></strong></span></p>\n" +
                                                                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">يسعد تطبيق رَفَد بانضمامك لعائلته   </span></span></p>\n" +
                                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"> ولكن يجب عليك الإنتظار حتى يقيم المشرف بياناتك المُدخلة ومن ثم سيتم إبلاغك في حالة القبول أو الرفض </span></span></p>\n" +
                                                                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\"></span></p>\n" +"</p>\n"+"</p>\n"+
                                                                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\">يرحب تطبيق رفد بك دوماً، دمت بود</span></p>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                                "<tbody>\n" +
                                                                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                                "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n" +
                                                                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 0px solid transparent; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                                "<tbody>\n" +
                                                                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                                "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" +
                                                                "</tr>\n" +
                                                                "</tbody>\n" +
                                                                "</table>\n" +
                                                                "</td>\n" +
                                                                "</tr>\n" +
                                                                "</tbody>\n" +
                                                                "</table>\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                                "<div class=\"block-grid three-up\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: #888888;\">\n" +
                                                                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#888888;\">\n" +
                                                                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#888888\"><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\n" +
                                                                "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                                "<div style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                                                                "<div style=\"font-size: 12px; line-height: 1.2; color: #a8bf6f; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                                "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\"><span style=\"color: #ffffff; font-size: 12px;\"><span style=\"font-size: 12px; color: #397A96;\">Phone.:</span> +966 530381254</span></p>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                                                                "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                                                                "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                                "<div style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                                                                "<div style=\"font-size: 12px; line-height: 1.2; color: #397A96; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                                "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\">Email <span style=\"color: #ffffff; font-size: 12px;\">rafad.app@gmail.com</span></p>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                                "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: transparent;\">\n" +
                                                                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                                                                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                                                                "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                                "<div style=\"width:100% !important;\">\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                                "<tbody>\n" +
                                                                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                                "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 30px; padding-bottom: 30px; padding-left: 30px;\" valign=\"top\">\n" +
                                                                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 0px solid transparent; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                                "<tbody>\n" +
                                                                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                                "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" +
                                                                "</tr>\n" +
                                                                "</tbody>\n" +
                                                                "</table>\n" +
                                                                "</td>\n" +
                                                                "</tr>\n" +
                                                                "</tbody>\n" +
                                                                "</table>\n" +
                                                                "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                                "</div>\n" +
                                                                "<!--<![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "</div>\n" +
                                                                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                                "</td>\n" +
                                                                "</tr>\n" +
                                                                "</tbody>\n" +
                                                                "</table>\n" +
                                                                "<!--[if (IE)]></div><![endif]-->\n" +
                                                                "</body>\n" +
                                                                "</html>  ");
                                                    }
                                                });



                                                //////////////////////////////////////////////////////////////////










                                                //////////////////////////////////////////////////////
                                                sendMail.sendMail("nadafjj@gmail.com", "لقد انضم مستفيد جديد !" , "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                                        "\n" +
                                                        "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                                                        "<head>\n" +
                                                        "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                                                        "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                                                        "<meta content=\"width=device-width\" name=\"viewport\"/>\n" +
                                                        "<!--[if !mso]><!-->\n" +
                                                        "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<title></title>\n" +
                                                        "<!--[if !mso]><!-->\n" +
                                                        "<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<style type=\"text/css\">\n" +
                                                        "\t\tbody {\n" +
                                                        "\t\t\tmargin: 0;\n" +
                                                        "\t\t\tpadding: 0;\n" +
                                                        "\t\t}\n" +
                                                        "\n" +
                                                        "\t\ttable,\n" +
                                                        "\t\ttd,\n" +
                                                        "\t\ttr {\n" +
                                                        "\t\t\tvertical-align: top;\n" +
                                                        "\t\t\tborder-collapse: collapse;\n" +
                                                        "\t\t}\n" +
                                                        "\n" +
                                                        "\t\t* {\n" +
                                                        "\t\t\tline-height: inherit;\n" +
                                                        "\t\t}\n" +
                                                        "\n" +
                                                        "\t\ta[x-apple-data-detectors=true] {\n" +
                                                        "\t\t\tcolor: inherit !important;\n" +
                                                        "\t\t\ttext-decoration: none !important;\n" +
                                                        "\t\t}\n" +
                                                        "\t</style>\n" +
                                                        "<style id=\"media-query\" type=\"text/css\">\n" +
                                                        "\t\t@media (max-width: 620px) {\n" +
                                                        "\n" +
                                                        "\t\t\t.block-grid,\n" +
                                                        "\t\t\t.col {\n" +
                                                        "\t\t\t\tmin-width: 320px !important;\n" +
                                                        "\t\t\t\tmax-width: 100% !important;\n" +
                                                        "\t\t\t\tdisplay: block !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.block-grid {\n" +
                                                        "\t\t\t\twidth: 100% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.col {\n" +
                                                        "\t\t\t\twidth: 100% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.col>div {\n" +
                                                        "\t\t\t\tmargin: 0 auto;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\timg.fullwidth,\n" +
                                                        "\t\t\timg.fullwidthOnMobile {\n" +
                                                        "\t\t\t\tmax-width: 100% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col {\n" +
                                                        "\t\t\t\tmin-width: 0 !important;\n" +
                                                        "\t\t\t\tdisplay: table-cell !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack.two-up .col {\n" +
                                                        "\t\t\t\twidth: 50% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num2 {\n" +
                                                        "\t\t\t\twidth: 16.6% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num3 {\n" +
                                                        "\t\t\t\twidth: 25% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num4 {\n" +
                                                        "\t\t\t\twidth: 33% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num5 {\n" +
                                                        "\t\t\t\twidth: 41.6% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num6 {\n" +
                                                        "\t\t\t\twidth: 50% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num7 {\n" +
                                                        "\t\t\t\twidth: 58.3% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num8 {\n" +
                                                        "\t\t\t\twidth: 66.6% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num9 {\n" +
                                                        "\t\t\t\twidth: 75% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.no-stack .col.num10 {\n" +
                                                        "\t\t\t\twidth: 83.3% !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.video-block {\n" +
                                                        "\t\t\t\tmax-width: none !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.mobile_hide {\n" +
                                                        "\t\t\t\tmin-height: 0px;\n" +
                                                        "\t\t\t\tmax-height: 0px;\n" +
                                                        "\t\t\t\tmax-width: 0px;\n" +
                                                        "\t\t\t\tdisplay: none;\n" +
                                                        "\t\t\t\toverflow: hidden;\n" +
                                                        "\t\t\t\tfont-size: 0px;\n" +
                                                        "\t\t\t}\n" +
                                                        "\n" +
                                                        "\t\t\t.desktop_hide {\n" +
                                                        "\t\t\t\tdisplay: block !important;\n" +
                                                        "\t\t\t\tmax-height: none !important;\n" +
                                                        "\t\t\t}\n" +
                                                        "\t\t}\n" +
                                                        "\t</style>\n" +
                                                        "</head>\n" +
                                                        "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #397a96;\">\n" +
                                                        "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                                                        "<table bgcolor=\"#397a96\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #397a96; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                        "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n" +
                                                        "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#397a96\"><![endif]-->\n" +
                                                        "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                        "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: transparent;\">\n" +
                                                        "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                                                        "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;\"><![endif]-->\n" +
                                                        "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                        "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: #FFFFFF;\">\n" +
                                                        "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                                                        "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                                                        "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                        "<div style=\"color:#5f9fbe;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                                                        "<div style=\"font-size: 12px; line-height: 1.2; color: #5f9fbe; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                        "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                        "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 26px;\"><strong><span style=\"font-size: 26px;\">مرحبًا بك ديم !</span></strong></span></p>\n" +
                                                        "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                        "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"> يسعدنا اخبارك بأنه هناك مستفيد جديد بحاجة لتفعيل حسابه </span></span></p>\n" +
                                                        "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +"</p>\n"+
                                                        "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 22px;\">نرجو الإطلاع عليه بأقرب فرصة، دمت بود </span></p>\n" +
                                                        "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 22px;\"></span></p>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                        "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n" +
                                                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 0px solid transparent; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                        "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                        "<div class=\"block-grid three-up\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: #888888;\">\n" +
                                                        "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#888888;\">\n" +
                                                        "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#888888\"><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\n" +
                                                        "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                        "<div style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                                                        "<div style=\"font-size: 12px; line-height: 1.2; color: #a8bf6f; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                        "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\"><span style=\"color: #ffffff; font-size: 12px;\"><span style=\"font-size: 12px; color: #397A96;\">Phone.:</span> +966 530381254</span></p>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                                                        "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#888888;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                                                        "<div class=\"col num4\" style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                                                        "<div style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                                                        "<div style=\"font-size: 12px; line-height: 1.2; color: #397A96; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                                                        "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\">Email <span style=\"color: #ffffff; font-size: 12px;\">rafad.app@gmail.com</span></p>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if mso]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<div style=\"background-color:transparent;overflow:hidden\">\n" +
                                                        "<div class=\"block-grid\" style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; width: 100%; background-color: transparent;\">\n" +
                                                        "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                                                        "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                                                        "<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                                                        "<div style=\"width:100% !important;\">\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                        "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 30px; padding-right: 30px; padding-bottom: 30px; padding-left: 30px;\" valign=\"top\">\n" +
                                                        "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 0px solid transparent; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                                                        "<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<!--[if (!mso)&(!IE)]><!-->\n" +
                                                        "</div>\n" +
                                                        "<!--<![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "</div>\n" +
                                                        "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                                                        "</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<!--[if (IE)]></div><![endif]-->\n" +
                                                        "</body>\n" +
                                                        "</html>");

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

}
