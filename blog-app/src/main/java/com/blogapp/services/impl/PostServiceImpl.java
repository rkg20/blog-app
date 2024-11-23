package com.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.models.Category;
import com.blogapp.models.Post;
import com.blogapp.models.User;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;


    public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId){

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));

        Post post=this.dtoToPost(postDto);
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImage("default.png");
        post.setPostDate(new Date());
        post.setUser(user);
    
        post.setCategory(category);

        // System.out.println(user.getUserId()+" "+user.getUserName()+" ");
        // System.out.println(category.getCategoryId()+" "+category.getCategoryTitle()+" ");

        Post savedPost=this.postRepo.save(post);
        // System.out.println(savedPost.getPostId());
        // System.out.println(savedPost.getPostTitie());
        // System.out.println(savedPost.getPostContent());
        // System.out.println(savedPost.getPostDate());
        // System.out.println(savedPost.getCategory().getCategoryTitle());
        // System.out.println(savedPost.getUser().getUserName());

        PostDto savedPostDto=this.postToDto(savedPost);
        return savedPostDto;
    }

    
    public PostDto updatePost(PostDto postDto,Integer postId) {

        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        
        // User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "Id", userId));

        // Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImage(post.getPostImage());
        post.setPostDate(new Date());
        // post.setUser(user);
        // post.setCategory(category);

        Post updatedPost=this.postRepo.save(post);
        PostDto updatedPostDto=this.postToDto(updatedPost);
        return updatedPostDto;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));

        PostDto postDto=this.postToDto(post);
        return postDto;
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize) {
        
        PageRequest pageable=PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost =this.postRepo.findAll(pageable);

        List<Post> posts=pagePost.getContent();
        List<PostDto>postDtos=posts.stream().map((post)->
        this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        
        PostResponse postResponse=new PostResponse();
        postResponse.setContext(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse; 
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        
        this.postRepo.delete(post);
    }

    

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));

        List<Post> posts=this.postRepo.findByCategory(category);

        List<PostDto> postDtos=posts.stream().map((post)->
            this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }

    @Override
    public List<PostDto> getPostByUser(Integer userId,Integer pageNumber,Integer pageSize) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));

        // PageRequest pageable=PageRequest.of(pageNumber, pageSize);
        // Page<Post> pagePost=(Page<Post>) this.postRepo.findByUser(pageable);
        
        List<Post> posts=this.postRepo.findByUser(user);
        // for (Post post : posts) {
        //     System.out.print(post.getPostId()+" ");
        // }
        List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        // for (PostDto postDto : postDtos) {
        //     System.out.print(postDto.getPostId()+" ");
        // }

        // PostResponse postResponse=new PostResponse();
        // postResponse.setContext(postDtos);
        // postResponse.setPageNumber(pagePost.getNumber());
        // postResponse.setPageSize(pagePost.getSize());
        // postResponse.setTotalElements(pagePost.getTotalElements());
        // postResponse.setTotalPages(pagePost.getTotalPages());
        // postResponse.setLastPage(pagePost.isLast());

        return postDtos;
        
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts=this.postRepo.findByPostTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    public PostDto postToDto(Post post){
        PostDto postDto=this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    public Post dtoToPost(PostDto postDto){
        Post post=this.modelMapper.map(postDto, Post.class);
        return post;
    }


    
}
