package com.es.phoneshop.parser;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.helping.ApplicationMessage;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;

public class AttributeParser {
    public AttributeParser() {

    }

    public Integer parseAttribute(HttpServletRequest request, String attributeString) throws CommonException {

        if(attributeString == null || attributeString.isEmpty()) {
            throw new CommonException(ApplicationMessage.EMPTY_FIELD);
        }
        Integer quantity = null;
        try {
            char test = attributeString.charAt(attributeString.length() - 1);
            int numberTest = Integer.parseInt(String.valueOf(test));
            Double tempDouble =  DecimalFormat.getNumberInstance(request.getLocale()).parse(attributeString).doubleValue();
            Integer tempInteger = tempDouble.intValue();
            if(!tempDouble.equals(tempInteger.doubleValue())) {
                throw new CommonException(ApplicationMessage.FRACTIONAL);
            }
            quantity =  tempInteger;
        }
        catch (NumberFormatException | ParseException e) {
            throw new CommonException(ApplicationMessage.NOT_NUMBER);
        }
        if(quantity <= 0) {
            throw new CommonException(ApplicationMessage.LESS_EQUAL_ZERO);
        }
        return quantity;
    }
}
