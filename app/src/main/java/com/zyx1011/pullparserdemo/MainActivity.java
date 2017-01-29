package com.zyx1011.pullparserdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.tv_result)
    TextView mTvResult;
    @Bind(R.id.btn_store)
    Button mBtnStore;

    private List<Book> mLists;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void printMethod() {
        mTvResult.setText("");
        for (Book book : mLists) {
            mTvResult.append(book.toString() + "\n");
        }
    }

    private void parserMethod() {
        InputStream is = null;
        try {
            if (mBtnStore.getVisibility() == View.GONE) {
                is = getResources().getAssets().open("book.xml");
                toast("assets");
            } else {
                is = new FileInputStream(new File(getExternalFilesDir(""), "xml.txt"));
                toast("files");
            }

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(is, "UTF-8");

            Book book = null;

            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("图书".equals(pullParser.getName())) {
                            book = new Book();
                            String id = pullParser.getAttributeValue(null, "编号");
                            book.setId(id);
                        }
                        if ("书名".equals(pullParser.getName())) {
                            String title = pullParser.nextText().trim();
                            book.setTitle(title);
                        }
                        if ("作者".equals(pullParser.getName())) {
                            String author = pullParser.nextText().trim();
                            book.setAuthor(author);
                        }
                        if ("价格".equals(pullParser.getName())) {
                            String price = pullParser.nextText().trim();
                            book.setPrice(price);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        mLists.add(book);
                        break;
                    default:
                        break;
                }
                eventType = pullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick({R.id.btn_submit, R.id.btn_store})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                mLists = new ArrayList<>();
                parserMethod();
                printMethod();
                toast("size: " + mLists.size());

                mBtnStore.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_store:
                storeMethod();
                toast("size: " + mLists.size());
                break;
        }
    }

    private void storeMethod() {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(getExternalFilesDir(""), "xml.txt"));

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlSerializer xmlSerializer = factory.newSerializer();
            xmlSerializer.setOutput(os, "utf-8");

            xmlSerializer.startDocument("utf-8", true);
            for (Book book : mLists) {
                xmlSerializer.startTag(null, "图书");
                xmlSerializer.attribute(null, "编号", book.getId());
                xmlSerializer.startTag(null, "书名");
                xmlSerializer.text(book.getTitle());
                xmlSerializer.endTag(null, "书名");
                xmlSerializer.startTag(null, "作者");
                xmlSerializer.text(book.getAuthor());
                xmlSerializer.endTag(null, "作者");
                xmlSerializer.startTag(null, "价格");
                xmlSerializer.text(book.getPrice());
                xmlSerializer.endTag(null, "价格");
                xmlSerializer.endTag(null, "图书");
            }
            xmlSerializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
