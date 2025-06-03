package com.edu.lx.cms.utils;

import com.edu.lx.cms.enums.MsgEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
    private String status;
    private String message;
    private Object data;

    public static JsonResult error(MsgEnum error) {
        String msg = error.toString();
        return new JsonResult("fail", msg, null);
    }

    public static JsonResult success(MsgEnum suc) {
        String msg = suc.toString();
        return new JsonResult("success", msg, null);
    }

    public static JsonResult success(MsgEnum suc, Object data) {
        String msg = suc.toString();
        return new JsonResult("success", msg, data);
    }
}