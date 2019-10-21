package id.my.cariberas.pengeluaranharian;

public class Contacts {
    private int id_pengeluaran;
    private String title, fee, tanggal;

    public Contacts( String title, String fee, String tanggal) {
        this.title = title;
        this.fee = fee;
        this.tanggal = tanggal;
    }

    public Contacts(int id_pengeluaran, String title, String fee, String tanggal) {
        this.id_pengeluaran = id_pengeluaran;
        this.title = title;
        this.fee = fee;
        this.tanggal = tanggal;
    }

    public int getId_pengeluaran() {
        return id_pengeluaran;
    }

    public void setId_pengeluaran(int id_pengeluaran) {
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