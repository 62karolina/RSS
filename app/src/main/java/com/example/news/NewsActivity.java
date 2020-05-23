package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.ValueIterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {


    // благодоря этому классу мы будет разбирать данные на куски
    public Elements title;
    // то в чем будем хранить данные пока не передадим адаптеру
    public ArrayList<String> titleList = new ArrayList<String>();
    public ArrayList<String> titleList1 = new ArrayList<String>();

    // Listview Adapter для вывода данных
    private ArrayAdapter<String> adapter;
    // List view
    private ListView lv;
    private Elements link;
    Document doc;
    CheckBox navinybyCheck;

    private Button naviny, minsknews, sputnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        naviny = (Button)findViewById(R.id.navinby);
        naviny.setOnClickListener(this);
        minsknews = (Button)findViewById(R.id.minsknewsby);
        minsknews.setOnClickListener(this);
        sputnik = (Button)findViewById(R.id.sputnikby);
        sputnik.setOnClickListener(this);
        Intent intent = getIntent();

        if(intent.getBooleanExtra("naviny", true))
            naviny.setVisibility(View.INVISIBLE);
        if(intent.getBooleanExtra("minsk", true))
            minsknews.setVisibility(View.INVISIBLE);
        if(intent.getBooleanExtra("sputnik", true))
            sputnik.setVisibility(View.INVISIBLE);

        // определение данных
        lv = findViewById(R.id.lvMain);
        // запрос к нашему отдельному поток на выборку данных
        new NewThread().execute("https://naviny.by/");
        // Добавляем данные для ListView
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, titleList);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catName = "";
                for (int i = 0; i < titleList1.size(); i++) {
                    catName = catName + titleList1.get(i) + " ";
                }
                Log.d("gfg", catName);

                Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                intent.putExtra("keyName", titleList1.get(position));

                startActivity(intent);
            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navinby:
                new NewThread().execute("https://naviny.by/" ); break;
            case R.id.minsknewsby:
                new NewThread().execute("https://minsknews.by/"); break;
            case R.id.sputnikby:
                new NewThread().execute("https://m.sputnik.by/"); break;

        }
    }


    public class NewThread extends AsyncTask<String, Void, String> {

        // Метод выполняющий запрос в фоне, в версиях выше 4 андроида, запросы в главном потоке выполнять
        // нельзя, поэтому все что вам нужно выполнять - выносите в отдельный тред
        @Override
        protected String doInBackground(String... arg) {

            // класс который захватывает страницу
            try {

                doc = Jsoup.connect(arg[0]).get();
               // title = doc.select(".news-entry big annoticed time nii");
                if(arg[0].equals("https://naviny.by/"))
                {
                    doc = Jsoup.connect("https://naviny.by/").get();

                    Elements mBody = doc.select("a.link-tooltip");

                    Elements urls = mBody.select("a");
                    Elements link = urls.tagName(".href");
                    titleList.clear();
                    titleList1.clear();
                    for (Element i : mBody) {
                        titleList.add(i.text());
                    }


                    for (Element titles : link) {
                        titleList1.add(titles.attr("abs:href"));
                    }
                }
                if(arg[0].equals("https://minsknews.by/"))
                {
                    doc = Jsoup.connect("https://minsknews.by/").get();

                    Elements mBody = doc.select("h3.entry-title.td-module-title");

                    Elements urls = mBody.select("a");
                    Elements link = urls.tagName(".href");
                    titleList.clear();
                    titleList1.clear();
                    for (Element i : mBody) {
                        titleList.add(i.text());
                    }


                    for (Element titles : link) {
                        titleList1.add(titles.attr("abs:href"));
                    }
                }
                if(arg[0].equals("https://m.sputnik.by/"))
                {
                    doc = Jsoup.connect("https://m.sputnik.by/").get();

                    Elements mBody = doc.select("div.b-stories__title");

                    Elements urls = mBody.select("a");
                    Elements link = urls.tagName(".href");
                    titleList.clear();
                    titleList1.clear();
                    for (Element i : mBody) {
                        titleList.add(i.text());
                    }


                    for (Element titles : link) {
                        titleList1.add(titles.attr("abs:href"));
                    }
                }


                Log.e("urls", titleList1.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            // после запроса обновляем листвью
            lv.setAdapter(adapter);
        }


    }

}
