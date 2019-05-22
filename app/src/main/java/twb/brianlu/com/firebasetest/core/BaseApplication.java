package twb.brianlu.com.firebasetest.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseApplication extends Application {

    public static List<String> tags;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    //7611ce223ea0842
    //2f18ee6998162e046f860e709d0b0fcf080552e2
    public static Context getContext() {
        initTags();
        return context;
    }

    public static void initTags() {
        String[] t = new String[]{
                "sport", "work", "talk", "Index", "MV", "office", "Lyric", "Youtube"
                , "Instagram", "Facebook", "WeChat", "Wootalk", "Dcard", "Discord"
                , "TWB", "Allo", "Zenly", "Tos", "Trello", "Github", "Gitlab", "WaCare", "Google"
                , "Gmail", "Excel", "Word", "PowerPoint", "WPS Office", "dfhsaljk", "jfd;g", "ghdfls", "jghfls", "jfhlg"
        };
        tags = new ArrayList<>();
        tags.add("旅遊");
        tags.add("健行");
        tags.add("露營");
        tags.add("游泳");
        tags.add("衝浪");
        tags.add("浮潛");
        tags.add("潛水");
        tags.add("極限運動");
        tags.add("生存遊戲");
        tags.add("射擊");
        tags.add("红色");
        tags.add("黑色");
        tags.add("花边色");
        tags.add("深蓝色");
        tags.add("白色");
        tags.add("玫瑰红色");
        tags.add("紫黑紫兰色");
        tags.add("葡萄红色");
        tags.add("绿色");
        tags.add("彩虹色");
        tags.add("牡丹色");
        tags.add("周杰伦");
        tags.add("刘德华");
        tags.add("林俊杰");
        tags.add("邓紫棋");
        tags.add("华晨宇");
        tags.add("王力宏");
        tags.add("Linkin Park");
        tags.add("legend never die");
        tags.add("红莲之弓矢");
        tags.add("Let It Go");
        tags.add("青鸟");
        tags.add("in the end");
        tags.add("Hey Jude");
        tags.add("告白气球");
        tags.add("see you again");
        tags.add("faded");
        tags.add("篮球");
        tags.add("网球");
        tags.add("棒球");
        tags.add("乒乓球");
        tags.add("足球");
        tags.add("跑步");
        tags.add("游泳");
        tags.add("冰球");
        tags.add("高尔夫球");
        tags.add("橄榄球");
        tags.add("羽毛球");
        tags.add("桌球");
        tags.add("beyond");
        tags.add("房東的貓");
        tags.add("癡心絕對");
        tags.add("哭砂");
        tags.add("馮提莫");
        tags.addAll(Arrays.asList(t));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


}
