package id.my.cariberas.pengeluaranharian;

public class Grocery {
    private String id_pengeluaran, title, fee, tanggal;

    public Grocery(String id_pengeluaran, String title, String fee, String tanggal) {
        this.id_pengeluaran = id_pengeluaran;
        this.title = title;
    }

    public String getId_pengeluaran() {
        return id_pengeluaran;
    }

    public void setId_pengeluaran(String id_pengeluaran) {
        this.id_pengeluaran = id_pengeluaran;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTanggal() {
        return title;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}