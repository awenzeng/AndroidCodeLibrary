package com.awen.codebase.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.MainAdapter;
import com.awen.codebase.common.base.BaseActivity;


@Route(path = ActivityRouter.AROUTER_VueFrameActivity)
public class VueFrameActivity extends BaseActivity {
    private ListView listView;
    private String[] iStrings = {"Vue移动端框架","Vant-有赞", "Mint-饿了么", "WotDesign-京东", "NutUI-京东", "vux",
            "vonic","Cube-滴滴", "Mand-金融", "vue-charts-图表", "vue-recyclerview",
            "Carbon-Material设计","VueBaiduMap"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        listView = this.findViewById(R.id.listView);
        MainAdapter myAdapter = new MainAdapter(this, iStrings);
        listView.setAdapter(myAdapter);
        myAdapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onClick(int position, String data) {
                Intent intent = null;
                String url = "";
                switch (data){
                    case "Vue移动端框架":
                        url = "https://www.jianshu.com/p/1e05c8d68407";
                        break;
                    case "Vant-有赞":
                        url = "https://youzan.github.io/vant/mobile.html#/zh-CN/";
                        break;
                    case "Mint-饿了么":
                        url = "http://elemefe.github.io/mint-ui/#/";
                        break;
                    case "WotDesign-京东":
                        url = "https://ftf.jd.com/wot-design/demo.html#/";
                        break;
                    case "NutUI-京东":
                        url = "https://nutui.jd.com/demo.html#/index";
                        break;
                    case "vux":
                        url = "https://vux.li/demos/v2/?x-page=v2-doc-home#/";
                        break;
                    case "vonic":
                        url = "https://wangdahoo.github.io/vonic/docs/#/home";
                        break;
                    case "Cube-滴滴":
                        url = "https://didi.github.io/cube-ui/example/#/";
                        break;
                    case "Mand-金融":
                        url = "https://didi.github.io/mand-mobile/examples/#/category";
                        break;
                    case "vue-charts-图表":
                        url = "https://codesandbox.io/s/z69myovqzx";
                        break;
                    case "vue-recyclerview":
                        url = "https://hilongjw.github.io/vue-recyclerview/";
                        break;
                    case "Carbon-Material设计":
                        url = "http://vue-js-modal.yev.io/";
                        break;
                    case "VueBaiduMap":
                        url = "https://dafrok.github.io/vue-baidu-map/#/zh/guide/painting";
                        break;
                    default:
                        break;
                }
                intent = new Intent(VueFrameActivity.this,WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL,url);
                startActivity(intent);
            }
        });
    }
    


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
