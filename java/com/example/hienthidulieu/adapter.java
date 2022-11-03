package com.example.hienthidulieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<User> list_computer;

    public adapter(Context context, int layout, List<User> list_computer) {
        this.context = context;
        this.layout = layout;
        this.list_computer = list_computer;
    }

    @Override
    public int getCount() {
        return list_computer.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Tạo view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        // ánh xạ

        TextView tv_id = view.findViewById(R.id.tv_idcomputer);
        TextView tv_name = view.findViewById(R.id.tv_name);

        // gán giá trị

        User user = list_computer.get(i);
        tv_id.setText("Mã máy: " + String.valueOf(user.getId()));
        tv_name.setText("Máy: " + user.getName());

        return view;
    }
}
