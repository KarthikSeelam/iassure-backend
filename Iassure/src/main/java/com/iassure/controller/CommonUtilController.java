package com.iassure.controller;

import com.iassure.util.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rjanumpally
 */
@RestController
@RequestMapping("/api/common/util")
public class CommonUtilController {

    @Autowired
    EncryptDecrypt edUtil;

    @GetMapping("/decrypt/{data}")
    public ResponseEntity<?> decryptValue(@PathVariable(value = "data") String data) {

        if ("".equals(data)) {
            return new ResponseEntity<>("Please provide value to decrypt.",
                    HttpStatus.BAD_REQUEST);
        }
        String decryptedValue = edUtil.decrypt(data);
        return new ResponseEntity<>( decryptedValue,HttpStatus.OK);
    }
    
}
