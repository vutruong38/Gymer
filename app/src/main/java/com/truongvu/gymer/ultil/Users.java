package com.truongvu.gymer.ultil;

public class Users {
    private String ngSing, SDT;
    private Float chieuCao,canNang;
    private int loaitk;
    private String id;

    public Users() {
    }

    public Users(String ngSing, String SDT, Float chieuCao, Float canNang, int loaitk, String id) {
        this.ngSing = ngSing;
        this.SDT = SDT;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
        this.loaitk = loaitk;
        this.id = id;
    }

    public String getNgSing() {
        return ngSing;
    }

    public void setNgSing(String ngSing) {
        this.ngSing = ngSing;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Float getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Float chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Float getCanNang() {
        return canNang;
    }

    public void setCanNang(Float canNang) {
        this.canNang = canNang;
    }

    public int getLoaitk() {
        return loaitk;
    }

    public void setLoaitk(int loaitk) {
        this.loaitk = loaitk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
