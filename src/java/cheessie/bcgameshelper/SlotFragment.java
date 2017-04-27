package cheessie.bcgameshelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Hashtable;


public abstract class SlotFragment extends Fragment {

    public static final int CHOOSE_ITEM_REQUEST = 666;


    protected static final Hashtable<String,Integer> COLORS = new Hashtable<String,Integer>()
    {{      put("Yellow",    0x30FFFF00);
            put("Black",     0x30000000);
            put("Green",     0x3000FF00);
            put("Red",       0x30FF0000);
            put("Cyan",      0x3000FFFF);
            put("Gray",      0x307F7F7F);
            put("Light gray",0x30FFFFFF);
            put("Purple",    0x30FF00FF);
            put("Orange",    0x30FF9100);
            put("Blue",      0x300000FF);}};


    public SlotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout and set onClickListener for the button
        View stuff = inflater.inflate(R.layout.fragment_slot, container, false);
        final Context context = getActivity();
        ImageButton button = (ImageButton)stuff.findViewById(R.id.setup);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent counterSetup = new Intent(context, SetupActivity.class);
                startActivityForResult(counterSetup, CHOOSE_ITEM_REQUEST);
            }
        });
        button.bringToFront();
        return stuff;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if the result was OK it causes the fragment to replace itself with one specified by the result
        int myContainerID = ((ViewGroup) getView().getParent()).getId();
        String myTag = getTag();
        if (resultCode == getActivity().RESULT_OK && requestCode == CHOOSE_ITEM_REQUEST) {
            String item = data.getStringExtra(SetupActivity.ITEM);
            String name = data.getStringExtra(SetupActivity.NAME);
            String color = data.getStringExtra(SetupActivity.COLOR);
            int minv = data.getIntExtra(SetupActivity.MINV, 1);
            int maxv = data.getIntExtra(SetupActivity.MAXV,10);
            int curcount = data.getIntExtra(SetupActivity.CURCOUNT,0);
            switch (item) {
                case "Counter":
                    CounterFragment cf = CounterFragment.newInstance(name, color, curcount);
                    getFragmentManager().beginTransaction().replace(myContainerID, cf, myTag).commit();
                    break;
                case "Die":
                    if (minv<=maxv) {
                        DieFragment df = DieFragment.newInstance(name, color, minv, maxv);
                        getFragmentManager().beginTransaction().replace(myContainerID, df, myTag).commit();
                    }
                    else{
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
                        dlgAlert.setMessage("Lower bound should be lower than the upper one, don't you think?");
                        dlgAlert.setTitle("Sad face");
                        dlgAlert.setPositiveButton("OK :(", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                    }
                    break;
                case "Empty":
                    getFragmentManager().beginTransaction().replace(myContainerID, new EmptyFragment(), myTag).commit();
            }
        }
    }

}
