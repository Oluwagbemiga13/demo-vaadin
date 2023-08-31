package com.example.demo.dto.join;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganSymptomDTO implements JoinItemDTO<OrganDTO,SymptomDTO>{

    private Long id;
    private OrganDTO organDTO;
    private SymptomDTO symptomDTO;

    public OrganSymptomDTO(OrganDTO organDTO, SymptomDTO symptomDTO) {
        this.organDTO = organDTO;
        this.symptomDTO = symptomDTO;
    }

    @Override
    public String getName() {
        return  getFullName();
    }

    @Override
    public String getFullName() {
        return organDTO.getName() + "+"  + symptomDTO.getName();
    }

    @Override
    public OrganDTO getFirstDTO() {
        return organDTO;
    }

    @Override
    public SymptomDTO getSecondDTO() {
        return symptomDTO;
    }
}
