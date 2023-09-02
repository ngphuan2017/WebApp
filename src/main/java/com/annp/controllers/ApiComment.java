/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Comment;
import com.annp.pojo.Product;
import com.annp.pojo.Report;
import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.service.CommentService;
import com.annp.service.ProductService;
import com.annp.service.UserService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiComment {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable(value = "productId") int id) {
        List<Comment> comments = this.commentService.getCommentsByProductId(id);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping(path = "/products/{productId}/comments", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, String> params,
            @PathVariable(value = "productId") int id, Principal pricipal) {
        Product p = this.productService.getProductById(id);
        Users u = this.userService.getUserByUsername(pricipal.getName());

        Comment c = new Comment();
        c.setId(0);
        c.setContent(params.get("content"));
        c.setProductid(p);
        c.setUserid(u);
        if (this.commentService.addComment(c) != null) {
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/products/{productId}/rating")
    public ResponseEntity rating(@PathVariable(value = "productId") int id,
            @RequestBody Map<String, Integer> params, Principal pricipal) {
        try {
            Users u = this.userService.getUserByUsername(pricipal.getName());
            if (u != null) {
                Product p = this.productService.getProductById(id);
                BigDecimal star = new BigDecimal(params.get("star").toString());
                BigDecimal reviewCount = new BigDecimal(p.getReviewCount());
                BigDecimal averageRating = p.getAverageRating();
                BigDecimal newAverageRating = (reviewCount.multiply(averageRating).add(star)).divide(reviewCount.add(BigDecimal.ONE), 9, RoundingMode.HALF_UP);
                p.setAverageRating(newAverageRating);
                p.setReviewCount(p.getReviewCount() + 1);
                if (this.productService.addOrUpdateProduct(p)) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/products/{productId}/comments/voted/{commentId}")
    public ResponseEntity votedComment(@PathVariable(value = "productId") int productId, @PathVariable(value = "commentId") int commentId,
            @RequestBody Map<String, Integer> params, Principal pricipal) {
        try {
            Users u = this.userService.getUserByUsername(pricipal.getName());
            if (u != null) {
                Comment c = this.commentService.getCommentById(commentId);
                if (params.get("vote") == 1) {
                    c.setLikeCount(c.getLikeCount() + 1);
                } else {
                    c.setDislike(c.getDislike() + 1);
                }
                if (this.commentService.updateComment(c)) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/products/{productId}/comments/report/{commentId}")
    public ResponseEntity reportComment(@PathVariable(value = "productId") int productId, 
            @PathVariable(value = "commentId") int commentId, Principal pricipal) {
        try {
            Users u = this.userService.getUserByUsername(pricipal.getName());
            if (u != null) {
                Comment c = this.commentService.getCommentById(commentId);
                Report r = this.commentService.getReportByCommentId(commentId);
                if (r == null) {
                    r = new Report();
                    r.setId(0);
                    r.setCommentid(c);
                    r.setUserid(c.getUserid());
                    r.setContent(c.getContent());
                    r.setReportCount(1);
                    r.setReportstatus(new Status(15));
                    r.setCreatedDate(new Date());
                } else {
                    r.setUpdatedDate(new Date());
                    r.setReportCount(r.getReportCount() + 1);
                }
                if (this.commentService.addOrUpdateReportComment(r)) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(path = "/products/{productId}/comments/changed/{commentId}")
    public ResponseEntity changeComment(@PathVariable(value = "productId") int productId, @PathVariable(value = "commentId") int commentId,
            @RequestBody Map<String, String> params, Principal pricipal) {
        try {
            Users u = this.userService.getUserByUsername(pricipal.getName());
            if (u != null) {
                Comment c = this.commentService.getCommentById(commentId);
                c.setContent(params.get("content"));
                if (this.commentService.updateComment(c)) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/products/{productId}/comments/deleted/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(value = "commentId") int id) {
        this.commentService.deleteComment(id);
    }
}
