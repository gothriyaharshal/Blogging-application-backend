package com.blog.Service;

import com.blog.Dto.CateogaryDto;
import java.util.List;

public interface CateogaryService {

    public CateogaryDto createCateogary(CateogaryDto cateogarDto);

    public CateogaryDto updateCateogary(CateogaryDto cateogarDto,Integer cateogaryId);

    public CateogaryDto getCateogary(Integer cateogaryId);

    public List<CateogaryDto> getAll();

    public void delete(Integer cateogaryId);
}
