package com.example.demo.dto.join;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartOrganDTO implements JoinItemDTO<PartDTO,OrganDTO> {

    private Long id;
    private PartDTO part;
    private OrganDTO organ;

    public PartOrganDTO(PartDTO part, OrganDTO organ) {
        super();
        this.part = part;
        this.organ = organ;
    }

    @Override
    public String getName() {
        return getFullName();
    }

    @Override
    public String getFullName() {
        return part.getName()+organ.getName();
    }

    @Override
    public PartDTO getFirstDTO() {
        return part;
    }

    @Override
    public OrganDTO getSecondDTO() {
        return organ;
    }
}
