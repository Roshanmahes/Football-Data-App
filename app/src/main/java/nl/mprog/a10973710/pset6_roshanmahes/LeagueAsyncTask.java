package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

class LeagueAsyncTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private CompetitionsActivity compAct;

    LeagueAsyncTask(CompetitionsActivity comp) {
        this.compAct = comp;
        this.context = this.compAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "loading league table...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpLeagueHelper.downloadFromServer(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject leagueObj = null;
        try {
            leagueObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert leagueObj != null;

        // send result
        this.compAct.leagueStartIntent(result);
    }
}
