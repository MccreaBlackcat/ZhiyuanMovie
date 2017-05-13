package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.QuestionAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Question;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QuestionActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.question_toolbar)
    Toolbar question_toolbar;
    @Bind(R.id.lv_question)
    ListView lv_question;
    @Bind(R.id.btn_contact)
    Button btn_contact;

    private List<Question> mQuestions;
    private QuestionAdapter adapter;
    //记录手机拨号权限
    boolean isOk = false;
    private static final int TAG_PERMISSION = 1023;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        adapter = new QuestionAdapter(this);
        initView();
        initListener();
        initData();
    }


    @Override
    protected int setContent() {
        return R.layout.activity_question;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(question_toolbar);
        getSupportActionBar().setTitle("客服");
        question_toolbar.setNavigationIcon(R.drawable.back);
        question_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initListener() {
        lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //弹出一个dialog
                new AlertDialog.Builder(QuestionActivity.this)
                        .setTitle(mQuestions.get(i).getQuestion())
                        .setMessage(mQuestions.get(i).getAnswer())
                        .setNegativeButton("我知道了", null)
                        .show();
            }
        });
    }

    private void initData() {
        BmobQuery<Question> query = new BmobQuery<>();
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if (e == null) {
                    mQuestions = list;
                    adapter.setQuestions(list);
                    lv_question.setAdapter(adapter);
                } else {

                }
            }
        });
    }


    @OnClick(R.id.btn_contact)
    public void onClick(View view) {
        //调用本地拨号
        new AlertDialog.Builder(QuestionActivity.this)
                .setTitle("警告！")
                .setMessage("您将拨打：" + 10086)
                .setPositiveButton("拨号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //判断是否有拨打电话的权限，返回值为true是还没有授权或者已经拒绝
                        if (ActivityCompat.checkSelfPermission(QuestionActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //返回值为true，已经拒绝授权，返回值为false，还没选择是否授权
                            if (ActivityCompat.shouldShowRequestPermissionRationale(QuestionActivity.this, Manifest.permission.CALL_PHONE)) {
                            } else {
                                //调用系统方法，设置权限
                                ActivityCompat.requestPermissions(QuestionActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        TAG_PERMISSION);
                                if (isOk) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + 10086));
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(callIntent);
                                }
                            }
                        } else {
//                                        Toast.makeText(PhoneNumberActivity.this, "已经授权", Toast.LENGTH_SHORT).show();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + 10086));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case TAG_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isOk = true;
                    Toast.makeText(this, "已经授权", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "拒绝授权", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
