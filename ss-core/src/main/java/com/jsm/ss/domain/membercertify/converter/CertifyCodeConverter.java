package com.jsm.ss.domain.membercertify.converter;

import com.jsm.ss.domain.membercertify.enums.CertifyCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CertifyCodeConverter implements AttributeConverter<CertifyCode, String> {

    @Override
    public String convertToDatabaseColumn(CertifyCode attribute) {
        return attribute.getCode();
    }

    @Override
    public CertifyCode convertToEntityAttribute(String dbData) {
        return CertifyCode.ofCode(dbData);
    }
}
