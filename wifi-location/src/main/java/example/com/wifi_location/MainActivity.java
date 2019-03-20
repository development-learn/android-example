package example.com.wifi_location;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import example.com.wifi_location.service.PointService;
import example.com.wifi_location.vo.PointInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PointService pointService;
    private Button btRefresh;
    private List<PointInfo> pointInfos;
    private MyAdapt myAdapt;

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointService=new PointService();
        wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        pointInfos = pointService.getPointInfos();

        btRefresh = findViewById(R.id.bt_refresh);
        btRefresh.setOnClickListener(v -> {
            refresh(v);
        });

        ListView listView = findViewById(R.id.lv_wifi);
        myAdapt = new MyAdapt();
        listView.setAdapter(myAdapt);


    }

    /**
     * 刷新操作
     *
     * @param view
     */
    public void refresh(View view) {
        pointInfos = pointService.getPointInfos();
        myAdapt.notifyDataSetChanged();

    }

    public class MyAdapt extends BaseAdapter {

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return pointInfos.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return pointInfos.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item_wifi, null);
            } else {
                view = convertView;
            }
            PointInfo info = pointInfos.get(position);
            TextView name = view.findViewById(R.id.item_tv_name);
            name.setText(info.getName());

            TextView xy = view.findViewById(R.id.item_tv_xy);
            xy.setText(info.getX() + "," + info.getY());

            TextView distance = view.findViewById(R.id.item_tv_distance);
            distance.setText(String.valueOf(info.getD()));

            return view;
        }
    }
}
