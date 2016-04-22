package grenify.airsustainabilitychecking.Customization;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import grenify.airsustainabilitychecking.R;

/**
 * Created by hackingdom on 4/22/16.
 */
public class ListViewAdpter extends BaseAdapter {
    public ArrayList <HashMap<String,String>> list;
    int FontSize;
    Activity activity;
    TextView textView_first;
    TextView textView_second;
    TextView textView_third;
    public ListViewAdpter(Activity activity,ArrayList <HashMap<String,String>> list,int FontSize){
        super();
        this.activity=activity;
        this.list=list;
        this.FontSize=FontSize;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.column_row,null);
            textView_first= (TextView) convertView.findViewById(R.id.column_disease_name);
            textView_second= (TextView) convertView.findViewById(R.id.column_disease_level_current_hour);
            textView_third= (TextView) convertView.findViewById(R.id.column_disease_level_next_hour);
        }
        HashMap <String,String> map=list.get(position);
        textView_first.setText(map.get("Disease Name"));
        textView_first.setTextSize(FontSize);
        textView_second.setText(map.get("Current Disease Level"));
        textView_second.setTextSize(FontSize);
        textView_third.setText(map.get("Next Hour Disease Level"));
        textView_third.setTextSize(FontSize);
        return convertView;
    }
}
