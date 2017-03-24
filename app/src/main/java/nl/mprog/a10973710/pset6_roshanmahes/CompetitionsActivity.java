package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompetitionsActivity extends AppCompatActivity {

    private ListView listView;
    JSONArray competitions;
    JSONObject json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

        // retrieve data
        Bundle extras = getIntent().getExtras();

        try {
            competitions = new JSONArray(extras.getString("data"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // store data into ArrayLists
        ArrayList<String> items = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        // put data at device screen
        putData(items, ids);

        // view our data in a list
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, items);

        listView = (ListView) findViewById(R.id.competitionsList);
        listView.setAdapter(mArrayAdapter);

        // listen for clicks
        setListener(ids);
    }

    // view data at screen
    private void putData(ArrayList<String> items, ArrayList<Integer> ids) {
        for (int i = 1; i < competitions.length() - 1; i++) {

            // (Hardcoded): don't add the DFB-Pokal, because this is not a league
            if (i == 6) continue;

            try {
                json_data = competitions.getJSONObject(i);
                int id = json_data.getInt("id");
                String caption = json_data.getString("caption");

                items.add(caption);
                ids.add(id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListener(final ArrayList<Integer> ids){
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer leagueNumber = ids.get(position);
                loadData(leagueNumber);
            }
        });
    }

    // send data to AsyncTask class
    public void loadData(Integer leagueNumber) {
        LeagueAsyncTask asyncTask = new LeagueAsyncTask(this);
        asyncTask.execute(leagueNumber.toString());
    }

    // start LeagueActivity
    public void leagueStartIntent(String result) {
        Intent intent = new Intent(this, LeagueActivity.class);
        intent.putExtra("data", result);
        this.startActivity(intent);
    }
}
