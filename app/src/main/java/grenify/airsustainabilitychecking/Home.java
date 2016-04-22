package grenify.airsustainabilitychecking;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import grenify.airsustainabilitychecking.Navigation.Disease;
import grenify.airsustainabilitychecking.Tab.Home_Tab;
import grenify.airsustainabilitychecking.Weather.file;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=8626d98926153b92b6e127d19a9725a6";
    DrawerLayout drawer;
    TabHost host;
    TextView textView;
    String data = "";
    NavigationView navigationView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.test_text);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        addTab("Home", 1);
        addTab("Stability Report", 2);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Home_Tab_Content();

    }
    private void Home_Tab_Content() {
        List <String> First_Column_header=new ArrayList<>();
        List <String> Second_Column_header=new ArrayList<>();
        List <String> Third_Column_header=new ArrayList<>();
        First_Column_header.add("Disease\nName");
        Second_Column_header.add("Current\nDisease Level");
        Third_Column_header.add("Next Hour\nDisease Level");
        new Home_Tab(this, (ListView) findViewById(R.id.home_listView_header),First_Column_header,Second_Column_header,Third_Column_header,true);
        List <String> First_Column_Content=new ArrayList<>();
        List <String> Second_Column_Content=new ArrayList<>();
        List <String> Third_Column_Content=new ArrayList<>();

        First_Column_Content.addAll(ReadDataFromJSON.GetSimilarValueFromALlObject(ReadDataFromJSON.ReadJson_From_Raw("user_disease_list")));;
        Second_Column_Content.add("5");
        Third_Column_Content.add("7");
        Second_Column_Content.add("7");
        Third_Column_Content.add("5");
        new Home_Tab(this, (ListView) findViewById(R.id.home_listView_data),First_Column_Content,Second_Column_Content,Third_Column_Content,false);
    }

    private void addTab(String tabName, int tabNo) {
        host = (TabHost) findViewById(R.id.tab_host);
        host.setup();
        TabHost.TabSpec spec;
        spec = host.newTabSpec(tabName);
        if (tabNo == 1) {
            spec.setContent(R.id.tab_one_container);
            spec.setIndicator("", getResources().getDrawable(R.drawable.home));
        } else if (tabNo == 2) {
            spec.setContent(R.id.tab_two_container);
            spec.setIndicator("", getResources().getDrawable(R.drawable.report));
        }
        host.addTab(spec);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            new OpenWeatherMapTask("").execute();
        }

//            Toast.makeText(getApplicationContext(),"dd"+out,Toast.LENGTH_SHORT).show();
        // Handle the camera action
        else if (id == R.id.nav_report) {
//            host.setCurrentTab(1);
                textView.setText(ReadDataFromJSON.getDataAsQuarry(getApplicationContext()));

        }  else if (id == R.id.nav_tools) {

            GridLayout diseaseLayout=(GridLayout) findViewById(R.id.nav_disease_grid_layout);
            diseaseLayout.setVisibility(View.VISIBLE);
            diseaseLayout.requestLayout();
            Disease.Show_Disease_Layout(navigationView,
                    diseaseLayout,
                    (SearchView) findViewById(R.id.nav_disease_search),
                    getApplicationContext(),
                    (ListView) findViewById(R.id.nav_disease_listView),
                    (Button) findViewById(R.id.nav_disease_button_back),
                    (Button) findViewById(R.id.nav_disease_button_add_disease),
                    (GridLayout) findViewById(R.id.nav_top_layout)
            );

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (id != R.id.nav_tools)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://grenify.airsustainabilitychecking/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://grenify.airsustainabilitychecking/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String cityName;


        OpenWeatherMapTask(String cityName) {
            this.cityName = cityName;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;


            String query = null;
            try {
                query = CURRENT_WEATHER_URL;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }


            final String finalQueryReturn = queryReturn;
            data = "";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    data = data.concat(finalQueryReturn);
                }
            });

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            file file = new file();
            try {
                int currenFilesize = file.getFileSize();
                if (currenFilesize <= data.length())
                    file.writeToFile(data,"weather");
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView.setText(data);
            textView.append(s);
        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        private String ParseJSON(String json) {
            String jsonResult = "";

            try {
                JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");

                if (cod != null) {
                    if (cod.equals("200")) {

                        jsonResult += jsonHelperGetString(JsonObject, "name") + "\n";
                        JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
                        if (sys != null) {
                            jsonResult += jsonHelperGetString(sys, "country") + "\n";
                        }
                        jsonResult += "\n";

                        JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                        if (coord != null) {
                            String lon = jsonHelperGetString(coord, "lon");
                            String lat = jsonHelperGetString(coord, "lat");
                            jsonResult += "lon: " + lon + "\n";
                            jsonResult += "lat: " + lat + "\n";
                        }
                        jsonResult += "\n";

                        JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        if (weather != null) {
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject thisWeather = weather.getJSONObject(i);
                                jsonResult += "weather " + i + ":\n";
                                jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
                                jsonResult += "\n";
                            }
                        }

                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        if (main != null) {
                            jsonResult += "temp: " + jsonHelperGetString(main, "temp") + "\n";
                            jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
                            jsonResult += "humidity: " + jsonHelperGetString(main, "humidity") + "\n";
                            jsonResult += "temp_min: " + jsonHelperGetString(main, "temp_min") + "\n";
                            jsonResult += "temp_max: " + jsonHelperGetString(main, "temp_max") + "\n";
                            jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
                            jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
                            jsonResult += "\n";
                        }

                        jsonResult += "visibility: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
                        jsonResult += "\n";

                        JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
                        if (wind != null) {
                            jsonResult += "wind:\n";
                            jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
                            jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                            jsonResult += "\n";
                        }

                        //...incompleted

                    } else if (cod.equals("404")) {
                        String message = jsonHelperGetString(JsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                } else {
                    jsonResult += "cod == null\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }

            return jsonResult;
        }

        private String jsonHelperGetString(JSONObject obj, String k) {
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k) {
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k) {
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }
    }
}


// Class with extends AsyncTask class
