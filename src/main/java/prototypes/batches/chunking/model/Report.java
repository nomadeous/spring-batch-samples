/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypes.batches.chunking.model;

import java.math.BigDecimal;
import java.util.Date;

public class Report {

    private int id;
    private Date date;
    private long impression;
    private int clicks;
    private BigDecimal earning;

    //getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getImpression() {
        return impression;
    }

    public void setImpression(long impression) {
        this.impression = impression;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public BigDecimal getEarning() {
        return earning;
    }

    public void setEarning(BigDecimal earning) {
        this.earning = earning;
    }

}
