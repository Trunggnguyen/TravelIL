package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Theloai_Adapter;
import com.example.travelil.R;

import java.util.ArrayList;


public class BlankFragment_theloai extends Fragment {

    RecyclerView recyclerView;
    Theloai_Adapter theloai_adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blank_theloai, container, false);
        ArrayList<String> arrayListtheloai= new ArrayList<>();
        arrayListtheloai.add("Danh lam thắng cảnh");
        arrayListtheloai.add("Di tích lịch sử");
        arrayListtheloai.add("Địa điểm nghỉ dưỡng");
        arrayListtheloai.add("Địa điểm văn hóa");
        arrayListtheloai.add("Địa điểm ẩm thực");
        arrayListtheloai.add("Địa điểm xanh");
        arrayListtheloai.add("Địa điểm MICE");
        arrayListtheloai.add("Teambuilding, Camping");
        arrayListtheloai.add("Khác...");


        ArrayList<String> arrayListimage= new ArrayList<>();

        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2F_MG_9868%20(1)%20Cropped.jpg?alt=media&token=7fddbeda-affa-40b3-92ec-d3befb99b567");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2FDi-tich-lich-su-ha-noi-cot-co-ha-noi1%20Cropped.jpg?alt=media&token=c1afcf46-5e7d-4efa-98b2-cb4df1aede61");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2F1616120537%20Cropped.jpg?alt=media&token=b441cf44-26ba-401a-8d7e-fbc7916b1997");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2Fdo-tren-suoi-yen-1654%20Cropped.jpg?alt=media&token=a05c1be9-0a2b-4585-a24b-3fc18d0c9d0e");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2Fcom-8421a%20Cropped.jpg?alt=media&token=2a1ca8c5-52ad-4fd2-b95a-e6efea6dae3b");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2Fecopark-co-gi-choi-wecheckin-5-1024x498%20Cropped.jpg?alt=media&token=851e5735-5096-4ed1-9f22-d1bff4be5449");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2Fsan-pham-du-lich-mice-la-gi%20Cropped.jpg?alt=media&token=6e9e08d8-8998-4c92-83fa-2e0c8aaee3c8");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2FCamping1-min%20Cropped.jpg?alt=media&token=c271825f-6af1-4870-a578-c8b1987512bb");
        arrayListimage.add("https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/image_theloai%2Funnamed%20Cropped.jpg?alt=media&token=c40b7228-ef71-499f-84f8-c76594979067");


        recyclerView = view.findViewById(R.id.recycalview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        theloai_adapter= new Theloai_Adapter(getContext(),arrayListtheloai,arrayListimage);
        recyclerView.setAdapter(theloai_adapter);
        return  view;
    }
}