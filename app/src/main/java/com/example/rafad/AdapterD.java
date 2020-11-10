package com.example.rafad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class AdapterD extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private List<postinfo> arrayList=new ArrayList<>();
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Button request;
    String UID1;
    static String bemail;



    public AdapterD(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_adapter_d, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);
    }




    public android.view.View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_adapter_d, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();


        TextView titText = (TextView) rowView.findViewById(R.id.name2);
        TextView titText2 = (TextView) rowView.findViewById(R.id.status);
        TextView titText3 = (TextView) rowView.findViewById(R.id.name2);

        final ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);
// Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);
        Button DisApprove=rowView.findViewById(R.id.button7);
        DisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("رفض الطلب")
                        .setMessage("هل انت متأكد من رفض الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance()
                                        ;
                                final String itemID=arrayList.get(position).itemID;
                                 bemail=arrayList.get(position).Bemail;
                                UID1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                CollectionReference beneficiaries = db.collection("item");
                                DocumentReference docRefB = beneficiaries.document(itemID);
                                docRefB.update("isRequested", "no");
                                docRefB.update("benN", "name");
                                docRefB.update("benS", "state");
                                context.startActivity(new Intent(context, don_3view.class));
                                Toast.makeText(getContext(), "لقد تم رفض الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                sendNot1();

                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;    }
        });
        Button Approve=rowView.findViewById(R.id.button);
        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("قبول الطلب")
                        .setMessage("هل انت متأكد من قبول الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                final String itemID=arrayList.get(position).itemID;
                                bemail=arrayList.get(position).Bemail;

                                Log.d(TAG, "benE data after request: " + bemail);

                                UID1=FirebaseAuth.getInstance().getCurrentUser().getUid();

                                CollectionReference beneficiaries = db.collection("item");
                                DocumentReference docRefB = beneficiaries.document(itemID);
                                docRefB.update("isRequested", "yes");
                                context.startActivity(new Intent(context, don_3view.class));
                                Toast.makeText(getContext(), "لقد تم قبول الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                sendNot2();


                                //SEND EMAIL TO THE BENFICARY

                                DocumentReference docRef=fStore.collection("item").document(itemID);//.get("User id")
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                final String benID= (String)document.get("benID");
                                                final String ItemName=(String)document.get("Title");//name

                                                DocumentReference docRef2=fStore.collection("beneficiaries").document(benID);//.get("User id")
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: Ben - - " + document.getData());
                                                String benMail= (String)document.get("email");
                                                final String benName= (String)document.get("userName");
                                                sendMail.sendMail(benMail, "لقد تم قبول طلبك!", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
                                                        "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong><span style=\"font-size: 28px;\">" +"مرحبًا "+benName+
                                                        "</span></strong></span></p>\n" +
                                                        "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                        "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><span style=\"font-size: 28px;\"> " +"يسعدنا اخبارك بقبول طلبكم لـ("+ItemName+")"+
                                                        "</span></span></p>\n" +
                                                        "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                        "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 22px;\">نرجو الدخول للتطبيق للتواصل واستلام المنتج</span></p>\n" +
                                                        "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 22px;\">دمت بود</span></p>\n" +
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
                                                        "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\"><span style=\"color: #ffffff; font-size: 12px;\"><span style=\"font-size: 12px; color: #a8bf6f;\">Phone.:</span> +966 505050505</span></p>\n" +
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
                                                        "<div style=\"font-size: 12px; line-height: 1.2; color: #a8bf6f; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
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


                                                //************************************************************************************//
                                                //Start adding them to each other chat
                                                //**Adding the benficary to the donator chat**/
                                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference ref = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()+"/People/"+benID);
                                                final DatabaseReference usersRef =ref.child("Messages");
                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.getValue()==null){
                                                            Map<String, String> People = new HashMap<>();
                                                            People.put("state", "accepted");
                                                            People.put("unread", "0");
                                                            usersRef.setValue(People);
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        Map<String, String> People = new HashMap<>();
                                                        People.put("state", "accepted");
                                                        People.put("unread", "0");
                                                        usersRef.setValue(People);
                                                    }
                                                    });




                                                //**End**//

                                                //**Adding the donator to the benficary chat**/
                                                final FirebaseDatabase database0 = FirebaseDatabase.getInstance();
                                                DatabaseReference ref0 = database0.getReference(benID +"/People/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                final DatabaseReference usersRef0 = ref0.child("Messages");
                                                ref0.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.getValue()==null){
                                                            Map<String, String> People = new HashMap<>();
                                                            People.put("state", "accepted");
                                                            People.put("unread", "0");
                                                            usersRef0.setValue(People);
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        Map<String, String> People = new HashMap<>();
                                                        People.put("state", "accepted");
                                                        People.put("unread", "0");
                                                        usersRef0.setValue(People);
                                                    }
                                                });

                                                //**End**//

                                                //End adding them to each other chat
                                                //************************************************************************************//


                                            }
                                        }
                                    }
                                });
                                ///end get mail

                            }
                        }
            }
        });
                                //END SEND MAIL

                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;    }
        });




        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "inter Adaptor D");

            }
        });




        titText.setText("اسم المستفيد: "+arrayList.get(position).BN);
        titText2.setText("درجة حالة المستفيد من 5: "+arrayList.get(position).BS);
        titText3.setText("عنوان الطلب: "+arrayList.get(position).tit);


        return rowView;

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
                    send_email=bemail;
                    //send_email="may.a.alfahad@gmail.com";
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
                                + "\"contents\": {\"en\": \"عذرا تم رفض طلبك!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

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
    private void sendNot2()
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
                    send_email=bemail;
                    //send_email="may.a.alfahad@gmail.com";
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
                                + "\"contents\": {\"en\": \"تم قبول طلبك!\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

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