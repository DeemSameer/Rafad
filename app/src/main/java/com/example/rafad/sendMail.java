package com.example.rafad;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMail {

    public static final String TAG = "TAG";
//        sendMail.sendMail("deemsameer.ds@gmail.com","Hi","Hello");
    public static void sendMail(String recepient, String MailSubject, String MailLetter){
        Log.d(TAG, "ERRRROR 0 :");

        Properties properties=new Properties();
        //

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        final String myAccount= "rafad.app@gmail.com";
        final String password="1234DeemNada";


        Session session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount,password);
            }
        });

        Message message=new MimeMessage(session);
        //prepare message
        try{
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));//You can change TO
            message.setSubject(MailSubject);
            message.setContent(MailLetter,"text/html; charset=utf-8");
        }
        catch (Exception ex){
            Log.d(TAG, "ERRRROR 2 :" + ex.toString());
        }
        //end
        //send the message
            new SendMail().execute(message);

    }


    private static class SendMail extends AsyncTask<Message,String,String> {

        @Override
        protected String doInBackground(Message... messages) {
            try{
                Transport.send(messages[0]);
                return "success";
            }
            catch (Exception ex){
                Log.d(TAG, "ERRRROR 12 :" + ex.toString());
                return "error";
            }
        }
    }
}

