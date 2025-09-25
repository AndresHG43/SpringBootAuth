package com.auth.authorization.controller;

import com.auth.authorization.utils.OutMessage;
import com.auth.authorization.utils.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller extends PageableUtils {
    @Autowired
    public OutMessage message;
}
