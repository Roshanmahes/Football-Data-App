package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

class TeamAsyncTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private LeagueActivity leagueAct;

    TeamAsyncTask(LeagueActivity league) {
        this.leagueAct = league;
        this.context = this.leagueAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "loading team...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        String link = params[0] + "/players";
        return HttpRequestHelper.downloadFromServer(link);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject teamObj = null;
        try {
            teamObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert teamObj != null;

        // send result
        this.leagueAct.teamStartIntent(result);
    }
}
