package grenify.airsustainabilitychecking;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import grenify.airsustainabilitychecking.R;
import grenify.airsustainabilitychecking.Weather.file;

/**
 * Created by hackingdom on 4/17/16.
 */
public class ReadDataFromJSON {

    private static String FILE_PATH="/sdcard/.HealthAlert/.data/.";
    public static String getDataAsQuarry(Context context) {
        String quarry = "";
        String s = null;
             s = file.readFromFile("/sdcard/.HealthAlert/.data/.weather.json");

        try {
            JSONObject obj = new JSONObject(s);
            JSONArray geodata = obj.getJSONArray("list");
            int n = geodata.length();
                JSONObject person = geodata.getJSONObject(0);
                quarry=person.getString("main");
//            obj = new JSONObject(quarry);
//            quarry = obj.getString("list");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quarry;
    }
        public static String ReadJson_From_Raw(String fileName){

            String s = null;
                s = file.readFromFile(FILE_PATH+fileName+".json");
        return s;
    }
    public static List <String> GetSimilarValueFromALlObject(String data){
        List<String> valueList =new ArrayList<String>();
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("disease");
            for (int i=0;i<jsonArray.length();i++){
               JSONObject jsonObject1=jsonArray.getJSONObject(i);
                valueList.add(jsonObject1.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueList;
    }

    public static void WriteOrObjectToJson(String name,boolean IsRemove){

        String pattern="{\"name\":\"" + name + "\"}";
            if (IsRemove==true) {
                String data = ReadJson_From_Raw("user_disease_list");
                data=data.replace(" ","");
                data = data.replace(","+pattern, "");
                data = data.replace(pattern+",", "");
                data = data.replace(pattern, "");
                file file = new file();
                file.writeToFile(data, "user_disease_list");
                //copy to diseaseList
                data = ReadJson_From_Raw("disease_list");
                data=data.substring(0, data.length() - 2);
                if (!data.equals("{\"disease\":[")) {
                    data = data.concat("," + pattern + "]}");
                }else {
                    data = data.concat( pattern + "]}");
                }
                file file2 = new file();
                file2.writeToFile(data, "disease_list");

            }else if (IsRemove==false){//add value to user list
                String data = ReadJson_From_Raw("disease_list");
                data=data.replace(" ","");
                data = data.replace(","+pattern, "");
                data = data.replace(pattern+",", "");
                data = data.replace(pattern, "");
                file file = new file();
                file.writeToFile(data, "disease_list");
                //copy to diseaseList
                data = ReadJson_From_Raw("user_disease_list");
                data=data.substring(0, data.length() - 2);
                if (!data.equals("{\"disease\":[")) {
                    data = data.concat("," + pattern + "]}");
                }else {
                    data = data.concat( pattern + "]}");
                }
                file file2 = new file();
                file2.writeToFile(data, "user_disease_list");
            }


    }

}
