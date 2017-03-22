package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeagueActivity extends AppCompatActivity {

    private ListView listView;
    TextView leagueTitle;
    JSONObject leagueArray;
    JSONArray standing;
    JSONObject json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        leagueTitle = (TextView) findViewById(R.id.leagueTitle);

        // retrieve data
        Bundle extras = getIntent().getExtras();

        try {
            leagueArray = new JSONObject(extras.getString("data"));

            // load standing array (API is not consistent)
            if (leagueArray.has("standing")) {
                standing = leagueArray.getJSONArray("standing");
            }
            if (leagueArray.has("standings")) {
                standing = leagueArray.getJSONArray("standings");
            }
            leagueTitle.setText(leagueArray.getString("leagueCaption"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // store data into ArrayLists
        final ArrayList<String> teamNames = new ArrayList<>();
        final ArrayList<String> teamLinks = new ArrayList<>();

        for (int i = 0; i < standing.length(); i++) {

            try {
                json_data = standing.getJSONObject(i);

                String teamName = json_data.getString("teamName");
                String played = json_data.getString("playedGames");
                String goalDifference = json_data.getString("goalDifference");
                String points = json_data.getString("points");

                JSONObject links = json_data.getJSONObject("_links");
                JSONObject team = links.getJSONObject("team");
                String teamLink = team.getString("href");

                // create listItems
                teamNames.add((i + 1) + ". " + teamName + "\nPlayed: " + played
                        + " | Points: " + points + " | Goal difference: " + goalDifference);
                teamLinks.add(teamLink);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // view our data in a list
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, teamNames);

        listView = (ListView) findViewById(R.id.leagueList);
        listView.setAdapter(mArrayAdapter);

        // listen for clicks
        setListener(teamLinks);
    }

    private void setListener(final ArrayList<String> teamLinks){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // add to favourites
                return true;
            }
        });
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String teamLink = teamLinks.get(position);
                loadData(teamLink);
            }
        });
    }

    // send data to AsyncTask class
    public void loadData(String teamLink) {
        TeamAsyncTask asyncTask = new TeamAsyncTask(this);
        asyncTask.execute(teamLink);
    }

    // start TeamActivity
    public void teamStartIntent(String result) {
        Intent intent = new Intent(this, TeamActivity.class);
        intent.putExtra("data", result);
        this.startActivity(intent);
    }
}
