package com.example.camcustomeraccessmethod.DBManager;

public class DbAnswerManager
{
    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public boolean isResult()
    {
        return result;
    }

    public void setResult(boolean result)
    {
        this.result = result;
    }

    String answer;
    boolean result;
}
