package nl.mprog.a10973710.pset6_roshanmahes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

class CompetitionsAsyncTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private MainActivity mainAct;

    CompetitionsAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "searching for competitions...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        String link = "http://api.football-data.org/v1/competitions";
        return HttpRequestHelper.downloadFromServer(link);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONArray competitionObj = null;
        try {
            competitionObj = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert competitionObj != null;

        // send result
        this.mainAct.competitionStartIntent(result);
    }
}
