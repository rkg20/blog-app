package com.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.models.Category;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.dtoToCategory(categoryDto);
        Category saveCategory=this.categoryRepo.save(category);
        return this.categorytToDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());

        Category updatedCategory=this.categoryRepo.save(category);
        CategoryDto updatedCategoryDto=this.categorytToDto(updatedCategory);
        return updatedCategoryDto;

    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        return this.categorytToDto(category);
   
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories=this.categoryRepo.findAll();
        List<CategoryDto> CategoriesDto=categories.stream().map(category->this.categorytToDto(category)).collect(Collectors.toList());

        return CategoriesDto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));

        this.categoryRepo.delete(category);
    

    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category=this.modelMapper.map(categoryDto,Category.class );
        return category;
    }

    public CategoryDto categorytToDto(Category category){
        CategoryDto categoryDto= this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    

}
