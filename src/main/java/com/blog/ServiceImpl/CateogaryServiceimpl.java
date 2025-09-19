package com.blog.ServiceImpl;

import com.blog.Dto.CateogaryDto;
import com.blog.Entity.Cateogary;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Repositary.CateogaryRepo;
import com.blog.Service.CateogaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CateogaryServiceimpl implements CateogaryService {

    @Autowired
    public CateogaryRepo cateogaryRepo;

    @Autowired
    public ModelMapper modelMapper;


    @Override
    public CateogaryDto createCateogary(CateogaryDto cateogarDto) {

        Cateogary map = this.modelMapper.map(cateogarDto, Cateogary.class);
        Cateogary save = this.cateogaryRepo.save(map);
        return this.modelMapper.map(save,CateogaryDto.class);
    }

    @Override
    public CateogaryDto updateCateogary(CateogaryDto cateogarDto, Integer cateogaryId) {

        Cateogary cateogary= this.cateogaryRepo.findById(cateogaryId).orElseThrow(()->new ResourceNotFoundException("cateogaryID" , "id not found" ,cateogaryId));
        cateogary.setCateogaryTitle(cateogarDto.getCateogaryTitle());
        cateogary.setCateogaryDescription(cateogarDto.getCateogaryDescription());
        Cateogary save = this.cateogaryRepo.save(cateogary);
        return this.modelMapper.map(save,CateogaryDto.class);
    }

    @Override
    public CateogaryDto getCateogary(Integer cateogaryId) {
        Cateogary cateogary = this.cateogaryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("cateogaryId", "not found", cateogaryId));
        return this.modelMapper.map(cateogary,CateogaryDto.class);
    }

    @Override
    public List<CateogaryDto> getAll() {

        List<Cateogary> all = this.cateogaryRepo.findAll();
        List<CateogaryDto> collect = all.stream().map(a -> this.modelMapper.map(a, CateogaryDto.class)).collect(Collectors.toList());


        return collect;
    }

    @Override
    public void delete(Integer cateogaryId) {
        Cateogary cateogary = this.cateogaryRepo.findById(cateogaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cateogary", "id", cateogaryId));

        this.cateogaryRepo.delete(cateogary);
    }
}
