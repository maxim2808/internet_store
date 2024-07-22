package com.example.internet_store.dto;

import java.util.ArrayList;
import java.util.List;

public class ManufactuterDTOList {
    List<ManufacturerDTO> manufacturerDTOList = new ArrayList<ManufacturerDTO>();
    public void addManufacturerDTO(ManufacturerDTO manufacturerDTO) {
        manufacturerDTOList.add(manufacturerDTO);
    }

    public List<ManufacturerDTO> getManufacturerDTOList() {
        return manufacturerDTOList;
    }

    public void setManufacturerDTOList(List<ManufacturerDTO> manufacturerDTOList) {
        this.manufacturerDTOList = manufacturerDTOList;
    }
}
