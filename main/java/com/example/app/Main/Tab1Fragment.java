package com.example.app.Main;

import static android.support.v4.content.ContextCompat.startActivities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.DataBase.DataBaseHelper;
import com.example.app.Game.GameConf;
import com.example.app.Game.GameService;
import com.example.app.Game.GameServiceImpl;
import com.example.app.Game.GameView;
import com.example.app.Utils.ImageUtil;
import com.example.app.Utils.LinkInfo;
import com.example.app.MainActivity;
import com.example.app.Piece.Piece;
import com.example.app.R;
import com.example.app.Ranking.RankingActivity;

import java.util.Timer;
import java.util.TimerTask;

/* 游戏主界面 */
public class Tab1Fragment extends Fragment {
    private GameConf config;
    private ViewPager mVp;
    private boolean Over=false;
    private GameService gameService;
    private GameView gameView;
    private Button startButton;
    private Button rankButton;
    private Button easyButton;
    private Button middleButton;
    private Button normalButton;
    private Button insaneButton;
    private TextView timeTextView;
    private AlertDialog.Builder lostDialog;
    private AlertDialog.Builder successDialog;
    private Timer timer;
    private int gameTime;
    private int time;
    private boolean isPlaying = false;
    private Piece selectedPiece = null;
    private SoundPool soundPool = new SoundPool(2,AudioManager.STREAM_SYSTEM , 8);
    private int sdp;
    private int wrong;
    private DataBaseHelper myDBHelper;
    private SQLiteDatabase db;
    private EditText et;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null,false);
        gameView = view.findViewById(R.id.gameView);
        setHasOptionsMenu(true);
        timeTextView = view.findViewById(R.id.timeText);//添加倒计时文本框
        timeTextView.setVisibility(View.INVISIBLE);//开始时默认为不可视，优势开始时显示
        startButton = view.findViewById(R.id.startButton);//定义默认模式开始按钮
        rankButton = view.findViewById(R.id.btn_rank);//定义排行榜按钮
        //定义四种模式开始按钮
        easyButton = view.findViewById(R.id.btneasy);
        middleButton = view.findViewById(R.id.btnmiddle);
        normalButton = view.findViewById(R.id.btnnormal);
        insaneButton = view.findViewById(R.id.btninsane);
        // 定义消除音效
        sdp = soundPool.load(getContext(),R.raw.sdq,1);
        wrong = soundPool.load(getContext(),R.raw.wrong,1);
        init();
        return view;
    }
    //构造右上角下拉框
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(1,1,1,"打乱重排");


    }
    //定义下拉框内容功能
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){

            case 1:
                if(isPlaying)
                {gameService.shuffle();
                gameView.postInvalidate();}
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化游戏的方法
     */
    private void init() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        config = new GameConf(7, 8, screenWidth, screenHeight, GameConf.DEFAULT_TIME, getContext());
        gameService = new GameServiceImpl(this.config);
        et = new EditText(getContext());
        gameView.setGameService(gameService);
        gameView.setSelectImage(ImageUtil.getSelectImage(getContext()));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                //构造3*4大小的面板开始游戏
                config.setxSize(3);
                config.setySize(4);
                config.setBeginImage();
                startGame(20);//定义不同模式下的游戏惩罚时间，简单模式惩罚20s
                //其他按钮不可视
                startButton.setVisibility(View.INVISIBLE);
                rankButton.setVisibility(View.INVISIBLE);
                easyButton.setVisibility(View.INVISIBLE);
                middleButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);
                insaneButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        rankButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //跳转到排行榜界面
                Intent intent = new Intent();
                intent.setClass(getContext(), RankingActivity.class);
                startActivity(intent);
            }
        });
        easyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                config.setxSize(3);
                config.setySize(4);
                config.setBeginImage();
                startGame(20);//定义不同模式下的游戏惩罚时间，简单模式惩罚20s
                startButton.setVisibility(View.INVISIBLE);
                rankButton.setVisibility(View.INVISIBLE);
                easyButton.setVisibility(View.INVISIBLE);
                middleButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);
                insaneButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        middleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                config.setxSize(4);
                config.setySize(4);
                config.setBeginImage();
                startGame(15);//定义不同模式下的游戏惩罚时间，本模式惩罚15s
                startButton.setVisibility(View.INVISIBLE);
                rankButton.setVisibility(View.INVISIBLE);
                easyButton.setVisibility(View.INVISIBLE);
                middleButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);
                insaneButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        normalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                config.setxSize(4);
                config.setySize(5);
                config.setBeginImage();
                startGame(10);//定义不同模式下的游戏惩罚时间，本模式惩罚10s
                startButton.setVisibility(View.INVISIBLE);
                rankButton.setVisibility(View.INVISIBLE);
                easyButton.setVisibility(View.INVISIBLE);
                middleButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);
                insaneButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        insaneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                config.setxSize(5);
                config.setySize(6);
                config.setBeginImage();
                startGame(0);//定义不同模式下的游戏惩罚时间，本模式不惩罚
                startButton.setVisibility(View.INVISIBLE);
                rankButton.setVisibility(View.INVISIBLE);
                easyButton.setVisibility(View.INVISIBLE);
                middleButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);
                insaneButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        // 为游戏区域的触碰事件绑定监听器
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    gameViewTouchDown(e);
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    gameViewTouchUp(e);
                }
                return true;
            }
        });
        // 初始化游戏失败的对话框
        lostDialog = createDialog("游戏失败", "超时,请重新开始游戏", R.drawable.lost)
                .setPositiveButton("重新开始这一局", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(0);
                    }
                });
        // 初始化游戏胜利的对话框
        successDialog = createDialog("成功完成", "请输入您的游戏名",
                R.drawable.success).setView(et).setPositiveButton("导入排行榜",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        myDBHelper = new DataBaseHelper(getContext(),"ranking",null,1);
                        db = myDBHelper.getWritableDatabase();
                        Time t=new Time();
                        t.setToNow();
                        int year = t.year;
                        int month = t.month+1;
                        int day = t.monthDay;
                        String date = year+"/"+month+"/"+day;
                        ContentValues cv = new ContentValues();
                        cv.put("name",input);
                        cv.put("time",String.valueOf(gameTime));
                        cv.put("date",date);
                        db.insert("users",null,cv);
                        Intent intent = new Intent();
                        intent.setAction("top.ysccx.broadcast");
                        intent.putExtra("name",input);
                        intent.putExtra("time",String.valueOf(gameTime));
                        getActivity().sendBroadcast(intent);
                        Intent intent1 = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent1);//跳回主菜单
                    }
                });
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    time=150-gameTime;
                    timeTextView.setText("倒计时： " +time);
                    gameTime++; // 游戏剩余时间减少
                    // 时间小于0, 游戏失败
                    if (gameTime >150) {
                        stopTimer();//游戏失败，停止计时
                        isPlaying = false;//更改游戏的状态
                        lostDialog.show();//失败后弹出对话框
                        return;
                    }
                    break;
            }
        }
    };



    private void gameViewTouchDown(MotionEvent event) {
        Piece[][] pieces = gameService.getPieces();
        float touchX = event.getX();
        Log.i("X",String.valueOf(touchX));
        float touchY = event.getY();
        Log.i("Y",String.valueOf(touchY));
        Piece currentPiece = gameService.findPiece(touchX, touchY);
        if (currentPiece == null)
            return;
        this.gameView.setSelectedPiece(currentPiece);
        if (this.selectedPiece == null) {
            this.selectedPiece = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        // 表示之前已经选择了一个
        if (this.selectedPiece != null) {
            LinkInfo linkInfo = this.gameService.link(this.selectedPiece,
                    currentPiece);
            if (linkInfo == null) {
                this.selectedPiece = currentPiece;
                if(((MainActivity)getActivity()).sound){
                    soundPool.play(wrong, 0.5f, 0.5f, 0, 0, 1);
                }
                this.gameView.postInvalidate();
            } else {
                handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
            }
        }
    }


    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }


    private void startGame(int gameTime) {
        this.gameTime = gameTime;
        gameView.startGame();
        isPlaying = true;
        if(timer==null) {
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0x123);
                }
            }, 0, 1000);
        }
        this.selectedPiece = null;
    }


    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {

        this.gameView.setLinkInfo(linkInfo);// 它们可以相连, 让GamePanel处理LinkInfo
        this.gameView.setSelectedPiece(null);// 将选中方块清空
        this.gameView.postInvalidate();
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;// 将两个Piece对象从数组中删除
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        this.selectedPiece = null;
        if(((MainActivity)getActivity()).sound){
            soundPool.play(sdp, 0.5f, 0.5f, 0, 0, 1);
        }
        // 判断是否还有剩下的方块, 如果没有, 游戏胜利
        if (!this.gameService.hasPieces()) {
            // 第一回合回合结束，开始更难的第二回合
            if(Over==false)//判断是否进行下一回合
            {
                config.setxSize(6);
                config.setySize(6);
                config.setBeginImage();
                startGame(gameTime-10);}//回合通关，奖励10s
            Over=!Over;//更改判标，将Over定义为true下次循环时可结束游戏
            if(Over==false)//判断是否通关
            {this.successDialog.show();
                // 停止定时器
                stopTimer();
            }
        }
    }



    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(getContext()).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    private void stopTimer() {
        // 停止定时器
        if(timer!=null) {
            this.timer.cancel();
            this.timer = null;
        }
    }
}
