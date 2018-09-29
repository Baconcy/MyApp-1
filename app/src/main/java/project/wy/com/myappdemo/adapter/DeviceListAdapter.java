package project.wy.com.myappdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import project.wy.com.myappdemo.OpenYSPlayerActivity;
import project.wy.com.myappdemo.R;
import project.wy.com.myappdemo.bean.DeviceInfoBean;

public class DeviceListAdapter extends BaseAdapter {

    private Context mContext;
    private DeviceInfoBean deviceList;
    private int item_layout;
    public DeviceListAdapter(Context context) {
        mContext = context;
        this.item_layout = item_layout;
    }

    public void setData(DeviceInfoBean deviceBean) {
        deviceList = deviceBean;
    }
    @Override
    public int getCount() {
        return deviceList.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         TextView deive_name = null;
         TextView device_type = null;
         ImageView device_icon = null;
         ImageView video_img = null;
        if(convertView == null){
           convertView = View.inflate(mContext, R.layout.device_item_layout,null);
           deive_name = convertView.findViewById(R.id.device_name);
           device_type = convertView.findViewById(R.id.device_model);
           device_icon = convertView.findViewById(R.id.decice_icon);
           video_img = convertView.findViewById(R.id.video_imgBtn);

           convertView.setTag(new ViewHolder(deive_name,device_type,device_icon,video_img));
        }else{
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            deive_name = viewHolder.device_name;
            device_type = viewHolder.device_type;
            device_icon = viewHolder.device_icon;
            video_img = viewHolder.video_img;
        }
        String name = deviceList.getList().get(position).getEquip_name();
        String type = deviceList.getList().get(position).getEquip_no();
        Log.i("adapter",name);
        deive_name.setText(name);
        device_type.setText(type);
        video_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OpenYSPlayerActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        private TextView device_name;
        private TextView device_type;
        private ImageView device_icon;
        private ImageView video_img;
        public ViewHolder(TextView device_name,TextView device_type,ImageView device_icon,ImageView video_img ){
            this.device_icon = device_icon;
            this.device_name = device_name;
            this.device_type = device_type;
            this.video_img = video_img;
        }
    }
}
