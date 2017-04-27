package cheessie.bcgameshelper;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null){
            FragmentManager fm = getSupportFragmentManager(); //populate the slots with saved fragments
            fm.beginTransaction().add(R.id.slot1, getItem(1),"i1").commit();
            fm.beginTransaction().add(R.id.slot2, getItem(2),"i2").commit();
            fm.beginTransaction().add(R.id.slot3, getItem(3),"i3").commit();
            fm.beginTransaction().add(R.id.slot4, getItem(4),"i4").commit();
            fm.beginTransaction().add(R.id.slot5, getItem(5),"i5").commit();
            fm.beginTransaction().add(R.id.slot6, getItem(6),"i6").commit();
            fm.beginTransaction().add(R.id.slot7, getItem(7),"i7").commit();
            fm.beginTransaction().add(R.id.slot8, getItem(8),"i8").commit();
            fm.beginTransaction().add(R.id.slot9, getItem(9),"i9").commit();
        }
    }

    private SlotFragment getItem(int i){
        String tag = "i"+String.valueOf(i);
        SharedPreferences preferences = getSharedPreferences("items",0);
        String[] info = preferences.getString(tag,"empty").split(";");
        SlotFragment res;
        switch (info[0]){
            case "counter":
                res = CounterFragment.newInstance(info[1],info[2],Integer.parseInt(info[3]));
                break;
            case "die": //if a die was saved then it's a 100% legal die (lowerbound isn't higher than the upper one)
                res = DieFragment.newInstance(info[1],info[2],Integer.parseInt(info[3]),Integer.parseInt(info[4]));
                break;
            default:
                res = new EmptyFragment();
                break;
        }
        return res;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent goToHelp = new Intent(this, HelpActivity.class);
            startActivity(goToHelp);
            return true;
        }

        if (id == R.id.action_reset) {
            reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void reset(){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.slot1, new EmptyFragment(), "i1").commit();
        fm.beginTransaction().replace(R.id.slot2, new EmptyFragment(), "i2").commit();
        fm.beginTransaction().replace(R.id.slot3, new EmptyFragment(), "i3").commit();
        fm.beginTransaction().replace(R.id.slot4, new EmptyFragment(), "i4").commit();
        fm.beginTransaction().replace(R.id.slot5, new EmptyFragment(), "i5").commit();
        fm.beginTransaction().replace(R.id.slot6, new EmptyFragment(), "i6").commit();
        fm.beginTransaction().replace(R.id.slot7, new EmptyFragment(), "i7").commit();
        fm.beginTransaction().replace(R.id.slot8, new EmptyFragment(), "i8").commit();
        fm.beginTransaction().replace(R.id.slot9, new EmptyFragment(), "i9").commit();
    }

}