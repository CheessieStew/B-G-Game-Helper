package cheessie.bcgameshelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class CounterFragment extends SlotFragment implements View.OnClickListener{

    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String COUNT = "count";
    private String name;
    private String color;
    private int count;

    public static CounterFragment newInstance(String name, String color, int count) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(COLOR, color);
        args.putInt(COUNT,count);
        fragment.setArguments(args);
        return fragment;
    }

    public CounterFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            name = savedInstanceState.getString(NAME);
            color = savedInstanceState.getString(COLOR);
            count = savedInstanceState.getInt(COUNT);
        }
        else if (getArguments() != null) {
            name = getArguments().getString(NAME);
            color = getArguments().getString(COLOR);
            count = getArguments().getInt(COUNT);
        }
        else {
            name = "unnamed";
            color = "Black";
            count = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState); //first inflate the SlotFragment layout
        ViewGroup slot = (ViewGroup) parent.findViewById(R.id.slot); //find the slot in it
        View stuff = inflater.inflate(R.layout.fragment_counter, slot, true); //then inflate this fragment into that slot

        stuff.findViewById(R.id.minusOne).setOnClickListener(this);
        stuff.findViewById(R.id.plusOne).setOnClickListener(this);
        ((TextView)stuff.findViewById(R.id.name)).setText(name);
        ((TextView)stuff.findViewById(R.id.counter)).setText(String.valueOf(count));
        slot.setBackgroundColor(COLORS.get(color));
        slot.setMinimumHeight(500);
        return parent;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(NAME, name);
        savedInstanceState.putString(COLOR, color);
        savedInstanceState.putInt(COUNT, count);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onStop(){
        super.onStop();
        SharedPreferences settings = getActivity().getSharedPreferences("items", 0);
        SharedPreferences.Editor editor = settings.edit();
        String saveData = "counter;"+name+";"+color+";"+String.valueOf(count);
        editor.putString(getTag(),saveData);
        editor.apply();
    }

    public void onClick(View view) {
        if( view.getId()==R.id.plusOne ) count++;
        else count--;
        TextView tv = (TextView) getView().findViewById(R.id.counter);
        tv.setText(String.valueOf(count));
    }

}
