package com.example.rafad.Block;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.rafad.ChatJava.MessageActivity;
import com.example.rafad.R;
import com.example.rafad.benDataModel;
import com.example.rafad.sendMail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBenList extends ArrayAdapter<BenModelBlock> {
public static final String TAG = "TAG";
private final Activity context;
    FirebaseFirestore fStore;

private final List<BenModelBlock> arrayList;
        FirebaseAuth fAuth;
    StorageReference storageReference;

public BlockBenList(@NonNull Activity context, @NonNull List<BenModelBlock> arrayList) {
        super(context, R.layout.blockbenitem, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
        }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        View rowView = inflater.inflate(R.layout.blockbenitem, null, true);

        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.phone);
        TextView subtitleText1 =  rowView.findViewById(R.id.ssn);


        titleText.setText("الاسم: "+arrayList.get(position).getPhoneNumber());
        subtitleText.setText("رقم الجوال: "+arrayList.get(position).getName());
        subtitleText1.setText("عدد مرات الابلاغ: "+arrayList.get(position).getCount());
        final ImageView profileImageViewChat = (ImageView) rowView.findViewById(R.id.profile_image);

        try {

            StorageReference profileRef = storageReference.child("users/" + arrayList.get(position).getUID() + "profile.jpg");
            Log.d(TAG, "before12 People Adapter" + profileRef);
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImageViewChat);
                    Log.d(TAG, "interrrrrr People Adapter");


                }
            });
        }catch (Exception e){

        }

        Button acceptt= rowView.findViewById(R.id.button);
        acceptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpBlockConfirmation pop=new PopUpBlockConfirmation();
                pop.showPopupWindow(view);
                pop.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Change the status to delete
                        CollectionReference beneficiaries = fStore.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(arrayList.get(position).getUID());
                        docRefB.update("flag", "Blocked");//block
                        DocumentReference docRef= fStore.collection("Reports").document(arrayList.get(position).getUID());
                        docRef.delete();//delete so the item will not be shown again.
                        //send mail to the benficary
                        sendMail(arrayList.get(position).getPhoneNumber(),arrayList.get(position).getEmail());
                        context.startActivity(new Intent(context, blockBen.class));

                    }
                });

            }
        });

        Button declined= rowView.findViewById(R.id.button7);
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpBlockConfirmation2 pop=new PopUpBlockConfirmation2();
                pop.showPopupWindow(view);
                pop.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DocumentReference docRef= fStore.collection("Reports").document(arrayList.get(position).getUID());
                        docRef.delete();
                        context.startActivity(new Intent(context, blockBen.class));

                    }
                });

            }
        });


        return rowView;

    };

    void sendMail(String name , String email){
        sendMail.sendMail(email, "لقد تم حظر حسابك! " , "   <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 28px;\"><strong><span style=\"font-size: 28px;\">" + "مرحبًا عزيزنا المستفيد " + name +
                "</span></strong></span></p>\n" +
                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">يأسف تطبيق رفد باخبارك بحظر حسابك</span></span></p>\n" +
                "<p style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\"><span style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"> لقد تم التبليغ عن حسابك وبعد النظر في امرك تم حظر الحساب </span></span></p>\n" +
                "<p style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\"></span></p>\n" +"</p>\n"+"</p>\n"+
                "<p style=\"font-size: 22px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 26px; margin: 0;\"><span style=\"font-size: 20px;\"> دمت بود</span></p>\n" +
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
                "<p style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\"><span style=\"color: #ffffff; font-size: 12px;\"><span style=\"font-size: 12px; color: #397A96;\">Phone.:</span> +966 505050505</span></p>\n" +
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
    }

