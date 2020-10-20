package com.example.rafad;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.DialogInterface;

class MyListAdapter extends ArrayAdapter<benDataModel> {
    public static final String TAG = "TAG";
    private final Activity context;
    private final List<benDataModel> arrayList;
    FirebaseAuth fAuth;
    boolean check=false;
    public MyListAdapter(@NonNull Activity context, @NonNull List<benDataModel> arrayList) {
        super(context, R.layout.mylist, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

      Button button=rowView.findViewById(R.id.button);

        final String UID=arrayList.get(position).getUID();
        //First accept
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "HIIIIIIIIIII ");
                final popUpClass popupclass = new popUpClass();
                popupclass.showPopupWindow(view);
                ///Approve///
                popupclass.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ///////////////////
                        if (popupclass.radioButton1.isChecked()|| popupclass.radioButton2.isChecked()||popupclass.radioButton3.isChecked()|| popupclass.radioButton4.isChecked()|| popupclass.radioButton5.isChecked()) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference beneficiaries = db.collection("beneficiaries");
                            DocumentReference docRefB = beneficiaries.document(UID);
                            docRefB.update("flag", "Accepted");
                            Map<String, Object> user = new HashMap<>();
                            String state="";
                            if (popupclass.radioButton1.isChecked())
                                state="1";
                            if (popupclass.radioButton2.isChecked())
                                state="2";
                            if (popupclass.radioButton3.isChecked())
                                state="3";
                            if (popupclass.radioButton4.isChecked())
                                state="4";
                            if (popupclass.radioButton5.isChecked())
                                state="5";

                            user.put("State",state);
                            docRefB.update(user);

                            //////////////////////////////////////////////////////

                            docRefB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String email = documentSnapshot.getString("email");
                                    String name = documentSnapshot.getString("userName");
                                    sendMail.sendMail(email, "تهانينا !  " , "  <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
                                            "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong><span style=\"font-size: 26px;\">مرحبًا عزيزنا المستفيد   </span></strong></span></p>\n" +
                                            "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><span style=\"font-size: 24px;\">تم قبول حسابك، يسعدنا أن تكون جزءًا من عائلة رَفَد</span></span><br/><br/><br/><br/>\n\n<span style=\"font-size: 22px;\">نتمنى لك تجربة ممتعة، دمت بود   </span></p>\n" +
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
                                            "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\"><span style=\"color: #ffffff; font-size: 12px;\"><span style=\"font-size: 12px; color: #a8bf6f;\">Phone.:</span> +966 530381254</span></p>\n" +
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
                                            "</html>   ");
                                }
                            });
                            ////////////////////////////////////////////////////////


                            context.startActivity(new Intent(context, homepageAdmin.class));
                            Toast.makeText(getContext(),"لقد تم قبول المستفيد بنجاح",Toast.LENGTH_SHORT).show();

                            return;
                        }else{
                            //Toast.makeText(MyListAdapter.this, " الرجاء ادخال الحالة ", Toast.LENGTH_LONG).show();
                            popupclass.settext.setText("يرجى إدخال الحالة");
                        }
                        //////////////////
                    }
                });
                ///end approve///


/*
                //Fullstar
                popupclass.FullStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","1");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar1
                popupclass.fullStar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","2");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar2
                popupclass.fullStar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","3");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar3
                popupclass.fullStar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","4");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar4
                popupclass.fullStar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","5");
                        docRefB.update(user);
                        check=true;
                    }
                });
*/
                //


            }
        });//End Big accept button
        ///Disapprove///
        Button DisApprove=rowView.findViewById(R.id.button7);
        DisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("رفض مستفيد")
                        .setMessage("هل أنت متأكد من رفض المستفيد؟")
                        .setPositiveButton("رفض", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference beneficiaries = db.collection("beneficiaries");
                                DocumentReference docRefB = beneficiaries.document(UID);

                                docRefB.update("flag", "Declined");

                                //////////////////////////////////////////////////////

                                docRefB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String email = documentSnapshot.getString("email");
                                        //(String) docRefB.get("email");
                                        String name = documentSnapshot.getString("userName");
                                        //(String) docRefB.get("userName");
                                        sendMail.sendMail(email, "تم رفض الحساب !  " ,  "  <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong><span style=\"font-size: 26px;\"> مرحبًا عزيزنا المستفيد </span></strong></span></p>\n" +
                                                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">نسعد في تطبيق رَفَد بانضمامك لنا ولكن يؤسفنا إخبارك </span></span></p>\n" +
                                                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">بأنه تم رفض حسابك لعدم استيفاء الشروط</span></span></p>\n" +
                                                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                                                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\">يمكنك إعادة التسجيل وإدخال معلومات صالحة</span></p>\n" +"\n"+
                                                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\">يوّد تطبيق رفد أن يلقاك قريبًا، دمت بود</span></p>\n" +
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
                                                "</html>  ");
                                    }
                                });
                                ////////////////////////////////////////////////////////
                                context.startActivity(new Intent(context, homepageAdmin.class));
                                Toast.makeText(getContext(), "لقد تم رفض المستفيد بنجاح", Toast.LENGTH_SHORT).show();


                                //dialog1.dismiss();

                            }
                        }).setNegativeButton("إلغاء", null).show();


                AlertDialog dialog1;


                //////////////////


            }

        });
        ///end Disapprove///


        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.phone);
        TextView subtitleText1 =  rowView.findViewById(R.id.ssn);
        TextView subtitleText2 =  rowView.findViewById(R.id.Resd);
        TextView subtitleText3 =  rowView.findViewById(R.id.income);

        String SecNum;
        if(arrayList.get(position).getSecurityNumber().isEmpty())
         SecNum= "لا يوجد";
        else
            SecNum= arrayList.get(position).getSecurityNumber();

        titleText.setText(arrayList.get(position).getUserName());
        subtitleText.setText(arrayList.get(position).getPhoneNumber());
        subtitleText1.setText("رقم الضمان الإجتماعي: "+SecNum);
        subtitleText2.setText("نوع السكن: "+arrayList.get(position).getTypeOfResidence());
        subtitleText3.setText("الدخل: "+arrayList.get(position).getTotalIncome());

        return rowView;

    };


}



