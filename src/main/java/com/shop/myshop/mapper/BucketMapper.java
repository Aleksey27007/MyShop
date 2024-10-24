package com.shop.myshop.mapper;

import com.shop.myshop.dto.BucketDto;
import com.shop.myshop.dto.CategoryDto;
import com.shop.myshop.model.Bucket;
import com.shop.myshop.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BucketMapper {
    private final ModelMapper modelMapper;

    public BucketDto convertToDto(Bucket bucket) {
        BucketDto bucketDto = modelMapper.map(bucket, BucketDto.class);
        bucketDto.setFirstName(bucket.getUser().getFirstName());
        return bucketDto;
    }
}
