package com.vchat.managedbeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.vchat.managedbeans.TrimString")
public class TrimString implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object modelValue) {
		if (!(modelValue instanceof String)) {
            return (String) modelValue; // Or throw ConverterException, your choice.
        }
		String trimedString = (String) modelValue;
		if(trimedString.length()>500){
			trimedString = trimedString.substring(0, 555).concat("........");
		}
		return trimedString;
	}

}	
