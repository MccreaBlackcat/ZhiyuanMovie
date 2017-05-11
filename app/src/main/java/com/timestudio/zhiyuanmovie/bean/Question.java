package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/5/12.
 */

public class Question extends BmobObject {

    private String question; //问题
    private String answer; //答案

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
