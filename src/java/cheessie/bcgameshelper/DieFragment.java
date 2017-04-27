package cheessie.bcgameshelper;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;


public class DieFragment extends SlotFragment implements View.OnClickListener {

    private static Random rand=new Random();
    public static final String NAME  = "name";
    public static final String COLOR = "color";
    public static final String MINV  = "min";
    public static final String MAXV  = "max";
    private String name;
    private String color;
    private int min;
    private int max;

    public DieFragment() {
        super();
    }

    public static DieFragment newInstance(String name, String color, int minv, int maxv) {
        DieFragment fragment = new DieFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(COLOR, color);
        args.putInt(MINV, minv);
        args.putInt(MAXV, maxv);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            color = getArguments().getString(COLOR);
            min = getArguments().getInt(MINV);
            max = getArguments().getInt(MAXV);
        }
        else {
            name = "unnamed";
            color = "black";
            min = 1;
            max = 10;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState); //first inflate the SlotFragment layout
        ViewGroup slot = (ViewGroup) parent.findViewById(R.id.slot); //find the slot in it
        View stuff = inflater.inflate(R.layout.fragment_die, slot, true); //then inflate this fragment into that slot

        stuff.findViewById(R.id.roll).setOnClickListener(this);
        ((TextView)stuff.findViewById(R.id.name)).setText(name);
        slot.setBackgroundColor(COLORS.get(color));
            slot.setMinimumHeight(500);
        ((TextView)stuff.findViewById(R.id.bounds)).setText(String.valueOf(min) + "-" + String.valueOf(max));
        return parent;
    }


    public void onStop(){
        super.onStop();
        SharedPreferences settings = getActivity().getSharedPreferences("items", 0);
        SharedPreferences.Editor editor = settings.edit();
        String saveData = "die;"+name+";"+color+";"+String.valueOf(min)+";"+String.valueOf(max);
        editor.putString(getTag(), saveData);
        editor.apply();
    }

    public void onClick(View view) {
        TextView tv = (TextView) getView().findViewById(R.id.random);
        int n = rand.nextInt((max-min)+1) + min;
        tv.setText(String.valueOf(n));
    }

}
