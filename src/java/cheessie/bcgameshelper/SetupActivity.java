package cheessie.bcgameshelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class SetupActivity extends FragmentActivity implements Spinner.OnItemSelectedListener{
    public static final String ITEM = "item";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String MINV = "minv";
    public static final String MAXV = "maxv";
    public static final String CURCOUNT="curcount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //populate the fragmentPicker spinner
        Spinner spinner = (Spinner) findViewById(R.id.fragmentPicker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fragments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        switch (position) {
            case 0:
                Fragment csf = getSupportFragmentManager().findFragmentByTag("csf");
                Fragment dsf = getSupportFragmentManager().findFragmentByTag("dsf");
                if (csf!=null) getSupportFragmentManager().beginTransaction().remove(csf).commit();
                if (dsf!=null) getSupportFragmentManager().beginTransaction().remove(dsf).commit();
                break;
            case 1:
                if (getSupportFragmentManager().findFragmentByTag("csf")==null) {
                    CounterSetupFragment ncsf = new CounterSetupFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.slot, ncsf, "csf").commit();
                }
                break;
            case 2:
                if  (getSupportFragmentManager().findFragmentByTag("dsf")==null) {
                    DieSetupFragment ndsf = new DieSetupFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.slot, ndsf, "dsf").commit();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent goToHelp = new Intent(this, HelpActivity.class);
            startActivity(goToHelp);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finish(View v){
        Intent data = new Intent();
        if (v.getId()==R.id.finish) {
            String item = ((Spinner) findViewById(R.id.fragmentPicker)).getSelectedItem().toString();

            String name = "Unnamed";
            if (findViewById(R.id.name) != null)
                name = ((EditText) findViewById(R.id.name)).getText().toString();

            String color = "black";
            if (findViewById(R.id.colorPicker) != null)
                color = ((Spinner) findViewById(R.id.colorPicker)).getSelectedItem().toString();

            int minv = 1;
            if (findViewById(R.id.lowerBound) != null)
                minv = Integer.parseInt(((EditText) findViewById(R.id.lowerBound)).getText().toString());

            int maxv = 10;
            if (findViewById(R.id.upperBound) != null)
                maxv = Integer.parseInt(((EditText) findViewById(R.id.upperBound)).getText().toString());

            int curcount = 0;
            if (findViewById(R.id.startCount) != null)
                curcount = Integer.parseInt(((EditText) findViewById(R.id.startCount)).getText().toString());

            data.putExtra(ITEM, item);
            data.putExtra(NAME, name);
            data.putExtra(COLOR, color);
            data.putExtra(MINV, minv);
            data.putExtra(MAXV, maxv);
            data.putExtra(CURCOUNT, curcount);
            setResult(RESULT_OK, data);
        }
        else setResult(RESULT_CANCELED,data);
        finish();
    }
}
