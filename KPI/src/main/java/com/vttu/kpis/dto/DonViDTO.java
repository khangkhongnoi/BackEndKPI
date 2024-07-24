package com.vttu.kpis.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonViDTO {
     int madonvi;
     String tendonvi;
     String mota;

    public DonViDTO(int madonvi, String tendonvi, String mota) {
        this.madonvi = madonvi;
        this.tendonvi = tendonvi;
        this.mota = mota;
    }

    public void setMadonvi(int madonvi) {
        this.madonvi = madonvi;
    }

    public void setTendonvi(String tendonvi) {
        this.tendonvi = tendonvi;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getMadonvi() {
        return madonvi;
    }

    public String getTendonvi() {
        return tendonvi;
    }

    public String getMota() {
        return mota;
    }
}
