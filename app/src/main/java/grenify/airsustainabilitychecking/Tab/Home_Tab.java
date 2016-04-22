package grenify.airsustainabilitychecking.Tab;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import grenify.airsustainabilitychecking.Customization.ListViewAdpter;

/**
 * Created by hackingdom on 4/22/16.
 */
public class Home_Tab {
    boolean isFirst;
    public Home_Tab(Activity activity,ListView listView,
                    List<String> First_Coulmn_ArrayList,
                    List <String> Second_Coulmn_ArrayList,
                    List <String> Third_Coulmn_ArrayList,
                    boolean isFirst
    ){
        ArrayList <HashMap<String,String>> list=new ArrayList<>();
        for (int i=0;i<First_Coulmn_ArrayList.size();i++) {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("Disease Name",First_Coulmn_ArrayList.get(i));
            temp.put("Current Disease Level",Second_Coulmn_ArrayList.get(i));
            temp.put("Next Hour Disease Level",Third_Coulmn_ArrayList.get(i));
            list.add(temp);
        }
        ListViewAdpter listViewAdpter;
        if (isFirst) {
            listViewAdpter = new ListViewAdpter(activity, list, 17);
        }else {
            listViewAdpter = new ListViewAdpter(activity, list, 15);
        }
        listView.setAdapter(listViewAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
