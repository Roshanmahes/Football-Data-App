package nl.mprog.a10973710.pset6_roshanmahes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    private ListView listView;

    JSONObject team;
    JSONArray playerArray;
    JSONObject json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // retrieve data
        Bundle extras = getIntent().getExtras();

        try {
            team = new JSONObject(extras.getString("data"));
            playerArray = team.getJSONArray("players");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // store data into ArrayList
        final ArrayList<String> playersInfo = new ArrayList<>();

        // put data at device screen
        putData(playersInfo);

            // view our data in a list
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_expandable_list_item_1, playersInfo);

            listView = (ListView) findViewById(R.id.teamList);
            listView.setAdapter(mArrayAdapter);

            // listen for clicks
            setListener(); // playersInfo
        }

    // view data at screen
    private void putData(ArrayList<String> playersInfo) {
        for (int i = 0; i < playerArray.length(); i++) {

            try {
                json_data = playerArray.getJSONObject(i);

                String name = json_data.getString("name");
                String teamNumber = json_data.getString("jerseyNumber");
                String dateOfBirth = json_data.getString("dateOfBirth");
                String nationality = json_data.getString("nationality");
                String value = json_data.getString("marketValue");

                // if teamNumber or value are unknown
                teamNumber = teamNumber.replaceFirst("null"," - ");
                value = value.replaceFirst("null"," ?");

                // create player info
                playersInfo.add("[" + teamNumber + "] " + name + " (€" + value.replaceFirst(" €","")
                        + ")\nNationality: " + nationality + " | Born: " + dateOfBirth);

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    // if no team info is available
        if (playerArray.length() == 0) {
            Toast.makeText(this, "No team info available.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void setListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // add to favourites
                return true;
            }
        });
    }
}