package com.nddcoder.quanlythisinh;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ThiSinhDB thiSinhDB;
    List<ThiSinh> dsThiSinh;
    ThiSinhAdapter thiSinhAdapter;

    FloatingActionButton floatingActionButton;
    EditText edtTimKiem;
    ListView lvThiSinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thiSinhDB = new ThiSinhDB(this, "db_thisinh", null, 2);
//        khoiTaoDuLieuDB();
        anhXa();
        themSuKien();
    }

    private void themSuKien() {

    }

    private void anhXa() {
        floatingActionButton = findViewById(R.id.floatingActionButton);
        edtTimKiem = findViewById(R.id.edtTimKiem);

        lvThiSinh = findViewById(R.id.lvThiSinh);

        dsThiSinh = thiSinhDB.danhSachThiSinh();

        //sap xep dsThiSinh
        Collections.sort(dsThiSinh, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {

                String[] arrName1 = o1.getHoTen().split(" ");
                String[] arrName2 = o2.getHoTen().split(" ");

                return arrName1[arrName1.length - 1].compareToIgnoreCase(arrName2[arrName2.length - 1]);
            }
        });

        thiSinhAdapter = new ThiSinhAdapter(dsThiSinh, this);

        lvThiSinh.setAdapter(thiSinhAdapter);

        //context menu
        registerForContextMenu(lvThiSinh);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.lvThiSinh) {
            getMenuInflater().inflate(R.menu.listview_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_xoa) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;

            ThiSinh ts = dsThiSinh.get(position);
            Double tongDiem = ts.getDiemToan() + ts.getDiemLy() + ts.getDiemHoa();

            final List<ThiSinh> dsCanXoa = new ArrayList<>();
            for (ThiSinh thiSinh : dsThiSinh) {
                if (thiSinh.getDiemHoa() + thiSinh.getDiemLy() + thiSinh.getDiemToan() < tongDiem) {
                    dsCanXoa.add(thiSinh);
                }
            }

            if (dsCanXoa.size() == 0) {
                Toast.makeText(this, "Khong co thi sinh co tong diem nho hon " + tongDiem, Toast.LENGTH_LONG).show();
                return false;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xoa thi sinh");
            builder.setMessage("Ban co muon xoa " + dsCanXoa.size() + " thi sinh co tong diem < " + tongDiem);

            builder.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int thanhCong = 0;

                    for (ThiSinh tscanxoa : dsCanXoa) {
                        if (thiSinhDB.xoaThiSinh(tscanxoa.getSoBaoDanh())) {
                            thanhCong++;
                        }
                    }

                    dsThiSinh.clear();
                    dsThiSinh.addAll(thiSinhDB.danhSachThiSinh());
                    thiSinhAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Xoa thanh cong " + thanhCong + ", that bai " + (dsCanXoa.size() - thanhCong), Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        return super.onContextItemSelected(item);


    }

    private void khoiTaoDuLieuDB() {
        thiSinhDB.themThiSinh(new ThiSinh("GHA01", "Nguyen Van A", 5f, 6f, 2f));
        thiSinhDB.themThiSinh(new ThiSinh("GHA05", "Nguyen Van F", 9f, 2f, 6f));
        thiSinhDB.themThiSinh(new ThiSinh("GHA02", "Nguyen Van B", 6f, 5f, 3f));
        thiSinhDB.themThiSinh(new ThiSinh("GHA03", "Nguyen Van C", 7f, 4f, 4f));
        thiSinhDB.themThiSinh(new ThiSinh("GHA06", "Nguyen Van G", 10f, 1f, 7f));
        thiSinhDB.themThiSinh(new ThiSinh("GHA04", "Nguyen Van D", 8f, 3f, 5f));
    }
}
