package applications.listadapter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {
    ArrayList<User> userList;
    ListView listView;
    User user;
    phpConn phpC = new phpConn();
    final String fetch = "http://192.168.1.144:8080/sqli/fetch2json.php";
    JSONArray js_array;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        userList = new ArrayList<>();
        try {
            phpC.urlCon(fetch);
//getting an array back
            js_array = new JSONArray(phpC.getAnswer());
            /* reading the JSON array line by line */
            for (int i = 0; i < js_array.length(); i++) {
                String value=js_array.getString(i);
                String value1 = value.substring(1, value.indexOf(","));
                Log.v("george value1 ",value1.toString());
                String remainder = value.substring(value.indexOf(",")+1, value.length());
                String value2 = remainder.substring( 0, remainder.indexOf(","));
                Log.v("george value2 ",value2.toString());
                user = new User(value1,value2,value2);
                userList.add(i,user);
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, userList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}