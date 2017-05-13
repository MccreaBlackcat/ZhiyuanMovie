package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Question;

import java.util.List;

/**
 * Created by strongShen on 2017/5/12.
 */

public class QuestionAdapter extends BaseAdapter {

    private List<Question> questions;
    private LayoutInflater inflater;

    public QuestionAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QuestionHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_question, null);
            holder = new QuestionHolder();
            holder.tv_question = (TextView) view.findViewById(R.id.tv_question);
            view.setTag(holder);
        } else {
            holder = (QuestionHolder) view.getTag();
        }
        holder.tv_question.setText(questions.get(i).getQuestion());
        return view;
    }

    class QuestionHolder{
        TextView tv_question;
    }

}
