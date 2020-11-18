
package com.example.rafad.report;
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

        import com.example.rafad.R;
        import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class pop_up_reported  extends AppCompatActivity {

    public Button Accept;
    Button Decline;
    public RadioButton radioButton2;
    public RadioButton radioButton1;

    TextView nameOfDonator;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //String state;
    //PopupWindow display method

    public void showPopupWindow(final View view) {

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_window_reported, null);



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

        nameOfDonator=popupView.findViewById(R.id.textView16);

        //nameOfDonator.setText(name);//Name of the donator


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