package grenify.airsustainabilitychecking.Weather;

import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by root on 05/01/16.
 */
public class file {
    private static String fileName;

    public file() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/.HealthAlert");
        File folderhtml = new File(Environment.getExternalStorageDirectory() + "/.HealthAlert/.data");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!folderhtml.exists()) {
            folderhtml.mkdir();

        }

    }

    public static String readFromFile(String path) {
        String ret = "";
        File file = new File(path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            if (fileInputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String reciveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((reciveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(reciveString);
                }
                ret = stringBuilder.toString();
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void writeToFile(String data,String name) {

        File file = new File("/sdcard/.HealthAlert/.data/."+name+".json");
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int getFileSize() throws IOException {
        int size = 0;
        File file = new File("/sdcard/.HealthAlert/.data/.weather.json");
        if (file.exists()) {
            size = Integer.parseInt(String.valueOf(file.length()));
        } else {
            file.createNewFile();
        }
        return size;
    }

}
