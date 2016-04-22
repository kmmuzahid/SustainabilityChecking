package grenify.airsustainabilitychecking.Navigation;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import grenify.airsustainabilitychecking.R;
import grenify.airsustainabilitychecking.ReadDataFromJSON;

/**
 * Created by hackingdom on 4/20/16.
 */
public class Disease{

    static int diseaseView_color;
    static ArrayAdapter<String> adpter;
    public static void  Show_Disease_Layout(final NavigationView navigationView , final GridLayout nav_disease_layout_grid, final SearchView searchView,
                                            final Context context, final ListView listView_disease_show, final Button backButton, final Button add, final GridLayout nav_top_layout
                                            ){

        Nav_Menu_Hide_Or_Visible(navigationView, 4, false);

        oncreate(context, navigationView, nav_top_layout, listView_disease_show, false,"user_disease_list");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add.getVisibility() == View.VISIBLE) {
                    nav_disease_layout_grid.setVisibility(View.GONE);
                    Nav_Menu_Hide_Or_Visible(navigationView, 4, true);
                } else {
                    add.setVisibility(View.VISIBLE);
                    listView_disease_show.setVisibility(View.VISIBLE);
                    listView_disease_show.setBackgroundColor(Color.BLACK);
                    searchView.setVisibility(View.GONE);
                    oncreate(context, navigationView, nav_top_layout, listView_disease_show, false,"user_disease_list");
                }

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diseaseView_color = listView_disease_show.getDrawingCacheBackgroundColor();
                add.setVisibility(View.GONE);
                listView_disease_show.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                adpter.clear();
                listView_disease_show.setAdapter(adpter);
                searchView.setVisibility(View.VISIBLE);
                searchView.onActionViewExpanded();
                oncreate(context, navigationView, nav_top_layout, listView_disease_show, true,"disease_list");

            }
        });


    }

    private static void oncreate(final Context context, final NavigationView navigationView,
                                 final GridLayout nav_top_layout, final ListView listView_disease_show, final boolean isAddEmementAlive, final String fileName
    ) {

        ArrayList<String> _diseaseName = new ArrayList<>();
        _diseaseName.addAll(ReadDataFromJSON.GetSimilarValueFromALlObject(ReadDataFromJSON.ReadJson_From_Raw(fileName)));
        int totalHeightOflistView_disease_show=navigationView.getHeight()-nav_top_layout.getHeight();
        int total_list_capble= totalHeightOflistView_disease_show / 30;
        if (total_list_capble>=_diseaseName.size()) {
            listView_disease_show.getLayoutParams().height = navigationView.getHeight() - nav_top_layout.getHeight();
        }else if (total_list_capble<_diseaseName.size()){
            int currentHeight=(_diseaseName.size()-total_list_capble)*30;
            listView_disease_show.getLayoutParams().height=currentHeight;
        }
        listView_disease_show.requestLayout();
         adpter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,_diseaseName);
         adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_disease_show.setAdapter(adpter);
        navigationView.requestLayout();
        if (isAddEmementAlive==true){
            listView_disease_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ReadDataFromJSON.WriteOrObjectToJson(listView_disease_show.getItemAtPosition(position).toString(), false);
                    Toast.makeText(context,listView_disease_show.getItemAtPosition(position).toString()+" Has Been Added to Your List",Toast.LENGTH_SHORT).show();
                    RefreshList(context,listView_disease_show,navigationView,nav_top_layout,fileName,isAddEmementAlive);
                }
            });
        }else if (isAddEmementAlive==false){
            listView_disease_show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    ReadDataFromJSON.WriteOrObjectToJson(listView_disease_show.getItemAtPosition(position).toString(),true);
                    Toast.makeText(context,listView_disease_show.getItemAtPosition(position).toString()+" Has Been Removed From Your List",Toast.LENGTH_SHORT).show();
                    RefreshList(context, listView_disease_show, navigationView, nav_top_layout, fileName,isAddEmementAlive);
                    return false;
                }
            });
        }

    }

    public static void Nav_Menu_Hide_Or_Visible(NavigationView navigationView,int listSize,boolean IsTrue){
        for (int i=0;i<=listSize;i++) {
            navigationView.getMenu().getItem(i).setVisible(IsTrue);
            navigationView.requestLayout();
        }
    }

    private static void RefreshList(Context context,ListView listView_disease_show,NavigationView navigationView,GridLayout nav_top_layout,String fileName,boolean isAddElemnetAlive){
        adpter.clear();
        listView_disease_show.setAdapter(adpter);
        oncreate(context, navigationView, nav_top_layout, listView_disease_show, isAddElemnetAlive,fileName);
    }


}
