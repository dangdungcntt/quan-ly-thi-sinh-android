package com.nddcoder.quanlythisinh;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ThiSinhAdapter extends BaseAdapter {

    List<ThiSinh> dsThiSinh;
    Activity activity;

    public ThiSinhAdapter(List<ThiSinh> dsThiSinh, Activity activity) {
        this.dsThiSinh = dsThiSinh;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return dsThiSinh.size();
    }

    @Override
    public Object getItem(int position) {
        return dsThiSinh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_thisinh, null);
            viewHolder = new ViewHolder();
            viewHolder.txtHoTen = convertView.findViewById(R.id.txtHoTen);
            viewHolder.txtSoBD = convertView.findViewById(R.id.txtSoBD);
            viewHolder.txtTongDiem = convertView.findViewById(R.id.txtTongDiem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ThiSinh thiSinh = (ThiSinh) getItem(position);

        viewHolder.txtHoTen.setText(thiSinh.getHoTen());
        viewHolder.txtSoBD.setText(thiSinh.getSoBaoDanh());

        Double tong = (thiSinh.getDiemToan() + thiSinh.getDiemLy() + thiSinh.getDiemHoa());
        viewHolder.txtTongDiem.setText(String.valueOf(tong));

        return convertView;
    }

    private static class ViewHolder {
        TextView txtHoTen, txtSoBD, txtTongDiem;
    }
}
