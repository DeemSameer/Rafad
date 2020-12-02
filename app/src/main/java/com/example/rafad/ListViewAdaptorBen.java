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
import java.util.List;
import java.util.Scanner;

/*
import com.example.rafad.notification.APIService;
import com.example.rafad.notification.Client;
import com.example.rafad.notification.Data;
import com.example.rafad.notification.MyResponse;
import com.example.rafad.notification.NotificationSender;
import com.example.rafad.notification.Token;
*/

public class ListViewAdaptorBen extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Button request;
    String UID1, benN, benS,benE;
    private final String Title = "لقد تم طلب سلعتك!";
    private final String Message ="تم طلب سلعتك من احد المستفيدين";
    // private APIService apiService;
    String LoggedIn_User_Email;
     static String Demail;
    static String itemN;




    public ListViewAdaptorBen(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_list_view_adaptor_ben, arrayList);
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

        final String itemID=arrayList.get(position).itemID;
        final String dID=arrayList.get(position).UID;
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_adaptor_ben, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        request=rowView.findViewById(R.id.button11);
        final String UID=arrayList.get(position).getUID();
        Demail = arrayList.get(position).Demail;
        itemN = arrayList.get(position).tit;

        Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&&dem " + arrayList.get(position).Demail);
        Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&&get " + arrayList.get(position).getDemail());
        Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&&isRe " + arrayList.get(position).isRe);

        benE=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UID1 = FirebaseAuth.getInstance().getCurrentUser().getUid();


                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setMessage("هل انت متأكد من الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final FirebaseFirestore db = FirebaseFirestore.getInstance();


                                // To retreive name and state for ben
                                ///////////////////////////////////////////////////
                                DocumentReference docRef = fStore.collection("beneficiaries").document(UID1);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data ListViewAdaptorBen: " + document.getData());
                                                benN=document.getString("userName");
                                                benS=document.getString("State");
                                                Log.d(TAG, "benN data ListViewAdaptorBen: " + benN);
                                                Log.d(TAG, "benN data after ListViewAdaptorBen: " + benN);
                                                CollectionReference beneficiaries = db.collection("item");
                                                DocumentReference docRefB = beneficiaries.document(itemID);
                                                docRefB.update("isRequested", "Pending");
                                                context.startActivity(new Intent(context, homePage.class));
                                                docRefB.update("benID", UID1);
                                                docRefB.update("benN", benN);
                                                docRefB.update("benS", benS);
                                                docRefB.update("benE", benE);
                                                arrayList.get(position).setBID(UID1);
                                                arrayList.get(position).setBem(benE);
                                                Toast.makeText(getContext(), " تم الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                                sendNotification();

                                                //SENDING MAIL TO THE DONATOR
                                                DocumentReference docRef=fStore.collection("item").document(itemID);//.get("User id")
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                                final String donatorID= (String)document.get("User id");
                                                                final String ItemName=(String)document.get("Title");

                                                                //// get the mail with thaat ID
                                                                DocumentReference docRef2=fStore.collection("donators").document(donatorID);//.get("User id")
                                                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()){
                                                                            DocumentSnapshot document = task.getResult();
                                                                            if (document.exists()) {
                                                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                                                String donatorMail= (String)document.get("email");
                                                                                final String BenName=(String)document.get("userName");//Donator name

                                                                                sendMail.sendMail(donatorMail, "لقد تم طلب المنتج الخاص بك", "<html>\n" +
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
                                                                                        "  <h1>مرحبًا\n" +BenName+
                                                                                        "  </h1>\n" +
                                                                                        "  <br>\n" +
                                                                                        "\n" +
                                                                                        "    <h4>يسعدنا اخباركم بطلب منتجكم  \n" +ItemName+
                                                                                        "</h4>\n" +
                                                                                        "<h6>نرجو الدخول للتطبيق والنظر في قبول الطلب </h6>\n" +
                                                                                        "  <br>\n" +
                                                                                        "\n" +
                                                                                        "  <p>دمت بود </p>\n" +
                                                                                        "  <br>\n" +
                                                                                        "\n" +
                                                                                        "  <p>Email: rafad.app@gmail.com</p>\n" +
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

                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                                ///end get mail

                                                            }
                                                        }
                                                    }
                                                });
                                                //END SENDING MAIL

                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                                //////////////////////////////////////////////////

                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;
            //Notification


            }
        });
       // UpdateToken();
        TextView desText = (TextView) rowView.findViewById(R.id.desAdabtorBen);
        TextView titText = (TextView) rowView.findViewById(R.id.titAdabtorBen);
        final ImageView HisImage = (ImageView)rowView.findViewById(R.id.imageView10);
        //TextView catText = (TextView) rowView.findViewById(R.id.catAdabtorBen);
        TextView date = (TextView) rowView.findViewById(R.id.time);



        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "inter Adaptor Benf ");

            }
        });


        desText.setText(arrayList.get(position).des);
        titText.setText(arrayList.get(position).tit);
        //catText.setText(arrayList.get(position).cat);
        date.setText(arrayList.get(position).date);
        return rowView;

    }

/*
private void UpdateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        //here error
        Token token= new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
        //
    }
    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
*/
    private void sendNotification()
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
                    send_email=Demail;
                    //send_email="arob2604@gmail.com";
                    /*
                    if (MainActivity.LoggedIn_User_Email.equals("user1@gmail.com")) {
                        send_email = "user2@gmail.com";
                    } else {
                        send_email = "user1@gmail.com";
                    }
                    */
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
                                + "\"contents\": {\"en\": \"تلقيت طلب جديد!\""+itemN+"\"}"
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
