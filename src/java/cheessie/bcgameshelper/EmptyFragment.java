package cheessie.bcgameshelper;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyFragment extends SlotFragment {


    public EmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup slot = (ViewGroup) parent.findViewById(R.id.slot);
        View stuff = inflater.inflate(R.layout.fragment_empty, slot, true);
        parent.setMinimumHeight(500);
        return parent;
    }

    public void onStop(){
        super.onStop();
        SharedPreferences settings = getActivity().getSharedPreferences("items", 0);
        SharedPreferences.Editor editor = settings.edit();
        String saveData = "empty";
        editor.putString(getTag(), saveData);
        editor.commit();
    }
}
