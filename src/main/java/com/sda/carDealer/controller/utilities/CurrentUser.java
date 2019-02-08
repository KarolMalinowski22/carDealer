package com.sda.carDealer.controller.utilities;

import com.sda.carDealer.model.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
    public static String getUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof AppUserDetails){
            return  ((AppUserDetails) principal).getUsername();
        }else{
            return null;
        }
    }
}
