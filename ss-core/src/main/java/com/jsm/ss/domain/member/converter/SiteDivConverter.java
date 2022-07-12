package com.jsm.ss.domain.member.converter;

import com.jsm.ss.domain.member.enums.SiteDiv;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SiteDivConverter implements AttributeConverter<SiteDiv, String> {

    @Override
    public String convertToDatabaseColumn(SiteDiv attribute) {
        return attribute.getCode();
    }

    @Override
    public SiteDiv convertToEntityAttribute(String dbData) {
        return SiteDiv.ofCode(dbData);
    }
}
