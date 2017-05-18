package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class CommentActivity extends BaseActivity implements View.OnClickListener,CommentView{

    @Bind(R.id.comment_toolbar)
    Toolbar comment_toolbar;
    @Bind(R.id.tv_comment_mv_name)
    TextView tv_comment_mv_name;
    @Bind(R.id.iv_comment_mv)
    ImageView iv_comment_mv;
    @Bind(R.id.et_comment)
    EditText et_comment;
    @Bind(R.id.btn_putComment)
    Button btn_putComment;

    private Order mOrder;
    private String commentStr = "";
    private CommentPresenter presenter = new CommentPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        presenter.attachView(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {
        mOrder = (Order) getIntent().getSerializableExtra("order");
        ButterKnife.bind(this);
        setSupportActionBar(comment_toolbar);
        getSupportActionBar().setTitle("评论");
        comment_toolbar.setNavigationIcon(R.drawable.back);
        tv_comment_mv_name.setText(mOrder.getOrderName());
        ImageLoader.getInstance().displayImage(mOrder.getPhoto(),
                iv_comment_mv,
                ImageLoadOptions.build_item());
    }

    @Override
    protected void initListener() {
        comment_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                commentStr = editable.toString();
            }
        });
    }

    @OnClick(R.id.btn_putComment)
    public void onClick(View view) {
        //发布评论
        if (!commentStr.equals("")) {
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            presenter.onPutComment(commentStr, myUser, mOrder);
        } else {
            Toast.makeText(this, "评论不能为空！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCommentSuccess() {
        Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCommentFailure() {

    }
}
