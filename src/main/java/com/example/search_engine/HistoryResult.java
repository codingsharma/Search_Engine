package com.example.search_engine;

import java.sql.Timestamp;

public class HistoryResult {
    private String Keyword;
    private Timestamp timestamp;

    public String getKeyword(){
        return this.Keyword;
    }
    public void setKeyword(String keyword){
        this.Keyword=keyword;
    }
    public Timestamp getTimestamp(){
        return this.timestamp;
    }
    public void setTimestamp(Timestamp time){
        this.timestamp=time;
    }
    HistoryResult(String keyword,Timestamp time){
        this.Keyword=keyword;
        this.timestamp=time;
    }
}
