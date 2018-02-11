package com.halilyavru.getirbitaksihackathon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by halilmac on 10/02/2018.
 */

public class RecordsFragment extends Fragment {


    private Button btnIleri,btnGeri;
    private List<Record> listRecord ;
    private RecyclerView recyclerView;
    private AdapterRecords adapter;
    private TextView tvIlerlemeAciklama;
    private ProgressBar pbIlerleme;
    private int toplamSayfa = 0,gecerliSayfa = 1;
    private List<Record> listCurrentRecord = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_records,container,false);

        if(getArguments().getParcelableArrayList("listRecord") != null){
            listRecord = getArguments().getParcelableArrayList("listRecord");
        }else{
            listRecord =  new ArrayList<>();
        }

        adapter = new AdapterRecords(listCurrentRecord);

        toplamSayfa = listRecord.size()%10 == 0 ? listRecord.size()/10 : (listRecord.size()/10)+1;

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);


        tvIlerlemeAciklama = (TextView) view.findViewById(R.id.tv_ilerleme_aciklama);
        pbIlerleme = (ProgressBar) view.findViewById(R.id.pb_ilerleme);
        btnIleri = (Button) view.findViewById(R.id.btn_ileri);
        btnGeri = (Button) view.findViewById(R.id.btn_geri);

        pbIlerleme.setMax(toplamSayfa);
        tvIlerlemeAciklama.setText("Sayfa "+gecerliSayfa+" / "+toplamSayfa);

        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gecerliSayfa == toplamSayfa){
                    return;
                }
                gecerliSayfa++;
                sayfaYukle();
            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gecerliSayfa == 1){
                    return;
                }
                gecerliSayfa--;
                sayfaYukle();

            }
        });
        sayfaYukle();

        return view;
    }


    private void sayfaYukle(){
        listCurrentRecord.clear();
        int sayfaIlkEleman = (gecerliSayfa-1)*10;
        int sayfaSonEleman = sayfaIlkEleman+10 < listRecord.size() ? sayfaIlkEleman+10 : listRecord.size()-1;
        List<Record> geciciList = listRecord.subList(sayfaIlkEleman,sayfaSonEleman);
        for (Record rec : geciciList) {
            listCurrentRecord.add(rec);
        }
        pbIlerleme.setProgress(gecerliSayfa);
        tvIlerlemeAciklama.setText("Sayfa "+gecerliSayfa+" / "+toplamSayfa);
        adapter.notifyDataSetChanged();
    }


}
