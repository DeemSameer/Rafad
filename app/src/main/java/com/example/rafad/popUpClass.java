package com.example.rafad;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.PopupWindow;
        import android.widget.RadioButton;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class popUpClass extends AppCompatActivity {

    Button Accept,Decline;
    RadioButton radioButton2,radioButton1,radioButton3,radioButton4,radioButton5;
    TextView settext;
    //String state;
    //PopupWindow display method

    public void showPopupWindow(final View view) {

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.po_up_window_admin, null);



        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

         Accept = popupView.findViewById(R.id.Accept);
         Decline = popupView.findViewById(R.id.Decline);
        radioButton1=popupView.findViewById(R.id.radioButton);
        radioButton2=popupView.findViewById(R.id.radioButton2);
        radioButton3=popupView.findViewById(R.id.radioButton3);
        radioButton4=popupView.findViewById(R.id.radioButton4);
        radioButton5=popupView.findViewById(R.id.radioButton5);
        settext=popupView.findViewById(R.id.settext);


        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                return;
            }
        });//end decline


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });


    }

}