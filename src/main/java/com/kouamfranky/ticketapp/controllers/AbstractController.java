package com.kouamfranky.ticketapp.controllers;

import com.kouamfranky.ticketapp.exceptions.MethodArgumentInvalidException;
import com.kouamfranky.ticketapp.utils.StringsUtils;
import org.springframework.validation.BindingResult;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 07/juin/2024 -- 05:26
 *
 * @author name :  Franky Brice on 07/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.controllers
 **/
public abstract class AbstractController {
    public void checkValidationFieldsOnDto(BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentInvalidException(StringsUtils.BINDING_RESULT_ERROR, result.getFieldErrors());
        }
    }
}
