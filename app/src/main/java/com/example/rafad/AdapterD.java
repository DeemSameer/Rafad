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
    static String itemN;




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
                        .setMessage("هل أنت متأكد من رفض الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance()
                                        ;
                                final String itemID=arrayList.get(position).itemID;
                                 bemail=arrayList.get(position).Bemail;
                                 itemN=arrayList.get(position).tit;
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
                        }).setNegativeButton("إلغاء", null).show();
                AlertDialog dialog1;    }
        });
        Button Approve=rowView.findViewById(R.id.button);
        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("قبول الطلب")
                        .setMessage("هل أنت متأكد من قبول الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                final String itemID=arrayList.get(position).itemID;
                                bemail=arrayList.get(position).Bemail;
                                itemN=arrayList.get(position).tit;

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
                                                sendMail.sendMail(benMail, "لقد تم قبول طلبك!", "<html>\n" +
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
                                                        "  <h1>مرحبًا\n" +benName+
                                                        "  </h1>\n" +
                                                        "  <br>\n" +
                                                        "\n" +
                                                        "    <h4>يسعدنا اخبارك بقبول طلبكم لـ \n" +ItemName+
                                                        "</h4>\n" +
                                                        "<h6>نرجو الدخول للتطبيق للتواصل واستلام المنتج</h6>\n" +
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
                        }).setNegativeButton("إلغاء", null).show();
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
        titText2.setText(" حالة المستفيد من 5 : "+arrayList.get(position).BS);
        titText3.setText("عنوان الطلب : "+arrayList.get(position).tit);


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
                                + "\"contents\": {\"en\": \"تم رفض طلبك\""+itemN+"\"}"
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
                                + "\"contents\": {\"en\": \"تم قبول طلبك\""+itemN+"\"}"
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