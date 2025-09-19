package com.blog.Controller;

import com.blog.Dto.ApiResponse;
import com.blog.Dto.CateogaryDto;
import com.blog.Entity.Cateogary;
import com.blog.Service.CateogaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cateogary")
public class CateogaryController {

    @Autowired
    public CateogaryService cateogaryService;

    @PostMapping("/")
    public ResponseEntity<CateogaryDto> creating(@RequestBody CateogaryDto cateogaryDto)
    {
        CateogaryDto cateogary = this.cateogaryService.createCateogary(cateogaryDto);
        return new ResponseEntity<CateogaryDto>(cateogary, HttpStatus.OK);
    }



    @PutMapping("/{cateogaryId}")
    public ResponseEntity<CateogaryDto> updating(@RequestBody CateogaryDto cateogaryDto,@PathVariable("cateogaryId") Integer cateogaryId)
    {
        CateogaryDto cateogary = this.cateogaryService.updateCateogary(cateogaryDto,cateogaryId);
        return new ResponseEntity<CateogaryDto>(cateogary, HttpStatus.OK);
    }



    @DeleteMapping("/{cateogaryId}")
    public ResponseEntity<ApiResponse> deleting(@PathVariable("cateogaryId") Integer cateogaryId)
    {
           this.cateogaryService.delete(cateogaryId);
           return new ResponseEntity<ApiResponse>(new ApiResponse ("CateogaryDeleted" , true),HttpStatus.OK);
    }



    @GetMapping("/{cateogaryId}")
    public ResponseEntity<CateogaryDto> gettingOne(@PathVariable("cateogaryId") Integer cateogaryId)
    {
        CateogaryDto cateogary = this.cateogaryService.getCateogary(cateogaryId);

        return new ResponseEntity<CateogaryDto>(cateogary, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CateogaryDto>> gettingAll()
    {
        List<CateogaryDto> all = this.cateogaryService.getAll();

        return new ResponseEntity<List<CateogaryDto>>(all , HttpStatus.OK);
    }
}
