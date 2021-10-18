package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Model.Daily;
import com.example.travelil.Model.Temp;
import com.example.travelil.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IteamWeather_Adapter extends RecyclerView.Adapter<IteamWeather_Adapter.Viewholder>  {
    Context context;
    ArrayList<Daily> dailyList;
    View view;
    String date;


    public IteamWeather_Adapter(Context context, ArrayList<Daily> dailyList, String date) {
        this.context = context;
        this.dailyList = dailyList;
        this.date = date;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.iteam_weather,parent,false);
        return new IteamWeather_Adapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

            Daily daily = dailyList.get(position);
            Temp temp = daily.getTemp();
            float nhiet = (float) (temp.getDay()- 273);
            int nhietss = (int) (nhiet- 0);
            float doam = (float) daily.getHumidity();
            int ndoams = (int) (doam- 0);
            holder.nhietdo.setText(nhietss+"");
            holder.doam.setText("Độ ẩm: "+ndoams+"%");
            holder.mua.setText(daily.getWeatherList().get(0).getDescription());
            String url ="https://trungnv.000webhostapp.com/HinhAnh/icweather/"+daily.getWeatherList().get(0).getIcon()+"@4x.png";
            Picasso.get().load(url).fit().into(holder.imageView);
        date = date.trim().toLowerCase();
        String[] arrdate={"Thứ hai","Thứ ba","Thứ tư","Thứ năm","Thứ sáu","Thứ bảy","Chủ nhật"};
        int a = arrdate.length;
       // Log.d("bbbb", a+"");
        position = position+1;
         for (int i = 0 ; i< a; i++) {
            // Log.d("bbbb", arrdate[i]+i);
             if (date.equals(arrdate[i].toLowerCase())) {

                    if (i+position<=6){

                        holder.date.setText(arrdate[i+position]);
                    }else {
                        holder.date.setText(arrdate[i+position-7]);
                     //   Log.d("bbbb", arrdate[i+position]);
                    }
             }
         }

    }
    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public class  Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView date, nhietdo, doam, mua;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            nhietdo = itemView.findViewById(R.id.nhietdo);
            doam = itemView.findViewById(R.id.doam);
            mua = itemView.findViewById(R.id.mua);
            imageView = itemView.findViewById(R.id.imageic);
        }
    }
}
