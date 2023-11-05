package com.theocean.fundering.domain.member.controller;

import com.theocean.fundering.domain.member.service.MyFundingService;
import com.theocean.fundering.global.jwt.userInfo.CustomUserDetails;
import com.theocean.fundering.global.utils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyFundingController {
    private final MyFundingService myFundingService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myfunding/host")
    public ResponseEntity<?> findAllPostingByHost(
            @AuthenticationPrincipal final CustomUserDetails userDetails,
            @PageableDefault final Pageable pageable) {
        var page = myFundingService.findAllPostingByHost(userDetails.getId(), pageable);
        return ResponseEntity.ok(ApiUtils.success(page));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myfunding/support")
    public ResponseEntity<?> findAllPostingBySupport(
            @AuthenticationPrincipal final CustomUserDetails userDetails,
            @PageableDefault final Pageable pageable
    ) {
        var page = myFundingService.findAllPostingBySupporter(userDetails.getId(), pageable);
        return ResponseEntity.ok(ApiUtils.success(page));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myfunding/nickname")
    public ResponseEntity<?> getNickname(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        String nickname = myFundingService.getNickname(userDetails.getId());
        return ResponseEntity.ok(ApiUtils.success(nickname));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myfunding/followers")
    public ResponseEntity<?> findFollowingCelebs(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        var followingCelebs = myFundingService.findFollowingCelebs(userDetails.getId());
        return ResponseEntity.ok(ApiUtils.success(followingCelebs));
    }


    @PostMapping("/myfunding/withdrawal")
    public ResponseEntity<?> approvalWithdrawal(
            @AuthenticationPrincipal final CustomUserDetails userDetails,
            @RequestParam final Long postId
    ) {
        myFundingService.applyWithdrawal(userDetails.getId(), postId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}